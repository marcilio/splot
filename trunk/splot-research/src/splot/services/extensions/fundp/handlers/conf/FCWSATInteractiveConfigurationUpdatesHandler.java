package splot.services.extensions.fundp.handlers.conf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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




public class FCWSATInteractiveConfigurationUpdatesHandler extends FreeMarkerHandler {
	protected FCWInteractiveConfigurationElementsProducer confElementProducer = null;

	public FCWSATInteractiveConfigurationUpdatesHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	@Override
	public void setResponseParameters(HttpServletResponse response) {
		response.setContentType("text/xml; charset=" + template.getEncoding());
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

		try {
			
			
           		 String viewType=(String)request.getParameter("viewType");
        		 String viewName=(String)request.getParameter("viewName");
        		 String featureModelFileName=(String)request.getParameter("selectedModels");
        		 String userKey=request.getParameter("userKey");
        		 if (viewType.compareToIgnoreCase("none")==0){
        			 viewName="none"; 
        		 }
        			 
        		

        	
        	
     		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; //getServlet().getInitParameter("viewFilesPath");
     		
        	//String viewDir=getServlet().getInitParameter("viewFilesPath");
    		String modelDir=getServlet().getInitParameter("modelsPath");

    		
			
	        String op = (String)request.getParameter("op");        
	        if ( op == null) {
	        	throw new HandlerExecutionException("Paremeter 'op' is missing");
	        }
	        
			
			templateModel.put("op", op);
        	
        	HttpSession session = request.getSession(true);        	
        	ConfigurationEngine confEngine = (ConfigurationEngine)session.getAttribute("conf_engine");	        		
    		
    		if (confEngine == null) {
    			throw new HandlerExecutionException("Configuration engine must be created first");
    		}
    		
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
        		
        	}
        	else {
        		throw new HandlerExecutionException("Invalid 'op' parameter: " + op);
        	}
        	
        	// Creates producer of HTML updates for features and configuration steps  
        	if ( confElementProducer == null ) {
        		confElementProducer = new FCWInteractiveConfigurationElementsProducer(getTemplateConfiguration());
        	}
        	
        	if ( !op.equals("undo") ) {
	        	// Traverses steps and identify several related parameters
	        	List<Map> stepsList = new LinkedList<Map>();
	        	for( ConfigurationStep step : stepsToUpdateList ) {
	        		Map stepData = new HashMap();
	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
	        		stepData.put("configurationStepElement", stepElementData);
	        		stepsList.add(stepData);
	        	}
	        	templateModel.put("steps", stepsList);
        	}
        	
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
