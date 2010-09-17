package splot.services.handlers.conf;

import java.io.File;

import javax.servlet.http.HttpServlet;

import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationEngineException;
import splar.plugins.configuration.bdd.javabdd.BDDConfigurationEngine;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class BDDInteractiveConfigurationMainHandler extends InteractiveConfigurationMainHandler {
	
	public static final String featureTemplateFilename = "catalog_interactive_configuration_element_feature.ftl";

	public BDDInteractiveConfigurationMainHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}

	protected String getFeatureTemplateFile() {
		return featureTemplateFilename;
	}
	
	protected ConfigurationEngine createConfigurationEngine(String modelLocatorString) throws HandlerExecutionException {
		try {
			
			File f = new File(modelLocatorString);
			String baseFileName = f.getName().split("\\.")[0];
			String baseFilePath = getResourcePath();
			String catalogFileName = baseFileName + "_catalog.csv";
			String catalogFilePath = baseFilePath + catalogFileName;
			
			// Creates configuration engine reasoner from file (BDD file and Keys file)
			BDDConfigurationEngine confEngine = new BDDConfigurationEngine(modelLocatorString, baseFilePath, baseFileName);
			confEngine.addProductCatalog(catalogFilePath);
			
			return confEngine;
			
		} catch (ConfigurationEngineException e1) {
			throw new HandlerExecutionException("Error reading CSV file for creating BDD configuration engine", e1);
		} catch (Exception e2) {
			throw new HandlerExecutionException("Error creating BDD configuration engine.", e2);
		}
	}
		
	protected String getResourcePath() {
		return getServlet().getInitParameter("modelsPath") + "catalog" + System.getProperty("file.separator");
	}
} 

