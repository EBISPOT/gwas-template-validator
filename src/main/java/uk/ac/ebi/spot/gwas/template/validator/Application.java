package uk.ac.ebi.spot.gwas.template.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.ac.ebi.spot.gwas.template.validator.component.parser.CellValidationParserAdapterFactory;
import uk.ac.ebi.spot.gwas.template.validator.component.validator.TemplateValidatorAdapterFactory;
import uk.ac.ebi.spot.gwas.template.validator.domain.SubmissionDocument;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateConverterService;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateValidatorService;
import uk.ac.ebi.spot.gwas.template.validator.util.FileSubmissionTemplateReader;
import uk.ac.ebi.spot.gwas.template.validator.util.SubmissionTemplateReader;

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

    @Autowired
    private TemplateConverterService templateConverterService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        String inputFile = "/home/tudor/dev/gwas-template-validator/src/test/resources/example.xlsx";

        SubmissionTemplateReader submissionTemplateReader = new FileSubmissionTemplateReader(inputFile);
        templateValidatorService.validate(submissionTemplateReader);
        SubmissionDocument submissionDocument = templateConverterService.convert(submissionTemplateReader);
        submissionTemplateReader.close();

    }
}
