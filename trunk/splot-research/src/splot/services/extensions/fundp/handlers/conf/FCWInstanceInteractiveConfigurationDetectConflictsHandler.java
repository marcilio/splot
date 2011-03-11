package splot.services.extensions.fundp.handlers.conf;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelException;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.XMLFeatureModel;
import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationEngineException;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.FeatureInViewCheckingResult;
import splot.services.extensions.fundp.utilities.Methods;
import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract  class FCWInstanceInteractiveConfigurationDetectConflictsHandler extends FreeMarkerHandler {
	
	protected FCWInteractiveConfigurationElementsProducer confElementProducer = null;

	public FCWInstanceInteractiveConfigurationDetectConflictsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}

	protected abstract String getFeatureTemplateFile();
	
	@Override
	public void setResponseParameters(HttpServletResponse response) {
		response.setContentType("text/html; charset=" + template.getEncoding());
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

		try {
			
	     	ServletConfig config = getServlet().getServletConfig();
	 	    ServletContext sc = config.getServletContext();

	 	    String userKey=request.getParameter("userKey");
	 	    if(userKey==null){
	        	throw new HandlerExecutionException("Paremeter 'instance ID' is missing");

	 	    }

		 
      		 String viewType=(String)request.getParameter("viewType");
       		 if(viewType==null){
       			sc.setAttribute(userKey+"_lock", "free");
 	        	throw new HandlerExecutionException("Paremeter 'view type' is missing");
       		 }
       		 
    		 String viewName=(String)request.getParameter("viewName");
    		 if(viewName==null){
    			 sc.setAttribute(userKey+"_lock", "free");
 	        	throw new HandlerExecutionException("Paremeter 'view name' is missing");

    		 }
    		 
    		 String featureModelFileName=(String)request.getParameter("selectedModels");
    		 if(featureModelFileName==null){
    			 sc.setAttribute(userKey+"_lock", "free");
 	        	throw new HandlerExecutionException("Paremeter 'feature model file name' is missing");

    		 }
    		 
    		 
    		 if (viewType.compareToIgnoreCase("none")==0){
    			 viewName="none"; 
    		 }
    			 
    		 
       	     String op = (String)request.getParameter("operation");        
    	     if ( op == null) {
    	    	 sc.setAttribute(userKey+"_lock", "free");
    	        throw new HandlerExecutionException("Paremeter 'operation' is missing");
    	      }

    	     
        	String toggleFeature = (String)request.getParameter("toggleFeature"); 
       		if (toggleFeature == null) {
       			sc.setAttribute(userKey+"_lock", "free");
    			throw new HandlerExecutionException("Toggle feature must be specified");
    		}

        	
    		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; //getServlet().getInitParameter("viewFilesPath");
    		String modelDir=getServlet().getInitParameter("modelsPath");

			
			/*********************************************************************
			 * get request parameter values
			 *********************************************************************/

	 	    ConfigurationEngine confEngine=null;
	        if ((ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine")==null){
	        	throw new HandlerExecutionException("Problem loading configuration engine");
	        }else{
  	 			confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
   	 			performConflictDetection(confEngine, request, response, templateModel, toggleFeature, viewType, viewName, userKey, viewDir, modelDir, featureModelFileName);
   	 			sc.setAttribute(userKey+"_conf_engine", confEngine);
   	 			sc.setAttribute(userKey+"_lock", "free");

	        }

   	     
// 	  		/*********************************************************************
//	   			 * load the configuration engine 
//	   		*********************************************************************/
//		      
//	     	ServletConfig config = getServlet().getServletConfig();
//	 	    ServletContext sc = config.getServletContext();
//	 	    ConfigurationEngine confEngine=null;
//	 	    String lock="";
//
//	 	   sc.setAttribute(userKey+"_lock", "locked");
//	 	   
//	        if ((ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine")==null){
//	         	throw new HandlerExecutionException("Problem loading configuration engine");
//	        }else{
//	       	 	if((String)sc.getAttribute(userKey+"_lock")==null){
//        	 		sc.setAttribute(userKey+"_lock", "locked");
//        	 		confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
//        	 		performConflictDetection(confEngine, request, response, templateModel, toggleFeature, viewType, viewName, userKey, viewDir, modelDir, featureModelFileName);
//        	        sc.setAttribute(userKey+"_conf_engine", confEngine);
//        	        sc.setAttribute(userKey+"_lock", "free");
//        	 	}else{
//        	 		lock=(String)sc.getAttribute(userKey+"_lock");
//        	 		if(lock.compareToIgnoreCase("free")==0){
//            	 		sc.setAttribute(userKey+"_lock", "locked");
//            	 		confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
//            	 		performConflictDetection(confEngine, request, response, templateModel, toggleFeature, viewType, viewName, userKey, viewDir, modelDir, featureModelFileName);
//            	        sc.setAttribute(userKey+"_conf_engine", confEngine);
//            	        sc.setAttribute(userKey+"_lock", "free");
//        	 		}else if(lock.compareToIgnoreCase("locked")==0){
//        	 			   timer=new Timer();
//
//        	 			   class Task extends TimerTask{
//        	 				Map templateModel=null;
//        	 				ConfigurationEngine confEngine=null;
//        	 				HttpServletRequest request=null;
//        	 				HttpServletResponse response=null;
//        	 				String viewType="";
//        	 				String viewName="";
//        	 				String featureModelFileName=null;
//        	 				String userKey="";
//        	 				String viewDir="";
//        	 				String modelDir="";
//        	 				ServletContext sc=null;
//        	 				String lock="";
//        	 				String toggleFeature="";
//        	 				
//        	 				public Task(ServletContext sc, Map templateModel, ConfigurationEngine confEngine, HttpServletRequest request, HttpServletResponse response,  String viewType, String viewName, String featureModelFileName, String userKey, String viewDir, String modelDir,String toggleFeature) {
//
//        	 					this.confEngine=confEngine;
//                				this.featureModelFileName=featureModelFileName;
//                				this.modelDir=modelDir;
//                				this.request=request;
//                				this.response=response;
//                				this.sc=sc;
//                				this.templateModel=templateModel;
//                				this.userKey=userKey;
//                				this.viewDir=viewDir;
//                				this.viewName=viewName;
//                				this.viewType=viewType;
//                				this.toggleFeature=toggleFeature;
//
//                				
//    						}
//
//							public void run() {
//
//								if((String)sc.getAttribute(userKey+"_lock")==null){
//				        	 		sc.setAttribute(userKey+"_lock", "locked");
//				        	 		confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
//				        	        try {
//				            	 		performConflictDetection(confEngine, request, response, templateModel, toggleFeature, viewType, viewName, userKey, viewDir, modelDir, featureModelFileName);
//									} catch (FeatureModelException e) {
//										e.printStackTrace();
//									} catch (ConfigurationEngineException e) {
//
//										e.printStackTrace();
//									}
//				        	        sc.setAttribute(userKey+"_conf_engine", confEngine);
//				        	        sc.setAttribute(userKey+"_lock", "free");
//				        	 	}else{
//
//				        	 		lock=(String)sc.getAttribute(userKey+"_lock");
//
//
//				        	 		if(lock.compareToIgnoreCase("free")==0){
//
//				            	 		sc.setAttribute(userKey+"_lock", "locked");
//				            	 		confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
//				            	        try {
//					            	 		performConflictDetection(confEngine, request, response, templateModel, toggleFeature, viewType, viewName, userKey, viewDir, modelDir, featureModelFileName);
//										} catch (FeatureModelException e) {
//
//											e.printStackTrace();
//										} catch (ConfigurationEngineException e) {
//
//											e.printStackTrace();
//										}
//				            	        sc.setAttribute(userKey+"_conf_engine", confEngine);
//				            	        sc.setAttribute(userKey+"_lock", "free");
//				        	 		}
//				        	 	}
//								
//
//
//							}
//        	 				
//							
//        	 			}
//
//
//        	 			timer.schedule(new Task(sc, templateModel, confEngine, request, response, viewType, viewName, featureModelFileName, userKey, viewDir, modelDir, toggleFeature), 0, 4000);
//        	 			
//
//        	 			
//        	 			
//        	 		}
//        	 	}
//	        }

     		
    		
    		

        	
		} catch (Exception e) {

			System.err.println(e);
			throw new HandlerExecutionException("Problems computing feature conflicts", e);
		}
	}
	
	
	
	private void performConflictDetection(ConfigurationEngine confEngine, HttpServletRequest request, HttpServletResponse response, Map templateModel, String toggleFeature, String viewType, String viewName, String userKey, String viewDir, String modelDir, String featureModelFileName ) throws FeatureModelException, ConfigurationEngineException{
   		
		FeatureTreeNode toggleFeatureObj = confEngine.getModel().getNodeByID(toggleFeature);
   		
		templateModel.put("toggleFeatureId", toggleFeatureObj.getID());
		templateModel.put("toggleFeatureName", toggleFeatureObj.getName());
		templateModel.put("toggleFeatureCurValue", toggleFeatureObj.getValue());
		templateModel.put("toggleFeatureNewValue", 1-toggleFeatureObj.getValue());
		
		
		FeatureModel featureModel = null;
		featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
		featureModel.loadModel();
		LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
		getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

		FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();

		
		
		List<FeatureTreeNode> conflictingFeatures = confEngine.detectConflicts(toggleFeature);
		
    	if ( confElementProducer == null ) {
    		confElementProducer = new FCWInteractiveConfigurationElementsProducer(getTemplateConfiguration());
    	}

		List templateFeatureList = new LinkedList();
		for( FeatureTreeNode feature : conflictingFeatures ) {    			
			Map featureData = new HashMap();
			confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(),viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds,userKey);

			
			templateFeatureList.add(featureData);
		}  
		

		
		templateModel.put("conflicting_features", templateFeatureList);
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

