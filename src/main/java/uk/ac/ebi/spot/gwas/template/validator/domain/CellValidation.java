package uk.ac.ebi.spot.gwas.template.validator.domain;

import java.util.List;

public class CellValidation {

    private String columnName;

    private String baseType;

    private boolean required;

    private String pattern;

    private Double lowerBound;

    private Double upperBound;

    private List<String> acceptedValues;

    public CellValidation() {

    }

    public CellValidation(String columnName, String baseType, boolean required) {
        this.columnName = columnName;
        this.baseType = baseType;
        this.required = required;
    }

    public CellValidation(String columnName, String baseType, boolean required, Double lowerBound, Double upperBound) {
        this.columnName = columnName;
        this.baseType = baseType;
        this.required = required;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public CellValidation(String columnName, String baseType, boolean required, String pattern) {
        this.columnName = columnName;
        this.baseType = baseType;
        this.required = required;
        this.pattern = pattern;
    }

    public CellValidation(String columnName, String baseType, boolean required, List<String> acceptedValues) {
        this.columnName = columnName;
        this.baseType = baseType;
        this.required = required;
        this.acceptedValues = acceptedValues;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Double upperBound) {
        this.upperBound = upperBound;
    }

    public List<String> getAcceptedValues() {
        return acceptedValues;
    }

    public void setAcceptedValues(List<String> acceptedValues) {
        this.acceptedValues = acceptedValues;
    }
}
