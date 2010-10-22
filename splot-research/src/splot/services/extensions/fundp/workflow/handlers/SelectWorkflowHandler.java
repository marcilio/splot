package splot.services.extensions.fundp.workflow.handlers;


import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.StringWriter;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelStatistics;
import splar.core.fm.XMLFeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;
import freemarker.template.Configuration;
import freemarker.template.Template;
/** SelectWorkflowHandler lists all the workflow specifications parsed in SPLOT allows the user to see it in detail.   
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class SelectWorkflowHandler extends FreeMarkerHandler {

	/** SelectWorkflowHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 * @param configuration		a Configuration object including information about freemarker template's configuration  
	 * @param template		a Template object including the template which should be used to create the interface of the html page              
	 */
	public SelectWorkflowHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration,template);
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
			
			
		        String enableSelection = request.getParameter("enableSelection");  // true: enables selection, false: shows models only
		        String selectionMode = request.getParameter("selectionMode");  // multiple files, one file
		        String serviceURL = request.getParameter("serviceURL");
		        String serviceHTTPMethod = request.getParameter("serviceHTTPMethod");
		        String serviceAction = request.getParameter("serviceAction");
		        
		        String sortBy = request.getParameter("sortby");
		        String dirType=request.getParameter("dirType"); 

		        List<Map<String,String>> modelList = listWorkflowSpecifications(getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows",getServlet().getServletContext().getRealPath("/")+ "extensions/imported_workflows", sortBy);                
		      
		        templateModel.put("models", modelList);


		        templateModel.put("enableSelection", enableSelection != null ? Boolean.valueOf(enableSelection) : false);
		        templateModel.put("selectionMode", selectionMode != null ? selectionMode : "");
		        templateModel.put("serviceURL", serviceURL != null ? serviceURL : "");
		        templateModel.put("serviceHTTPMethod", serviceHTTPMethod != null ? serviceHTTPMethod : "");
		        templateModel.put("serviceAction", serviceAction != null ? serviceAction : "");
		        templateModel.put("sortBy", sortBy == null ? "name" : sortBy);
		        templateModel.put("dirType", dirType != null ? dirType : "");

		}

        catch( HandlerExecutionException handlerExcObj ) {
        	throw handlerExcObj;
        }
        catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}
	}


	/**
	 * listWorkflowSpecifications creates a list of the workflow specifications stored in SPLOT.
	 * 
	 * @throws	HandlerExecutionException	if an exception has occurred that interferes with the handler's normal operation
	 * @param modelsPath	a string containing the real path to the models folder
	 * @param sortBy	a string including the name of a field that the list should be sorted based on that	
	 * @return a list of workflow specifications 
	 */
	private List<Map<String,String>> listWorkflowSpecifications(String modelsPath,String importedPath, String sortBy ) throws HandlerExecutionException {
		
		List<Map<String,String>> modelList = new LinkedList<Map<String,String>>();
		
		try {
			

			
			File modelsDir = new File(modelsPath);		
			File models[] = modelsDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return ((name.endsWith(".yawl")) || (name.endsWith(".xml")) );
				}
			});
					
			for( File modelFile : models ) {
				
				try {
  					 File file = new File(modelFile.getAbsolutePath());
  					 DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
  					 Document doc = builder.parse(file);
  					 NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
					 Element specificationElement = (Element) specificationNodes.item(0);
					 
					 NodeList creatorNodes = doc.getElementsByTagName("creator");  // this tag includes workflow's information
					 Element creatorElement = (Element) creatorNodes.item(0);
					 
					 NodeList versionNodes = doc.getElementsByTagName("version");  // this tag includes workflow's information
					 Element versionElement = (Element) versionNodes.item(0);
  					 
					 Map<String,String> modelData = new HashMap<String, String>();
					 modelData.put("name", specificationElement.getAttribute("uri"));
					 modelData.put("creator", Methods.getCharacterDataFromElement(creatorElement));
					 modelData.put("version", Methods.getCharacterDataFromElement(versionElement));
					 modelData.put("file", modelFile.getName());
				        modelList.add(modelData);
				} catch (Exception e) {
					System.out.println("Problems loading workflow: " + modelFile.getName());
					e.printStackTrace();
				}

			}
			
				Comparator modelComparator = null;
				if ( sortBy == null || sortBy.equals("name")) {
					modelComparator = new Comparator() {
						public int compare(Object model1Hash, Object model2Hash) {
							String name1 = (String)((Map)model1Hash).get("name");
							String name2 = (String)((Map)model2Hash).get("name");
							return name1.compareToIgnoreCase(name2);
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
							String name1 = (String)((Map)model1Hash).get("version");
							String name2 = (String)((Map)model2Hash).get("version");
							return name1.compareToIgnoreCase(name2);
						}			
					};
				}
		
				Collections.sort(modelList, modelComparator);
		}
		catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the workflow specification repository path":e.getMessage());
		}
        return modelList;
	}
	
	private List<Map<String,String>> listImportedWorkflowSpecifications(String modelsPath,String importedPath, String sortBy, String actionType) throws HandlerExecutionException {
		
		List<Map<String,String>> modelList = new LinkedList<Map<String,String>>();
		if (actionType.compareToIgnoreCase("select")==0){
				
		        return modelList;
		}
		
		try {
			String parsedDir=modelsPath;
			String importedDir=importedPath;
			File  dir=new File(importedDir);
			String xmlData="";
			Boolean hasData=false;
			
			
			String[]  childeren=dir.list();
       		if (childeren!=null){
       			for (int i=0;i<childeren.length;i++){
       				if ((childeren[i].endsWith(".xml") !=false) ||  (childeren[i].endsWith(".yawl") !=false)){
      					String  fileName=childeren[i];
       					File importfile = new File(importedDir+"/"+fileName);
       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       					Document doc = builder.parse(importfile);
       					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
       					Element specificationElement = (Element) specificationNodes.item(0);

   					    NodeList creatorNodes = doc.getElementsByTagName("creator");  // this tag includes workflow's information
					    Element creatorElement = (Element) creatorNodes.item(0);
					 
					    NodeList versionNodes = doc.getElementsByTagName("version");  // this tag includes workflow's information
					    Element versionElement = (Element) versionNodes.item(0);

      					StringWriter stw = new StringWriter(); 
       					Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
       					serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
       					xmlData=stw.toString();
	   					File exportFile=new File(parsedDir+"/"+fileName);
	   					
      					if (!exportFile.exists()){
       						try {
       							FileWriter fw=new FileWriter(parsedDir+"/"+fileName);
       							fw.write(xmlData);
       							fw.close();  
       							importfile.delete();
       							Map<String,String> modelData = new HashMap<String, String>();
       							modelData.put("name", specificationElement.getAttribute("uri"));
       							modelData.put("creator", Methods.getCharacterDataFromElement(creatorElement));
       							modelData.put("version", Methods.getCharacterDataFromElement(versionElement));
       							modelData.put("file", fileName);
       							modelList.add(modelData);
       							hasData=true;
       							
       						} catch (Exception e) {
       							
       							e.printStackTrace();
       						}
       						
       					}
       					
       					

       				}

       			}

       		}
	
       		
								
				Comparator modelComparator = null;
				if ( sortBy == null || sortBy.equals("name")) {
					modelComparator = new Comparator() {
						public int compare(Object model1Hash, Object model2Hash) {
							String name1 = (String)((Map)model1Hash).get("name");
							String name2 = (String)((Map)model2Hash).get("name");
							return name1.compareToIgnoreCase(name2);
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
							String name1 = (String)((Map)model1Hash).get("version");
							String name2 = (String)((Map)model2Hash).get("version");
							return name1.compareToIgnoreCase(name2);
						}			
					};
				}
		
				Collections.sort(modelList, modelComparator);
		}
		catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the workflow specification repository path":e.getMessage());
		}
        return modelList;
	}
		
	
}
