package splot.services.extensions.fundp.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import splot.core.HandlerBasedServlet;
import splot.services.extensions.fundp.handlers.CreateFeatureModelViewsHandler;
import freemarker.template.Configuration;


/**
 * Servlet implementation class SplotMain
 */
public class MultiplePerspectiveConfigurationViewsServlet extends HandlerBasedServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultiplePerspectiveConfigurationViewsServlet() {
        super();
    }
    
	Configuration cfg = new Configuration();
    public void createHandlers(ServletConfig config) {
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates/extensions/fundp");
        try {
        	// Add your new handles here
        	addHandler(new CreateFeatureModelViewsHandler("multiple_conf_views", this, cfg, cfg.getTemplate("multiple_conf_views.ftl")));
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }
    
}
