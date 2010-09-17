package splot.services.handlers.analyses;

import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelStatistics;
import splar.core.fm.XMLFeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ShowFeatureModelDetailsHandler extends FreeMarkerHandler {

	public ShowFeatureModelDetailsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

        try {
            String modelFileName = (String)request.getParameter("modelFile");
    		String modelLocatorString = getServlet().getInitParameter("modelsPath") + modelFileName;
    		FeatureModel model = new XMLFeatureModel(modelLocatorString, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
    		model.loadModel();
    		
			FeatureModelStatistics stats = new FeatureModelStatistics(model);
			stats.update();
    		
    		templateModel.put("modelName", model.getName());
    		templateModel.put("modelFile", modelFileName);
    		templateModel.put("modelCreationDate", model.getMetaData("date"));
    		templateModel.put("modelCreator", model.getMetaData("creator"));
    		templateModel.put("modelDescription", model.getMetaData("description"));
    		templateModel.put("modelCreatorEmail", model.getMetaData("email"));
    		templateModel.put("modelCreatorDepartment", model.getMetaData("department"));
    		templateModel.put("modelCreatorOrganization", model.getMetaData("organization"));
    		templateModel.put("modelCreatorAddress", model.getMetaData("address"));
    		templateModel.put("modelCreatorPhone", model.getMetaData("phone"));
    		templateModel.put("modelCreatorWebSite", model.getMetaData("website"));
    		templateModel.put("modelReference", model.getMetaData("reference"));
    		
    		// stats    		
    		templateModel.put("modelFeatures", stats.countFeatures());
    		templateModel.put("modelECR", String.valueOf((int)(stats.getECRepresentativeness()*100)));
	        if ( stats.getECRepresentativeness() == 0 ) {
	        	templateModel.put("modelClauseDensity", "N/A");	
	        }
	        else {
	        	DecimalFormat format = new DecimalFormat("0.0");
	        	templateModel.put("modelClauseDensity", format.format(stats.getECClauseDensity()));
	        }
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing feature model repository path":e.getMessage());
		}
	}

	private String[] extractURLs(String urls) {
//		return urls.split(";");
		return urls.split("\r\n");
	}
	
//	private List<Map<String,String>> listUserFeatureModels(String[] urlsList) {		
//		List<Map<String,String>> modelList = new LinkedList<Map<String,String>>();
//		for( String url : urlsList ) {
//			url.trim();
//			FeatureModel model = new XMLFeatureModel(url, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
//			FeatureModelStatistics stats = new FeatureModelStatistics(model);
//			model.loadModel();
//			stats.update();
//			
//	        Map<String,String> modelData = new HashMap<String, String>();
//	        modelData.put("name", model.getName());
//	        modelData.put("features", String.valueOf(stats.countFeatures()));
//	        modelData.put("ecr", String.valueOf((int)(stats.getECRepresentativeness()*100)));
//	        if ( stats.getECRepresentativeness() == 0 ) {
//	        	modelData.put("clausedensity", "N/A");	
//	        }
//	        else {
//	        	DecimalFormat format = new DecimalFormat("0.0");
//	        	modelData.put("clausedensity", format.format(stats.getECClauseDensity()));
//	        }
//	        modelData.put("file", url);
//
//	        modelList.add(modelData);
//		}
//		return modelList;
//	}
	

	
}

