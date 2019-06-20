package uk.ac.ebi.spot.gwas.template.validator.service.impl;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.spot.gwas.template.validator.component.parser.CellValidationParser;
import uk.ac.ebi.spot.gwas.template.validator.component.parser.CellValidationParserAdapterFactory;
import uk.ac.ebi.spot.gwas.template.validator.component.validator.TemplateValidator;
import uk.ac.ebi.spot.gwas.template.validator.component.validator.TemplateValidatorAdapterFactory;
import uk.ac.ebi.spot.gwas.template.validator.config.SystemConfigProperties;
import uk.ac.ebi.spot.gwas.template.validator.config.ValidationConfig;
import uk.ac.ebi.spot.gwas.template.validator.domain.ValidationConfiguration;
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
    private CellValidationParserAdapterFactory cellValidationParserAdapterFactory;

    @Autowired
    private SystemConfigProperties systemConfigProperties;

    @Override
    public void validate(SubmissionTemplateReader submissionTemplateReader) {
        if (submissionTemplateReader.isValid()) {
            Map<String, String> studyTagMap = readStudiesSheet(submissionTemplateReader);
            Iterator<Sheet> sheets = submissionTemplateReader.sheets();
            while (sheets.hasNext()) {
                Sheet sheet = sheets.next();
                for (ValidationConfig validationConfig : systemConfigProperties.getValidationConfigList()) {
                    if (!validationConfig.isStudies() && sheet.getSheetName().equalsIgnoreCase(validationConfig.getSheetName())) {
                        processSheet(sheet, validationConfig, studyTagMap);
                        continue;
                    }
                }
            }
        }
    }

    private Map<String, String> readStudiesSheet(SubmissionTemplateReader submissionTemplateReader) {
        Iterator<Sheet> sheets = submissionTemplateReader.sheets();

        Map<String, String> studyTagMap = new HashMap<>();
        ValidationConfig studiesConfig = null;
        for (ValidationConfig validationConfig : systemConfigProperties.getValidationConfigList()) {
            if (validationConfig.isStudies()) {
                studiesConfig = validationConfig;
                break;
            }
        }
        if (studiesConfig == null) {
            System.err.println("Unable to find studies validation configuration. Aborting process.");
            return null;
        }

        while (sheets.hasNext()) {
            Sheet sheet = sheets.next();
            if (sheet.getSheetName().equalsIgnoreCase(studiesConfig.getSheetName())) {
                processSheet(sheet, studiesConfig, studyTagMap);
                break;
            }
        }
        return studyTagMap;
    }

    private void processSheet(Sheet sheet, ValidationConfig validationConfig, Map<String, String> studyTagMap) {
        TemplateValidator templateValidator = templateValidatorAdapterFactory.getAdapter(validationConfig.getSheetValidationComponent());
        CellValidationParser cellValidationParser = cellValidationParserAdapterFactory.getAdapter(validationConfig.getValidationConfigFormat());
        ValidationConfiguration validationConfiguration = cellValidationParser.parseCellValidations(validationConfig.getValidationConfigLocation());
        templateValidator.validateSheet(sheet, validationConfiguration, studyTagMap);
    }
}
