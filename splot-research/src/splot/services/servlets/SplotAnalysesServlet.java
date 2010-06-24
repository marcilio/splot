package splot.services.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import splot.core.HandlerBasedServlet;
import splot.services.handlers.analyses.AnalysesMainHandler;
import splot.services.handlers.analyses.DisplayRepositoryStatisticsHandler;
import splot.services.handlers.analyses.RecordFeatureModelDownloadRequestHandler;
import splot.services.handlers.analyses.RenderFeatureModelHandler;
import splot.services.handlers.analyses.RunFeatureModelAnalysesHandler;
import splot.services.handlers.analyses.RunFeatureModelMetricsHandler;
import splot.services.handlers.analyses.RunFeatureModelStatisticsHandler;
import splot.services.handlers.analyses.SelectFeatureModelHandler;
import splot.services.handlers.analyses.ShowFeatureModelDetailsHandler;
import freemarker.template.Configuration;


/**
 * Servlet implementation class SplotMain
 */
public class SplotAnalysesServlet extends HandlerBasedServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SplotAnalysesServlet() {
        super();
    }
    
	Configuration cfg = new Configuration();
    public void createHandlers(ServletConfig config) {
        cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
        try {
        	// SELECT MODEL
        	addHandler(new SelectFeatureModelHandler("select_model", this, cfg, cfg.getTemplate("select_model.ftl")));
        	addHandler(new ShowFeatureModelDetailsHandler("show_model_details", this, cfg, cfg.getTemplate("show_model_details.ftl")));
        	// BUILD ANALYSES PAGE
        	addHandler(new AnalysesMainHandler("analyses_main", this, cfg, cfg.getTemplate("analyses_main.ftl")));
			addHandler(new RunFeatureModelStatisticsHandler("run_statistics", this, cfg, cfg.getTemplate("run_statistics.ftl")));
			addHandler(new RunFeatureModelAnalysesHandler("run_analyses", this, cfg, cfg.getTemplate("run_analyses.ftl")));
			addHandler(new RunFeatureModelMetricsHandler("run_metrics", this, cfg, cfg.getTemplate("run_metrics.ftl")));
    		addHandler(new RenderFeatureModelHandler("render_model", this, cfg, cfg.getTemplate("render_model.ftl")));        		
        	addHandler(new DisplayRepositoryStatisticsHandler("display_repository_statistics", this, cfg, cfg.getTemplate("display_repository_statistics.ftl")));
        	addHandler(new RecordFeatureModelDownloadRequestHandler("record_featuremodel_download_request", this));
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }
    
}
