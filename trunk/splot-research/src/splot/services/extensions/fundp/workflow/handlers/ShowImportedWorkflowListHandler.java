package splot.services.extensions.fundp.workflow.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;
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
import org.xml.sax.InputSource;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

public class ShowImportedWorkflowListHandler extends FreeMarkerHandler {

	public ShowImportedWorkflowListHandler(String handlerName,
			HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buildModel(HttpServletRequest request,
			HttpServletResponse response, Map templateModel)
			throws HandlerExecutionException {
		try {
			
	        String enableSelection = request.getParameter("enableSelection");  // true: enables selection, false: shows models only
	        String selectionMode = request.getParameter("selectionMode");  // multiple files, one file
	        String serviceURL = request.getParameter("serviceURL");
	        String serviceHTTPMethod = request.getParameter("serviceHTTPMethod");
	        String serviceAction = request.getParameter("serviceAction");
	        String dirType=request.getParameter("dirType"); 
	        String sortBy = request.getParameter("sortby");

		
				
			
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
	private List<Map<String,String>> listWorkflowSpecifications(String modelsPath,String importedPath, String sortBy ) throws HandlerExecutionException {
		
		List<Map<String,String>> modelList = new LinkedList<Map<String,String>>();
		
		try {
			

			
			File modelsDir = new File(importedPath);		
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
	/** getRequestAsString  gets a body of a request from the request object and returns it in a string value.
	 * 
	 * @throws IOException 	if an exception has occurred that interferes with the IOException handler.
	 * @param request	a  HttpServletRequest object containing the request from the client
	 * @return a string value including the body of the request  
	 */	
private String getRequestAsString(HttpServletRequest request) throws java.io.IOException {
	InputStream in = request.getInputStream();
	BufferedReader requestData = new BufferedReader(new InputStreamReader(in));
	StringBuffer stringBuffer = new StringBuffer();
	String line;


	try{
		while ((line = requestData.readLine()) != null) {
			stringBuffer.append(line+"\n");
		}
	} catch (Exception e){
		
	}

	return stringBuffer.toString();


	}

/** checkWorkflowExistenceInImportedList  checks a new workflow name in the repository to avoid duplicate workflow names.
 * 
 * @throws	HandlerExecutionException	if an exception has occurred that interferes with the handler's normal operation
 * @param XMLData	the workflow specification in string format
 * @param importedDir			a string containing the real path to the imported workflow's folder
 * @return true, if the workflow name exists in the repository, otherwise, is false  
 */	
private boolean checkWorkflowExistenceInImportedList(String XMLData,String importedDir) throws HandlerExecutionException{
	
	Boolean retVal=false;
	try{
		DocumentBuilder builder_new = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		 InputSource is = new InputSource();
	     is.setCharacterStream(new StringReader(XMLData));
		
		Document doc_new = builder_new.parse(is);
		NodeList specificationNodes_new = doc_new.getElementsByTagName("specification");  // this tag includes workflow's information
		Element specificationElement_new = (Element) specificationNodes_new.item(0);
		String workflowName_new=specificationElement_new.getAttribute("uri");
		
		File  dir=new File(importedDir);
  		String[]  childeren=dir.list();
   		if (childeren!=null){
   			for (int i=0;i<childeren.length;i++){
   				if ((childeren[i].endsWith(".xml") !=false) || (childeren[i].endsWith(".yawl") !=false)){
  					String  fileName=childeren[i];
   					File importfile = new File(importedDir+"/"+fileName);
   					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
   					Document doc = builder.parse(importfile);
   					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
   					Element specificationElement = (Element) specificationNodes.item(0);
   					String workflowName=specificationElement.getAttribute("uri");
   					if (workflowName.compareToIgnoreCase(workflowName_new)==0){
   						return true;
   					}

   				}

   			}

   		}


		
		
	}catch (Exception e) {
		throw new HandlerExecutionException(e.getMessage());
	}
	return retVal;
	
	}


/** getWorkflowName  gets the workflow name from the workflow specification's string data.
 * 
 * @throws	HandlerExecutionException	if an exception has occurred that interferes with the handler's normal operation
 * @param XMLData	the workflow specification in string format
 * @param importedDir			a string containing the real path to the imported workflow's folder
 * @return the name of the workflow  
 */	
private String getWorkflowName(String XMLData,String importedDir) throws HandlerExecutionException{
	
	String retVal="";
	try{
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		 InputSource is = new InputSource();
	     is.setCharacterStream(new StringReader(XMLData));
		
		Document doc = builder.parse(is);
		NodeList specificationNodes = doc.getElementsByTagName("specification");  
		Element specificationElement = (Element) specificationNodes.item(0);
		String workflowName=specificationElement.getAttribute("uri");
		retVal=workflowName;
	}catch (Exception e) {
		throw new HandlerExecutionException(e.getMessage());
	}
	return retVal;
	
	}



}
