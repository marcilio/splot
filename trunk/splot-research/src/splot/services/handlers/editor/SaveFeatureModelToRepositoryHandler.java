package splot.services.handlers.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sat4j.specs.ContradictionException;

import splar.core.fm.FeatureModel;
import splar.plugins.reasoners.sat.sat4j.FMReasoningWithSAT;
import splot.core.HandlerExecutionException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SaveFeatureModelToRepositoryHandler extends ExportFeatureModelHandler {

	private static final int MINIMUM_NUMBER_OF_FEATURES = 10;  
	
	public SaveFeatureModelToRepositoryHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {		
        try {
    		templateModel.put("error_saving_model", false);
    		templateModel.put("error_saving_model_message", "");
    		templateModel.put("model_url", "");
			templateModel.put("model_filename", "");
			boolean newModel = true;
			if ( request.getParameter("new_model").compareToIgnoreCase("false") == 0 ) {
				newModel = false;
			}
			templateModel.put("new_model", newModel);
			String modelFileName = request.getParameter("model_filename_in_repository");				
        	String featureModelJSONString = request.getParameter("featureModelJSONString");
        	if ( featureModelJSONString != null ) {	
        		JSONFeatureModel featureModel = new JSONFeatureModel(featureModelJSONString);
        		featureModel.loadModel();
        		
        		// model must have at least 10 features
        		if ( featureModel.countFeatures() >= MINIMUM_NUMBER_OF_FEATURES ) {
        		
	        		// check if model is consistent
	     	       	FMReasoningWithSAT reasoner = new FMReasoningWithSAT("MiniSAT", featureModel, 60000);
	     	       	// SAT-based analysis
	     	       	boolean isConsistent = true;
	     	       	try {
	         	       	reasoner.init();
	         	       	isConsistent = reasoner.isConsistent();
					} catch (ContradictionException e) {
						isConsistent = false;
					}
					
					String errorMessage = mandatoryAdditionalInformationHasBeenFilled(featureModel);
					
					// additional information's mandatory fields have been properly filled out
					if (  errorMessage == null ) {
						// is model consistent?
						if ( isConsistent ) {
		         	       	List<String> deadFeatures = reasoner.allDeadFeatures(new HashMap<String, String>());
		         	       	// model has dead features?
		         	       	if ( deadFeatures != null && deadFeatures.size() > 0 ) {
		    		    		templateModel.put("error_saving_model", true);
		    		    		templateModel.put("error_saving_model_message", "Your model contains DEAD features and thus cannot be saved in the repository!");
		         	       	}
		         	       	// model can finally be saved
		         	       	else {
		                		String location = getServlet().getInitParameter("modelsPath");
		                		if ( newModel ) {
		                			modelFileName = ExportFeatureModelHandler.genFeatureModelFileName(featureModel) + ".xml";
		                		}
		                		
		            			templateModel.put("model_url", "/SPLOT/models/" + modelFileName);
		        				templateModel.put("model_filename", modelFileName);
		            			saveFeatureModel(featureModel, location + modelFileName);
		            			
		            			addAdditionalFeatureModelInfo( featureModel, templateModel );
		            			
		            			emailMe(featureModel, templateModel);
		         	       	}
						}
						else {
				    		templateModel.put("error_saving_model", true);
				    		templateModel.put("error_saving_model_message", "Your model is INCONSISTENT and thus cannot be saved in the repository!");
						}
					}
					else {
			    		templateModel.put("error_saving_model", true);
			    		templateModel.put("error_saving_model_message", errorMessage);
					}
        		}
        		// doesn't have the minimum number of features
        		else {
		    		templateModel.put("error_saving_model", true);
		    		templateModel.put("error_saving_model_message", "Only models with " + MINIMUM_NUMBER_OF_FEATURES + " or more features can be saved in the repository!");
        		}
        	}
        }
        catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}
	}
	
	private String mandatoryAdditionalInformationHasBeenFilled(FeatureModel featureModel) {
		if ( featureModel.getName().trim().length() == 0 ) {
			return "You must specify a name for your feature model in the appropriate 'Additional Information' form at the bottom of this page";
		}
		String metaData[] = {
				"description:model description", "creator:primary author", "email:author's email", "organization: author's organization" 
		};			
		for( String metaDataString : metaData ) {				
			String metadataValue = featureModel.getMetaData(metaDataString.split(":")[0]);
			if ( metadataValue == null || metadataValue.trim().length() == 0 ) {
				return "Your model could not be saved in the repository.<br/> Reason: You must fill out field '<b>" + metaDataString.split(":")[1]+ "'</b> in the appropriate '<b>Additional Information</b>' form at the bottom of this page";
			}
		}			
		return null;
	}

	private void addAdditionalFeatureModelInfo( FeatureModel model, Map templateModel ) {
		templateModel.put("model_name", model.getName());
		String metaData[] = {
				"model_description:description", "model_author:creator", "model_author_address:address", 
				"model_author_email:email", "model_author_phone:phone", "model_author_website:website",
				"model_author_organization:organization", "model_author_department:department", 
				"model_creation_date:date", "model_publication:reference" 
		};			
		for( String metaDataString : metaData ) {				
			templateModel.put(metaDataString.split(":")[0], model.getMetaData( metaDataString.split(":")[1]));
		}
	}
	
	private void emailMe(final FeatureModel featureModel, final Map templateModel) {
		new Thread() {
			public void run() {
				try {
					
					String body = 	
						"SPLOT's feature model repository has been updated." +
						"\r\n\n" + 
						"Model name: " + templateModel.get("model_name") + 
					 	"\r\n" + 
						"Model size: " + featureModel.countFeatures() + " features" +
					 	"\r\n" + 
						"Author: " + templateModel.get("model_author") +
					 	"\r\n" +
						"Email: " + templateModel.get("model_author_email") +
					 	"\r\n" +
						"Description: " + templateModel.get("model_description") +
					 	"\r\n" +
						"Organization: " + templateModel.get("model_author_organization") +
					 	"\r\n" +
						"Model URL: http://www.splot-research.org/models/" + templateModel.get("model_name") + ".xml";

					String subject = ((Boolean)templateModel.get("new_model")) ? "New Model: " : "Updated Model: ";
					subject += templateModel.get("model_name");
					
					WebConversation wc = new WebConversation();
					String url="http://www.marcilio-mendonca.com/emailme.asp";
					WebRequest request = new GetMethodWebRequest( url );
					
					request.setParameter("_subject", subject );
					request.setParameter("_body", body );
					wc.getResponse(request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
}

