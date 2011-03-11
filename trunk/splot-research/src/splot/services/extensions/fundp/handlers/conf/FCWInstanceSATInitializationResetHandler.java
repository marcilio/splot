package splot.services.extensions.fundp.handlers.conf;

import javax.servlet.http.HttpServlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationEngineException;
import splar.plugins.configuration.sat.sat4j.SATConfigurationEngine;
import splot.core.HandlerExecutionException;



public class FCWInstanceSATInitializationResetHandler extends FCWInstanceInitializationResetHandler{

	public static final String featureTemplateFilename = "fcw_interactive_configuration_element_feature.ftl";
	
	public FCWInstanceSATInitializationResetHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}

	protected String getFeatureTemplateFile() {
		return featureTemplateFilename;
	}
	
	protected ConfigurationEngine createConfigurationEngine(String modelLocatorString) throws HandlerExecutionException {
		try {
			   return new SATConfigurationEngine(modelLocatorString);
		} catch (ConfigurationEngineException e) {
			throw new HandlerExecutionException("Error creating SAT configuration engine.", e);
		}
	}
	
	protected String getResourcePath() {
		return getServlet().getInitParameter("modelsPath");
	}

}
