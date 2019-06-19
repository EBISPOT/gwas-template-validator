package uk.ac.ebi.spot.gwas.template.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.ac.ebi.spot.gwas.template.validator.component.TemplateValidatorAdapterFactory;
import uk.ac.ebi.spot.gwas.template.validator.service.TemplateValidatorService;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Bean
    public ServiceLocatorFactoryBean templateValidatorAdapterFactoryBean() {
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(TemplateValidatorAdapterFactory.class);
        return bean;
    }

    @Bean
    public TemplateValidatorAdapterFactory templateValidatorAdapterFactory() {
        return (TemplateValidatorAdapterFactory) templateValidatorAdapterFactoryBean().getObject();
    }

    @Autowired
    private TemplateValidatorService templateValidatorService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        String inputFile = "/home/tudor/dev/gwas-template-validator/src/test/resources/example.xlsx";

        templateValidatorService.validate(inputFile);
    }
}
