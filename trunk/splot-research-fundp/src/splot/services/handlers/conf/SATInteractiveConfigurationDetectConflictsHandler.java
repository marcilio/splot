package splot.services.handlers.conf;

import javax.servlet.http.HttpServlet;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SATInteractiveConfigurationDetectConflictsHandler extends InteractiveConfigurationDetectConflictsHandler {

	public SATInteractiveConfigurationDetectConflictsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	protected String getFeatureTemplateFile() {
		return SATInteractiveConfigurationMainHandler.featureTemplateFilename;
	}

}

