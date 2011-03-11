package splot.services.extensions.fundp.handlers.conf;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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

import org.json.simple.JSONValue;
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
import splot.core.Handler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

public class FCWInstanceInteractiveConfigurationExportConfigurationToFileHandler extends Handler{
	public FCWInstanceInteractiveConfigurationExportConfigurationToFileHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
	}
	
	
	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
			
		
 				String retValue="";
 				String result="";
 				Map jsonObj=new LinkedHashMap();
		     	ServletConfig config = getServlet().getServletConfig();
		 	    ServletContext sc = config.getServletContext();

		 	    String userKey=request.getParameter("userKey");
		 	   

 				
		  try {	
			  
	    		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; //getServlet().getInitParameter("viewFilesPath");
	    		String modelDir=getServlet().getInitParameter("modelsPath");
				String configuredModelsPath=getServlet().getServletContext().getRealPath("/")+ "models/configured_models"; 
		    	
		 	    
		 	    
		 	    
		 	    String userName=request.getParameter("userName");
		 	    if(userName==null){
		 	    	sc.setAttribute(userKey+"_lock", "free");
		        	throw new HandlerExecutionException("Paremeter 'user name' is missing");

		 	    }
		 	    
		 	    
		 	    
			     String workflow=request.getParameter("workflowName");
			 	 if(workflow==null){
			 	    	sc.setAttribute(userKey+"_lock", "free");
			        	throw new HandlerExecutionException("Paremeter 'workflow name' is missing");
			 	  }
			 	    
			 	 String featureModelName=request.getParameter("modelName");
			 	 if(featureModelName==null){
			 	    	sc.setAttribute(userKey+"_lock", "free");
			        	throw new HandlerExecutionException("Paremeter 'feature model  name' is missing");
			 	  }   
				
				 String task=request.getParameter("taskName");
			 	 if(task==null){
			 	    	sc.setAttribute(userKey+"_lock", "free");
			        	throw new HandlerExecutionException("Paremeter 'taskName' is missing");
			 	  }  
    				
	      		 String viewType=(String)request.getParameter("viewType");
	       		 if(viewType==null){
	       			sc.setAttribute(userKey+"_lock", "free");
	 	        	throw new HandlerExecutionException("Paremeter 'view type' is missing");
	       		 }
	       		 
	    		 String viewName=(String)request.getParameter("viewName");
	    		 if(viewName==null){
	    			 sc.setAttribute(userKey+"_lock", "free");
	 	        	throw new HandlerExecutionException("Paremeter 'view name' is missing");

	    		 }
	    		 
	    		 String userID=(String)request.getParameter("userID");
	    		 if(userID==null){
	    			 sc.setAttribute(userKey+"_lock", "free");
	 	        	throw new HandlerExecutionException("Paremeter 'user ID' is missing");

	    		 }
	    		 
	    		 String featureModelFileName=(String)request.getParameter("selectedModels");
	    		 if(featureModelFileName==null){
	    			 sc.setAttribute(userKey+"_lock", "free");
	 	        	throw new HandlerExecutionException("Paremeter 'feature model file name' is missing");

	    		 }

    			
        			
	    		ConfigurationEngine confEngine=null;
	  	        if ((ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine")==null){
	  	        	sc.setAttribute(userKey+"_lock", "free");
	  	        	throw new HandlerExecutionException("Problem loading configuration engine");
	  	        }else{
	 	 			confEngine=(ConfigurationEngine)sc.getAttribute(userKey+"_conf_engine");
	 	 			
			    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			    	HttpSession session = request.getSession(true);	 
					java.util.Date date = new Date(session.getCreationTime());
					String strDate=  (dateFormat.format(date));
					
	        		String configuredFileName=Methods.getConfiguredFileName(configuredModelsPath, userKey);
	        		
	        		String uncoveredFeatures=Methods.getFeatureModelUncoveredFeaturesInAllocatedViews(featureModelName, viewDir, modelDir);
	        		if ((uncoveredFeatures!="") && (uncoveredFeatures!=null)){
	        			sc.setAttribute(userKey+"_lock", "free");
	        			
		        		jsonObj.put("result", "error");
		        		jsonObj.put("value", "There are uncovered features in the views, so the configuration cannot be saved");
		        		jsonObj.put("user_key", userKey);
		        		
		        		retValue = JSONValue.toJSONString(jsonObj);
		        	}

	 	 			
	        		
	        		if (configuredFileName.compareToIgnoreCase("false")==0){
		        		FeatureModel model = confEngine.getModel();
			    		if (confEngine == null) {
			    			sc.setAttribute(userKey+"_lock", "free");
			    			jsonObj.put("result", "error");
			        		jsonObj.put("value", "Configuration engine must be created first");
			        		jsonObj.put("user_key", userKey);
			        		retValue = JSONValue.toJSONString(jsonObj);
			    			response.getWriter().write(retValue);
			    		}
			    		
						 try{
							
			    			 configuredFileName=Methods.createConfiguredModelFileName(featureModelFileName);
						 }catch (Exception e) {
							    sc.setAttribute(userKey+"_lock", "free");
							 	jsonObj.put("result", "error");
				        		jsonObj.put("value", "Problem in creating configuration file name");
				        		jsonObj.put("user_key", userKey);
				        		retValue = JSONValue.toJSONString(jsonObj);
				    			response.getWriter().write(retValue);
						 }
	        		

						   result=createConfigurationFile(featureModelName, configuredFileName,featureModelFileName,configuredModelsPath, userName,userID, workflow, task,"task", strDate,userKey,"configured");
							if (result.compareToIgnoreCase("false")==0){
							    sc.setAttribute(userKey+"_lock", "free");

								jsonObj.put("result", "error");
				        		jsonObj.put("value", "Problem in creating configuration file");
				        		jsonObj.put("user_key", userKey);

				        		retValue = JSONValue.toJSONString(jsonObj);
				    			response.getWriter().write(retValue);
							}else{
					        	for( FeatureTreeNode featureNode : model.getNodes() ) {
					        		if ( featureNode.isInstantiated() ) {
						        		String decisionType=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionType");
						        		String decisionStep=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionStep");
						        		result=appendConfigurationFileFeatureInfo(configuredFileName, configuredModelsPath, userName, userID,Integer.toString(featureNode.getValue()), decisionType, decisionStep, task, strDate, viewName, featureNode.getID(), "true");
					        		}
					        	}
					    		jsonObj.put("result", "true");
				        		jsonObj.put("value", "The configuration has been saved successfully in the repository");
				        		jsonObj.put("user_key", userKey);
							  

				        		retValue = JSONValue.toJSONString(jsonObj);
				        		sc.setAttribute(userKey+"_lock", "free");
				    			response.getWriter().write(retValue);

							}
							

		        	}else{
		        		
		        		configuredFileName=Methods.getConfiguredFileName(configuredModelsPath, userKey);
		        		if (configuredFileName.compareToIgnoreCase("false")==0){
		        			sc.setAttribute(userKey+"_lock", "free");
		        			jsonObj.put("result", "error");
			        		jsonObj.put("value", "Problem in finding configuration file");
			        		jsonObj.put("user_key", userKey);

			        		retValue = JSONValue.toJSONString(jsonObj);
			    			response.getWriter().write(retValue);

			    			

		        		}else{
		        			
							FeatureModel model = confEngine.getModel();
				    		if (confEngine == null) {
				    			sc.setAttribute(userKey+"_lock", "free");
				    			jsonObj.put("result", "error");
				        		jsonObj.put("value", "Configuration engine must be created first");
				        		jsonObj.put("user_key", userKey);

				        		retValue = JSONValue.toJSONString(jsonObj);
				    			response.getWriter().write(retValue);

				    		}
				        	
				    		result=appendConfigurationFile(configuredFileName, configuredModelsPath, userName, userID,  task,"task", strDate,"configured");
				        	

				        	for( FeatureTreeNode featureNode : model.getNodes() ) {
				        		if ( featureNode.isInstantiated() ) {
					        		String decisionType=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionType");
					        		String decisionStep=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionStep");
					        		result=appendConfigurationFileFeatureInfo(configuredFileName, configuredModelsPath, userName,userID,Integer.toString(featureNode.getValue()), decisionType, decisionStep, task, strDate, viewName, featureNode.getID(), "true");
				        		}
				        	}

				        	
				    		jsonObj.put("result", "true");
			        		jsonObj.put("value", "The configuration has been saved successfully in the repository");
			        		jsonObj.put("user_key", userKey);

			        		retValue = JSONValue.toJSONString(jsonObj);
			        		sc.setAttribute(userKey+"_lock", "free");
			    			response.getWriter().write(retValue);

		        		}
		        		
		        	}
						 
	        	} 
		  
     		} catch (Exception e) {
     			sc.setAttribute(userKey+"_lock", "free");
				e.printStackTrace();
				jsonObj.put("result", "error");
        		jsonObj.put("value", e);
        		retValue = JSONValue.toJSONString(jsonObj);
    			response.getWriter().write(retValue);

			
			}
			
		
	}	
	
	
	
	
	
	
	
	
	
	protected static String createConfigurationFile(String featureModelName,String configuredFileName,String featureModelFileName ,String configuredModelsPath, String userName,String userID, String workflow, String task,String taskType, String session , String userKey, String action) throws ParserConfigurationException{
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
	        	rootElement.setAttribute("user_key", userKey);
	        	rootElement.setAttribute("workflow", workflow);


	        	rootElement.setAttribute("edtiable", "true");
	        	
	        	document.appendChild(rootElement);
	        	
	        	//configuration_info
	        	Element configurationInfoElement=document.createElement("configuration_info");
	        	
	        	configurationInfoElement.setAttribute("row", "1");
	        	rootElement.appendChild(configurationInfoElement);
	        	
	        	
	        	Element dateElement=document.createElement("date");
	        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
	        	configurationInfoElement.appendChild(dateElement);
	        	
	        	
	        	
	        	Element userNameElement=document.createElement("user_name");
	        	userNameElement.appendChild(document.createTextNode(userName));
	        	configurationInfoElement.appendChild(userNameElement);
	        	
	        	Element userIDElement=document.createElement("user_id");
	        	userIDElement.appendChild(document.createTextNode(userID));
	        	configurationInfoElement.appendChild(userIDElement);
	        	
	        	Element actionElement=document.createElement("action");
	        	actionElement.appendChild(document.createTextNode(action));
	        	configurationInfoElement.appendChild(actionElement);
        	
       	
	        	
	        	Element taskElement=document.createElement("task");
	        	taskElement.appendChild(document.createTextNode(task));
	        	configurationInfoElement.appendChild(taskElement);

	        	Element taskTypeElement=document.createElement("type");
	        	taskTypeElement.appendChild(document.createTextNode(taskType));
	        	configurationInfoElement.appendChild(taskTypeElement);

	        	
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
	

	
	
	
	
	
	protected static String appendConfigurationFile(String configuredFeatureModelFileName,String configuredModelsPath, String userName,String userID, String task,String taskType, String session, String action) throws ParserConfigurationException{
		String retValue="false";
		
    	try {
    		
    		
    		
    			File fileName = new File(configuredModelsPath+"/"+configuredFeatureModelFileName);	
		       	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
		    	Document document = docBuilder.parse(fileName);
		    	
		    	Element featureModelElement=(Element) document.getFirstChild();
		    	
	    		int maxRow=0;
		    	NodeList  configurationInfoNodes = document.getElementsByTagName("configuration_info");		    	
		    		
		    	for(int i=0;i<configurationInfoNodes.getLength();i++){
			    	Element  configurationInfoElement=(Element)configurationInfoNodes.item(i);
			    	
		    		if (Integer.parseInt(configurationInfoElement.getAttribute("row"))>maxRow){
		    			maxRow=Integer.parseInt(configurationInfoElement.getAttribute("row"));
		    		}
		    	}
		    	
	        	
	        	//configuration_info
	        	Element configurationInfoElement=document.createElement("configuration_info");
	        	configurationInfoElement.setAttribute("row", Integer.toString(maxRow+1));
	        	featureModelElement.appendChild(configurationInfoElement);
	        	
	        	Element dateElement=document.createElement("date");
	        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
	        	configurationInfoElement.appendChild(dateElement);
	        	
	        	
	        	
	        	
	        	Element userNameElement=document.createElement("user_name");
	        	userNameElement.appendChild(document.createTextNode(userName));
	        	configurationInfoElement.appendChild(userNameElement);
	        	
	        	Element userIDElement=document.createElement("user_id");
	        	userIDElement.appendChild(document.createTextNode(userID));
	        	configurationInfoElement.appendChild(userIDElement);
	        	
	        	Element actionElement=document.createElement("action");
	        	actionElement.appendChild(document.createTextNode(action));
	        	configurationInfoElement.appendChild(actionElement);
        	
	        
	        	
	        	
	        	Element taskElement=document.createElement("task");
	        	taskElement.appendChild(document.createTextNode(task));
	        	configurationInfoElement.appendChild(taskElement);
	        	
	        	Element taskTypeElement=document.createElement("type");
	        	taskTypeElement.appendChild(document.createTextNode(taskType));
	        	configurationInfoElement.appendChild(taskTypeElement);

	        	
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
	
	
	
	
	protected static String appendConfigurationFileFeatureInfo(String configuredFeatureModelFileName,String configuredModelsPath, String userName, String userID, String decisionValue, String decisionType,String decisionStep, String task, String session, String viewName , String featureID , String editable) throws ParserConfigurationException{
		
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
   								
   						    	
   								NodeList  decisionInfoList=featureElement.getElementsByTagName("decision_info");
   								int maxRow=0;
   								for (int k=0; k<decisionInfoList.getLength();k++){
   									Element decisionInfoElement=(Element)decisionInfoList.item(k);

   						    		if (Integer.parseInt(decisionInfoElement.getAttribute("row"))>maxRow){
   						    			maxRow=Integer.parseInt(decisionInfoElement.getAttribute("row"));
   						    		}
   								}
   								
   								
   								Element decisionElement=document.createElement("decision_info");
   								decisionElement.setAttribute("row", Integer.toString(maxRow+1));
   						    	featureElement.appendChild(decisionElement);
   					        	
   					        	Element dateElement=document.createElement("date");
   					        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
   					        	decisionElement.appendChild(dateElement);
   					        	
   					        	Element userNameElement=document.createElement("user_name");
   					        	userNameElement.appendChild(document.createTextNode(userName));
   					        	decisionElement.appendChild(userNameElement);
   					        	
   					        	Element userIDElement=document.createElement("user_id");
					        	userIDElement.appendChild(document.createTextNode(userID));
					        	decisionElement.appendChild(userIDElement);
					        	
					        	
   					        
   					        	
   					        	
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
				    	decisionElement.setAttribute("row", "1");
				    	featureElement.appendChild(decisionElement);
			        	
			        	Element dateElement=document.createElement("date");
			        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
			        	decisionElement.appendChild(dateElement);
			        	
			        	Element userNameElement=document.createElement("user_name");
			        	userNameElement.appendChild(document.createTextNode(userName));
			        	decisionElement.appendChild(userNameElement);
			        	
			        	Element userIDElement=document.createElement("user_id");
			        	userIDElement.appendChild(document.createTextNode(userID));
			        	decisionElement.appendChild(userIDElement);
			        	

				     
				        	
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
			    	decisionElement.setAttribute("row", "1");
			    	featureElement.appendChild(decisionElement);
		        	
		        	Element dateElement=document.createElement("date");
		        	dateElement.appendChild(document.createTextNode(Methods.getCurrentDate()));
		        	decisionElement.appendChild(dateElement);
		        	
		        	Element userNameElement=document.createElement("user_name");
		        	userNameElement.appendChild(document.createTextNode(userName));
		        	decisionElement.appendChild(userNameElement);
		        	
		        	Element userIDElement=document.createElement("user_id");
		        	userIDElement.appendChild(document.createTextNode(userID));
		        	decisionElement.appendChild(userIDElement);
		        	
			     
		        	
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
