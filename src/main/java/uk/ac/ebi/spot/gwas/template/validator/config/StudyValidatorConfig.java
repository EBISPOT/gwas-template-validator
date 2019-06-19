package uk.ac.ebi.spot.gwas.template.validator.config;

import org.springframework.stereotype.Component;
import uk.ac.ebi.spot.gwas.template.validator.domain.CellValidation;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class StudyValidatorConfig {

    public int getStartingRow() {
        return 4;
    }

    public Map<Integer, CellValidation> getColumns() {
        Map<Integer, CellValidation> columnValidation = new LinkedHashMap<>();

        columnValidation.put(0, new CellValidation("studyTag", "String", true));
        columnValidation.put(1, new CellValidation("genotypingTechnology", "String", true,
                Arrays.asList(new String[]{
                        "Genome-wide genotyping array",
                        "Targeted genotyping array",
                        "Exome genotyping array",
                        "Whole genome sequencing"
                })));
        columnValidation.put(2, new CellValidation("arrayManufacturer", "String", false));
        columnValidation.put(3, new CellValidation("arrayInformation", "String", false));
        columnValidation.put(4, new CellValidation("imputation", "Boolean", true));
        columnValidation.put(5, new CellValidation("variantCount", "Integer", true));
        columnValidation.put(6, new CellValidation("statisticalModel", "String", false));
        columnValidation.put(7, new CellValidation("studyDescription", "String", false));
        columnValidation.put(8, new CellValidation("trait", "String", true));
        columnValidation.put(9, new CellValidation("efoTrait", "String", false));
        columnValidation.put(10, new CellValidation("backgroundTrait", "String", false));
        columnValidation.put(11, new CellValidation("backgroundEfoTrait", "String", false));
        columnValidation.put(12, new CellValidation("summaryStatisticsFile", "String", false));
        columnValidation.put(13, new CellValidation("summaryStatisticsAssembly", "String", false));

        return columnValidation;
    }

}
