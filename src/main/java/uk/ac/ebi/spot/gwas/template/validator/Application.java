package uk.ac.ebi.spot.gwas.template.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.ac.ebi.spot.gwas.template.validator.component.parser.CellValidationParserAdapterFactory;
import uk.ac.ebi.spot.gwas.template.validator.component.validator.TemplateValidatorAdapterFactory;
import uk.ac.ebi.spot.gwas.template.validator.domain.ValidationOutcome;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateValidatorService;
import uk.ac.ebi.spot.gwas.template.validator.util.FileSubmissionTemplateReader;
import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

import java.io.File;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Bean
    public ServiceLocatorFactoryBean templateValidatorAdapterFactoryBean() {
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(TemplateValidatorAdapterFactory.class);
        return bean;
    }

    @Bean
    public ServiceLocatorFactoryBean cellValidationParserAdapterFactoryBean() {
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(CellValidationParserAdapterFactory.class);
        return bean;
    }

    @Bean
    public TemplateValidatorAdapterFactory templateValidatorAdapterFactory() {
        return (TemplateValidatorAdapterFactory) templateValidatorAdapterFactoryBean().getObject();
    }

    @Bean
    public CellValidationParserAdapterFactory cellValidationParserAdapterFactory() {
        return (CellValidationParserAdapterFactory) cellValidationParserAdapterFactoryBean().getObject();
    }

    @Autowired
    private TemplateValidatorService templateValidatorService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        /*
        if (args.length != 1) {
            System.err.println("Please specify an input file.");
        }*/
        SubmissionTemplateReader submissionTemplateReader = new FileSubmissionTemplateReader(
                new File("/home/tudor/dev/gwas-template-validator/src/test/resources/incorrect_accepted_value.xlsx"));
        ValidationOutcome validationOutcome = templateValidatorService.validate(submissionTemplateReader);

        if (validationOutcome.isValid()) {
            System.out.println("No errors found while validating submission!");
        } else {
            Map<String, List<String>> errors = validationOutcome.getErrorMessages();
            System.err.println("Errors found while validating submission!");
            for (String sheet : errors.keySet()) {
                System.err.println("Sheet: " + sheet);
                for (String error : errors.get(sheet)) {
                    System.err.println(" - " + error);
                }
            }
        }

        submissionTemplateReader.close();
    }
}
