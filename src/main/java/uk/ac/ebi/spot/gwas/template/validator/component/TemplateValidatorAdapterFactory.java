package uk.ac.ebi.spot.gwas.template.validator.component;

public interface TemplateValidatorAdapterFactory {

    TemplateValidator getAdapter(String adapterName);

}
