package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotesConfig implements ValidationConfig {

    @Value("${validation.notes.sheet:notes}")
    private String sheetName;

    @Value("${validation.notes.component:NOTE}")
    private String sheetValidationComponent;

    @Value("${validation.notes.configuration.format:YAML}")
    private String validationConfigFormat;

    @Value("${validation.notes.configuration.location:notes.yml}")
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
