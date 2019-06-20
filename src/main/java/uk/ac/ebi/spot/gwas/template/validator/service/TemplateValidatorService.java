package uk.ac.ebi.spot.gwas.template.validator.service;

import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

public interface TemplateValidatorService {

    void validate(SubmissionTemplateReader submissionTemplateReader);

}
