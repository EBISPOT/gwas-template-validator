package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StudiesConfig implements ValidationConfig {

    @Value("${validation.studies.sheet:studies}")
    private String sheetName;

    @Value("${validation.studies.component:STUDY}")
    private String sheetValidationComponent;

    @Value("${validation.studies.configuration.format:YAML}")
    private String validationConfigFormat;

    @Value("${validation.studies.configuration.location:studies.yml}")
    private String validationConfigLocation;

    public String getSheetName() {
        return sheetName;
    }

    public String getSheetValidationComponent() {
        return sheetValidationComponent;
    }

    public String getValidationConfigFormat() {
        return validationConfigFormat;
    }

    public String getValidationConfigLocation() {
        return validationConfigLocation;
    }

    public boolean isStudies() {
        return true;
    }
}
