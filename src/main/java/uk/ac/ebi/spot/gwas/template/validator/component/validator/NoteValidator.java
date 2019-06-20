package uk.ac.ebi.spot.gwas.template.validator.component.validator;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import uk.ac.ebi.spot.gwas.template.validator.config.ValidatorConstants;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;
import uk.ac.ebi.spot.gwas.template.validator.domain.Note;
import uk.ac.ebi.spot.gwas.template.validator.domain.SubmissionDocument;

import java.util.List;
import java.util.Map;

@Component(ValidatorConstants.NOTE)
public class NoteValidator extends AbstractTemplateValidator implements TemplateValidator {

    @Override
    public boolean handleValidRow(String studyTag, Map<String, String> studyTags, String sheetName) {
        if (!studyTags.containsKey(studyTag)) {
            System.err.println("Sheet [" + sheetName + "] is not valid. Orphan association found.");
            return false;
        }

        return true;
    }

    @Override
    public void convertRow(Row row, List<CellValidation> columns, SubmissionDocument submissionDocument) {
        Note note = new Note();
        convertToObject(note, row, columns, submissionDocument);
        submissionDocument.addNote(note);
    }

}
