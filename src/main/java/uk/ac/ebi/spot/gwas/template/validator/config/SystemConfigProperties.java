package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SystemConfigProperties {

//    @Value("${spring.data.mongodb.uri}")
//    private String mongoUri;


    @PostConstruct
    public void initialize() {
    }

    public String getComponentForSheetCount(int sheetCount) {
        switch (sheetCount) {
            case 0:
                return ValidatorConstants.STUDY;
            case 1:
                return ValidatorConstants.ASSOCIATION;
            case 2:
                return ValidatorConstants.SAMPLE;
            case 3:
                return ValidatorConstants.NOTE;
        }

        return null;
    }
}
