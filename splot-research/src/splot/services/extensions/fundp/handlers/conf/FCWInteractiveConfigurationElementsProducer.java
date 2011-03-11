package splot.services.extensions.fundp.handlers.conf;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;



import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelException;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.XMLFeatureModel;
import splar.core.fm.configuration.ConfigurationStep;
import splot.services.extensions.fundp.utilities.FeatureCardinality;
import splot.services.extensions.fundp.utilities.FeatureDecisionInfo;
import splot.services.extensions.fundp.utilities.FeatureInViewCheckingResult;
import splot.services.extensions.fundp.utilities.Methods;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FCWInteractiveConfigurationElementsProducer {

	private Template featureElementTemplate = null;
	private Template stepElementTemplate = null;
	private Configuration cfg = null;
	
	public FCWInteractiveConfigurationElementsProducer(Configuration cfg) {
		this.cfg = cfg;
	}
	
	
	public String produceFeatureElement(FeatureTreeNode feature, Map featureData, String templateFileName) {
		String output= "";		
		try {
			if ( featureElementTemplate == null ) {
				featureElementTemplate = cfg.getTemplate(templateFileName);
			}			
			featureData.putAll(produceBasicFeatureData( feature ));
			FeatureTreeNode parentNode = (FeatureTreeNode)((feature instanceof FeatureGroup) ? feature.getParent() : feature);
			List children = new ArrayList(parentNode.getChildCount());
			for( int i = 0 ; i < parentNode.getChildCount() ; i++ ) {
				FeatureTreeNode childNode = (FeatureTreeNode)parentNode.getChildAt(i);
				if ( childNode instanceof FeatureGroup ) {
					for( int j = 0 ; j < childNode.getChildCount() ; j++ ) {
						FeatureTreeNode groupedNode = (FeatureTreeNode)childNode.getChildAt(j);
						children.add(produceBasicFeatureData( groupedNode ));
					}
				}
				else {
					children.add(produceBasicFeatureData( childNode ));
				}
			}
			featureData.put("children", children);
			StringWriter outputWriter = new StringWriter();
			featureElementTemplate.process(featureData, outputWriter);
			output = outputWriter.toString();
		}
		catch( Exception e ) {
			output = e.getMessage();
		}
		return output;
	}
	
	public Map produceBasicFeatureData(FeatureTreeNode feature) {
		Map basicDataMap = new HashMap();
		basicDataMap.put("feature_id", feature.getID());
		basicDataMap.put("feature_name", getFeatureName(feature));
		basicDataMap.put("feature_type", getFeatureType(feature));
		basicDataMap.put("feature_level", feature.getLevel());
		basicDataMap.put("feature_parentid", getFeatureParent(feature));
		basicDataMap.put("feature_decision", ""+feature.getValue());
		basicDataMap.put("feature_decisionType", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionType"));   // manual, propagated, auto-completion
		basicDataMap.put("feature_decisionStep", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionStep"));   
		basicDataMap.put("feature_previousDecisionStep", feature.getValue() == -1 ? "" : ""+(Integer.valueOf((String)feature.getProperty("decisionStep"))-1));
		basicDataMap.put("feature_has_children", feature.getChildCount()>0);
		basicDataMap.put("feature_group_min", -1);
		basicDataMap.put("feature_group_max", -1 );
		if ( feature instanceof FeatureGroup ) {
			FeatureGroup group = (FeatureGroup)feature;
			int min = group.getMin();
			int max = group.getMax();
			basicDataMap.put("feature_group_min", min);
			basicDataMap.put("feature_group_max", max == -1 ? group.getChildCount() : max );
		}
		return basicDataMap;
	}
	
	
	public String produceFeatureElement(FeatureTreeNode feature, Map featureData, String templateFileName, String viewDir, String modelDir,String featureModelFileName, String featureModelName, String viewName,
			 FeatureInViewCheckingResult result,String visualizationType,LinkedList<FeatureTreeNode> fmChilds, String userKey) {
		String output= "";	
		
		try {
			if ( featureElementTemplate == null ) {
				featureElementTemplate = cfg.getTemplate(templateFileName);
			}			
			featureData.putAll(produceBasicFeatureData( feature,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType,fmChilds,userKey ));
			FeatureTreeNode parentNode = (FeatureTreeNode)((feature instanceof FeatureGroup) ? feature.getParent() : feature);
			List children = new ArrayList(parentNode.getChildCount());
			
			for( int i = 0 ; i < parentNode.getChildCount() ; i++ ) {
				FeatureTreeNode childNode = (FeatureTreeNode)parentNode.getChildAt(i);
				
				
				if ( childNode instanceof FeatureGroup ) {
					for( int j = 0 ; j < childNode.getChildCount() ; j++ ) {
						FeatureTreeNode groupedNode = (FeatureTreeNode)childNode.getChildAt(j);
						children.add(produceBasicFeatureData( groupedNode,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType,fmChilds,userKey ));
					}
				}
				else {
					children.add(produceBasicFeatureData( childNode,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType,fmChilds,userKey ));
				}
			}
			
			
			
			featureData.put("children", children);
			StringWriter outputWriter = new StringWriter();
			featureElementTemplate.process(featureData, outputWriter);
			output = outputWriter.toString();
		}
		catch( Exception e ) {
			output = e.getMessage();
		}
		return output;
	}
	

	
 
	
	public Map produceBasicFeatureData(FeatureTreeNode feature, String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName,
			  FeatureInViewCheckingResult result,String visualizationType,LinkedList<FeatureTreeNode> fmChilds, String userKey) throws FeatureModelException  {
		Map basicDataMap = new HashMap();
		
		FeatureDecisionInfo decisionResult=new FeatureDecisionInfo();
		if ((userKey!="") && (userKey.compareToIgnoreCase("false")!=0)){
			decisionResult=Methods.getFeatureDecisionInfo(modelDir, userKey, feature.getID());
			
			
		
			
		
		}
		
//		System.out.println("1:"+decisionResult.found);
//		System.out.println("2:"+feature.getID());
//		System.out.println("3:"+decisionResult.step);
//		System.out.println("4:"+decisionResult.type);
//		System.out.println("5:"+decisionResult.value);
//	

	
		
		if (visualizationType.compareToIgnoreCase("none")==0){
			basicDataMap.put("feature_id", feature.getID());
			basicDataMap.put("feature_name", getFeatureName(feature));
			basicDataMap.put("feature_type", getFeatureType(feature));
			basicDataMap.put("feature_level", getfeatureLevel(  feature , viewDir, modelDir, featureModelFileName,  featureModelName,  viewName, visualizationType) );
			basicDataMap.put("feature_parentid", getFeatureParent(feature));
			basicDataMap.put("feature_connectedid", getConnectedFeature( feature,  viewDir, modelDir, featureModelFileName,  featureModelName,  viewName,	visualizationType));
		
			
			if (decisionResult.found){
				basicDataMap.put("feature_decision", ""+decisionResult.value );
				basicDataMap.put("feature_decisionType", decisionResult.type );   // manual, propagated, auto-completion
				basicDataMap.put("feature_decisionStep", decisionResult.step);   
				basicDataMap.put("feature_previousDecisionStep", (Integer.valueOf((String)decisionResult.step)-1));

				
				
			}else{
				basicDataMap.put("feature_decision", ""+feature.getValue());
				basicDataMap.put("feature_decisionType", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionType"));   // manual, propagated, auto-completion
				basicDataMap.put("feature_decisionStep", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionStep"));   
				basicDataMap.put("feature_previousDecisionStep", feature.getValue() == -1 ? "" : ""+(Integer.valueOf((String)feature.getProperty("decisionStep"))-1));
			}
			
		
			
			
			basicDataMap.put("feature_has_children", feature.getChildCount()>0);
			basicDataMap.put("feature_group_min", -1);
			basicDataMap.put("feature_group_max", -1 );
			if ( feature instanceof FeatureGroup ) {
				FeatureGroup group = (FeatureGroup)feature;
				int min = group.getMin();
				int max = group.getMax();
				basicDataMap.put("feature_group_min", min);
				basicDataMap.put("feature_group_max", max == -1 ? group.getChildCount() : max );
			}
			
			
			
			basicDataMap.put("feature_show", "true");
			basicDataMap.put("feature_available", "true");
			basicDataMap.put("feature_error","");
			basicDataMap.put("feature_execution","true");
			
			FeatureCardinality featureCardinality=new FeatureCardinality();
			getFeatureCardinality(feature, featureCardinality);
			
			basicDataMap.put("feature_min",featureCardinality.minValue);
			basicDataMap.put("feature_min_dummy",featureCardinality.minDummyValue);
			
			basicDataMap.put("feature_max",featureCardinality.maxValue);
			basicDataMap.put("feature_max_dummy",featureCardinality.maxDummyValue);
			
			
			
			getFeatureCardinalityForVisualization(feature, viewDir, modelDir, featureModelFileName, featureModelName, viewName, visualizationType, featureCardinality,fmChilds,true);
			
			basicDataMap.put("feature_min_visualization",featureCardinality.minValue);
			basicDataMap.put("feature_min_dummy_visualization",featureCardinality.minDummyValue);
			
			basicDataMap.put("feature_max_visualization",featureCardinality.maxValue);
			basicDataMap.put("feature_max_dummy_visualization",featureCardinality.maxDummyValue);
		
			
			
		}else if (visualizationType.compareToIgnoreCase("greyed")==0){
			basicDataMap.put("feature_id", feature.getID());
			basicDataMap.put("feature_name", getFeatureName(feature));
			basicDataMap.put("feature_type", getFeatureType(feature));
			basicDataMap.put("feature_level", getfeatureLevel(  feature , viewDir, modelDir, featureModelFileName,  featureModelName,  viewName, visualizationType) );
			basicDataMap.put("feature_parentid", getFeatureParent(feature));
			basicDataMap.put("feature_connectedid", getConnectedFeature( feature,  viewDir, modelDir, featureModelFileName,  featureModelName,  viewName,	visualizationType));
		
			if (decisionResult.found){
				basicDataMap.put("feature_decision", ""+decisionResult.value );
				basicDataMap.put("feature_decisionType", decisionResult.type );   // manual, propagated, auto-completion
				basicDataMap.put("feature_decisionStep", decisionResult.step);   
				basicDataMap.put("feature_previousDecisionStep", (Integer.valueOf((String)decisionResult.step)-1));

				
			}else{
				basicDataMap.put("feature_decision", ""+feature.getValue());
				basicDataMap.put("feature_decisionType", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionType"));   // manual, propagated, auto-completion
				basicDataMap.put("feature_decisionStep", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionStep"));   
				basicDataMap.put("feature_previousDecisionStep", feature.getValue() == -1 ? "" : ""+(Integer.valueOf((String)feature.getProperty("decisionStep"))-1));
			}
						
			
			
			basicDataMap.put("feature_has_children", feature.getChildCount()>0);
			basicDataMap.put("feature_group_min", -1);
			basicDataMap.put("feature_group_max", -1 );
			if ( feature instanceof FeatureGroup ) {
				FeatureGroup group = (FeatureGroup)feature;
				int min = group.getMin();
				int max = group.getMax();
				basicDataMap.put("feature_group_min", min);
				basicDataMap.put("feature_group_max", max == -1 ? group.getChildCount() : max );
			}
			Methods.checkFeatureInViewStatus(feature,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType);  
			basicDataMap.put("feature_show", new Boolean(result.show).toString());
			basicDataMap.put("feature_available", new Boolean(result.available).toString());
			basicDataMap.put("feature_error", result.errorList);
			basicDataMap.put("feature_execution", new Boolean(result.executionStatus));

			FeatureCardinality featureCardinality=new FeatureCardinality();
			getFeatureCardinality(feature, featureCardinality);
			
			basicDataMap.put("feature_min",featureCardinality.minValue);
			basicDataMap.put("feature_min_dummy",featureCardinality.minDummyValue);
			
			basicDataMap.put("feature_max",featureCardinality.maxValue);
			basicDataMap.put("feature_max_dummy",featureCardinality.maxDummyValue);
			
			
			
			getFeatureCardinalityForVisualization(feature, viewDir, modelDir, featureModelFileName, featureModelName, viewName, visualizationType, featureCardinality,fmChilds,result.show);
			
			basicDataMap.put("feature_min_visualization",featureCardinality.minValue);
			basicDataMap.put("feature_min_dummy_visualization",featureCardinality.minDummyValue);
			
			basicDataMap.put("feature_max_visualization",featureCardinality.maxValue);
			basicDataMap.put("feature_max_dummy_visualization",featureCardinality.maxDummyValue);

			
			}else if (visualizationType.compareToIgnoreCase("pruned")==0){
			basicDataMap.put("feature_id", feature.getID());
			basicDataMap.put("feature_type", getFeatureType(feature));
			basicDataMap.put("feature_level", getfeatureLevel(  feature , viewDir, modelDir, featureModelFileName,  featureModelName,  viewName, visualizationType) );
			basicDataMap.put("feature_parentid", getFeatureParent(feature));
			basicDataMap.put("feature_connectedid", getConnectedFeature( feature,  viewDir, modelDir, featureModelFileName,  featureModelName,  viewName,	visualizationType));
			
			
			if (decisionResult.found){
				basicDataMap.put("feature_decision", ""+decisionResult.value );
				basicDataMap.put("feature_decisionType", decisionResult.type );   // manual, propagated, auto-completion
				basicDataMap.put("feature_decisionStep", decisionResult.step);   
				basicDataMap.put("feature_previousDecisionStep", (Integer.valueOf((String)decisionResult.step)-1));

				
			}else{
				basicDataMap.put("feature_decision", ""+feature.getValue());
				basicDataMap.put("feature_decisionType", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionType"));   // manual, propagated, auto-completion
				basicDataMap.put("feature_decisionStep", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionStep"));   
				basicDataMap.put("feature_previousDecisionStep", feature.getValue() == -1 ? "" : ""+(Integer.valueOf((String)feature.getProperty("decisionStep"))-1));
			}
			
			
			
			basicDataMap.put("feature_has_children", feature.getChildCount()>0);

			Methods.checkFeatureInViewStatus(feature,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType);  
			basicDataMap.put("feature_show", new Boolean(result.show).toString());
			basicDataMap.put("feature_available", new Boolean(result.available).toString());
			basicDataMap.put("feature_error", result.errorList);
			basicDataMap.put("feature_execution", new Boolean(result.executionStatus));
			
			FeatureCardinality featureCardinality=new FeatureCardinality();
			getFeatureCardinality(feature, featureCardinality);
			
			basicDataMap.put("feature_min",featureCardinality.minValue);
			basicDataMap.put("feature_min_dummy",featureCardinality.minDummyValue);
			
			basicDataMap.put("feature_max",featureCardinality.maxValue);
			basicDataMap.put("feature_max_dummy",featureCardinality.maxDummyValue);
			
			
			
			getFeatureCardinalityForVisualization(feature, viewDir, modelDir, featureModelFileName, featureModelName, viewName, visualizationType, featureCardinality,fmChilds,result.show);
			
			basicDataMap.put("feature_min_visualization",featureCardinality.minValue);
			basicDataMap.put("feature_min_dummy_visualization",featureCardinality.minDummyValue);
			
			basicDataMap.put("feature_max_visualization",featureCardinality.maxValue);
			basicDataMap.put("feature_max_dummy_visualization",featureCardinality.maxDummyValue);
			
			basicDataMap.put("feature_group_min", -1);
			basicDataMap.put("feature_group_max", -1 );
		
			if ( feature instanceof FeatureGroup ) {
				FeatureGroup group = (FeatureGroup)feature;
				basicDataMap.put("feature_group_min", featureCardinality.minValue);
				basicDataMap.put("feature_group_max", featureCardinality.maxValue);
			}

			basicDataMap.put("feature_name", getFeatureName(feature,featureCardinality.minValue,featureCardinality.maxValue));
			
			
			
			


			
			}else if (visualizationType.compareToIgnoreCase("collapsed")==0){
			basicDataMap.put("feature_id", feature.getID());
			basicDataMap.put("feature_name", getFeatureName(feature));
			basicDataMap.put("feature_type", getFeatureType(feature));
			basicDataMap.put("feature_level", getfeatureLevel(  feature , viewDir, modelDir, featureModelFileName,  featureModelName,  viewName, visualizationType) );
			basicDataMap.put("feature_parentid", getFeatureParent(feature));
			basicDataMap.put("feature_connectedid", getConnectedFeature( feature,  viewDir, modelDir, featureModelFileName,  featureModelName,  viewName,	visualizationType));
		
			
			if (decisionResult.found){
				basicDataMap.put("feature_decision", ""+decisionResult.value );
				basicDataMap.put("feature_decisionType", decisionResult.type );   // manual, propagated, auto-completion
				basicDataMap.put("feature_decisionStep", decisionResult.step);   
				basicDataMap.put("feature_previousDecisionStep", (Integer.valueOf((String)decisionResult.step)-1));

				
			}else{
				basicDataMap.put("feature_decision", ""+feature.getValue());
				basicDataMap.put("feature_decisionType", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionType"));   // manual, propagated, auto-completion
				basicDataMap.put("feature_decisionStep", feature.getValue() == -1 ? "" : (String)feature.getProperty("decisionStep"));   
				basicDataMap.put("feature_previousDecisionStep", feature.getValue() == -1 ? "" : ""+(Integer.valueOf((String)feature.getProperty("decisionStep"))-1));
			}
						
			
			
			
			
			
			
			basicDataMap.put("feature_has_children", feature.getChildCount()>0);
			
			
			
		
			Methods.checkFeatureInViewStatus(feature,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType);  
			basicDataMap.put("feature_show", new Boolean(result.show).toString());
			basicDataMap.put("feature_available", new Boolean(result.available).toString());
			basicDataMap.put("feature_error", result.errorList);
			basicDataMap.put("feature_execution", new Boolean(result.executionStatus));
			
			
		
			
			if (result.show){
				FeatureCardinality featureCardinality=new FeatureCardinality();
				getFeatureCardinality(feature, featureCardinality);
				
				basicDataMap.put("feature_min",featureCardinality.minValue);
				basicDataMap.put("feature_min_dummy",featureCardinality.minDummyValue);
				
				basicDataMap.put("feature_max",featureCardinality.maxValue);
				basicDataMap.put("feature_max_dummy",featureCardinality.maxDummyValue);
				
				
				
				getFeatureCardinalityForVisualization(feature, viewDir, modelDir, featureModelFileName, featureModelName, viewName, visualizationType, featureCardinality,fmChilds,result.show);
				
				basicDataMap.put("feature_min_visualization",featureCardinality.minValue);
				basicDataMap.put("feature_min_dummy_visualization",featureCardinality.minDummyValue);
				
				basicDataMap.put("feature_max_visualization",featureCardinality.maxValue);
				basicDataMap.put("feature_max_dummy_visualization",featureCardinality.maxDummyValue);
				
				basicDataMap.put("feature_group_min", -1);
				basicDataMap.put("feature_group_max", -1 );
			
				if ( feature instanceof FeatureGroup ) {
					FeatureGroup group = (FeatureGroup)feature;
					basicDataMap.put("feature_group_min", featureCardinality.minValue);
					basicDataMap.put("feature_group_max", featureCardinality.maxValue);
				}

				basicDataMap.put("feature_name", getFeatureName(feature,featureCardinality.minValue,featureCardinality.maxValue));
				
				
				
				
			}else{
				basicDataMap.put("feature_min",-1);
				basicDataMap.put("feature_min_dummy",-1);
				basicDataMap.put("feature_max",-1);
				basicDataMap.put("feature_max_dummy",-1);
				basicDataMap.put("feature_min_visualization",-1);
				basicDataMap.put("feature_min_dummy_visualization",-1);
				basicDataMap.put("feature_max_visualization",-1);
				basicDataMap.put("feature_max_dummy_visualization",-1);
				
				basicDataMap.put("feature_group_min", -1);
				basicDataMap.put("feature_group_max", -1 );
				if ( feature instanceof FeatureGroup ) {
					FeatureGroup group = (FeatureGroup)feature;
					int min = group.getMin();
					int max = group.getMax();
					basicDataMap.put("feature_group_min", min);
					basicDataMap.put("feature_group_max", max == -1 ? group.getChildCount() : max );
				}

				
			

			}
				


			}
		
		
		return basicDataMap;
		

		
	}
	
	
	
	public void produceStepTemplateElement(ConfigurationStep step, Map stepData) {
		String output= "";		
		try {
			stepData.put("step_id", step.getId());
			List stepManualDecisionsList = new LinkedList();
			if ( step.getDecisions().isEmpty() ) {
				Map tempMap = new HashMap();
				tempMap.put("featureName", "auto-completion");
				tempMap.put("featureId", "auto-completion");
				tempMap.put("featureValue", "");
				stepManualDecisionsList.add(tempMap);
			}
			else {
				for ( FeatureTreeNode manualDecisionFeature : step.getDecisions() ) {
					Map tempMap = new HashMap();
					tempMap.put("featureName", manualDecisionFeature.getName());
					tempMap.put("featureId", manualDecisionFeature.getID());
					tempMap.put("featureValue", manualDecisionFeature.getValue());
					stepManualDecisionsList.add(tempMap);
				}
			}
			stepData.put("step_manualDecisions", stepManualDecisionsList);
			stepData.put("step_countDecisions", step.countDecisions());
			stepData.put("step_countPropagations", step.countPropagations());
			// Step attributes
			stepData.putAll(step.getAttributesMap());
		}
		catch( Exception e ) {
		}
	}

	public String produceStepElement(ConfigurationStep step, Map stepData) {
		String output= "";		
		try {
			if ( stepElementTemplate == null ) {
				stepElementTemplate = cfg.getTemplate("fcw_interactive_configuration_element_step.ftl");
			}			
			stepData.put("step_id", step.getId());
			List stepManualDecisionsList = new LinkedList();
			if ( step.getDecisions().isEmpty() ) {
				Map tempMap = new HashMap();
				tempMap.put("featureName", "auto-completion");
				tempMap.put("featureId", "auto-completion");
				tempMap.put("featureValue", "");
				stepManualDecisionsList.add(tempMap);
			}
			else {
				for ( FeatureTreeNode manualDecisionFeature : step.getDecisions() ) {
					Map tempMap = new HashMap();
					tempMap.put("featureName", manualDecisionFeature.getName());
					tempMap.put("featureId", manualDecisionFeature.getID());
					tempMap.put("featureValue", manualDecisionFeature.getValue());
					stepManualDecisionsList.add(tempMap);
				}
			}
			stepData.put("step_manualDecisions", stepManualDecisionsList);
			stepData.put("step_countDecisions", step.countDecisions());
			stepData.put("step_countPropagations", step.countPropagations());
			// Step attributes
			stepData.putAll(step.getAttributesMap());
			StringWriter outputWriter = new StringWriter();
			stepElementTemplate.process(stepData, outputWriter);
			output = outputWriter.toString();
		}
		catch( Exception e ) {
			output = e.getMessage();
		}
		return output;
	}
	
	public String produceStepElement(String stepID,ConfigurationStep step, Map stepData) {
		String output= "";		
		try {
			if ( stepElementTemplate == null ) {
				stepElementTemplate = cfg.getTemplate("fcw_interactive_configuration_element_step.ftl");
			}			
			stepData.put("step_id", stepID);
			List stepManualDecisionsList = new LinkedList();
			if ( step.getDecisions().isEmpty() ) {
				Map tempMap = new HashMap();
				tempMap.put("featureName", "auto-completion");
				tempMap.put("featureId", "auto-completion");
				tempMap.put("featureValue", "");
				stepManualDecisionsList.add(tempMap);
			}
			else {
				for ( FeatureTreeNode manualDecisionFeature : step.getDecisions() ) {
					Map tempMap = new HashMap();
					tempMap.put("featureName", manualDecisionFeature.getName());
					tempMap.put("featureId", manualDecisionFeature.getID());
					tempMap.put("featureValue", manualDecisionFeature.getValue());
					stepManualDecisionsList.add(tempMap);
				}
			}
			stepData.put("step_manualDecisions", stepManualDecisionsList);
			stepData.put("step_countDecisions", step.countDecisions());
			stepData.put("step_countPropagations", step.countPropagations());
			// Step attributes
			stepData.putAll(step.getAttributesMap());
			StringWriter outputWriter = new StringWriter();
			stepElementTemplate.process(stepData, outputWriter);
			output = outputWriter.toString();
		}
		catch( Exception e ) {
			output = e.getMessage();
		}
		return output;
	}
	
	
	protected String getFeatureParent(FeatureTreeNode feature) {
		FeatureTreeNode parent = (FeatureTreeNode)feature.getParent();
		if ( parent == null ) {
			return "";
		}
		return parent.getID();		
	}
	
	
		protected String getConnectedFeature(FeatureTreeNode feature, String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName,
			   String visualizationType) {	
			
		
			
		FeatureTreeNode parent = (FeatureTreeNode)feature.getParent();
		
		String retValue="";
		if ( parent == null ) {
			return "";
		}
		
		while (parent!=null){
			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
			Methods.checkFeatureInViewStatus(parent,viewDir,modelDir,featureModelFileName,featureModelName,viewName,featureInViewCheckingResult,visualizationType);  
			
			if (featureInViewCheckingResult.show){
				retValue=parent.getID();
				return parent.getID();
			}else{
				parent=(FeatureTreeNode)parent.getParent();
			}
		}
		
		
		return retValue;
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
	
	
	protected String getFeatureName( FeatureTreeNode feature, int min , int max ) {
		if ( feature instanceof FeatureGroup ) {
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
	
	
	protected int getfeatureLevel( FeatureTreeNode feature ,String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName,
			   String visualizationType) {
		int retVal=0;
		FeatureTreeNode connectedFeature=getConnectedFeatureNode( feature,  viewDir, modelDir, featureModelFileName,  featureModelName,  viewName,visualizationType);
		if (connectedFeature==null){
			retVal=0;
		}else{
			retVal=connectedFeature.getLevel()+1;
		}
		
		
		return retVal;
	}	
	
	

	protected FeatureTreeNode getConnectedFeatureNode(FeatureTreeNode feature, String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName,
		   String visualizationType) {	
		
		FeatureTreeNode retValue=null;	
		
	FeatureTreeNode parent = (FeatureTreeNode)feature.getParent();
	

	if ( parent == null ) {
		return null;
	}
	while (parent!=null){
		FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
		Methods.checkFeatureInViewStatus(parent,viewDir,modelDir,featureModelFileName,featureModelName,viewName,featureInViewCheckingResult,visualizationType);  
		
		if (featureInViewCheckingResult.show){
			retValue=parent;
			return parent;
		}else{
			parent=(FeatureTreeNode)parent.getParent();
		}
	}
	
	
	return retValue;
	}

	
	protected void  getFeatureCardinalityForVisualization (FeatureTreeNode feature, String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName,
			   String visualizationType, FeatureCardinality featureCardinality ,LinkedList<FeatureTreeNode> fmChilds, boolean viewValue) throws FeatureModelException {	
		
		
		if ((visualizationType.compareToIgnoreCase("none")==0) || (visualizationType.compareToIgnoreCase("greyed")==0)){
			
			getFeatureCardinality (feature,featureCardinality);
			
		}else if (visualizationType.compareToIgnoreCase("pruned")==0) {
		//	FeatureInViewCheckingResult result=new FeatureInViewCheckingResult();
		//	Methods.checkFeatureInViewStatus(feature,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType); 
			
			if (!viewValue){
				featureCardinality.minValue=-1;
				featureCardinality.minDummyValue=-1;
				featureCardinality.maxValue=-1;
				featureCardinality.maxDummyValue=-1;
				
			}else{
				FeatureCardinality fCardinality=new FeatureCardinality();
				getFeatureCardinality (feature,fCardinality);
				
			
				
				if (feature.isLeaf()){
					featureCardinality=fCardinality;
				}else{
					
					int  childNotInView=0;
					for( int i = 0 ; i < feature.getChildCount() ; i++ ) {
						FeatureTreeNode childNode = (FeatureTreeNode)feature.getChildAt(i);
						FeatureInViewCheckingResult result=new FeatureInViewCheckingResult();
						Methods.checkFeatureInViewStatus(childNode,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType); 
						if (!result.show){
							childNotInView++;
						}
						
					}
					
					
					
					if ((fCardinality.minValue-childNotInView)>0){
						featureCardinality.minValue=fCardinality.minValue-childNotInView;
						featureCardinality.minDummyValue=-1;
					}else{
						featureCardinality.minValue=1;
						featureCardinality.minDummyValue=-1;

					}
					
					
					if (fCardinality.maxValue<(feature.getChildCount()-childNotInView)){
						featureCardinality.maxValue=fCardinality.maxValue;
						featureCardinality.maxDummyValue=-1;

					}else{
						featureCardinality.maxValue=feature.getChildCount()-childNotInView;
						featureCardinality.maxDummyValue=-1;
	
					}
					
				}
				
				
			}

		}else if (visualizationType.compareToIgnoreCase("collapsed")==0) {
	
			/*	FeatureModel featureModel = null;
		*/
			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
			//Methods.checkFeatureInViewStatus(feature,viewDir,modelDir,featureModelFileName,featureModelName,viewName,featureInViewCheckingResult,visualizationType); 

			if (viewValue){
			
				/*try	{
					featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
					featureModel.loadModel();
				}catch (Exception e) {
					e.printStackTrace();
				}
				*/
				int childCounts=0;
				
				if (feature.isRoot()){
					
				//	LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
				//	getFeatureModelChilds(featureModel.getRoot(), fmChilds);
					for (int i=0;i<fmChilds.size();i++){
						FeatureTreeNode  child=(FeatureTreeNode) fmChilds.get(i);
						FeatureTreeNode  parentNode=getConnectedFeatureNode(child,viewDir,modelDir,featureModelFileName,featureModelName, viewName,visualizationType);
						if ((parentNode!=null)  && (parentNode.getID().compareToIgnoreCase(feature.getID())==0)){
						//	FeatureInViewCheckingResult result=new FeatureInViewCheckingResult();
							//Methods.checkFeatureInViewStatus(child,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType); 
						//	if (result.show){
								
								childCounts++;
							//}
									
						}
					}
					featureCardinality.minValue=childCounts;
					featureCardinality.minDummyValue=-1;
					featureCardinality.maxValue=childCounts;
					featureCardinality.maxDummyValue=-1;
					return;

				}//if (feature.isRoot())
				
				if (feature instanceof FeatureGroup){
					if (((FeatureGroup) feature).getMax()==1){
						featureCardinality.minValue=1;
						featureCardinality.minDummyValue=-1;
						
						featureCardinality.maxValue=1;
						featureCardinality.maxDummyValue=-1;

					}else{
						//LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
						//getFeatureModelChilds(featureModel.getRoot(), fmChilds);
						childCounts=0;
						for (int i=0;i<fmChilds.size();i++){
							FeatureTreeNode  child=(FeatureTreeNode) fmChilds.get(i);
							FeatureTreeNode  parentNode=getConnectedFeatureNode(child,viewDir,modelDir,featureModelFileName,featureModelName, viewName,visualizationType);
							if ((parentNode!=null)  && (parentNode.getID().compareToIgnoreCase(feature.getID())==0)){
								//FeatureInViewCheckingResult result=new FeatureInViewCheckingResult();
							//	Methods.checkFeatureInViewStatus(child,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType); 
							//	if (result.show){
									
									childCounts++;
							//	}
										
							}
						}
						
						featureCardinality.minValue=1;
						featureCardinality.minDummyValue=-1;
						featureCardinality.maxValue=childCounts;
						featureCardinality.maxDummyValue=-1;
					}
					
					return;
				}//if (feature instanceof FeatureGroup)
				
				if (feature instanceof SolitaireFeature){
					if (((SolitaireFeature)feature).isOptional()) {
						if (feature.isLeaf()){
							featureCardinality.minValue=0;
							featureCardinality.minDummyValue=1;
							
							featureCardinality.maxValue=1;
							featureCardinality.maxDummyValue=1;
							
							
						}else{
							featureCardinality.minValue=0;
							featureCardinality.minDummyValue=1;
							
							featureCardinality.maxValue=1;
							featureCardinality.maxDummyValue=1;					
						}
					}else{//mandetory
						if (feature.isLeaf()){
							featureCardinality.minValue=0;
							featureCardinality.minDummyValue=-1;

							featureCardinality.maxValue=0;
							featureCardinality.maxDummyValue=-1;
						}else{
							//LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
							//getFeatureModelChilds(featureModel.getRoot(), fmChilds);
							childCounts=0;
							for (int i=0;i<fmChilds.size();i++){
								FeatureTreeNode  child=(FeatureTreeNode) fmChilds.get(i);
								FeatureTreeNode  parentNode=getConnectedFeatureNode(child,viewDir,modelDir,featureModelFileName,featureModelName, viewName,visualizationType);
								if ((parentNode!=null)  && (parentNode.getID().compareToIgnoreCase(feature.getID())==0)){
								//	FeatureInViewCheckingResult result=new FeatureInViewCheckingResult();
								//	Methods.checkFeatureInViewStatus(child,viewDir,modelDir,featureModelFileName,featureModelName,viewName,result,visualizationType);
									//	if (result.show){
												childCounts++;
									//	}

									
								}

							}
							
							featureCardinality.minValue=childCounts;
							featureCardinality.minDummyValue=-1;
							featureCardinality.maxValue=childCounts;
							featureCardinality.maxDummyValue=-1;
						}
						
					}
					
				}//if (feature instanceof SolitaireFeature)
			}else{//if (featureInViewCheckingResult.show)
				featureCardinality.minValue=-1;
				featureCardinality.minDummyValue=-1;
				
				featureCardinality.maxValue=-1;
				featureCardinality.maxDummyValue=-1;
			}

		


				
		}//else if (visualizationType.compareToIgnoreCase("collapsed")==0)	
			
				
		
	
		
	}


	
	private void getFeatureCardinality (FeatureTreeNode feature,FeatureCardinality featureCardinality){
		
		
		if (feature instanceof FeatureGroup){
			FeatureGroup group = (FeatureGroup)feature;
			featureCardinality.minValue=group.getMin();
			featureCardinality.minDummyValue=-1;
			
			featureCardinality.maxValue=group.getMax()==-1 ? group.getChildCount() : group.getMax();
			featureCardinality.maxDummyValue=-1;
			
			
			
		}else{
			if ( feature instanceof SolitaireFeature) {
				if (((SolitaireFeature)feature).isOptional()) {
					if (feature.isLeaf()){
						featureCardinality.minValue=0;
						featureCardinality.minDummyValue=1;
						
						featureCardinality.maxValue=1;
						featureCardinality.maxDummyValue=1;
						
						
					}else{
						featureCardinality.minValue=0;
						featureCardinality.minDummyValue=1;
						
						featureCardinality.maxValue=1;
						featureCardinality.maxDummyValue=1;
				
					}
				}else{// feature is mandetory
					if (feature.isLeaf()){
						featureCardinality.minValue=0;
						featureCardinality.minDummyValue=-1;

						featureCardinality.maxValue=0;
						featureCardinality.maxDummyValue=-1;
						
						
					}else{
						if (feature.getChildCount()==1){
							if (feature instanceof FeatureGroup){
								FeatureGroup group = (FeatureGroup)feature;
								featureCardinality.minValue=group.getMin();
								featureCardinality.minDummyValue=-1;
								
								featureCardinality.maxValue=group.getMax()==-1 ? group.getChildCount() : group.getMax();
								featureCardinality.maxDummyValue=-1;

								
								
							}else{
								featureCardinality.minValue=feature.getChildCount();
								featureCardinality.minDummyValue=-1;
								
								featureCardinality.maxValue=feature.getChildCount(); 
								featureCardinality.maxDummyValue=-1;
				
								
								
							}
						}else{
							featureCardinality.minValue=feature.getChildCount();
							featureCardinality.minDummyValue=-1;
							
							featureCardinality.maxValue=feature.getChildCount(); 
							featureCardinality.maxDummyValue=-1;
				
							
							
						}
					}
					
				}
				
			}else{
				featureCardinality.minValue=feature.getChildCount();
				featureCardinality.minDummyValue=-1;
				
				featureCardinality.maxValue=feature.getChildCount(); 
				featureCardinality.maxDummyValue=-1;

			}
			
			
			
			
			
		}
		
	
	}
	
	

	
		

	
}