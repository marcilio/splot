package splot.services.extensions.fundp.handlers.conf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.XMLFeatureModel;
import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationStep;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.FeatureInViewCheckingResult;
import splot.services.extensions.fundp.utilities.Methods;




public class FCWInstanceSATInteractiveConfigurationUpdatesHandler extends FreeMarkerHandler {
	protected FCWInteractiveConfigurationElementsProducer confElementProducer = null;
	   static Timer timer;

	public FCWInstanceSATInteractiveConfigurationUpdatesHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	@Override
	public void setResponseParameters(HttpServletResponse response) {
		response.setContentType("text/xml; charset=" + template.getEncoding());
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
		
		
		try {
			
     		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; //getServlet().getInitParameter("viewFilesPath");
    		String modelDir=getServlet().getInitParameter("modelsPath");

		
			/*********************************************************************
			 * get request parameter values
			 *********************************************************************/

       		 String viewType=(String)request.getParameter("viewType");
       		 if(viewType==null){
 	        	throw new HandlerExecutionException("Paremeter 'view type' is missing");
       		 }
       		 
    		 String viewName=(String)request.getParameter("viewName");
    		 if(viewName==null){
 	        	throw new HandlerExecutionException("Paremeter 'view name' is missing");

    		 }
    		 
    		 String featureModelFileName=(String)request.getParameter("selectedModels");
    		 if(featureModelFileName==null){
 	        	throw new HandlerExecutionException("Paremeter 'feature model file name' is missing");

    		 }
    		 
    		 String userKey=request.getParameter("userKey");
    		 if(userKey==null){
 	        	throw new HandlerExecutionException("Paremeter 'instance ID' is missing");

    		 }
    		 
    		 if (viewType.compareToIgnoreCase("none")==0){
    			 viewName="none"; 
    		 }
    			 
    		
    	     String op = (String)request.getParameter("operation");        
    	     if ( op == null) {
    	        throw new HandlerExecutionException("Paremeter 'operation' is missing");
    	      }
    	     
    	    
		
    	     
 	  		/*********************************************************************
	   			 * load the configuration engine 
	   		*********************************************************************/
		      
     	ServletConfig config = getServlet().getServletConfig();
 	    ServletContext sc = config.getServletContext();
 	    ConfigurationEngine confEngine=null;
 	    String lock="";
 	    
         if ((ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine")==null){

         	throw new HandlerExecutionException("Problem loading configuration engine");


         }else{        	 
	 			confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
    	        performChanges(templateModel, confEngine, request, response, viewType, viewName, featureModelFileName, userKey, op,viewDir,modelDir);
   	 			sc.setAttribute(userKey+"_conf_engine", confEngine);
   	 			sc.setAttribute(userKey+"_lock", "free");
   	 			
         	}
         
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Problem updating configuration", e);
		}
	}
	
	
	protected void performChanges(Map templateModel, ConfigurationEngine confEngine, HttpServletRequest request, HttpServletResponse response,  String viewType, String viewName, String featureModelFileName, String userKey, String op, String viewDir, String modelDir) throws HandlerExecutionException{
		
		
		try {
     		templateModel.put("op", op);
		
        	System.out.println(op);
     		
    		List<ConfigurationStep> stepsToUpdateList = null;
    		Set<FeatureTreeNode> additionalFeatures = null;
    		
    		/*********************************************************************
    		 * UNDO configuration step
    		 *********************************************************************/
        	if ( op.compareToIgnoreCase("undo") == 0 ) {
        		
        		
        		stepsToUpdateList = confEngine.undo(Integer.valueOf(request.getParameter("step")));
        		templateModel.put("countUndoSteps", stepsToUpdateList.size());
    			
        	}
    		/*********************************************************************
    		 * AUTO-COMPLETE configuration
    		 *********************************************************************/
        	else if ( op.compareToIgnoreCase("completion") == 0 ) {

        		boolean valueOrder = false;  // if false (default): false,true ; if true: true,false
        		try {
        			valueOrder = Boolean.valueOf(request.getParameter("precedence"));        			
        		}
        		catch(Exception e) {}
        		
        		stepsToUpdateList = new LinkedList<ConfigurationStep>();
        		stepsToUpdateList.add( confEngine.autoComplete(valueOrder) );
        		
        	}
    		/*********************************************************************
    		 * CONFIGURATION step
    		 *********************************************************************/
        	else if ( op.compareToIgnoreCase("conf") == 0 ) {

        		// Recover decision details: featureid and assignment value
        		String decisionVar = request.getParameter("decision").split(":")[0];
        		String decisionValue = request.getParameter("decision").split(":")[1];  // 0=false, 1=true
        		
        		if ( decisionVar == null || decisionValue == null ) {
        			throw new HandlerExecutionException("Configuration step requires specifying a feature ID and a value to be assigned");
        		}
        		
        		stepsToUpdateList = new LinkedList<ConfigurationStep>();
        		stepsToUpdateList.add( confEngine.configure(decisionVar, decisionValue.equals("1") ? 1 : 0) );
 
        	}
    		/*********************************************************************
    		 * TOGGLE feature value
    		 *********************************************************************/
        	else if ( op.compareToIgnoreCase("toggle") == 0 ) {
            	String toggleFeature = (String)request.getParameter("toggleFeature");          		
        		if (toggleFeature == null) {
        			throw new HandlerExecutionException("Toggle feature must be specified");
        		}
        		FeatureTreeNode toggleFeatureNode = confEngine.getModel().getNodeByID(toggleFeature);
        		if ( toggleFeatureNode == null ) {
        			throw new HandlerExecutionException("Toggle feature Id is invalid");
        		}
        		
        		int toggleFeatureStep = Integer.valueOf((String)toggleFeatureNode.getProperty("decisionStep"));        		
        		int countUndoSteps = confEngine.getSteps().size() - toggleFeatureStep + 1;

        		// save relevant propagated features before toggling
        		additionalFeatures = new HashSet<FeatureTreeNode>();
        		for( int i = toggleFeatureStep-1 ;  i < confEngine.getSteps().size() ; i++ ) {
        			additionalFeatures.addAll(confEngine.getSteps().get(i).getPropagations() );
        		}
        		
        		// toggle decision and grabs new steps
        		
        		stepsToUpdateList = confEngine.toggleDecision(toggleFeature);
        		templateModel.put("countUndoSteps", countUndoSteps);
        		
        	}else if ( op.compareToIgnoreCase("reload") == 0 ){
        		
        		
        	}
        	else {
        		throw new HandlerExecutionException("Invalid 'op' parameter: " + op);
        	}
        	
        	// Creates producer of HTML updates for features and configuration steps  
        	if ( confElementProducer == null ) {
        		confElementProducer = new FCWInteractiveConfigurationElementsProducer(getTemplateConfiguration());
        	}
      
        	List<Map> stepsList = new LinkedList<Map>();
        	if ( !op.equals("undo") ) {
        		if(op.compareToIgnoreCase("reload")==0){
        			for( ConfigurationStep step : confEngine.getSteps() ) {
    	        		Map stepData = new HashMap();
    	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
    	        		stepData.put("configurationStepElement", stepElementData);
    	        		stepsList.add(stepData);
    	        	}
    	        	templateModel.put("steps", stepsList);
    	        	
        		}else{
        			// Traverses steps and identify several related parameters
    	        	
    	        	for( ConfigurationStep step : stepsToUpdateList ) {
    	        		Map stepData = new HashMap();
    	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
    	        		stepData.put("configurationStepElement", stepElementData);
    	        		stepsList.add(stepData);
    	        	}
    	        	templateModel.put("steps", stepsList);
        		}
        		
        		
	        
        	}
        	
        	
        	
        	
    		
        	if(op.compareToIgnoreCase("reload")==0){
        		List<Map> featuresList = new LinkedList<Map>();	
				FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();

				
				LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
				getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

	    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
	    			Map featureData = new HashMap();
		    		FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
	    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, FCWSATInteractiveConfigurationMainHandler.featureTemplateFilename, viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, userKey);
	    		
	    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
	    			
	    			
	    			featureData.put("configurationFeatureElement", featureElementData);
	    			featuresList.add(featureData);
	    		}                                        
	            	templateModel.put("countFeatures", confEngine.getModel().countFeatures());
	            	
	            	
	            	templateModel.put("countInstantiatedFeatures", confEngine.getModel().getInstantiatedNodes().size());
	            	
	            	
	        		templateModel.put("features", featuresList);
	    			templateModel.put("done", confEngine.isDone());

	    			
	    		
        		
        	}else{
            	FeatureModel featureModel = null;
    			featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
    			featureModel.loadModel();
    	
    			LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
    			getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

            	List<Map> featuresList = new LinkedList<Map>();		    		
            	for( ConfigurationStep confStepObj : stepsToUpdateList ) {
            		Set<FeatureTreeNode> stepFeatures = new HashSet<FeatureTreeNode>();
            		stepFeatures.addAll(confStepObj.getDecisions());	        			        	
            		stepFeatures.addAll(confStepObj.getPropagations());	        			        	
    	    		for( FeatureTreeNode feature : stepFeatures ) {
    	    			if ( additionalFeatures != null && additionalFeatures.contains(feature) ) {
        					additionalFeatures.remove(feature);
    	    			}
    	    			Map featureData = new HashMap();
    	    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
    	    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, FCWSATInteractiveConfigurationMainHandler.featureTemplateFilename, viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds,userKey);
    	    			featureData.put("configurationFeatureElement", featureElementData);
    	    			featuresList.add(featureData);
    	    		}
            	}
            	
            	if ( additionalFeatures != null ) {
    	    		for( FeatureTreeNode feature : additionalFeatures ) {
    	    			Map featureData = new HashMap();
    	    			
    	    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
    	    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, FCWSATInteractiveConfigurationMainHandler.featureTemplateFilename, viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds,userKey);
    	    		    featureData.put("configurationFeatureElement", featureElementData);
    	    			featuresList.add(featureData);
    	    		}
            	}
            	
            	templateModel.put("countFeatures", confEngine.getModel().countFeatures());
            	templateModel.put("countInstantiatedFeatures", confEngine.getModel().getInstantiatedNodes().size());
            	
        		templateModel.put("features", featuresList);
    			templateModel.put("done", confEngine.isDone());

        	}
        	
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Problem updating configuration", e);
		}
		
	}
	
	protected String getFeatureParent(FeatureTreeNode feature) {
		FeatureTreeNode parent = (FeatureTreeNode)feature.getParent();
		if ( parent == null ) {
			return "";
		}
		return parent.getID();		
	}
	
	protected String getFeatureName( FeatureTreeNode feature ) {
		if ( feature instanceof FeatureGroup ) {
			int min = ((FeatureGroup)feature).getMin();
			int max = ((FeatureGroup)feature).getMax();
			max = ( max==-1 ) ? feature.getChildCount() : max; 
			return "[" + min + ".." + max +"]";
		}
		return feature.getName();
	}
	
	protected String getFeatureType( FeatureTreeNode feature ) {
		if ( feature.isRoot() ) { 
			return "root";				
		}
		else if ( feature instanceof SolitaireFeature) {
			if (((SolitaireFeature)feature).isOptional()) {
				return "optional";
			}
			else {
				return "mandatory";
			}
		}
		else if ( feature instanceof FeatureGroup ){
			return "group";
		}
		else if ( feature instanceof GroupedFeature ){
			return "grouped";
		}
		return "error";
	}	
	
	public static void getFeatureModelChilds(FeatureTreeNode featureTreeNode,LinkedList<FeatureTreeNode> featureList , String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName,
			   String visualizationType){
		
		try{
		if ( featureTreeNode.isRoot() ) {
			featureList.add(featureTreeNode);
		}
		for( int i = 0 ; i < featureTreeNode.getChildCount() ; i++ ) {
			
			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
			Methods.checkFeatureInViewStatus((FeatureTreeNode)featureTreeNode.getChildAt(i),viewDir,modelDir,featureModelFileName,featureModelName,viewName,featureInViewCheckingResult,visualizationType); 
			if (featureInViewCheckingResult.show){
				featureList.add((FeatureTreeNode)featureTreeNode.getChildAt(i));
			}
			
			getFeatureModelChilds((FeatureTreeNode) featureTreeNode.getChildAt(i),featureList,viewDir,modelDir,featureModelFileName,featureModelName,viewName,visualizationType);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}


	

