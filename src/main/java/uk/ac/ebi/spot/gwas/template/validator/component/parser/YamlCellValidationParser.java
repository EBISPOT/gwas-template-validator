package uk.ac.ebi.spot.gwas.template.validator.component.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import uk.ac.ebi.spot.gwas.template.validator.config.ValidatorConstants;
import uk.ac.ebi.spot.gwas.template.validator.domain.ValidationConfiguration;

import java.io.IOException;

@Component(ValidatorConstants.FORMAT_YAML)
public class YamlCellValidationParser implements CellValidationParser {

    private static final Logger log = LoggerFactory.getLogger(YamlCellValidationParser.class);

    public ValidationConfiguration parseCellValidations(String inputFile) {
        try {
            Resource resource = new ClassPathResource(inputFile);
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            ValidationConfiguration validationConfiguration = yamlReader.readValue(resource.getFile(), ValidationConfiguration.class);
            return validationConfiguration;
        } catch (IOException e) {
            log.info("Unable to parse studies validation configuration [{}]", inputFile, e.getMessage());
        }
        return null;
    }
}
