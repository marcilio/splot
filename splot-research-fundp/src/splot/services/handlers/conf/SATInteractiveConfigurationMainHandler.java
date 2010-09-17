package splot.services.handlers.conf;

import javax.servlet.http.HttpServlet;

import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationEngineException;
import splar.plugins.configuration.sat.sat4j.SATConfigurationEngine;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class SATInteractiveConfigurationMainHandler extends InteractiveConfigurationMainHandler2 {
	
	public static final String featureTemplateFilename = "interactive_configuration_element_feature.ftl";
	
	public SATInteractiveConfigurationMainHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
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

