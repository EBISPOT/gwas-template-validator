package uk.ac.ebi.spot.gwas.template.validator.component;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

public interface TemplateValidator {

    void validateSheet(Sheet sheet, Map<String, String> studyTags);

}
