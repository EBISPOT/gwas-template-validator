package uk.ac.ebi.spot.gwas.template.validator.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class NoteTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Note.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}