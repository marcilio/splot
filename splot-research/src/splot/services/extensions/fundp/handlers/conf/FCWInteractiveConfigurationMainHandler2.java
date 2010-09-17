package splot.services.extensions.fundp.handlers.conf;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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




public abstract class FCWInteractiveConfigurationMainHandler2 extends FreeMarkerHandler {

	protected FCWInteractiveConfigurationElementsProducer confElementProducer = null;

	public FCWInteractiveConfigurationMainHandler2(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	} 
	
	protected abstract ConfigurationEngine createConfigurationEngine(String modelLocatorString) throws HandlerExecutionException;
	protected abstract String getResourcePath();	
	protected abstract String getFeatureTemplateFile();
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
				
				
        try {	   
        	String featureModelFileName="";
        	String requestQueryString=request.getQueryString();
        	String viewType="";
        	String viewName="";
        	if (requestQueryString.indexOf("viewType")==-1){
        		viewType="none";
        		viewName="none";
        	}else{
           		 viewType=(String)request.getParameter("viewType");
        		 viewName=(String)request.getParameter("viewName");
        		 
        		 if (viewType.compareToIgnoreCase("none")==0){
        			 viewName="none";
        		 }

        	}	

        	
         		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; //getServlet().getInitParameter("viewFilesPath");
        		String modelDir=getServlet().getInitParameter("modelsPath");

  	        
	            String op = (String)request.getParameter("op");        
	            if ( op == null || op.compareToIgnoreCase("reset") != 0) {
	            	throw new HandlerExecutionException("Paremeter 'op' must be specified to 'reset'");
	            }
	
	            HttpSession session = request.getSession(true);
	        	ConfigurationEngine confEngine = (ConfigurationEngine)session.getAttribute("conf_engine");
	        	
	    		/*********************************************************************
	    		 * RESET configuration
	    		 *********************************************************************/
	            String modelFileName = (String)request.getParameter("userModels");
	            if ( modelFileName != null && modelFileName.trim().length() != 0) {
	            	if ( !modelFileName.startsWith("http://")) {
	            		throw new HandlerExecutionException("User model's URL must start with \"http://\"");
	            	}
	            }
	            else {
	        		modelFileName = (String)request.getParameter("selectedModels");
	        		featureModelFileName=(String)request.getParameter("selectedModels");
	        		if ((featureModelFileName==null) ||(featureModelFileName=="") ){
	        			featureModelFileName=(String)request.getParameter("fm_file");
	        		}
	//	            if ( modelFileName == null || modelFileName.trim().length() == 0 ) {
	//	            	throw new HandlerExecutionException("A model must be indicated/selected");
	//	            }
	            }
	            		            
	            String tmpModelPath = (String)request.getParameter("tmpModelPath");
	            
	    		// If model is not in the session, load and add it
	    		if ( confEngine == null || modelFileName != null ) {
	    			
		    		String modelLocatorString = modelFileName.trim();
		        	if ( !modelFileName.startsWith("http://") ) {
		        		modelLocatorString = getResourcePath() + (tmpModelPath == null ? "" : tmpModelPath.trim() + System.getProperty("file.separator")) + modelLocatorString;
		        	}
	
		        	// Creates configuration engine and adds to session
		        	confEngine = createConfigurationEngine(modelLocatorString);
		        	confEngine.reset();
		    		session.setAttribute("conf_engine", confEngine);
	    		}
	    		// Otherwise, reset configuration engine
	    		else {
	    			
	    			confEngine.reset();
	    		}
	
	    		
				// Producer of parts of the template
	        	if ( confElementProducer == null ) {
	        		confElementProducer = new FCWInteractiveConfigurationElementsProducer(getTemplateConfiguration());
	        	}
				
				// Traverses steps and identify several related parameters
	        	List<Map> stepsList = new LinkedList<Map>();
	        	for( ConfigurationStep step : confEngine.getSteps() ) {
	        		Map stepData = new HashMap();
	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
	        		stepData.put("configurationStepElement", stepElementData);
	        		stepsList.add(stepData);
	        	}
	        	templateModel.put("steps", stepsList);
	        
	    		// Traverses features and identify several related parameters
	        	
				FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();
				LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
				getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

	        	
	        	
	    		List<Map> featuresList = new LinkedList<Map>();	
	    		Boolean viewLoadStatus=true;
	    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
	    			Map featureData = new HashMap();
	    			
	    		
	    		
	    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
	    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds);
	    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
	    			featureData.put("configurationFeatureElement", featureElementData);
	    			featuresList.add(featureData);
	    			
	    		}	    			    		 
	    		templateModel.put("features", featuresList);
	    		templateModel.put("modelName", confEngine.getModel().getName());
	        	templateModel.put("countFeatures", confEngine.getModel().countFeatures());
	        	templateModel.put("countInstantiatedFeatures", confEngine.getModel().getInstantiatedNodes().size());
				templateModel.put("done", confEngine.isDone());
				templateModel.put("fm_views", Methods.getFeatureModelViews(viewDir));
				templateModel.put("viewType", viewType.toLowerCase());
				templateModel.put("viewName", viewName);
				templateModel.put("fm_file", featureModelFileName);
				
				
				
 			
		} catch (HandlerExecutionException e1) {
			throw e1;
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



