package splot.services.handlers.analyses;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import splar.core.constraints.PropositionalFormula;
import splar.core.fm.FTTraversalNodeSelector;
import splar.core.fm.FTTraversals;
import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class RenderFeatureModelHandler extends AbstractFeatureModelHandler {

	public RenderFeatureModelHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration,template);
	}
	
	public Map buildModel(Map modelMap, Map root, HttpServletRequest request) throws ServletException, IOException {
		
        FeatureModel model = (FeatureModel)modelMap.get("model");
        
        String highlight = request.getParameter("highlight");
        Set<String> highlightFeatures = null;
        
        if ( model != null ) {
    
        	try {
            	if ( highlight != null ) {
            		if ( highlight.compareToIgnoreCase("dead") == 0 ) {
            			highlightFeatures = (Set<String>)modelMap.get("modelDeadFeatures");
            		}
            		else if ( highlight.compareToIgnoreCase("common") == 0 ) {
            			highlightFeatures = (Set<String>)modelMap.get("modelCommonFeatures");
            		}
            	}
			} catch (Exception e) {
				e.printStackTrace();		
			}
			
			List<FeatureTreeNode> nodes = FTTraversals.dfs(model.getRoot(), new FTTraversalNodeSelector() {
				@Override
				public boolean select(FeatureTreeNode node) {
					return true;
				}			
			});
	
			List<Map<String,String>> features = new LinkedList<Map<String,String>>();
			
			for( FeatureTreeNode node : nodes ) {
				Map<String,String> nodeData = new HashMap<String, String>();
				nodeData.put("id", node.getID());
				nodeData.put("name", node.getName());
				int level = model.getLevel(node);
				int shift = 22;
				nodeData.put("shift", String.valueOf(level*shift));
				if ( node.isRoot() ) { 
					nodeData.put("image", "root");				
				}
				else if ( node instanceof SolitaireFeature) {
					if (((SolitaireFeature)node).isOptional()) {
						nodeData.put("image", "optional");
					}
					else {
						nodeData.put("image", "mandatory");
					}
				}
				else if ( node instanceof FeatureGroup ){
					nodeData.put("image", "group");
					if ( ((FeatureGroup)node).getMax() == 1 ) {
						nodeData.put("name", "[1,1]");
					}
					else { 
						nodeData.put("name", "[1,*]");
					}
				}
				else if ( node instanceof GroupedFeature ){
					nodeData.put("shift", String.valueOf((level+1)*shift));
					nodeData.put("image", "grouped");
				}
				
				nodeData.put("highlightType", "normal");
				if ( highlightFeatures != null) {
					if ( highlightFeatures.contains(node.getID()) ) {
						nodeData.put("highlightType",highlight);
					}
				}
				
				features.add(nodeData);
			}
			
			List<Map> extraConstraints = new LinkedList<Map>();
			for( PropositionalFormula formula : model.getConstraints() ) {
				Map<String,String> ecData = new HashMap<String, String>();
				ecData.put("name", formula.getName());
				ecData.put("formula", formula.getFormula());
				extraConstraints.add(ecData);
			}
			
			root.put("features", features);
			root.put("extraconstraints", extraConstraints);
        }

		return root;
	}
	
}
