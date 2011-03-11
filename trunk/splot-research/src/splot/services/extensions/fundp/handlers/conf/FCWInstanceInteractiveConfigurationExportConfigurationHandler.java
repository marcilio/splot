
package splot.services.extensions.fundp.handlers.conf;

import java.util.Date;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.configuration.ConfigurationEngine;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

public class FCWInstanceInteractiveConfigurationExportConfigurationHandler extends FreeMarkerHandler{
	public FCWInstanceInteractiveConfigurationExportConfigurationHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

		
		ServletConfig config = getServlet().getServletConfig();
 	    ServletContext sc = config.getServletContext();

 	    String userKey=request.getParameter("userKey");
 	    
 	    
        try {
        	
        	ConfigurationEngine confEngine=null;
  	        if ((ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine")==null){
  	        	sc.setAttribute(userKey+"_lock", "free");
  	        	throw new HandlerExecutionException("Problem loading configuration engine");
  	        }else{
 	 			confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
 	 			FeatureModel model = confEngine.getModel();
 	    		if (confEngine == null) {
 	    			throw new HandlerExecutionException("Configuration engine must be created first");
 	    		}
 	        	
 	        	templateModel.put("modelName", model.getName());
 	        	
 	        	List features = new LinkedList();
 	        	for( FeatureTreeNode featureNode : model.getNodes() ) {
 	        		
 	        		if ( featureNode.isInstantiated() ) {
 		        		Map featureData = new HashMap();
 		    			featureData.put("id", featureNode.getID());
 		    			featureData.put("name", getFeatureName(featureNode));
 		    			featureData.put("type", getFeatureType(featureNode));
 		    			featureData.put("value", ""+featureNode.getValue());
 		    			featureData.put("decisionType", featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionType"));   // manual, propagated, auto-completion
 		    			featureData.put("decisionStep", featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionStep"));   
 		        		features.add(featureData);
 	        		}
 	        	}
 	        	sc.setAttribute(userKey+"_lock", "free");
 	        	templateModel.put("features", features);	        
  	        }
        	
        	
        	  	

        
        	
	        	
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Configuration engine must be created first", e);
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
			return "templateModel";				
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

}
