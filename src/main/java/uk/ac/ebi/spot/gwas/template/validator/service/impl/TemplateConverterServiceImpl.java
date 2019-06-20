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
import uk.ac.ebi.spot.gwas.template.validator.domain.SubmissionDocument;
import uk.ac.ebi.spot.gwas.template.validator.domain.ValidationConfiguration;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateConverterService;
import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

import java.util.Iterator;

@Service
public class TemplateConverterServiceImpl implements TemplateConverterService {

    @Autowired
    private TemplateValidatorAdapterFactory templateValidatorAdapterFactory;

    @Autowired
    private CellValidationParserAdapterFactory cellValidationParserAdapterFactory;

    @Autowired
    private SystemConfigProperties systemConfigProperties;

    @Override
    public SubmissionDocument convert(SubmissionTemplateReader submissionTemplateReader) {
        SubmissionDocument submissionDocument = new SubmissionDocument();
        if (submissionTemplateReader.isValid()) {
            Iterator<Sheet> sheets = submissionTemplateReader.sheets();
            while (sheets.hasNext()) {
                Sheet sheet = sheets.next();
                for (ValidationConfig validationConfig : systemConfigProperties.getValidationConfigList()) {
                    if (sheet.getSheetName().equalsIgnoreCase(validationConfig.getSheetName())) {
                        convertSheet(sheet, validationConfig, submissionDocument);
                    }
                }
            }
        }

        return submissionDocument;
    }

    private void convertSheet(Sheet sheet, ValidationConfig validationConfig, SubmissionDocument submissionDocument) {
        TemplateValidator templateValidator = templateValidatorAdapterFactory.getAdapter(validationConfig.getSheetValidationComponent());
        CellValidationParser cellValidationParser = cellValidationParserAdapterFactory.getAdapter(validationConfig.getValidationConfigFormat());
        ValidationConfiguration validationConfiguration = cellValidationParser.parseCellValidations(validationConfig.getValidationConfigLocation());
        templateValidator.convertSheet(sheet, validationConfiguration, submissionDocument);
    }
}
