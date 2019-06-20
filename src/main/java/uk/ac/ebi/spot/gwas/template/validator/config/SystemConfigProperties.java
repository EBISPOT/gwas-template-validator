package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class SystemConfigProperties {

    @Autowired
    private StudiesConfig studiesConfig;

    @Autowired
    private AssociationsConfig associationsConfig;

    @Autowired
    private SamplesConfig samplesConfig;

    @Autowired
    private NotesConfig notesConfig;

    private List<ValidationConfig> validationConfigList;

    @PostConstruct
    public void initialize() {
        validationConfigList = new ArrayList<>();
        validationConfigList.add(studiesConfig);
        validationConfigList.add(associationsConfig);
        validationConfigList.add(samplesConfig);
        validationConfigList.add(notesConfig);
    }

    public List<ValidationConfig> getValidationConfigList() {
        return validationConfigList;
    }
}
