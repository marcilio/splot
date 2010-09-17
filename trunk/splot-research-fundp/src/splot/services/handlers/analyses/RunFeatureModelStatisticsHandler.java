package splot.services.handlers.analyses;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelStatistics;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class RunFeatureModelStatisticsHandler extends AbstractFeatureModelHandler {

	public RunFeatureModelStatisticsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration,template);
	}
	
	@Override
	public void setResponseParameters(HttpServletResponse response) {
		response.setContentType("text/xml; charset=" + template.getEncoding());
	}

	public Map buildModel(Map modelMap, Map root, HttpServletRequest request) throws ServletException, IOException {
		
        FeatureModel model = (FeatureModel)modelMap.get("model");         	
        if ( model != null ) {
        
			FeatureModelStatistics stats = new FeatureModelStatistics(model);
			stats.update();
	
	        root.put("modelName", model.getName());
	        root.put("modelFileName",  modelMap.get("modelFileName"));
	        root.put("modelFeatures", String.valueOf(stats.countFeatures()));
	        root.put("modelEcr", String.valueOf((int)(stats.getECRepresentativeness()*100)));
	        if ( stats.getECRepresentativeness() == 0 ) {
	        	root.put("modelClausedensity", "N/A");	
	        }
	        else {
	        	DecimalFormat format = new DecimalFormat("0.0");
	        	root.put("modelClausedensity", format.format(stats.getECClauseDensity()));
	        }
	
	        root.put("modelTreeDepth", String.valueOf(stats.depth()));
	        root.put("modelECConstraints", String.valueOf(stats.countConstraints()));
	        root.put("modelECVariables", String.valueOf(stats.countConstraintVars()));
	        
	        root.put("modelCountMandatory", String.valueOf(stats.countMandatory()));
	        root.put("modelCountOptional", String.valueOf(stats.countOptional()));
	        
	        int groups1n = stats.countGroups1N();
	        int groups11 = stats.countGroups11();
	        root.put("modelCountGroups1n", String.valueOf(groups1n));
	        root.put("modelCountGroups11", String.valueOf(groups11));
	        root.put("modelCountGroups", String.valueOf(groups1n+groups11));
	
	        int grouped1n = stats.countGrouped1n();
	        int grouped11 = stats.countGrouped11();
	        root.put("modelCountGrouped11", String.valueOf(grouped11));
	        root.put("modelCountGrouped1n", String.valueOf(grouped1n));
	        root.put("modelCountGrouped", String.valueOf(grouped11+grouped1n));
	
	        int ftCNF = stats.countFeatureTreeCNFClauses();
	        int ecCNF = stats.countExtraConstraintCNFClauses();
	
	        root.put("modelCountFTCNFClauses", String.valueOf(ftCNF));        
	        root.put("modelCountECCNFClauses", String.valueOf(ecCNF));        
	        root.put("modelCountFMCNFClauses", String.valueOf(ecCNF+ftCNF));
        }
        
        return root;
	}		
}
