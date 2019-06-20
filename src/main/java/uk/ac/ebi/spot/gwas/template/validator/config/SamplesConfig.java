package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SamplesConfig implements ValidationConfig {

    @Value("${validation.samples.sheet:studies}")
    private String sheetName;

    @Value("${validation.samples.component:SAMPLE}")
    private String sheetValidationComponent;

    @Value("${validation.samples.configuration.format:YAML}")
    private String validationConfigFormat;

    @Value("${validation.samples.configuration.location:samples.yml}")
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
