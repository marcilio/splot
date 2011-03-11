package splot.services.extensions.fundp.handlers.conf;





import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.XMLFeatureModel;
import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationStep;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;


import freemarker.template.Configuration;
import freemarker.template.Template;

import splot.services.extensions.fundp.utilities.*;




public abstract class FCWInstanceInitializationResetHandler extends FreeMarkerHandler {

	protected FCWInteractiveConfigurationElementsProducer confElementProducer = null;

	public FCWInstanceInitializationResetHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	} 
	
	protected abstract ConfigurationEngine createConfigurationEngine(String modelLocatorString) throws HandlerExecutionException;
	protected abstract String getResourcePath();	
	protected abstract String getFeatureTemplateFile();
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
				
				
        try {	
        	String requestQueryString=request.getQueryString();

        	String  newSession="false";
        	String  workflow="false";
        	String  task="false";
        	String  featureModelName="false";
        	String featureModelFileName="false";
        	String viewName="none";
        	String viewType="none";
        	String  userKey="false";
        	String  userName="guest";
        	String  userID="guest";
            String uncoveredFeatures="";
        	String  configurationFileName="";


    		String modelDir=getServlet().getInitParameter("modelsPath");
    		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; 
    		String configuredModelPath=modelDir+"/configured_models";

        	
       			/*********************************************************************
       			 * Check if the user starts a new session
       			 *********************************************************************/
    				newSession=Methods.getNewSessionFromRequest(request);
            	
           		/*********************************************************************
        		 * workflow name
        		 *********************************************************************/
    				workflow=Methods.getWorkflowNameFromRequest(request);
    				if(workflow.compareToIgnoreCase("false")==0){
    					throw new HandlerExecutionException("Problem finding the workflow name");
    				}
 			

    			/*********************************************************************
        		 * task name
        		 *********************************************************************/
    				task=Methods.getTaskNameFromRequest(request);
    				if(task.compareToIgnoreCase("false")==0){
    					throw new HandlerExecutionException("Problem finding the task name");
    				}
    				
    		

    			
           		/*********************************************************************
        		 * feature model name
        		 *********************************************************************/
    				featureModelName=Methods.getFeatureModelNameFromRequest(request);
    				if(featureModelName.compareToIgnoreCase("false")==0){
    					throw new HandlerExecutionException("Problem finding the feature model name");
    				}
    				
	    		/*********************************************************************
	    		 * Feature model file name
	    		 *********************************************************************/
    				featureModelFileName=Methods.getFeatureModelFileNameFromRequest(request, modelDir, featureModelName);
    				if(featureModelFileName.compareToIgnoreCase("false")==0){
    					throw new HandlerExecutionException("Problem finding the feature model file name");
    				}
    				
    			
    			
           		/*********************************************************************
        		 * view name
        		 *********************************************************************/
    				viewName=Methods.getViewNameFromRequest(request, viewDir, workflow, featureModelName, task);
    			

    			
    			
          		/*********************************************************************
        		 * view type
        		 *********************************************************************/
    				viewType=Methods.getViewTypeFromRequest(request, viewName);
    			
        		
           		/*********************************************************************
        		 * user key
        		 *********************************************************************/
    				userKey=Methods.getUserKeyFromRequest(request);
    				if(userKey.compareToIgnoreCase("false")==0){
    					throw new HandlerExecutionException("Problem finding the user key");
    				}
    				
           		/*********************************************************************
        		 * user Name
        		 *********************************************************************/
    				userName=Methods.getUserNameFromRequest(request);
    				if(userName.compareToIgnoreCase("false")==0){
    					throw new HandlerExecutionException("Problem finding the user name");
    				}
         		
         		
    			/*********************************************************************
        		 * user ID
        		 *********************************************************************/
    				userID=Methods.getUserIDFromRequest(request);
    				if(userID.compareToIgnoreCase("false")==0){
    					throw new HandlerExecutionException("Problem finding the user ID");
    				}
    				
    				
    			
	            
           		/*********************************************************************
        		 * uncovered features
        		 *********************************************************************/
	            if ((newSession.compareToIgnoreCase("true")==0)){
	            	uncoveredFeatures=Methods.getFeatureModelUncoveredFeaturesInAllocatedViews(featureModelName, viewDir, modelDir);
	            }
    			
	            
           		/*********************************************************************
        		 * configuration file name
        		 *********************************************************************/
	            configurationFileName=Methods.getConfiguredFileName(configuredModelPath, userKey);
    			
	            
           		/*********************************************************************
        		 * get steps
        		 *********************************************************************/
	            if ( confElementProducer == null ) {
	        		confElementProducer = new FCWInteractiveConfigurationElementsProducer(getTemplateConfiguration());
	        	}
	            
	            List<Map> featuresList = new LinkedList<Map>();	
	        	ServletConfig config = getServlet().getServletConfig();
	    	    ServletContext sc = config.getServletContext();
	    	    ConfigurationEngine confEngine=null;
	    	    
	    	    
	            if ((ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine")==null){
	            	throw new HandlerExecutionException("Problem loading configuration engine");

	            }else{

	            	confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
	            }
	            
	            
	            
	        	List<Map> stepsList = new LinkedList<Map>();
	        	for( ConfigurationStep step : confEngine.getSteps() ) {
	        		Map stepData = new HashMap();
	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
	        		stepData.put("configurationStepElement", stepElementData);
	        		stepsList.add(stepData);


	        	}
	        	templateModel.put("steps", stepsList);
	        	
	        	


				
				
				
          		/*********************************************************************
        		 * Producer of parts of the template
        		 *********************************************************************/

				FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();

				
				LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
				getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

	    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
	    			Map featureData = new HashMap();
		    		FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
	    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, userKey);
	    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
	    			featureData.put("configurationFeatureElement", featureElementData);
	    			featuresList.add(featureData);
	    			
	    		}

	        	
		
         		/*********************************************************************
        		 * provide template model
        		 *********************************************************************/
	    		templateModel.put("features", featuresList);
	    		templateModel.put("modelName", confEngine.getModel().getName());
	        	templateModel.put("countFeatures", confEngine.getModel().countFeatures());
	        	templateModel.put("countInstantiatedFeatures", confEngine.getModel().getInstantiatedNodes().size());
				templateModel.put("done", confEngine.isDone());
				templateModel.put("fm_views", Methods.getFeatureModelViews(viewDir));
				templateModel.put("viewType", viewType.toLowerCase());
				templateModel.put("workflowExistence", "true");
				templateModel.put("newSession", newSession);

				templateModel.put("viewName", viewName);
				templateModel.put("selectedModels", featureModelFileName);
				templateModel.put("workflowName", workflow);
				templateModel.put("taskName", task);
				templateModel.put("userName", userName);
				templateModel.put("userID", userID);
				templateModel.put("userKey", userKey);
				templateModel.put("uncoveredFeatures", uncoveredFeatures);
	    		
	    		
	    		
		} catch (Exception e2) {
			e2.printStackTrace();
			throw new HandlerExecutionException("Problems locating/acessing the feature model repository path", e2);
		}
	}
	
	
	
	
	
	
	protected String getFeatureParent(fm.FeatureTreeNode feature) {
		fm.FeatureTreeNode parent = (fm.FeatureTreeNode)feature.getParent();
		if ( parent == null ) {
			return "";
		}
		return parent.getID();		
	}
	
	protected String getFeatureName( fm.FeatureTreeNode feature ) {
		if ( feature instanceof fm.FeatureGroup ) {
			int min = ((fm.FeatureGroup)feature).getMin();
			int max = ((fm.FeatureGroup)feature).getMax();
			max = ( max==-1 ) ? feature.getChildCount() : max; 
			return "[" + min + ".." + max +"]";
		}
		return feature.getName();
	}
	
	protected String getFeatureType( fm.FeatureTreeNode feature ) {
		if ( feature.isRoot() ) { 
			return "root";				
		}
		else if ( feature instanceof fm.SolitaireFeature) {
			if ( ((fm.SolitaireFeature) feature).isOptional()) {
				return "optional";
			}
			else {
				return "mandatory";
			}
		}
		else if ( feature instanceof fm.FeatureGroup ){
			return "group";
		}
		else if ( feature instanceof fm.GroupedFeature ){
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



