package splot.services.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import splot.core.HandlerBasedServlet;
import splot.services.handlers.editor.ConfigureFeatureModelHandler;
import splot.services.handlers.editor.ExportFeatureModelHandler;
import splot.services.handlers.editor.FeatureModelEditorHandler;
import splot.services.handlers.editor.RealTimeAnalysesHandler;
import splot.services.handlers.editor.SaveFeatureModelToRepositoryHandler;
import freemarker.template.Configuration;


/**
 * Servlet implementation class SplotMain
 */
public class SplotEditorServlet extends HandlerBasedServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SplotEditorServlet() {
        super();
    }
    
	Configuration cfg = new Configuration();
    public void createHandlers(ServletConfig config) {
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
        try {
        	// CREATE YOUR FEATURE MODEL SERVICE
        	addHandler(new FeatureModelEditorHandler("feature_model_editor", this, cfg, cfg.getTemplate("feature_model_editor.ftl")));
        	addHandler(new RealTimeAnalysesHandler("realtime_analyses", this, cfg, cfg.getTemplate("realtime_analyses.ftl")));        		
        	addHandler(new ExportFeatureModelHandler("export_model", this, cfg, cfg.getTemplate("export_model.ftl")));
        	addHandler(new ConfigureFeatureModelHandler("configure_model", this, cfg, cfg.getTemplate("configure_model.ftl")));
        	addHandler(new SaveFeatureModelToRepositoryHandler("save_model_to_repository", this, cfg, cfg.getTemplate("save_model_to_repository.ftl")));
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }
    
}
