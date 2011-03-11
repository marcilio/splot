package splot.services.extensions.fundp.handlers.conf;

import javax.servlet.http.HttpServlet;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FCWInstanceSATInteractiveConfigurationDetectConflictsHandler extends FCWInstanceInteractiveConfigurationDetectConflictsHandler {

	public FCWInstanceSATInteractiveConfigurationDetectConflictsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	protected String getFeatureTemplateFile() {
		return FCWSATInteractiveConfigurationMainHandler.featureTemplateFilename;
	}

}