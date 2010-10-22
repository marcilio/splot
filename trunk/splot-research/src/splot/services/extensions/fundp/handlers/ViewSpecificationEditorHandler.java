package splot.services.extensions.fundp.handlers;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splar.core.constraints.CNFClause;
import splar.core.constraints.CNFLiteral;
import splar.core.constraints.PropositionalFormula;
import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelStatistics;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.XMLFeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods; 

/** ViewSpecificationEditorHandler is used to manage the view specification editor. This also prepares the required information for the editor.  
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ViewSpecificationEditorHandler extends FreeMarkerHandler {
	
	/** ParseWorkflowSpecificationHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 * @param configuration		a Configuration object including information about freemarker template's configuration  
	 * @param template		a Template object including the template which should be used to create the interface of the html page              
	 */
	public ViewSpecificationEditorHandler (String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	/**
	 * buildModel implements the handler's logic. It basically fills the templateModel map with the necessary information
	 * that will be later referenced by this handler's freemarker template.
	 * 
	 * @throws	HandlerExecutionException	if an exception has occurred that interferes with the handler's normal operation
	 * @param request	a  HttpServletRequest object containing the request received by handler from the client
	 * @param response	a  HttpServletResponse object containing the response should be sent to the client 	
	 * @param templateModel	a template including the information's structure should be sent to the client
	 */
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
        try {     

        	String viewFileSource="new_file";
        	
        	String modelSource = "new_model";
    		String url = null;
    		String filename = null;
        	
            String modelFileName = (String)request.getParameter("userModels");
            if ( modelFileName != null && modelFileName.trim().length() != 0) {
            	if ( !modelFileName.startsWith("http://")) {
            		throw new HandlerExecutionException("User model's URL must start with \"http://\"");
            	}
            	url = modelFileName;
	            modelSource = "existing_model";
            }
            else {
        		modelFileName = (String)request.getParameter("selectedModels");
        		if ( modelFileName != null ) {
		            filename = modelFileName; 
		            modelSource = "existing_model";
        		}
            }
        	
    		templateModel.put("new_model", false);
        	if ( modelSource.compareToIgnoreCase("new_model" ) == 0 ) {
        		templateModel.put("new_model", true);
    			templateModel.put("model_filename_in_repository", "");
    			
    			templateModel.put("fm_name", "");
    			templateModel.put("fm_description", "");
    			templateModel.put("fm_author", "");
    			templateModel.put("fm_author_address", "");
    			templateModel.put("fm_author_email", "");
    			templateModel.put("fm_author_phone", "");
    			templateModel.put("fm_author_website", "");
    			templateModel.put("fm_author_organization", "");
    			templateModel.put("fm_author_department", "");
    			templateModel.put("fm_creation_date", "");
    			templateModel.put("fm_publication", "");
        	}
        	else if ( modelSource.compareToIgnoreCase("existing_model" ) == 0 ) {
        		        		        		
        		String featureModelPath = url;        		
        		if ( url == null ) {
        			featureModelPath = getServlet().getServletContext().getRealPath("/")+ "models/" + filename;
        		}
        		
        		FeatureModel featureModel = new XMLFeatureModel(featureModelPath, XMLFeatureModel.SET_ID_AUTOMATICALLY);
        		featureModel.loadModel();
        		
        		// Feature Diagram
        		List features = new LinkedList();
        		createFeatureModelTemplateModel(featureModel, featureModel.getRoot(), templateModel, features, 1);
        		templateModel.put("features", features);
        		
        		// Statistics
        		FeatureModelStatistics stats = new FeatureModelStatistics(featureModel);
        		stats.update();

        		int numberOfConstraints = stats.countConstraints();
        		templateModel.put("statsCountFeatures", stats.countFeatures());
        		templateModel.put("statsCountMandatory", stats.countMandatory());
        		templateModel.put("statsCountOptional", stats.countOptional());
        		templateModel.put("statsCountXOR", stats.countGroups11());
        		templateModel.put("statsCountOR", stats.countGroups1N());
        		templateModel.put("statsCountGrouped", stats.countGrouped());
        		templateModel.put("statsCountConstraints", numberOfConstraints);
        		templateModel.put("statsCTCR", stats.getECRepresentativeness());
        		templateModel.put("statsDistinctCTCVars", stats.countConstraintVars());
        		templateModel.put("statsCTCClauseDensity", stats.getECClauseDensity());
        		
        		// Cross Tree Constraints
        		List constraints = new ArrayList(numberOfConstraints);
    			for( PropositionalFormula formula : featureModel.getConstraints() ) {
    				CNFClause clause = formula.toCNFClauses().toArray(new CNFClause[0])[0];
    				Map constraintData = new HashMap();
    				constraintData.put("constraint_name", formula.getName());
    				List literals = new ArrayList(clause.countLiterals());
    				for( CNFLiteral literal : clause.getLiterals() ) {
    					Map literalData = new HashMap();
    					literalData.put("literal_variable", literal.getVariable().getID());
    					literalData.put("literal_feature_name", featureModel.getNodeByID(literal.getVariable().getID()).getName());
    					literalData.put("literal_negated", Boolean.toString(!literal.isPositive()));
    					literals.add(literalData);
    				}
    				constraintData.put("constraint_literals", literals);
    				constraints.add(constraintData);
    			}
    			templateModel.put("constraints", constraints); 
    		
        		
    			// additional information
    			templateModel.put("fm_name", featureModel.getName());
    			templateModel.put("model_filename_in_repository", filename);

    			templateModel.put("fm_description", featureModel.getMetaData("description"));
    			templateModel.put("fm_author", featureModel.getMetaData("creator"));
    			templateModel.put("fm_author_address", featureModel.getMetaData("address"));
    			templateModel.put("fm_author_email", featureModel.getMetaData("email"));
    			templateModel.put("fm_author_phone", featureModel.getMetaData("phone"));
    			templateModel.put("fm_author_website", featureModel.getMetaData("website"));
    			templateModel.put("fm_author_organization", featureModel.getMetaData("organization"));
    			templateModel.put("fm_author_department", featureModel.getMetaData("department"));
    			templateModel.put("fm_creation_date", featureModel.getMetaData("date"));
    			templateModel.put("fm_publication", featureModel.getMetaData("reference"));
    			
    			
    			
    			String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath"); 
    			String viewFileName="view_"+filename;
    			File xmlFile=new File(viewDir+"/"+viewFileName);
    			
    			if (xmlFile.exists()){
    				
    				viewFileSource="existing_file";
    				templateModel.put("view_file_source", viewFileSource);
    				templateModel.put("view_file_name", viewFileName);
    				templateModel.put("feature_model_file_name", filename);
    				templateModel.put("related_feature_model", featureModel.getName());
    				
    				
 					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					Document doc = builder.parse(xmlFile);
					
					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
					Element  featureModelElement = (Element) featureModelNodes.item(0);
					
					templateModel.put("related_feature_model",featureModelElement.getAttribute("name"));
					
					List<Map> viewlist =new LinkedList<Map>();
					NodeList  viewNodes = doc.getElementsByTagName("view"); 
					for (int i = 0; i < viewNodes.getLength(); i++){
						Element element = (Element) viewNodes.item(i);
						Map viewData = new HashMap();
						viewData.put("view_name", element.getAttribute("name").trim());
						NodeList metaData = element.getElementsByTagName("data");
					
						for (int j = 0; j < metaData.getLength(); j++){
							
							Element descriptionElement = (Element) metaData.item(0);
							viewData.put("view_description", Methods.getCharacterDataFromElement(descriptionElement).trim());
							
							Element creatorElement = (Element) metaData.item(1);
							viewData.put("view_creator", Methods.getCharacterDataFromElement(creatorElement).trim());
	
							Element emailElement = (Element) metaData.item(2);
							viewData.put("view_email", Methods.getCharacterDataFromElement(emailElement).trim());

							Element dateElement = (Element) metaData.item(3);
							viewData.put("view_date", Methods.getCharacterDataFromElement(dateElement).trim());

							Element commentElement = (Element) metaData.item(4);
							viewData.put("view_comment", Methods.getCharacterDataFromElement(commentElement).trim());
							
						}
						
					     NodeList viewSpecification = element.getElementsByTagName("specification");
					     Element specification = (Element) viewSpecification.item(0);
					     viewData.put("view_specification", Methods.getCharacterDataFromElement(specification).trim());
					     
					     viewlist.add(viewData);
					      
					}
					
					templateModel.put("views_list", viewlist);
					

    			}else{
    				viewFileSource="new_file";
       				templateModel.put("view_file_source", viewFileSource);
    				templateModel.put("view_file_name", viewFileName);
    				templateModel.put("feature_model_file_name", filename);
    				templateModel.put("related_feature_model", featureModel.getName());
    				templateModel.put("views_list", "");
    				
    			}
    			
    			
    			
        	}        	
        }
        catch (Exception e) {
        	e.printStackTrace();
        	throw new HandlerExecutionException(e.getMessage());
		}
	}
	
	/**
	 * createFeatureModelTemplateModel is used to prepare the information needed to create a feature diagram based on a feature model.
	 * @param featureModel	the feature model which should be presented as a feature model.
	 * @param featureNode	a feature of the feature model	
	 * @param templateModel	a template including the information's structure should be sent to the client
	 * @param features a list of features with their information
	 * @param level the level of the feature in the feature diagram
	 * @return a Map object containing the feature's data 
	 */
	private Map createFeatureModelTemplateModel(FeatureModel featureModel, FeatureTreeNode featureNode, Map templateModel, List features, int level) {

		Map featureData = new HashMap();
		featureData.put("feature_id", featureNode.getID());
		featureData.put("feature_level", level);
		String featureType = getFeatureType(featureNode);
		featureData.put("feature_type", featureType );
		if ( featureType.equals("or") ) {
			featureData.put("feature_name", "[1..*]" );
		}
		else if ( featureType.equals("xor") ) {
			featureData.put("feature_name", "[1..1]" );
		}
		else {
			featureData.put("feature_name", featureNode.getName());
		}
		if ( featureNode.isRoot() ) {
			featureData.put("feature_parent_id", "");
		}
		else {
			featureData.put("feature_parent_id", ((FeatureTreeNode)featureNode.getParent()).getID());
		}
		List children = new ArrayList(featureNode.getChildCount());
		for( int i = 0 ; i < featureNode.getChildCount() ; i++ ) {
			FeatureTreeNode childNode = (FeatureTreeNode)featureNode.getChildAt(i);
			children.add(createFeatureModelTemplateModel(featureModel, childNode, templateModel, features, level+1));
		}
		featureData.put("feature_children", children);
		if ( featureNode.isRoot() ) {
			templateModel.put("root_feature", featureData);
		}
		features.add(featureData);
		return featureData;
	}
	
	/**
	 * getFeatureType is used to get the feature's type in a feature model.
	 * @param node	a node which its type should be checked.
	 * @return a string containing the node's type.
	 */
	private String getFeatureType(FeatureTreeNode node) {
		if ( node instanceof SolitaireFeature ) {
			if ( ((SolitaireFeature)node).isOptional() ) {
				return "optional";
			}
			else {
				return "mandatory";
			}
		}
		else if ( node instanceof FeatureGroup ) {
			int min = ((FeatureGroup)node).getMin();
			int max = ((FeatureGroup)node).getMax();
			if ( min == 1 && max == 1 ) {
				return "xor";
			}
			else if ( min == 1 && (max == node.getChildCount() || max == -1 ) ) {
				return "or";
			}
			else {
				return "group";
			}
		}
		else if ( node instanceof GroupedFeature ) {
			return "grouped";
		}
		return "root";
	}
	
	
}
