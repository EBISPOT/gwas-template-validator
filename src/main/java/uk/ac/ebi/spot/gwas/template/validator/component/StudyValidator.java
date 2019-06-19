package uk.ac.ebi.spot.gwas.template.validator.component;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.spot.gwas.template.validator.config.StudyValidatorConfig;
import uk.ac.ebi.spot.gwas.template.validator.config.ValidatorConstants;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;
import uk.ac.ebi.spot.gwas.template.validator.domain.Study;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

@Component(ValidatorConstants.STUDY)
public class StudyValidator implements TemplateValidator {

    @Autowired
    private StudyValidatorConfig studyValidatorConfig;

    @Override
    public void validateSheet(Sheet sheet, Map<String, String> studyTags) {
        Iterator<Row> rowIterator = sheet.rowIterator();

        int count = 1;
        boolean ok = true;
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if (count >= studyValidatorConfig.getStartingRow()) {
                RowValidator rowValidator = new RowValidator(currentRow, studyValidatorConfig.getColumns());
                ok = ok && rowValidator.isValid();
                if (!ok) {
                    System.err.println("Sheet provided is not valid.");
                    break;
                } else {
                    studyTags.put(rowValidator.getStudyTag(), "");
                }
            }
            count++;
        }

        System.out.println("Sheet provided is valid.");
    }

    private Study rowToStudy(Row row) {
        Map<Integer, CellValidation> columns = studyValidatorConfig.getColumns();
        Study study = new Study();

        Iterator<Cell> cellIterator = row.cellIterator();
        int cellPosition = 0;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            CellValidation cellValidation = columns.get(cellPosition);
            try {
                Field field = study.getClass().getField(cellValidation.getColumnName());
                field.set(study, cell.getStringCellValue());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            cellPosition++;
        }

        return study;
    }
}
