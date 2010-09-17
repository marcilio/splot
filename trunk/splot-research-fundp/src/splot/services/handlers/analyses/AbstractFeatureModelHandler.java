package splot.services.handlers.analyses;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import splar.core.fm.FeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class AbstractFeatureModelHandler extends FreeMarkerHandler {

	public AbstractFeatureModelHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public abstract Map buildModel(Map modelMap, Map templateModel, HttpServletRequest request) throws ServletException, IOException;

	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
		
		HttpSession session = request.getSession(true);
		List<Map> loadedModels = (List<Map>)session.getAttribute("loadedModels");
		
		FeatureModel model = null;
		try {
			int modelIndex = Integer.valueOf(request.getParameter("modelIndex"));
			if ( modelIndex >=0 && modelIndex < loadedModels.size() ) {
				Map modelMap = loadedModels.get(modelIndex);
				templateModel = buildModel(modelMap, templateModel, request);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
