package splot.services.handlers.conf;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.configuration.ConfigurationStep;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class InteractiveConfigurationElementsProducer {

	private Template featureElementTemplate = null;
	private Template stepElementTemplate = null;
	private Configuration cfg = null;
	
	public InteractiveConfigurationElementsProducer(Configuration cfg) {
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
				stepElementTemplate = cfg.getTemplate("interactive_configuration_element_step.ftl");
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
