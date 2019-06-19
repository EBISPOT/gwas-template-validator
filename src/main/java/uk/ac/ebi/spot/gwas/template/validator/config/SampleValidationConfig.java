package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.stereotype.Component;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class SampleValidationConfig {

    public int getStartingRow() {
        return 4;
    }

    public Map<Integer, CellValidation> getColumns() {
        Map<Integer, CellValidation> columnValidation = new LinkedHashMap<>();

        columnValidation.put(0, new CellValidation("studyTag", "String", true));
        columnValidation.put(1, new CellValidation("stage", "String", true,
                Arrays.asList(new String[]{
                        "discovery",
                        "replication"
                })));
        columnValidation.put(2, new CellValidation("size", "Integer", true));
        columnValidation.put(3, new CellValidation("cases", "Integer", false));
        columnValidation.put(4, new CellValidation("controls", "Integer", false));
        columnValidation.put(5, new CellValidation("sampleDescription", "Integer", false));
        columnValidation.put(6, new CellValidation("ancestryCategory", "String", true));
        columnValidation.put(7, new CellValidation("ancestry", "String", false));
        columnValidation.put(8, new CellValidation("ancestryDescription", "String", false));
        columnValidation.put(9, new CellValidation("countryRecruitement", "String", true));

        return columnValidation;
    }

}
