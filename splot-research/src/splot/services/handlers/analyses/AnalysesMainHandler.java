package splot.services.handlers.analyses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import splar.core.fm.FeatureModel;
import splar.core.fm.XMLFeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class AnalysesMainHandler extends FreeMarkerHandler {

	public AnalysesMainHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public static int maxModelSizeForBDD = 500;
	
	private boolean hasValidModels(String models[]) {
		if (models != null) {
			for( String model : models) {
				if ( model.trim().length() > 0 ) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
        // Build the data-model
        
		// Session:
		//   Map
		//   - model: FeatureModel
		//   - modelFileName: String
		//   - modelFilePath: String
		//   - modelDeadFeatures: List<String>  (added by RunFeatureModelAnalysesHandler handler)
		//   - modelCommonFeatures: List<String>  (added by RunFeatureModelAnalysesHandler handler)
		
        try {

	        String urlsList[] = extractURLs(request.getParameter("userModels")); 
	        String selectedModels[] = request.getParameterValues("selectedModels");
	
	        if ( !hasValidModels(urlsList) && !hasValidModels(selectedModels) ) {
	        	throw new HandlerExecutionException("At least one model must be indicated/selected");
	        }
	        else {
	        
		        List<String> modelsToProcess = new LinkedList<String>();
		
		    	// user models (URL)        
		        if ( urlsList != null ) {
		        	for( String url : urlsList ) {
		        		if ( url.startsWith("http://") ) {
		        			modelsToProcess.add(url);
		        		}
		        	}
		        }
		        
				// pre-loaded models (FILE)
		        if ( selectedModels != null ) {
		        	modelsToProcess.addAll(Arrays.asList(selectedModels));
		        }
		
		        List<Map> loadedModels = new LinkedList<Map>();
		        // FreeMarked template model
		        List templateLoadedModels = new LinkedList();
		
		        templateModel.put("maxModelSizeForBDD", maxModelSizeForBDD);
		        
		        // PRE-LOADED MODELS (FILE)
		        int modelIndex = 0;
		        for( String selectedModel : modelsToProcess ) {
		        	String modelLocatorString = selectedModel.trim();
		        	if ( !selectedModel.startsWith("http://") ) {
		        		modelLocatorString = getServlet().getInitParameter("modelsPath") + selectedModel;
		        	}
		        	
		        	try {
			    		FeatureModel model = new XMLFeatureModel(modelLocatorString, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
			    		model.loadModel();
			    		Map modelMap = new HashMap();
			    		modelMap.put("model", model);
//			    		modelMap.put("modelFileName", selectedModel);
//			    		modelMap.put("modelFilePath", getServlet().getInitParameter("modelsPath"));
			    		loadedModels.add(modelMap);
			    		
			            Map loadedModelsHash = new HashMap();
			            loadedModelsHash.put("index", modelIndex);
			            loadedModelsHash.put("name", model.getName());
			            loadedModelsHash.put("fileName", selectedModel);
			            loadedModelsHash.put("size", model.countFeatures());
			            templateLoadedModels.add(loadedModelsHash);
			            
			            modelIndex++;
			            
					} catch (Exception e) {					
					}
		        }
		                
		        templateModel.put("loadedModels", templateLoadedModels);
		        
				HttpSession session = request.getSession(true);
				session.setAttribute("loadedModels", loadedModels);
	        }
        }
        catch( HandlerExecutionException handlerExcObj ) {
        	throw handlerExcObj;
        }
        catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}
	}

	private String[] extractURLs(String urls) {
		return urls.split("\r\n");
	}
}

