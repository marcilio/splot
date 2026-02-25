/*                                    
    * Copyright (c) 2009-2026 Marcilio Mendonca                                            
    * Licensed under the Apache License, Version 2.0 (the "License");                      
    * you may not use this file except in compliance with the License.                     
    */

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

