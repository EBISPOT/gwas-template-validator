package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AssociationsConfig implements ValidationConfig {

    @Value("${validation.associations.sheet:associations}")
    private String sheetName;

    @Value("${validation.associations.component:ASSOCIATION}")
    private String sheetValidationComponent;

    @Value("${validation.associations.configuration.format:YAML}")
    private String validationConfigFormat;

    @Value("${validation.associations.configuration.location:associations.yml}")
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
        return false;
    }
}
