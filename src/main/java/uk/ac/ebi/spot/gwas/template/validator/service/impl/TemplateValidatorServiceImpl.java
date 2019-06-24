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
import uk.ac.ebi.spot.gwas.template.validator.domain.ValidationOutcome;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateValidatorService;
import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

import java.util.*;

@Service
public class TemplateValidatorServiceImpl implements TemplateValidatorService {

    @Autowired
    private TemplateValidatorAdapterFactory templateValidatorAdapterFactory;

    @Autowired
    private CellValidationParserAdapterFactory cellValidationParserAdapterFactory;

    @Autowired
    private SystemConfigProperties systemConfigProperties;

    @Override
    public ValidationOutcome validate(SubmissionTemplateReader submissionTemplateReader) {
        Map<String, List<String>> errors = new LinkedHashMap<>();
        if (submissionTemplateReader.isValid()) {
            Map<String, String> studyTagMap = readStudiesSheet(submissionTemplateReader, errors);
            Iterator<Sheet> sheets = submissionTemplateReader.sheets();
            while (sheets.hasNext()) {
                Sheet sheet = sheets.next();
                for (ValidationConfig validationConfig : systemConfigProperties.getValidationConfigList()) {
                    if (!validationConfig.isStudies() && sheet.getSheetName().equalsIgnoreCase(validationConfig.getSheetName())) {
                        List<String> errorMap = processSheet(sheet, validationConfig, studyTagMap);
                        if (!errorMap.isEmpty()) {
                            errors.put(sheet.getSheetName(), errorMap);
                        }
                        continue;
                    }
                }
            }
        }

        return new ValidationOutcome(errors.isEmpty(), errors);
    }

    private Map<String, String> readStudiesSheet(SubmissionTemplateReader submissionTemplateReader,
                                                 Map<String, List<String>> errors) {
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
                List<String> errorMap = processSheet(sheet, studiesConfig, studyTagMap);
                if (!errorMap.isEmpty()) {
                    errors.put(sheet.getSheetName(), errorMap);
                }
                break;
            }
        }
        return studyTagMap;
    }

    private List<String> processSheet(Sheet sheet, ValidationConfig validationConfig, Map<String, String> studyTagMap) {
        TemplateValidator templateValidator = templateValidatorAdapterFactory.getAdapter(validationConfig.getSheetValidationComponent());
        CellValidationParser cellValidationParser = cellValidationParserAdapterFactory.getAdapter(validationConfig.getValidationConfigFormat());
        ValidationConfiguration validationConfiguration = cellValidationParser.parseCellValidations(validationConfig.getValidationConfigLocation());
        return templateValidator.validateSheet(sheet, validationConfiguration, studyTagMap);
    }
}
