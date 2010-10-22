package splot.services.extensions.fundp.handlers.conf;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.GroupedFeature;
import splar.core.fm.SolitaireFeature;
import splar.core.fm.configuration.ConfigurationEngine;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

public class FCWInteractiveConfigurationExportConfigurationToFileHandler extends FreeMarkerHandler{
	public FCWInteractiveConfigurationExportConfigurationToFileHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

        try {	
        	
        	
     		String configuredModelsPath=getServlet().getServletContext().getRealPath("/")+ "models/configured_models"; 
     		String newServerKey="";
        	
        	HttpSession session = request.getSession(true);	        	
        	ConfigurationEngine confEngine = (ConfigurationEngine)session.getAttribute("conf_engine");
        	String user=(String)session.getAttribute("user");
        	String viewName=(String)session.getAttribute("viewName");
        	String viewType=(String)session.getAttribute("viewType");
        	String task=(String)session.getAttribute("task");
        	String workflow=(String)session.getAttribute("workflow");
        	String featureModelFileName=(String)session.getAttribute("modelFileName");
        	HttpServletResponse mainResponse=(HttpServletResponse)session.getAttribute("response");
        	HttpServletRequest mainRequest=(HttpServletRequest)session.getAttribute("request");
        	String featureModelName=(String)session.getAttribute("featureModelName");
        	String processStatus=(String)session.getAttribute("processStatus");
        	String workflowExistence=(String)session.getAttribute("workflowExistence");
        	
        	String userKey=(String)session.getAttribute("userKey");
        	String serverKey=(String)session.getAttribute("serverKey");
        	String configuredFileName = null;
        	String result=null;

        	if (processStatus.compareToIgnoreCase("true")==0){
    			throw new HandlerExecutionException("You have already saved this file ");

        	}
        	
        	
        	
        	
        	if ((serverKey.isEmpty()) || (serverKey=="") || (serverKey==null)){
        		
        		FeatureModel model = confEngine.getModel();
	    		if (confEngine == null) {
	    			throw new HandlerExecutionException("Configuration engine must be created first");
	    		}
	    		
				 try{
					
	    			 configuredFileName=Methods.createConfiguredModelFileName(featureModelFileName);
				 }catch (Exception e) {
	    			throw new HandlerExecutionException("Problem in creating configuration file");

				 }
				
				 
	            result=createConfigurationFile(featureModelName, configuredFileName,featureModelFileName,configuredModelsPath, user, "created", workflow, task, session.getId(),userKey);
	            newServerKey=getConfigurationFileServerKeyValue(configuredFileName, configuredModelsPath);
	            serverKey=newServerKey;
				if (result.compareToIgnoreCase("false")==0){
	    			throw new HandlerExecutionException("Problem in creating configuration file");

				}else{
		        	templateModel.put("modelName", model.getName());
		        	
		        	List features = new LinkedList();
		        	for( FeatureTreeNode featureNode : model.getNodes() ) {
		        		if ( featureNode.isInstantiated() ) {
			        		Map featureData = new HashMap();
			        		
			        		String decisionType=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionType");
			        		String decisionStep=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionStep");
			        		
			        		result=appendConfigurationFileFeatureInfo(configuredFileName, configuredModelsPath, user,Integer.toString(featureNode.getValue()), decisionType, decisionStep, task, session.getId(), viewName, featureNode.getID(), "true");
			    			featureData.put("id", featureNode.getID());
			    			featureData.put("name", getFeatureName(featureNode));
			    			featureData.put("type", getFeatureType(featureNode));
			    			featureData.put("value", ""+featureNode.getValue());
			    			featureData.put("decisionType", featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionType"));   // manual, propagated, auto-completion
			    			featureData.put("decisionStep", featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionStep"));   
			        		features.add(featureData);
			        		
		        		}
		        	}
		        	session.setAttribute("responseReady", "true");
		    		session.setAttribute("serverKey", newServerKey);

		        	templateModel.put("features", features);
				}
				
				
				
				
				
				
				
				
				

        	}else{
        		
        		configuredFileName=getConfiguredFileName(configuredModelsPath, serverKey);
        		if (configuredFileName.compareToIgnoreCase("false")==0){
	    			throw new HandlerExecutionException("Problem in finding configuration file");

        		}else{
        			
					FeatureModel model = confEngine.getModel();
		    		if (confEngine == null) {
		    			throw new HandlerExecutionException("Configuration engine must be created first");
		    		}
		        	
		    		result=appendConfigurationFile(configuredFileName, configuredModelsPath, user, "appended", workflow, task, session.getId());
		        	templateModel.put("modelName", model.getName());
		        	
		        	List features = new LinkedList();

		        	for( FeatureTreeNode featureNode : model.getNodes() ) {
		        		if ( featureNode.isInstantiated() ) {
		        			
		        			
		        			
			        		Map featureData = new HashMap();
			        		
			        		String decisionType=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionType");
			        		String decisionStep=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionStep");
			        		
			        		result=appendConfigurationFileFeatureInfo(configuredFileName, configuredModelsPath, user,Integer.toString(featureNode.getValue()), decisionType, decisionStep, task, session.getId(), viewName, featureNode.getID(), "true");

			    			featureData.put("id", featureNode.getID());
			    			featureData.put("name", getFeatureName(featureNode));
			    			featureData.put("type", getFeatureType(featureNode));
			    			featureData.put("value", ""+featureNode.getValue());
			    			featureData.put("decisionType", featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionType"));   // manual, propagated, auto-completion
			    			featureData.put("decisionStep", featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionStep"));   
			        		features.add(featureData);
			        		

			        		
		        		}
		        	}
		        	session.setAttribute("response_ready", "true");
		        	templateModel.put("features", features);

				
        		}
        		
        	}
        	
        	
        	

        
	        	
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Configuration engine must be created first", e);
		}
	}
	
	
	
	
	
	protected static String createConfigurationFile(String featureModelName,String configuredFileName,String featureModelFileName ,String configuredModelsPath, String user, String action, String workflow, String task, String session , String userKey) throws ParserConfigurationException{
		String retValue="false";
		
    	try {
    			
    			
    			String fileName=configuredModelsPath+"/"+ configuredFileName; 
	    		// feature_model 
	    		String root="feature_model";
	        	DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
	        	DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
	        	Document document=documentBuilder.newDocument();
	        	Element rootElement=document.createElement(root);
	        	rootElement.setAttribute("name", featureModelName);
	        	rootElement.setAttribute("id", featureModelFileName);
	        	rootElement.setAttribute("server_key", configuredFileName);
	        	rootElement.setAttribute("user_key", userKey);

	        	rootElement.setAttribute("edtiable", "true");
	        	
	        	document.appendChild(rootElement);
	        	
	        	//configuration_info
	        	Element configurationInfoElement=document.createElement("configuration_info");
	        	rootElement.appendChild(configurationInfoElement);
	        	
	        	Element dateElement=document.createElement("date");
	        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
	        	configurationInfoElement.appendChild(dateElement);
	        	
	        	
	        	Element workflowElement=document.createElement("workflow");
	        	workflowElement.appendChild(document.createTextNode(workflow));
	        	configurationInfoElement.appendChild(workflowElement); 
	        	
	        	Element userElement=document.createElement("user");
	        	userElement.appendChild(document.createTextNode(user));
	        	configurationInfoElement.appendChild(userElement);
	        	
	        	Element actionElement=document.createElement("action");
	        	actionElement.appendChild(document.createTextNode("create"));
	        	configurationInfoElement.appendChild(actionElement);
	        	
	        	
	        	
	        	Element taskElement=document.createElement("task");
	        	taskElement.appendChild(document.createTextNode(task));
	        	configurationInfoElement.appendChild(taskElement);
	        	
	        	Element sessionElement=document.createElement("session");
	        	sessionElement.appendChild(document.createTextNode(session));
	        	configurationInfoElement.appendChild(sessionElement);
	        	
	        	
		    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
			    Transformer transformer=transformerFactory.newTransformer();
			    DOMSource source=new DOMSource(document);
			    StreamResult result=new StreamResult(fileName);
			    transformer.transform(source, result);
			    retValue="true";

	        	
	        	
		} catch (Exception e) {
			retValue=e.getMessage();
		}

		return retValue;
	}
	

	
	
	protected static String getConfigurationFileEditableValue(String configuredFeatureModelFileName,String configuredModelsPath){
	String retValue="false";
		
    	try {
    		
			File file = new File(configuredModelsPath+"/"+configuredFeatureModelFileName);	
			if (!(file.exists())){
				retValue="File not found!";
				return retValue;
			}
			
			
	       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
	    	Document document = docBuilder.parse(file);
	    	
	    	Element featureModelElement=(Element) document.getFirstChild();
	    	String fileEditable;
	    	try {
		    	fileEditable=(String)featureModelElement.getAttribute("editable");
			} catch (Exception e) {
				retValue=e.getMessage();
				return retValue;
			}
	    	
	    	if ((fileEditable==null)|| (fileEditable=="")){
	    		retValue="The required attribute not found!";
	    		return retValue;
	    	}
	    	
	    	if (fileEditable.compareToIgnoreCase("true")==0){
	    		retValue="true";
	    	}else{
	    		retValue="false";
	    	}
		} catch (Exception e) {
			retValue=e.getMessage();
		}
		
		return retValue;
	}
	
	
	
	protected static String getConfigurationFileServerKeyValue(String configuredFeatureModelFileName,String configuredModelsPath){
		String retValue="false";
			
	    	try {
	    		
				File file = new File(configuredModelsPath+"/"+configuredFeatureModelFileName);	
				
				if (!(file.exists())){
					retValue="false";
					return retValue;
				}

		       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
		    	Document document = docBuilder.parse(file);
		    	
		    	Element featureModelElement=(Element) document.getFirstChild();
		    	
		    	
		    	try {
		    		retValue=(String)featureModelElement.getAttribute("server_key");
				} catch (Exception e) {
					retValue="false";
				}
		    	
		    	
		    	
			} catch (Exception e) {
				retValue="false";
			}
			
			return retValue;
		}
		
	
	
	protected static String setConfigurationFileEditableValue(String configuredFeatureModelFileName,String configuredModelsPath,String editable){
		String retValue="false";
			
	    	try {
	    		
				File fileName = new File(configuredModelsPath+"/"+configuredFeatureModelFileName);	
				if (!(fileName.exists())){
					retValue="File not found!";
					return retValue;
				}

				
		       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
		    	Document document = docBuilder.parse(fileName);
		    	
		    	Element featureModelElement=(Element) document.getFirstChild();
		    	featureModelElement.setAttribute("editable", editable);
		    	
		    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
			    Transformer transformer=transformerFactory.newTransformer();
			    DOMSource source=new DOMSource(document);
			    StreamResult result=new StreamResult(fileName);
			    transformer.transform(source, result);
			    retValue="true";
			} catch (Exception e) {
				retValue=e.getMessage();
			}
			
			return retValue;
		}
	

	
	
	
	
	
	protected static String appendConfigurationFile(String configuredFeatureModelFileName,String configuredModelsPath, String user, String action, String workflow, String task, String session) throws ParserConfigurationException{
		String retValue="false";
		
    	try {
    		
    		
    		
    			File fileName = new File(configuredModelsPath+"/"+configuredFeatureModelFileName);	
		       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
		    	Document document = docBuilder.parse(fileName);
		    	
		    	Element featureModelElement=(Element) document.getFirstChild();
		    	
	        	
	        	//configuration_info
	        	Element configurationInfoElement=document.createElement("configuration_info");
	        	featureModelElement.appendChild(configurationInfoElement);
	        	
	        	Element dateElement=document.createElement("date");
	        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
	        	configurationInfoElement.appendChild(dateElement);
	        	
	        	
	        	Element workflowElement=document.createElement("workflow");
	        	workflowElement.appendChild(document.createTextNode(workflow));
	        	configurationInfoElement.appendChild(workflowElement); 
	        	
	        	Element userElement=document.createElement("user");
	        	userElement.appendChild(document.createTextNode(user));
	        	configurationInfoElement.appendChild(userElement);
	        	
	        	Element actionElement=document.createElement("action");
	        	actionElement.appendChild(document.createTextNode("create"));
	        	configurationInfoElement.appendChild(actionElement);
	        	
	        	
	        	
	        	Element taskElement=document.createElement("task");
	        	taskElement.appendChild(document.createTextNode(task));
	        	configurationInfoElement.appendChild(taskElement);
	        	
	        	Element sessionElement=document.createElement("session");
	        	sessionElement.appendChild(document.createTextNode(session));
	        	configurationInfoElement.appendChild(sessionElement);
	        	
	        	
		    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
			    Transformer transformer=transformerFactory.newTransformer();
			    DOMSource source=new DOMSource(document);
			    StreamResult result=new StreamResult(fileName);
			    transformer.transform(source, result);
			    retValue="true";

	        	
	        	
		} catch (Exception e) {
			retValue=e.getMessage();
		}

		return retValue;
	}
	
	
	
	
	protected static String appendConfigurationFileFeatureInfo(String configuredFeatureModelFileName,String configuredModelsPath, String user, String decisionValue, String decisionType,String decisionStep, String task, String session, String viewName , String featureID , String editable) throws ParserConfigurationException{
		
		String retValue="false";
		Boolean featureFound=false;
		
    	try {
   			File fileName = new File(configuredModelsPath+"/"+configuredFeatureModelFileName);	
    			
				if (!(fileName.exists())){
					retValue="File not found!";
					return retValue;
				}

		       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
		    	Document document = docBuilder.parse(fileName);
		    	
		    	Element featureModelElement=(Element) document.getFirstChild();
		    	
	        	
	        	//feature_info
		    	
		    	NodeList   featureNodes=featureModelElement.getElementsByTagName("feature");
		    	int featureNodesLength=featureNodes.getLength();
		    	
		    	if (featureNodesLength>=1){
						for (int j = 0; (j < featureNodes.getLength()) && (!featureFound); j++){
   							Element featureElement = (Element) featureNodes.item(j);
   							String featureElementID=(String)featureElement.getAttribute("id");
   							
   							if (featureElementID.compareToIgnoreCase(featureID)==0){
   								featureElement.setAttribute("editable",editable); 
   								
   						    	Element decisionElement=document.createElement("decision_info");
   						    	featureElement.appendChild(decisionElement);
   					        	
   					        	Element dateElement=document.createElement("date");
   					        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
   					        	decisionElement.appendChild(dateElement);
   					        	
   					        	Element userElement=document.createElement("user");
   					        	userElement.appendChild(document.createTextNode(user));
   					        	decisionElement.appendChild(userElement);
   					        	
   					        	Element taskElement=document.createElement("task");
   					        	taskElement.appendChild(document.createTextNode(task));
   					        	decisionElement.appendChild(taskElement);
   					        	
   					        	Element viewElement=document.createElement("view");
   					        	viewElement.appendChild(document.createTextNode(viewName));
   					        	decisionElement.appendChild(viewElement);
   					        	
   					        	Element valueElement=document.createElement("value");
   					        	valueElement.appendChild(document.createTextNode(decisionValue));
   					        	decisionElement.appendChild(valueElement);
   					        	
   					        	Element typeElement=document.createElement("type");
   					        	typeElement.appendChild(document.createTextNode(decisionType));
   					        	decisionElement.appendChild(typeElement);
   					        	
   					        	Element stepElement=document.createElement("step");
   					        	stepElement.appendChild(document.createTextNode(decisionStep));
   					        	decisionElement.appendChild(stepElement);
   					        	
   					        	Element sessionElement=document.createElement("session");
   					        	sessionElement.appendChild(document.createTextNode(session));
   					        	decisionElement.appendChild(sessionElement);
   					        	
   					        	featureFound=true;
   							}
						}
		    		
					if (!featureFound){
				    	Element featureElement=document.createElement("feature");
				    	featureElement.setAttribute("id", featureID);
				    	featureElement.setAttribute("editable", editable);
				    	featureModelElement.appendChild(featureElement);
				    	
				    	Element decisionElement=document.createElement("decision_info");
				    	featureElement.appendChild(decisionElement);
			        	
			        	Element dateElement=document.createElement("date");
			        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
			        	decisionElement.appendChild(dateElement);
			        	
			        	Element userElement=document.createElement("user");
			        	userElement.appendChild(document.createTextNode(user));
			        	decisionElement.appendChild(userElement);
			        	
			        	Element taskElement=document.createElement("task");
			        	taskElement.appendChild(document.createTextNode(task));
			        	decisionElement.appendChild(taskElement);
			        	
			        	Element viewElement=document.createElement("view");
			        	viewElement.appendChild(document.createTextNode(viewName));
			        	decisionElement.appendChild(viewElement);
			        	
			        	Element valueElement=document.createElement("value");
			        	valueElement.appendChild(document.createTextNode(decisionValue));
			        	decisionElement.appendChild(valueElement);
			        	
			        	Element typeElement=document.createElement("type");
			        	typeElement.appendChild(document.createTextNode(decisionType));
			        	decisionElement.appendChild(typeElement);
			        	
			        	Element stepElement=document.createElement("step");
			        	stepElement.appendChild(document.createTextNode(decisionStep));
			        	decisionElement.appendChild(stepElement);
			        	
			        	Element sessionElement=document.createElement("session");
			        	sessionElement.appendChild(document.createTextNode(session));
			        	decisionElement.appendChild(sessionElement);
					}
		    		
		    	}else{
			    	Element featureElement=document.createElement("feature");
			    	featureElement.setAttribute("id", featureID);
			    	featureElement.setAttribute("editable", editable);
			    	featureModelElement.appendChild(featureElement);
			    	
			    	Element decisionElement=document.createElement("decision_info");
			    	featureElement.appendChild(decisionElement);
		        	
		        	Element dateElement=document.createElement("date");
		        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
		        	decisionElement.appendChild(dateElement);
		        	
		        	Element userElement=document.createElement("user");
		        	userElement.appendChild(document.createTextNode(user));
		        	decisionElement.appendChild(userElement);
		        	
		        	Element taskElement=document.createElement("task");
		        	taskElement.appendChild(document.createTextNode(task));
		        	decisionElement.appendChild(taskElement);
		        	
		        	Element viewElement=document.createElement("view");
		        	viewElement.appendChild(document.createTextNode(viewName));
		        	decisionElement.appendChild(viewElement);
		        	
		        	Element valueElement=document.createElement("value");
		        	valueElement.appendChild(document.createTextNode(decisionValue));
		        	decisionElement.appendChild(valueElement);
		        	
		        	Element typeElement=document.createElement("type");
		        	typeElement.appendChild(document.createTextNode(decisionType));
		        	decisionElement.appendChild(typeElement);
		        	
		        	Element stepElement=document.createElement("step");
		        	stepElement.appendChild(document.createTextNode(decisionStep));
		        	decisionElement.appendChild(stepElement);
		        	
		        	Element sessionElement=document.createElement("session");
		        	sessionElement.appendChild(document.createTextNode(session));
		        	decisionElement.appendChild(sessionElement);
		    	}
		    	
		    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
			    Transformer transformer=transformerFactory.newTransformer();
			    DOMSource source=new DOMSource(document);
			    StreamResult result=new StreamResult(fileName);
			    transformer.transform(source, result);
			    retValue="true";

	        	
	        	
		} catch (Exception e) {
			retValue=e.getMessage();
		}

		return retValue;
	}
	
	
	
	protected static String getConfigurationFileFeatureEditable(String configuredFeatureModelFileName,String configuredModelsPath, String featureID) throws ParserConfigurationException{
		String retValue="unknown";
		Boolean featureFound=false;

		try {
   			File fileName = new File(configuredModelsPath+"/"+configuredFeatureModelFileName);	
			
			if (!(fileName.exists())){
				retValue="File not found!";
				return retValue;
			}
			
	       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
	    	Document document = docBuilder.parse(fileName);
	    	
	    	Element featureModelElement=(Element) document.getFirstChild();
	    	
        	
	    	
	    	NodeList   featureNodes=featureModelElement.getElementsByTagName("feature");
	    	int featureNodesLength=featureNodes.getLength();
	    	if (featureNodesLength>=1){
				for (int j = 0; (j < featureNodes.getLength()) && (!featureFound); j++){
					Element featureElement = (Element) featureNodes.item(j);
					String featureElementID=(String)featureElement.getAttribute("id");
					if (featureElementID.compareToIgnoreCase(featureID)==0){
						try {
							retValue=featureElement.getAttribute("editable");
							featureFound=true;
							return retValue;
						} catch (Exception e) {
							retValue="The required attribute not found!";
							return retValue;
						}
					}

				}
				
				if (!featureFound){
					retValue="The required feature not found!";
					return retValue;
				}
	    		
	    	}else{
	    		retValue="Feature not found!";
	    		return retValue;
	    	}
		} catch (Exception e) {
			retValue=e.getMessage();
		}
		
		return retValue;
	}
	
	
	protected static String setConfigurationFileFeatureEditable(String configuredFeatureModelFileName,String configuredModelsPath, String featureID, String editable) throws ParserConfigurationException{
		String retValue="unknown";
		Boolean featureFound=false;

		try {
   			File fileName = new File(configuredModelsPath+"/"+configuredFeatureModelFileName);	
			
			if (!(fileName.exists())){
				retValue="File not found!";
				return retValue;
			}
			
	       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
	    	Document document = docBuilder.parse(fileName);
	    	
	    	Element featureModelElement=(Element) document.getFirstChild();
	    	
        	
	    	
	    	NodeList   featureNodes=featureModelElement.getElementsByTagName("feature");
	    	int featureNodesLength=featureNodes.getLength();
	    	if (featureNodesLength>=1){
				for (int j = 0; (j < featureNodes.getLength()) && (!featureFound); j++){
					Element featureElement = (Element) featureNodes.item(j);
					String featureElementID=(String)featureElement.getAttribute("id");
					if (featureElementID.compareToIgnoreCase(featureID)==0){
						try {
							featureElement.setAttribute("editable", editable);
							featureFound=true;
					    	
					    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
						    Transformer transformer=transformerFactory.newTransformer();
						    DOMSource source=new DOMSource(document);
						    StreamResult result=new StreamResult(fileName);
						    transformer.transform(source, result);
						    retValue="true";
							return retValue;
						} catch (Exception e) {
							return e.getMessage();
						}
					}

				}
				
				if (!featureFound){
					retValue="The required feature not found!";
					return retValue;
				}
	    		
	    	}else{
	    		retValue="Feature not found!";
	    		return retValue;
	    	}
		} catch (Exception e) {
			retValue=e.getMessage();
		}
		
		return retValue;
	}
	
	
	
	
   protected String  getConfiguredFileName(String configuredModelsPath,String serverKey){
	   String retVal="false";
	   	try {
			File  dir=new File(configuredModelsPath);
			String[]  childeren=dir.list();
					for (int i=0;i<childeren.length;i++){
						if (childeren!=null){
							if (childeren[i].endsWith(".xml") !=false){
		 						String  fileName=childeren[i];
		       					File confFile = new File(configuredModelsPath+fileName);
		       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		       					Document doc = builder.parse(confFile);
		       					
		       					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  
		       					Element  featureModelElement = (Element) featureModelNodes.item(0);
		       					String   FMName=featureModelElement.getAttribute("name");
		       					if (serverKey.compareToIgnoreCase(featureModelElement.getAttribute("server_key"))==0){
		       						retVal=fileName;
		       						return retVal;
		       					}



							}

						}

					}


		} catch (Exception e) {
			retVal="false";
		}
	   	
	   	
	   
	   return retVal;
   }
	
	protected String getFeatureParent(FeatureTreeNode feature) {
		FeatureTreeNode parent = (FeatureTreeNode)feature.getParent();
		if ( parent == null ) {
			return "";
		}
		return parent.getID();		
	}
	
	protected String getFeatureName( FeatureTreeNode feature ) {
		if ( feature instanceof FeatureGroup ) {
			int min = ((FeatureGroup)feature).getMin();
			int max = ((FeatureGroup)feature).getMax();
			max = ( max==-1 ) ? feature.getChildCount() : max; 
			return "[" + min + ".." + max +"]";
		}
		return feature.getName();
	}
	
	protected String getFeatureType( FeatureTreeNode feature ) {
		if ( feature.isRoot() ) { 
			return "templateModel";				
		}
		else if ( feature instanceof SolitaireFeature) {
			if (((SolitaireFeature)feature).isOptional()) {
				return "optional";
			}
			else {
				return "mandatory";
			}
		}
		else if ( feature instanceof FeatureGroup ){
			return "group";
		}
		else if ( feature instanceof GroupedFeature ){
			return "grouped";
		}
		return "error";
	}	

}
