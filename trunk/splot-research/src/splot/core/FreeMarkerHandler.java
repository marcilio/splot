package splot.core;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class FreeMarkerHandler extends Handler {

	protected Template template;
	protected Configuration configuration;
	
	public FreeMarkerHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet);
		this.template = template;
		this.configuration = configuration;
	}

	public abstract void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException;

	public String getUserId(HttpServletRequest request) {
    	String user = "unknown";
    	if ( request.getSession(false) != null) {
    		user = request.getSession(false).getId();
    	}		
    	return user;
	}
	
	public void error(Map templateModel, String errorMessage, String user) {		
		// log error message on file
		ErrorManager.getManager().logError(getServlet().getInitParameter("logFilePath"), this, errorMessage, user);
		// add error parameters to template model
		templateModel.put("hasError", true);
		templateModel.put("errorMessage", errorMessage);
	}
	
	public void noError(Map templateModel) {
		templateModel.put("hasError", false);
		templateModel.put("errorMessage", "");
	}
	
	public Configuration getTemplateConfiguration() {
		return configuration;
	}
	
	public Template getTemplate() {
		return template;
	}
	
	public void setResponseParameters(HttpServletResponse response) {
		response.setContentType("text/html; charset=" + template.getEncoding());
	}
	
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Map templateModel = new HashMap();
		try {
			buildModel(request, response, templateModel);
	    	noError(templateModel);
		}
		catch( HandlerExecutionException handlerExcObj ) {
			error(templateModel, handlerExcObj.getMessage(), getUserId(request));
		}
                
        setResponseParameters(response);
        Writer out = response.getWriter();
        
        // Merge the data-model and the template
        try {
            template.process(templateModel, out);
        } catch (TemplateException e) {
            throw new ServletException(
                    "Error while processing FreeMarker template", e);
        }
	}	
}
