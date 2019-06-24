package uk.ac.ebi.spot.gwas.template.validator.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ebi.spot.gwas.template.validator.domain.ValidationOutcome;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateValidatorService;
import uk.ac.ebi.spot.gwas.template.validator.util.FileSubmissionTemplateReader;
import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

import java.io.File;

import static org.junit.Assert.*;

public class TemplateValidatorServiceImplTest extends IntegrationTest {

    private final static String[] TEST_FILES = new String[]{
            "valid.xlsx",
            "missing_mandatory_value.xlsx",
            "incorrect_accepted_value.xlsx",
            "orphan_association.xlsx",
            "orphan_sample.xlsx",
            "wrong_lower_bound.xlsx",
            "wrong_upper_bound.xlsx",
            "wrong_pattern_value.xlsx",
            "wrong_value_type.xlsx"
    };

    @Autowired
    private TemplateValidatorService templateValidatorService;

    @Test
    public void shouldValidateValidSubmission() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(0);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertTrue(validationOutcome.isValid());
        assertTrue(validationOutcome.getErrorMessages().isEmpty());
        submissionTemplateReader.close();
    }

    @Test
    public void shouldNotValidateMissingMandatoryValue() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(1);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertFalse(validationOutcome.isValid());
        assertEquals(1, validationOutcome.getErrorMessages().size());
        assertTrue(validationOutcome.getErrorMessages().containsKey("studies"));
        assertEquals(1, validationOutcome.getErrorMessages().get("studies").size());
        assertEquals("Column 'Variant count' in row '2' lacks mandatory value; Column 'Trait' in row '2' lacks mandatory value", validationOutcome.getErrorMessages().get("studies").get(0));
        submissionTemplateReader.close();
    }

    @Test
    public void shouldNotValidateIncorrectAcceptedValue() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(2);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertFalse(validationOutcome.isValid());
        assertEquals(2, validationOutcome.getErrorMessages().size());
        assertTrue(validationOutcome.getErrorMessages().containsKey("samples"));
        assertEquals(1, validationOutcome.getErrorMessages().get("samples").size());
        assertEquals("Column 'Stage' in row '3' contains incorrect value. Accepted values are: 'discovery; replication'", validationOutcome.getErrorMessages().get("samples").get(0));
        submissionTemplateReader.close();
    }

    @Test
    public void shouldNotValidateOrphanAssociation() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(3);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertFalse(validationOutcome.isValid());
        assertEquals(1, validationOutcome.getErrorMessages().size());
        assertTrue(validationOutcome.getErrorMessages().containsKey("associations"));
        assertEquals(1, validationOutcome.getErrorMessages().get("associations").size());
        assertEquals("Row '4' makes references to a non-existing study tag", validationOutcome.getErrorMessages().get("associations").get(0));
        submissionTemplateReader.close();
    }

    @Test
    public void shouldNotValidateOrphanSample() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(4);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertFalse(validationOutcome.isValid());
        assertEquals(1, validationOutcome.getErrorMessages().size());
        assertTrue(validationOutcome.getErrorMessages().containsKey("samples"));
        assertEquals(1, validationOutcome.getErrorMessages().get("samples").size());
        assertEquals("Row '5' makes references to a non-existing study tag", validationOutcome.getErrorMessages().get("samples").get(0));
        submissionTemplateReader.close();
    }

    @Test
    public void shouldNotValidateWrongLowerBound() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(5);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertFalse(validationOutcome.isValid());
        assertEquals(1, validationOutcome.getErrorMessages().size());
        assertTrue(validationOutcome.getErrorMessages().containsKey("associations"));
        assertEquals(1, validationOutcome.getErrorMessages().get("associations").size());
        assertEquals("Column 'p-value' in row '2' contains incorrect value. Accepted values should be in the range: '0.0-1.0E-5'", validationOutcome.getErrorMessages().get("associations").get(0));
        submissionTemplateReader.close();
    }

    @Test
    public void shouldNotValidateWrongUpperBound() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(6);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertFalse(validationOutcome.isValid());
        assertEquals(1, validationOutcome.getErrorMessages().size());
        assertTrue(validationOutcome.getErrorMessages().containsKey("associations"));
        assertEquals(1, validationOutcome.getErrorMessages().get("associations").size());
        assertEquals("Column 'p-value' in row '1' contains incorrect value. Accepted values should be in the range: '0.0-1.0E-5'", validationOutcome.getErrorMessages().get("associations").get(0));
        submissionTemplateReader.close();
    }

    @Test
    public void shouldNotValidateWrongPattern() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(7);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertFalse(validationOutcome.isValid());
        assertEquals(1, validationOutcome.getErrorMessages().size());
        assertTrue(validationOutcome.getErrorMessages().containsKey("associations"));
        assertEquals(1, validationOutcome.getErrorMessages().get("associations").size());
        assertEquals("Column 'Effect allele' in row '3' contains incorrect value. Accepted values should respect the pattern: '^[actgACTG]*$'", validationOutcome.getErrorMessages().get("associations").get(0));
        submissionTemplateReader.close();
    }

    @Test
    public void shouldNotValidateWrongValueType() {
        SubmissionTemplateReader submissionTemplateReader = getForFile(8);
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);
        assertFalse(validationOutcome.isValid());
        assertEquals(1, validationOutcome.getErrorMessages().size());
        assertTrue(validationOutcome.getErrorMessages().containsKey("studies"));
        assertEquals(1, validationOutcome.getErrorMessages().get("studies").size());
        assertEquals("Column 'Variant count' in row '1' lacks mandatory value", validationOutcome.getErrorMessages().get("studies").get(0));
        submissionTemplateReader.close();
    }

    private SubmissionTemplateReader getForFile(int index) {
        String inputFile = TEST_FILES[index];
        File file = new File(getClass().getClassLoader().getResource(inputFile).getFile());
        return new FileSubmissionTemplateReader(file);
    }
}