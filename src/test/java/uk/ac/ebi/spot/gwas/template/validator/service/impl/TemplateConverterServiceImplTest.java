package uk.ac.ebi.spot.gwas.template.validator.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ebi.spot.gwas.template.validator.domain.SubmissionDocument;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateConverterService;
import uk.ac.ebi.spot.gwas.template.validator.util.FileSubmissionTemplateReader;
import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

import javax.annotation.PreDestroy;
import java.io.File;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TemplateConverterServiceImplTest extends IntegrationTest {

    @Autowired
    private TemplateConverterService templateConverterService;

    private SubmissionTemplateReader submissionTemplateReader;

    @Before
    public void setup() {
        String inputFile = "valid.xlsx";
        File file = new File(getClass().getClassLoader().getResource(inputFile).getFile());
        submissionTemplateReader = new FileSubmissionTemplateReader(file);
    }

    @PreDestroy
    public void shutdown() {
        submissionTemplateReader.close();
    }

    @Test
    public void shouldCovertSubmissionDocument() {
        SubmissionDocument submissionDocument = templateConverterService.convert(submissionTemplateReader);
        assertFalse(submissionDocument.getStudyEntries().isEmpty());
        assertFalse(submissionDocument.getAssociationEntries().isEmpty());
        assertFalse(submissionDocument.getSampleEntries().isEmpty());
        assertTrue(submissionDocument.getNoteEntries().isEmpty());

        assertEquals(2, submissionDocument.getStudyEntries().size());
        assertEquals(4, submissionDocument.getAssociationEntries().size());
        assertEquals(7, submissionDocument.getSampleEntries().size());
    }
}