package uk.ac.ebi.spot.gwas.template.validator.domain.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class NoteDtoTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(NoteDto.class)
                .verify();
    }

}