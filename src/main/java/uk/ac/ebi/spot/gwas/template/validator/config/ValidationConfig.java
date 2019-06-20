package uk.ac.ebi.spot.gwas.template.validator.config;

public interface ValidationConfig {

    String getSheetName();

    String getSheetValidationComponent();

    String getValidationConfigFormat();

    String getValidationConfigLocation();

    boolean isStudies();
}
