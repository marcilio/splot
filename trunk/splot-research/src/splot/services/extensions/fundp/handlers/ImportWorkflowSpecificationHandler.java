
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

import splot.core.Handler;
import splot.core.HandlerExecutionException;

/** ImportWorkflowSpecificationHandler receives the workflow specification in an XML data and saves it in a folder containing the files of the workflow specifications.    
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ImportWorkflowSpecificationHandler extends Handler{
	
	/** ImportWorkflowSpecificationHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public ImportWorkflowSpecificationHandler (String handlerName,HttpServlet servlet) {
		super(handlerName, servlet);
	}

	

	/** run called by the servlet container to start its activity.  
	 * 
	 * @throws	ServletException,IOException 	if an exception has occurred, based on its type, that interferes with the servlet's normal operation or IOException handler.
	 * @param request	a  HttpServletRequest object containing the request received by handler from the client
	 * @param response	a  HttpServletResponse object containing the response should be sent to the client 	
	 */	
	public  void run(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String responseMessage=""; // replied to client

	   	String  xmlData="";    //  received file's content 
		boolean validData=false;
		boolean fileCreationStatus=false;

		String xmlDir=getServlet().getServletContext().getRealPath("/")+ "extensions/imported_workflows";     //getServlet().getInitParameter("importedWorkflowPath");  // directory of workflow's xml files: SPLOT/WebContent/extensions/imported_workflows

		String fileName=(String)request.getParameter("fileName").trim();// received file name
		
		// check received file's correct name
		if ((fileName==null)||(fileName.equals("")) || (fileName.trim()=="") || (fileName.endsWith(".xml")==false)){
			responseMessage="Illegal file name.\n";
		}else {
			xmlData= getRequestAsString(request);// read the request body
			
			  //check files content
			if ((xmlData.indexOf("task")==-1) || (xmlData==null) || (xmlData=="")){
			    validData=false;
			    responseMessage="Invalid file content.\n";
			}else{
				validData=true;
			}			
		}
		
		
		try {
			if (checkWorkflowExistenceInImportedList(xmlData, xmlDir)){
				validData=false;
				responseMessage=getWorkflowName(xmlData,xmlDir)+ " exists in the repository.";
				
			}else{
				validData=true;
				
				
			}
		} catch (HandlerExecutionException e1) {
			responseMessage=e1.getMessage();
			validData=false;
		}
		
		if (validData){
			File xmlFile=new File(xmlDir+"/"+fileName);
			
			// save file in repository
			if (xmlFile.exists()){
				responseMessage=fileName+" exists in the repository.\n";
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
			responseMessage="The file " + fileName + " is successfully saved in the repository.\n";
		}
		
		// response to client
		Writer outputStream=response.getWriter();	
	    outputStream.write(responseMessage);
	    outputStream.flush();
	     try{
	    	outputStream.close(); 
	     }
	     catch (Exception e) {
	    	 e.printStackTrace();
		}

	
			
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
   				if (childeren[i].endsWith(".xml") !=false){
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



