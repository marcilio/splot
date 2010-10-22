
package splot.services.extensions.fundp.handlers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.HttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import freemarker.template.Configuration;
import freemarker.template.Template;

import splot.core.FreeMarkerHandler;
import splot.core.Handler;
import splot.core.HandlerExecutionException;

/** ImportWorkflowSpecificationHandler receives the workflow specification in an XML data and saves it in a folder containing the files of the workflow specifications.    
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ImportWorkflowSpecificationHandler extends FreeMarkerHandler{
	
	/** ImportWorkflowSpecificationHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public ImportWorkflowSpecificationHandler (String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}

	
	@Override
	public void buildModel(HttpServletRequest request,
			HttpServletResponse response, Map templateModel)
			throws HandlerExecutionException {
		// TODO Auto-generated method stub
		
	
		String responseMessage=""; 
	   	String  xmlData="";    
		boolean validData=false;
		boolean fileCreationStatus=false;

		String xmlDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows";     //getServlet().getInitParameter("importedWorkflowPath");  // directory of workflow's xml files: SPLOT/WebContent/extensions/imported_workflows

		String fileName=(String)request.getParameter("fileName").trim();// received file name
		
		// check received file's correct name
		if ((fileName==null)||(fileName.equals("")) || (fileName.trim()=="") ){
			responseMessage="File name is empty.\n";
			
		}else if ((fileName.endsWith(".xml")==false) && (fileName.endsWith(".yawl")==false)) {
			responseMessage="Illegal file extension.\n";

		}else {
			try {
				xmlData= getRequestAsString(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			  //check files content
			if ((xmlData.indexOf("task")==-1) || (xmlData==null) || (xmlData=="") || (xmlData.indexOf("specificationSet")==-1) ){
			    validData=false;
			    responseMessage="Invalid file content.";

			}else{

				xmlData=xmlData.substring(xmlData.indexOf("<?xml"), xmlData.indexOf("</specificationSet>"))+" </specificationSet>";
				validData=true;
			}			
		}
		
		
		if (validData){
			try {
				if (checkWorkflowExistenceInImportedList(xmlData, xmlDir)){
					validData=false;
					responseMessage="<b>"+getWorkflowName(xmlData,xmlDir)+ "</b> workflow exists in the repository.";
					
				}else{
					validData=true;
					
					
				}
				
				
			} catch (HandlerExecutionException e1) {
				responseMessage=e1.getMessage();
				validData=false;
			}
		}	
		
		if (validData){
			File xmlFile=new File(xmlDir+"/"+fileName);
			
			// save file in repository
			if (xmlFile.exists()){
				responseMessage="<b>"+fileName+"</b> exists in the repository.\n";
			}else{
				try {
					FileWriter fw=new FileWriter(xmlDir+"/"+fileName);
					fw.write(xmlData);
					fw.close();
					fileCreationStatus=true;
					
				} catch (Exception e) {
					responseMessage=e.getMessage();
					fileCreationStatus=false;
				}
			}
		}
		
		if (fileCreationStatus){
			responseMessage="The file <b> " + fileName + " </b> is successfully saved in the repository.\n";
		}
		
		Map message=new HashMap();
		List<Map> messages=new LinkedList<Map>();
		message.put("value", responseMessage);
		messages.add(message);
		templateModel.put("messages", messages);
		
		
		
		
	
			
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



