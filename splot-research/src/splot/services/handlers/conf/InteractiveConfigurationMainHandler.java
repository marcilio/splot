package splot.services.handlers.conf;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationStep;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class InteractiveConfigurationMainHandler extends FreeMarkerHandler {

	protected InteractiveConfigurationElementsProducer confElementProducer = null;

	public InteractiveConfigurationMainHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	} 
	
	protected abstract ConfigurationEngine createConfigurationEngine(String modelLocatorString) throws HandlerExecutionException;
	protected abstract String getResourcePath();	
	protected abstract String getFeatureTemplateFile();
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

        try {	        	
        	
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
//	            if ( modelFileName == null || modelFileName.trim().length() == 0 ) {
//	            	throw new HandlerExecutionException("A model must be indicated/selected");
//	            }
            }
            		  
            String tmpModelPath = (String)request.getParameter("tmpModelPath");

    		// If model is not in the session, load it and add to session
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
        		confElementProducer = new InteractiveConfigurationElementsProducer(getTemplateConfiguration());
        	}
			
			// Traverses step 1 (root feature selected automatically) and identify several related parameters
        	List<Map> stepsList = new LinkedList<Map>();
        	for( ConfigurationStep step : confEngine.getSteps() ) {
        		Map stepData = new HashMap();
    			confElementProducer.produceStepTemplateElement(step, stepData);
        		stepsList.add(stepData);
        	}
        	templateModel.put("steps", stepsList);
        	
        	// save each feature ID as a freemarker map key
        	Map ids = new HashMap();

    		// Traverses features and identify several related parameters
    		List<Map> featuresList = new LinkedList<Map>();		    		
    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
    			Map featureData = new HashMap();
    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile());
    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
    			featureData.put("configurationFeatureElement", featureElementData);
    			featuresList.add(featureData);
    			ids.put(feature.getID(), featureData);
    		}	    			    		 

        	templateModel.put("ids", ids);
        	templateModel.put("features", featuresList);
    		
    		templateModel.put("modelName", confEngine.getModel().getName());
        	templateModel.put("countFeatures", confEngine.getModel().countFeatures());
        	templateModel.put("countInstantiatedFeatures", confEngine.getModel().getInstantiatedNodes().size());
			templateModel.put("done", confEngine.isDone());
			
		} catch (HandlerExecutionException e1) {
			throw e1;
		} catch (Exception e2) {
			e2.printStackTrace();
			throw new HandlerExecutionException("Problems locating/acessing the feature model repository path", e2);
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
}

