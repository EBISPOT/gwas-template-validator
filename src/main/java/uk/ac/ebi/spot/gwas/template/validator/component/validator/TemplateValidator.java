package uk.ac.ebi.spot.gwas.template.validator.component.validator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;
import uk.ac.ebi.spot.gwas.template.validator.domain.SubmissionDocument;
import uk.ac.ebi.spot.gwas.template.validator.domain.ValidationConfiguration;

import java.util.List;
import java.util.Map;

public interface TemplateValidator {

    void validateSheet(Sheet sheet, ValidationConfiguration validationConfiguration, Map<String, String> studyTags);

    boolean handleValidRow(String studyTag, Map<String, String> studyTags, String sheetName);

    void convertSheet(Sheet sheet, ValidationConfiguration validationConfiguration, SubmissionDocument submissionDocument);

    void convertRow(Row row, List<CellValidation> columns, SubmissionDocument submissionDocument);
}
