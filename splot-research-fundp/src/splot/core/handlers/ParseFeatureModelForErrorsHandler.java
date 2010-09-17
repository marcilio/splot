package splot.core.handlers;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelException;
import splar.core.fm.XMLFeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ParseFeatureModelForErrorsHandler extends FreeMarkerHandler {

	public ParseFeatureModelForErrorsHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration,template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

        try {
			templateModel.put("fmHasError", false);
			templateModel.put("fmErrorMessage", "No Errors");
			
	        String modelURL = (String)request.getParameter("modelURL");        
	        if ( modelURL == null || modelURL.trim().length() == 0) {
	        	throw new HandlerExecutionException("Please, enter a valid URL for your feature model");
	        }
	        modelURL = modelURL.trim();
	        try {
	            FeatureModel model = new XMLFeatureModel(modelURL, XMLFeatureModel.SET_ID_AUTOMATICALLY);
	            model.loadModel();
			} catch (FeatureModelException e1) {
				templateModel.put("fmHasError", true);
				templateModel.put("fmErrorMessage", e1.getMessage());
			}
            
		} catch (HandlerExecutionException e2) {
			throw e2;
		} catch (Exception e3) {
			e3.printStackTrace();
			throw new HandlerExecutionException("Problems checking feature model syntax", e3);
		}
	}
}
