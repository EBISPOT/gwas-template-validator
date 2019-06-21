package uk.ac.ebi.spot.gwas.template.validator.service;

import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

import java.util.List;
import java.util.Map;

public interface TemplateValidatorService {

    Map<String, List<String>> validate(SubmissionTemplateReader submissionTemplateReader);

}
