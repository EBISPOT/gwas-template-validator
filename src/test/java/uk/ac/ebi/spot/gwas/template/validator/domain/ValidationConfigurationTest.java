package uk.ac.ebi.spot.gwas.template.validator.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ValidationConfigurationTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(ValidationConfiguration.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}