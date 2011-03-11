package splot.services.extensions.fundp.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.XMLFeatureModel;
import splot.core.HandlerExecutionException;

/** Methods class integrates common methods. 
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class Methods {
	
	/** evaluateXPathExpression evaluates an XPath expression on an XML string. 
	 * 
	 * @throws XPathExpressionException 	if the XPath expression is not a valid expression and an exception has occurred that interferes with the XPath expression's operation  	
	 * @param XMLData			a string containing the XML data
	 * @param XPathExpression	a string including the XPath expression	
	 * @param result            an object of type EvaluationResult for keeping the result of the evaluation process
	 */
	public static void evaluateXPathExpression(String XMLData,String XPathExpression , EvaluationResult result) throws XPathExpressionException{
		XPathExpression=XPathExpression.replace(" ", ".");
		XPath xpath = XPathFactory.newInstance().newXPath(); 
		InputSource inputSource = new InputSource(new StringReader( XMLData));
		String tempResult="";
		String exceptionValue="";
		Boolean nodeExist=false;
		 try {
			 NodeList nodes =  (NodeList) xpath.evaluate(XPathExpression, inputSource, XPathConstants.NODESET);
			 
			 
			 for (int i=0;i<nodes.getLength();i++){
					Node node=(Node) nodes.item(i);
					if(node.getLocalName().compareToIgnoreCase("feature.group")!=0){
						nodeExist=true;
						tempResult=tempResult+node+"\n";
					}
					
			 }
			 
			 
		} catch (Exception e) {
			exceptionValue=e.getMessage();
		}
		if (exceptionValue!=""){
			result.error=exceptionValue;
			
		}
		
		else{
			if (!nodeExist){
				result.error="The expression is not evaluated to any node.";
				
			}
			else{
				tempResult=tempResult.replace(".", " ");
				result.nodesList=tempResult;
			}
		}
		
		
		  result.nodesList=result.nodesList.replace('[',' ');
		  result.nodesList=result.nodesList.replace(']',' ');
		  result.nodesList=result.nodesList.replace(':',' ');
		  result.nodesList=result.nodesList.replaceAll("null"," ");
		  result.nodesList=result.nodesList.replaceAll("feature group"," ");



		//result.nodesList=result.nodesList.replaceAll("]","");
		//result.nodesList=result.nodesList.replaceAll("null","");
		
		
		
		
	
	}
	
	
	public static void getXPathExpressionFeatuures(String XMLData,String XPathExpression , EvaluationResult result) throws XPathExpressionException{
		XPathExpression=XPathExpression.replace(" ", ".");
		XPath xpath = XPathFactory.newInstance().newXPath(); 
		InputSource inputSource = new InputSource(new StringReader( XMLData));
		String tempResult="";
		String exceptionValue="";
		Boolean nodeExist=false;
		 try {
			 NodeList nodes =  (NodeList) xpath.evaluate(XPathExpression, inputSource, XPathConstants.NODESET);
			 
			 
			 for (int i=0;i<nodes.getLength();i++){
					Node node=(Node) nodes.item(i);
					nodeExist=true;
					tempResult=tempResult+","+node+"\n";
			 }
			 
			 
		} catch (Exception e) {
			exceptionValue=e.getMessage();
		}
		if (exceptionValue!=""){
			result.error=exceptionValue;
			
		}
		
		else{
			if (!nodeExist){
				result.error="The expression is not evaluated to any node.";
				
			}
			else{
				tempResult=tempResult.replace(".", " ");
				result.nodesList=tempResult;
			}
		}
		
		
		  result.nodesList=result.nodesList.replace('[',' ');
		  result.nodesList=result.nodesList.replace(']',' ');
		  result.nodesList=result.nodesList.replace(':',' ');
		  result.nodesList=result.nodesList.replaceAll("null"," ");



		//result.nodesList=result.nodesList.replaceAll("]","");
		//result.nodesList=result.nodesList.replaceAll("null","");
		
		
		
		
	
	}
	
	
	
	/** getCharacterDataFromElement returns the data value of an element in an XML data   
	 * 
	 * @param element	 an object containing an element of the XML data
	 * @return           returns a string which includes the text of the element 
	 */	
	public static String getCharacterDataFromElement(Element element) {
		   Node child = element.getFirstChild();
		   if (child instanceof CharacterData) {
		     CharacterData cd = (CharacterData) child;
		       return cd.getData();
		     }
		   return "?";
		 }	


	/** getFeatureModelViews   returns a list of views of a special feature.
	 * 
	 * @throws HandlerExecutionException 	if an exception has occurred that interferes with the handler's normal operation  	
	 * @param viewDir			a string containing the real path to the views folder
	 * @return                  a list of views    
	 */
	public static   List<Map> getFeatureModelViews(String viewDir) throws HandlerExecutionException {
		List<Map> viewlist =new LinkedList<Map>();
		
		try {
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
				for (int i=0;i<childeren.length;i++){
					if (childeren!=null){
						if (childeren[i].endsWith(".xml") !=false){
	     					String  fileName=childeren[i];
	       					File viewFile = new File(viewDir+"/"+fileName);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
          					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
           					Element  featureModelElement = (Element) featureModelNodes.item(0);
           					String   featureModelElementName=featureModelElement.getAttribute("name");
           					Map featureModel=new HashMap(); 
               				featureModel.put("fm_name",featureModelElementName);
               					
               				List<Map> fmViewList=new LinkedList<Map>();
           					NodeList viewNodes = doc.getElementsByTagName("view");
           					for (int j = 0; j < viewNodes.getLength(); j++){
            					Element viewElement = (Element) viewNodes.item(j);
            					String viewElementName=(String)viewElement.getAttribute("name").trim();
            					Map viewName = new HashMap();
            					viewName.put("view_name", viewElementName);
            					fmViewList.add(viewName);
            						  
           					}
           						featureModel.put("views", fmViewList);
           						viewlist.add(featureModel);
           					

						}
					}
				}
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the view repository path":e.getMessage());
		}
		

		
		return   viewlist;
	}

	
	
	public static  String getFeatureModelViews(String viewDir, String featureModelName) throws HandlerExecutionException {
		String retValue="";
		
		try {
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
				for (int i=0;i<childeren.length;i++){
					if (childeren!=null){
						if (childeren[i].endsWith(".xml") !=false){
	     					String  fileName=childeren[i];
	       					File viewFile = new File(viewDir+"/"+fileName);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
          					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
           					Element  featureModelElement = (Element) featureModelNodes.item(0);
           					String   featureModelElementName=featureModelElement.getAttribute("name");
           					
           					if (featureModelElementName.compareToIgnoreCase(featureModelName)==0){
           						NodeList viewNodes = doc.getElementsByTagName("view");
               					for (int j = 0; j < viewNodes.getLength(); j++){
                					Element viewElement = (Element) viewNodes.item(j);
                					String viewElementName=(String)viewElement.getAttribute("name").trim();
                					
                					if (retValue==""){
                						retValue=viewElementName;
                					}else{
                						retValue=retValue+","+viewElementName;
                					}
                				
                						  
               					}
           						
           					}

						}
					}
				}
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the view repository path":e.getMessage());
		}
			

		
		return   retValue;
	}

	public static  String getFeatureModelAllocatedViews(String viewDir, String featureModelName) throws HandlerExecutionException {
		String retValue="";
		
		try {
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
				for (int i=0;i<childeren.length;i++){
					if (childeren!=null){
						if (childeren[i].endsWith(".xml") !=false){
	     					String  fileName=childeren[i];
	       					File viewFile = new File(viewDir+"/"+fileName);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
          					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
           					Element  featureModelElement = (Element) featureModelNodes.item(0);
           					String   featureModelElementName=featureModelElement.getAttribute("name");
           					
           					if (featureModelElementName.compareToIgnoreCase(featureModelName)==0){
           						NodeList viewNodes = doc.getElementsByTagName("view");
               					for (int j = 0; j < viewNodes.getLength(); j++){
                					Element viewElement = (Element) viewNodes.item(j);
                					
                					NodeList taskNodes=viewElement.getElementsByTagName("task");
                					if (taskNodes.getLength()>0){
                							Element  taskElement=(Element)taskNodes.item(0);
                							NodeList WorkflowNodes=taskElement.getElementsByTagName("workflow");
                							if (WorkflowNodes.getLength()>0){
                								String viewElementName=(String)viewElement.getAttribute("name").trim();
                            					
                            					if (retValue==""){
                            						retValue=viewElementName;
                            					}else{
                            						retValue=retValue+","+viewElementName;
                            					}	
                							}

                    					
                					}
                   					}
           						
           					}

						}
					}
				}
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the view repository path":e.getMessage());
		}
			

		return   retValue;
	}

	public static  String getFeatureModelAllViews(String viewDir, String featureModelName) throws HandlerExecutionException {
		String retValue="";
		
		try {
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
				for (int i=0;i<childeren.length;i++){
					if (childeren!=null){
						if (childeren[i].endsWith(".xml") !=false){
	     					String  fileName=childeren[i];
	       					File viewFile = new File(viewDir+"/"+fileName);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
          					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
           					Element  featureModelElement = (Element) featureModelNodes.item(0);
           					String   featureModelElementName=featureModelElement.getAttribute("name");
           					
           					if (featureModelElementName.compareToIgnoreCase(featureModelName)==0){
           						NodeList viewNodes = doc.getElementsByTagName("view");
               					for (int j = 0; j < viewNodes.getLength(); j++){
                					Element viewElement = (Element) viewNodes.item(j);
                					
                					String viewElementName=(String)viewElement.getAttribute("name").trim();
                					
                					if (retValue==""){
                						retValue=viewElementName;
                					}else{
                						retValue=retValue+","+viewElementName;
                					}	                					
                   				}
           						
           					}

						}
					}
				}
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the view repository path":e.getMessage());
		}
			

		return   retValue;
	}
	
	public static  String getPlaceAllocatedViews(String viewDir, String featureModelName, String workflow, String placeName,String placeType) throws HandlerExecutionException {
		String retValue="";
		
		try {
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
				for (int i=0;i<childeren.length;i++){
					if (childeren!=null){
						if (childeren[i].endsWith(".xml") !=false){
	     					String  fileName=childeren[i];
	       					File viewFile = new File(viewDir+fileName);
	       					
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
	       					
          					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
           					Element  featureModelElement = (Element) featureModelNodes.item(0);
           					String   featureModelElementName=featureModelElement.getAttribute("name");
           					
           					if (featureModelElementName.compareToIgnoreCase(featureModelName)==0){

           						NodeList viewNodes = doc.getElementsByTagName("view");
               					for (int j = 0; j < viewNodes.getLength(); j++){
                					Element viewElement = (Element) viewNodes.item(j);

                					NodeList taskNodes=viewElement.getElementsByTagName("task");
                					for(int k=0; k<taskNodes.getLength();k++){
                						Element taskElement=(Element) taskNodes.item(k);

                						NodeList workflowNodes=taskElement.getElementsByTagName("workflow");
                						for (int p=0;p<workflowNodes.getLength()  ;p++){

                							Element workflowElement=(Element) workflowNodes.item(p);
                							if (workflowElement.getAttribute("name").compareToIgnoreCase(workflow)==0){
                								
                								NodeList taskNameNodes=workflowElement.getElementsByTagName("task_name");
                								Element  taskNameElement=(Element)taskNameNodes.item(0);
                								if (placeType.compareToIgnoreCase("task")==0){

                									String taskPart=getCharacterDataFromElement(taskNameElement).split("\\?")[0];
                									if (taskPart.compareToIgnoreCase(placeName)==0){
                										if (retValue==""){
                                    						retValue=viewElement.getAttribute("name");
                                    					}else{
                                    						retValue=retValue+","+viewElement.getAttribute("name");
                                    					}
                									}

                								}else{
                									String stopPart=getCharacterDataFromElement(taskNameElement).split("\\?")[1];
                									if (stopPart.compareToIgnoreCase(placeName)==0){
                										if (retValue==""){
                                    						retValue=viewElement.getAttribute("name");
                                    					}else{
                                    						retValue=retValue+","+viewElement.getAttribute("name");
                                    					}
                									}
                									
                								}
                								
                								
                								
                							}
                							
                						}
                					}
                					
                					
                   				}
           						
           					}

						}
					}
				}
		} catch (Exception e) {
			retValue="";
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the view repository path":e.getMessage());
		}
			

		return   retValue;
	}
	
	
	/** getXPathFromView   gets the XPath expression that is stored in a view file.
	 * 
	 * @param viewDir			a string containing the real path to the view's folder
	 * @param featureModelName	a string containing the name of a feature model
	 * @param viewName 	a string containing the name of a view
	 * @return          a string including the XPath expression       
	 */	
	public static String getXPathFromView(String viewDir, String featureModelName,String viewName){
		String retValue="";
		try {
			
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
				start:
				for (int i=0;i<childeren.length;i++){
					if (childeren!=null){
						if (childeren[i].endsWith(".xml") !=false){
	 						String  fileName=childeren[i];
	       					File viewFile = new File(viewDir+"/"+fileName);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
	       				
	       					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
	       					Element  featureModelElement = (Element) featureModelNodes.item(0);
	       					String   FMName=featureModelElement.getAttribute("name");
	       					if (featureModelName.compareToIgnoreCase(FMName)==0){
	      						NodeList viewNodes = doc.getElementsByTagName("view");
	       						for (int j = 0; j < viewNodes.getLength(); j++){
	       							Element viewElement = (Element) viewNodes.item(j);
	       							String   vName=viewElement.getAttribute("name");
	       							if (viewName.compareToIgnoreCase(vName)==0){
	       								
	       						     NodeList viewSpecification = viewElement.getElementsByTagName("specification");
	       						     Element specification = (Element) viewSpecification.item(0);
	       						     String  xpathExpression=getCharacterDataFromElement(specification).trim();
	       						     retValue=xpathExpression;
	       						     break start;
	       						     

	       							}
	       								
	       							}

	       						
	       					}

						}
					}
					
				}
			
			
		} catch (Exception e) {
			retValue="error";
		}
		
		if ((retValue=="") || (retValue.isEmpty()) || (retValue==null)){
			retValue="error";
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
	
	
	
	public static String checkConfigurationCompletionInStopPlace(String featureModelName , String viewDir,String modelDir,String configuredModelsPath, String placeName ,String placeType , String workflow, String configuredFileName, String userName, String userID) throws HandlerExecutionException {
		String retVal="true";
		
		try {
			
			
			String stopViews=getPlaceAllocatedViews(viewDir, featureModelName, workflow, placeName, placeType);
			if (stopViews==""){
				retVal="";
				return retVal;
			}else{
				String[] viewList=stopViews.split("\\,");
				for (int i=0;i<viewList.length;i++){
					String status=viewConfigurationIsComplete(featureModelName, viewDir, modelDir, viewList[i], configuredFileName);
					
					String taskName=Methods.getViewTaskName(viewDir, workflow, featureModelName, viewList[i]);
					
					if (status.compareToIgnoreCase("true")==0){
						String result=appendConfigurationFile(configuredFileName, configuredModelsPath, userName, userID,  taskName,"task", getCurrentDate(),"completed");
					}
		    		
				
				}


			}
			
			String result=appendConfigurationFile(configuredFileName, configuredModelsPath, userName, userID,  placeName,placeType, getCurrentDate(),"completed");

			
		} catch (Exception e) {
			retVal="false";
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the model/view repository path":e.getMessage());
		}
		
		return retVal;
		
	}
	
	public static String viewConfigurationIsComplete(String featureModelName , String viewDir,String modelDir, String viewName , String configuredFileName){
		String retVal="true";
		
		Boolean completed=true;
		
		try {
		
		String featureModelFileName=getfeatureModelFileName(modelDir, featureModelName);
	
			
		String viewFeatureList=ViewFeatureList(viewDir, modelDir, featureModelFileName, featureModelName, viewName);
		
		if (viewFeatureList==""){
			retVal="unknown";
		}else{
			
			String[] featureList=viewFeatureList.split("\\,");
			for (int i=0;i<featureList.length && completed ;i++){
				
				String result=featureConfigurationStatusInVeiw(featureList[i], configuredFileName, modelDir);
				if ((result.compareToIgnoreCase("0")!=0) && (result.compareToIgnoreCase("1")!=0) ){
					completed=false;
					retVal="false";

				}
				
				
			}
			

		}
		} catch (Exception e) {
			 retVal="false";
			 e.printStackTrace();
			
		}
		
		return retVal;
	}
	
	
	public static String featureConfigurationStatusInVeiw(String featureID, String configuredFileName,String modelDir) throws HandlerExecutionException{
		String retVal="";
		
		try {
			File confFile = new File(modelDir+"configured_models/"+configuredFileName);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(confFile);

			NodeList  featureList=doc.getElementsByTagName("feature");
			if (featureList.getLength()<1){
				retVal="false";
				return retVal;
			}
			
			Boolean found=false;
			
			for(int i=0; i<featureList.getLength() && !found; i++){
				Element featureElement=(Element) featureList.item(i);
				
				if (featureElement.getAttribute("id").compareToIgnoreCase(featureID)==0){
					found=true;
					
					NodeList featureDecisionList=featureElement.getElementsByTagName("decision_info");
				
					int maxRow=0;
					for (int j=0; j<featureDecisionList.getLength();j++){
						Element featureDecisionElement=(Element) featureDecisionList.item(j);
						if (Integer.parseInt(featureDecisionElement.getAttribute("row"))>maxRow){
							maxRow=Integer.parseInt(featureDecisionElement.getAttribute("row"));
							
							NodeList valueList=featureDecisionElement.getElementsByTagName("value");
							Element  valueElement=(Element)valueList.item(0);
							
							retVal=Methods.getCharacterDataFromElement(valueElement);
							
						}
					}
				}

				
			}
			
	
			
		} catch (Exception e) {
			retVal="";
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the model repository path":e.getMessage());

		}
		
		return retVal;
	}
	
	public static String getFeatureModelUncoveredFeaturesInAllocatedViews(String featureModelName,String viewDir,String modelDir ) throws HandlerExecutionException{
		
		String retValue="";
		try {
			String featureModelFileName=getfeatureModelFileName(modelDir, featureModelName);
			
			FeatureModel featureModel = null;
			featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
			featureModel.loadModel();
			
				for( FeatureTreeNode feature : featureModel.getNodes()) {
					String featureCoveredStatus=featureCoveredInAllocatedViews(feature, viewDir, modelDir, featureModelFileName, featureModelName);

					if (featureCoveredStatus.compareToIgnoreCase("true")!=0){
						if (retValue==""){
							retValue=feature.getName();
						}else{
							retValue=retValue+","+feature.getName();
						}
					}
				}

			

			
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the view/model repository path":e.getMessage());
		}
		
		
		return retValue;
		
	}
	
	public static String getFeatureModelUncoveredFeaturesInAllViews(String featureModelName,String viewDir,String modelDir ) throws HandlerExecutionException{
		
		String retValue="";
		try {
			String featureModelFileName=getfeatureModelFileName(modelDir, featureModelName);
			
			FeatureModel featureModel = null;
			featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
			featureModel.loadModel();
			
				for( FeatureTreeNode feature : featureModel.getNodes()) {
					String featureCoveredStatus=featureCoveredInAllViews(feature, viewDir, modelDir, featureModelFileName, featureModelName);

					if (featureCoveredStatus.compareToIgnoreCase("true")!=0){
						if (retValue==""){
							retValue=feature.getName();
						}else{
							retValue=retValue+","+feature.getName();
						}
					}
				}

			

			
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the view/model repository path":e.getMessage());
		}
		
		
		return retValue;
		
	}
	
	
	public static  String featureCoveredInViews(FeatureTreeNode feature , String viewDir,String modelDir,String featureModelFileName, String featureModelName) throws HandlerExecutionException{
		String retVal="unknown";
		Boolean foundInView=false;
		Boolean foundInPropagationList=false;
		
		try {
			String viewString=getFeatureModelViews(viewDir, featureModelName);
			String[] viewList=viewString.split("\\,");
			
			for (int i=0;i<viewList.length && !foundInView;i++){
				String viewName=viewList[i];
				String result=FeatureInView(feature, viewDir, modelDir, featureModelFileName, featureModelName, viewName);
				if (result.compareToIgnoreCase("true")==0){
					retVal="true";
					foundInView=true;
				}
				
			}
			
			if (!foundInView){
				
		   		FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();

				for (FeatureTreeNode tmpfeature: featureModel.getNodes()){
					if (!(feature.equals(tmpfeature))){
						
						List<FeatureTreeNode> featurePropagatedList =new LinkedList<FeatureTreeNode>();
						
						featurePropagatedList=tmpfeature.getPropagatedNodes();
						
						if (featurePropagatedList.contains(feature)){
							foundInPropagationList=true;
							retVal="true";
							return retVal;
							
						}
						
					}
					
					
				}
				
				
				if (!foundInPropagationList){
					retVal="false";
					return retVal;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
		
		return retVal;
		
	}

	public static  String featureCoveredInAllocatedViews(FeatureTreeNode feature , String viewDir,String modelDir,String featureModelFileName, String featureModelName) throws HandlerExecutionException{
		String retVal="unknown";
		Boolean foundInView=false;
		Boolean foundInPropagationList=false;
		
		try {
			String viewString=getFeatureModelAllocatedViews(viewDir, featureModelName);
			String[] viewList=viewString.split("\\,");

			for (int i=0;i<viewList.length && !foundInView;i++){
				String viewName=viewList[i];
				String result=FeatureInView(feature, viewDir, modelDir, featureModelFileName, featureModelName, viewName);
				if (result.compareToIgnoreCase("true")==0){
					
					retVal="true";
					foundInView=true;
				}
				
			}
			
			if (!foundInView){
				
		   		FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();

				for (FeatureTreeNode tmpfeature: featureModel.getNodes()){
					if (!(feature.equals(tmpfeature))){
						
						List<FeatureTreeNode> featurePropagatedList =new LinkedList<FeatureTreeNode>();
						
						featurePropagatedList=tmpfeature.getPropagatedNodes();
						
						if (featurePropagatedList.contains(feature)){
							foundInPropagationList=true;
							retVal="true";
							return retVal;
							
						}
						
					}
					
					
				}
				
				
				if (!foundInPropagationList){
					retVal="false";
					return retVal;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
		
		
		return retVal;
		
	}

	public static  String featureCoveredInAllViews(FeatureTreeNode feature , String viewDir,String modelDir,String featureModelFileName, String featureModelName) throws HandlerExecutionException{
		String retVal="unknown";
		Boolean foundInView=false;
		Boolean foundInPropagationList=false;
		
		try {
			String viewString=getFeatureModelAllViews(viewDir, featureModelName);
			String[] viewList=viewString.split("\\,");

			for (int i=0;i<viewList.length && !foundInView;i++){
				String viewName=viewList[i];
				String result=FeatureInView(feature, viewDir, modelDir, featureModelFileName, featureModelName, viewName);
				if (result.compareToIgnoreCase("true")==0){
					
					retVal="true";
					foundInView=true;
				}
				
			}
			
			if (!foundInView){
				
		   		FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();

				for (FeatureTreeNode tmpfeature: featureModel.getNodes()){
					if (!(feature.equals(tmpfeature))){
						
						List<FeatureTreeNode> featurePropagatedList =new LinkedList<FeatureTreeNode>();
						
						featurePropagatedList=tmpfeature.getPropagatedNodes();
						
						if (featurePropagatedList.contains(feature)){
							foundInPropagationList=true;
							retVal="true";
							return retVal;
							
						}
						
					}
					
					
				}
				
				
				if (!foundInPropagationList){
					retVal="false";
					return retVal;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
		
		
		return retVal;
		
	}

	
	public static  String FeatureInView(FeatureTreeNode feature , String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName) throws HandlerExecutionException  {
		String retVal="unknown";
		
		try {
			String featureName=feature.getName();
			
			if (feature instanceof FeatureGroup ){
				retVal="true";
				return retVal;
			}
		
			File   Modelfile = new File(modelDir+featureModelFileName);
			
			if (!Modelfile.exists()){
				retVal="unknown";
				throw new HandlerExecutionException("The feature model file not found.");

			}

			
			String featureModelInXML=SXFMToXML.parse(modelDir+featureModelFileName);
			
			if ((featureModelInXML=="") || (featureModelInXML.compareToIgnoreCase("error")==0)){
				retVal="unknown";
				throw new HandlerExecutionException("Problem in converting SXFM to XML.");

			}
			
			String xpathExpression=getXPathFromView(viewDir,featureModelName,viewName);
			if (xpathExpression==""){
				
				retVal="unknown";
				throw new HandlerExecutionException("Error in getting XPath expression from view.");
			}
			
			if (xpathExpression.compareToIgnoreCase("error")==0){
				
				retVal="unknown";
				throw new HandlerExecutionException("Error in getting XPath expression from view.");
			}
			
			   EvaluationResult EvaluationResult=new EvaluationResult();
			   evaluateXPathExpression(featureModelInXML, xpathExpression, EvaluationResult);

			   
			   if ((EvaluationResult.nodesList==null) || (EvaluationResult.nodesList.compareToIgnoreCase("null")==0) || (EvaluationResult.nodesList=="")){
					retVal="unknown";
					throw new HandlerExecutionException("Error in XPath expression evaluation.");
			   }
			   
			   
			   if (!(EvaluationResult.nodesList.indexOf(featureName)==-1) ||(feature.isRoot()) ){
				   retVal="true";
				   return  retVal;
			   }else{
				   retVal="false";
				   return  retVal;
 
			   }

			   
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Problems locating/acessing the view/model repository path");
		} 
	}

	
	
	public static  String ViewFeatureList( String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName) throws HandlerExecutionException  {
		String retVal="";
		
		try {
			
			
			File   Modelfile = new File(modelDir+"/"+featureModelFileName);
			
			if (!Modelfile.exists()){
				retVal="unknown";
				throw new HandlerExecutionException("The feature model file not found.");

			}
			
	   		FeatureModel featureModel = null;
			featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
			featureModel.loadModel();

			for (FeatureTreeNode feature: featureModel.getNodes()){
				
				if (!(feature instanceof FeatureGroup)){
					String result=FeatureInView(feature, viewDir, modelDir, featureModelFileName, featureModelName, viewName);
					if (result.compareToIgnoreCase("true")==0){
						
						if (retVal==""){
							retVal=feature.getID();
						}else{
							retVal=retVal+","+feature.getID();

						}
						
					}
				}
				
				
				
			}

			
			
			
			

//			
//			String featureModelInXML=SXFMToXML.parse(modelDir+featureModelFileName);
//			
//			if ((featureModelInXML=="") || (featureModelInXML.compareToIgnoreCase("error")==0)){
//				retVal="unknown";
//				throw new HandlerExecutionException("Problem in converting SXFM to XML.");
//
//			}
//			
//			String xpathExpression=getXPathFromView(viewDir,featureModelName,viewName);
//			if (xpathExpression==""){
//				
//				retVal="unknown";
//				throw new HandlerExecutionException("Error in getting XPath expression from view.");
//			}
//			
//			if (xpathExpression.compareToIgnoreCase("error")==0){
//				
//				retVal="unknown";
//				throw new HandlerExecutionException("Error in getting XPath expression from view.");
//			}
//			
//			   EvaluationResult EvaluationResult=new EvaluationResult();
//			   getXPathExpressionFeatuures(featureModelInXML, xpathExpression, EvaluationResult);
//
//			   
//			   
//			   
//			   if ((EvaluationResult.nodesList==null) || (EvaluationResult.nodesList.compareToIgnoreCase("null")==0) || (EvaluationResult.nodesList=="")){
//					retVal="";
//					
//			   }else{
//				   retVal=EvaluationResult.nodesList;
//			   }
//			   
//			   
			   
			   
			
		} catch (Exception e) {
			retVal="unknown";
			e.printStackTrace();
			throw new HandlerExecutionException("Problems locating/acessing the view/model repository path");
		} 
		
		
		return retVal;
	}

	
	
	
	
	public static  String FeatureInXPathExpression(String featureName , String modelDir,String featureModelFileName,  String XPathExpression) throws HandlerExecutionException  {
		String retVal="unknown";
		
		try {
			
			
		
			File   Modelfile = new File(modelDir+featureModelFileName);
			
			if (!Modelfile.exists()){
				retVal="unknown";
				throw new HandlerExecutionException("The feature model file not found.");

			}

			
			String featureModelInXML=SXFMToXML.parse(modelDir+featureModelFileName);
			
			
			if ((featureModelInXML=="") || (featureModelInXML.compareToIgnoreCase("error")==0)){
				retVal="unknown";
				throw new HandlerExecutionException("Problem in converting SXFM to XML.");

			}
			
		
			if (XPathExpression==""){
				
				retVal="unknown";
				throw new HandlerExecutionException("Error in getting XPath expression from view.");
			}
			
			
			   EvaluationResult EvaluationResult=new EvaluationResult();
			   evaluateXPathExpression(featureModelInXML, XPathExpression, EvaluationResult);

			   
			   if ((EvaluationResult.nodesList==null) || (EvaluationResult.nodesList.compareToIgnoreCase("null")==0) || (EvaluationResult.nodesList=="")){
					retVal="unknown";
					throw new HandlerExecutionException("Error in XPath expression evaluation.");
			   }
			   
			   
			   if (!(EvaluationResult.nodesList.indexOf(featureName)==-1) ){
				   retVal="true";
				   return  retVal;
			   }else{
				   retVal="false";
				   return  retVal;
 
			   }

			   
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Paremeter 'op' must be specified to 'reset'");
		} 
	}

	
	
	/** checkFeatureInViewStatus   checks the visibility of a feature in a special view.
	 * 
	 * @param feature	a FeatureTreeNode object including the feature should be checked
	 * @param viewDir	a string containing the real path to the views folder
	 * @param modelDir 	a string containing the real path to the models folder
	 * @param featureModelFileName  a string including the file name in which the model is stored
	 * @param featureModelName      the name of the model that the feature is part of it
	 * @param viewName    the name of the view
	 * @param result      a FeatureInViewCheckingResult object keeping the visibility status of the feature in the view 
	 * @param visualizationType  a string including one of four valid values for visualization: none (not a visualization), greyed,pruned and collapsed 
	 */		
	public static  void checkFeatureInViewStatus(FeatureTreeNode feature , String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName,
			  FeatureInViewCheckingResult result,String visualizationType)  {
		String featureName=feature.getName();
		
		try {
			if (viewName.compareToIgnoreCase("none")==0){
				result.errorList="";
				result.show=true;
				result.available=true;
				result.executionStatus=true;
				return;

			}
			
			if (visualizationType.compareToIgnoreCase("none")==0){
				result.errorList="";
				result.show=true;
				result.available=true;
				result.executionStatus=true;
				return;

			}
			
			
			File   Modelfile = new File(modelDir+featureModelFileName);
			
			if (!Modelfile.exists()){
				result.errorList=modelDir+featureModelFileName+"is not valid!";
				result.show=true;
				result.available=true;
				result.executionStatus=false;
				return;

			}
			
			String featureModelInXML=SXFMToXML.parse(modelDir+featureModelFileName);
			
			
			if ((featureModelInXML=="") || (featureModelInXML.compareToIgnoreCase("error")==0)){
				result.errorList="Error in conversion from SXFM to XML!";
				result.show=true;
				result.available=true;
				result.executionStatus=false;
				return;
			}
			
			
			String xpathExpression=getXPathFromView(viewDir,featureModelName,viewName);
			if (xpathExpression==""){
				result.errorList="Error in getting XPath expression from view!";
				result.show=true;
				result.available=true;
				result.executionStatus=false;
				return;
			}
			
			   EvaluationResult EvaluationResult=new EvaluationResult();
			   evaluateXPathExpression(featureModelInXML, xpathExpression, EvaluationResult);
			
			   if ((EvaluationResult.nodesList==null) || (EvaluationResult.nodesList.compareToIgnoreCase("null")==0) || (EvaluationResult.nodesList=="")){
					result.errorList="Error in XPath expression evaluation!";
					result.show=true;
					result.available=true;
					result.executionStatus=false;
					return;
			   }
			   
			   if (visualizationType.compareToIgnoreCase("greyed")==0){
				   if (!(EvaluationResult.nodesList.indexOf(featureName)==-1) ||(feature.isRoot()) ){
						result.errorList="";
						result.show=true;
						result.available=true;
						result.executionStatus=true;
				   }else{
						result.errorList="";
						result.show=true;
						result.available=false;
						result.executionStatus=true;
				   }
				   
			   }else if (visualizationType.compareToIgnoreCase("pruned")==0){
				   if ((feature instanceof FeatureGroup)){
					   FeatureTreeNode rootFeature=(FeatureTreeNode) feature.getParent();
					   FeatureInViewCheckingResult rootFeatureInViewCheckingResult=new FeatureInViewCheckingResult();
					   checkFeatureInViewStatus( rootFeature,viewDir,modelDir,featureModelFileName,featureModelName,viewName,rootFeatureInViewCheckingResult,visualizationType);
					   
						result.errorList=rootFeatureInViewCheckingResult.errorList;
						result.show=rootFeatureInViewCheckingResult.show;
						result.available=rootFeatureInViewCheckingResult.available;
						result.executionStatus=rootFeatureInViewCheckingResult.executionStatus;
			  
					   
				   }else{
				   
					   if ((!(EvaluationResult.nodesList.indexOf(featureName)==-1)) || (feature.isRoot())){
						   result.errorList="";
						   result.show=true;
						   result.available=true;
						   result.executionStatus=true;
						
					   }else if(!(xpathExpression.indexOf(featureName)==-1) && (!(EvaluationResult.nodesList.indexOf(featureName)==-1)) ){
						   result.errorList="";
						   result.show=true;
						   result.available=true;
						   result.executionStatus=true;

					   
					   }else if(!(xpathExpression.indexOf(featureName)==-1) && (EvaluationResult.nodesList.indexOf(featureName)==-1)){
						   result.errorList="";
						   result.show=true;
						   result.available=false;
						   result.executionStatus=true;
					   }else{
						result.errorList="";
						result.show=false;
						result.available=false;
						result.executionStatus=true;

					   }
				   }  
			   }else if (visualizationType.compareToIgnoreCase("collapsed")==0){
				   
				   if (feature instanceof FeatureGroup){
					   FeatureTreeNode rootFeature=(FeatureTreeNode) feature.getParent();
					   FeatureInViewCheckingResult rootFeatureInViewCheckingResult=new FeatureInViewCheckingResult();
					   checkFeatureInViewStatus( rootFeature,viewDir,modelDir,featureModelFileName,featureModelName,viewName,rootFeatureInViewCheckingResult,visualizationType);
					   
						result.errorList=rootFeatureInViewCheckingResult.errorList;
						result.show=rootFeatureInViewCheckingResult.show;
						result.available=rootFeatureInViewCheckingResult.available;
						result.executionStatus=rootFeatureInViewCheckingResult.executionStatus;
				   }else{
				   
					   if ((!(EvaluationResult.nodesList.indexOf(featureName)==-1)) || (feature.isRoot())){
						   result.errorList="";
						   result.show=true;
						   result.available=true;
						   result.executionStatus=true;
					   }else{
						   result.errorList="";
						   result.show=false;
						   result.available=false;
						   result.executionStatus=true;

					   }
				   }
				   
			   }else if (visualizationType.compareToIgnoreCase("none")==0){
					result.errorList="";
					result.show=true;
					result.available=true;
					result.executionStatus=true;
				   
			   }else{
					result.errorList="Invalid visualization type!";
					result.show=true;
					result.available=true;
					result.executionStatus=false; 
			   }
			   
			   
			
		} catch (Exception e) {
			result.errorList=e.getMessage();
			result.show=true;
			result.available=true;
			result.executionStatus=false;
		}
		
	}
	
	public static String getCurrentDate(){
		String retValue="";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		java.util.Date date = new java.util.Date();
		return (dateFormat.format(date));
	}
	
	
	public static String createConfiguredModelFileName(String modelFileName){
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		return("configured_"+format.format(new Date()) + "_" + Math.abs(new Random().nextInt()))+"_"+modelFileName;

	}
	
	public static String getfeatureModelFileName(String ModelsPath, String modelName){
		String retVal="false";
		try {
			
			File  dir=new File(ModelsPath);
			String[]  childeren=dir.list();
				start:
					for (int i=0;i<childeren.length;i++){
						if (childeren!=null){
							if (childeren[i].endsWith(".xml") !=false){
		 						String  fileName=childeren[i];
		       					File modelFile = new File(ModelsPath+fileName);
		       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		       					Document doc = builder.parse(modelFile);
		       					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
		       					Element  featureModelElement = (Element) featureModelNodes.item(0);
		       					String   FMName=featureModelElement.getAttribute("name");
		       					
		       					if (FMName.compareToIgnoreCase(modelName)==0){
		       						retVal=fileName;
		       						break start;
		       					}
		       					

							}

						}

					}

		} catch (Exception e) {
			retVal="false";
			return retVal;
		}
		
		return retVal;
	}
	
	
	public static String getFeatureModelViewFileName(String viewDir , String featureModelName){
		String retValue="";
		try {
			
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
				start:
				for (int i=0;i<childeren.length;i++){
					if (childeren!=null){
						if (childeren[i].endsWith(".xml") !=false){
	 						String  fileName=childeren[i];
	       					File viewFile = new File(viewDir+fileName);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
	       				
	       					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
	       					Element  featureModelElement = (Element) featureModelNodes.item(0);
	       					String   FMName=featureModelElement.getAttribute("name");
	       					if (featureModelName.compareToIgnoreCase(FMName)==0){
	       						retValue=fileName;
	       						break start;
	       					}

						}
					}
					
				}
			
			
		} catch (Exception e) {
			retValue="false";
		}
		
		if ((retValue=="") || (retValue.isEmpty()) || (retValue==null)){
			retValue="false";
		}
	
		return retValue;
	}
	
	public static String getTaskViewName(String viewDir, String workflowName, String featureModelName, String taskName){
		String retVal="false";
		
		try {
			
			String featureModelViewFileName=getFeatureModelViewFileName(viewDir, featureModelName);
			if (featureModelViewFileName.compareTo("false")==0){
				retVal="false";
				return retVal; 
			}
					
			
			File viewFile = new File(viewDir+featureModelViewFileName);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(viewFile);

			NodeList  viewNodes=doc.getElementsByTagName("view");
			start:
			for(int i=0;i<viewNodes.getLength();i++){
					Element viewElement = (Element) viewNodes.item(i);
					NodeList  taskNodes=viewElement.getElementsByTagName("task");
					
					
					if(taskNodes.getLength()>0){
						Element   taskElement=(Element)taskNodes.item(0);
						NodeList  workflowNodes=taskElement.getElementsByTagName("workflow");
						for (int j=0;j<workflowNodes.getLength();j++){
							Element workflowElement=(Element)workflowNodes.item(j);
							if (workflowName.compareToIgnoreCase(workflowElement.getAttribute("name"))==0){
								
								NodeList workflowTaskNodes=workflowElement.getElementsByTagName("task_name");
								for (int k=0;k<workflowTaskNodes.getLength();k++){
									Element workflowTaskElement=(Element)workflowTaskNodes.item(k);

									String taskPart=getCharacterDataFromElement(workflowTaskElement).split("\\?")[0];
								

									if (taskName.compareToIgnoreCase(taskPart)==0){

										retVal=viewElement.getAttribute("name");

										break start;
									}
								}
							}
						}
					}
				
					

			}
				
			
			
			
		} catch (Exception e) {
			retVal="false";
			return retVal; 
		}
		
		if ((retVal=="") || (retVal.isEmpty()) || (retVal==null)){
			retVal="false";
		}

		return retVal;
	}
	
	
	public static boolean  taskHasView(String viewDir, String workflowName, String featureModelName, String taskName){
		boolean retVal=false;
		
		try {
			
			String featureModelViewFileName=getFeatureModelViewFileName(viewDir, featureModelName);
			if (featureModelViewFileName.compareTo("false")==0){
				retVal=false;
				return retVal; 
			}
					
			
			File viewFile = new File(viewDir+featureModelViewFileName);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(viewFile);

			NodeList  viewNodes=doc.getElementsByTagName("view");
			start:
			for(int i=0;i<viewNodes.getLength();i++){
					Element viewElement = (Element) viewNodes.item(i);
					NodeList  taskNodes=viewElement.getElementsByTagName("task");
					
					
					if(taskNodes.getLength()>0){
						Element   taskElement=(Element)taskNodes.item(0);
						NodeList  workflowNodes=taskElement.getElementsByTagName("workflow");
						for (int j=0;j<workflowNodes.getLength();j++){
							Element workflowElement=(Element)workflowNodes.item(j);
							if (workflowName.compareToIgnoreCase(workflowElement.getAttribute("name"))==0){
								
								NodeList workflowTaskNodes=workflowElement.getElementsByTagName("task_name");
								for (int k=0;k<workflowTaskNodes.getLength();k++){
									Element workflowTaskElement=(Element)workflowTaskNodes.item(k);

									String taskPart=getCharacterDataFromElement(workflowTaskElement).split("\\?")[0];
								

									if (taskName.compareToIgnoreCase(taskPart)==0){

										retVal=true;

										break start;
									}
								}
							}
						}
					}
				
					

			}
				
			
			
			
		} catch (Exception e) {
			retVal=false;
			return retVal; 
		}
		
		

		return retVal;
	}
	
	
	public static String getViewTaskName(String viewDir, String workflowName, String featureModelName, String viewName){
		String retVal="false";
		
		try {
			
			String featureModelViewFileName=getFeatureModelViewFileName(viewDir, featureModelName);
			if (featureModelViewFileName.compareTo("false")==0){
				retVal="false";
				return retVal; 
			}
					
			
			File viewFile = new File(viewDir+"/"+featureModelViewFileName);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(viewFile);

			NodeList  viewNodes=doc.getElementsByTagName("view");
			start:
			for(int i=0;i<viewNodes.getLength();i++){
					Element viewElement = (Element) viewNodes.item(i);
					if (viewElement.getAttribute("name").compareToIgnoreCase(viewName)==0){
						NodeList  taskNodes=viewElement.getElementsByTagName("task");
						if(taskNodes.getLength()>0){
							Element   taskElement=(Element)taskNodes.item(0);
							NodeList  workflowNodes=taskElement.getElementsByTagName("workflow");
							for (int j=0;j<workflowNodes.getLength();j++){
								Element workflowElement=(Element)workflowNodes.item(j);
								if (workflowName.compareToIgnoreCase(workflowElement.getAttribute("name"))==0){
									NodeList workflowTaskNodes=workflowElement.getElementsByTagName("task_name");
									Element workflowTaskElement=(Element)workflowTaskNodes.item(0);
									String taskPart=getCharacterDataFromElement(workflowTaskElement).split("\\?")[0];
									retVal=taskPart;
									break start;
								}
							}


						}

					}

			}
				
			
		} catch (Exception e) {
			retVal="false";
			return retVal; 
		}
		
		if ((retVal=="") || (retVal.isEmpty()) || (retVal==null)){
			retVal="false";
		}

		return retVal;
	}

	
	  public static String  getConfiguredFileName(String configuredModelsPath,String userKey){
		   String retVal="false";
		   	try {
				File  dir=new File(configuredModelsPath);
				String[]  childeren=dir.list();
						for (int i=0;i<childeren.length;i++){
							if (childeren!=null){
								if (childeren[i].endsWith(".xml") !=false){
			 						String  fileName=childeren[i];
			       					File confFile = new File(configuredModelsPath+"/"+fileName);
			       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			       					Document doc = builder.parse(confFile);
			       					
			       					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  
			       					Element  featureModelElement = (Element) featureModelNodes.item(0);
			       					String   FMName=featureModelElement.getAttribute("name");
			       					if (userKey.compareToIgnoreCase(featureModelElement.getAttribute("user_key"))==0){
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
	  
	  

	  public static String  getConfiguredFileName(String configuredModelsPath,String userKey, String featureModel, String workflow){
		   String retVal="false";
		   	try {
				File  dir=new File(configuredModelsPath);
				String[]  childeren=dir.list();
						for (int i=0;i<childeren.length;i++){
							if (childeren!=null){
								if (childeren[i].endsWith(".xml") !=false){
			 						String  fileName=childeren[i];
			       					File confFile = new File(configuredModelsPath+"/"+fileName);
			       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			       					Document doc = builder.parse(confFile);
			       					
			       					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  
			       					Element  featureModelElement = (Element) featureModelNodes.item(0);
			       					String   FMName=featureModelElement.getAttribute("name");
			       					String   userKeyValue=featureModelElement.getAttribute("user_key");
			       					String   workflowValue= featureModelElement.getAttribute("workflow");
			       					
			       					if ((featureModel.compareToIgnoreCase(FMName)==0)&&(workflow.compareToIgnoreCase(workflowValue)==0)&&(userKey.compareToIgnoreCase(userKeyValue)==0)){
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
		

		
	  
	 
	  
	  public static String  taskIsConfiguring(String configuredModelsDir, String configuredFileName, String taskName, String user, String sessionKey){
		  String retVal="false";
		  
		  try {

			  File   Modelfile = new File(configuredModelsDir+"/"+configuredFileName);
			  
			  if (!Modelfile.exists()){
					retVal="false";
					return retVal;
			  }
			  
			  Boolean taskFound=false;
			  
			  DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			  Document doc = builder.parse(Modelfile);
			  
			  NodeList  configurationInfoNodes = doc.getElementsByTagName("configuration_info");
			  if (configurationInfoNodes.getLength()>0){
				  
				  for (int i=0; i<configurationInfoNodes.getLength() && !taskFound; i++){
					  Element configurationInfoElement=(Element)configurationInfoNodes.item(i);
					  NodeList taskNodes= configurationInfoElement.getElementsByTagName("task");
					  Element  taskElement=(Element)taskNodes.item(0);
					  
					  NodeList userNodes= configurationInfoElement.getElementsByTagName("user");
					  Element  userElement=(Element)userNodes.item(0);
					  
					  NodeList sessionKeyNodes= configurationInfoElement.getElementsByTagName("session_key");
					  Element  sessionKeyElement=(Element)sessionKeyNodes.item(0);
					  
					  NodeList actionNodes=configurationInfoElement.getElementsByTagName("action");
					  Element  actionElement=(Element)actionNodes.item(0);
					  
					  String taskNameValue=getCharacterDataFromElement(taskElement);
					  String userNameValue=getCharacterDataFromElement(userElement);
					  String sessionKeyValue=getCharacterDataFromElement(sessionKeyElement);
					  String actionValue=getCharacterDataFromElement(actionElement);
					  if ((taskNameValue.compareToIgnoreCase(taskName)==0)&& (userNameValue.compareToIgnoreCase(user)==0) &&(sessionKeyValue.compareToIgnoreCase(sessionKey)==0) && (actionValue.compareToIgnoreCase("configuring")==0)){
						  taskFound=true;
						  retVal="true";
						  return retVal;
					  }

					  
				  }
				  
			  }else{
				  retVal="false";
				  return retVal;  
			  }
		
			  
		if(!taskFound){
			 retVal="false";
			  return retVal; 
		}
			
		} catch (Exception e) {
			retVal="false";
			  return retVal; 
			
		}
		  
		  return retVal;
	  }
	  
	  public static String getTaskConfigurationResult(String configuredModelsDir, String configuredFileName, String taskName, String user, String sessionKey){
		  String retVal="";
		  
		  try {
			
			  File   Modelfile = new File(configuredModelsDir+"/"+configuredFileName);
			  
			  if (!Modelfile.exists()){
					retVal="false";
					return retVal;
			  }
				
			  DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			  Document doc = builder.parse(Modelfile);
			  
			  NodeList  configurationInfoNodes = doc.getElementsByTagName("configuration_info");
			  if (configurationInfoNodes.getLength()>0){
				  
			
				  int maxRow=0;
				  for (int i=0; i<configurationInfoNodes.getLength(); i++){
					  Element configurationInfoElement=(Element)configurationInfoNodes.item(i);
					  NodeList taskNodes= configurationInfoElement.getElementsByTagName("task");
					  Element  taskElement=(Element)taskNodes.item(0);
					  
					  NodeList userNodes= configurationInfoElement.getElementsByTagName("user");
					  Element  userElement=(Element)userNodes.item(0);
					  
					  NodeList sessionKeyNodes= configurationInfoElement.getElementsByTagName("session_key");
					  Element  sessionKeyElement=(Element)sessionKeyNodes.item(0);
					  
					  
					  String taskNameValue=getCharacterDataFromElement(taskElement);
					  String userNameValue=getCharacterDataFromElement(userElement);
					  String sessionKeyValue=getCharacterDataFromElement(sessionKeyElement);
					  
					  if ((taskNameValue.compareToIgnoreCase(taskName)==0)&& (userNameValue.compareToIgnoreCase(user)==0) &&(sessionKeyValue.compareToIgnoreCase(sessionKey)==0)){
						  if (Integer.parseInt(configurationInfoElement.getAttribute("row"))>maxRow){

							  maxRow=Integer.parseInt(configurationInfoElement.getAttribute("row"));
							  retVal="";
							  NodeList dateNodes= configurationInfoElement.getElementsByTagName("date");
							  Element  dateElement=(Element)dateNodes.item(0);
							  
							  
							  NodeList actionNodes= configurationInfoElement.getElementsByTagName("action");
							  Element  actionElement=(Element)actionNodes.item(0);
							  
							  NodeList sessionNodes= configurationInfoElement.getElementsByTagName("session");
							  Element  sessionElement=(Element)sessionNodes.item(0);
							  
							  retVal=getCharacterDataFromElement(dateElement)+","+getCharacterDataFromElement(userElement)+","+getCharacterDataFromElement(actionElement)+","+getCharacterDataFromElement(sessionElement);

							  
						  } 
					  }
				  }
				  
				  
				  
				  
				
				  
				  
			  }else{
				  retVal="false";
				  return retVal;
			  }
			  
			  
		} catch (Exception e) {
			retVal="false";
		}
		  
		  
		  return retVal;
		  
	  }
	  

	  
		public static String getWorkflowTasks(String workflowName, String workflowDir){
			String retValue="false";
			String taskList="";
			File  dir=new File(workflowDir);
			String[]  childeren=dir.list();
			try {
	   			for (int i=0;i<childeren.length;i++){
	   				if (childeren!=null){
	   					if ((childeren[i].endsWith(".xml") !=false)  || (childeren[i].endsWith(".yawl") !=false)  ){
	   						String  fileName=childeren[i];
	       					File workflowFile = new File(workflowDir+"/"+fileName);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(workflowFile);
	       					
	       					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
	       					Element specificationElement = (Element) specificationNodes.item(0);
	       					if (specificationElement.getAttribute("uri").trim().compareToIgnoreCase(workflowName)==0){
	       						NodeList taskNodes = doc.getElementsByTagName("task");
	       						for (int j = 0; j < taskNodes.getLength(); j++) {
	       								Element element = (Element) taskNodes.item(j);
	       								NodeList title = element.getElementsByTagName("name");
	       								if (title.getLength()>=1){
	       									Element line = (Element) title.item(0);
	           								String taskName=Methods.getCharacterDataFromElement(line);
	           								if (taskList==""){
	           									taskList=taskName;
	           								}else{
	           									taskList=taskList+","+taskName;
	           								}
	       								}
	       						}
						  }
	   					}
	   					
	   				}
	   			}

				
				
			} catch (Exception e) {
				retValue="false";
			}
				
			

			retValue=taskList;
			return retValue; 
		}
	  
	  public static String getTaskLastConfigurationResult(String configuredModelsDir, String configuredFileName, String taskName){
		  String retVal="";
		  
		  try {
			
			  File   Modelfile = new File(configuredModelsDir+"/"+configuredFileName);
			  
			  if (!Modelfile.exists()){
					retVal="false";
					return retVal;
			  }
				
			  DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			  Document doc = builder.parse(Modelfile);
			  
			  NodeList  configurationInfoNodes = doc.getElementsByTagName("configuration_info");
			  if (configurationInfoNodes.getLength()>0){
				  
			
				  int maxRow=0;
				  for (int i=0; i<configurationInfoNodes.getLength(); i++){
					  Element configurationInfoElement=(Element)configurationInfoNodes.item(i);
					  NodeList taskNodes= configurationInfoElement.getElementsByTagName("task");
					  Element  taskElement=(Element)taskNodes.item(0);
					  
					
					  
					  
					  String taskNameValue=getCharacterDataFromElement(taskElement);
				
					  
					  if ((taskNameValue.compareToIgnoreCase(taskName)==0)){
						  if (Integer.parseInt(configurationInfoElement.getAttribute("row"))>maxRow){

							  maxRow=Integer.parseInt(configurationInfoElement.getAttribute("row"));
							  retVal="";
							  NodeList dateNodes= configurationInfoElement.getElementsByTagName("date");
							  Element  dateElement=(Element)dateNodes.item(0);
							  
							  
							  NodeList actionNodes= configurationInfoElement.getElementsByTagName("action");
							  Element  actionElement=(Element)actionNodes.item(0);
							  
							  NodeList userNameNodes= configurationInfoElement.getElementsByTagName("user_name");
							  Element  userNameElement=(Element)userNameNodes.item(0);
							  
							  NodeList taskTypeNodes= configurationInfoElement.getElementsByTagName("type");
							  Element  taskTypeElement=(Element)taskTypeNodes.item(0);
							  
							  retVal=taskName+","+getCharacterDataFromElement(taskTypeElement)+","+getCharacterDataFromElement(dateElement)+","+getCharacterDataFromElement(userNameElement)+","+getCharacterDataFromElement(actionElement);

							  
						  } 
					  }
				  }
				  
				  
				  
				  
				
				  
				  
			  }else{
				  retVal="false";
				  return retVal;
			  }
			  
			  
		} catch (Exception e) {
			retVal="false";
		}
		  
		  if (retVal==""){
			  retVal="false";
		  }
		
		  return retVal;
		  
	  }
	  
	  
	  
	  
	  
		public static FeatureDecisionInfo getFeatureDecisionInfo(String modelDir, String userKey, String featureID){
			
			FeatureDecisionInfo result=new FeatureDecisionInfo();

			try {
				String configurationFileName=Methods.getConfiguredFileName(modelDir+"/configured_models", userKey);
				if (configurationFileName.compareToIgnoreCase("false")==0){
					
					result.found=false;
					return result;
				}
				
				File confFile = new File(modelDir+"/configured_models/"+configurationFileName);
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = builder.parse(confFile);
				
				NodeList  featureList=doc.getElementsByTagName("feature");
				
				
				if (!(featureList.getLength()>0)){
					result.found=false;
					return result;
				}
				
				Boolean found=false;
				for(int i=0; i<featureList.getLength() && !(found) ;i++){
					Element featureElement=(Element) featureList.item(i);
					
					if (featureElement.getAttribute("id").compareToIgnoreCase(featureID)==0){
						result.found=true;
						result.editable=featureElement.getAttribute("editable");
						found=true;
						
						NodeList featureDecisionList=featureElement.getElementsByTagName("decision_info");
						int maxRow=0;
						for (int j=0; j<featureDecisionList.getLength();j++){
							Element featureDecisionElement=(Element) featureDecisionList.item(j);
							if (Integer.parseInt(featureDecisionElement.getAttribute("row"))>maxRow){
								maxRow=Integer.parseInt(featureDecisionElement.getAttribute("row"));
							}
						}
						
						
						for (int j=0; j<featureDecisionList.getLength();j++){
							Element featureDecisionElement=(Element) featureDecisionList.item(j);
							if (Integer.parseInt(featureDecisionElement.getAttribute("row"))==maxRow){

								NodeList dateList=featureDecisionElement.getElementsByTagName("date");
								Element  dateElement=(Element) dateList.item(0);
								
								NodeList sessionList=featureDecisionElement.getElementsByTagName("session");
								Element  sessionElement=(Element)sessionList.item(0);
								
								NodeList taskList=featureDecisionElement.getElementsByTagName("task");
								Element  taskElement=(Element)taskList.item(0);
								
								NodeList userNameList=featureDecisionElement.getElementsByTagName("user_name");
								Element  userNameElement=(Element)userNameList.item(0);
								
								NodeList userIDList=featureDecisionElement.getElementsByTagName("user_id");
								Element  userIDElement=(Element)userIDList.item(0);
								
								
								NodeList userList=featureDecisionElement.getElementsByTagName("user_id");
								Element  userElement=(Element)userIDList.item(0);
								
								NodeList valueList=featureDecisionElement.getElementsByTagName("value");
								Element  valueElement=(Element)valueList.item(0);
								
								

								NodeList viewList=featureDecisionElement.getElementsByTagName("view");
								Element  viewElement=(Element)viewList.item(0);
								

								NodeList typeList=featureDecisionElement.getElementsByTagName("type");
								Element  typeElement=(Element)typeList.item(0);
								

								NodeList stepList=featureDecisionElement.getElementsByTagName("step");
								Element  stepElement=(Element)stepList.item(0);
								

								
								result.date=Methods.getCharacterDataFromElement(dateElement);
								result.session=Methods.getCharacterDataFromElement(sessionElement);
								result.step=Methods.getCharacterDataFromElement(stepElement);
								result.task=Methods.getCharacterDataFromElement(taskElement);
								result.type=Methods.getCharacterDataFromElement(typeElement);
								result.userName=Methods.getCharacterDataFromElement(userNameElement);
								result.userID=Methods.getCharacterDataFromElement(userIDElement);

								result.value=Methods.getCharacterDataFromElement(valueElement);
								result.view=Methods.getCharacterDataFromElement(viewElement);
								
								
								
							}
						}
						
					}
				}
				
			
			if (!(found)){
				result.found=false;
				
			}
				
			} catch (Exception e) {
				result.found=false;
				e.printStackTrace();
				// TODO: handle exception
			}
			
			

			
			
			return result;
		}
		
		public static String getWorkflowName(String workflowFilePath){
			String retVal="";
			try {
				File baseFile=new File(workflowFilePath);
				DocumentBuilder baseBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document baseDoc = baseBuilder.parse(baseFile);
				
				
		    	NodeList specificationSetNodes=baseDoc.getElementsByTagName("specificationSet");
		    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
				
				NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
		    	Element  specificationElement=(Element)specificationNodes.item(0);

		    	retVal=specificationElement.getAttribute("uri");
		    	return (retVal);

			} catch (Exception e) {
				retVal="";
			}
			
			return retVal;
		}
		
		public static void transform (String  baseFilePath , String viewFilePath,  String transformedFilePath) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException{
			
			
				File baseFile=new File(baseFilePath);
				DocumentBuilder baseBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document baseDoc = baseBuilder.parse(baseFile);
				
				
				
				StringWriter stw = new StringWriter(); 
				Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
				serializer.transform(new DOMSource(baseDoc), new StreamResult(stw)); 
				String xmlData=stw.toString();
					
				FileWriter fw=new FileWriter(transformedFilePath);
				fw.write(xmlData);
				fw.close();  

				String retValue=buildInitCheck(transformedFilePath);
				System.out.println("1:"+retValue);

				if (retValue.compareToIgnoreCase("true")==0){
					retValue=BuildFinalCheck(transformedFilePath);
					System.out.println("2:"+retValue);

					if (retValue.compareToIgnoreCase("true")==0){
						String workflowName=getWorkflowName(baseFilePath);
						if (workflowName!=""){
							retValue=updateFinalCondition(viewFilePath, workflowName, "Final_Stop", "end");
							System.out.println("3:"+retValue);

							if (retValue.compareToIgnoreCase("true")==0){
								retValue=runConditionsCheck(baseFilePath, viewFilePath, transformedFilePath);
								System.out.println("4:"+retValue);

								if (retValue.compareToIgnoreCase("true")==0){
									retValue=runTaskCheck(baseFilePath, viewFilePath, transformedFilePath);
									System.out.println("5:"+retValue);

								}
							}
						}
						
						
					}
 
	
				}
				
				
		}
		
		
	
		public static  String  findLasts(String transformedFilePath){
			String retVal="";
			String outputConditionID=""; 
			
			try {
				File transformedFile=new File(transformedFilePath);
				DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document transformedDoc = transformedBuilder.parse(transformedFile);

			
		    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
		    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
				
				NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
		    	Element  specificationElement=(Element)specificationNodes.item(0);
		    	
		    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");

		    	
		    	
		    	
		    	
		    	
		    	
		    	if (decompositionNodes.getLength()==1){
		    		
		    		Element decompositionElement=(Element)decompositionNodes.item(0);
		    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		    		Element   processControlElement=(Element)processControlNodes.item(0);
		    		
			    	// find output condition's id
		    		
		    		NodeList  outputConditionNodes=processControlElement.getElementsByTagName("outputCondition");
		    		Element   outputConditionElement=(Element)outputConditionNodes.item(0);
		    		outputConditionID=outputConditionElement.getAttribute("id");
		    		
		    		
		    		
			    	// find nodes point to output condition
		       		
		    		//input condition
		    		NodeList inputConditionNodes=processControlElement.getElementsByTagName("inputCondition");
		    		Element  inputConditionElement=(Element)inputConditionNodes.item(0);
		    		
		    		NodeList inputConditionFlowsIntoNodes=inputConditionElement.getElementsByTagName("flowsInto");
		    		for(int i=0; i<inputConditionFlowsIntoNodes.getLength();i++){
		    			Element  inputConditionFlowsIntoElement=(Element)inputConditionFlowsIntoNodes.item(i);
		    			
		    			NodeList inputConditionFlowsIntoNextNodes=inputConditionFlowsIntoElement.getElementsByTagName("nextElementRef");
		    			Element  inputConditionFlowsIntoNextElement=(Element) inputConditionFlowsIntoNextNodes.item(0);
		    			if (inputConditionFlowsIntoNextElement.getAttribute("id").compareToIgnoreCase(outputConditionID)==0){
		    				if (retVal==""){
		    					retVal=inputConditionElement.getAttribute("id");
		    				}else{
		    					retVal=retVal+","+inputConditionElement.getAttribute("id");

		    				}
		    			}
		    		}

		    		
		    		
		    		// conditions
		    		NodeList  conditionNodes=processControlElement.getElementsByTagName("condition");
		    		for(int i=0; i<conditionNodes.getLength();i++){
		    			Element conditionElement=(Element)conditionNodes.item(i);
		    			
		    			NodeList  conditionFlowsIntoNode=conditionElement.getElementsByTagName("flowsInto");
		    			for(int j=0; j<conditionFlowsIntoNode.getLength();j++){
		    				Element conditionFlowsIntoElement=(Element)conditionFlowsIntoNode.item(j);
		    				
		    				NodeList  conditionFlowsIntoNextNode=conditionFlowsIntoElement.getElementsByTagName("nextElementRef");
		    				Element   conditionFlowsIntoNextElement=(Element)conditionFlowsIntoNextNode.item(0);
		    				
		    				if (conditionFlowsIntoNextElement.getAttribute("id").compareToIgnoreCase(outputConditionID)==0){
		    					if (retVal==""){
			    					retVal=conditionElement.getAttribute("id");
			    				}else{
			    					retVal=retVal+","+conditionElement.getAttribute("id");

			    				}
		    					
		    				}
		    			}
		    		}
		    	
		    		
		    		// tasks
		    			NodeList taskNodes=processControlElement.getElementsByTagName("task");
		    			for (int i=0; i<taskNodes.getLength();i++){
		    				Element  taskElement=(Element)taskNodes.item(i);
		    				
		    				NodeList  taskFlowsIntoNodes=taskElement.getElementsByTagName("flowsInto");
		    				for (int j=0; j<taskFlowsIntoNodes.getLength();j++){
		    					Element taskFlowsIntoElement=(Element)taskFlowsIntoNodes.item(j);
		    					
		    					NodeList  taskFlowsIntoNextNode=taskFlowsIntoElement.getElementsByTagName("nextElementRef");
		    					Element   taskFlowsIntoNextElement=(Element)taskFlowsIntoNextNode.item(0);
		    					
		    					if (taskFlowsIntoNextElement.getAttribute("id").compareToIgnoreCase(outputConditionID)==0){
		    						if (retVal==""){
				    					retVal=taskElement.getAttribute("id");
				    				}else{
				    					retVal=retVal+","+taskElement.getAttribute("id");

				    				}
		    					}
		    					
		    					
		    					
		    				}
		    				
		    				
		    			}
		    		

		    		
		    		
		    		
		    	}else{// There are  more decomposition nodes.
		    		
		    	}

				
				
			} catch (Exception e) {
				retVal="";
			}
			
			return retVal;
		}
		
		public static String findElementsLastID(String transformedFilePath) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException	{
		String retVal="1";
		
		int defaultID=1;
		
		File transformedFile=new File(transformedFilePath);
		DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document transformedDoc = transformedBuilder.parse(transformedFile);

	
    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
		
		NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
    	Element  specificationElement=(Element)specificationNodes.item(0);
    	
    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");

    	if (decompositionNodes.getLength()==1){
    		
    		Element decompositionElement=(Element)decompositionNodes.item(0);
    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
    		Element   processControlElements=(Element)processControlNodes.item(0);
    		
       		NodeList inputConditionNodes=processControlElements.getElementsByTagName("inputCondition");
    		Element  inputConditionElement=(Element)inputConditionNodes.item(0);
    		
    		String   inputConditionElementID=inputConditionElement.getAttribute("id");

    		String[]      list=inputConditionElementID.split("\\_");
    		
    		int 		  inputID=Integer.parseInt(list[list.length-1]);
    		
    		if (inputID>defaultID){
    			defaultID=inputID;
    		}
    		

    		
    		NodeList  outputConditionNodes=processControlElements.getElementsByTagName("outputCondition");
    		Element   outputConditionElement=(Element)outputConditionNodes.item(0);
    		
    		String    outputConditionElementID=outputConditionElement.getAttribute("id");
    		
    		list=outputConditionElementID.split("\\_");
    		
    		int      outputID=Integer.parseInt(list[list.length-1]);
    		
    		if(outputID>defaultID){
    			defaultID=outputID;
    		}
    		
    		
    		
    		NodeList  taskList=processControlElements.getElementsByTagName("task");
    		if (taskList.getLength()>0){
    			for (int i=0;i<taskList.getLength();i++){
    				Element  taskElement=(Element)taskList.item(i);
    				
    				String  taskElementID=taskElement.getAttribute("id");
    				list=taskElementID.split("\\_");
    				
    				int   taskID=Integer.parseInt(list[list.length-1]);
    				
    				if (taskID>defaultID){
    					defaultID=taskID;
    				}
    				
    				
    				
    				
    			}
    		}
    		
    		
    		
    		NodeList  conditionList=processControlElements.getElementsByTagName("condition");
    		if(conditionList.getLength()>0){
    			for(int i=0;i<conditionList.getLength();i++){
    				Element conditionElement=(Element)conditionList.item(i);
    				
    				String conditionElementID=conditionElement.getAttribute("id");
    				list=conditionElementID.split("\\_");
    				
    				int conditionID=Integer.parseInt(list[list.length-1]);
    				
    				if (conditionID>defaultID){
    					defaultID=conditionID;
    				}
    			}
    		}
    		
    		
    		
    		retVal=Integer.toString((++defaultID));
    		
    		
    		
    	}else{// there are more decomposition nodes
    		
    	}
    	
		
		return retVal;
	}
		
	public static String incrementContainerBounds(String initialValue, String incrementalValue){
		String retVal=initialValue;
		
		try {
			
			retVal=String.valueOf(Integer.parseInt(initialValue.split("\\.")[0])+Integer.parseInt(incrementalValue))+".0";
			
		} catch (Exception e) {
			 retVal=initialValue;
		}
		
		return retVal;
	}	
	public static String buildInitCheck(String transformedFilePath) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException, TransformerException{
		String retVal="false";
		
	try {
		
		
		String  initTaskID="init_"+findElementsLastID(transformedFilePath);
		
		
		File transformedFile=new File(transformedFilePath);
		DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document transformedDoc = transformedBuilder.parse(transformedFile);

	
    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
		
		NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
    	Element  specificationElement=(Element)specificationNodes.item(0);
    	
    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
    	if (decompositionNodes.getLength()==1){   		
    		Element decompositionElement=(Element)decompositionNodes.item(0);
    		
    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
    		Element   processControlElements=(Element)processControlNodes.item(0);
  
    		
    		String nextElementRefID="";


    	//remove the link between the input condition and firsts  
    		NodeList inputConditionNodes=processControlElements.getElementsByTagName("inputCondition");
    		Element  inputConditionElement=(Element)inputConditionNodes.item(0);
    		String   inputConditionElementID=inputConditionElement.getAttribute("id");
    		NodeList flowsIntoNodes=inputConditionElement.getElementsByTagName("flowsInto");
    		for(int i=0;i<flowsIntoNodes.getLength();i++){
    			Element flowsIntoElement=(Element)flowsIntoNodes.item(i);
    			NodeList nextElementRefNodes=flowsIntoElement.getElementsByTagName("nextElementRef");
    			Element nextElementRefElement=(Element)nextElementRefNodes.item(0);
    			if(nextElementRefID==""){
    				nextElementRefID=nextElementRefElement.getAttribute("id");
    			}else{
    				nextElementRefID=nextElementRefID+","+nextElementRefElement.getAttribute("id");
    			}
				inputConditionElement.removeChild(flowsIntoElement);
    		}
    		
    		
    		
    		
   		//add the init task    		
    		Element initTaskElement=createWorkflowTask(transformedDoc, initTaskID, "init", "xor", "and", nextElementRefID);
    		processControlElements.appendChild(initTaskElement);

    			
    	// add the link (i,init)	
    		Element  newFlowsIntoElement =transformedDoc.createElement("flowsInto");
    		Element  newNextElementRefElement=transformedDoc.createElement("nextElementRef");
    		newNextElementRefElement.setAttribute("id", initTaskID);
    		newFlowsIntoElement.appendChild(newNextElementRefElement);
    		inputConditionElement.appendChild(newFlowsIntoElement);
    		

    	
 
    		
    	/// layout
    		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
    		Element   layoutElement=(Element)layoutNodes.item(0);
    		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
    		if (layoutSpecificationNodes.getLength()==1){
    			Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
    			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
    			if (layoutNetNodes.getLength()==1){
    				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
    				
    				
    	    		// init container
    				String inputConditionXY=getWorkflowElementContainerAttributeXY(transformedFilePath, inputConditionElementID);
    				String inputConditionX=inputConditionXY.split("\\,")[0];
    				String inputConditionY=inputConditionXY.split("\\,")[1];
    				Element initTaskContainerElement=createTaskContainer(transformedDoc, initTaskID, incrementContainerBounds(inputConditionX, "50"), inputConditionY, "", "AND_split");
    				layoutNetElement.appendChild(initTaskContainerElement);
    				
    				
				
    			
    				// remove flows between (i, first)
    				NodeList layoutFlowNodes=layoutNetElement.getElementsByTagName("flow");
    				for(int i=0;i<layoutFlowNodes.getLength();i++){
    					Element layoutFlowElement=(Element)layoutFlowNodes.item(i);
    					if (inputConditionElementID.compareToIgnoreCase(layoutFlowElement.getAttribute("source"))==0){
    						layoutNetElement.removeChild(layoutFlowElement);
    					}
    					
    				}
    				
   
    				// add flow between (i,init)
    				Element InputConditionInitFlowElement=createFlowElement(transformedDoc, inputConditionElementID, initTaskID, "12", "13", null);
    				layoutNetElement.appendChild(InputConditionInitFlowElement);
    				
    				
    				// add flow between (init,first)

    				String[] first=nextElementRefID.split("\\,");
    				for(int i=0;i<first.length;i++){
    					Element flowElement=createFlowElement(transformedDoc, initTaskID, first[i], "12", "13", null);
    					layoutNetElement.appendChild(flowElement);
    				}	
    					
    			}else{//There are more than one net
    				
    			}
    			
    			
    		}else{// there are more than one specification node in layout
    			
    		}
    	}else{// if there are more decomposition nodes
    		
    	}
    		
    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
	    Transformer transformer=transformerFactory.newTransformer();
	    DOMSource source=new DOMSource(transformedDoc);
	    StreamResult result=new StreamResult(transformedFile);
	    transformer.transform(source, result);
	    retVal="true";
    	
	} catch (Exception e) {
	    retVal="false";
	}

		
		return retVal;
	}
	
	
	
	public static Element createTaskContainer(Document doc, String elementID, String x, String y , String joinType,  String splitType){
		Element taskContainerElement=null;
		
		try {
			
			taskContainerElement=doc.createElement("container");
			taskContainerElement.setAttribute("id", elementID);
			
			Element vertexElement=doc.createElement("vertex");
			
			Element vertexAttributeElement=doc.createElement("attributes");
			
			Element vertexAttributeBoundsElement=doc.createElement("bounds");
			vertexAttributeBoundsElement.setAttribute("x", x);
			vertexAttributeBoundsElement.setAttribute("y", y);
			vertexAttributeBoundsElement.setAttribute("w", "32.0");
			vertexAttributeBoundsElement.setAttribute("h", "32.0");
			
			vertexAttributeElement.appendChild(vertexAttributeBoundsElement);
			vertexElement.appendChild(vertexAttributeElement);
			taskContainerElement.appendChild(vertexElement);
			
			
			String labelXY=calculateElementLabelXY(x, y);
			
			Element labelElement=doc.createElement("label");
			
			Element labelAttributeElement=doc.createElement("attributes");
			
			Element labelAttributeBoundsElement=doc.createElement("bounds");
			labelAttributeBoundsElement.setAttribute("x", labelXY.split("\\,")[0]);
			labelAttributeBoundsElement.setAttribute("y", labelXY.split("\\,")[1]);
			labelAttributeBoundsElement.setAttribute("w", "97.0");
			labelAttributeBoundsElement.setAttribute("h", "21.0");

			labelAttributeElement.appendChild(labelAttributeBoundsElement);
			labelElement.appendChild(labelAttributeElement);
			taskContainerElement.appendChild(labelElement);
			
			
			if (joinType!=""){
				Element joinDecoratorElement =doc.createElement("decorator");
				joinDecoratorElement.setAttribute("type", joinType);
				
				Element joinPositionElement=doc.createElement("position");
				joinPositionElement.appendChild(doc.createTextNode("12"));
				
				
				Element joinAttributesElement=doc.createElement("attributes");
				
				Element joinAttributeBoundsElement=doc.createElement("bounds");
				
				String joinXY=calculateElementJoinXY(x, y);
				
				joinAttributeBoundsElement.setAttribute("x", joinXY.split("\\,")[0]);
				joinAttributeBoundsElement.setAttribute("y", joinXY.split("\\,")[1]);
				joinAttributeBoundsElement.setAttribute("w", "11.0");
				joinAttributeBoundsElement.setAttribute("h", "32.0");
				
				Element joinAttributeSizeElement=doc.createElement("size");
				joinAttributeSizeElement.setAttribute("w", "11");
				joinAttributeSizeElement.setAttribute("h", "32");
				
				joinAttributesElement.appendChild(joinAttributeBoundsElement);
				joinAttributesElement.appendChild(joinAttributeSizeElement);
				
				
				joinDecoratorElement.appendChild(joinPositionElement);
				joinDecoratorElement.appendChild(joinAttributesElement);
				taskContainerElement.appendChild(joinDecoratorElement);
			}
			
			
			
			if (splitType!=""){
				
				Element splitDecoratorElement =doc.createElement("decorator");
				splitDecoratorElement.setAttribute("type", splitType);
				
				Element splitPositionElement=doc.createElement("position");
				splitPositionElement.appendChild(doc.createTextNode("13"));
				
				
				Element splitAttributesElement=doc.createElement("attributes");
				
				Element splitAttributeBoundsElement=doc.createElement("bounds");
				

				String splitXY=calculateElementJoinXY(x, y);
				
				splitAttributeBoundsElement.setAttribute("x", splitXY.split("\\,")[0]);
				splitAttributeBoundsElement.setAttribute("y", splitXY.split("\\,")[1]);
				splitAttributeBoundsElement.setAttribute("w", "11.0");
				splitAttributeBoundsElement.setAttribute("h", "32.0");
				
				Element splitAttributeSizeElement=doc.createElement("size");
				splitAttributeSizeElement.setAttribute("w", "11");
				splitAttributeSizeElement.setAttribute("h", "32");
				
				splitAttributesElement.appendChild(splitAttributeBoundsElement);
				splitAttributesElement.appendChild(splitAttributeSizeElement);
				
				
				splitDecoratorElement.appendChild(splitPositionElement);
				splitDecoratorElement.appendChild(splitAttributesElement);
				taskContainerElement.appendChild(splitDecoratorElement);
				
				
				
			}
			
			
			
		} catch (Exception e) {
			taskContainerElement=null;
		}
		
		return taskContainerElement;
	}
	
	
	public static Element createFlowElement(Document doc, String source, String target , String inPortNo, String outPortNo , String[] x){
		Element flowElement=null;
		
		try {
			
			flowElement=doc.createElement("flow");
			flowElement.setAttribute("source", source);
			flowElement.setAttribute("target", target);
			
			Element portsElement=doc.createElement("ports");
			portsElement.setAttribute("in", inPortNo);
			portsElement.setAttribute("out", outPortNo);
			
			
			Element attributesElement=doc.createElement("attributes");
			
			Element lineStyleElement=doc.createElement("lineStyle");
			lineStyleElement.appendChild(doc.createTextNode("11"));
			
//			Element pointsElement=doc.createElement("points");
//			
//			int valueNo=pointValues.length / 2;
//			int index=0;
//			for (int i=0; i<valueNo;i++){
//				Element valueElement=doc.createElement("value");
//				valueElement.setAttribute("x", pointValues[index++]);
//				valueElement.setAttribute("y", pointValues[index++]);
//				
//				pointsElement.appendChild(valueElement);
//			}
//			
//			attributesElement.appendChild(pointsElement);
			attributesElement.appendChild(lineStyleElement);
			
			flowElement.appendChild(portsElement);
			flowElement.appendChild(attributesElement);
			
			
		} catch (Exception e) {
			flowElement=null;
		}
		
		return flowElement;
	}
	
	public static Element createWorkflowTask(Document doc, String taskID, String taskName, String joinCode, String splitCode, String nextElementRefID){
		Element taskElement=null;
		
		try {
			
			taskElement=doc.createElement("task");
			taskElement.setAttribute("id", taskID);
			
			Element taskNameElement=doc.createElement("name");
			taskNameElement.appendChild(doc.createTextNode(taskName));
			
			Element taskJoinElement=doc.createElement("join");
			taskJoinElement.setAttribute("cocde", joinCode);
			
			Element taskSplitElement=doc.createElement("split");
			taskSplitElement.setAttribute("code", splitCode);
			
			
			taskElement.appendChild(taskNameElement);
			taskElement.appendChild(taskJoinElement);
			taskElement.appendChild(taskSplitElement);
			
			if (nextElementRefID!=""){
				String[] nextElements=nextElementRefID.split("\\,");
				for (int i=0;i<nextElements.length;i++){
					Element flowsIntoElement=doc.createElement("flowsInto");
					
					Element nextElementRefElement=doc.createElement("nextElementRef");
					nextElementRefElement.setAttribute("id", nextElements[i]);
					
					flowsIntoElement.appendChild(nextElementRefElement);
					taskElement.appendChild(flowsIntoElement);
				}
			}
			
		
			
			
		} catch (Exception e) {
			taskElement=null;
		}
		
		return taskElement;
	}
	
	public static Element createWorkflowConditionElement(Document doc, String conditionID, String conditionName,String nextElementRefID ){
		Element conditionElement=null;
		
		try {
			conditionElement=doc.createElement("condition");
			conditionElement.setAttribute("id", conditionID);
			
			Element conditionNameElement=doc.createElement("name");
			conditionNameElement.appendChild(doc.createTextNode(conditionName));
			
			conditionElement.appendChild(conditionNameElement);
			
			if(nextElementRefID!=""){
				String[] nextElements=nextElementRefID.split("\\,");
				for (int i=0;i<nextElements.length;i++){
					Element flowsIntoElement=doc.createElement("flowsInto");
					
					Element nextElementRefElement=doc.createElement("nextElementRef");
					nextElementRefElement.setAttribute("id", nextElements[i]);
					
					flowsIntoElement.appendChild(nextElementRefElement);
					conditionElement.appendChild(flowsIntoElement);
				}
			}
			
			
			
			
			
		} catch (Exception e) {
			conditionElement=null;
		}
		
		return conditionElement;
	}
	
	public static Element createConditionContainer(Document doc, String elementID, String x,String y ){
		
		Element conditionContainerElement=null;
		
		try {
			
			conditionContainerElement=doc.createElement("container");
			conditionContainerElement.setAttribute("id", elementID);
			
			
			Element vertexElement=doc.createElement("vertex");
			
			Element vertexAttributeElement=doc.createElement("attributes");
			
			Element vertexAttributeBoundsElement=doc.createElement("bounds");
			vertexAttributeBoundsElement.setAttribute("x", x);
			vertexAttributeBoundsElement.setAttribute("y",y);
			vertexAttributeBoundsElement.setAttribute("w", "32.0");
			vertexAttributeBoundsElement.setAttribute("h", "32.0");
			
			vertexAttributeElement.appendChild(vertexAttributeBoundsElement);
			vertexElement.appendChild(vertexAttributeElement);
			conditionContainerElement.appendChild(vertexElement);
			
			
			
			String labelXY=calculateElementLabelXY(x, y);
			
			Element labelElement=doc.createElement("label");
			
			Element labelAttributeElement=doc.createElement("attributes");
			
			Element labelAttributeBoundsElement=doc.createElement("bounds");
			labelAttributeBoundsElement.setAttribute("x", labelXY.split("\\,")[0]);
			labelAttributeBoundsElement.setAttribute("y", labelXY.split("\\,")[1]);
			labelAttributeBoundsElement.setAttribute("w", "97.0");
			labelAttributeBoundsElement.setAttribute("h","21.0");

			labelAttributeElement.appendChild(labelAttributeBoundsElement);
			labelElement.appendChild(labelAttributeElement);
			conditionContainerElement.appendChild(labelElement);
			
			
			
			
		} catch (Exception e) {
			conditionContainerElement=null;
		}
		
		return conditionContainerElement;
		
	}
	
	public static String BuildFinalCheck(String transformedFilePath){
		String retVal="false";
		
		try {
			
			String  taskID=findElementsLastID(transformedFilePath);
			String  checkOutTaskID="check_out_"+taskID;
			String  exitTaskID="exit_"+String.valueOf(Integer.parseInt(taskID)+1);
			String  endConditionID="end_"+String.valueOf(Integer.parseInt(taskID)+2);
			
			
			File transformedFile=new File(transformedFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);

		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");

	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElements=(Element)processControlNodes.item(0);

	    		NodeList  outputConditionNodes=processControlElements.getElementsByTagName("outputCondition");
	    		Element   outputConditionElement=(Element)outputConditionNodes.item(0);
	    		String outputConditionID=outputConditionElement.getAttribute("id");

	    		// find the nodes who point to the final condition
	    		String lasts=findLasts(transformedFilePath);

	    		
	       		//add the check_out task
	    		Element   checkoutTaskElement=createWorkflowTask(transformedDoc, checkOutTaskID, "check_out", "xor", "and","");
	    		
	    		
	       		//add the exit task
	    		Element exitTaskElement=createWorkflowTask(transformedDoc, exitTaskID, "exit", "and", "and", outputConditionID);
	    		

	       		//add the end condition
	    		Element endConditionElement=createWorkflowConditionElement(transformedDoc, endConditionID, "end", checkOutTaskID);
	    	

	    		
	    		// change the link between (last,outputCondition) to (last,end)
	    		if ((lasts!="") && (lasts!=null)){
	    			String[]  lastNodes=lasts.split("\\,");
	    			for (int i=0; i< lastNodes.length;i++){
	    				String nodeID=lastNodes[i];
	    				
		    			NodeList  processControlElementChilderen=processControlElements.getElementsByTagName("task");
	    				for (int j=0; j<processControlElementChilderen.getLength();j++){
	    					Element  childElement=(Element)processControlElementChilderen.item(j);
	    					if (childElement.getAttribute("id").compareToIgnoreCase(nodeID)==0){
	    						NodeList  flowsIntoNodes=childElement.getElementsByTagName("flowsInto");
	    						for (int k=0; k<flowsIntoNodes.getLength();k++){
	    							Element flowsIntoElement=(Element)flowsIntoNodes.item(k);
	    							
	    							NodeList  nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
	    							Element   nextElementRefElement=(Element)nextElementRefNode.item(0);
	    							
	    							if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(outputConditionID)==0){
	    								nextElementRefElement.setAttribute("id",endConditionID);
	    							}
	    						}
	    					}
	    				}
	    				
	    				
	    				
	    				processControlElementChilderen=processControlElements.getElementsByTagName("condition");
	    				for (int j=0; j<processControlElementChilderen.getLength();j++){
	    					Element  childElement=(Element)processControlElementChilderen.item(j);

	    					if (childElement.getAttribute("id").compareToIgnoreCase(nodeID)==0){
	    						
	    						NodeList  flowsIntoNodes=childElement.getElementsByTagName("flowsInto");
	    						for (int k=0; k<flowsIntoNodes.getLength();k++){
	    							Element flowsIntoElement=(Element)flowsIntoNodes.item(k);
	    							
	    							NodeList  nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
	    							Element   nextElementRefElement=(Element)nextElementRefNode.item(0);
	    							
	    							if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(outputConditionID)==0){
	    								nextElementRefElement.setAttribute("id",endConditionID);
	    							}
	    							
	    							
	    						}
	    					}
	    					
	    				}
	    				
	    				
	    			}
	    			
	    		}
	    		
	    		
	    		processControlElements.appendChild(endConditionElement);
	    		processControlElements.appendChild(exitTaskElement);
	    		processControlElements.appendChild(checkoutTaskElement);
	    		
	    		
	    		
	    		
	    	
	    		
	    		
	    		
	    		
	    		//  layout
	    		
	    		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
	    		Element   layoutElement=(Element)layoutNodes.item(0);

	    		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
	    		
	    		if (layoutSpecificationNodes.getLength()==1){
	    			
	    			Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
	    			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");

	    			if (layoutNetNodes.getLength()==1){
	    				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
	    				
	    				
	    				
	    				
	    				// end condition
	    				
	    				String endConditionX="600.0";
	    				String endConditionY="400.0";
	    				Element newEndContainerElement=createConditionContainer(transformedDoc, endConditionID, endConditionX,endConditionY);
	    				layoutNetElement.appendChild(newEndContainerElement);
	    			
	    				// check_out task
	    				String   checkOutTaskX=incrementContainerBounds(endConditionX, "50.0");
	    				String   checkOutTaskY=endConditionY;
	    				

	    				Element newcheckOutContainerElement=createTaskContainer(transformedDoc, checkOutTaskID, checkOutTaskX, checkOutTaskX, "", "AND_split");
	    				layoutNetElement.appendChild(newcheckOutContainerElement);
	    				
	    				
	    				//exit task
	    				
	    				

	    				String outputConditionXY=getWorkflowElementContainerAttributeXY(transformedFilePath, outputConditionID);
	    				String outputConditionX=outputConditionXY.split("\\,")[0];
	    				String outputConditionY=outputConditionXY.split("\\,")[1];
	    				Element newExitTaskContainerElement=createTaskContainer(transformedDoc, exitTaskID, incrementContainerBounds(outputConditionX, "-50"), outputConditionY, "AND_join", "");
	    				layoutNetElement.appendChild(newExitTaskContainerElement);
	    				
	    				
	    				
	    				//flows
	    				// remove flows between lasts and output condition and make new link with end condition
	    				if ((lasts!="") && (lasts!=null)){
	    	    			String[]  lastNodes=lasts.split("\\,");
	    	    			for (int i=0; i< lastNodes.length;i++){
	    	    				String nodeID=lastNodes[i];
	    	    				
	    	    				NodeList layoutNetFlowNodes=layoutNetElement.getElementsByTagName("flow");
	    	    				for (int j=0; j<layoutNetFlowNodes.getLength();j++){
	    	    					Element layoutNetFlowElement=(Element)layoutNetFlowNodes.item(j);
	    	    					
	    	    					if (layoutNetFlowElement.getAttribute("target").compareToIgnoreCase(outputConditionID)==0){
	    	    						layoutNetFlowElement.setAttribute("target", endConditionID);
	    	    					}
	    	    					
	    	    				}
	    	    				
	    	    				
	    	    			}
	    				}

	    				
	    				
	    				
	    				
	    				//(end,check_out)
	    				
	    				
//	    		*		Element end_CheckOutLayoutNetFlowElement=createFlowElement(transformedDoc, endConditionID, checkOutTaskID, "12", "13");
//	    			*	layoutNetElement.appendChild(end_CheckOutLayoutNetFlowElement);
	    				
	    				
//	    				// (check_out,exit)
//	    				String[] CheckOut_exitPointsValues={"560.0","165.0","570.0","165.0"};
//	    				Element CheckOut_exitLayoutNetFlowElement=createFlowElement(transformedDoc, checkOutTaskID, exitTaskID, "2", "2", CheckOut_exitPointsValues);
//	    				layoutNetElement.appendChild(CheckOut_exitLayoutNetFlowElement);
	    				
	    				// (exit,output)
//	    			*	Element  exit_OutputLayoutNetFlowElement=createFlowElement(transformedDoc, exitTaskID, outputConditionID, "12", "13" );
//	    			*	layoutNetElement.appendChild(exit_OutputLayoutNetFlowElement);
	    				
	    			}else{ //There are more than one net
	    			}


	    			
	    		}else{ //there are more than one specification node in layout 
	    		}
	    			
	    		
	    	}else{// if there are more decomposition nodes
	    		
	    	}
			
			
	    	
	    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		    Transformer transformer=transformerFactory.newTransformer();
		    DOMSource source=new DOMSource(transformedDoc);
		    StreamResult result=new StreamResult(transformedFile);
		    transformer.transform(source, result);
		    retVal="true";
			
		} catch (Exception e) {
			retVal="false";
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	
	public static String getInElementsBoundsMaxX(String workflowFilePath , String elementID){
		String retVal="";
		
		try {
			
			File workflowFile=new File(workflowFilePath);
			DocumentBuilder workflowBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document Doc = workflowBuilder.parse(workflowFile);

		
	    	NodeList specificationSetNodes=Doc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
    		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
    		Element   layoutElement=(Element)layoutNodes.item(0);

    		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
    		
    		if (layoutSpecificationNodes.getLength()==1){
    			
    			Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
    			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");

    			if (layoutNetNodes.getLength()==1){
    				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
    				
    				NodeList flowNodes=layoutNetElement.getElementsByTagName("flow");
    				for(int i=0; i<flowNodes.getLength();i++){
    					Element flowElement=(Element)flowNodes.item(i);
    				}
    				
    			}else{
    				
    			}//there are more layout net nodes
    		}else{// there are more layout specification nodes
    			
    		}
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
		
	}
	
	public static String updateFinalCondition(String viewFilePath,String workflowName, String outputCondition, String endCondition){
		String retVal="false";
		try {
			
			File viewFile=new File(viewFilePath);
			DocumentBuilder viewBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document viewDoc = viewBuilder.parse(viewFile);

			NodeList workflowNodes=viewDoc.getElementsByTagName("workflow");
			for(int i=0; i<workflowNodes.getLength();i++){
				Element workflowElement=(Element)workflowNodes.item(i);
				
				if (workflowElement.getAttribute("name").compareToIgnoreCase(workflowName)==0){
					NodeList taskNode=workflowElement.getElementsByTagName("task_name");
					Element  taskElement=(Element)taskNode.item(0);
					
					String taskName=getCharacterDataFromElement(taskElement).split("\\?")[0];
					String stopName=getCharacterDataFromElement(taskElement).split("\\?")[1];
					if (stopName.compareToIgnoreCase(outputCondition)==0){
						taskElement.removeChild(taskElement.getFirstChild());
						taskElement.appendChild(viewDoc.createTextNode(taskName+"?"+endCondition));
					}
				}
			}
			
	    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		    Transformer transformer=transformerFactory.newTransformer();
		    DOMSource source=new DOMSource(viewDoc);
		    StreamResult result=new StreamResult(viewFile);
		    transformer.transform(source, result);
		    retVal="true";

			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static Boolean conditionIsStop(String viewFilePath, String conditionName, String workflowName ){
		Boolean retVal=false;
		
		try {
			
			File viewFile=new File(viewFilePath);
			DocumentBuilder viewBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document viewDoc = viewBuilder.parse(viewFile);

			NodeList workflowNodes=viewDoc.getElementsByTagName("workflow");
			for(int i=0; i<workflowNodes.getLength();i++){
				Element workflowElement=(Element)workflowNodes.item(i);
				
				if (workflowElement.getAttribute("name").compareToIgnoreCase(workflowName)==0){
					NodeList taskNode=workflowElement.getElementsByTagName("task_name");
					Element  taskElement=(Element)taskNode.item(0);
					
					String stopName=getCharacterDataFromElement(taskElement).split("\\?")[1];
					if (stopName.compareToIgnoreCase(conditionName)==0){
						return true;
					}
				}
			}
			
			
		} catch (Exception e) {
			retVal=false;
		}
		
		return retVal;
	}
	
	
	
	public static String getStopOfStart(String viewFilePath, String taskName, String workflowName){
		String retVal="";

		try {
			
			File viewFile=new File(viewFilePath);
			DocumentBuilder viewBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document viewDoc = viewBuilder.parse(viewFile);

			NodeList workflowNodes=viewDoc.getElementsByTagName("workflow");
			for(int i=0; i<workflowNodes.getLength();i++){
				Element workflowElement=(Element)workflowNodes.item(i);
				
				if (workflowElement.getAttribute("name").compareToIgnoreCase(workflowName)==0){
					NodeList taskNode=workflowElement.getElementsByTagName("task_name");
					Element  taskElement=(Element)taskNode.item(0);
					
					String startName=getCharacterDataFromElement(taskElement).split("\\?")[0];
					String stopName=getCharacterDataFromElement(taskElement).split("\\?")[1];
					if (startName.compareToIgnoreCase(taskName)==0){
						return stopName;
					}
				}
			}
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	
	
	public static Boolean taskIsStart(String viewFilePath, String taskName, String workflowName){
		Boolean retVal=false;
		
		try {
			
			File viewFile=new File(viewFilePath);
			DocumentBuilder viewBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document viewDoc = viewBuilder.parse(viewFile);

			NodeList workflowNodes=viewDoc.getElementsByTagName("workflow");
			for(int i=0; i<workflowNodes.getLength();i++){
				Element workflowElement=(Element)workflowNodes.item(i);
				
				if (workflowElement.getAttribute("name").compareToIgnoreCase(workflowName)==0){
					NodeList taskNode=workflowElement.getElementsByTagName("task_name");
					Element  taskElement=(Element)taskNode.item(0);
					
					String startName=getCharacterDataFromElement(taskElement).split("\\?")[0];
					if (startName.compareToIgnoreCase(taskName)==0){
						return true;
					}
				}
			}
			
			
		} catch (Exception e) {
			retVal=false;
		}
		
		return retVal;	}
	
	public static String getStops(String   transformedFilePath ,String viewFilePath ){
		String retVal="";
		
		try {
			File  transformedFile=new File(transformedFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);
			
			
			
			
    		String workflowName =getWorkflowName(transformedFilePath);
			
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElements=(Element)processControlNodes.item(0);
	    		
	    		
	    		
	    		NodeList  conditionNode=processControlElements.getElementsByTagName("condition");
	    		for (int i=0;i<conditionNode.getLength();i++){
	    			Element conditionElement=(Element) conditionNode.item(i);
	    			
	    			NodeList conditionNameNode=conditionElement.getElementsByTagName("name");
	    			Element  conditionNameElement=(Element) conditionNameNode.item(0);
	    			
	    			String conditionName=getCharacterDataFromElement(conditionNameElement);
	    			if (conditionIsStop(viewFilePath, conditionName, workflowName)){

		    			if (retVal==""){
		    				retVal=conditionName;
		    			}else{
		    				retVal=retVal+","+conditionName;
		    			}
		    			
		    		}
	    		}
	    		
	    		
	    		
	    		
	    		
	    		
	  
	    	}else{ //there are no or more decomposition elements
	    		
	    	}
			

			
			
		} catch (Exception e) {
			retVal="";
		}
		
		
		return retVal;
	}

	

	
	public static String getStarts(String   transformedFilePath ,String viewFilePath ){
		String retVal="";
		
		try {
			File  transformedFile=new File(transformedFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);
			
			
			
			
    		String workflowName =getWorkflowName(transformedFilePath);
			
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElements=(Element)processControlNodes.item(0);
	    		
	    		
	    		
	    		NodeList  taskNodes=processControlElements.getElementsByTagName("task");
	    		for (int i=0;i<taskNodes.getLength();i++){
	    			Element taskElement=(Element) taskNodes.item(i);
	    			
	    			NodeList taskNameNode=taskElement.getElementsByTagName("name");
	    			Element  taskNameElement=(Element) taskNameNode.item(0);
	    			
	    			String taskName=getCharacterDataFromElement(taskNameElement);
	    			if (taskIsStart(viewFilePath, taskName, workflowName)){

		    			if (retVal==""){
		    				retVal=taskName;
		    			}else{
		    				retVal=retVal+","+taskName;
		    			}
		    			
		    		}
	    		}
	    		
	    		
	    		
	    		
	    		
	    		
	  
	    	}else{ //there are no or more decomposition elements
	    		
	    	}
			

			
			
		} catch (Exception e) {
			retVal="";
		}
		
		
		return retVal;
	}

	public static String getWorkflowElementIDFromName(String workflowFilePath, String elementName){
		String retVal="";
		try {
			
			File workflowFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document workflowDoc = transformedBuilder.parse(workflowFile);
			
	    	NodeList specificationSetNodes=workflowDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElement=(Element)processControlNodes.item(0);
	    		
	    		if(elementName.compareToIgnoreCase("inputCondition")==0){
		    		NodeList  inputConditionNode=processControlElement.getElementsByTagName("inputCondition");
		    		Element   inputConditionElement=(Element)inputConditionNode.item(0);
		    		
		    		retVal=inputConditionElement.getAttribute("id");
		    		return retVal;
	
	    		}
	    		
	    		if(elementName.compareToIgnoreCase("outputCondition")==0){
		    		NodeList outputConditionNode=processControlElement.getElementsByTagName("outputCondition");
		    		Element  outputElement=(Element)outputConditionNode.item(0);
		    		retVal=outputElement.getAttribute("id");
		    		return retVal;
	    		}
	    		
	    		
	    		NodeList  conditionNodes=processControlElement.getElementsByTagName("condition");
	    		for(int i=0; i<conditionNodes.getLength();i++){
	    			Element conditionElement=(Element)conditionNodes.item(i);
	    			NodeList conditionNameNode=conditionElement.getElementsByTagName("name");
    				Element  conditionNameElement=(Element)conditionNameNode.item(0);
    				if(getCharacterDataFromElement(conditionNameElement).compareToIgnoreCase(elementName)==0){
    					retVal=conditionElement.getAttribute("id");
    					return retVal;
    				}
  	    		}
	    		
	    		
	    		NodeList taskNodes=processControlElement.getElementsByTagName("task");
	    		for(int i=0; i<taskNodes.getLength();i++){
	    			Element taskElement=(Element)taskNodes.item(i);
    				NodeList taskNameNode=taskElement.getElementsByTagName("name");
    				Element  taskNameElement=(Element)taskNameNode.item(0);
    				if(getCharacterDataFromElement(taskNameElement).compareToIgnoreCase(elementName)==0){
    					retVal=taskElement.getAttribute("id");
    					return retVal;

    				}
	  
	    		}
	  
	    	}else{ //there are no or more decomposition elements
	    		
	    	}

			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal; 
	}
	
    public static String getWorkflowElementNameFromID(String workflowFilePath, String elementID){
    	String retVal="";
    	
    	try {
    		
			File workflowFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document workflowDoc = transformedBuilder.parse(workflowFile);
			
	    	NodeList specificationSetNodes=workflowDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElement=(Element)processControlNodes.item(0);
	    		
	    		NodeList  inputConditionNode=processControlElement.getElementsByTagName("inputCondition");
	    		Element   inputConditionElement=(Element)inputConditionNode.item(0);
	    		if (inputConditionElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    			retVal="inputCondition";
	    			return retVal;
	    		}
	    		
	    		NodeList outputConditionNode=processControlElement.getElementsByTagName("outputCondition");
	    		Element  outputElement=(Element)outputConditionNode.item(0);
	    			
	    		if(outputElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    			retVal="outputCondition";
	    			return retVal;
	    		}
	    		
	    		NodeList  conditionNodes=processControlElement.getElementsByTagName("condition");
	    		for(int i=0; i<conditionNodes.getLength();i++){
	    			Element conditionElement=(Element)conditionNodes.item(i);
	    			if(conditionElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    				NodeList conditionNameNode=conditionElement.getElementsByTagName("name");
	    				Element  conditionNameElement=(Element)conditionNameNode.item(0);
	    				retVal=getCharacterDataFromElement(conditionNameElement);
	    				return retVal;
	    			}
	    		}
	    		
	    		NodeList taskNodes=processControlElement.getElementsByTagName("task");
	    		for(int i=0; i<taskNodes.getLength();i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			if (taskElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    				NodeList taskNameNode=taskElement.getElementsByTagName("name");
	    				Element  taskNameElement=(Element)taskNameNode.item(0);
	    				retVal=getCharacterDataFromElement(taskNameElement);
	    				
	    				return retVal;
	    			}
	    		}
	    		
	    		
	    		
	    		
	    		
	  
	    	}else{ //there are no or more decomposition elements
	    		
	    	}

			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
    }
	 
    public static String getSuccessors(String workflowFilePath , String elementID){
    
	    		
	  
		String retVal="";
		
		try {
			File workflowFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document workflowDoc = transformedBuilder.parse(workflowFile);
			
	    	NodeList specificationSetNodes=workflowDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElement=(Element)processControlNodes.item(0);
	    		
	    	
	    	
	    		NodeList inputConditionNode=processControlElement.getElementsByTagName("inputCondition");
	    		Element  inputConditionElement=(Element)inputConditionNode.item(0);
	    		if(inputConditionElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    			NodeList flowsIntoNodes=inputConditionElement.getElementsByTagName("flowsInto");
	    			for(int i=0;i<flowsIntoNodes.getLength();i++){
	    				Element flowsIntoElement=(Element)flowsIntoNodes.item(i);
	    				
		    			NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
		    			Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
		    			
		    			if (retVal==""){
    						retVal=nextElementRefElement.getAttribute("id");
    					}else{
    						retVal=retVal+","+nextElementRefElement.getAttribute("id");

    					}

	    			}
	    		}
	    		
	    		
	    		
	    		NodeList outputConditionNode=processControlElement.getElementsByTagName("outputCondition");
	    		Element  outputConditionElement=(Element)outputConditionNode.item(0);
		    	if (outputConditionElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
		    		retVal="";
	    		}
	    		
	    		
	    		NodeList  taskNodes=processControlElement.getElementsByTagName("task");
	    		for(int i=0; i<taskNodes.getLength();i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			if(taskElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    				NodeList flowsIntoNodes=taskElement.getElementsByTagName("flowsInto");
		    			for(int j=0; j<flowsIntoNodes.getLength();j++){
		    				Element flowsIntoElement=(Element)flowsIntoNodes.item(j);
		    				
		    				NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
		    				Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
		    					if (retVal==""){
		    						retVal=nextElementRefElement.getAttribute("id");
		    					}else{
		    						retVal=retVal+","+nextElementRefElement.getAttribute("id");
		    					}
		    			}
		    			
	    			}
	    		}
	    		
	      		
	    		
	    		NodeList  conditionNodes=processControlElement.getElementsByTagName("condition");
	    		for(int i=0; i<conditionNodes.getLength();i++){
	    			Element conditionElement=(Element)conditionNodes.item(i);
	    			if(conditionElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    				NodeList flowsIntoNodes=conditionElement.getElementsByTagName("flowsInto");
		    			for(int j=0; j<flowsIntoNodes.getLength();j++){
		    				Element flowsIntoElement=(Element)flowsIntoNodes.item(j);
		    				
		    				NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
		    				Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
		    					if (retVal==""){
		    						retVal=nextElementRefElement.getAttribute("id");
		    					}else{
		    						retVal=retVal+","+nextElementRefElement.getAttribute("id");
		    					}
		    			}
		    			
	    			}
	    		}
	    		
	      		
	    		
	    	
	  
	    	}else{ //there are no or more decomposition elements
	    		
	    	}

			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="";
		}
		
		return retVal;
    	
    	
    }
    
	public static String getPredecessors(String workflowFilePath , String elementID){
		String retVal="";
		
		try {
			File workflowFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document workflowDoc = transformedBuilder.parse(workflowFile);
			
	    	NodeList specificationSetNodes=workflowDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElement=(Element)processControlNodes.item(0);
	    		
	    	
	    		NodeList inputConditionNode=processControlElement.getElementsByTagName("inputCondition");
	    		Element  inputConditionElement=(Element)inputConditionNode.item(0);
	    		
	    		NodeList inputConditionFlowsIntoNodes=inputConditionElement.getElementsByTagName("flowsInto");
	    		for(int i=0;i<inputConditionFlowsIntoNodes.getLength();i++){
	    			Element FlowsIntoElement=(Element)inputConditionFlowsIntoNodes.item(i);
	    			
	    			NodeList nextElementRefNode=FlowsIntoElement.getElementsByTagName("nextElementRef");
	    			Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
	    			
	    			if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
    					if (retVal==""){
    						retVal=inputConditionElement.getAttribute("id");
    					}else{
    						retVal=retVal+","+inputConditionElement.getAttribute("id");

    					}
    				}
	    			
	    		}
	    		
	    		
	    		
	    		
	    		NodeList  taskNodes=processControlElement.getElementsByTagName("task");
	    		for(int i=0; i<taskNodes.getLength();i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			
	    			NodeList flowsIntoNodes=taskElement.getElementsByTagName("flowsInto");
	    			for(int j=0; j<flowsIntoNodes.getLength();j++){
	    				Element flowsIntoElement=(Element)flowsIntoNodes.item(j);
	    				
	    				NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
	    				Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
	    				
	    				if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    					if (retVal==""){
	    						retVal=taskElement.getAttribute("id");
	    					}else{
	    						retVal=retVal+","+taskElement.getAttribute("id");

	    					}
	    				}
	    				
	    			}
	    			
	    		}
	    		
	      		
	    		NodeList  conditionNodes=processControlElement.getElementsByTagName("condition");
	    		for(int i=0; i<conditionNodes.getLength();i++){
	    			Element conditionElement=(Element)conditionNodes.item(i);
	    			
	    			NodeList flowsIntoNodes=conditionElement.getElementsByTagName("flowsInto");
	    			for(int j=0; j<flowsIntoNodes.getLength();j++){
	    				Element flowsIntoElement=(Element)flowsIntoNodes.item(j);
	    				
	    				NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
	    				Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
	    				
	    				if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
	    					if (retVal==""){
	    						retVal=conditionElement.getAttribute("id");
	    					}else{
	    						retVal=retVal+","+conditionElement.getAttribute("id");

	    					}
	    				}
	    				
	    			}
	    			
	    		}
	  
	    	}else{ //there are no or more decomposition elements
	    		
	    	}

			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="";
		}
		
		return retVal;
	}
	
	public static String buildPresenceCheck(String baseFilePath, String transformedFilePath, String conditionName){
		String retVal="false";
		
		try {
			String conditionID=getWorkflowElementIDFromName(transformedFilePath, conditionName);
			String lastElementID=findElementsLastID(transformedFilePath);

			File transformedFile=new File(transformedFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);

		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");

	    	if (decompositionNodes.getLength()==1){
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElements=(Element)processControlNodes.item(0);

	    		
	    		
	    		// calculate elements' ID
	    		String deadConditionID="dead_"+conditionName+"_"+lastElementID;
	    		String activeTaskConditionID="active_task_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+1);
	    		String activeConditionID="active_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+2);
	    		String checkoutTaskID="check_out_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+3);
	    		String checkActiveTaskID="check_active_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+4);
	    		String proceedTaskID="proceed_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+5);
	    		String endTaskID="end_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+6);
	    		String exitTaskID="exit_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+7);

	    		
	    		// add dead  condition
	    		String nextElementRefsID=proceedTaskID;
	    		Element deadConditionElement=createWorkflowConditionElement(transformedDoc, deadConditionID, "dead_"+conditionName, nextElementRefsID);
	    		processControlElements.appendChild(deadConditionElement);	
	    		
	    		// add active_task condition 
	    		nextElementRefsID=checkActiveTaskID;
	    		Element activeTaskConditionElement=createWorkflowConditionElement(transformedDoc, activeTaskConditionID, "active_task_"+conditionName, nextElementRefsID);
	    		processControlElements.appendChild(activeTaskConditionElement);
	    		
	    		// add active condition
	    		nextElementRefsID=endTaskID;
	    		Element activeConditionElement=createWorkflowConditionElement(transformedDoc, activeConditionID, "active_"+conditionName, nextElementRefsID);
	    		processControlElements.appendChild(activeConditionElement);
	    		
	    		// add check_out task
	    		nextElementRefsID=proceedTaskID+","+checkActiveTaskID;
	    		Element checkoutTaskElement=createWorkflowTask(transformedDoc, checkoutTaskID, "check_out_"+conditionName, "and", "xor", nextElementRefsID);
	    		processControlElements.appendChild(checkoutTaskElement);
	    		
	    		// add check_active task
	    		nextElementRefsID=endTaskID;
	    		Element checkActiveTaskElement=createWorkflowTask(transformedDoc, checkActiveTaskID, "check_active_"+conditionName, "and", "and", nextElementRefsID);
	    		processControlElements.appendChild(checkActiveTaskElement);
	    		
	    		
	    		
	    		// add proceed task
	    		nextElementRefsID=exitTaskID;
	    		Element proceedTaskElement=createWorkflowTask(transformedDoc, proceedTaskID, "proceed_"+conditionName, "and", "and", nextElementRefsID);
	    		processControlElements.appendChild(proceedTaskElement);
	    		
	    		// add end task
	    		nextElementRefsID=exitTaskID;
	    		Element endTaskElement=createWorkflowTask(transformedDoc, endTaskID, "end_"+conditionName, "and", "and", nextElementRefsID);
	    		processControlElements.appendChild(endTaskElement);
	    		
	    		
	    		// add exit task
	    		nextElementRefsID=getWorkflowElementIDFromName(transformedFilePath, "exit");;
	    		Element exitTaskElement=createWorkflowTask(transformedDoc, exitTaskID, "exit_"+conditionName, "xor", "and", nextElementRefsID);
	    		processControlElements.appendChild(exitTaskElement);
	    		

	    		// add additional flows
	    		
	    		// (check_out, check_out_c)
	    		NodeList taskNodes=processControlElements.getElementsByTagName("task");
	    		for (int i=0;i<taskNodes.getLength();i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			if (taskElement.getAttribute("id").compareToIgnoreCase(getWorkflowElementIDFromName(transformedFilePath, "check_out"))==0){
	    				Element checkOutFlowsIntoElement=transformedDoc.createElement("flowsInto");
	    	    		
	    	    		Element checkOutFlowsIntoNextElement=transformedDoc.createElement("nextElementRef");
	    	    		checkOutFlowsIntoNextElement.setAttribute("id", checkoutTaskID);
	    	    		
	    	    		checkOutFlowsIntoElement.appendChild(checkOutFlowsIntoNextElement);
	    	    		taskElement.appendChild(checkOutFlowsIntoElement);
	    			}
	    			
	    		}
	    		
	    		
	    		
	    		
	    	

	    		
	    		//(init, dead_condition)
	    		
	    		for (int i=0;i<taskNodes.getLength();i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			if (taskElement.getAttribute("id").compareToIgnoreCase(getWorkflowElementIDFromName(transformedFilePath, "init"))==0){
	    	    		Element  initFlowsIntoElement=transformedDoc.createElement("flowsInto");
	    	    		
	    	    		Element  initFlowsIntoNextElement=transformedDoc.createElement("nextElementRef");
	    	    		initFlowsIntoNextElement.setAttribute("id", deadConditionID);
	    	    		
	    	    		initFlowsIntoElement.appendChild(initFlowsIntoNextElement);
	    	    		taskElement.appendChild(initFlowsIntoElement);
	    			}
	    			
	    		}
	    		

	    		NodeList initNode=processControlElements.getElementsByTagName("init");
	    		Element  initElement=(Element)initNode.item(0);
	    		
	    		

	    		
	    		//(run_c, active_c)
	    		for (int i=0;i<taskNodes.getLength();i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			if (taskElement.getAttribute("id").compareToIgnoreCase(getWorkflowElementIDFromName(transformedFilePath, "run_"+conditionName))==0){
	    				Element  runFlowsIntoElement=transformedDoc.createElement("flowsInto");
	    	    		
	    	    		Element  runFlowsIntoNextElement=transformedDoc.createElement("nextElementRef");
	    	    		runFlowsIntoNextElement.setAttribute("id", activeConditionID);
	    	    		
	    	    		runFlowsIntoElement.appendChild(runFlowsIntoNextElement);
	    	    		taskElement.appendChild(runFlowsIntoElement);
	    			}
	    			
	    		}
	    		
	    		
	    		
	    		// layout
	    		
	    		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
	    		Element   layoutElement=(Element)layoutNodes.item(0);

	    		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
	    		
	    		if (layoutSpecificationNodes.getLength()==1){
	    			
	    			Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
	    			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");

	    			if (layoutNetNodes.getLength()==1){
	    				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
	    				
	    				// dead condition
	    				String[] deadConditionBounds={"800.0","356.0","32.0","32.0","790.0","388.0","97.0","21.0"};
//	    		*		Element  deadContainerElement=createConditionContainer(transformedDoc, deadConditionID, deadConditionBounds);
//	    			*	layoutNetElement.appendChild(deadContainerElement);
	    				
	    				
	    				// active_task condition
//	    			*	String[] activeTaskConditionBounds={"850.0","356.0","32.0","32.0","840.0","388.0","97.0","21.0"};
//	    			*	Element  activeTaskContainerElement=createConditionContainer(transformedDoc, activeTaskConditionID, activeTaskConditionBounds);
//	    			*	layoutNetElement.appendChild(activeTaskContainerElement);
	    				
	    				// active condition
//	    			*	String[] activeConditionBounds={"890.0","356.0","32.0","32.0","880.0","388.0","97.0","21.0"};
//	    			*	Element  activeContainerElement=createConditionContainer(transformedDoc, activeConditionID, activeConditionBounds);
//	    			*	layoutNetElement.appendChild(activeContainerElement);
	    				
	    				
	    				// check_out task
	    				
	    				String[] checkoutTaskBounds={"900.0","356.0","32.0","32.0","900.0","388.0","97.0","21.0"};
	    				String[] checkoutJoinBounds={"890.0","356.0","11.0","32.0","11","32"}; 
	    				String[] checkoutSplitBounds={"931.0","356.0","11.0","32.0","11","32"}; 
	    				
//*	    				Element checkOutContainerElement=createTaskContainer(transformedDoc, checkoutTaskID, checkoutTaskBounds, "", checkoutJoinBounds, "XOR_split", checkoutSplitBounds);
//	*    				layoutNetElement.appendChild(checkOutContainerElement);
	    				
	    				// check_active task

	    				String[] checkActiveTaskBounds={"960.0","356.0","32.0","32.0","950.0","388.0","97.0","21.0"};
	    				String[] checkActiveJoinBounds={"950.0","356.0","11.0","32.0","11","32"}; 
	    				String[] checkActiveSplitBounds={"991.0","356.0","11.0","32.0","11","32"}; 
	    				
//	 *   				Element checkActiveContainerElement=createTaskContainer(transformedDoc, checkActiveTaskID, checkActiveTaskBounds, "AND_join", checkActiveJoinBounds, "", checkActiveSplitBounds);
//	 *   				layoutNetElement.appendChild(checkActiveContainerElement);
	    				
	    				
	    				// proceed task
	    				
	    				String[] proceedTaskBounds={"1020.0","356.0","32.0","32.0","1010.0","388.0","97.0","21.0"};
	    				String[] proceedJoinBounds={"1010.0","356.0","11.0","32.0","11","32"}; 
	    				String[] proceedSplitBounds={"1051.0","356.0","11.0","32.0","11","32"}; 
	    				
//	  *  				Element proceedContainerElement=createTaskContainer(transformedDoc, proceedTaskID, proceedTaskBounds, "AND_join", proceedJoinBounds, "", proceedSplitBounds);
//	 *   				layoutNetElement.appendChild(proceedContainerElement);
	    				
	    				//end task
	    				
	      				String[] endTaskBounds={"1070.0","356.0","32.0","32.0","1060.0","388.0","97.0","21.0"};
	    				String[] endJoinBounds={"1060.0","356.0","11.0","32.0","11","32"}; 
	    				String[] endSplitBounds={"1101.0","356.0","11.0","32.0","11","32"}; 
	    				
//	   * 				Element endContainerElement=createTaskContainer(transformedDoc, endTaskID, endTaskBounds, "AND_join", endJoinBounds, "", endSplitBounds);
//	  *  				layoutNetElement.appendChild(endContainerElement);
//    				
	    				// exit task
	      				String[] exitTaskBounds={"1150.0","356.0","32.0","32.0","1140.0","388.0","97.0","21.0"};
	    				String[] exitJoinBounds={"1140.0","356.0","11.0","32.0","11","32"}; 
	    				String[] exitSplitBounds={"1181.0","356.0","11.0","32.0","11","32"}; 
	    				
//	 *   				Element exitContainerElement=createTaskContainer(transformedDoc, exitTaskID, exitTaskBounds, "XOR_join", exitJoinBounds, "", exitSplitBounds);
//	 *   				layoutNetElement.appendChild(exitContainerElement);

	    				//flows
	    				//(check_out , check_out_c)
	    				String[]  pointValues1={"600.0","165.0","610.0","165.0"};
	    				Element   checkout_Checkout_C_LayoutNetFlowElement=createFlowElement(transformedDoc, getWorkflowElementIDFromName(transformedFilePath, "check_out"), checkoutTaskID , "3", "3", pointValues1);
	    				layoutNetElement.appendChild(checkout_Checkout_C_LayoutNetFlowElement);
	    				
	    				
	    				//(check_out_c , check_active)
	    				String[] pointValues2={"600.0","165.0","610.0","165.0"};
	    				Element   checkout_CheckActiveLayoutNetFlowElement=createFlowElement(transformedDoc, checkoutTaskID, checkActiveTaskID , "3", "3", pointValues2);
	    				layoutNetElement.appendChild(checkout_CheckActiveLayoutNetFlowElement);
    				
	    				
	    				
	    				//(check_out_c , proceed)
	    				String[] pointValues3={"600.0","165.0","610.0","165.0"};
	    				Element   checkout_ProceedLayoutNetFlowElement=createFlowElement(transformedDoc, checkoutTaskID, proceedTaskID , "3", "3", pointValues3);
	    				layoutNetElement.appendChild(checkout_ProceedLayoutNetFlowElement);
    				
	    				
	    				//(proceed , exit)
	    				String[] pointValues4={"600.0","165.0","610.0","165.0"};
	    				Element   proceed_ExitLayoutNetFlowElement=createFlowElement(transformedDoc, proceedTaskID, exitTaskID , "3", "3", pointValues4);
	    				layoutNetElement.appendChild(proceed_ExitLayoutNetFlowElement);
    				
	    				//(check_active , end)
	    				String[] pointValues5={"600.0","165.0","610.0","165.0"};
	    				Element   checkActive_endLayoutNetFlowElement=createFlowElement(transformedDoc, checkActiveTaskID, endTaskID , "3", "3", pointValues5);
	    				layoutNetElement.appendChild(checkActive_endLayoutNetFlowElement);
	    				
	    				//(end , exit)
	    				String[] pointValues6={"600.0","165.0","610.0","165.0"};
	    				Element   end_ExitLayoutNetFlowElement=createFlowElement(transformedDoc, endTaskID, exitTaskID , "3", "3", pointValues6);
	    				layoutNetElement.appendChild(end_ExitLayoutNetFlowElement);
    
	    				//(active_task , check_active)
	    				String[] pointValues7={"600.0","165.0","610.0","165.0"};
	    				Element   activeTask_CheckActiveLayoutNetFlowElement=createFlowElement(transformedDoc, activeTaskConditionID, checkActiveTaskID , "13", "2", pointValues7);
	    				layoutNetElement.appendChild(activeTask_CheckActiveLayoutNetFlowElement);

	    				//(active , end)
	    				String[] pointValues8={"600.0","165.0","610.0","165.0"};
	    				Element   active_EndActiveLayoutNetFlowElement=createFlowElement(transformedDoc, activeConditionID, endTaskID , "13", "2", pointValues8);
	    				layoutNetElement.appendChild(active_EndActiveLayoutNetFlowElement);
	    				
	    				
	    				//(dead , proceed)
	    				String[] pointValues9={"600.0","165.0","610.0","165.0"};
	    				Element   dead_ProceedLayoutNetFlowElement=createFlowElement(transformedDoc, deadConditionID , proceedTaskID , "13", "2", pointValues9);
	    				layoutNetElement.appendChild(dead_ProceedLayoutNetFlowElement);
				
	    				//(init , dead)
	    				String[] pointValues10={"600.0","165.0","610.0","165.0"};
	    				Element   init_DeadLayoutNetFlowElement=createFlowElement(transformedDoc, getWorkflowElementIDFromName(transformedFilePath, "init") , deadConditionID , "2", "12", pointValues10);
	    				layoutNetElement.appendChild(init_DeadLayoutNetFlowElement);
	    				
	    				//(run , active)
	    				String[] pointValues11={"600.0","165.0","610.0","165.0"};
	    				Element   run_ActiveLayoutNetFlowElement=createFlowElement(transformedDoc, getWorkflowElementIDFromName(transformedFilePath, "run_"+conditionName) , activeConditionID , "2", "12", pointValues11);
	    				layoutNetElement.appendChild(run_ActiveLayoutNetFlowElement);
	    				
	    				
	    				//(exit_c , exit)
	    				String[] pointValues12={"600.0","165.0","610.0","165.0"};
	    				Element   exit_c_exitLayoutNetFlowElement=createFlowElement(transformedDoc, exitTaskID , getWorkflowElementIDFromName(transformedFilePath, "exit") , "3", "3", pointValues12);
	    				layoutNetElement.appendChild(exit_c_exitLayoutNetFlowElement);
    				

	    				

	    			}
	    		
	    			
	    			
	    			
	    			
	    			
	    				
	    				
	    		}else{// there are more layoutSpecificationNodes
	    			
	    		}
	    		
	    		
 
	    		
	    	}else{// there are more decomposition nodes
	    		
	    	}

	    	
			
	    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		    Transformer transformer=transformerFactory.newTransformer();
		    DOMSource source=new DOMSource(transformedDoc);
		    StreamResult result=new StreamResult(transformedFile);
		    transformer.transform(source, result);
		    retVal="true";

			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String getTaskJoinType(String baseFilePath,String taskID){
		String retVal="";
		
		try {
			
			File baseFile=new File(baseFilePath);
			DocumentBuilder baseBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = baseBuilder.parse(baseFile);

		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
	    	
    		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
    		Element   layoutElement=(Element)layoutNodes.item(0);

    		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
    		
    		if (layoutSpecificationNodes.getLength()==1){
    			
    			Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
    			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");

    			if (layoutNetNodes.getLength()==1){
    				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
    				
    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
    				for(int i=0;i<containerNodes.getLength();i++){
    					Element containerElement=(Element)containerNodes.item(i);
    					if(containerElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
    						NodeList decoratorNodes=containerElement.getElementsByTagName("decorator");
    						for (int j=0;j<decoratorNodes.getLength();j++){
    							Element decoratorElement=(Element)decoratorNodes.item(j);
    							String decoratorType=decoratorElement.getAttribute("type");
    							String decompositionType=decoratorType.split("\\_")[0];
    							String connectionType=decoratorType.split("\\_")[1];
    							if(connectionType.compareToIgnoreCase("join")==0){
    								return (decompositionType);
    							}
    						}
    					}
    				}

    			}else{ // there are more layout nodes
    				
    			}
    		}else{//there are more layout specification nodes
    			
    		}
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	public static String getTaskSplitType(String baseFilePath,String taskID){
		String retVal="";
		
		try {
			
			File baseFile=new File(baseFilePath);
			DocumentBuilder baseBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = baseBuilder.parse(baseFile);

		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
	    	
    		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
    		Element   layoutElement=(Element)layoutNodes.item(0);

    		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
    		
    		if (layoutSpecificationNodes.getLength()==1){
    			
    			Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
    			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");

    			if (layoutNetNodes.getLength()==1){
    				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
    				
    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
    				for(int i=0;i<containerNodes.getLength();i++){
    					Element containerElement=(Element)containerNodes.item(i);
    					if(containerElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
    						NodeList decoratorNodes=containerElement.getElementsByTagName("decorator");
    						for (int j=0;j<decoratorNodes.getLength();j++){
    							Element decoratorElement=(Element)decoratorNodes.item(j);
    							String decoratorType=decoratorElement.getAttribute("type");
    							String decompositionType=decoratorType.split("\\_")[0];
    							String connectionType=decoratorType.split("\\_")[1];
    							if(connectionType.compareToIgnoreCase("split")==0){
    								return (decompositionType);
    							}
    						}
    					}
    				}

    			}else{ // there are more layout nodes
    				
    			}
    		}else{//there are more layout specification nodes
    			
    		}
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	public static String buildTaskCheck(String baseFilePath, String transformedFilePath,String taskName, String conditionName){
		String retVal="false";

		try {
			
			String lastElementID=findElementsLastID(transformedFilePath);
			String taskID=getWorkflowElementIDFromName(baseFilePath, taskName);
			String conditionID=getWorkflowElementIDFromName(baseFilePath, conditionName);
			
			String predecessors=getPredecessors(baseFilePath, getWorkflowElementIDFromName(baseFilePath, taskName));
			String successors=getSuccessors(baseFilePath, getWorkflowElementIDFromName(baseFilePath, taskName));
			
			String tSplit=getTaskSplitType(baseFilePath, getWorkflowElementIDFromName(baseFilePath, taskName));
			String tJoin=getTaskJoinType(baseFilePath, getWorkflowElementIDFromName(baseFilePath, taskName));
			
			File transformedFile=new File(transformedFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);

		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");

	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElements=(Element)processControlNodes.item(0);
	    		
	    		
	    		
	    		// calculate elements' ID
	    		String enabledConditionID="enabled_"+taskName+"_"+lastElementID;
	    		String activeConditionID="active_"+taskName+"_"+String.valueOf(Integer.parseInt(lastElementID)+1);
	    		String disabledConditionID="disabled_"+taskName+"_"+String.valueOf(Integer.parseInt(lastElementID)+2);
	    		String checkConditionID="check_"+conditionName+"_"+taskName+"_"+String.valueOf(Integer.parseInt(lastElementID)+3);
	    		String runTaskID="run_"+taskName+"_"+String.valueOf(Integer.parseInt(lastElementID)+4);
	    		String proceedTaskID="proceed_"+taskName+"_"+String.valueOf(Integer.parseInt(lastElementID)+5);
	    		String validateTaskID="validate_"+taskName+"_"+String.valueOf(Integer.parseInt(lastElementID)+6);
	    		String disableTaskID="disable_"+taskName+"_"+String.valueOf(Integer.parseInt(lastElementID)+7);
	    		String endTaskID="end_"+conditionName+"_"+taskName+String.valueOf(Integer.parseInt(lastElementID)+8);

	    		
	    		
	    		// add enabled  condition
	    		String nextElementRefsID=taskID;
	    		Element enabledConditionElement=createWorkflowConditionElement(transformedDoc, enabledConditionID, "enabled_"+taskName, nextElementRefsID);
	    		processControlElements.appendChild(enabledConditionElement);	

				
	    		// add active  condition
	    		nextElementRefsID=validateTaskID;
	    		Element activeConditionElement=createWorkflowConditionElement(transformedDoc, activeConditionID, "active_"+taskName, nextElementRefsID);
	    		processControlElements.appendChild(activeConditionElement);	

			
	    		// add disabled  condition
	    		nextElementRefsID=disableTaskID;
	    		Element disabledConditionElement=createWorkflowConditionElement(transformedDoc, disabledConditionID, "disabled_"+taskName, nextElementRefsID);
	    		processControlElements.appendChild(disabledConditionElement);	
  		
	    		// add check  condition
	    		nextElementRefsID=validateTaskID+","+disableTaskID;
	    		Element checkConditionElement=createWorkflowConditionElement(transformedDoc, checkConditionID, "check_"+conditionName+"_"+taskName, nextElementRefsID);
	    		processControlElements.appendChild(checkConditionElement);	
  		
	    		
	    		
	    		// add run task
	    		nextElementRefsID=taskID;
	    		Element runTaskElement=createWorkflowTask(transformedDoc, runTaskID, "run_"+taskName, tJoin.toLowerCase(), "and", nextElementRefsID);
	    		processControlElements.appendChild(runTaskElement);

	    		
	    		// add proceed task
	    		nextElementRefsID="";
	    		Element proceedTaskElement=createWorkflowTask(transformedDoc, proceedTaskID, "proceed_"+taskName, "and", tSplit.toLowerCase(), nextElementRefsID);
	    		processControlElements.appendChild(proceedTaskElement);
    	
	       		// add proceed task
	    		nextElementRefsID=endTaskID+","+activeConditionID;
	    		Element validateTaskElement=createWorkflowTask(transformedDoc, validateTaskID, "validate_"+taskName, "and", "and", nextElementRefsID);
	    		processControlElements.appendChild(validateTaskElement);
    
	    		
	       		// add disable task
	    		nextElementRefsID=disabledConditionID+","+endTaskID;
	    		Element disableTaskElement=createWorkflowTask(transformedDoc, disableTaskID, "disable_"+taskName, "and", "and", nextElementRefsID);
	    		processControlElements.appendChild(disableTaskElement);
   		
	    		
	    		// add end  task
	    		nextElementRefsID=runTaskID;
	    		Element endTaskElement=createWorkflowTask(transformedDoc, endTaskID, "end_"+conditionName+"_"+taskName, "xor", "and", nextElementRefsID);
	    		processControlElements.appendChild(endTaskElement);
	    		
	    		
	    		
	    		// change predecessors link from task to run_t
	    		String[] predecessorsList=predecessors.split("\\,");
	    		for(int i=0; i<predecessorsList.length;i++){
	    			
	    		
	    			NodeList inputConditionNode=processControlElements.getElementsByTagName("inputCondition");
	    			Element  inputConditionElement=(Element)inputConditionNode.item(0);
	    			if(inputConditionElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
    					NodeList flowsIntoNode=inputConditionElement.getElementsByTagName("flowsInto");
    					Element  flowsIntoElement=(Element)flowsIntoNode.item(0);
    			
    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
    					
    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
    						nextElementRefElement.setAttribute("id", runTaskID);
    					}

	    			}
	    			
	    			

	    			NodeList  processControlElementConditionNodes=processControlElements.getElementsByTagName("condition");
	    			for(int j=0;j<processControlElementConditionNodes.getLength();j++){
	    				Element childElement=(Element)processControlElementConditionNodes.item(j);
	    				if (childElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
	    					
	    					NodeList flowsIntoNode=childElement.getElementsByTagName("flowsInto");
	    					Element  flowsIntoElement=(Element)flowsIntoNode.item(0);
	    					
	    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
	    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
	    					
	    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
	    						nextElementRefElement.setAttribute("id", runTaskID);
	    					}
	    				}
	    			}
	    			
	    			
	    			NodeList  processControlElementTaskNodes=processControlElements.getElementsByTagName("task");
	    			for(int j=0;j<processControlElementTaskNodes.getLength();j++){
	    				Element childElement=(Element)processControlElementTaskNodes.item(j);
	    				if (childElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
	    					
	    					NodeList flowsIntoNode=childElement.getElementsByTagName("flowsInto");
	    					Element  flowsIntoElement=(Element)flowsIntoNode.item(0);
	    					
	    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
	    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
	    					
	    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
	    						nextElementRefElement.setAttribute("id", runTaskID);
	    					}
	    				}
	    			}
	    			
	    		}
 			
		    		// change successor link from task to proceed_t
	    		
	    		if(successors!=""){
    				String[] successorList=successors.split("\\,");

	    			NodeList  taskNodes=processControlElements.getElementsByTagName("task");
	    			for(int i=0;i<taskNodes.getLength();i++){
	    				Element taskElement=(Element)taskNodes.item(i);
	    				if (taskElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
		    				for(int j=0;j<successorList.length;j++){
		    					NodeList flowsIntoNode=taskElement.getElementsByTagName("flowsInto");
		    					Element  flowsIntoElement=(Element)flowsIntoNode.item(0);
		    					
		    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
		    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
		    					
		    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(successorList[j])==0){
		    						taskElement.removeChild(flowsIntoElement);
		    					}
		    				}

	    				}
	    			}
	    			
	    			
	    			for(int i=0;i<successorList.length;i++){
	    				Element flowsElement=transformedDoc.createElement("flowsInto");
	    				
	    				Element nextElementRefElement=transformedDoc.createElement("nextElementRef");
	    				nextElementRefElement.setAttribute("id", successorList[i]);
	    				
	    				flowsElement.appendChild(nextElementRefElement);
	    				proceedTaskElement.appendChild(flowsElement);
	    			}
	    			

    			}

	    		
		    	
	    		NodeList taskNodes = processControlElements.getElementsByTagName("task");
	    		for(int i=0;i<taskNodes.getLength() ;i++){
	    			Element  taskElement=(Element)taskNodes.item(i);
	    			
	    			if(taskElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
	    				// (t, enabled_t)
	    				Element flowsElement1=transformedDoc.createElement("flowsInto");
	    				Element nextElementRefElement1=transformedDoc.createElement("nextElementRef");
	    				nextElementRefElement1.setAttribute("id", enabledConditionID);
	    				
	    				flowsElement1.appendChild(nextElementRefElement1);
	    				taskElement.appendChild(flowsElement1);
	    				
	    				// (t, proceed_t)
	    				Element flowsElement2=transformedDoc.createElement("flowsInto");
	    				Element nextElementRefElement2=transformedDoc.createElement("nextElementRef");
	    				nextElementRefElement2.setAttribute("id", proceedTaskID);
	    				
	    				flowsElement2.appendChild(nextElementRefElement2);
	    				taskElement.appendChild(flowsElement2);
	    				
	    				
	    				// (t, active_t)
	    				Element flowsElement3=transformedDoc.createElement("flowsInto");
	    				Element nextElementRefElement3=transformedDoc.createElement("nextElementRef");
	    				nextElementRefElement3.setAttribute("id", activeConditionID);
	    				
	    				flowsElement3.appendChild(nextElementRefElement3);
	    				taskElement.appendChild(flowsElement3);
	    				
	    				
	    				// (t, active_task_c)
	    				Element flowsElement4=transformedDoc.createElement("flowsInto");
	    				Element nextElementRefElement4=transformedDoc.createElement("nextElementRef");
	    				nextElementRefElement4.setAttribute("id", getWorkflowElementIDFromName(transformedFilePath, "active_task_"+conditionName));
	    				
	    				flowsElement4.appendChild(nextElementRefElement4);
	    				taskElement.appendChild(flowsElement4);
	    				
	    				
	    				// split(t) , join(t)<-- and
	    				
	    				NodeList joinNode=taskElement.getElementsByTagName("join");
	    				Element  joinElement=(Element)joinNode.item(0);
	    				joinElement.setAttribute("code", "and");
	    				
	    				NodeList splitNode=taskElement.getElementsByTagName("split");
	    				Element  splitElement=(Element)splitNode.item(0);
	    				splitElement.setAttribute("code", "and");
	    				
	    				

	    			}
	    			
	    			if (taskElement.getAttribute("id").compareToIgnoreCase(getWorkflowElementIDFromName(transformedFilePath, "init"))==0){
	    	
	    				// (init, enabled_t)
	    				Element flowsElement1=transformedDoc.createElement("flowsInto");
	    				Element nextElementRefElement1=transformedDoc.createElement("nextElementRef");
	    				nextElementRefElement1.setAttribute("id", enabledConditionID);
	    				
	    				flowsElement1.appendChild(nextElementRefElement1);
	    				taskElement.appendChild(flowsElement1);
	    			
	    				// (init, disabled_t)
	    				Element flowsElement2=transformedDoc.createElement("flowsInto");
	    				Element nextElementRefElement2=transformedDoc.createElement("nextElementRef");
	    				nextElementRefElement2.setAttribute("id", disabledConditionID);
	    				
	    				flowsElement2.appendChild(nextElementRefElement2);
	    				taskElement.appendChild(flowsElement2);

	    			}
	    			
	    			
	    			if (taskElement.getAttribute("id").compareToIgnoreCase(getWorkflowElementIDFromName(transformedFilePath, "sync_"+conditionName))==0){
	    		    	
	    				// (sync_t, check_c_t)
	    				Element flowsElement1=transformedDoc.createElement("flowsInto");
	    				Element nextElementRefElement1=transformedDoc.createElement("nextElementRef");
	    				nextElementRefElement1.setAttribute("id", checkConditionID);
	    				
	    				flowsElement1.appendChild(nextElementRefElement1);
	    				taskElement.appendChild(flowsElement1);
	    			
	    				
	    			}
	    			
	    		}
	    		processControlElements.appendChild(enabledConditionElement);
	    		processControlElements.appendChild(activeConditionElement);
	    		processControlElements.appendChild(disabledConditionElement);
	    		processControlElements.appendChild(checkConditionElement);
	    		processControlElements.appendChild(runTaskElement);
	    		processControlElements.appendChild(proceedTaskElement);
	    		proceedTaskElement.appendChild(validateTaskElement);
	    		processControlElements.appendChild(disableTaskElement);
	    		processControlElements.appendChild(endTaskElement);
	    		
	    		
	    		
	    	}else{// there are more decomposition nodes
	    		
	    	}

			
	    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		    Transformer transformer=transformerFactory.newTransformer();
		    DOMSource source=new DOMSource(transformedDoc);
		    StreamResult result=new StreamResult(transformedFile);
		    transformer.transform(source, result);
		    retVal="true";


			
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String buildConditionCheck( String baseFilePath, String transformedFilePath, String conditionName){
		String retVal="false";
		
		try {
		
			String predecessors=getPredecessors(baseFilePath, getWorkflowElementIDFromName(baseFilePath, conditionName));
			
			
			String lastElementID=findElementsLastID(transformedFilePath);
			
			String conditionID=getWorkflowElementIDFromName(transformedFilePath, conditionName);
			
			File transformedFile=new File(transformedFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);

		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
	    	Element  specificationElement=(Element)specificationNodes.item(0);
	    	
	    	NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");

	    	if (decompositionNodes.getLength()==1){
	    		
	    		Element decompositionElement=(Element)decompositionNodes.item(0);
	    		
	    		NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	    		Element   processControlElements=(Element)processControlNodes.item(0);

	    		
	    		
	    		String subsConditionID="subs_"+conditionName+"_"+lastElementID;
				String syncTaskID="sync_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+1);
	    		
	    		// add subs  condition
				
				
				
				Element  subsConditionElement=createWorkflowConditionElement(transformedDoc, subsConditionID, "subs_"+conditionName, syncTaskID);
				
				
				
				
	    	
				// add sync task
				
				
				Element syncTaskElement=transformedDoc.createElement("task");
				syncTaskElement.setAttribute("id", syncTaskID);
				
				Element  syncTaskNameElement=transformedDoc.createElement("name");
				syncTaskNameElement.appendChild(transformedDoc.createTextNode("sync_"+conditionName));
				
				
				Element syncTaskJoinElement=transformedDoc.createElement("join");
				syncTaskJoinElement.setAttribute("code","and");
				
				Element syncTaskSplitElement=transformedDoc.createElement("split");
				syncTaskSplitElement.setAttribute("code", "and");
				
				syncTaskElement.appendChild(syncTaskNameElement);
				syncTaskElement.appendChild(syncTaskSplitElement);
				syncTaskElement.appendChild(syncTaskJoinElement);
				
				//add run task
				String runTaskID="run_"+conditionName+"_"+String.valueOf(Integer.parseInt(lastElementID)+2);
	    		
	    		Element runTaskElement=transformedDoc.createElement("task");
	    		runTaskElement.setAttribute("id", runTaskID);
	    		
	    		Element runTaskNameElement=transformedDoc.createElement("name");
	    		runTaskNameElement.appendChild(transformedDoc.createTextNode("run_"+conditionName));
	    		
	    		Element runTaskJoinElement=transformedDoc.createElement("join");
	    		runTaskJoinElement.setAttribute("code", "and");
	    		
	    		Element runTaskSplitElement=transformedDoc.createElement("split");
	    		runTaskSplitElement.setAttribute("code", "and");
	    		
	    		runTaskElement.appendChild(runTaskNameElement);
	    		runTaskElement.appendChild(runTaskSplitElement);
	    		runTaskElement.appendChild(runTaskJoinElement);
	    		

	    		
	    		//  create links between new nodes 

	    		// (subs_c, sync_c)
	    		Element subsConditionFlowsIntoElement=transformedDoc.createElement("flowsInto");
	    		Element subsConditionFlowsIntoNextElementRefElement=transformedDoc.createElement("nextElementRef");
	    		subsConditionFlowsIntoNextElementRefElement.setAttribute("id", syncTaskID);
	    		
	    		subsConditionFlowsIntoElement.appendChild(subsConditionFlowsIntoNextElementRefElement);
	    		subsConditionElement.appendChild(subsConditionFlowsIntoElement);
	    		
	   
	    		//(run_c , condition)
	    		Element runTaskFlowsIntoElement=transformedDoc.createElement("flowsInto");
	    		Element runTaskFlowsIntoNextElementRefElement=transformedDoc.createElement("nextElementRef");
	    		runTaskFlowsIntoNextElementRefElement.setAttribute("id", conditionID);
	    		
	    		runTaskFlowsIntoElement.appendChild(runTaskFlowsIntoNextElementRefElement);
	    		runTaskElement.appendChild(runTaskFlowsIntoElement);
	    		
	    		// change predecessors link from condition to subs_c
	    		String[] predecessorsList=predecessors.split("\\,");
	    		for(int i=0; i<predecessorsList.length;i++){
	    			
	    		
	    			NodeList inputConditionNode=processControlElements.getElementsByTagName("inputCondition");
	    			Element  inputConditionElement=(Element)inputConditionNode.item(0);
	    			if(inputConditionElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
    					NodeList flowsIntoNode=inputConditionElement.getElementsByTagName("flowsInto");
    					Element  flowsIntoElement=(Element)flowsIntoNode.item(0);
    			
    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
    					
    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(conditionID)==0){
    						nextElementRefElement.setAttribute("id", subsConditionID);
    					}

	    			}
	    			
	    			

	    			NodeList  processControlElementConditionNodes=processControlElements.getElementsByTagName("condition");
	    			for(int j=0;j<processControlElementConditionNodes.getLength();j++){
	    				Element childElement=(Element)processControlElementConditionNodes.item(j);
	    				if (childElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
	    					
	    					NodeList flowsIntoNode=childElement.getElementsByTagName("flowsInto");
	    					Element  flowsIntoElement=(Element)flowsIntoNode.item(0);
	    					
	    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
	    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
	    					
	    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(conditionID)==0){
	    						nextElementRefElement.setAttribute("id", subsConditionID);
	    					}
	    				}
	    			}
	    			
	    			
	    			NodeList  processControlElementTaskNodes=processControlElements.getElementsByTagName("task");
	    			for(int j=0;j<processControlElementTaskNodes.getLength();j++){
	    				Element childElement=(Element)processControlElementTaskNodes.item(j);
	    				if (childElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
	    					
	    					NodeList flowsIntoNode=childElement.getElementsByTagName("flowsInto");
	    					Element  flowsIntoElement=(Element)flowsIntoNode.item(0);
	    					
	    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
	    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
	    					
	    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(conditionID)==0){
	    						nextElementRefElement.setAttribute("id", subsConditionID);
	    					}
	    				}
	    			}
	    			
	    			
 			
	    			
	    		
	    			
	    		}
	    		
	    		processControlElements.appendChild(runTaskElement);
	    		processControlElements.appendChild(syncTaskElement);
	    		processControlElements.appendChild(subsConditionElement);
	    		
				//layout
	    		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
	    		Element   layoutElement=(Element)layoutNodes.item(0);

	    		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
	    		
	    		if (layoutSpecificationNodes.getLength()==1){
	    			
	    			Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
	    			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");

	    			if (layoutNetNodes.getLength()==1){
	    				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
	    				
	    				// subs condition
	    				String[] subsConditionBounds={"636.0","356.0","32.0","32.0","604.0","388.0","97.0","21.0"};
	    				
//*	    				Element newSubsContainerElement=createConditionContainer(transformedDoc, subsConditionID, subsConditionBounds);
	    		//*		layoutNetElement.appendChild(newSubsContainerElement);
	    				
	    				
	    				// sync task
	    				
	    				String[] syncTaskBounds={"736.0","356.0","32.0","32.0","750.0","388.0","97.0","21.0"};
	    				String[] syncJoinBounds={"700.0","356.0","11.0","32.0","11","32"}; 
	    				String[] syncSplitBounds={"755.0","356.0","11.0","32.0","11","32"}; 

//	*    				Element syncContainerElement=createTaskContainer(transformedDoc, syncTaskID, syncTaskBounds, "", syncJoinBounds, "AND_split", syncSplitBounds);
//	 *   				layoutNetElement.appendChild(syncContainerElement);

	    				
	    				// run task
	    				
	    				String[] runTaskBounds={"786.0","356.0","32.0","32.0","770.0","388.0","97.0","21.0"};
	    				String[] runJoinBounds={"790.0","356.0","11.0","32.0","11","32"}; 
	    				String[] runSplitBounds={"830.0","356.0","11.0","32.0","11","32"}; 

//	 *   				Element runContainerElement=createTaskContainer(transformedDoc, runTaskID, runTaskBounds, "AND_join", runJoinBounds, "AND_split", runSplitBounds);
//	 *   				layoutNetElement.appendChild(runContainerElement);

	    				
	    				// flows
	    				
	    				//(subs, sync)
	    				
	    				String[]  subs_SyncPointsValues={"600.0","165.0","610.0","165.0"};
	    				Element   subs_SyncLayoutNetFlowElement=createFlowElement(transformedDoc, subsConditionID, syncTaskID, "13", "2", subs_SyncPointsValues);
	    				layoutNetElement.appendChild(subs_SyncLayoutNetFlowElement);
	    				
	    		
	    				//(run, condition)
	    				
	    				String[] run_ConditionPointsValues={"830.0","165.0","620.0","165.0"};
	    				Element  run_ConditionLayoutNetFlowElement=createFlowElement(transformedDoc, runTaskID, conditionID, "13", "12", run_ConditionPointsValues);
	    				layoutNetElement.appendChild(run_ConditionLayoutNetFlowElement);

	    				
	    				// (predecessors, condition)
	    	    		for(int i=0; i<predecessorsList.length;i++){
	    	    			
		    				NodeList layoutFlowsNodes=layoutNetElement.getElementsByTagName("flow");
		    				for(int j=0;j<layoutFlowsNodes.getLength();j++){
		    					Element layoutFlowElement=(Element)layoutFlowsNodes.item(j);
		    					if (layoutFlowElement.getAttribute("source").compareToIgnoreCase(predecessorsList[i])==0){
		    						layoutFlowElement.setAttribute("target", conditionID);
		    					}
		    				}
	    	    		}

	    	    		
	    				
	    				
	    			}
	    		}else{// There are more layout nodes
	    			
	    		}

	    	}else{ // There are more decomposition nodes
	    		
	    	}

			
			
			
	    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		    Transformer transformer=transformerFactory.newTransformer();
		    DOMSource source=new DOMSource(transformedDoc);
		    StreamResult result=new StreamResult(transformedFile);
		    transformer.transform(source, result);
		    retVal="true";


			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	
	
	
	public static String runTaskCheck(String baseFile, String viewFilePath, String transformedFilePath){
		String retVal="false";
		
		try {
			
			
			String starts=getStarts(transformedFilePath, viewFilePath);
			if (starts!=""){
				String[] startList=starts.split("\\,");
				
				for (int i=0;i<startList.length;i++){
					
					String workflowName=getWorkflowName(transformedFilePath);
					String stopName=getStopOfStart(viewFilePath, startList[i], workflowName);
					
					retVal=buildTaskCheck(baseFile, transformedFilePath, startList[i], stopName);
					
					
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String runConditionsCheck(String baseFile, String viewFilePath, String transformedFilePath){
		String retVal="false";
		
		try {
			
			
			String stops=getStops(transformedFilePath, viewFilePath);
			if (stops!=""){
				String[] stopList=stops.split("\\,");
				
				for (int i=0;i<stopList.length;i++){
					retVal=buildConditionCheck(baseFile, transformedFilePath, stopList[i]);
					if(retVal.compareTo("true")==0){
						retVal=buildPresenceCheck(baseFile, transformedFilePath, stopList[i]);
						
					}
				}
			}
			
			
			
	
			
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	
	public static String getWorkflowElementContainerAttributeXY(String workflowFileFullPath, String ElementID){
		String retVal="";
		
		try {
			File workflowFile=new File(workflowFileFullPath);
			DocumentBuilder workflowBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = workflowBuilder.parse(workflowFile);

		
	    	NodeList specificationSetNodes=doc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
			
    		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
    		Element   layoutElement=(Element)layoutNodes.item(0);

    		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
    		
    		if (layoutSpecificationNodes.getLength()==1){
    			
    			Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
    			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");

    			if (layoutNetNodes.getLength()==1){
    				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
    				Boolean found=false;
    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
    				for(int i=0; i<containerNodes.getLength() && !found;i++){
    					Element containerElement=(Element)containerNodes.item(i);
    					if(containerElement.getAttribute("id").compareToIgnoreCase(ElementID)==0){
    						found=true;
    						NodeList vertexNode=containerElement.getElementsByTagName("vertex");
    						Element  vertexElement=(Element)vertexNode.item(0);
    						
    						NodeList attributesNode=vertexElement.getElementsByTagName("attributes");
    						Element  attributesElement=(Element)attributesNode.item(0);
    						
    						NodeList boundsNode=attributesElement.getElementsByTagName("bounds");
    						Element  boundsElement=(Element)boundsNode.item(0);
    						
    						retVal=boundsElement.getAttribute("x")+","+boundsElement.getAttribute("y");
    						return retVal;
    					}
    				}
    				
    			}else{// there are more layout net nodes
    				
    			}
    		}else{// there are more layout specification nodes
    			
    		}
	    	
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	public static String calculateElementLabelXY(String ElementX , String ElementY){
		String retVal="";
		try {
			
			int x=Integer.valueOf(ElementX.split("\\,")[0])-32;
			int y=Integer.valueOf(ElementY.split("\\,")[0])+32;
			
			retVal=String.valueOf(x)+".0"+","+String.valueOf(y)+".0";
			
			return retVal;
			
			
			

			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	public static String calculateElementJoinXY(String ElementX, String ElementY){
		String retVal="";
		
		try {
			
			int x=Integer.valueOf(ElementX.split("\\,")[0])-10;
			int y=Integer.valueOf(ElementY.split("\\,")[0]);
			
			retVal=String.valueOf(x)+".0"+","+String.valueOf(y)+".0";
			
			return retVal;

			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	public static String calculateElementSplitXY(String ElementX, String ElementY){
		String retVal="";
		
		try {
			
			int x=Integer.valueOf(ElementX.split("\\,")[0])+31;
			int y=Integer.valueOf(ElementY.split("\\,")[0]);
			
			retVal=String.valueOf(x)+".0"+","+String.valueOf(y)+".0";
			
			return retVal;

			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	public static String getUserKeyFromRequest(HttpServletRequest request){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString();
			
   			if (!(requestQueryString.indexOf("userKey")==-1)){
   				retVal=request.getParameter("userKey");
				if((retVal=="") ||(retVal.isEmpty())){
					retVal="false";
				}
				
			}else{// request does not include userKey
				retVal="false";
			}
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String getRequestTypeFromRequest(HttpServletRequest request){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString(); 
			
   			if (!(requestQueryString.indexOf("requestType")==-1)){
   				retVal=request.getParameter("requestType");
				if((retVal=="") ||(retVal.isEmpty())){
					retVal="false";
				}
				
			}else{// request does not include request type
				retVal="false";
			}
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String getOperationFromRequest(HttpServletRequest request){
		String retVal="false";

		try {
			String requestQueryString=request.getQueryString();
			
			if (!(requestQueryString.indexOf("operation")==-1)){
				retVal=request.getParameter("operation");
				if((retVal=="") ||(retVal.isEmpty())){
					retVal="false";
 
				}
				
			}else{// request does not include operation
				retVal="false";
			}
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String getFeatureModelNameFromRequest(HttpServletRequest request){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString();
			
			if (!(requestQueryString.indexOf("modelName")==-1)){
				retVal=request.getParameter("modelName");
				if((retVal=="") || (retVal.isEmpty())){
					retVal="false";
				}
				
			}else{// request does not include featureModelName
				retVal="false";
			}

			retVal=retVal.replace("?", " ");

		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String getFeatureModelFileNameFromRequest(HttpServletRequest request, String modelDir, String featureModelName){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString();

			if(requestQueryString.indexOf("selectedModels")==-1){
    			if (featureModelName.compareToIgnoreCase("false")==0){
    				retVal="false";
    			}else{
    				retVal=Methods.getfeatureModelFileName(modelDir, featureModelName);
	        		if ((retVal.isEmpty()) ||(retVal=="")  || (retVal.compareToIgnoreCase("false")==0)){
	        			retVal="false";
	        		}
    			}

			}else{//if(requestQueryString.indexOf("selectedModels")==-1){

				retVal=(String)request.getParameter("selectedModels");
				if ((retVal=="") ||(retVal.isEmpty())  || (retVal.compareToIgnoreCase("false")==0)){
        			if (featureModelName.compareToIgnoreCase("false")==0){
        				retVal="false";
        			}else{
        				retVal=Methods.getfeatureModelFileName(modelDir, featureModelName);
    	        		if ((retVal=="") ||(retVal.isEmpty())  || (retVal.compareToIgnoreCase("false")==0)){
    	        			retVal="false";
    	        		}
        			}

				}
			}
			
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String getNewSessionFromRequest(HttpServletRequest request){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString();
			
			if (!(requestQueryString.indexOf("newSession")==-1)){
				retVal=(String)request.getParameter("newSession");
        		if (!((retVal.isEmpty())) && (!(retVal=="")) && (retVal.compareToIgnoreCase("true")==0)){
        			retVal="true";
        		}else{
        			retVal="false";
        		}
        	}else{
        		retVal="false";
        	}
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String getWorkflowNameFromRequest(HttpServletRequest request){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString();
			
			if (requestQueryString.indexOf("workflowName")==-1){
				retVal="false";

			}else{
				retVal=(String)request.getParameter("workflowName");
				if ((retVal=="") || (retVal.isEmpty()) || (retVal.compareToIgnoreCase("false")==0)){
					retVal="false";
				}
			}
			
		} catch (Exception e) {
			retVal="false";
		}
		return retVal;
	}

	public static String getTaskNameFromRequest(HttpServletRequest request){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString();
			
			if (requestQueryString.indexOf("taskName")==-1){
				retVal="false";
			}else{
				retVal=(String)request.getParameter("taskName");
    			if ((retVal=="") || (retVal.isEmpty())||(retVal.compareToIgnoreCase("false")==0)){
    				retVal="false";
    			}
			}
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	
	public static String getViewNameFromRequest(HttpServletRequest request, String viewDir, String workflow, String featureModelName, String task){
		String retVal="none";
		
		try {
			
			String requestQueryString=request.getQueryString();
			if (requestQueryString.indexOf("viewName")==-1){
				
       			if ((workflow.compareToIgnoreCase("false")==0) || (featureModelName.compareToIgnoreCase("false")==0) || (task.compareToIgnoreCase("false")==0)){
       				retVal="none";

    			}else{
    				String tmpViewName=	Methods.getTaskViewName(viewDir, workflow, featureModelName, task);
     				if ((tmpViewName.compareToIgnoreCase("false")==0) ||(tmpViewName.isEmpty()) || (tmpViewName=="") ){
     					retVal="none";
    				}else{
    					retVal=tmpViewName;
    				}
    			}
			}else{//if (requestQueryString.indexOf("viewName")==-1)
				retVal=(String)request.getParameter("viewName");
    			if ((retVal.isEmpty()) ||(retVal=="") ){
    				retVal="none";
    			}

			}
		} catch (Exception e) {
			retVal="none";
		}
		
		return retVal;
	}
	
	public static String getViewTypeFromRequest(HttpServletRequest request, String viewName){
		
		String retVal="none";
		
		try {
			String requestQueryString=request.getQueryString();
			if (requestQueryString.indexOf("viewType")==-1){
				retVal="none";
    		}else{
    			retVal=(String)request.getParameter("viewType");
    			if ((retVal.isEmpty()) ||(retVal=="") ){
    				retVal="none";
    			}
    		}
          
			if (viewName.compareToIgnoreCase("none")==0){
    			retVal="none";
            }

			
		} catch (Exception e) {
			retVal="none";
		}
		
		return retVal;
	}
	
	public static String getUserNameFromRequest(HttpServletRequest request){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString();
			
			if (requestQueryString.indexOf("userName")==-1){
				retVal="false";
			}else{
				retVal=(String)request.getParameter("userName");
    			if ((retVal.isEmpty()) ||(retVal=="") ){
    				retVal="false";
    			}
			}
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String getUserIDFromRequest(HttpServletRequest request){
		String retVal="false";
		
		try {
			
			String requestQueryString=request.getQueryString();
			
			if (requestQueryString.indexOf("userID")==-1){
				retVal="false";
			}else{
				retVal=(String)request.getParameter("userID");
    			if ((retVal.isEmpty()) ||(retVal=="") ){
    				retVal="false";
    			}
			}
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
}
