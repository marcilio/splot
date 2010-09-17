package splot.services.extensions.fundp.handlers.conf;

import javax.servlet.http.HttpServlet;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FCWSATInteractiveConfigurationDetectConflictsHandler extends FCWInteractiveConfigurationDetectConflictsHandler {

	public FCWSATInteractiveConfigurationDetectConflictsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	protected String getFeatureTemplateFile() {
		return FCWSATInteractiveConfigurationMainHandler.featureTemplateFilename;
	}

}