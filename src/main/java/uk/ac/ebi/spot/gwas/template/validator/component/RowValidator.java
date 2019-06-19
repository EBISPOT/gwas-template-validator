package uk.ac.ebi.spot.gwas.template.validator.component;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;

import java.util.Iterator;
import java.util.Map;

public class RowValidator {

    private Map<Integer, CellValidation> columns;

    private String studyTag;

    private boolean valid;

    public RowValidator(Row row, Map<Integer, CellValidation> columns) {
        this.columns = columns;
        this.valid = true;
        this.processRow(row);
    }

    private void processRow(Row currentRow) {
        Iterator<Cell> cellIterator = currentRow.cellIterator();
        int cellPosition = 0;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            CellValidation cellValidation = columns.get(cellPosition);
            if (cellPosition > columns.size() - 1) {
                break;
            }

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
                if (cellValidation.getColumnName().equalsIgnoreCase("studyTag")) {
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
                            if (!value.equalsIgnoreCase("yes") && !value.equalsIgnoreCase("no")) {
                                valid = false;
                            }
                        }
                    }
                }
            }

            cellPosition++;
        }
    }

    public String getStudyTag() {
        return studyTag;
    }

    public boolean isValid() {
        return valid;
    }
}
