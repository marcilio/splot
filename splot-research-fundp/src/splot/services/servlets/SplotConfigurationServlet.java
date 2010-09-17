package splot.services.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import splot.core.HandlerBasedServlet;
import splot.services.handlers.conf.BDDInteractiveConfigurationFilterProductsHandler;
import splot.services.handlers.conf.BDDInteractiveConfigurationMainHandler;
import splot.services.handlers.conf.BDDInteractiveConfigurationUpdatesHandler;
import splot.services.handlers.conf.InteractiveConfigurationExportConfigurationHandler;
import splot.services.handlers.conf.SATInteractiveConfigurationDetectConflictsHandler;
import splot.services.handlers.conf.SATInteractiveConfigurationMainHandler;
import splot.services.handlers.conf.SATInteractiveConfigurationUpdatesHandler;

import freemarker.template.Configuration;


/**
 * Servlet implementation class SplotMain
 */
public class SplotConfigurationServlet extends HandlerBasedServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SplotConfigurationServlet() {
        super();
    }
    
	Configuration cfg = new Configuration();
    public void createHandlers(ServletConfig config) {
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
        // cfg.setSharedVariable("wrap", new WrapDirective());
        try { 
        	// CATALOG INTERACTIVE CONFIGURATION 
        	addHandler(new BDDInteractiveConfigurationMainHandler("catalog_interactive_configuration_main", this, cfg, cfg.getTemplate("catalog_interactive_configuration_main.ftl")));
        	addHandler(new BDDInteractiveConfigurationFilterProductsHandler("catalog_interactive_configuration_filter_products", this, cfg, cfg.getTemplate("catalog_interactive_configuration_filter_products.ftl")));
        	addHandler(new BDDInteractiveConfigurationUpdatesHandler("catalog_interactive_configuration_updates", this, cfg, cfg.getTemplate("catalog_interactive_configuration_updates.ftl")));
        	
        	// SPECIALIZED CATALOG INTERACTIVE CONFIGURATION
        	addHandler(new BDDInteractiveConfigurationMainHandler("trek_catalog_interactive_configuration_main", this, cfg, cfg.getTemplate("trek_catalog_interactive_configuration_main.ftl")));

        	// FM INTERACTIVE CONFIGURATION 
        	addHandler(new SATInteractiveConfigurationMainHandler("interactive_configuration_main", this, cfg, cfg.getTemplate("interactive_configuration_main2.ftl")));
        	addHandler(new SATInteractiveConfigurationUpdatesHandler("interactive_configuration_updates", this, cfg, cfg.getTemplate("interactive_configuration_updates2.ftl")));
        	addHandler(new SATInteractiveConfigurationDetectConflictsHandler("detect_conflicts", this, cfg, cfg.getTemplate("detect_conflicts.ftl")));
        	
        	// FM & CATALOG
        	addHandler(new InteractiveConfigurationExportConfigurationHandler("export_configuration_csv", this, cfg, cfg.getTemplate("export_configuration_csv.ftl")));
        	addHandler(new InteractiveConfigurationExportConfigurationHandler("export_configuration_xml", this, cfg, cfg.getTemplate("export_configuration_xml.ftl")));
        	
//        	addHandler(new InteractiveConfigurationMainHandler("interactive_configuration_main", this, cfg, cfg.getTemplate("interactive_configuration_main.ftl")));
//        	addHandler(new InteractiveConfigurationUpdatesHandler("interactive_configuration_updates", this, cfg, cfg.getTemplate("interactive_configuration_updates.ftl")));

        	// EXPORTING INTERACTIVE CONFIGURATION TO XML INTERFACES FOR THIRD-PARTY INTEGRATION
        	addHandler(new SATInteractiveConfigurationMainHandler("splot_config_main", this, cfg, cfg.getTemplate("interactive_configuration_main2.ftl")));
        	addHandler(new SATInteractiveConfigurationUpdatesHandler("splot_config_action", this, cfg, cfg.getTemplate("interactive_configuration_updates2.ftl")));
        	addHandler(new SATInteractiveConfigurationDetectConflictsHandler("splot_config_detect_conflicts", this, cfg, cfg.getTemplate("detect_conflicts.ftl")));

        	
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }
    
}
