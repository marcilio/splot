package splot.core.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import splot.core.HandlerBasedServlet;
import splot.core.handlers.ParseFeatureModelForErrorsHandler;
import splot.core.handlers.ShowMessageLogHandler;

import freemarker.template.Configuration;


/**
 * Servlet implementation class SplotCoreServlet
 */
public class SplotCoreServlet extends HandlerBasedServlet {
	private static final long serialVersionUID = 1L;
	
    public SplotCoreServlet() {
        super();
    }
    
	Configuration cfg = new Configuration();
    public void createHandlers(ServletConfig config) {
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
        try {
        	addHandler(new ShowMessageLogHandler("messages_log", this, cfg, cfg.getTemplate("messages_log.ftl")));
        	addHandler(new ParseFeatureModelForErrorsHandler("parse_feature_model_for_errors", this, cfg, cfg.getTemplate("parse_feature_model_for_errors.ftl")));
        }
        catch(IOException e) {
        	System.err.println(e);
        	e.printStackTrace();
        } 
    } 
    
}
