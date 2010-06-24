package splot.services.handlers.analyses;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splar.core.fm.FeatureModel;
import splar.plugins.reasoners.sat.sat4j.FMReasoningWithSAT;
import splar.plugins.reasoners.sat.sat4j.FTReasoningWithSAT;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class RunFeatureModelAnalysesHandler extends AbstractFeatureModelHandler {

	public RunFeatureModelAnalysesHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration,template);
	}
	
	@Override
	public void setResponseParameters(HttpServletResponse response) {
		response.setContentType("text/xml; charset=" + template.getEncoding());
	}
	
	public Map buildModel(Map modelMap, Map root, HttpServletRequest request) throws ServletException, IOException {

        FeatureModel model = (FeatureModel)modelMap.get("model");
        if ( model != null ) {
        
        	try {
     	       	FMReasoningWithSAT reasoner = new FMReasoningWithSAT("MiniSAT", model, 60000);
     	       	reasoner.init();
    	       
   	        	// CONSISTENCY
    	       	long runningTime = System.currentTimeMillis();
    	       	boolean isSAT = reasoner.isConsistent();
    	       	runningTime = System.currentTimeMillis()- runningTime;
    	       	root.put("consistency_result", isSAT ? "consistent" : "inconsistent");
    	       	root.put("consistency_running_time", String.valueOf(runningTime));
    	       	root.put("consistency_technique", "SAT Solver");
    	       	root.put("consistency_technology", "SAT4J version 2.0-rc8");
    	       
    	       	if ( isSAT ) {
	    	       	// DEAD FEATURES
	    	       	boolean optimizations[] = new boolean[] {true,true,true,true};
	    	       	Map<String,String> stats = new HashMap<String,String>();
	    	       	
	    	       	runningTime = System.currentTimeMillis();
	    	       	byte [][] domainTable = reasoner.computeValidDomains(new int[]{1}, optimizations, stats);
	    	       	runningTime = System.currentTimeMillis()- runningTime;
					int numDead = 0;
					int index = 0;
					Set<String> deadFeatures = new HashSet<String>();
					for( byte[] entry : domainTable ) {
						if ( entry[1] == FTReasoningWithSAT.NO ) {						
							numDead++;
							deadFeatures.add(reasoner.getVariableName(index+1));
						}
						index++;
					}
				
					// Add dead features to session
					modelMap.put("modelDeadFeatures", deadFeatures);
					
	    	       	root.put("deadfeature_result", numDead);
	    	       	root.put("deadfeature_running_time", runningTime);
	    	       	
	    	       	// COMMON FEATURES
	    	       	stats = new HashMap<String,String>();
	    	       	
	    	       	runningTime = System.currentTimeMillis();
	    	       	domainTable = reasoner.computeValidDomains(new int[]{0}, optimizations, stats);
	    	       	runningTime = System.currentTimeMillis()- runningTime;
					int numCommon = 0;
					index = 0;
					Set<String> commonFeatures = new HashSet<String>();
					for( byte[] entry : domainTable ) {
						if ( entry[0] == FTReasoningWithSAT.NO ) {
							numCommon++;
							commonFeatures.add(reasoner.getVariableName(index+1));
						}
						index++;
					}
					// Add common features to session
					modelMap.put("modelCommonFeatures", commonFeatures);
					
	    	       	root.put("commonfeature_result", numCommon);
	    	       	root.put("commonfeature_running_time", runningTime);
    	       	}
    	       	else {
    	       		root.put("deadfeature_result", "N/A");
    	       		root.put("deadfeature_running_time", "N/A");
    	       		root.put("commonfeature_result", "N/A");
    	       		root.put("commonfeature_running_time", "N/A");
    	       	}
    	       	root.put("deadfeature_technique", "SAT Solver");
    	       	root.put("deadfeature_technology", "SAT4J version 2.0-rc8");
    	       	root.put("commonfeature_technique", "SAT Solver");
    	       	root.put("commonfeature_technology", "SAT4J version 2.0-rc8");

    	       	// MACHINE SPECS
    	       	root.put("machine_os_name", System.getProperty("os.name"));
    	       	root.put("machine_os_arch", System.getProperty("os.arch"));
    	       	root.put("machine_memory", "");
    	       	root.put("machine_java_jvm_version", System.getProperty("java.vm.version"));
    	       	root.put("machine_java_jvm_name", System.getProperty("java.vm.version"));
    	       	root.put("", "");
    	       
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
	       
        }
       
        
        return root;
	}
	
}
