package splot.services.handlers.analyses;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splar.core.fm.FeatureModel;
import splar.core.heuristics.FTPreOrderSortedECTraversalHeuristic;
import splar.core.heuristics.VariableOrderingHeuristic;
import splar.core.heuristics.VariableOrderingHeuristicsManager;
import splar.plugins.reasoners.bdd.javabdd.FMReasoningWithBDD;
import splar.plugins.reasoners.bdd.javabdd.ReasoningWithBDD;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class RunFeatureModelMetricsHandler extends AbstractFeatureModelHandler {

	public RunFeatureModelMetricsHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration,template);
	}
	
	@Override
	public void setResponseParameters(HttpServletResponse response) {
		response.setContentType("text/xml; charset=" + template.getEncoding());
	}
		
	public Map buildModel(Map modelMap, Map root, HttpServletRequest request) throws ServletException, IOException {		
		
        FeatureModel model = (FeatureModel)modelMap.get("model");        

        if ( model != null ) {
        
           	// COUNT CONFIGURATION
			try {
				int countFeatures = model.countFeatures();
				if ( countFeatures <= AnalysesMainHandler.maxModelSizeForBDD ) {
    				new FTPreOrderSortedECTraversalHeuristic("Pre-CL-MinSpan", model, FTPreOrderSortedECTraversalHeuristic.FORCE_SORT);		
    				VariableOrderingHeuristic heuristic = VariableOrderingHeuristicsManager.createHeuristicsManager().getHeuristic("Pre-CL-MinSpan");
    				double countSolutions = 0;
    				ReasoningWithBDD bddReasoner = null;
    				try {
        				bddReasoner = new FMReasoningWithBDD(model, heuristic, 100000, 100000, 60000, "pre-order");
            	       	long runningTime = System.currentTimeMillis();
        				bddReasoner.init();
        				countSolutions = bddReasoner.countValidConfigurations();
            	       	runningTime = System.currentTimeMillis()- runningTime;
            	       	root.put("countconf_result", countSolutions);
            	       	root.put("countconf_running_time", runningTime);
            	       	root.put("vardegree_result", (countSolutions*100)/(Math.pow(2,countFeatures)));
            	       	root.put("vardegree_running_time", runningTime);
            	       	
            	       	root.put("bddnodes", bddReasoner.getBDD().nodeCount());
            	       	root.put("bddheuristic", "Pre-CL-MinSpan");
					} catch (Exception e) {
            	       	root.put("countconf_result", "?"); 	       	
	        	       	root.put("countconf_technique_details", "");
            	       	root.put("countconf_running_time", "+60 secs (timeout)");
            	       	root.put("vardegree_result", "");
            	       	root.put("vardegree_running_time", "+60 secs (timeout)");
					}
	    	       	root.put("bddtechnique", "Binary decision diagram (BDD)");
	    	       	root.put("bddtechnology", "JavaBDD 1.0b2");
	    	       	root.put("maxModelSizeReached", false);
				}
				else {
	    	       	root.put("maxModelSizeReached", true);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
    	}
       
        
        return root;
	}
	
}
