package splot.services.extensions.fundp.handlers.conf;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelStatistics;
import splar.core.fm.XMLFeatureModel;
import splot.core.HandlerExecutionException;
import splot.services.handlers.analyses.SelectFeatureModelHandler;

public class FCWSelectFeatureModelHandler extends SelectFeatureModelHandler{

	public FCWSelectFeatureModelHandler(String handlerName,
			HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
		// TODO Auto-generated constructor stub
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
        
		try {
			
			
	        String enableSelection = request.getParameter("enableSelection");  // true: enables selection, false: shows models only
	        String selectionMode = request.getParameter("selectionMode");  // multiple files, one file
	        String serviceURL = request.getParameter("serviceURL");
	        String serviceHTTPMethod = request.getParameter("serviceHTTPMethod");
	        String serviceAction = request.getParameter("serviceAction");
	        String showModelDetails = request.getParameter("showModelDetails");
	        
	        String sortBy = request.getParameter("sortby");
	        
	        // Read pre-loaded local models (FILE)
	        List<Map<String,String>> modelList = listFeatureModels(getServlet().getInitParameter(("modelsPath")), sortBy);                
	        templateModel.put("models", modelList);
	        templateModel.put("enableSelection", enableSelection != null ? Boolean.valueOf(enableSelection) : false);
	        templateModel.put("selectionMode", selectionMode != null ? selectionMode : "");
	        templateModel.put("serviceURL", serviceURL != null ? serviceURL : "");
	        templateModel.put("serviceHTTPMethod", serviceHTTPMethod != null ? serviceHTTPMethod : "");
	        templateModel.put("serviceAction", serviceAction != null ? serviceAction : "");
	        templateModel.put("sortBy", sortBy == null ? "features" : sortBy);
	        templateModel.put("showModelDetails", showModelDetails != null ? Boolean.valueOf(showModelDetails) : true);
	        
	        
	        
	        
	        
	        
	        
	        
		} 
        catch( HandlerExecutionException handlerExcObj ) {
        	throw handlerExcObj;
        }
        catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}
	}

	
	private List<Map<String,String>> listFeatureModels(String modelsPath, String sortBy) throws HandlerExecutionException {
		
		List<Map<String,String>> modelList = new LinkedList<Map<String,String>>();
		
		try {
				File modelsDir = new File(modelsPath);		
				File models[] = modelsDir.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return (name.endsWith(".xml"));
					}
				});
						
				for( File modelFile : models ) {
					
					try {
						
						FeatureModel model = new XMLFeatureModel(modelFile.getAbsolutePath(), XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
						model.loadModel();
						FeatureModelStatistics stats = new FeatureModelStatistics(model);
						stats.update();
						
				        Map<String,String> modelData = new HashMap<String, String>();
				        modelData.put("name", model.getName());
				        modelData.put("features", String.valueOf(model.countFeatures()));
				        modelData.put("ecr", String.valueOf((int)(stats.getECRepresentativeness()*100)));
				        if ( stats.getECRepresentativeness() == 0 ) {
				        	modelData.put("clausedensity", "N/A");	
				        }
				        else {
				        	DecimalFormat format = new DecimalFormat("0.0");
				        	modelData.put("clausedensity", format.format(stats.getECClauseDensity()));
				        }
				        modelData.put("file", modelFile.getName());
			
				        String creator = model.getMetaData("creator");
				        modelData.put("creator", "-");
				        if ( creator != null ) {
				        	modelData.put("creator", creator);
				        }
				        String date = model.getMetaData("date");
				        modelData.put("date", "-");
				        if ( date != null ) {
				        	modelData.put("date", date);
				        }
			
				        modelList.add(modelData);
					} catch (Exception e) {
						System.out.println("Problems loading model: " + modelFile.getName());
						e.printStackTrace();
					}

				}
						
				Comparator modelComparator = null;
				if ( sortBy == null || sortBy.equals("features")) {
					modelComparator = new Comparator() {
						public int compare(Object model1Hash, Object model2Hash) {
							int size1 = Integer.valueOf((String)((Map)model1Hash).get("features"));
							int size2 = Integer.valueOf((String)((Map)model2Hash).get("features"));
							return (size1 > size2 ? 1 : (size1 < size2 ? -1 : 0));
						}			
					};
				}
				else if  (sortBy.equals("ecr") ){
					modelComparator = new Comparator() {
						public int compare(Object model1Hash, Object model2Hash) {
							int ecr1 = Integer.valueOf((String)((Map)model1Hash).get("ecr"));
							int ecr2 = Integer.valueOf((String)((Map)model2Hash).get("ecr"));
							return (ecr1 > ecr2 ? 1 : (ecr1 < ecr2 ? -1 : 0));
						}			
					};
				}
				else if  (sortBy.equals("creator") ){
					modelComparator = new Comparator() {
						public int compare(Object model1Hash, Object model2Hash) {
							String name1 = (String)((Map)model1Hash).get("creator");
							String name2 = (String)((Map)model2Hash).get("creator");
							return name1.compareToIgnoreCase(name2);
						}			
					};
				}
				else {
					modelComparator = new Comparator() {
						public int compare(Object model1Hash, Object model2Hash) {
							String name1 = (String)((Map)model1Hash).get("name");
							String name2 = (String)((Map)model2Hash).get("name");
							return name1.compareToIgnoreCase(name2);
						}			
					};
				}
		
				Collections.sort(modelList, modelComparator);
		}
		catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the feature model repository path":e.getMessage());
		}
        return modelList;
	}
		
	

}
