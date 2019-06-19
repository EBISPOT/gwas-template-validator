package uk.ac.ebi.spot.gwas.template.validator.service.impl;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.spot.gwas.template.validator.component.TemplateValidator;
import uk.ac.ebi.spot.gwas.template.validator.component.TemplateValidatorAdapterFactory;
import uk.ac.ebi.spot.gwas.template.validator.config.SystemConfigProperties;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateValidatorService;
import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class TemplateValidatorServiceImpl implements TemplateValidatorService {

    @Autowired
    private TemplateValidatorAdapterFactory templateValidatorAdapterFactory;

    @Autowired
    private SystemConfigProperties systemConfigProperties;

    @Override
    public void validate(String inputFile) {
        System.out.println("Reading submission from: " + inputFile);
        SubmissionTemplateReader submissionTemplateReader = new SubmissionTemplateReader(inputFile);
        if (submissionTemplateReader.isValid()) {
            Iterator<Sheet> sheets = submissionTemplateReader.sheets();

            Map<String, String> studyTagMap = new HashMap<>();
            int count = 0;
            while (sheets.hasNext()) {
                Sheet sheet = sheets.next();
                TemplateValidator templateValidator = templateValidatorAdapterFactory.getAdapter(systemConfigProperties.getComponentForSheetCount(count));
                templateValidator.validateSheet(sheet, studyTagMap);
                count++;
            }


        }
        submissionTemplateReader.close();
    }
}
