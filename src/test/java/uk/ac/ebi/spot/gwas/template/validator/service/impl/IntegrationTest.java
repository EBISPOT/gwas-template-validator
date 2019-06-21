package uk.ac.ebi.spot.gwas.template.validator.service.impl;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.spot.gwas.template.validator.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class},
        initializers = ConfigFileApplicationContextInitializer.class)
public class IntegrationTest {
}
