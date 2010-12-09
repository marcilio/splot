package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.LinkedHashMap;

import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelException;
import splar.core.fm.XMLFeatureModel;
import splot.core.Handler;
import splot.services.extensions.fundp.utilities.Methods;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


import org.w3c.dom.*;

import org.xml.sax.SAXException;

import fm.FeatureTreeNode;

/** SaveViewSpecificationToRepositoryHandler is used to create a file for a view and save it in the views folder.  
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class SaveViewSpecificationToRepositoryHandler extends Handler {
	
	/** SaveViewSpecificationToRepositoryHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public SaveViewSpecificationToRepositoryHandler (String handlerName , HttpServlet servlet) {
		super(handlerName, servlet);
	}
	
	/** run called by the servlet container to start its activity.  
	 * 
	 * @throws	ServletException,IOException 	if an exception has occurred, based on its type, that interferes with the servlet's normal operation or IOException handler.
	 * @param request	a  HttpServletRequest object containing the request received by handler from the client
	 * @param response	a  HttpServletResponse object containing the response should be sent to the client 	
	 */	
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
        try{

        		String modelDir=getServlet().getServletContext().getRealPath("/")+ "models";
        		String requestValidation=requestValidation(request, modelDir);

        		if (!(requestValidation.compareToIgnoreCase("true")==0)){
        			response.getWriter().write(requestValidation);
        		}else{
				        	String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath");
				        	String featureModelFileName=(String)request.getParameter("feature_model_file_name");
				        	String viewFileName=(String)request.getParameter("view_file_name_in_repository");
				        	String viewJSONString = request.getParameter("view_in_json");
				        	String relatedFeatureModel=request.getParameter("related_feature_model");
				        	boolean newFile=false;
				        	File file = new File(viewDir+"/"+viewFileName);
				        	String filePath=viewDir+"/"+viewFileName;
				        //	String JSONValidationResult=JSONStringValidation(viewJSONString);
				        	
					   		
				        	
				        	
				        	
				        	if (file.exists()){
				        		newFile=false;
				        		
				        		try{
						        		String[] viewInDetail= new String[8];
						        		viewInDetail=getViewJSONDInArray(viewJSONString);
						        		String viewOldName=viewInDetail[0];
						        		String viewNewName=viewInDetail[1];
						        		if (viewOldName.compareToIgnoreCase("New View")==0){
						        			if (checkViewExistingInSpecification(filePath, viewInDetail[1])){
						        				response.getWriter().write("The view already exists in the specification.");
						        			}else{
							        			String result= appendToViewSpecificationXMLFile(viewJSONString, filePath);
							        			if (result.compareToIgnoreCase("true")==0){
							    					response.getWriter().write("true");
							    				}else{
							    					response.getWriter().write(result);
							    				}
						        				
						        			}
						        			
						        		}else{
						        			
						        			if (!(viewInDetail[0].compareToIgnoreCase(viewInDetail[1])==0)){
						        				if (checkViewExistingInSpecification(filePath, viewInDetail[1])){
						        					response.getWriter().write("The view already exists in the repository.");
						        				}else{
						        					String resultUpdate=UpdateExistingViewInformationInXMLFile(viewJSONString, filePath);
								        			
								        			
								        			if (resultUpdate.compareToIgnoreCase("true")==0){
								        				response.getWriter().write("true");
								        			}else{
								        				response.getWriter().write(resultUpdate);
								        			}
								        			
						        				}
						        				
						        			}else{
						        				String resultUpdate=UpdateExistingViewInformationInXMLFile(viewJSONString, filePath);
							        			
							        			
							        			if (resultUpdate.compareToIgnoreCase("true")==0){
							        				response.getWriter().write("true");
							        			}else{
							        				response.getWriter().write(resultUpdate);
							        			}
							        			
						        			}
						        			
						        		}
						        		
				        		}catch (Exception e) {
				        			response.getWriter().write(e.getMessage());
								}
				        	}else{
				        		newFile=true;
				        		try{
				        				String result= createViewSpecificationXMLFile(viewJSONString, filePath, relatedFeatureModel);
				        				if (result.compareToIgnoreCase("true")==0){
				        					response.getWriter().write("true");
				        				}else{
				        					response.getWriter().write(result);
				        				}
				        		 }catch (Exception e) {
				        			response.getWriter().write(e.getMessage());
								}	
				        	}
        		}   	
	    }catch (Exception e) {
	        	e.printStackTrace();
			}
	}
	
	private static String[] getViewJSONDInArray (String viewJSONString) throws JSONException, ParseException, FeatureModelException{
		String[] retStr=new String[8];
		JSONParser parser = new JSONParser();
		ContainerFactory  factory = new ContainerFactory() {
			
			@Override
			public Map createObjectContainer() {
				// TODO Auto-generated method stub
				return new LinkedHashMap();
			}
			
			@Override
			public List creatArrayContainer() {
				// TODO Auto-generated method stub
				return new LinkedList();
			}
		};
		
		try{
			Map json =(Map)parser.parse(viewJSONString, factory);
			java.util.Iterator iter=json.entrySet().iterator();
			while (iter.hasNext()){
				Map.Entry entry=(Map.Entry)iter.next();
				
				if ("view_old_name".compareToIgnoreCase((String) entry.getKey())==0){
					retStr[0]=(String)entry.getValue();
				}else if ("view_new_name".compareToIgnoreCase((String) entry.getKey())==0){
					retStr[1]=(String)entry.getValue();
				}else if ("view_xpath".compareToIgnoreCase((String) entry.getKey())==0){
					retStr[2]=(String)entry.getValue();
				}else if ("view_description".compareToIgnoreCase((String) entry.getKey())==0){
					retStr[3]=(String)entry.getValue();
				}else if ("view_creator".compareToIgnoreCase((String) entry.getKey())==0){
					retStr[4]=(String)entry.getValue();
				}else if ("view_email".compareToIgnoreCase((String) entry.getKey())==0){
					retStr[5]=(String)entry.getValue();
				}else if ("view_date".compareToIgnoreCase((String) entry.getKey())==0){
					retStr[6]=(String)entry.getValue();
				}else if ("view_comment".compareToIgnoreCase((String) entry.getKey())==0){
					retStr[7]=(String)entry.getValue();
				}
			}
		}catch(ParseException pe){
			System.out.println(pe);
		}
		return retStr;
	}

	/** createViewSpecificationXMLFile converts a view specification to an XML format and creates a file to save the view specification.  
	 * 
	 * @throws	JSONException, ParseException, FeatureModelException, ParserConfigurationException 	if an exception has occurred, based on its type, that interferes with the relevant handler.
	 * @param viewJSONString	the view specification in a JSON format
	 * @param fileName	the full path of the relevant view file
	 * @param featureModelName the name of the  related feature model name.
	 * @return returns true if the process is done successfully, otherwise returns the error message.   
	 */	
	private static  String createViewSpecificationXMLFile(String viewJSONString, String fileName, String featureModelName) throws JSONException, ParseException, FeatureModelException, ParserConfigurationException{
		String retValue="";
		try{
		    	String[] viewDetail= new String[8];
		    	viewDetail=getViewJSONDInArray(viewJSONString);
		    	String root="feature_model";
		    	DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
		    	DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
		    	Document document=documentBuilder.newDocument();
		    	Element rootElement=document.createElement(root);
		    	rootElement.setAttribute("name", featureModelName);
		    	document.appendChild(rootElement);
		    	
		    	
		    	
		    	Element viewSpecificationElement=document.createElement("view_specification");
		    	rootElement.appendChild(viewSpecificationElement);
		    	
		    	Element viewElement=document.createElement("view");
		    	viewElement.setAttribute("name", viewDetail[1]);
		    	viewSpecificationElement.appendChild(viewElement);
		    	
		    	
		    	Element metaElement=document.createElement("meta");
		    	viewElement.appendChild(metaElement);
		    	
		    	
		    	Element descriptionElement=document.createElement("data");
		    	
		    	descriptionElement.setAttribute("name", "description");
		    	descriptionElement.appendChild(document.createTextNode(viewDetail[3]));
		    	metaElement.appendChild(descriptionElement);
		    	
		    	
		    	Element creatorElement=document.createElement("data");
		    	creatorElement.setAttribute("name", "creator");
		    	creatorElement.appendChild(document.createTextNode(viewDetail[4]));
		    	metaElement.appendChild(creatorElement);
		    	
		    	

		    	Element emailElement=document.createElement("data");
		    	emailElement.setAttribute("name", "email");
		    	emailElement.appendChild(document.createTextNode(viewDetail[5]));
		    	metaElement.appendChild(emailElement);
		    	
		    	
		    	    	
		    	
		    	Element dateElement=document.createElement("data");
		    	dateElement.setAttribute("name", "date");
		    	dateElement.appendChild(document.createTextNode(viewDetail[6]));
		    	metaElement.appendChild(dateElement);
		    	
		    
		    	Element commentElement=document.createElement("data");
		    	commentElement.setAttribute("name", "comment");
		    	commentElement.appendChild(document.createTextNode(viewDetail[7]));
		    	metaElement.appendChild(commentElement);
		    	
		    	
		    	Element specificationElement=document.createElement("specification");
		    	specificationElement.appendChild(document.createTextNode(viewDetail[2]));
		    	viewElement.appendChild(specificationElement);
		    	
		    	/*
		    	Element taskElement=document.createElement("task");
		    	viewElement.appendChild(taskElement);
		    	
		    	Element workflowElement=document.createElement("workflow");
		    	workflowElement.setAttribute("name","");
		    	taskElement.appendChild(workflowElement);
		    	
		    	Element taskNameElement=document.createElement("task_name");
		    	workflowElement.appendChild(taskNameElement);
		    	*/
		    	
		    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
			    Transformer transformer=transformerFactory.newTransformer();
			    DOMSource source=new DOMSource(document);
			    StreamResult result=new StreamResult(fileName);
			    transformer.transform(source, result);
			    retValue="true";
			    
			    
		
		}catch (Exception e) {
			retValue=e.getMessage();
		}	
    	
    	

		return retValue;
	}
	
	/** appendToViewSpecificationXMLFile appends a new view specification to an existing file.  
	 * 
	 * @throws	JSONException, ParseException, FeatureModelException, ParserConfigurationException 	if an exception has occurred, based on its type, that interferes with the relevant handler.
	 * @param viewJSONString	the view specification in a JSON format
	 * @param fileName	the full path of the relevant view file
	 * @return returns true if the process is done successfully, otherwise returns the error message.   
	 */
	private static  String appendToViewSpecificationXMLFile(String viewJSONString, String fileName) throws JSONException, ParseException, FeatureModelException, ParserConfigurationException{
		String retValue="";
		try{
		    	String[] viewDetail= new String[8];
		    	viewDetail=getViewJSONDInArray(viewJSONString);
		    	File file = new File(fileName);	
		       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
		    	Document document = docBuilder.parse(file);
		    	
		    	Element featureModelElement=(Element) document.getFirstChild();
		    	Element viewSpecificationElement=(Element) featureModelElement.getFirstChild();
		    	
		    	
		    	Element viewElement=document.createElement("view");
		    	viewElement.setAttribute("name", viewDetail[1]);
		    	viewSpecificationElement.appendChild(viewElement);
		    	
		    	Element metaElement=document.createElement("meta");
		    	viewElement.appendChild(metaElement);
		    	Element descriptionElement=document.createElement("data");
		    	
		    	descriptionElement.setAttribute("name", "description");
		    	descriptionElement.appendChild(document.createTextNode(viewDetail[3]));
		    	metaElement.appendChild(descriptionElement);
		    	
		    	
		    	Element creatorElement=document.createElement("data");
		    	creatorElement.setAttribute("name", "creator");
		    	creatorElement.appendChild(document.createTextNode(viewDetail[4]));
		    	metaElement.appendChild(creatorElement);
		    	
		    	

		    	Element emailElement=document.createElement("data");
		    	emailElement.setAttribute("name", "email");
		    	emailElement.appendChild(document.createTextNode(viewDetail[5]));
		    	metaElement.appendChild(emailElement);
		    	
		    	
		    	    	
		    	
		    	Element dateElement=document.createElement("data");
		    	dateElement.setAttribute("name", "date");
		    	dateElement.appendChild(document.createTextNode(viewDetail[6]));
		    	metaElement.appendChild(dateElement);
		    	
		    
		    	Element commentElement=document.createElement("data");
		    	commentElement.setAttribute("name", "comment");
		    	commentElement.appendChild(document.createTextNode(viewDetail[7]));
		    	metaElement.appendChild(commentElement);
		    	Element specificationElement=document.createElement("specification");
		    	specificationElement.appendChild(document.createTextNode(viewDetail[2]));
		    	viewElement.appendChild(specificationElement);
		    	
		    	/*
		    	Element taskElement=document.createElement("task");
		    	viewElement.appendChild(taskElement);
		    	
		    	
		    	Element workflowElement=document.createElement("workflow");
		    	workflowElement.setAttribute("name","");
		    	taskElement.appendChild(workflowElement);
		    	
		    	Element taskNameElement=document.createElement("task_name");
		    	workflowElement.appendChild(taskNameElement);
				*/
		    
		    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
			    Transformer transformer=transformerFactory.newTransformer();
			    DOMSource source=new DOMSource(document);
			    StreamResult result=new StreamResult(file);
			    transformer.transform(source, result);
			    retValue="true";
		
		}catch (Exception e) {
			e.printStackTrace();
			//retValue=e.getMessage();
			
		}	
		return retValue;
	}
	
	
	/** JSONStringValidation checks the JSON string to be sure that that is a valid string and all mandatory fields are filled. 
	 * 
	 * @param JSONInString	the view specification in a JSON format
	 * @return returns true if the string is valid, otherwise returns the error messages.    
	 */
	private static String JSONStringValidation(String JSONInString){
		String retValue="";
		String[] viewInDetail= new String[8];
		if ((JSONInString.isEmpty()) || (JSONInString=="") || (JSONInString==null)){
			retValue="The request string is invalid.";
			return retValue;
		}
		
		try {
			viewInDetail=getViewJSONDInArray(JSONInString);
			if ((viewInDetail[1].isEmpty()) || (viewInDetail[1]==null) || (viewInDetail[1]=="")){
				retValue="You must fill out mandatory fields: View name, XPath expression, Creator and Email.\n";
			}
			
			if ((viewInDetail[2].isEmpty()) || (viewInDetail[2]==null) || (viewInDetail[2]=="")){
				retValue="You must fill out mandatory fields: View name, XPath expression, Creator and Email.\n";
			}

			if ((viewInDetail[4].isEmpty()) || (viewInDetail[4]==null) || (viewInDetail[4]=="")){
				retValue="You must fill out mandatory fields: View name, XPath expression, Creator and Email.\n";
			}
			
			if ((viewInDetail[5].isEmpty()) || (viewInDetail[5]==null) || (viewInDetail[5]=="")){
				retValue="You must fill out mandatory fields: View name, XPath expression, Creator and Email.\n";
			}
			
			
			


			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeatureModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (retValue==""){
			retValue="true";
		}
		return retValue;
		
	}
	
	
	/** requestValidation validates the request to find any incorrect data sent by the client. This also uses JSONStringValidation to validate the JSON string.
	 * 
	 * @param request	a  HttpServletRequest object containing the request received from the client
	 * @param modelsPath	a string containing the real path to the models folder
	 * @return returns true if the sent data is correct, otherwise, returns the error message.      
	 */
	private static String requestValidation(HttpServletRequest request, String modelsPath ){
		String retValue="true";
    	String featureModelFileName=(String)request.getParameter("feature_model_file_name");
    	if ((featureModelFileName=="") || (featureModelFileName.isEmpty()) ||(featureModelFileName.endsWith(".xml")==false)){
    		retValue="The feature model\'s file name is invalid.\n";
    		return retValue;
    		
    	}
    	
    	File file = new File(modelsPath+"/"+featureModelFileName);
    	if (!file.exists()){
    		retValue="The feature model\'s file name not found in the models path.";
    		return retValue;
    	}

    	String viewJSONString = request.getParameter("view_in_json");
    	String JSONValidationResult=JSONStringValidation(viewJSONString);
    	retValue=JSONValidationResult;
		return retValue;
		
	}

	/** checkViewExistingInSpecification checks a view file to find is there a view with special name or not?
	 * 
	 * @param filePath	  a full path of a view file name
	 * @param viewName	  a name of a view which its existence should be checked.
	 * @return returns true if there is a view same as the viewName parameter, otherwise, returns false.       
	 */
	private static boolean checkViewExistingInSpecification(String filePath, String viewName) throws ParserConfigurationException, SAXException, IOException{
		boolean result=false;
		File file = new File(filePath);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(file);
			
		NodeList  viewNodes = doc.getElementsByTagName("view"); 
		for (int i = 0; i < viewNodes.getLength(); i++){
			Element element = (Element) viewNodes.item(i);
			if (element.getAttribute("name").trim().compareToIgnoreCase(viewName)==0){
				result=true;
				return result;
			}
				
			}
		return result;
		
		
	}

	/** UpdateExistingViewInformationInXMLFile updates the existing view specification.  
	 * 
	 * @throws	JSONException, ParseException, FeatureModelException, ParserConfigurationException 	if an exception has occurred, based on its type, that interferes with the relevant handler.
	 * @param viewJSONString	the view specification in a JSON format
	 * @param fileName	the full path of the relevant view file
	 * @return returns true if the process is done successfully, otherwise returns the error message.   
	 */
	private static  String UpdateExistingViewInformationInXMLFile(String viewJSONString, String fileName) throws JSONException, ParseException, FeatureModelException, ParserConfigurationException{
		String retValue="";
		try{
		    	String[] viewDetail= new String[8];		    	
		    	viewDetail=getViewJSONDInArray(viewJSONString);
		    	String viewOldName=viewDetail[0];
		    	String viewNewName=viewDetail[1];
		    	
		    	
		    	File file = new File(fileName);	
		      	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
		    	Document document = docBuilder.parse(file);
		    	
		    	
		    	Element featureModelElement=(Element) document.getFirstChild();
		    	Element viewSpecificationElement=(Element) featureModelElement.getFirstChild();

				NodeList  viewNodes = viewSpecificationElement.getElementsByTagName("view"); 
				for (int i = 0; i < viewNodes.getLength(); i++){
					Element element = (Element) viewNodes.item(i);
					if (element.getAttribute("name").trim().compareToIgnoreCase(viewOldName)==0){
					
						
						NodeList  preTaskNodes = element.getElementsByTagName("task"); 
						Boolean preTaskElementExist=false;
						Element preTaskElement=null;
						if (preTaskNodes.getLength()>0){
							 preTaskElement=(Element) (Element) preTaskNodes.item(0);
							 preTaskElementExist=true;
						}
						
						viewSpecificationElement.removeChild(element);
						
				    	Element viewElement=document.createElement("view");
				    	viewElement.setAttribute("name", viewNewName);
				    	viewSpecificationElement.appendChild(viewElement);
				    	
				    	Element metaElement=document.createElement("meta");
				    	viewElement.appendChild(metaElement);
				    	Element descriptionElement=document.createElement("data");
				    	
				    	descriptionElement.setAttribute("name", "description");
				    	descriptionElement.appendChild(document.createTextNode(viewDetail[3]));
				    	metaElement.appendChild(descriptionElement);
				    	
				    	
				    	Element creatorElement=document.createElement("data");
				    	creatorElement.setAttribute("name", "creator");
				    	creatorElement.appendChild(document.createTextNode(viewDetail[4]));
				    	metaElement.appendChild(creatorElement);
				    	
				    	

				    	Element emailElement=document.createElement("data");
				    	emailElement.setAttribute("name", "email");
				    	emailElement.appendChild(document.createTextNode(viewDetail[5]));
				    	metaElement.appendChild(emailElement);
				    	
				    	
				    	    	
				    	
				    	Element dateElement=document.createElement("data");
				    	dateElement.setAttribute("name", "date");
				    	dateElement.appendChild(document.createTextNode(viewDetail[6]));
				    	metaElement.appendChild(dateElement);
				    	
				    
				    	Element commentElement=document.createElement("data");
				    	commentElement.setAttribute("name", "comment");
				    	commentElement.appendChild(document.createTextNode(viewDetail[7]));
				    	metaElement.appendChild(commentElement);
				    	Element specificationElement=document.createElement("specification");
				    	specificationElement.appendChild(document.createTextNode(viewDetail[2]));
				    	viewElement.appendChild(specificationElement);
				    	
				    	if (preTaskElementExist){
				    	viewElement.appendChild(preTaskElement);
				    	}
				    	/*
				    	Element taskElement=document.createElement("task");
				    	viewElement.appendChild(taskElement);
				    	
				    	Element workflowElement=document.createElement("workflow");
				    	workflowElement.setAttribute("name","");
				    	taskElement.appendChild(workflowElement);
				    	
				    	Element taskNameElement=document.createElement("task_name");
				    	workflowElement.appendChild(taskNameElement);
						*/
						
						
				    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
					    Transformer transformer=transformerFactory.newTransformer();
					    DOMSource source=new DOMSource(document);
					    StreamResult result=new StreamResult(file);
					    transformer.transform(source, result);
					    retValue="true";
					    

					}

				}

		
		}catch (Exception e) {
			retValue=e.getMessage();
		}	
    	
    	

		return retValue;
	}

		
}


