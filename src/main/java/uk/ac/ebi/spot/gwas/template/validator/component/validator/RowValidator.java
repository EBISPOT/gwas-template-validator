package uk.ac.ebi.spot.gwas.template.validator.component.validator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;

import java.util.List;

public class RowValidator {

    public final static String BOOL_VALUE_YES = "yes";

    public final static String BOOL_VALUE_NO = "no";

    private List<CellValidation> columns;

    private String studyTag;

    private boolean valid;

    public RowValidator(Row row, List<CellValidation> columns, String studyTagColumnName) {
        this.columns = columns;
        this.valid = true;
        this.processRow(row, studyTagColumnName);
    }

    private void processRow(Row currentRow, String studyTagColumnName) {
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = currentRow.getCell(i);
            CellValidation cellValidation = columns.get(i);

            if (cellValidation.getBaseType().equalsIgnoreCase(String.class.getSimpleName())) {
                String value = null;
                if (cellValidation.isRequired()) {
                    try {
                        value = cell.getStringCellValue();
                    } catch (Exception e) {
                        valid = false;
                    }
                    if ("".equals(value)) {
                        valid = false;
                    }
                }
                if (cellValidation.getAcceptedValues() != null) {
                    if (value != null) {
                        if (!cellValidation.getAcceptedValues().contains(value)) {
                            valid = false;
                        }
                    }
                }
                if (cellValidation.getPattern() != null) {
                    if (value != null) {
                        if (!value.matches(cellValidation.getPattern())) {
                            valid = false;
                        }
                    }
                }
                if (cellValidation.getColumnName().equalsIgnoreCase(studyTagColumnName)) {
                    if (value != null) {
                        studyTag = value;
                    }
                }
            } else {
                if (cellValidation.getBaseType().equalsIgnoreCase(Double.class.getSimpleName()) ||
                        cellValidation.getBaseType().equalsIgnoreCase(Integer.class.getSimpleName())) {
                    Double value = null;
                    if (cellValidation.isRequired()) {
                        try {
                            value = cell.getNumericCellValue();
                        } catch (Exception e) {
                            valid = false;
                        }
                    }
                    if (value != null) {
                        if (cellValidation.getLowerBound() != null) {
                            if (value < cellValidation.getLowerBound()) {
                                valid = false;
                            }
                        }
                        if (cellValidation.getUpperBound() != null) {
                            if (value > cellValidation.getUpperBound()) {
                                valid = false;
                            }
                        }
                    }
                } else {
                    if (cellValidation.getBaseType().equalsIgnoreCase(Boolean.class.getSimpleName())) {
                        String value = null;
                        if (cellValidation.isRequired()) {
                            try {
                                value = cell.getStringCellValue();
                            } catch (Exception e) {
                                valid = false;
                            }
                            if ("".equals(value)) {
                                valid = false;
                            }
                        }
                        if (value != null) {
                            if (!value.equalsIgnoreCase(BOOL_VALUE_YES) && !value.equalsIgnoreCase(BOOL_VALUE_NO)) {
                                valid = false;
                            }
                        }
                    }
                }
            }
        }
    }

    public String getStudyTag() {
        return studyTag;
    }

    public boolean isValid() {
        return valid;
    }
}
