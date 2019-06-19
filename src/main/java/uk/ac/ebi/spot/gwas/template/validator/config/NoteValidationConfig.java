package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.stereotype.Component;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class NoteValidationConfig {

    public int getStartingRow() {
        return 4;
    }

    public Map<Integer, CellValidation> getColumns() {
        Map<Integer, CellValidation> columnValidation = new LinkedHashMap<>();

        columnValidation.put(0, new CellValidation("studyTag", "String", true));
        columnValidation.put(1, new CellValidation("note", "String", true));
        columnValidation.put(2, new CellValidation("note_subject", "String", true,
                Arrays.asList(new String[]{
                        "Preliminary review",
                        "Review/secondary extraction",
                        "Post-publishing review",
                        "Trait",
                        "Initial extraction",
                        "Other",
                        "Duplication TAG"
                })));
        columnValidation.put(3, new CellValidation("status", "String", true,
                Arrays.asList(new String[]{
                        "private",
                        "public"
                })));

        return columnValidation;
    }

}
