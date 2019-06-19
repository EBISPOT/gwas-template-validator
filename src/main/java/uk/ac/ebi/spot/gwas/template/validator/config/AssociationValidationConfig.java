package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.stereotype.Component;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AssociationValidationConfig {

    public int getStartingRow() {
        return 4;
    }

    public Map<Integer, CellValidation> getColumns() {
        Map<Integer, CellValidation> columnValidation = new LinkedHashMap<>();

        columnValidation.put(0, new CellValidation("studyTag", "String", true));
        columnValidation.put(1, new CellValidation("haplotypeId", "String", false));
        columnValidation.put(2, new CellValidation("variantId", "String", true));
        columnValidation.put(3, new CellValidation("pvalue", "Double", true, 0.0, 0.00001));
        columnValidation.put(4, new CellValidation("pvalueText", "String", false));
        columnValidation.put(5, new CellValidation("proxyVariant", "String", false));
        columnValidation.put(6, new CellValidation("effectAllele", "String", true, "^[actgACTG]*$"));
        columnValidation.put(7, new CellValidation("otherAllele", "String", false, "^[actgACTG]*$"));
        columnValidation.put(8, new CellValidation("effectAlleleFrequency", "Double", true));
        columnValidation.put(9, new CellValidation("oddsRatio", "Double", false));
        columnValidation.put(10, new CellValidation("beta", "Double", false));
        columnValidation.put(11, new CellValidation("beta_unit", "String", false));
        columnValidation.put(12, new CellValidation("ciLower", "Double", false));
        columnValidation.put(13, new CellValidation("ciUpper", "Double", false));
        columnValidation.put(14, new CellValidation("standardError", "Double", false));

        return columnValidation;
    }

}
