package uk.ac.ebi.spot.gwas.template.validator.component;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.spot.gwas.template.validator.config.SampleValidationConfig;
import uk.ac.ebi.spot.gwas.template.validator.config.ValidatorConstants;

import java.util.Iterator;
import java.util.Map;

@Component(ValidatorConstants.SAMPLE)
public class SampleValidator implements TemplateValidator {

    @Autowired
    private SampleValidationConfig sampleValidationConfig;

    @Override
    public void validateSheet(Sheet sheet, Map<String, String> studyTags) {
        Iterator<Row> rowIterator = sheet.rowIterator();

        int count = 1;
        boolean ok = true;
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            if (count >= sampleValidationConfig.getStartingRow()) {
                RowValidator rowValidator = new RowValidator(currentRow, sampleValidationConfig.getColumns());
                ok = ok && rowValidator.isValid();
                if (!ok) {
                    System.err.println("Sheet provided is not valid. Structural validation failed.");
                    break;
                } else {
                    if (!studyTags.containsKey(rowValidator.getStudyTag())) {
                        System.err.println("Sheet provided is not valid. Orphan association found.");
                        break;
                    }
                }
            }
            count++;
        }

        if (ok) {
            System.out.println("Sheet provided is valid.");
        }
    }
}
