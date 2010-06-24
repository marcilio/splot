package splot.services.handlers.editor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sat4j.specs.ContradictionException;

import splar.core.heuristics.FTPreOrderSortedECTraversalHeuristic;
import splar.core.heuristics.VariableOrderingHeuristic;
import splar.core.heuristics.VariableOrderingHeuristicsManager;
import splar.plugins.reasoners.bdd.javabdd.FMReasoningWithBDD;
import splar.plugins.reasoners.bdd.javabdd.ReasoningWithBDD;
import splar.plugins.reasoners.sat.sat4j.FMReasoningWithSAT;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class RealTimeAnalysesHandler extends FreeMarkerHandler {

	public RealTimeAnalysesHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
        try {           	
        	String featureModelJSONString = request.getParameter("featureModelJSONString");
        	if ( featureModelJSONString != null ) {

        		JSONFeatureModel featureModel = new JSONFeatureModel(featureModelJSONString);
        		featureModel.loadModel();
        		
     	       	FMReasoningWithSAT reasoner = new FMReasoningWithSAT("MiniSAT", featureModel, 60000);
     	       	// SAT-based analysis
     	       	boolean isConsistent = true;
     	       	try {
         	       	reasoner.init();
         	       	isConsistent = reasoner.isConsistent();
				} catch (ContradictionException e) {
					isConsistent = false;
				}
     	       	
     	       	templateModel.put("isConsistent", new Boolean(isConsistent).toString());
     	       	if ( isConsistent ) {
         	       	List<String> deadFeatures = reasoner.allDeadFeatures(new HashMap<String, String>());
         	       	List<String> coreFeatures = reasoner.allCoreFeatures(new HashMap<String, String>());
         	       	templateModel.put("hasDeadFeatures", new Boolean(deadFeatures.size() > 0).toString());
	     	       	templateModel.put("deadFeatures", deadFeatures);
	     	       	templateModel.put("coreFeatures", coreFeatures);
     	       	}
     	       	else {
	     	       	templateModel.put("hasDeadFeatures", "false");
	     	       	templateModel.put("deadFeatures", new LinkedList<String>());
	     	       	templateModel.put("coreFeatures", new LinkedList<String>());
     	       	}

     	       	// BDD-based analysis
     	       	if ( isConsistent && featureModel.countFeatures() <= 500 ) {
					new FTPreOrderSortedECTraversalHeuristic("Pre-CL-MinSpan", featureModel, FTPreOrderSortedECTraversalHeuristic.FORCE_SORT);		
					VariableOrderingHeuristic heuristic = VariableOrderingHeuristicsManager.createHeuristicsManager().getHeuristic("Pre-CL-MinSpan");
    				ReasoningWithBDD bddReasoner = new FMReasoningWithBDD(featureModel, heuristic, 10000, 10000, 60000, "pre-order");
    				bddReasoner.init();
    				DecimalFormat format = new DecimalFormat("###,###,###,###,##0");
    				if( bddReasoner.countValidConfigurations() > 1000000000 ) {
    					templateModel.put("countValidConfigurations", "more than 1 billion");
    				}
    				else {
    					templateModel.put("countValidConfigurations", format.format(bddReasoner.countValidConfigurations()));
    				}
     	       	}
     	       	else {
         	       	templateModel.put("countValidConfigurations", "model is too large");
     	       	}
        	}        	
        }
        catch (Exception e) {
        	e.printStackTrace();
        	throw new HandlerExecutionException(e.getMessage());
		}
	}
}



//Reader reader = request.getReader();
//char cbuf[] = new char[100];
//StringBuffer str = new StringBuffer(100);
//int counter = reader.read(cbuf);
//while( counter != -1 ) {
//	System.out.println("entrei");
//	str.append(new String(cbuf, 0, counter));
//	counter = reader.read(cbuf);
//}