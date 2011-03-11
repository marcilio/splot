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
import javax.servlet.http.HttpSession;

import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.XMLFeatureModel;
import splar.core.fm.configuration.ConfigurationEngine;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.FeatureInViewCheckingResult;
import splot.services.extensions.fundp.utilities.Methods;
import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class FCWInteractiveConfigurationDetectConflictsHandler extends FreeMarkerHandler {

	protected FCWInteractiveConfigurationElementsProducer confElementProducer = null;

	public FCWInteractiveConfigurationDetectConflictsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}

	protected abstract String getFeatureTemplateFile();
	
	@Override
	public void setResponseParameters(HttpServletResponse response) {
		response.setContentType("text/html; charset=" + template.getEncoding());
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

		try {
			
			
			
			
        	HttpSession session = request.getSession(true);        	
        	ConfigurationEngine confEngine = (ConfigurationEngine)session.getAttribute("conf_engine");
        	String toggleFeature = (String)request.getParameter("toggleFeature"); 
        	String viewType=(String)request.getParameter("viewType");
        	String viewName=(String)request.getParameter("viewName");
			String featureModelFileName=(String)request.getParameter("selectedModels");
			String userKey=request.getParameter("userKey");
        	
    		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; //getServlet().getInitParameter("viewFilesPath");
    		String modelDir=getServlet().getInitParameter("modelsPath");

    		

    		
    		if (confEngine == null) {
    			throw new HandlerExecutionException("Configuration engine must be created first");
    		}
    		if (toggleFeature == null) {
    			throw new HandlerExecutionException("Toggle feature must be specified");
    		}
    		
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
        	
		} catch (Exception e) {
			System.err.println(e);
			throw new HandlerExecutionException("Problems computing feature conflicts", e);
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

