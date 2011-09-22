package splot.services.extensions.fundp.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TimeZone;

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

import org.json.simple.JSONValue;
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
	
	
	public static void evaluateXPathExpressionWithSeparator(String XMLData,String XPathExpression , EvaluationResult result) throws XPathExpressionException{
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
	
	
	
	public static String getViewCoveredFeatures(String FeatureModelFilePath, String viewFilePath, String viewName){
		String retVal="";
		
		
		
		try {
			String featureModelInXML="";
			featureModelInXML=SXFMToXML.parse(FeatureModelFilePath);
			if ((featureModelInXML=="") || (FeatureModelFilePath==null) || (featureModelInXML.compareToIgnoreCase("error")==0)){
				retVal="";
			}else{
		   		FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(FeatureModelFilePath, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();
				
				
				String featureModelName=featureModel.getName();
				
				String xpathExpression=getXPathFromViewWithPath(viewFilePath, featureModelName, viewName);
				
			
				
				for( FeatureTreeNode feature : featureModel.getNodes()) {
					
					String exist=FeatureInXPathExpressionWithPath(feature.getName(), FeatureModelFilePath, xpathExpression);
					if (exist.compareToIgnoreCase("true")==0){
					
						if(retVal==""){
							retVal=feature.getName();
						}else{
							retVal=retVal+","+feature.getName();
						}
					}
					
					
					
					List<FeatureTreeNode> featurePropagatedList =new LinkedList<FeatureTreeNode>();
					
					featurePropagatedList=feature.getPropagatedNodes();
					
					ListIterator itr=featurePropagatedList.listIterator();
					while(itr.hasNext()){
						FeatureTreeNode propagatedFeature=(FeatureTreeNode)itr.next();
						if(retVal==""){
							retVal=propagatedFeature.getName();
						}else{
							retVal=retVal+","+propagatedFeature.getName();
						}
						
						
					}
					
					
					
					
				}
				
			


			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return retVal;
		
	}
	
	
	
	public static String getFeatureIDFromName(String FeatureModelFilePath,String featureName){
		String retVal="";
		
		try {
			
			String featureModelInXML="";
			featureModelInXML=SXFMToXML.parse(FeatureModelFilePath);
			if ((featureModelInXML=="") || (FeatureModelFilePath==null) || (featureModelInXML.compareToIgnoreCase("error")==0)){
				retVal="";
			}else{

		   		FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(FeatureModelFilePath, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();
				for( FeatureTreeNode feature : featureModel.getNodes()) {
					if(feature.getName().compareTo(featureName)==0){
						retVal=feature.getID();
						return retVal;
					}
				}
				
			}
			
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	
	public static String getFeatureNameFromID(String FeatureModelFilePath,String featureID){
		String retVal="";
		
		try {
			
			String featureModelInXML="";
			featureModelInXML=SXFMToXML.parse(FeatureModelFilePath);
			if ((featureModelInXML=="") || (FeatureModelFilePath==null) || (featureModelInXML.compareToIgnoreCase("error")==0)){
				retVal="";
			}else{

		   		FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(FeatureModelFilePath, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();
				for( FeatureTreeNode feature : featureModel.getNodes()) {
					if(feature.getID().compareTo(featureID)==0){
						retVal=feature.getName();
						return retVal;
					}
				}
				
			}
			
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	public static String getViewFeatures(String FeatureModelFilePath, String viewFilePath, String viewName){
		String retVal="";
		
		
		
		try {
			String featureModelInXML="";
			featureModelInXML=SXFMToXML.parse(FeatureModelFilePath);
			if ((featureModelInXML=="") || (FeatureModelFilePath==null) || (featureModelInXML.compareToIgnoreCase("error")==0)){
				retVal="";
			}else{
		   		FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(FeatureModelFilePath, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();
				
				
				String featureModelName=featureModel.getName();
				
				String xpathExpression=getXPathFromViewWithPath(viewFilePath, featureModelName, viewName);
					for( FeatureTreeNode feature : featureModel.getNodes()) {
					
					String exist=FeatureInXPathExpressionWithPath(feature.getName(), FeatureModelFilePath, xpathExpression);
					if (exist.compareToIgnoreCase("true")==0){
					
						if(retVal==""){
							retVal=feature.getName();
						}else{
							retVal=retVal+","+feature.getName();
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return retVal;
		
	}
	
	
	
	public static String getViewCoveredFeaturesList(String viewFilePath, String viewName){
		String retVal="";
		
		
		
		try {
				File viewFile = new File(viewFilePath);
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = builder.parse(viewFile);
				
				NodeList viewNodes = doc.getElementsByTagName("view");	
				for(int i=0;i<viewNodes.getLength();i++){
					Element viewElement=(Element)viewNodes.item(i);
					if(viewElement.getAttribute("name").compareToIgnoreCase(viewName)==0){
						
						NodeList featuresNode=viewElement.getElementsByTagName("features");
						if(featuresNode.getLength()>=1){
							Element featuresElement=(Element)featuresNode.item(0);
							retVal=getCharacterDataFromElement(featuresElement);
							
						}else{
							retVal="";
						}
					}
				}
				
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return retVal;
		
	}
	
	public static  String getViews(String viewDir) throws HandlerExecutionException {
		String retValue="";
		
		try {
			
	       					File viewFile = new File(viewDir);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
          					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
           					Element  featureModelElement = (Element) featureModelNodes.item(0);
           					String   featureModelElementName=featureModelElement.getAttribute("name");
           					
           				
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
           						
           				

		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing the view repository path":e.getMessage());
		}
			

		
		return   retValue;
	}

	public static  String getViews(String viewDir, String workflowName) throws HandlerExecutionException {
		String retValue="";
		
		try {
			
	       					File viewFile = new File(viewDir);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(viewFile);
           						NodeList viewNodes = doc.getElementsByTagName("view");
               					for (int i = 0; i < viewNodes.getLength(); i++){
                					Element viewElement = (Element) viewNodes.item(i);
                					NodeList taskNodes=viewElement.getElementsByTagName("task");
                					if(taskNodes.getLength()>0){
                						for(int j=0; j<taskNodes.getLength();j++){
                    						Element taskElement=(Element)taskNodes.item(j);
                    						
                    						NodeList workflowNodes=taskElement.getElementsByTagName("workflow");
                    						
                    						if(workflowNodes.getLength()>0){
                    							for(int k=0; k<workflowNodes.getLength();k++){
                    								Element workflowElement=(Element)workflowNodes.item(k);
                    								if(workflowElement.getAttribute("name").compareToIgnoreCase(workflowName)==0){
                                    					if (retValue==""){
                                    						retValue=(String)viewElement.getAttribute("name").trim();
                                    					}else{
                                    						retValue=retValue+","+(String)viewElement.getAttribute("name").trim();
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
	
	
	public static String getXPathFromViewWithPath(String viewDir, String featureModelName,String viewName){
		String retValue="";
		try {
			
	       					File viewFile = new File(viewDir);
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
		       						  
		       							if ((retValue=="") || (retValue.isEmpty()) || (retValue==null)){
		       								retValue="error";
		       							}
		       							
		       							return retValue;

	       							}
	       								
	       						}

	       						
	       					}
			
		} catch (Exception e) {
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
	
	
	public static String getFeatureModelFeatures(String featureModelFilePath){
		String retVal="";
		try {
			
			FeatureModel featureModel = null;
			featureModel = new XMLFeatureModel(featureModelFilePath, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
			featureModel.loadModel();
			for( FeatureTreeNode feature : featureModel.getNodes()) {
				
				if(!(feature instanceof FeatureGroup)){
					if (retVal==""){
						retVal=feature.getName();
					}else{
						retVal=retVal+","+feature.getName();
					}
				}
				
				
				
			}
			
			
		} catch (Exception e) {
			retVal="";
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

	
	
	public static  String FeatureInXPathExpressionWithPath(String featureName , String featureModelFullPath,  String XPathExpression) throws HandlerExecutionException  {
		String retVal="unknown";
		
		try {
			
			
		
			File   Modelfile = new File(featureModelFullPath);
			
			if (!Modelfile.exists()){
				retVal="unknown";
				throw new HandlerExecutionException("The feature model file not found.");

			}

			
			String featureModelInXML=SXFMToXML.parse(featureModelFullPath);
			
			
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
		       					File modelFile = new File(ModelsPath+"/"+fileName);
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
	
	
	
	public static String getSDFromViewFile(String viewFileFullPath,String viewName){
		String retVal="";
		
		try {
			
			File viewFile = new File(viewFileFullPath);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(viewFile);

			NodeList  viewNodes=doc.getElementsByTagName("view");
			for(int i=0;i<viewNodes.getLength();i++){
				
				Element viewElement=(Element)viewNodes.item(i);
				if(viewElement.getAttribute("name").compareToIgnoreCase(viewName)==0){
					NodeList sdNode=viewElement.getElementsByTagName("sd");
					if(sdNode.getLength()>0){
						Element sdElement=(Element)sdNode.item(0);
						retVal=getCharacterDataFromElement(sdElement);
						return retVal;
					}
				}
				
			}
			
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	
	public static String getWDFromViewFile(String viewFileFullPath,String viewName){
		String retVal="";
		
		try {
			
			File viewFile = new File(viewFileFullPath);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(viewFile);

			NodeList  viewNodes=doc.getElementsByTagName("view");
			for(int i=0;i<viewNodes.getLength();i++){
				
				Element viewElement=(Element)viewNodes.item(i);
				if(viewElement.getAttribute("name").compareToIgnoreCase(viewName)==0){
					NodeList wdNode=viewElement.getElementsByTagName("wd");
					if(wdNode.getLength()>0){
						Element wdElement=(Element)wdNode.item(0);
						retVal=getCharacterDataFromElement(wdElement);
						return retVal;
					}
				}
				
			}
			
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
	}
	
	public static String getOpenFeaturesList(String viewDir,String modelDir,String configurationDir,String viewName,String modelName,String viewFileName,String featureModelFileName,String configurationFileName, String userKey){
		String retVal="";
		
		try {
			
			
			String openFeatures="";
			
			
			String mustConfiguredFeatures="";
			String warningFeatures="";
			
			
			String featureModelFileFullPath=modelDir+"/"+featureModelFileName;
			String viewFileFullPath=viewDir+"/"+viewFileName;
			
			String viewFeatures=getViewFeatures(featureModelFileFullPath, viewFileFullPath, viewName);
			
			String[] sdList=getSDFromViewFile(viewFileFullPath, viewName).split("\\,");
			String[] wdList=getWDFromViewFile(viewFileFullPath, viewName).split("\\,");

			
			String[] featuresList=viewFeatures.split("\\,");
			for(int i=0;i<featuresList.length;i++){
				FeatureDecisionInfo decisionResult=new FeatureDecisionInfo();
				decisionResult=Methods.getFeatureDecisionInfo(modelDir, userKey, getFeatureIDFromName(featureModelFileFullPath, featuresList[i]));
				
				
				if(decisionResult.found){
					
					if(decisionResult.value.compareToIgnoreCase("0")==0){//open feature
						
						
						Boolean featureSD=false;
						Boolean featureWD=false;
						
						for(int j=0;j<sdList.length;j++){
							if(sdList[j].compareTo(featuresList[i])==0){
								featureSD=true;
								break;
							}
						}
						
						for(int j=0;j<wdList.length;j++){
							if(wdList[j].compareTo(featuresList[i])==0){
								featureWD=true;
								break;
							}
						}
						
						if((!featureSD) && (!featureWD)){
							if(mustConfiguredFeatures==""){
								mustConfiguredFeatures=featuresList[i];
							}else{
								mustConfiguredFeatures=mustConfiguredFeatures+","+featuresList[i];
							}
						}else if((!featureSD) && (featureWD)){
							if(warningFeatures==""){
								warningFeatures=featuresList[i];
							}else{
								warningFeatures=warningFeatures+","+featuresList[i];
							}
						}
						
						
						
						
					}
					
				}else{//open feature
					Boolean featureSD=false;
					Boolean featureWD=false;
					
					for(int j=0;j<sdList.length;j++){
						if(sdList[j].compareTo(featuresList[i])==0){
							featureSD=true;
							break;
						}
					}
					
					for(int j=0;j<wdList.length;j++){
						if(wdList[j].compareTo(featuresList[i])==0){
							featureWD=true;
							break;
						}
					}
					
					if((!featureSD) && (!featureWD)){
						if(mustConfiguredFeatures==""){
							mustConfiguredFeatures=featuresList[i];
						}else{
							mustConfiguredFeatures=mustConfiguredFeatures+","+featuresList[i];
						}
					}else if((!featureSD) && (featureWD)){
						if(warningFeatures==""){
							warningFeatures=featuresList[i];
						}else{
							warningFeatures=warningFeatures+","+featuresList[i];
						}
					}
					
					
				}
				
				
			}
			
			
			
			
			if(warningFeatures==""){
				warningFeatures="empty";
			}
			
			if(mustConfiguredFeatures==""){
				mustConfiguredFeatures="empty";
			}
			
			
			retVal=warningFeatures+";"+mustConfiguredFeatures;
			
			
			
			
		} catch (Exception e) {
			retVal="false";
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

	       					File viewFile = new File(viewDir+"/"+fileName);
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
	
	
	
	public static String getTaskViewNameByDir(String viewDir, String workflowName,  String taskName){
		String retVal="false";
		
		try {
			
			
					
			
			File viewFile = new File(viewDir);
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
	  

	    public static String getWorkflowFileName(String workflowName, String workflowDir){
	    	String retVal="false";
			File  dir=new File(workflowDir);
			String[]  childeren=dir.list();

	    	try {
				
	    		start:
	    			for (int i=0;i<childeren.length;i++){
	    				if (childeren!=null){
	    					if (childeren!=null){
	    						if ((childeren[i].endsWith(".xml") !=false)  || (childeren[i].endsWith(".yawl") !=false)  ){
	    							String  fileName=childeren[i];
	    	       					File workflowFile = new File(workflowDir+"/"+fileName);
	    	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    	       					Document doc = builder.parse(workflowFile);
	    	       					
	    	       					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
	    	       					Element specificationElement = (Element) specificationNodes.item(0);
	    	       					if (specificationElement.getAttribute("uri").trim().compareToIgnoreCase(workflowName)==0){
	    	       						retVal=fileName;
	    	       						break start;
	    	       					}
	    						}
	    					}
	    				}
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
		
		
		public static String deleteLayout(String workflowFilePath){
			String retVal="false";
			try {
				File baseFile=new File(workflowFilePath);
				DocumentBuilder baseBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document baseDoc = baseBuilder.parse(baseFile);
				
				
		    	NodeList specificationSetNodes=baseDoc.getElementsByTagName("specificationSet");
		    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
				
				NodeList layoutNodes=specificationSetElement.getElementsByTagName("layout");
		    	Element  layoutElement=(Element)layoutNodes.item(0);
		    	
		    	specificationSetElement.removeChild(layoutElement);

		    	
		       	TransformerFactory transformerFactory=TransformerFactory.newInstance();
			    Transformer transformer=transformerFactory.newTransformer();
			    DOMSource source=new DOMSource(baseDoc);
			    StreamResult result=new StreamResult(baseFile);
			    transformer.transform(source, result);
			    
		    	retVal="true";

			} catch (Exception e) {
				retVal="false";
			}
			
			return retVal;
		}
		
		
		

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		public static void CDEP(String originalWorkflowFileFullPath, String viewFileFullPath, String featureModelFileFullPath, String DependencySetFilePath) throws IOException{
//			
//			try {
//				
//				String workflowName=getWorkflowName(originalWorkflowFileFullPath);
//				String stop=getStops(originalWorkflowFileFullPath, viewFileFullPath);
//				String[] stopList=stop.split("\\,");
//				
//				long startTime = System.currentTimeMillis();
//				
//				for (int i=0;i<stopList.length;i++){
//					String allPlaces=getWorkflowElements(originalWorkflowFileFullPath);
//					
//		    		String[] places=allPlaces.split("\\?");
//		    		int allPlacesNo=places.length;
//					
//					PlaceDependencySet[] PDS = new PlaceDependencySet[allPlacesNo];
//		    		
//		    		for(int j=0; j<allPlacesNo;j++){
//		    			PDS[j]=new PlaceDependencySet();
//		    		}
//		    		
//					
//
//					WDFS(originalWorkflowFileFullPath,viewFileFullPath,featureModelFileFullPath,stopList[i],PDS,allPlacesNo);
//					
//					
//					
//					
//					
//					String[] tasks=getStartsOfStop(viewFileFullPath, stopList[i], workflowName).split("\\,");
//					for (int j=0;j<tasks.length;j++){
//						
//						for (int k=0;k<allPlacesNo;k++){
//						
//							if(PDS[k].placeName.compareTo(tasks[j])==0){
//								String SDList="";
//								
//								if(!(PDS[k].sds==null)){
//									for(int l=0;l<PDS[k].sds.length;l++){
//										if(SDList==""){
//											if(!(PDS[k].sds[l]==null)){
//												SDList=PDS[k].sds[l];
//											}
//											
//										}else{
//											if(!(PDS[k].sds[l]==null)){
//												SDList=SDList+","+PDS[k].sds[l];
//											}
//											
//										}
//									
//									}
//								}
//								
//								
//								
//								String WDList="";
//								
//								if(!(PDS[k].wds==null)){
//
//									for(int l=0;l<PDS[k].wds.length;l++){
//										if(WDList==""){
//											if(!(PDS[k].wds[l]==null)){
//												WDList=PDS[k].wds[l];
//											}
//											
//										}else{
//											if(!(PDS[k].wds[l]==null)){
//												WDList=WDList+","+PDS[k].wds[l];
//											}
//											
//										}
//									
//									}
//								}
//								
//								
//								 Writer output=null;
//									
//								 File file=new File(DependencySetFilePath);
//								 output=new BufferedWriter(new FileWriter(file,true));
//								 String text=tasks[j]+","+stopList[i]+":"+"\n";
//								 text=text+"WD:"+WDList+"\n";
//								 text=text+"SD:"+SDList+"\n\n";
//								 output.write(text);
//								 output.close();
//								
//							}
//							
//							
//						}
//						
//						
//						
//						
//					}
//					
//					
//					
//					
//				}
//				
//				long currentTime = System.currentTimeMillis();
//				long elapsed =currentTime - startTime; 
//				
//				 Writer output=null;
//					
//				 File file=new File(DependencySetFilePath);
//				 output=new BufferedWriter(new FileWriter(file,true));
//				 String text="-----------------------------------"+"\n";
//				 text=text+"Computation Time:"+String.valueOf(elapsed)+"\n";
//				 output.write(text);
//				 output.close();
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//
//			
//			
//		}
		
		
		
//		public static void WDFS (String originalWorkflowFileFullPath, String viewFileFullPath,String featureModelFileFullPath, String stop, PlaceDependencySet[] PDS, int allPlacesNo ){
//			
//			try {
//				
//				
//				Stack stack = new Stack();
//				String stopID=getWorkflowElementIDFromName(originalWorkflowFileFullPath, stop);
//				String stopPredecessor=getPredecessors(originalWorkflowFileFullPath, stopID);
//				
//				String[] predecessorsList=stopPredecessor.split("\\,");
//	    		for(int i=0; i<predecessorsList.length;i++){
//		    			stack.add(predecessorsList[i]);
//		    	//		System.out.println("stopName="+stop+"/operation=push"+"/place="+predecessorsList[i]);
//
//	    		}
//	    		
//	    		
//	    	
//	    		String allPlaces=getWorkflowElements(originalWorkflowFileFullPath);
//	    		String[] places=allPlaces.split("\\?");
//	    	
//	    		for(int i=0; i<allPlacesNo;i++){
//	    	
//	    			String placeName=places[i].split("\\,")[0];
//	    			String placeID=places[i].split("\\,")[1];
//	    			String placeType=places[i].split("\\,")[2];
//
//	    			
//	    			if(placeID.compareToIgnoreCase(stopID)==0){
//	    				PDS[i].placeName=placeName;
//	    				PDS[i].placeID=placeID;
//	    				PDS[i].fixed=true;
//	    				PDS[i].placeType=placeType;
//	    				PDS[i].sds=null;
//	    				PDS[i].wds=null;
//	    				PDS[i].wds_view=new String[allPlacesNo];
//	    				PDS[i].sds_view=new String[allPlacesNo];
//
//	    			}else{
//	    				PDS[i].placeName=placeName;
//	    				PDS[i].placeID=placeID;
//	    				PDS[i].fixed=false;
//	    				PDS[i].placeType=placeType;
//	    				PDS[i].sds=null;
//	    				PDS[i].wds=null;
//	    				PDS[i].wds_view=new String[allPlacesNo];
//	    				PDS[i].sds_view=new String[allPlacesNo];
//	    			}
//	    		
//	    			
//	    			
//	    		}
//	    		
//	    	
//	    		
//	
//	    			
//	    		while(!stack.empty()){
//	    				String place=(String) stack.pop();
//	    				
//		    		//	System.out.println("stopName="+stop+"/operation=pop"+"/place="+place);
//
//	    				for(int i=0; i<allPlacesNo;i++){
//	    					if(PDS[i].placeID.compareToIgnoreCase(place)==0){
//	    						if (!PDS[i].fixed){
//	    							SEARCH(originalWorkflowFileFullPath, viewFileFullPath, featureModelFileFullPath,PDS[i].placeID,getWorkflowElementType(originalWorkflowFileFullPath, PDS[i].placeID),PDS,stack,allPlacesNo,stop);
//		
//	    						}
//	    					}
//	    				}
//	    				
//	    				
//	    				
//	    			}
//	    			
//	    		
//	    		
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//    		
//    		
//    		
//    		
//			
//
//			
//		}
//		

//		public static void SEARCH(String originalWorkflowFileFullPath, String viewFileFullPath,String featureModelFileFullPath, String place, String placeType, PlaceDependencySet[] PDS, Stack stack, int allPlacesNo, String stop){
//			
//			try {
//
//				String[] tmps=null;
//				String[] tmpw=null;
//				String[] allFeatures=null; 
//				String placeSplitType=null;
//				String workflowName=getWorkflowName(originalWorkflowFileFullPath);
//				if(placeType.compareToIgnoreCase("task")==0){
//					 String splitType=getTaskSplitType(originalWorkflowFileFullPath, place);
//
//					
//					 if (splitType==""){
//						 placeSplitType="false";
//					 }else{
//						 placeSplitType=splitType; 
//						
//					 }
//				}
//				
//				if ((placeType.compareToIgnoreCase("task")==0) &&(placeSplitType.compareToIgnoreCase("AND")==0)){
//					tmps=null;
//					
//				}else{
//
//					//String featureModelFeatures=getFeatureModelFeatures(featureModelFileFullPath);
//					
//					String featureModelFeatures=getFeaturesFromTextFile(featureModelFileFullPath);
//
//					if(featureModelFeatures!=""){
//						allFeatures=new String[featureModelFeatures.split("\\,").length];
//						allFeatures=featureModelFeatures.split("\\,");
//
//						tmps=new String[featureModelFeatures.split("\\,").length];
//						tmps=featureModelFeatures.split("\\,");
//					}
//					
//				}
//				tmpw=null;
//				
//				
//
//				String successors=getSuccessors(originalWorkflowFileFullPath, place);
//				String[] successorList=successors.split("\\,");
//				for (int i=0;i<successorList.length;i++){
//					
//					String successorPlaceID=successorList[i];
//					String successorPlaceName=getWorkflowElementNameFromID(originalWorkflowFileFullPath, successorPlaceID);
//					String successorPlaceType=getWorkflowElementType(originalWorkflowFileFullPath, successorPlaceID);
//				
//					
//					if((successorPlaceType.compareToIgnoreCase("condition")==0) || (successorPlaceType.compareToIgnoreCase("inputCondition")==0) || (successorPlaceType.compareToIgnoreCase("outputCondition")==0)){
//						for (int j=0;j<allPlacesNo;j++){
//							
//							
//							if (PDS[j].placeID.compareToIgnoreCase(successorPlaceID)==0){
//								
//								String[] temp1=null;
//								
//								if((tmpw==null) && (PDS[j].wds==null) ){
//									temp1=null;
//								}else if ((tmpw==null) && (!(PDS[j].wds==null)) ){
//									temp1=new String[PDS[j].wds.length];
//									
//								}else if ((!(tmpw==null)) && (PDS[j].wds==null)){
//									temp1=new String[tmpw.length];
//									
//								}else{
//									
//									temp1=new String[tmpw.length+PDS[j].wds.length];
//									
//								}	
//								
//								temp1=union(tmpw, PDS[j].wds);
//								tmpw=null;
//								if (!(temp1==null)){
//									tmpw=new String[temp1.length];
//									for (int k=0;k<temp1.length;k++){
//										if(!(temp1[k]==null)){
//											tmpw[k]=temp1[k];
//										}
//									}
//								}
//								
//								String[] temp2=null;
//								
//								if((tmps==null) && (PDS[j].sds==null) ){
//									temp2=null;
//								}else if ((tmps==null) && (!(PDS[j].sds==null)) ){
//									temp2=new String[PDS[j].sds.length];
//									
//								}else if ((!(tmps==null)) && (PDS[j].sds==null)){
//									temp2=new String[tmps.length];
//									
//								}else{
//									
//									temp2=new String[tmps.length+PDS[j].sds.length];
//									
//								}	
//								temp2=intersection(tmps, PDS[j].sds);
//								tmps=null;
//								
//								if(!(temp2==null)){
//									tmps=new String[temp2.length];
//									for (int k=0;k<temp2.length;k++){
//										if(!(temp2[k]==null)){
//											tmps[k]=temp2[k];
//										}
//										
//									}
//								}
//								
//							
//								
//								
//							}
//						}
//						
//					}else{
//						
//						
//						String viewName=getTaskViewNameByDir(viewFileFullPath, workflowName, successorPlaceName);
//						String[] result=null;
//						if(viewName.compareToIgnoreCase("false")==0){
//							result=null;
//						}else{
//
//							String featuresString=getViewCoveredFeaturesList(viewFileFullPath, viewName);
//							result=new String[featuresString.split("\\,").length];
//							
//							result=featuresString.split("\\,");
//							
//
//						}
//						
//						for (int j=0;j<allPlacesNo;j++){
//							if (PDS[j].placeID.compareToIgnoreCase(successorPlaceID)==0){
//								
//								String[] temp1=null;
//								if((PDS[j].wds==null) && (result==null) ){
//									temp1=null;
//								}else if ((PDS[j].wds==null) && (!(result==null)) ){
//									temp1=new String[result.length];
//									
//								}else if ((!(PDS[j].wds==null)) && (result==null)){
//									temp1=new String[PDS[j].wds.length];
//									
//								}else{
//									
//									temp1=new String[PDS[j].wds.length+result.length];
//									
//								}	
//								
//								 temp1=union(PDS[j].wds,result);
//								
//								
//								 String[] temp2=null;
//								 	if((tmpw==null) && (temp1==null) ){
//										temp2=null;
//									}else if ((tmpw==null) && (!(temp1==null)) ){
//										temp2=new String[temp1.length];
//										
//									}else if ((!(tmpw==null)) && (temp1==null)){
//										temp2=new String[tmpw.length];
//										
//									}else{
//										
//										temp2=new String[tmpw.length+temp1.length];
//										
//									}	
//								 
//								temp2=union(tmpw,temp1);
//								
//								tmpw=null;
//								
//								if(!(temp2==null)){
//									tmpw=new String[temp2.length];
//									for (int k=0;k<temp2.length;k++){
//										if(!(temp2[k]==null)){
//											tmpw[k]=temp2[k];
//										}
//										
//									}
//								}
//								
//								if(viewName.compareToIgnoreCase("false")!=0){
//									for (int k=0;k<allPlacesNo;k++){
//										if (PDS[k].placeID.compareToIgnoreCase(place)==0){
//											
//											if(PDS[k].wds_view==null){
//												PDS[k].wds_view=new String[allPlacesNo];
//												PDS[k].wds_view[0]=viewName;
//												
//											}else{
//												int lastIndex=0;
//												for(int p=0;(p<PDS[k].wds_view.length) && (!(PDS[k].wds_view[p]==null));p++){
//													lastIndex++;
//												}
//												PDS[k].wds_view[lastIndex]=viewName;
//											}
//											
//											
//										}
//									}
//								}
//								
//								
//								
//								
//								
//							}
//						}
//						
//						 String splitType=getTaskSplitType(originalWorkflowFileFullPath, place);
//
//							
//						 if (splitType==""){
//							 placeSplitType="false";
//						 }else{
//							 placeSplitType=splitType; 
//							
//						 }
//						
//						if(placeSplitType.compareToIgnoreCase("AND")==0){
//							for (int j=0;j<allPlacesNo;j++){
//								if (PDS[j].placeID.compareToIgnoreCase(successorPlaceID)==0){
//
//									
//									String[] temp1=null;
//									
//									if((PDS[j].sds==null) && (result==null) ){
//										temp1=null;
//									}else if ((PDS[j].sds==null) && (!(result==null)) ){
//										temp1=new String[result.length];
//										
//									}else if ((!(PDS[j].sds==null)) && (result==null)){
//										temp1=new String[PDS[j].sds.length];
//										
//									}else{
//										
//										temp1=new String[PDS[j].sds.length+result.length];
//										
//									}	
//									
//									temp1=union(PDS[j].sds,result);
//									
//									String[] temp2=null;
//									
//									if((tmps==null) && (temp1==null) ){
//										temp2=null;
//									}else if ((tmps==null) && (!(temp1==null)) ){
//										temp2=new String[temp1.length];
//										
//									}else if ((!(tmps==null)) && (temp1==null)){
//										temp2=new String[tmps.length];
//										
//									}else{
//										
//										temp2=new String[tmps.length+temp1.length];
//										
//									}	
//									
//									 temp2=union(tmps,temp1);
//
//									
//									tmps=null;
//									if(!(temp2==null)){
//										tmps=new String[temp2.length];
//										for (int k=0;k<temp2.length;k++){
//											if(!(temp2[k]==null)){
//												tmps[k]=temp2[k];
//											}
//											
//										}	
//									}
//									
//									
//									if(viewName.compareToIgnoreCase("false")!=0){
//										for (int k=0;k<allPlacesNo;k++){
//											if (PDS[k].placeID.compareToIgnoreCase(place)==0){
//												
//												if(PDS[k].sds_view==null){
//													PDS[k].sds_view=new String[allPlacesNo];
//													PDS[k].sds_view[0]=viewName;
//													
//												}else{
//													int lastIndex=0;
//													for(int p=0;(p<PDS[k].sds_view.length) && (!(PDS[k].sds_view[p]==null));p++){
//														lastIndex++;
//													}
//													PDS[k].sds_view[lastIndex]=viewName;
//												}
//												
//												
//											}
//										}
//									}
//									
//									
//								}
//							}
//							
//							
//						}else{
//							for (int j=0;j<allPlacesNo;j++){
//
//								if (PDS[j].placeID.compareToIgnoreCase(successorPlaceID)==0){
//									
//									String[] temp1=null;
//									
//									if((PDS[j].sds==null) && (result==null) ){
//										temp1=null;
//									}else if ((PDS[j].sds==null) && (!(result==null)) ){
//										temp1=new String[result.length];
//										
//									}else if ((!(PDS[j].sds==null)) && (result==null)){
//										temp1=new String[PDS[j].sds.length];
//										
//									}else{
//										
//										temp1=new String[PDS[j].sds.length+result.length];
//										
//									}	
//									
//									
//									temp1=union(PDS[j].sds,result);
//									
//									String[] temp2=null;
//									
//									if((tmps==null) && (temp1==null) ){
//										temp2=null;
//									}else if ((tmps==null) && (!(temp1==null)) ){
//										temp2=new String[temp1.length];
//										
//									}else if ((!(tmps==null)) && (temp1==null)){
//										temp2=new String[tmps.length];
//										
//									}else{
//										
//										temp2=new String[tmps.length+temp1.length];
//										
//									}	
//									
//									 temp2=intersection(tmps,temp1);
//
//									 tmps=null;
//									if(!(temp2==null)){
//										tmps=new String[temp2.length];
//										for (int k=0;k<temp2.length;k++){
//											if(!(temp2[k]==null)){
//												tmps[k]=temp2[k];
//											}
//											
//										}
//									}
//									
//									if(viewName.compareToIgnoreCase("false")!=0){
//										for (int k=0;k<allPlacesNo;k++){
//											if (PDS[k].placeID.compareToIgnoreCase(place)==0){
//												
//												if(PDS[k].sds_view==null){
//													PDS[k].sds_view=new String[allPlacesNo];
//													PDS[k].sds_view[0]=viewName;
//													
//												}else{
//													int lastIndex=0;
//													for(int p=0;(p<PDS[k].sds_view.length) && (!(PDS[k].sds_view[p]==null));p++){
//														lastIndex++;
//													}
//													PDS[k].sds_view[lastIndex]=viewName;
//												}
//												
//												
//											}
//										}
//									}
//									
//								}
//							}
//							
//						}
//							
//						}
//					
//				}
//				
//				
//				
//				
//				String predecessors=getPredecessors(originalWorkflowFileFullPath, place);
//				
//				
//				if ((predecessors!=null) && ((predecessors!=""))){
//			
//					String[] predecessorsList=predecessors.split("\\,");
//					for (int i=0;i<predecessorsList.length;i++){
//						stack.add(predecessorsList[i]);
//		    		//	System.out.println("stopName="+stop+"/operation=push"+"/place="+predecessorsList[i]);
//						
//					}
//				}
//				
//				
//				
//			
//				
//				
//				for (int j=0;j<allPlacesNo;j++){
//					if (PDS[j].placeID.compareToIgnoreCase(place)==0){
//						
//						System.out.println("place:"+place+"/stop:"+stop);
//						if(tmpw==null){
//							System.out.println("tmpw is null");
//						}else{
//							System.out.print("tmpw:");
//							for (int i=0;(i<tmpw.length) &&(!(tmpw[i]==null)) ;i++){
//								System.out.print(tmpw[i]+",");
//							}
//						}
//						
//						System.out.println();
//						
//						if(PDS[j].wds==null){
//							System.out.println("PDS[j].wds is null");
//						}else{
//							System.out.print("PDS[j].wds:");
//
//							for (int i=0;(i<PDS[j].wds.length) &&(!(PDS[j].wds[i]==null)) ;i++){
//								System.out.print(PDS[j].wds[i]+",");
//							}
//						}
//						System.out.println();
//					
//						
//						if(tmps==null){
//							System.out.println("tmps is null");
//						}else{
//							System.out.print("tmps:");
//							for (int i=0;(i<tmps.length) && (!(tmps[i]==null));i++){
//								System.out.print(tmps[i]+",");
//							}
//						}
//						System.out.println();
//						
//
//						if(PDS[j].sds==null){
//							System.out.println("PDS[j].sds is null");
//						}else{
//							System.out.print("PDS[j].sds:");
//							for (int i=0;(i<PDS[j].sds.length) && (!(PDS[j].sds[i]==null));i++){
//								System.out.print(PDS[j].sds[i]+",");
//							}
//						}
//						System.out.println();
//						
//						String cond1=subSet(PDS[j].wds, tmpw);
//						String cond2=subSet(PDS[j].sds, tmps);
//						System.out.println(place+":"+cond1+"/"+cond2);
//						
//						System.out.println("------------------------------------------");
//						
//						if((cond1.compareToIgnoreCase("true")==0) || (cond2.compareToIgnoreCase("true")==0)){
//							PDS[j].wds=null;
//							if(!(tmpw==null)){
//								PDS[j].wds=new String[tmpw.length]; 
//								for(int i=0;i<tmpw.length;i++){
//									if(!(tmpw[i]==null)){
//										PDS[j].wds[i]=tmpw[i];
//									}
//									
//								}
//							}
//							PDS[j].sds=null;
//							if(!(tmps==null)){
//								PDS[j].sds=new String[tmps.length]; 
//								for(int i=0;i<tmps.length;i++){
//									if(!(tmps[i]==null)){
//										PDS[j].sds[i]=tmps[i];	
//									}
//									
//								}
//							}
//						}else{
//							
//							System.out.println("ssssss2:"+PDS[j].placeName);
//
//							PDS[j].fixed=true;
//						}
//					}
//				}
//				
//
//
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
//		
//		
//		public static String[] intersection(String[] set1, String[] set2 ){
//			String[] retVal=null;
//			int index=-1;
//			try {
//				
//				if ((set1==null) || (set2==null)){
//					retVal=null;
//					return retVal;
//				}else{
//						int retValIndex=0;
//						if (set1.length>=set2.length){
//							retValIndex=set1.length;
//						}else{
//							retValIndex=set2.length;
//						}
//						
//						retVal=new String[retValIndex]; 
//						
//						for(int i=0;(i<set1.length) && (!(set1[i]==null));i++){
//							for (int j=0;(j<set2.length) && (!(set2[j]==null));j++){
//								if (set1[i].compareTo(set2[j])==0){
//									index++;
//									retVal[index]=set1[i];
//								}
//							}
//						}
//					
//					
//					
//					
//				}
//				
//				
//			} catch (Exception e) {
//				retVal=null;
//			}
//
//			return retVal;
//			
//		}
//		
//		
//		
//		public static String[] union(String[] set1, String[] set2){
//
//			String[] retVal=null;
//			int index=-1;
//			
//			try {
//				
//				if((set1==null) && (set2==null) ){
//					retVal=null;
//				}else if ((set1==null) && (!(set2==null)) ){
//					retVal=new String[set2.length];
//					for(int i=0;i<set2.length;i++){
//						if(!(set2[i]==null)){
//							index++;
//							retVal[index]=set2[i];
//						}
//					}
//				}else if ((!(set1==null)) && (set2==null)){
//					retVal=new String[set1.length];
//					for(int i=0;i<set1.length;i++){
//						if(!(set1[i]==null)){
//							index++;
//							retVal[index]=set1[i];
//						}
//						
//					}
//				}else{
//					
//					retVal=new String[set1.length+set2.length];
//					
//					for(int i=0;i<set1.length;i++){
//						if(!(set1[i]==null)){
//							index++;
//							retVal[index]=set1[i];
//							
//						}
//						
//						
//					}
//					
//					
//					Boolean exist=false;
//					for(int i=0;(i<set2.length) && (!(set2[i]==null));i++){
//							exist=false;
//							for(int j=0;(j<retVal.length) && (!(retVal[j]==null));j++){
//									if (set2[i].compareTo(retVal[j])==0){
//										exist=true;
//										break;
//									}
//							}
//						if (!exist){
//							index++;
//							retVal[index]=set2[i];
//							exist=false;
//						}
//						
//					}
//					
//					
//				}
//				
//				
//				
//				
//				
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				retVal=null;
//			}
//
//			return retVal;
//			
//		}
//		
//		
//		public static String subSet(String[] set1, String[] set2){
//
//			String retVal="error";
//			int index=-1;
//			
//			try {
//				
//			
//				
//				if ((set1==null) && (set2==null)){
//					retVal="false";
//				}else if ((set1==null) && (!(set2==null))){
//					retVal="true";
//				}else if ((!(set1==null)) && (set2==null)){
//					retVal="false";
//				}else{
//					
//					int set1Length=0;
//					for (int i=0;(i<set1.length) && (!(set1[i]==null));i++){
//						set1Length++;
//					}
//					
//					int set2Length=0;
//					
//					for (int i=0;(i<set2.length) && (!(set2[i]==null));i++){
//						set2Length++;
//					}
//					
//					if(set1Length>=set2Length){
//						retVal="false";
//					}else{
//						
//						Boolean[] existStatus=new Boolean[set1Length];
//						
//						for (int i=0;i<set1Length;i++){
//							existStatus[i]=new Boolean(false);
//						}
//						
//						
//						for(int i=0;(i<set1Length) && (!(set1[i]==null));i++){
//							
//							for( int j=0;(j<set2Length) && (!(set2[j]==null));j++){
//								if (set1[i].compareTo(set2[j])==0){
//									index++;
//									existStatus[index]=true;
//								}
//							}
//							
//							
//						}
//						
//						for(int i=0;i<set1Length;i++){
//							if (!existStatus[i]){
//								retVal="false";
//								return retVal;
//							}
//						}
//						
						
//					 retVal="true";
//					 return retVal;
//
//					}
//					
//					
//										
//				}
//				
//				
//				
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				retVal="error";
//			}
//
//			return retVal;
//		}
		

		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		public static String setDependencySet(String workflowPath, String viewFilePath, String featureModelFilePath, String featureModel,String workflow){
			
			String retVal="false";
			
			try {
				
				//====================================================  Get Views' Features =========================================	
				
				HashMap<String,HashMap> viewMaps=new HashMap<String, HashMap>();

				String views=getViews(viewFilePath, workflow);
				String[] viewList=views.split("\\,");
				
				for (int i=0;i<viewList.length;i++){
					String viewName=viewList[i];
					
					ArrayList viewFeatures=new ArrayList(Arrays.asList(getViewCoveredFeatures(featureModelFilePath, viewFilePath, viewName).split("\\,")));
					HashMap<String,String> featureResultresult=new HashMap<String, String>();

					
					int index=0;
					while(index<viewFeatures.size()){

						featureResultresult.put((String)viewFeatures.get(index), (String)viewFeatures.get(index));
						index++;
					}
					viewMaps.put(viewName, featureResultresult);
				}
				//====================================================  Get Views' Features =========================================	

				
				
				//====================================================  Preprocessing ================================================
				
				String allPlaces=Methods.getWorkflowElements(workflowPath);  
				String[] places=allPlaces.split("\\?");
	    		int allPlacesNo=places.length;

	    		
	    		HashMap<String, PlaceDependencySet> PDS=new HashMap<String,PlaceDependencySet>(allPlacesNo);
	    		for(int j=0; j<allPlacesNo;j++){
	    			String placeName=places[j].split("\\,")[0];
	    			String placeID=places[j].split("\\,")[1];
	    			String placeType=places[j].split("\\,")[2];
	    			
	    			
	    			PlaceDependencySet PDSInitilaizer=new PlaceDependencySet();
	    			PDSInitilaizer.placeID=placeID;
	    			PDSInitilaizer.placeName=placeName;
	    			PDSInitilaizer.placeType=placeType;
	    			
	    			if(placeType.compareToIgnoreCase("task")==0){
	    				
	    				String SplitType=Methods.getTaskSplitType(workflowPath, placeID);
	    				if((SplitType=="") || (SplitType==null) ){
	    					SplitType="false";
	    				}
	    				
	    				PDSInitilaizer.Split=SplitType;
	    			}else{
	    				PDSInitilaizer.Split="false";
	    			}
	    			
	    			
	    			PDS.put(placeID,PDSInitilaizer);
	    		}

	    		
	    		
	    		HashMap<String,String> workflowElements=new HashMap<String, String>();
	    		for(int j=0; j<allPlacesNo;j++){
	    			String placeName=places[j].split("\\,")[0];
	    			String placeID=places[j].split("\\,")[1];
	    			
	    			workflowElements.put(placeName,placeID);
	    		}
	    		
	    		
	    		
	    		
	    		
				
	    		HashMap<String, String> predecessors=new HashMap<String, String>();
	    		HashMap<String, String> successors=new HashMap<String, String>();

	    		
	    		for(int j=0; j<allPlacesNo;j++){
	    			String placeID=places[j].split("\\,")[1];
	    			String placePredecessor=Methods.getPredecessors(workflowPath, placeID);
	    			String placeSuccessors=Methods.getSuccessors(workflowPath, placeID);
	    			
	    			predecessors.put(placeID, placePredecessor);
	    			successors.put(placeID, placeSuccessors);
	    			
	    		}
	    		
	    		
	    		
	    		String workflowName=Methods.getWorkflowName(workflowPath);
				String stop=Methods.getStops(workflowPath, viewFilePath);
				String[] stopList=stop.split("\\,");
				
				HashMap <String,String> stopsAndTasks= new HashMap<String, String>();
				
				for (int j=0;j<stopList.length;j++){
					String tasks=Methods.getStartsOfStop(viewFilePath, stopList[j], workflowName);
					stopsAndTasks.put(stopList[j], tasks);
				}
				
				HashMap<String,String> elementView=new HashMap<String, String>();
	    		for(int j=0; j<allPlacesNo;j++){
	    			String placeName=places[j].split("\\,")[0];
		    		String viewName=Methods.getTaskViewNameByDir(viewFilePath, workflowName, placeName);
	    			elementView.put(placeName,viewName);
	    		}
				

	    		
	    		String featureModelFeatures=Methods.getFeatureModelFeatures(featureModelFilePath);
	    		HashMap<String,String> featuresMap=new HashMap<String,String>();

	    		if(featureModelFeatures!=""){
	    			ArrayList features=new ArrayList(Arrays.asList(featureModelFeatures.split("\\,")));
	    			int index=0;
	    			
	    			while(index<features.size()){
	    			
	    				featuresMap.put((String)features.get(index),(String)features.get(index));
	    				index++;
	    			}
	    		}else{
	    			featuresMap.clear();
	    		}
	    		
	    		
	    		
				
				
				//====================================================  Preprocessing ================================================	

				retVal=CDEP(featuresMap,viewMaps,PDS,allPlacesNo,stopList,stopsAndTasks,predecessors,successors,workflowElements,elementView,viewFilePath,workflowName);

	    		
				
			} catch (Exception e) {
				retVal="false";
			}
			
			return retVal;
			
		}
		
		
		
		
		
		
		
		public static String CDEP(HashMap<String,String> allFeaturesMap, HashMap<String,HashMap> viewMap, HashMap<String, PlaceDependencySet> PDS, int allPlacesNo,String[] stopList, HashMap <String,String> stopsAndTasks,HashMap<String, String> predecessors,HashMap<String, String> successors,HashMap<String,String> workflowElements,HashMap<String,String> elementView, String viewPath,String workflowName) throws IOException{
			
			
			 String retVal="false"; 		
			 long executionTime = 0;
			try {
				
				HashMap<String, ArrayList> SD =new HashMap<String, ArrayList>();
				HashMap<String, ArrayList> WD =new HashMap<String, ArrayList>();

				SD.clear();
				WD.clear();
				 for (int i=0;i<stopList.length;i++){
		    		WDFS(stopList[i],PDS,allPlacesNo,allFeaturesMap,viewMap,predecessors,successors,workflowElements,elementView);
		    		String[] tasks=stopsAndTasks.get(stopList[i]).split("\\,");
		    		for (int j=0;j<tasks.length;j++){
		    				SD.put(tasks[j], new ArrayList(PDS.get(workflowElements.get(tasks[j])).sds.values()));
							WD.put(tasks[j], new ArrayList(PDS.get(workflowElements.get(tasks[j])).wds.values()));
					}
					
				}
				
				for(String s:SD.keySet()){
					
					String WDList="";
					String SDList="";
					ArrayList<String> sdValues=new ArrayList<String>();
					sdValues=SD.get(s);
					int index1=0;
					while(index1<sdValues.size()){
						if(SDList==""){
							SDList=sdValues.get(index1)+",";
						}else{
							SDList=SDList+sdValues.get(index1)+",";
						}
						index1++;
					}
					
					ArrayList<String> wdValues=new ArrayList<String>();
					wdValues=WD.get(s);
					int index2=0;
					while(index2<wdValues.size()){
						if(WDList==""){
							WDList=wdValues.get(index2)+",";
						}else{
							WDList=WDList+wdValues.get(index2)+",";
						}
						index2++;
					}
					
					
  					File viewFile = new File(viewPath);
   					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
   					Document doc = builder.parse(viewFile);
    					
   					NodeList viewNodes = doc.getElementsByTagName("view");
   					
   					for(int i=0;i<viewNodes.getLength();i++){
   						
   						Element viewElement=(Element)viewNodes.item(i);
   						
   						NodeList taskNodes=viewElement.getElementsByTagName("task");
   						Element  taskElement=(Element)taskNodes.item(0);
   						
   						NodeList workflowNode=taskElement.getElementsByTagName("workflow");
   						Element  workflowElement=(Element)workflowNode.item(0);
   						
   						if(workflowElement.getAttribute("name").compareToIgnoreCase(workflowName)==0){
   							
   							NodeList taskNameNodes=workflowElement.getElementsByTagName("task_name");
   							Element  taskNameElement=(Element)taskNameNodes.item(0);
   							
   							String   taskName=getCharacterDataFromElement(taskNameElement);
   							if(taskName.split("\\?")[0].compareToIgnoreCase(s)==0){
   								
   								NodeList  SDNode=viewElement.getElementsByTagName("sd");
   								if(SDNode.getLength()==0){
   									
   									Element SDElement=doc.createElement("sd");
   									SDElement.appendChild(doc.createTextNode(SDList));
   									viewElement.appendChild(SDElement);
   								}else{
   									Element SDElement=(Element)SDNode.item(0);
   									viewElement.removeChild(SDElement);
   									
   									Element newSDElement=doc.createElement("sd");
   									newSDElement.appendChild(doc.createTextNode(SDList));
   									viewElement.appendChild(newSDElement);
   									
   								}
   								
   								
   								NodeList  WDNode=viewElement.getElementsByTagName("wd");
   								if(WDNode.getLength()==0){
   									
   									Element WDElement=doc.createElement("wd");
   									WDElement.appendChild(doc.createTextNode(WDList));
   									viewElement.appendChild(WDElement);
   								}else{
   									Element WDElement=(Element)WDNode.item(0);
   									viewElement.removeChild(WDElement);
   									
   									Element newWDElement=doc.createElement("wd");
   									newWDElement.appendChild(doc.createTextNode(WDList));
   									viewElement.appendChild(newWDElement);
   									
   								}

   								
   								
   							}
   							
   						}
   						
   					}

					
					
   					TransformerFactory transformerFactory=TransformerFactory.newInstance();
				    Transformer transformer=transformerFactory.newTransformer();
				    DOMSource source=new DOMSource(doc);
				    StreamResult result=new StreamResult(viewFile);
				    transformer.transform(source, result);
					
					
			
				}
				
				retVal="true"; 

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			return retVal;

		}
		
		
		
		
		public static void WDFS (String stop, HashMap<String,PlaceDependencySet> PDS, int allPlacesNo,HashMap<String,String> allFeaturesMap, HashMap<String,HashMap> viewMap,HashMap<String, String> predecessors,HashMap<String, String> successors,HashMap<String,String> workflowElements,HashMap<String,String> elementView ){

		
			try {
				
				
				Queue<String> queue = new LinkedList();

				String stopID=workflowElements.get(stop);
				String stopPredecessor=predecessors.get(stopID);
				String[] predecessorsList=stopPredecessor.split("\\,");
	    		for(int i=0; i<predecessorsList.length;i++){
	    			queue.add(predecessorsList[i]);
	    		}
	    		
	    		
	    	
	    	
	    		
	    		for(String s:PDS.keySet()){
	    			
	    			if(s.compareToIgnoreCase(stopID)==0){
	    				PDS.get(s).fixed=true;
	    			}else{
	    				PDS.get(s).fixed=false;
	    			}
	    			
	    			PDS.get(s).sds.clear();
	    			PDS.get(s).wds.clear();
	    			PDS.get(s).wds_view.clear();
	    			PDS.get(s).sds_view.clear();
	    			
	    		}
	    		
	    		while(!queue.isEmpty()){
	    				String place=(String) queue.remove();
	    				if(!(PDS.get(place).fixed)){
							SEARCH(place,PDS.get(place).placeType,PDS,queue,allPlacesNo,stop,allFeaturesMap,viewMap,predecessors,successors,elementView);
	    				}
	    		}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		
		public static void SEARCH(String place, String placeType, HashMap<String,PlaceDependencySet> PDS, Queue<String> queue, int allPlacesNo, String stop,HashMap<String,String> allFeaturesMap, HashMap<String,HashMap> viewMap,HashMap<String, String> predecessors,HashMap<String, String> successors,HashMap<String,String> elementView) throws IOException{
			
			
			String tmpsValue="";
			String tmpwValue="";
			String sdsValue="";
			String wdsValue="";
			String startValue="";
			String tempValue="";




			try {
				
				HashMap<String,String> tmps=new HashMap<String,String>();
				HashMap<String,String> tmpw=new HashMap<String,String>();
				
				
			
				String placeSplitType="false";
				if(placeType.compareToIgnoreCase("task")==0){
					placeSplitType=PDS.get(place).Split;
				}

				
				if ((placeType.compareToIgnoreCase("task")==0) &&(placeSplitType.compareToIgnoreCase("AND")==0)){
					tmps.clear();
					
				}else{
					tmps.putAll(allFeaturesMap);
				}
				tmpw.clear();
				
				String[] successorList=successors.get(place).split("\\,");
				
				
				for (int i=0;i<successorList.length;i++){
					String successorPlaceID=successorList[i];
					String successorPlaceName=PDS.get(successorPlaceID).placeName;
					String successorPlaceType=PDS.get(successorPlaceID).placeType;
					
					
					if((successorPlaceType.compareToIgnoreCase("condition")==0) || (successorPlaceType.compareToIgnoreCase("inputCondition")==0) || (successorPlaceType.compareToIgnoreCase("outputCondition")==0)){
						
						HashMap<String,String> temp1=new HashMap<String,String>();
						
						temp1=union(tmpw,PDS.get(successorPlaceID).wds);
						tmpw.clear();
						tmpw.putAll(temp1);

						
						HashMap<String,String> temp2=new HashMap<String, String>();
						
						
						temp2=intersection(tmps, PDS.get(successorPlaceID).sds);
						tmps.clear();
						tmps.putAll(temp2);
						

						
					}else{
						
						String viewName=elementView.get(successorPlaceName);


						HashMap<String,String> result=new HashMap<String, String>();
						if(viewName.compareToIgnoreCase("false")==0){
							result.clear();
						}else{
							
							for(String s:viewMap.keySet()){
								if(s.compareToIgnoreCase(viewName)==0){
									result.putAll(viewMap.get(s));
								}
							}
						}
						


						
						HashMap<String,String> temp1=new HashMap<String, String>();
						temp1=union(PDS.get(successorPlaceID).wds,result);

						HashMap<String,String> temp2=new HashMap<String, String>();
						temp2=union(tmpw,temp1);
						tmpw.clear();
						tmpw.putAll(temp2);
						
						
						if(viewName.compareToIgnoreCase("false")!=0){
							PDS.get(place).wds_view.add(viewName);
						}
						
						 
							if(placeSplitType.compareToIgnoreCase("AND")==0){


								
								temp1.clear();
								temp1=union(PDS.get(successorPlaceID).sds,result);

								temp2.clear();
								temp2=union(tmps,temp1);
								tmps.clear();
								tmps.putAll(temp2);

								if(viewName.compareToIgnoreCase("false")!=0){
									
									PDS.get(place).sds_view.add(viewName);
								}
								
								
							}else{
								temp1.clear();
								temp1=union(PDS.get(successorPlaceID).sds,result);
								
								
								temp2.clear();
								temp2=intersection(tmps,temp1);
								tmps.clear();
								tmps.putAll(temp2);
								
								if(viewName.compareToIgnoreCase("false")!=0){
									
									PDS.get(place).sds_view.add(viewName);
								}
							}
					}
				}
				
				
				String predecessorValues="";
				if ((predecessors.get(place)!=null) && ((predecessors.get(place)!=""))){
					String[] predecessorsList=predecessors.get(place).split("\\,");
					for (int i=0;i<predecessorsList.length;i++){
						queue.add(predecessorsList[i]);
						if(predecessorValues==""){
							predecessorValues=predecessorsList[i];
						}else{
							predecessorValues=predecessorValues+predecessorsList[i];
						}
					}
				}
				


				
				String cond2=equal(PDS.get(place).sds,tmps);


				String cond1=equal(PDS.get(place).wds,tmpw);
				
				if((cond1.compareToIgnoreCase("true")==0) && (cond2.compareToIgnoreCase("true")==0)){
					
					PDS.get(place).fixed=true;

				}else{
					PDS.get(place).wds.clear();
					PDS.get(place).sds.clear();
					PDS.get(place).wds.putAll(tmpw);
					PDS.get(place).sds.putAll(tmps);

				}
				
				

				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
		public static String CDEP(String traceFile,String timeDetailFile, String DependencySetFilePath,HashMap<String,String> allFeaturesMap, HashMap<String,HashMap> viewMap,String workflowFileName, HashMap<String, PlaceDependencySet> PDS, int allPlacesNo,String[] stopList, HashMap <String,String> stopsAndTasks,HashMap<String, String> predecessors,HashMap<String, String> successors,HashMap<String,String> workflowElements,HashMap<String,String> elementView) throws IOException{
			
			
			 String retVal=""; 		
			 long executionTime = 0;
			try {
				
				HashMap<String, ArrayList> SD =new HashMap<String, ArrayList>();
				HashMap<String, ArrayList> WD =new HashMap<String, ArrayList>();
//
//				
				 Writer output=null;
					
				 File file=new File(DependencySetFilePath);
				 output=new BufferedWriter(new FileWriter(file,true));
				
				 String text="-----------------------------------------------------------------------------------------------------------------\n";
				 text=text+"WORKFLOW NAME:"+workflowFileName+"\n";
				 text=text+"===================================================================================================================\n";
				 output.write(text);
				 output.close();
				
				 
				
				

				long allWDFSTime=0;
				 
				long startCDEPTime=0;	
				long endCDEPTime = 0;	

			for(int t=0;t<=2;t++){
				 startCDEPTime = System.currentTimeMillis();
				SD.clear();
				WD.clear();
				 for (int i=0;i<stopList.length;i++){
					//long startWDFSTime = System.currentTimeMillis();
		    		executionTime=WDFS(traceFile,timeDetailFile,stopList[i],PDS,allPlacesNo,allFeaturesMap,viewMap,predecessors,successors,workflowElements,elementView);
		    		//long endWDFSTime = System.currentTimeMillis();
		    		//allWDFSTime=allWDFSTime+(endWDFSTime-startWDFSTime);
					
		    		String[] tasks=stopsAndTasks.get(stopList[i]).split("\\,");
//					
		    		for (int j=0;j<tasks.length;j++){
		    				
						
//							PlaceDependencySet PDSFinalizer=new PlaceDependencySet();
//							
//							String taskID=workflowElements.get(tasks[j]);  
//							
//							PDSFinalizer=PDS.get(taskID);
//						
//							HashMap<String,String> sds=new HashMap<String, String>();
//							sds=PDSFinalizer.sds;
//							
//							
//							HashMap<String,String> wds=new HashMap<String, String>();
//							wds=PDSFinalizer.wds;
							
							
		    				SD.put(tasks[j], new ArrayList(PDS.get(workflowElements.get(tasks[j])).sds.values()));
							//SD.put(tasks[j], new ArrayList(sds.values()));
							//WD.put(tasks[j], new ArrayList(wds.values()));
							WD.put(tasks[j], new ArrayList(PDS.get(workflowElements.get(tasks[j])).wds.values()));

							
							
//							String SDList="";
//							Collection sdsCollection=sds.values();
//							Iterator   sdsItr=sdsCollection.iterator();
//							while(sdsItr.hasNext()){
//								if(SDList==""){
//									SDList=(String)sdsItr.next();
//								}else{
//									SDList=SDList+","+(String)sdsItr.next();
//								}
//							}
//					
//							
//							String WDList="";
//							Collection wdsCollection=wds.values();
//							Iterator   wdsItr=wdsCollection.iterator();
//							
//							while(wdsItr.hasNext()){
//								if(WDList==""){
//									WDList=(String)wdsItr.next();
//								}else{
//									WDList=WDList+","+(String)wdsItr.next();
//								}
//							}
//
//							
//							
//							ArrayList<String> wds_view=new ArrayList<String>();
//							wds_view=PDSFinalizer.wds_view;
//							int index=0;
//							String wds_Views="";
//							while(index<wds_view.size()){
//								if(wds_Views==""){
//									wds_Views=wds_view.get(index);
//								}else{
//									wds_Views=wds_Views+wds_view.get(index);
//								}
//								index++;
//							}
//							
//							ArrayList<String> sds_view=new ArrayList<String>();
//							sds_view=PDSFinalizer.sds_view;
//							index=0;
//							String sds_Views="";
//							while(index<sds_view.size()){
//								if(sds_Views==""){
//									sds_Views=sds_view.get(index);
//								}else{
//									sds_Views=sds_Views+sds_view.get(index);
//								}
//								index++;
//							}
								
							
							
						
					}
					
				}
				
					 endCDEPTime = System.currentTimeMillis();
					
					if(retVal==""){
						retVal=String.valueOf(endCDEPTime-startCDEPTime);
					}else{
						retVal=retVal+","+String.valueOf(endCDEPTime-startCDEPTime);
					}	
					

			}
				
				
				
//				long endCDEPTime = System.currentTimeMillis();
//				long elapsedCDEP =endCDEPTime - startCDEPTime; 
//				
//				retVal=elapsedCDEP+","+allWDFSTime+","+executionTime; 
				
				
				
				 Writer outputTime=null;
					
				 File fileTime=new File(timeDetailFile);
				 outputTime=new BufferedWriter(new FileWriter(fileTime,true));
				
				 String textTime="Function Name: CDEP, Time:"+String.valueOf(endCDEPTime-startCDEPTime)+"\n";
				 outputTime.write(textTime);
				 outputTime.close();
//				
//				
//				
//				
//				
//
//
				for(String s:SD.keySet()){
					
					String WDList="";
					String SDList="";
					ArrayList<String> sdValues=new ArrayList<String>();
					sdValues=SD.get(s);
					int index1=0;
					while(index1<sdValues.size()){
						if(SDList==""){
							SDList=sdValues.get(index1)+",";
						}else{
							SDList=SDList+sdValues.get(index1)+",";
						}
						index1++;
					}
					
					ArrayList<String> wdValues=new ArrayList<String>();
					wdValues=WD.get(s);
					int index2=0;
					while(index2<wdValues.size()){
						if(WDList==""){
							WDList=wdValues.get(index2)+",";
						}else{
							WDList=WDList+wdValues.get(index2)+",";
						}
						index2++;
					}
					
					
					
					
						 output=null;
						
						 output=new BufferedWriter(new FileWriter(file,true));
						
						
						 
						 text="TASK NAME:"+s+"\n";
						 text=text+"WD LIST:"+WDList+"\n";
						 //text=text+"WD VIEWS:"+wds_Views+"\n";

						 text=text+"SD LIST:"+SDList+"\n";
						 //text=text+"SD VIEWS:"+sds_Views+"\n";

						 text=text+"===================================================================================================================\n";

						 output.write(text);
						 output.close();
					
				}
				

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			return retVal;
			
		}
		
		
		
		public static long WDFS (String traceFile,String timeDetailFile,String stop, HashMap<String,PlaceDependencySet> PDS, int allPlacesNo,HashMap<String,String> allFeaturesMap, HashMap<String,HashMap> viewMap,HashMap<String, String> predecessors,HashMap<String, String> successors,HashMap<String,String> workflowElements,HashMap<String,String> elementView ){
			
			long retVal=0;
			try {
				
				
				Queue<String> queue = new LinkedList();

				String stopID=workflowElements.get(stop);
				String stopPredecessor=predecessors.get(stopID);
				String[] predecessorsList=stopPredecessor.split("\\,");
				
				long startTime = System.currentTimeMillis();
				
	    		for(int i=0; i<predecessorsList.length;i++){
	    			queue.add(predecessorsList[i]);
	    		}
	    		
	    		
	    	
	    	
	    		
	    		for(String s:PDS.keySet()){
	    			
	    			if(s.compareToIgnoreCase(stopID)==0){
	    				PDS.get(s).fixed=true;
	    			}else{
	    				PDS.get(s).fixed=false;
	    			}
	    			
	    			PDS.get(s).sds.clear();
	    			PDS.get(s).wds.clear();
	    			PDS.get(s).wds_view.clear();
	    			PDS.get(s).sds_view.clear();
	    			
	    		}
	    		
	    		while(!queue.isEmpty()){
	    				String place=(String) queue.remove();
	    				
	    				
	    			
	    				if(!(PDS.get(place).fixed)){
							SEARCH(traceFile,timeDetailFile,place,PDS.get(place).placeType,PDS,queue,allPlacesNo,stop,allFeaturesMap,viewMap,predecessors,successors,elementView);

	    				}
	    			
	    				
	    		}
	    		
	    		long endTime = System.currentTimeMillis();

	    		retVal=endTime-startTime;
	    		
	    		
	    		
	    		long EndTime = System.currentTimeMillis();


				 Writer output=null;
					
				 File file=new File(timeDetailFile);
				 output=new BufferedWriter(new FileWriter(file,true));
				
				 String text="Function Name: WDFS"+", Stop Name:"+stop+"Time:"+String.valueOf(EndTime-startTime)+"\n";  
				 output.write(text);
				 output.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return retVal;
		}
		
		
		public static String getFeaturesFromTextFile(String filePath){
			String retVal="";
			
			try {
				
				
				FileInputStream fstream=new FileInputStream(filePath);
				
				DataInputStream in=new DataInputStream(fstream);
				
				BufferedReader br=new BufferedReader(new InputStreamReader(in));
				
				String strLine;
				
			
				
				while ((strLine=br.readLine())!=null){
					
					String[]  strSegments =strLine.split("\\ "); 
					if (retVal==""){
						retVal=strSegments[2];
						
					}else{
						retVal=retVal+","+strSegments[2];
					}
				}
				
				in.close();
				
			} catch (Exception e) {
				retVal="";
			}
			return retVal;
		}
		
		public static void SEARCH(String traceFile, String timeDetailFile,String place, String placeType, HashMap<String,PlaceDependencySet> PDS, Queue<String> queue, int allPlacesNo, String stop,HashMap<String,String> allFeaturesMap, HashMap<String,HashMap> viewMap,HashMap<String, String> predecessors,HashMap<String, String> successors,HashMap<String,String> elementView) throws IOException{
			
			//====================================================================================================
//			String text="================================================================\n";
//			text=text+"Stop Name:"+stop+"\n";
//			text=text+"Place Name:"+place+"\n";
			//====================================================================================================

			long startTime = System.currentTimeMillis();
			
			String tmpsValue="";
			String tmpwValue="";
			String sdsValue="";
			String wdsValue="";
			String startValue="";
			String tempValue="";




			try {
				
				HashMap<String,String> tmps=new HashMap<String,String>();
				HashMap<String,String> tmpw=new HashMap<String,String>();
				
				
			
				String placeSplitType="false";
				if(placeType.compareToIgnoreCase("task")==0){
					placeSplitType=PDS.get(place).Split;
				}

				//====================================================================================================
//				text=text+"Place Split Type:"+placeSplitType+"\n";
				//====================================================================================================
				
				if ((placeType.compareToIgnoreCase("task")==0) &&(placeSplitType.compareToIgnoreCase("AND")==0)){
					tmps.clear();
					
				}else{
					tmps.putAll(allFeaturesMap);
				}
				tmpw.clear();
				
//				//====================================================================================================
//				text=text+"tmps initial value:";
//				tmpsValue="";
//				for(String s:tmps.keySet()){
//					if(tmpsValue==""){
//						tmpsValue=tmps.get(s)+",";	 
//					}else{
//						tmpsValue=tmpsValue+tmps.get(s)+",";
//					}
//				}
//				text=text+tmpsValue+"\n";
//				text=text+"----------------------------------------------------------------------\n";
//
//				text=text+"tmpw initial value:";
//				tmpwValue="";
//				for(String s:tmpw.keySet()){
//					if(tmpwValue==""){
//						tmpwValue=tmpw.get(s)+",";	 
//					}else{
//						tmpwValue=tmpwValue+tmpw.get(s)+",";
//					}
//				}
//				text=text+tmpwValue+"\n";
//				text=text+"----------------------------------------------------------------------\n";
				//====================================================================================================

				
				
				String[] successorList=successors.get(place).split("\\,");
				
				//====================================================================================================
//				text=text+"Successor List:"+successors.get(place)+"\n";
				//====================================================================================================
				
				for (int i=0;i<successorList.length;i++){
					String successorPlaceID=successorList[i];
					String successorPlaceName=PDS.get(successorPlaceID).placeName;
					String successorPlaceType=PDS.get(successorPlaceID).placeType;
					
					//====================================================================================================
//					text=text+"Successor in loop line 8:"+successorPlaceName+"\n";
					//====================================================================================================
					
					if((successorPlaceType.compareToIgnoreCase("condition")==0) || (successorPlaceType.compareToIgnoreCase("inputCondition")==0) || (successorPlaceType.compareToIgnoreCase("outputCondition")==0)){
						
						HashMap<String,String> temp1=new HashMap<String,String>();
						
						//====================================================================================================
//						text=text+"tmpw old value in line 10:";
//						tmpwValue="";
//						for(String s:tmpw.keySet()){
//							if(tmpwValue==""){
//								tmpwValue=tmpw.get(s)+",";
//							}else{
//								tmpwValue=tmpwValue+tmpw.get(s)+",";
//							}
//						}
//						text=text+tmpwValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
//
//						
//						text=text+"wds value in line 10:";
//						wdsValue="";
//						for(String s:PDS.get(successorPlaceID).wds.keySet()){
//							if(wdsValue==""){
//								wdsValue=PDS.get(successorPlaceID).wds.get(s)+",";
//							}else{
//								wdsValue=wdsValue+PDS.get(successorPlaceID).wds.get(s)+",";
//							}
//						}
//						text=text+wdsValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
						//====================================================================================================

						
						temp1=union(tmpw,PDS.get(successorPlaceID).wds);
						tmpw.clear();
						tmpw.putAll(temp1);

						//====================================================================================================
//						text=text+"tmpw new value in line 10:";
//						tmpwValue="";
//						for(String s:tmpw.keySet()){
//							if(tmpwValue==""){
//								tmpwValue=tmpw.get(s)+",";
//							}else{
//								tmpwValue=tmpwValue+tmpw.get(s)+",";
//							}
//						}
//						text=text+tmpwValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
						//====================================================================================================

						
						HashMap<String,String> temp2=new HashMap<String, String>();
						
						
						//====================================================================================================
//						text=text+"tmps old value in line 11:";
//						tmpsValue="";
//						for(String s:tmps.keySet()){
//							if(tmpsValue==""){
//								tmpsValue=tmps.get(s)+",";	 
//							}else{
//								tmpsValue=tmpsValue+tmps.get(s)+",";
//							}
//						}
//						text=text+tmpsValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
//						
//						text=text+"sds value in line 11:";
//						sdsValue="";
//						for(String s:PDS.get(successorPlaceID).sds.keySet()){
//							if(sdsValue==""){
//								sdsValue=PDS.get(successorPlaceID).sds.get(s)+",";
//							}else{
//								sdsValue=sdsValue+PDS.get(successorPlaceID).sds.get(s)+",";
//							}
//						}
//						text=text+sdsValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
						//====================================================================================================

						temp2=intersection(tmps, PDS.get(successorPlaceID).sds);
						tmps.clear();
						tmps.putAll(temp2);
						
						//====================================================================================================
//						text=text+"tmps new value in line 11:";
//						tmpsValue="";
//						for(String s:tmps.keySet()){
//							if(tmpsValue==""){
//								tmpsValue=tmps.get(s)+",";	 
//							}else{
//								tmpsValue=tmpsValue+tmps.get(s)+",";
//							}
//						}
//						text=text+tmpsValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
						//====================================================================================================

						
					}else{
						
						String viewName=elementView.get(successorPlaceName);

						//====================================================================================================
//						text=text+"Associated view name:"+viewName+"\n";
//						text=text+"----------------------------------------------------------------------\n";
						//====================================================================================================
						

						HashMap<String,String> result=new HashMap<String, String>();
						if(viewName.compareToIgnoreCase("false")==0){
							result.clear();
						}else{
							
							for(String s:viewMap.keySet()){
								if(s.compareToIgnoreCase(viewName)==0){
									result.putAll(viewMap.get(s));
								}
							}
						}
						

						//====================================================================================================
//						text=text+"start value in line 13:";
//						startValue="";
//						for(String s:result.keySet()){
//							if(startValue==""){
//								startValue=result.get(s)+",";	 
//							}else{
//								startValue=startValue+result.get(s)+",";
//							}
//						}
//						text=text+startValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
//
//						text=text+"wds value in line 13:";
//						wdsValue="";
//						for(String s:PDS.get(successorPlaceID).wds.keySet()){
//							if(wdsValue==""){
//								wdsValue=PDS.get(successorPlaceID).wds.get(s)+",";
//							}else{
//								wdsValue=wdsValue+PDS.get(successorPlaceID).wds.get(s)+",";
//							}
//						}
//						text=text+wdsValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
//
//						
//						text=text+"tmpw old value in line 13:";
//						tmpwValue="";
//						for(String s:tmpw.keySet()){
//							if(tmpwValue==""){
//								tmpwValue=tmpw.get(s)+",";
//							}else{
//								tmpwValue=tmpwValue+tmpw.get(s)+",";
//							}
//						}
//						text=text+tmpwValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
						//====================================================================================================

						
						HashMap<String,String> temp1=new HashMap<String, String>();
						temp1=union(PDS.get(successorPlaceID).wds,result);

						//====================================================================================================
//						text=text+"(wds union start) value in line 13:";
//						tempValue="";
//						for(String s:temp1.keySet()){
//							if(tempValue==""){
//								tempValue=temp1.get(s)+",";
//							}else{
//								tempValue=tempValue+temp1.get(s)+",";
//							}
//						}
//						text=text+tempValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
						//====================================================================================================

						
						HashMap<String,String> temp2=new HashMap<String, String>();
						temp2=union(tmpw,temp1);
						tmpw.clear();
						tmpw.putAll(temp2);
						
						//====================================================================================================
//						text=text+"tmpw new value in line 13:";
//						tmpwValue="";
//						for(String s:tmpw.keySet()){
//							if(tmpwValue==""){
//								tmpwValue=tmpw.get(s)+",";
//							}else{
//								tmpwValue=tmpwValue+tmpw.get(s)+",";
//							}
//						}
//						text=text+tmpwValue+"\n";
//						text=text+"----------------------------------------------------------------------\n";
						//====================================================================================================
						
						
						if(viewName.compareToIgnoreCase("false")!=0){
							PDS.get(place).wds_view.add(viewName);
						}
						
						 
							if(placeSplitType.compareToIgnoreCase("AND")==0){

								//====================================================================================================
//								text=text+"start value in line 15:";
//								startValue="";
//								for(String s:result.keySet()){
//									if(startValue==""){
//										startValue=result.get(s)+",";	 
//									}else{
//										startValue=startValue+result.get(s)+",";
//									}
//								}
//								text=text+startValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
//
//								text=text+"sds value in line 15:";
//								sdsValue="";
//								for(String s:PDS.get(successorPlaceID).sds.keySet()){
//									if(sdsValue==""){
//										sdsValue=PDS.get(successorPlaceID).sds.get(s)+",";
//									}else{
//										sdsValue=sdsValue+PDS.get(successorPlaceID).sds.get(s)+",";
//									}
//								}
//								text=text+sdsValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
//
//								
//								text=text+"tmps old value in line 15:";
//								tmpsValue="";
//								for(String s:tmps.keySet()){
//									if(tmpsValue==""){
//										tmpsValue=tmps.get(s)+",";
//									}else{
//										tmpsValue=tmpsValue+tmps.get(s)+",";
//									}
//								}
//								text=text+tmpsValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
								//====================================================================================================

								
								temp1.clear();
								temp1=union(PDS.get(successorPlaceID).sds,result);
								//====================================================================================================
//								text=text+"(sds union start) value in line 15:";
//								tempValue="";
//								for(String s:temp1.keySet()){
//									if(tempValue==""){
//										tempValue=temp1.get(s)+",";
//									}else{
//										tempValue=tempValue+temp1.get(s)+",";
//									}
//								}
//								text=text+tempValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
								//====================================================================================================
								

								temp2.clear();
								temp2=union(tmps,temp1);
								tmps.clear();
								tmps.putAll(temp2);

								//====================================================================================================
//								text=text+"tmps new value in line 15:";
//								tmpsValue="";
//								for(String s:tmps.keySet()){
//									if(tmpsValue==""){
//										tmpsValue=tmps.get(s)+",";
//									}else{
//										tmpsValue=tmpsValue+tmps.get(s)+",";
//									}
//								}
//								text=text+tmpsValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
								//====================================================================================================


								if(viewName.compareToIgnoreCase("false")!=0){
									
									PDS.get(place).sds_view.add(viewName);
								}
								
								
							}else{
								
								
								//====================================================================================================
//								text=text+"start value in line 17:";
//								startValue="";
//								for(String s:result.keySet()){
//									if(startValue==""){
//										startValue=result.get(s)+",";	 
//									}else{
//										startValue=startValue+result.get(s)+",";
//									}
//								}
//								text=text+startValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
//
//								text=text+"sds value in line 17:";
//								sdsValue="";
//								for(String s:PDS.get(successorPlaceID).sds.keySet()){
//									if(sdsValue==""){
//										sdsValue=PDS.get(successorPlaceID).sds.get(s)+",";
//									}else{
//										sdsValue=sdsValue+PDS.get(successorPlaceID).sds.get(s)+",";
//									}
//								}
//								text=text+sdsValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
//
//								
//								text=text+"tmps old value in line 17:";
//								tmpsValue="";
//								for(String s:tmps.keySet()){
//									if(tmpsValue==""){
//										tmpsValue=tmps.get(s)+",";
//									}else{
//										tmpsValue=tmpsValue+tmps.get(s)+",";
//									}
//								}
//								text=text+tmpsValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
								//====================================================================================================


								
								temp1.clear();
								temp1=union(PDS.get(successorPlaceID).sds,result);
								
								
								//====================================================================================================
//								text=text+"(sds union start) value in line 17:";
//								tempValue="";
//								for(String s:temp1.keySet()){
//									if(tempValue==""){
//										tempValue=temp1.get(s)+",";
//									}else{
//										tempValue=tempValue+temp1.get(s)+",";
//									}
//								}
//								text=text+tempValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
								//====================================================================================================
	
								
								

								temp2.clear();
								temp2=intersection(tmps,temp1);
								tmps.clear();
								tmps.putAll(temp2);
								
								//====================================================================================================
//								text=text+"tmps new value in line 17:";
//								tmpsValue="";
//								for(String s:tmps.keySet()){
//									if(tmpsValue==""){
//										tmpsValue=tmps.get(s)+",";
//									}else{
//										tmpsValue=tmpsValue+tmps.get(s)+",";
//									}
//								}
//								text=text+tmpsValue+"\n";
//								text=text+"----------------------------------------------------------------------\n";
								//====================================================================================================
								if(viewName.compareToIgnoreCase("false")!=0){
									
									PDS.get(place).sds_view.add(viewName);
								}
							}
					}
				}
				
				
				String predecessorValues="";
				if ((predecessors.get(place)!=null) && ((predecessors.get(place)!=""))){
					String[] predecessorsList=predecessors.get(place).split("\\,");
					for (int i=0;i<predecessorsList.length;i++){
						queue.add(predecessorsList[i]);
						if(predecessorValues==""){
							predecessorValues=predecessorsList[i];
						}else{
							predecessorValues=predecessorValues+predecessorsList[i];
						}
					}
				}
				

				//====================================================================================================
//				text=text+"Pushed values in queue in  line 22:"+predecessorValues+"\n";
				//====================================================================================================

				
				
				//====================================================================================================
				
//				text=text+"sds value in line 24:";
//				sdsValue="";
//				for(String s:PDS.get(place).sds.keySet()){
//					if(sdsValue==""){
//						sdsValue=PDS.get(place).sds.get(s)+",";
//					}else{
//						sdsValue=sdsValue+PDS.get(place).sds.get(s)+",";
//					}
//				}
//				text=text+sdsValue+"\n";
//				text=text+"----------------------------------------------------------------------\n";
//
//				text=text+"tmps  value in line 24:";
//				tmpsValue="";
//				for(String s:tmps.keySet()){
//					if(tmpsValue==""){
//						tmpsValue=tmps.get(s)+",";
//					}else{
//						tmpsValue=tmpsValue+tmps.get(s)+",";
//					}
//				}
//				text=text+tmpsValue+"\n";
//				text=text+"----------------------------------------------------------------------\n";
				
				//====================================================================================================
				
				String cond2=equal(PDS.get(place).sds,tmps);

				//====================================================================================================
//				text=text+"(sds equal tmps) value in line 24:"+cond2+"\n";
//				text=text+"----------------------------------------------------------------------\n";
				//====================================================================================================

				
				//====================================================================================================
//				text=text+"wds value in line 24:";
//				wdsValue="";
//				for(String s:PDS.get(place).wds.keySet()){
//					if(wdsValue==""){
//						wdsValue=PDS.get(place).wds.get(s)+",";
//					}else{
//						wdsValue=wdsValue+PDS.get(place).wds.get(s)+",";
//					}
//				}
//				text=text+wdsValue+"\n";
//				text=text+"----------------------------------------------------------------------\n";
//
//				text=text+"tmpw new value in line 24:";
//				tmpwValue="";
//				for(String s:tmpw.keySet()){
//					if(tmpwValue==""){
//						tmpwValue=tmpw.get(s)+",";
//					}else{
//						tmpwValue=tmpwValue+tmpw.get(s)+",";
//					}
//				}
//				text=text+tmpwValue+"\n";
//				text=text+"----------------------------------------------------------------------\n";
				//====================================================================================================

				String cond1=equal(PDS.get(place).wds,tmpw);
				
				
				//====================================================================================================
//				text=text+"(wds equal tmpw) value in line 24:"+cond1+"\n";
//				text=text+"----------------------------------------------------------------------\n";
				//====================================================================================================

				
				if((cond1.compareToIgnoreCase("true")==0) && (cond2.compareToIgnoreCase("true")==0)){
					
					PDS.get(place).fixed=true;

				}else{
					PDS.get(place).wds.clear();
					PDS.get(place).sds.clear();
					PDS.get(place).wds.putAll(tmpw);
					PDS.get(place).sds.putAll(tmps);

				}
				
				//====================================================================================================
//				text=text+"wds value at the end of this calling of SEARCH:";
//				wdsValue="";
//				for(String s:PDS.get(place).wds.keySet()){
//					if(wdsValue==""){
//						wdsValue=PDS.get(place).wds.get(s)+",";
//					}else{
//						wdsValue=wdsValue+PDS.get(place).wds.get(s)+",";
//					}
//				}
//				text=text+wdsValue+"\n";
//				text=text+"----------------------------------------------------------------------\n";
//				
//				
//				text=text+"sds value at the end of this calling of SEARCH:";
//				sdsValue="";
//				for(String s:PDS.get(place).sds.keySet()){
//					if(sdsValue==""){
//						sdsValue=PDS.get(place).sds.get(s)+",";
//					}else{
//						sdsValue=sdsValue+PDS.get(place).sds.get(s)+",";
//					}
//				}
//				text=text+sdsValue+"\n";
//				text=text+"----------------------------------------------------------------------\n";
//
//				text=text+"fixed(p) value at the end of this calling of SEARCH:"+String.valueOf(PDS.get(place).fixed)+"\n";
				//====================================================================================================

				
//				
//				 File file1=new File(traceFile);
//				 Writer output1=new BufferedWriter(new FileWriter(file1,true));
//				
//				 output1.write(text);
//				 output1.close();

				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			 long EndTime = System.currentTimeMillis();
			 Writer output=null;
			 File file=new File(timeDetailFile);
			 output=new BufferedWriter(new FileWriter(file,true));
			 String text2="Function Name: SEARCH"+", Stop Name:"+stop+"  ,Place Name:"+place+",  Time:"+String.valueOf(EndTime-startTime)+"\n";  
			 output.write(text2);
			 output.close();
			
			
		}
		
		
		public static HashMap<String,String> intersection(HashMap<String,String> set1, HashMap<String,String> set2 ){
			HashMap<String,String> retVal=new HashMap<String, String>();
			
			try {

				if ((set1.isEmpty()) || (set2.isEmpty())){
					retVal.clear();
					return retVal;
				}else{
					
					Collection collection=set2.values();
					
					retVal.putAll(set1);
					retVal.values().retainAll(collection);
					
//					for(String s: set1.keySet()){
//						if(set2.containsValue(s)){
//							retVal.put(s, s);	
//						}
//					}
					
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				retVal.clear();
				
			}

			return retVal;
			
		}
		
		
		
	
		
		public static HashMap<String,String> union(HashMap<String,String> set1,HashMap<String,String> set2){

			HashMap<String,String> retVal= new HashMap<String, String>();
			try {
				
				if((set1.isEmpty()) && (set2.isEmpty()) ){
					retVal.clear();
				}else if ((set1.isEmpty()) && (!set2.isEmpty()) ){
					retVal.putAll(set2);
				}else if ((!set1.isEmpty()) && (set2.isEmpty())){
					retVal.putAll(set1);
				}else{
					
					
					
					
					retVal.putAll(set1);
					retVal.putAll(set2);
				
				
					
//					for(String s: set2.keySet()){
//						if(!(retVal.containsValue(s))){
//							retVal.put(s, s);
//						}
//					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				retVal.clear();
			}
		
			return retVal;
			
		}
		
		
		public static String subSet(HashMap<String,String> set1, HashMap<String,String> set2){

			String retVal="error";
			
			
			try {
				
				if ((set1.isEmpty()) && (set2.isEmpty())){
					retVal="false";
				}else if ((set1.isEmpty()) && (!set2.isEmpty())){
					retVal="true";
				}else if ((!set1.isEmpty()) && (set2.isEmpty())){
					retVal="false";
				}else{
					
					
					
					if (set1.size()>=set2.size()){
						retVal="false";
					}else{
						retVal="true";
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				retVal="error";
			}

		
			return retVal;
		}
		
		public static String equal(HashMap<String,String> set1, HashMap<String,String> set2){

			String retVal="error";
			
			
			try {
				
				
				
				if ((set1.isEmpty()) && (set2.isEmpty())){
					retVal="true";
				}else if ((set1.isEmpty()) && (!set2.isEmpty())){
					retVal="false";
				}else if ((!set1.isEmpty()) && (set2.isEmpty())){
					retVal="false";
				}else{
					
					if(set1.equals(set2)){
						retVal="true";
					}else{
						retVal="false";
					}
		
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				retVal="error";
			}

		
			return retVal;
		}

		
//		public static void transform (String  baseFilePath , String viewFilePath,  String transformedFilePath, String executionTimeFile, String workflowFileName) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException{
		
		public static void transform (String  baseFilePath , String viewFilePath,  String transformedFilePath, String executionTimeFile, String workflowFileName) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException{
			
			 String StartandStop="";
			 String stops="";
			 String workflowName="";
			 
			 String baseWorkflowInformation=getWorkflowInformation(baseFilePath);
			 Writer output=null;
				
			 String text="\n"+workflowFileName+";"+baseWorkflowInformation+"."+"\n";
			 File file=new File(executionTimeFile);
			 output=new BufferedWriter(new FileWriter(file,true));
			 output.write(text);
			 output.close();
				
			 
			 
			workflowName=getWorkflowName(baseFilePath);
			StartandStop=getStartAndStop(viewFilePath, workflowName);
			
			long startTime = System.currentTimeMillis();
			
				
			String[] arrStartandStop=StartandStop.split("\\,");
			
			for (int i=0;i<arrStartandStop.length;i++){
				String task=arrStartandStop[i].split("\\?")[0];
				String stop=arrStartandStop[i].split("\\?")[1];
			
				String formattedTaskName=task.replace(" ", "_");
				
				String transformedFileFullPath=transformedFilePath+"/"+formattedTaskName+"_"+"transformed_"+workflowFileName;
				
				File baseFile=new File(baseFilePath);
				DocumentBuilder baseBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document baseDoc = baseBuilder.parse(baseFile);
			
				
				
				StringWriter stw = new StringWriter(); 
				Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
				serializer.transform(new DOMSource(baseDoc), new StreamResult(stw)); 
				String xmlData=stw.toString();
					
				FileWriter fw=new FileWriter(transformedFileFullPath);
				fw.write(xmlData);
				fw.close();  

				String retValue=buildInitCheck(transformedFileFullPath);
				System.out.println("1:"+retValue);
				
				if (retValue.compareToIgnoreCase("true")==0){
				retValue=buildFinalCheck(transformedFileFullPath);
				System.out.println("2:"+retValue);
					if (retValue.compareToIgnoreCase("true")==0){
						if (stop.compareToIgnoreCase("outputCondition")==0){
							stop="end";
							System.out.println("3:true");
						}
							
						retValue=runConditionsCheck(baseFilePath,transformedFileFullPath,stop);
						System.out.println("4:"+retValue);
						
						if (retValue.compareToIgnoreCase("true")==0){
							
							retValue=runTaskCheck(baseFilePath,transformedFileFullPath, task,stop);
							System.out.println("5:"+retValue);
							if (retValue.compareToIgnoreCase("true")==0){
								retValue=runBuildCleanForStops(baseFilePath, transformedFileFullPath, stop);
								System.out.println("6:"+retValue);
								if (retValue.compareToIgnoreCase("true")==0){
									
									retValue=runBuildCleanForStarts(baseFilePath,transformedFileFullPath, task);
									System.out.println("7:"+retValue);
									
									long currentTime = System.currentTimeMillis();
									long elapsed =currentTime - startTime; 
									
									
									String workflowInformation=getWorkflowInformation(transformedFileFullPath);
									
									 output=null;
									
									 text=workflowFileName+";"+"View:"+task+";"+"Time:"+elapsed+";"+workflowInformation+"."+"\n";
									 file=new File(executionTimeFile);
									output=new BufferedWriter(new FileWriter(file,true));
									output.write(text);
									output.close();
									
								}
							}
						}
					}
				}

				

				
			}
			
			
//				File baseFile=new File(baseFilePath);
//				DocumentBuilder baseBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//				Document baseDoc = baseBuilder.parse(baseFile);
//				String workflowName="";
//				
//				
//				StringWriter stw = new StringWriter(); 
//				Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
//				serializer.transform(new DOMSource(baseDoc), new StreamResult(stw)); 
//				String xmlData=stw.toString();
//					
//				FileWriter fw=new FileWriter(transformedFilePath);
//				fw.write(xmlData);
//				fw.close();  
//
//				String retValue=buildInitCheck(transformedFilePath);
//				System.out.println("1:"+retValue);
//				
//				
//				if (retValue.compareToIgnoreCase("true")==0){
//					retValue=buildFinalCheck(transformedFilePath);
//					System.out.println("2:"+retValue);
//					
//
//
//					if (retValue.compareToIgnoreCase("true")==0){
//						workflowName=getWorkflowName(baseFilePath);
//						if (workflowName!=""){
//							retValue=updateFinalCondition(viewFilePath, workflowName, "Final_Stop", "end");
//							System.out.println("3:"+retValue);
//
//
//							if (retValue.compareToIgnoreCase("true")==0){
//								retValue=runConditionsCheck(baseFilePath, viewFilePath, transformedFilePath);
//								System.out.println("4:"+retValue);
//
//								
//								if (retValue.compareToIgnoreCase("true")==0){
//									retValue=runTaskCheck(baseFilePath, viewFilePath, transformedFilePath);
//									System.out.println("5:"+retValue);
//
//
//									if (retValue.compareToIgnoreCase("true")==0){
//										retValue=runBuildCleanForStops(baseFilePath, viewFilePath, transformedFilePath);
//										System.out.println("6:"+retValue);
//										
//										if (retValue.compareToIgnoreCase("true")==0){
//											retValue=runBuildCleanForStarts(baseFilePath, viewFilePath, transformedFilePath);
//											System.out.println("7:"+retValue);
//											
//											long currentTime = System.currentTimeMillis();
//											long elapsed =currentTime - startTime; 
//											
//											
//											Writer output=null;
//											
//											String text=workflowFileName+";"+views+";"+elapsed+"."+"\n";
//											File file=new File(executionTimeFile);
//											output=new BufferedWriter(new FileWriter(file,true));
//											output.write(text);
//											output.close();
//										
//											
//										}
//									}
//
//								}
//							}
//						}
//						
//						
//					}
// 
//	
//				}
				
				
		}
		
		
		public static String findLastsForOneDecomposition(String transformedFilePath, Element processControlElement ){
			String retVal="";
			String outputConditionID=""; 
			
			try {
			
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
		    		

		    		
		    		
		    	

				
				
			} catch (Exception e) {
				retVal="";
			}
			
			return retVal;
		}
		
		public static  String  findLasts(String transformedFilePath){
			
			String retVal="false";
			
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
					Element   decompositionElement=(Element)decompositionNodes.item(0);
						

			    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
			   		Element   processControlElements=(Element)processControlNodes.item(0);
						
			   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
			   		Element   layoutElement=(Element)layoutNodes.item(0);
			    		
			   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
			   		if (layoutSpecificationNodes.getLength()==1){
			    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
			    			
			   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
			   			if (layoutNetNodes.getLength()==1){
			   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
			    				
			   				retVal=findLastsForOneDecomposition(transformedFilePath, processControlElements);
			    				
		    			}else{// there are more layout net nodes
			    				
		    			}
			    			
			   		}else{ // there are more layout specification nodes
			    			
			   		}
			    		
						
				}else{ // there are more decomposition nodes
					Element   decompositionElement=null;
					for (int i=0; i<decompositionNodes.getLength();i++){
						Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
						if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
							   decompositionElement=tmpDecompositionElement;
						}
					}
					
					NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
			   		Element   processControlElements=(Element)processControlNodes.item(0);
			   		
			   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
			   		Element   layoutElement=(Element)layoutNodes.item(0);
			    		
			   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
			   		if (layoutSpecificationNodes.getLength()==1){
			    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
			    			
			   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
			   			if (layoutNetNodes.getLength()==1){
			   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
			    				
			   				retVal=findLastsForOneDecomposition(transformedFilePath, processControlElements);
		    			}else{// there are more layout net nodes
			    				
		    			}
			    			
			   		}else{ // there are more layout specification nodes
			    			
			   		}
					
					
				}	
				
				
		    	
			} catch (Exception e) {
				e.printStackTrace();
			    retVal="false";
			}

				
				return retVal;

		}
		
		
		
		public static  String  getWorkflowElements(String workflowFilePath){
			
			String retVal="false";
			
			try {
				
				
				File workflowFile=new File(workflowFilePath);
				DocumentBuilder fileBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = fileBuilder.parse(workflowFile);
			
		    	NodeList specificationSetNodes=doc.getElementsByTagName("specificationSet");
		    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
		    	
		    	
		    	
				NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
				Element  specificationElement=(Element)specificationNodes.item(0);
				
					
					
				NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
				if (decompositionNodes.getLength()==1){
					Element   decompositionElement=(Element)decompositionNodes.item(0);
						

			    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
			   		Element   processControlElements=(Element)processControlNodes.item(0);
						
			   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
			   		Element   layoutElement=(Element)layoutNodes.item(0);
			    		
			   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
			   		if (layoutSpecificationNodes.getLength()==1){
			    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
			    			
			   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
			   			if (layoutNetNodes.getLength()==1){
			   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
			    				
			   				retVal=getWorkflowElementsForOneDecomposition(processControlElements, layoutNetElement);
			    				
		    			}else{// there are more layout net nodes
			    				
		    			}
			    			
			   		}else{ // there are more layout specification nodes
			    			
			   		}
			    		
						
				}else{ // there are more decomposition nodes
					Element   decompositionElement=null;
					for (int i=0; i<decompositionNodes.getLength();i++){
						Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
						if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
							   decompositionElement=tmpDecompositionElement;
						}
					}
					
					NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
			   		Element   processControlElements=(Element)processControlNodes.item(0);
			   		
			   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
			   		Element   layoutElement=(Element)layoutNodes.item(0);
			    		
			   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
			   		if (layoutSpecificationNodes.getLength()==1){
			    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
			    			
			   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
			   			if (layoutNetNodes.getLength()==1){
			   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
			    				
			   				retVal=getWorkflowElementsForOneDecomposition(processControlElements, layoutNetElement);
		    			}else{// there are more layout net nodes
			    				
		    			}
			    			
			   		}else{ // there are more layout specification nodes
			    			
			   		}
					
					
				}	
				
				
		    	
			} catch (Exception e) {
				e.printStackTrace();
			    retVal="false";
			}

				
				return retVal;

		}
		
		
		public static String findElementsLastIDForOneDecomposition(String transformedFilePath , Element processControlElements) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException	{
			String retVal="1";
			
			int defaultID=1;
			
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
	    		
	    		
	    
			
			return retVal;

		}
		
		
	public static String findElementsLastID(String transformedFilePath) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException	{
		String retVal="1";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=findElementsLastIDForOneDecomposition(transformedFilePath, processControlElements) ;
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=findElementsLastIDForOneDecomposition(transformedFilePath, processControlElements) ;
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
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
	
	
	public static String fillSpaceWithUnderline(String value){
		String retVal="";
		
		retVal=value.replace(" ", "_");
		
		return retVal; 
	}
	
	public static String  buildInitCheckForOneDecomposition(String transformedFilePath , Document transformedDoc , Element processControlElements,Element layoutNetElement  ){
		
		String retVal="false";
		
		try {
			String  initTaskID="init_"+findElementsLastID(transformedFilePath);
			
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
        		

        	
     
        		
          // layout
        				

        	    // init container
        		String inputConditionXY=getWorkflowElementContainerAttributeXY(transformedFilePath, inputConditionElementID);

        		String inputConditionX=inputConditionXY.split("\\,")[0];
        		String inputConditionY=inputConditionXY.split("\\,")[1];

        		shiftRightWorkflowElements(transformedDoc, "100.0", incrementContainerBounds(inputConditionX, "1"));
        				
        		Element initTaskContainerElement=createTaskContainer(transformedDoc, initTaskID, incrementContainerBounds(inputConditionX, "100"), inputConditionY, "", "AND_split");
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
        		Element InputConditionInitFlowElement=createFlowElement(transformedDoc, inputConditionElementID, initTaskID, "13", "12");
        		layoutNetElement.appendChild(InputConditionInitFlowElement);
        				
        				
        		// add flow between (init,first)

        		String[] first=nextElementRefID.split("\\,");
        		for(int i=0;i<first.length;i++){
        			if (getTaskJoinType(transformedFilePath, first[i])==""){
        				Element flowElement=createFlowElement(transformedDoc, initTaskID, first[i], "2", "12");
        				layoutNetElement.appendChild(flowElement);
        			}else{
        				Element flowElement=createFlowElement(transformedDoc, initTaskID, first[i], "2", "2");
        				layoutNetElement.appendChild(flowElement);
        			}

        		}	

        		retVal="true";
			
		} catch (Exception e) {
			retVal="false";
		}
		
		
		return retVal;
	}
	
	public static String buildInitCheck(String transformedFilePath) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException, TransformerException{
		String retVal="false";
		
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
			Element   decompositionElement=(Element)decompositionNodes.item(0);
				

	    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	   		Element   processControlElements=(Element)processControlNodes.item(0);
				
	   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
	   		Element   layoutElement=(Element)layoutNodes.item(0);
	    		
	   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
	   		if (layoutSpecificationNodes.getLength()==1){
	    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
	    			
	   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
	   			if (layoutNetNodes.getLength()==1){
	   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
	    				
	   				retVal=buildInitCheckForOneDecomposition(transformedFilePath, transformedDoc, processControlElements, layoutNetElement);
	    				
	    				
    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
	   			    Transformer transformer=transformerFactory.newTransformer();
	   			    DOMSource source=new DOMSource(transformedDoc);
	   			    StreamResult result=new StreamResult(transformedFile);
	   			    transformer.transform(source, result);
    			    retVal="true";

	    				
    			}else{// there are more layout net nodes
	    				
    			}
	    			
	   		}else{ // there are more layout specification nodes
	    			
	   		}
	    		
				
		}else{ // there are more decomposition nodes
			Element   decompositionElement=null;
			for (int i=0; i<decompositionNodes.getLength();i++){
				Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
				if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
					   decompositionElement=tmpDecompositionElement;
				}
			}
			
			NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
	   		Element   processControlElements=(Element)processControlNodes.item(0);
	   		
	   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
	   		Element   layoutElement=(Element)layoutNodes.item(0);
	    		
	   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
	   		if (layoutSpecificationNodes.getLength()==1){
	    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
	    			
	   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
	   			if (layoutNetNodes.getLength()==1){
	   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
	    				
	   				retVal=buildInitCheckForOneDecomposition(transformedFilePath, transformedDoc, processControlElements, layoutNetElement);
	    				
	    				
    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
	   			    Transformer transformer=transformerFactory.newTransformer();
	   			    DOMSource source=new DOMSource(transformedDoc);
	   			    StreamResult result=new StreamResult(transformedFile);
	   			    transformer.transform(source, result);
    			    retVal="true";

	    				
    			}else{// there are more layout net nodes
	    				
    			}
	    			
	   		}else{ // there are more layout specification nodes
	    			
	   		}
			
			
		}	
		
		
    	
	} catch (Exception e) {
		e.printStackTrace();
	    retVal="false";
	}

		
		return retVal;
	}
	
	public static void createTaskDecorator(Document doc,Element taskContainerElement, String elementID, String x, String y , String joinType,  String splitType){
		
		
		try {
			
		
			
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
				

				String splitXY=calculateElementSplitXY(x, y);
				
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
			e.printStackTrace();
			
		}
		
		
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
				

				String splitXY=calculateElementSplitXY(x, y);
				
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
			e.printStackTrace();
			taskContainerElement=null;
		}
		
		return taskContainerElement;
	}
	
	
	public static Element createFlowElement(Document doc, String source, String target , String inPortNo, String outPortNo){
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
			
			//System.out.println(taskID+":"+taskName+":"+joinCode+":"+splitCode);
			
			if (joinCode==""){
				joinCode="xor";
				
				
			}
			
			if (splitCode==""){
				splitCode="and";
				

			}
			
			
			taskElement=doc.createElement("task");
			taskElement.setAttribute("id", taskID);
			
			Element taskNameElement=doc.createElement("name");
			taskNameElement.appendChild(doc.createTextNode(taskName));
			
			Element taskJoinElement=doc.createElement("join");
			taskJoinElement.setAttribute("code", joinCode);
			
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
	
	
	public static String buildFinalCheckForOneDecomposition(String transformedFilePath , Document transformedDoc , Element processControlElements,Element layoutNetElement ){
		String retVal="false";
		
		try {
			
			String  taskID=findElementsLastID(transformedFilePath);
			String  checkOutTaskID="check_out_"+taskID;
			String  exitTaskID="exit_"+String.valueOf(Integer.parseInt(taskID)+1);
			String  endConditionID="end_"+String.valueOf(Integer.parseInt(taskID)+2);

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
    		
			//exit task
			String outputConditionXY=getWorkflowElementContainerAttributeXY(transformedFilePath, outputConditionID);
			String outputConditionX=outputConditionXY.split("\\,")[0];
			String outputConditionY=outputConditionXY.split("\\,")[1];
			
			shiftRightWorkflowElements(transformedDoc, "400.0", incrementContainerBounds(outputConditionX, "-1"));
			
		
			
			String exitTaskX=incrementContainerBounds(outputConditionX, "300");
			String exitTaskY=outputConditionY;
			
			Element newExitTaskContainerElement=createTaskContainer(transformedDoc, exitTaskID, exitTaskX, exitTaskY, "AND_join", "");
			layoutNetElement.appendChild(newExitTaskContainerElement);
			
			
			// end condition
			String endConditionX=incrementContainerBounds(exitTaskX, "-200");
			String endConditionY=exitTaskY;
			Element newEndContainerElement=createConditionContainer(transformedDoc, endConditionID, endConditionX,endConditionY);
			layoutNetElement.appendChild(newEndContainerElement);
		
			// check_out task
			String   checkOutTaskX=incrementContainerBounds(endConditionX, "100");
			String   checkOutTaskY=endConditionY;
			
			Element newcheckOutContainerElement=createTaskContainer(transformedDoc, checkOutTaskID, checkOutTaskX, checkOutTaskY, "", "AND_split");
			layoutNetElement.appendChild(newcheckOutContainerElement);
			
			
		
			
			
			
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
			Element end_CheckOutLayoutNetFlowElement=createFlowElement(transformedDoc, endConditionID, checkOutTaskID, "13", "12");
			layoutNetElement.appendChild(end_CheckOutLayoutNetFlowElement);
			
			

			
			// (exit,output)
			Element  exit_OutputLayoutNetFlowElement=createFlowElement(transformedDoc, exitTaskID, outputConditionID, "13", "12" );
			layoutNetElement.appendChild(exit_OutputLayoutNetFlowElement);

    		
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String buildFinalCheck(String transformedFilePath){
		String retVal="false";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildFinalCheckForOneDecomposition(transformedFilePath, transformedDoc, processControlElements, layoutNetElement);
		    				
		    				
	    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
	    			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildFinalCheckForOneDecomposition(transformedFilePath, transformedDoc, processControlElements, layoutNetElement);
		    				
		    				
	    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
	    			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;
	}
	
	
	public static String getWorkflowMaxY(Document doc){
		String retVal="";
		
		try {
			
			int y=0;
			
			
		
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
    				
    			
    				//vertex
    				NodeList vertexNodes=layoutNetElement.getElementsByTagName("vertex");
    				for (int i=0; i<vertexNodes.getLength();i++){
    					Element vertexElement=(Element)vertexNodes.item(0);
    					if (vertexElement.hasAttribute("id")){
    						
    						
    						NodeList vertexAttributesNode=vertexElement.getElementsByTagName("attributes");
    						Element  vertexAttributesElement=(Element)vertexAttributesNode.item(0);
    						
    						NodeList  vertexBoundsNode=vertexAttributesElement.getElementsByTagName("bounds");
    						Element   vertexBoundsElement=(Element)vertexBoundsNode.item(0);
    						
    						if (getValueInInt(vertexBoundsElement.getAttribute("y"))>y){
    							
    							y=getValueInInt(vertexBoundsElement.getAttribute("y"));
    							
    						}
    					}
    				}
    				
    				
    				
    				
    				//container
    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
    				for(int i=0;i<containerNodes.getLength();i++){
    					Element containerElement=(Element)containerNodes.item(i);
    					
    					NodeList containerVertexNode=containerElement.getElementsByTagName("vertex");
    					Element  containerVertexElement=(Element)containerVertexNode.item(0);
    					
    					NodeList containerAttributesNode=containerVertexElement.getElementsByTagName("attributes");
    					Element  containerAttributesElement=(Element)containerAttributesNode.item(0);
    					
    					NodeList containerBoundsNode=containerAttributesElement.getElementsByTagName("bounds");
    					Element  containerBoundsElement=(Element)containerBoundsNode.item(0);
    					
    					if (getValueInInt(containerBoundsElement.getAttribute("y"))>y){
							y=getValueInInt(containerBoundsElement.getAttribute("y"));
						}
    					
    					
    					NodeList labelNode=containerElement.getElementsByTagName("label");
    					Element  labelElement=(Element)labelNode.item(0);
    					
    					NodeList labelAttributesNode=labelElement.getElementsByTagName("attributes");
    					Element  labelAttributesElement=(Element)labelAttributesNode.item(0);
    					
    					NodeList  labelBoundsNode=labelAttributesElement.getElementsByTagName("bounds");
    					Element   labelBoundsElement=(Element)labelBoundsNode.item(0);
    					
    					if (getValueInInt(labelBoundsElement.getAttribute("y"))>y){
							y=getValueInInt(labelBoundsElement.getAttribute("y"));
						}
    					
    					
    					NodeList decoratorNodes=containerElement.getElementsByTagName("container");
    					for(int j=0;j<decoratorNodes.getLength();j++){
    						Element decoratorElement=(Element)decoratorNodes.item(j);
    						
    						NodeList decoratorAttributesNode=decoratorElement.getElementsByTagName("attributes");
    						Element  decoratorAttributesElement=(Element)decoratorAttributesNode.item(0);
    						
    						NodeList decoratorBoundsNode=decoratorAttributesElement.getElementsByTagName("bounds");
    						Element decoratorBoundsElement=(Element)decoratorBoundsNode.item(0);
    						
    						if (getValueInInt(decoratorBoundsElement.getAttribute("y"))>y){
    							y=getValueInInt(decoratorBoundsElement.getAttribute("y"));
    						}
    					}
   					 
    				}
    				
    				
    				//flow
    				
    				NodeList  flowNodes=layoutNetElement.getElementsByTagName("flow");
    				
    				for (int i=0; i<flowNodes.getLength();i++){
    					Element flowElement=(Element)flowNodes.item(i);
    					
    					NodeList flowAttributesNode=flowElement.getElementsByTagName("attributes");
    					Element  flowAttributesElement=(Element)flowAttributesNode.item(0);
    					
    					NodeList  flowPointsNode=flowAttributesElement.getElementsByTagName("points");
    					Element   flowPointsElement=(Element)flowPointsNode.item(0);
    					
    					if(flowPointsElement!=null){
    						NodeList  pointValuesNode=flowPointsElement.getElementsByTagName("value");
        					for (int j=0;j<pointValuesNode.getLength();j++){
        						
        						Element pointValuesElement=(Element)pointValuesNode.item(j);
        						
        						if (getValueInInt(pointValuesElement.getAttribute("y"))>y){
        							y=getValueInInt(pointValuesElement.getAttribute("y"));
        							
        						}
        					}
    					}
    					
    				
    				}
    				
    			}else{//there are more layout net nodes
    				
    			}
    		}else{// there are more layout specification nodes
    			
    		}
    		
    		retVal=String.valueOf(y+".0");
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="";
		}
		
		return retVal;
		
	}
	
	
	public static String getWorkflowMinY(Document doc){
		String retVal="";
		
		try {
			
			
			int y=0;
			
			
		
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
    				
    				Boolean first=true;
    				
    				//vertex
    				NodeList vertexNodes=layoutNetElement.getElementsByTagName("vertex");
    				for (int i=0; i<vertexNodes.getLength();i++){
    					Element vertexElement=(Element)vertexNodes.item(0);
    					if (vertexElement.hasAttribute("id")){
    						
    						NodeList vertexAttributesNode=vertexElement.getElementsByTagName("attributes");
    						Element  vertexAttributesElement=(Element)vertexAttributesNode.item(0);
    						
    						NodeList  vertexBoundsNode=vertexAttributesElement.getElementsByTagName("bounds");
    						Element   vertexBoundsElement=(Element)vertexBoundsNode.item(0);
    						
    						if (first){
    							y=getValueInInt(vertexBoundsElement.getAttribute("y"));
    							first=false;
    						}
    						
    						if (getValueInInt(vertexBoundsElement.getAttribute("y"))<y){
    							
    							y=getValueInInt(vertexBoundsElement.getAttribute("y"));
    							
    						}
    					}
    				}
    				
    				
    				
    				
    				//container
    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
    				for(int i=0;i<containerNodes.getLength();i++){
    					Element containerElement=(Element)containerNodes.item(i);
    					
    					NodeList containerVertexNode=containerElement.getElementsByTagName("vertex");
    					Element  containerVertexElement=(Element)containerVertexNode.item(0);
    					
    					NodeList containerAttributesNode=containerVertexElement.getElementsByTagName("attributes");
    					Element  containerAttributesElement=(Element)containerAttributesNode.item(0);
    					
    					NodeList containerBoundsNode=containerAttributesElement.getElementsByTagName("bounds");
    					Element  containerBoundsElement=(Element)containerBoundsNode.item(0);
    					
    					if (getValueInInt(containerBoundsElement.getAttribute("y"))<y){
							y=getValueInInt(containerBoundsElement.getAttribute("y"));
						}
    					
    					
    					NodeList labelNode=containerElement.getElementsByTagName("label");
    					Element  labelElement=(Element)labelNode.item(0);
    					
    					NodeList labelAttributesNode=labelElement.getElementsByTagName("attributes");
    					Element  labelAttributesElement=(Element)labelAttributesNode.item(0);
    					
    					NodeList  labelBoundsNode=labelAttributesElement.getElementsByTagName("bounds");
    					Element   labelBoundsElement=(Element)labelBoundsNode.item(0);
    					
    					if (getValueInInt(labelBoundsElement.getAttribute("y"))<y){
							y=getValueInInt(labelBoundsElement.getAttribute("y"));
						}
    					
    					
    					NodeList decoratorNodes=containerElement.getElementsByTagName("container");
    					for(int j=0;j<decoratorNodes.getLength();j++){
    						Element decoratorElement=(Element)decoratorNodes.item(j);
    						
    						NodeList decoratorAttributesNode=decoratorElement.getElementsByTagName("attributes");
    						Element  decoratorAttributesElement=(Element)decoratorAttributesNode.item(0);
    						
    						NodeList decoratorBoundsNode=decoratorAttributesElement.getElementsByTagName("bounds");
    						Element decoratorBoundsElement=(Element)decoratorBoundsNode.item(0);
    						
    						if (getValueInInt(decoratorBoundsElement.getAttribute("y"))<y){
    							y=getValueInInt(decoratorBoundsElement.getAttribute("y"));
    						}
    					}
   					 
    				}
    				
    				
    				//flow
    				
    				NodeList  flowNodes=layoutNetElement.getElementsByTagName("flow");
    				
    				for (int i=0; i<flowNodes.getLength();i++){
    					Element flowElement=(Element)flowNodes.item(i);
    					
    					NodeList flowAttributesNode=flowElement.getElementsByTagName("attributes");
    					Element  flowAttributesElement=(Element)flowAttributesNode.item(0);
    					
    					NodeList  flowPointsNode=flowAttributesElement.getElementsByTagName("points");
    					Element   flowPointsElement=(Element)flowPointsNode.item(0);
    					
    					if(flowPointsElement!=null){
    						NodeList  pointValuesNode=flowPointsElement.getElementsByTagName("value");
        					for (int j=0;j<pointValuesNode.getLength();j++){
        						
        						Element pointValuesElement=(Element)pointValuesNode.item(j);
        						
        						if (getValueInInt(pointValuesElement.getAttribute("y"))<y){
        							y=getValueInInt(pointValuesElement.getAttribute("y"));
        							
        						}
        					}
    					}
    					
    				
    				}
    				
    			}else{//there are more layout net nodes
    				
    			}
    		}else{// there are more layout specification nodes
    			
    		}
    		
    		retVal=String.valueOf(y+".0");
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="";
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
					
					if ((stopName.compareToIgnoreCase(conditionName)==0)) {
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
	
	
	public static String getStartsOfStop(String viewFilePath, String stopName, String workflowName){
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
					String tmpStopName=getCharacterDataFromElement(taskElement).split("\\?")[1];
					if (tmpStopName.compareToIgnoreCase(stopName)==0){
						
						if (retVal==""){
							retVal=startName;
						}else{
							retVal=retVal+","+startName;
						}
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
		
		return retVal;	
	}
	
	public static String getStartAndStop(String viewFilePath, String workflowName){
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
					
					if (retVal==""){
						retVal=getCharacterDataFromElement(taskElement);
					}else{
						retVal=retVal+","+getCharacterDataFromElement(taskElement);
					}
				
				}
			}
			
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	
	
	
	
	public static String getStops(String transformedFilePath, String viewFilePath){
		String retVal="false";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getStopsForOneDecomposition(transformedFilePath, viewFilePath,processControlElements);
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getStopsForOneDecomposition(transformedFilePath, viewFilePath,processControlElements);
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;
	}
	
	
	
	
	
	public static String getStopsForOneDecomposition(String   transformedFilePath ,String viewFilePath, Element processControlElements ){
		String retVal="";
		
		try {
			
				String workflowName =getWorkflowName(transformedFilePath);
				
				NodeList outputConditionNode=processControlElements.getElementsByTagName("outputCondition");
				Element  outputConditionElement=(Element)outputConditionNode.item(0);
				
				NodeList outputConditionNameNode=outputConditionElement.getElementsByTagName("name");
				if (outputConditionNameNode.getLength()>0){
					
					Element outputConditionNameElement=(Element)outputConditionNameNode.item(0);
					String  outputName=getCharacterDataFromElement(outputConditionNameElement);
					
					if (conditionIsStop(viewFilePath, outputName, workflowName)){
	    				

		    			if (retVal==""){
		    				retVal=outputName;
		    			}else{
		    				retVal=retVal+","+outputName;
		    			}
		    			
		    		}
					
				}else{
					
					String outputName="outputCondition";
					if (conditionIsStop(viewFilePath, outputName, workflowName)){
		    			if (retVal==""){
		    				retVal=outputName;
		    			}else{
		    				retVal=retVal+","+outputName;
		    			}
		    			
		    		}
					
				}
				
			
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
	    		
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="";
		}
		
		
		return retVal;
	}

	
	public static String getStartsForOneDecomposition(String   transformedFilePath ,String viewFilePath, Element   processControlElements ){
		
	String retVal="";
		
		try {
		String workflowName =getWorkflowName(transformedFilePath);
		
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
		
		} catch (Exception e) {
			retVal="";
		}
		
		
		return retVal;
	}
	
	public static String getStarts(String   transformedFilePath ,String viewFilePath ){
		String retVal="false";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getStartsForOneDecomposition(transformedFilePath, viewFilePath,processControlElements);
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getStartsForOneDecomposition(transformedFilePath, viewFilePath,processControlElements);
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;
	}

	
	public static String getWorkflowElementIDFromNameForOneDecomposition(String workflowFilePath, String elementName , Element processControlElement ){
		String retVal="";
		try {
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
	  
	    

			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal; 
	}
	
	
	public static String getWorkflowElementIDFromName(String workflowFilePath, String elementName){
		String retVal="";
		
		try {
			
			
			File transformedFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);
		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
	    	
	    	
	    	
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
			Element  specificationElement=(Element)specificationNodes.item(0);
			
				
				
			NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
			if (decompositionNodes.getLength()==1){
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getWorkflowElementIDFromNameForOneDecomposition(workflowFilePath, elementName, processControlElements);
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getWorkflowElementIDFromNameForOneDecomposition(workflowFilePath, elementName, processControlElements);
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="";
		}

			
			return retVal;
	}
	
	
    public static String getWorkflowElementNameFromIDForOneDecomposition(String workflowFilePath, String elementID, Element processControlElement){
    	String retVal="";
    	
    	try {
    		
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
	    		
			
		} catch (Exception e) {
			retVal="";
		}
		
		return retVal;
    }
	
    public static String getWorkflowElementNameFromID(String workflowFilePath, String elementID){
		String retVal="";
		
		try {
			
			
			File transformedFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);
		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
	    	
	    	
	    	
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
			Element  specificationElement=(Element)specificationNodes.item(0);
			
				
				
			NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
			if (decompositionNodes.getLength()==1){
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getWorkflowElementNameFromIDForOneDecomposition(workflowFilePath, elementID, processControlElements);
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getWorkflowElementNameFromIDForOneDecomposition(workflowFilePath, elementID, processControlElements);
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="";
		}

			
			return retVal;
    }
	 
    
    public static String getSuccessorsForOneDecomposition(String workflowFilePath , String elementID, Element processControlElement){
        
		
  	  
		String retVal="";
		
		try {
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
	    		
	      		
	    		
	    	
	  
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="";
		}
		
		return retVal;
    	
    	
    }
    
    
    public static String getSuccessors(String workflowFilePath , String elementID){
    
		String retVal="";
		
		try {
			
			
			File transformedFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);
		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
	    	
	    	
	    	
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
			Element  specificationElement=(Element)specificationNodes.item(0);
			
				
				
			NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
			if (decompositionNodes.getLength()==1){
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getSuccessorsForOneDecomposition(workflowFilePath, elementID, processControlElements);
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getSuccessorsForOneDecomposition(workflowFilePath, elementID, processControlElements);
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="";
		}

			
			return retVal;
    	
    	
    }
    
	public static String getPredecessorsForOneDecomposition(String workflowFilePath , String elementID, Element processControlElement){
		String retVal="";
		
		try {
		
	    		
	    	
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
	    		
	    		
	    		
	    		NodeList outputConditionNode=processControlElement.getElementsByTagName("outputCondition");
	    		Element  outputConditionElement=(Element)outputConditionNode.item(0);
	    		
	    		NodeList outputConditionFlowsIntoNodes=outputConditionElement.getElementsByTagName("flowsInto");
	    		for(int i=0;i<outputConditionFlowsIntoNodes.getLength();i++){
	    			Element FlowsIntoElement=(Element)outputConditionFlowsIntoNodes.item(i);
	    			
	    			NodeList nextElementRefNode=FlowsIntoElement.getElementsByTagName("nextElementRef");
	    			Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
	    			
	    			if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(elementID)==0){
    					if (retVal==""){
    						retVal=outputConditionElement.getAttribute("id");
    					}else{
    						retVal=retVal+","+outputConditionElement.getAttribute("id");

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
	  
	    
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="";
		}
		
		return retVal;
	}
	
    
	public static String getWorkflowElementType(String workflowFilePath , String elementID){
		String retVal="false";
		
		try {
			
			
			File transformedFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);
		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
	    	
	    	
	    	
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
			Element  specificationElement=(Element)specificationNodes.item(0);
			
				
				
			NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
			if (decompositionNodes.getLength()==1){
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getWorkflowElementTypeForOneDecomposition(elementID, processControlElements, layoutNetElement);
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getWorkflowElementTypeForOneDecomposition(elementID, processControlElements, layoutNetElement);
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;
	}
	

	public static String getPredecessors(String workflowFilePath , String elementID){
		String retVal="false";
		
		try {
			
			
			File transformedFile=new File(workflowFilePath);
			DocumentBuilder transformedBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = transformedBuilder.parse(transformedFile);
		
	    	NodeList specificationSetNodes=transformedDoc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
	    	
	    	
	    	
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
			Element  specificationElement=(Element)specificationNodes.item(0);
			
				
				
			NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
			if (decompositionNodes.getLength()==1){
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getPredecessorsForOneDecomposition(workflowFilePath, elementID, processControlElements);
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getPredecessorsForOneDecomposition(workflowFilePath, elementID, processControlElements);
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;
	}

	
	public static String buildPresenceCheckForOneDecomposition(String baseFilePath, String transformedFilePath, String conditionName , Document transformedDoc , Element processControlElements,Element layoutNetElement){
		String retVal="false";
		
		try {
			String conditionID=getWorkflowElementIDFromName(transformedFilePath, conditionName);
			String lastElementID=findElementsLastID(transformedFilePath);

    		// calculate elements' ID
    		String deadConditionID="dead_"+fillSpaceWithUnderline(conditionName)+"_"+lastElementID;
    		String activeTaskConditionID="active_task_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+1);
    		String activeConditionID="active_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+2);
    		String checkoutConditionID="check_out_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+3);
    		String checkActiveTaskID="check_active_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+4);
    		String proceedTaskID="proceed_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+5);
    		String endTaskID="end_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+6);
    		String exitTaskID="exit_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+7);

    		
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
    		
    		// add check_out condition
    		nextElementRefsID=proceedTaskID+","+checkActiveTaskID;
    		Element checkoutConditionElement=createWorkflowConditionElement(transformedDoc, checkoutConditionID, "check_out_"+conditionName, nextElementRefsID);
    		processControlElements.appendChild(checkoutConditionElement);
    		
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
    	    		checkOutFlowsIntoNextElement.setAttribute("id", checkoutConditionID);
    	    		
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
    		

    		
    		//layout
    		
			String maxY=getWorkflowMaxY(transformedDoc);
			
			String checkOutXY=getWorkflowElementContainerAttributeXY(transformedFilePath, getWorkflowElementIDFromName(transformedFilePath, "check_out"));

			String checkOutX=checkOutXY.split("\\,")[0];
			String checkOutY=checkOutXY.split("\\,")[1];

			
			
			// active condition
			String activeConditionX=incrementContainerBounds(checkOutX, "300");
			String activeConditionY=incrementContainerBounds(maxY, "100");

			Element  activeContainerElement=createConditionContainer(transformedDoc, activeConditionID, activeConditionX,activeConditionY);
			layoutNetElement.appendChild(activeContainerElement);

			
			// active_task condition
			String activeTaskX=incrementContainerBounds(activeConditionX, "-200");
			String activeTaskY=incrementContainerBounds(activeConditionY, "100");
			Element  activeTaskContainerElement=createConditionContainer(transformedDoc, activeTaskConditionID, activeTaskX,activeTaskY);
			layoutNetElement.appendChild(activeTaskContainerElement);

			// check_out task
			String checkOutConditionX=activeTaskX;
			String checkOutConditionY=incrementContainerBounds(activeTaskY, "200");
			Element checkOutContainerElement=createConditionContainer(transformedDoc, checkoutConditionID, checkOutConditionX, checkOutConditionY);
			layoutNetElement.appendChild(checkOutContainerElement);

			
			// check_active task
			String checkActiveX=incrementContainerBounds(activeTaskX, "100");
			String checkActiveY=incrementContainerBounds(activeTaskY, "100");

			
			Element checkActiveContainerElement=createTaskContainer(transformedDoc, checkActiveTaskID, checkActiveX, checkActiveY, "AND_join", "");
			layoutNetElement.appendChild(checkActiveContainerElement);
			
			//end task
			String endTaskX=incrementContainerBounds(activeConditionX, "100");
			String endTaskY=incrementContainerBounds(activeConditionY, "100");

			
			Element endContainerElement=createTaskContainer(transformedDoc, endTaskID, endTaskX, endTaskY, "AND_join", "");
			layoutNetElement.appendChild(endContainerElement);
			
			
			
			
			// dead condition
			String deadConditionX=checkOutConditionX;
			String deadConditionY=incrementContainerBounds(checkOutConditionY, "200");

			
			Element  deadContainerElement=createConditionContainer(transformedDoc, deadConditionID, deadConditionX, deadConditionY);
			layoutNetElement.appendChild(deadContainerElement);
			
			
			    				
			
			// proceed task
			String proceedTaskX=checkActiveX;
			String proceedTaskY=incrementContainerBounds(checkActiveY, "200");

			
			Element proceedContainerElement=createTaskContainer(transformedDoc, proceedTaskID, proceedTaskX, proceedTaskY, "AND_join", "");
			layoutNetElement.appendChild(proceedContainerElement);
			
		
		
			// exit task
			String exitTaskX=incrementContainerBounds(endTaskX, "100");
			String exitTaskY=incrementContainerBounds(endTaskY, "100");
			Element exitContainerElement=createTaskContainer(transformedDoc, exitTaskID, exitTaskX, exitTaskY, "XOR_join", "");
			layoutNetElement.appendChild(exitContainerElement);

			//flows
			//(check_out , check_out_c)
			Element   checkout_Checkout_C_LayoutNetFlowElement=createFlowElement(transformedDoc, getWorkflowElementIDFromName(transformedFilePath, "check_out"), checkoutConditionID, "2", "12");
			layoutNetElement.appendChild(checkout_Checkout_C_LayoutNetFlowElement);
			
			
			//(check_out_c , check_active)
			Element   checkout_CheckActiveLayoutNetFlowElement=createFlowElement(transformedDoc, checkoutConditionID, checkActiveTaskID , "13", "2");
			layoutNetElement.appendChild(checkout_CheckActiveLayoutNetFlowElement);
		
			
			
			//(check_out_c , proceed)
			Element   checkout_ProceedLayoutNetFlowElement=createFlowElement(transformedDoc, checkoutConditionID, proceedTaskID , "13", "2");
			layoutNetElement.appendChild(checkout_ProceedLayoutNetFlowElement);
		
			
			//(proceed , exit)
			Element   proceed_ExitLayoutNetFlowElement=createFlowElement(transformedDoc, proceedTaskID, exitTaskID , "13", "2");
			layoutNetElement.appendChild(proceed_ExitLayoutNetFlowElement);
		
			//(check_active , end)
			Element   checkActive_endLayoutNetFlowElement=createFlowElement(transformedDoc, checkActiveTaskID, endTaskID , "13", "2");
			layoutNetElement.appendChild(checkActive_endLayoutNetFlowElement);
			
			//(end , exit)
			Element   end_ExitLayoutNetFlowElement=createFlowElement(transformedDoc, endTaskID, exitTaskID , "13", "2");
			layoutNetElement.appendChild(end_ExitLayoutNetFlowElement);

			//(active_task , check_active)
			Element   activeTask_CheckActiveLayoutNetFlowElement=createFlowElement(transformedDoc, activeTaskConditionID, checkActiveTaskID , "13", "2");
			layoutNetElement.appendChild(activeTask_CheckActiveLayoutNetFlowElement);

			//(active , end)
			Element   active_EndActiveLayoutNetFlowElement=createFlowElement(transformedDoc, activeConditionID, endTaskID , "13", "2");
			layoutNetElement.appendChild(active_EndActiveLayoutNetFlowElement);
			
			
			//(dead , proceed)
			Element   dead_ProceedLayoutNetFlowElement=createFlowElement(transformedDoc, deadConditionID , proceedTaskID , "13", "2");
			layoutNetElement.appendChild(dead_ProceedLayoutNetFlowElement);
	
			//(init , dead)
			Element   init_DeadLayoutNetFlowElement=createFlowElement(transformedDoc, getWorkflowElementIDFromName(transformedFilePath, "init") , deadConditionID , "2", "12");
			layoutNetElement.appendChild(init_DeadLayoutNetFlowElement);
			
			//(run , active)
			Element   run_ActiveLayoutNetFlowElement=createFlowElement(transformedDoc, getWorkflowElementIDFromName(transformedFilePath, "run_"+conditionName) , activeConditionID , "2", "12");
			layoutNetElement.appendChild(run_ActiveLayoutNetFlowElement);
			
			
			//(exit_c , exit)
			Element   exit_c_exitLayoutNetFlowElement=createFlowElement(transformedDoc, exitTaskID , getWorkflowElementIDFromName(transformedFilePath, "exit") , "13", "2");
			layoutNetElement.appendChild(exit_c_exitLayoutNetFlowElement);
		

			
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	public static String buildPresenceCheck(String baseFilePath, String transformedFilePath, String conditionName){
		String retVal="false";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildPresenceCheckForOneDecomposition(baseFilePath, transformedFilePath, conditionName, transformedDoc, processControlElements, layoutNetElement);
		   					
	    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
	    			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildPresenceCheckForOneDecomposition(baseFilePath, transformedFilePath, conditionName, transformedDoc, processControlElements, layoutNetElement);
		    				
		    				
	    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
	    			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;

	}
	
	
	public static String getWorkflowInformation(String workflowPath){
		String retVal="false";
		
		try {
			
			
			File workflowFile=new File(workflowPath);
			DocumentBuilder workflowBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = workflowBuilder.parse(workflowFile);
		
	    	NodeList specificationSetNodes=doc.getElementsByTagName("specificationSet");
	    	Element  specificationSetElement=(Element)specificationSetNodes.item(0);
	    	
	    	
	    	
			NodeList specificationNodes=specificationSetElement.getElementsByTagName("specification");
			Element  specificationElement=(Element)specificationNodes.item(0);
			
				
				
			NodeList decompositionNodes=specificationElement.getElementsByTagName("decomposition");
			if (decompositionNodes.getLength()==1){
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getWorkflowElementsInformation(processControlElements, layoutNetElement);
		   					
	    			

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=getWorkflowElementsInformation(processControlElements, layoutNetElement);
		    				
		    				
	    				

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
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
    						if(decoratorNodes==null){
    							retVal="";
    							return (retVal);
    						}
    						
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
    						if(decoratorNodes==null){
    							retVal="";
    							return (retVal);
    						}
    						
    						
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
	
	public static int getValueInInt(String value){
		int retVal=0;
		
		try {
			
			if (value.contains(".")){
				
				retVal=Integer.valueOf(value.split("\\.")[0]);				
			}else{
				retVal=Integer.valueOf(value);				
			}
			
		} catch (Exception e) {
			retVal=0;
		}
		
		return retVal;
	}
	
	public static void   shiftWorkflowElementX(String elementType, Element element, String shiftValue, String conditionValue ){
		
		try {
			
			int x=0;
			
			
			
			if (elementType.compareToIgnoreCase("attributes")==0){
				NodeList boundsNode=element.getElementsByTagName("bounds");
				Element  boundsElement=(Element)boundsNode.item(0);
				
				
				if (Integer.valueOf(boundsElement.getAttribute("x").split("\\.")[0])>Integer.valueOf(conditionValue.split("\\.")[0])){
					if (shiftValue.contains(".")){
						x=Integer.valueOf(boundsElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
					}else{
						x=Integer.valueOf(boundsElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue);
					}

					boundsElement.setAttribute("x", String.valueOf(x)+".0");
				}
			}else if (elementType.compareToIgnoreCase("points")==0){
				NodeList valueNodes=element.getElementsByTagName("value");
				for (int i=0;i<valueNodes.getLength();i++){
					Element valueElement=(Element)valueNodes.item(i);
					
					if (Integer.valueOf(valueElement.getAttribute("x").split("\\.")[0])>Integer.valueOf(conditionValue.split("\\.")[0])){
						if (shiftValue.contains(".")){
							x=Integer.valueOf(valueElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
						}else{
							x=Integer.valueOf(valueElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue);
						}

						valueElement.setAttribute("x", String.valueOf(x)+".0");
					}
				}
			}else if (elementType.compareToIgnoreCase("container")==0){
				
				NodeList vertexNode=element.getElementsByTagName("vertex");
				Element  vertexElement=(Element)vertexNode.item(0);
				
				NodeList vertexAttributesNode=vertexElement.getElementsByTagName("attributes");
				Element  vertexAttributesElement=(Element)vertexAttributesNode.item(0);
				
				NodeList vertexAttributesBoundsNode=vertexAttributesElement.getElementsByTagName("bounds");
				Element  vertexAttributesBoundsElement=(Element)vertexAttributesBoundsNode.item(0);
				
				if (Integer.valueOf(vertexAttributesBoundsElement.getAttribute("x").split("\\.")[0])>Integer.valueOf(conditionValue.split("\\.")[0])){
					if (shiftValue.contains(".")){
						x=Integer.valueOf(vertexAttributesBoundsElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
					}else{
						x=Integer.valueOf(vertexAttributesBoundsElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue);
					}

					vertexAttributesBoundsElement.setAttribute("x", String.valueOf(x)+".0");
					
					
					
				NodeList labelNode=element.getElementsByTagName("label");
				Element  labelElement=(Element)labelNode.item(0);
				
				NodeList  labelAttributesNode=labelElement.getElementsByTagName("attributes");
				Element   labelAttributesElement=(Element)labelAttributesNode.item(0);
				
				NodeList  labelBoundsNode=labelAttributesElement.getElementsByTagName("bounds");
				Element   labelBoundsElement=(Element)labelBoundsNode.item(0);
				
				if (shiftValue.contains(".")){
					x=Integer.valueOf(labelBoundsElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
				}else{
					x=Integer.valueOf(labelBoundsElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue);
				}
				
				labelBoundsElement.setAttribute("x", String.valueOf(x)+".0");
				
				
				
				NodeList	decoratorNode=element.getElementsByTagName("decorator");
				for (int i=0;i<decoratorNode.getLength();i++){
					
					Element  decoratorElement=(Element)decoratorNode.item(i);
					
					NodeList decoratorAttributesNode=decoratorElement.getElementsByTagName("attributes");
					Element  decoratorAttributesElement=(Element)decoratorAttributesNode.item(0);
					
					NodeList decoratorBoundsNode=decoratorAttributesElement.getElementsByTagName("bounds");
					Element  decoratorBoundsElemen=(Element)decoratorBoundsNode.item(0);
					
					
					if (shiftValue.contains(".")){
						x=Integer.valueOf(decoratorBoundsElemen.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
					}else{
						x=Integer.valueOf(decoratorBoundsElemen.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue);
					}
					
					decoratorBoundsElemen.setAttribute("x", String.valueOf(x)+".0");
					
				}
				
				
				
				}
				
				
			}
			
			
			
			
		} catch (Exception e) {
			
		}
		
	}
	
	public static void   shiftWorkflowElementY(String elementType, Element element, String shiftValue, String conditionValue ){
		
		try {
			
			int x=0;
			
			
			
			if (elementType.compareToIgnoreCase("attributes")==0){
				NodeList boundsNode=element.getElementsByTagName("bounds");
				Element  boundsElement=(Element)boundsNode.item(0);
				
				
				if (Integer.valueOf(boundsElement.getAttribute("y").split("\\.")[0])>Integer.valueOf(conditionValue.split("\\.")[0])){
					if (shiftValue.contains(".")){
						x=Integer.valueOf(boundsElement.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
					}else{
						x=Integer.valueOf(boundsElement.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue);
					}

					boundsElement.setAttribute("y", String.valueOf(x)+".0");
				}
			}else if (elementType.compareToIgnoreCase("points")==0){
				NodeList valueNodes=element.getElementsByTagName("value");
				for (int i=0;i<valueNodes.getLength();i++){
					Element valueElement=(Element)valueNodes.item(i);
					
					if (Integer.valueOf(valueElement.getAttribute("y").split("\\.")[0])>Integer.valueOf(conditionValue.split("\\.")[0])){
						if (shiftValue.contains(".")){
							x=Integer.valueOf(valueElement.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
						}else{
							x=Integer.valueOf(valueElement.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue);
						}

						valueElement.setAttribute("y", String.valueOf(x)+".0");
					}
				}
			}else if (elementType.compareToIgnoreCase("container")==0){
				
				NodeList vertexNode=element.getElementsByTagName("vertex");
				Element  vertexElement=(Element)vertexNode.item(0);
				
				NodeList vertexAttributesNode=vertexElement.getElementsByTagName("attributes");
				Element  vertexAttributesElement=(Element)vertexAttributesNode.item(0);
				
				NodeList vertexAttributesBoundsNode=vertexAttributesElement.getElementsByTagName("bounds");
				Element  vertexAttributesBoundsElement=(Element)vertexAttributesBoundsNode.item(0);
				
				if (Integer.valueOf(vertexAttributesBoundsElement.getAttribute("y").split("\\.")[0])>Integer.valueOf(conditionValue.split("\\.")[0])){
					if (shiftValue.contains(".")){
						x=Integer.valueOf(vertexAttributesBoundsElement.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
					}else{
						x=Integer.valueOf(vertexAttributesBoundsElement.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue);
					}

					vertexAttributesBoundsElement.setAttribute("y", String.valueOf(x)+".0");
					
					
					
				NodeList labelNode=element.getElementsByTagName("label");
				Element  labelElement=(Element)labelNode.item(0);
				
				NodeList  labelAttributesNode=labelElement.getElementsByTagName("attributes");
				Element   labelAttributesElement=(Element)labelAttributesNode.item(0);
				
				NodeList  labelBoundsNode=labelAttributesElement.getElementsByTagName("bounds");
				Element   labelBoundsElement=(Element)labelBoundsNode.item(0);
				
				if (shiftValue.contains(".")){
					x=Integer.valueOf(labelBoundsElement.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
				}else{
					x=Integer.valueOf(labelBoundsElement.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue);
				}
				
				labelBoundsElement.setAttribute("y", String.valueOf(x)+".0");
				
				
				
				NodeList	decoratorNode=element.getElementsByTagName("decorator");
				for (int i=0;i<decoratorNode.getLength();i++){
					
					Element  decoratorElement=(Element)decoratorNode.item(i);
					
					NodeList decoratorAttributesNode=decoratorElement.getElementsByTagName("attributes");
					Element  decoratorAttributesElement=(Element)decoratorAttributesNode.item(0);
					
					NodeList decoratorBoundsNode=decoratorAttributesElement.getElementsByTagName("bounds");
					Element  decoratorBoundsElemen=(Element)decoratorBoundsNode.item(0);
					
					
					if (shiftValue.contains(".")){
						x=Integer.valueOf(decoratorBoundsElemen.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
					}else{
						x=Integer.valueOf(decoratorBoundsElemen.getAttribute("y").split("\\.")[0])+ Integer.valueOf(shiftValue);
					}
					
					decoratorBoundsElemen.setAttribute("y", String.valueOf(x)+".0");
					
				}
				
				
				
				}
				
				
			}
			
			
			
			
		} catch (Exception e) {
			
		}
		
	}
	
	
	public static void   shiftWorkflowElementX(String elementType, Element element, String shiftValue){
		
		try {
			int x=0;
			if (elementType.compareToIgnoreCase("attributes")==0){
				NodeList boundsNode=element.getElementsByTagName("bounds");
				Element  boundsElement=(Element)boundsNode.item(0);
				
					if (shiftValue.contains(".")){
						x=Integer.valueOf(boundsElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
					}else{
						x=Integer.valueOf(boundsElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue);
					}

					boundsElement.setAttribute("x", String.valueOf(x)+".0");
					
			}else if (elementType.compareToIgnoreCase("points")==0){
				NodeList valueNodes=element.getElementsByTagName("value");
				for (int i=0;i<valueNodes.getLength();i++){
					Element valueElement=(Element)valueNodes.item(i);
					
					if (shiftValue.contains(".")){
						x=Integer.valueOf(valueElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue.split("\\.")[0]);
					}else{
						x=Integer.valueOf(valueElement.getAttribute("x").split("\\.")[0])+ Integer.valueOf(shiftValue);
					}

					valueElement.setAttribute("x", String.valueOf(x)+".0");
				}
				
				
			}
			
		
		
			
			
			
		} catch (Exception e) {
			
		}
		
	}
	
	
	
	
	public static String shiftRightWorkflowElements(String filePath, String shiftValue, String conditionValue){
		
		String retVal="false";
		
		try {
			
			File file=new File(filePath);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document transformedDoc = builder.parse(file);
			

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
    				
    				//shift input/output conditions
    				NodeList vertexNodes=layoutNetElement.getElementsByTagName("vertex");
    				for(int i=0; i<vertexNodes.getLength();i++){
    					Element vertexElement=(Element)vertexNodes.item(i);
    					
    					NodeList attributesNode=vertexElement.getElementsByTagName("attributes");
    					Element  attributesElement=(Element)attributesNode.item(0);
    					shiftWorkflowElementX("attributes",attributesElement, shiftValue, conditionValue);
    				}
    				
    				
    				
    				// shift containers
    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
    				for(int i=0;i<containerNodes.getLength();i++){
    					Element containerElement=(Element)containerNodes.item(i);
    					
    					NodeList containerVertexNode=containerElement.getElementsByTagName("vertex");
    					Element  containerVertexElement=(Element)containerVertexNode.item(0);
    					
    					NodeList containerVertexAttributesNode=containerVertexElement.getElementsByTagName("attributes");
    					Element  containerVertexAttributesElement=(Element)containerVertexAttributesNode.item(0);
    					
    					shiftWorkflowElementX("attributes",containerVertexAttributesElement, shiftValue, conditionValue);
    					
    					
    					
    					NodeList labelNode=containerElement.getElementsByTagName("label");
    					Element  labelElement=(Element)labelNode.item(0);
    					
    					NodeList labelAttributesNode=labelElement.getElementsByTagName("attributes");
    					Element  labelAttributeElement=(Element)labelAttributesNode.item(0);
    					
    					shiftWorkflowElementX("attributes",labelAttributeElement, shiftValue);
    					
    					
    					
    					NodeList decoratorNodes=containerElement.getElementsByTagName("decorator");
    					for(int j=0;j<decoratorNodes.getLength();j++){
    						
    						Element decoratorElement=(Element)decoratorNodes.item(j);
    						
    						NodeList decoratorAttributesNode=decoratorElement.getElementsByTagName("attributes");
    						Element  decoratorAttributeElement=(Element)decoratorAttributesNode.item(0);
    						
    						shiftWorkflowElementX("attributes",decoratorAttributeElement, shiftValue, conditionValue);
    					}
    				}
    				

    				// shift flows
    				
    				NodeList flowNodes=layoutNetElement.getElementsByTagName("flow");
    				for (int i=0;i<flowNodes.getLength();i++){
    					
    					Element flowElement=(Element)flowNodes.item(i);
    					
    					NodeList flowAttributesNode=flowElement.getElementsByTagName("attributes");
    					Element  flowAttributeElement=(Element)flowAttributesNode.item(0);
    					
    					NodeList flowPointsNode= flowAttributeElement.getElementsByTagName("points");
    					Element  flowPointsElement=(Element)flowPointsNode.item(0);
    					shiftWorkflowElementX("points", flowPointsElement, shiftValue);
    					
    				}
    				
    				
    			}else{// there is more layout net nodes
    				
    			}
    		}else{// there are more layout specification nodes
    			
    		}
			
    		

	    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		    Transformer transformer=transformerFactory.newTransformer();
		    DOMSource source=new DOMSource(transformedDoc);
		    StreamResult result=new StreamResult(file);
		    transformer.transform(source, result);
    		
    		
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
	
public static String shiftRightWorkflowElements(Document transformedDoc, String shiftValue, String conditionValue){
		
		String retVal="false";
		
		try {

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
    				
    				//shift input/output conditions
    				NodeList vertexNodes=layoutNetElement.getElementsByTagName("vertex");
    				for(int i=0; i<vertexNodes.getLength();i++){
    					Element vertexElement=(Element)vertexNodes.item(i);
    					
    					if(vertexElement.hasAttribute("id")){
    						NodeList attributesNode=vertexElement.getElementsByTagName("attributes");
        					Element  attributesElement=(Element)attributesNode.item(0);
        					shiftWorkflowElementX("attributes",attributesElement, shiftValue, conditionValue);
        					
    					}
    					
    					
    					
    				}
    				
    				
    				
    				// shift containers
    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
    				for(int i=0;i<containerNodes.getLength();i++){
    					Element containerElement=(Element)containerNodes.item(i);
    					
    					shiftWorkflowElementX("container",containerElement, shiftValue, conditionValue);
    					

    				}
    				

    				// shift flows
    				
    				NodeList flowNodes=layoutNetElement.getElementsByTagName("flow");
    				for (int i=0;i<flowNodes.getLength();i++){
    					
    					Element flowElement=(Element)flowNodes.item(i);
    					
    					NodeList flowAttributesNode=flowElement.getElementsByTagName("attributes");
    					Element  flowAttributeElement=(Element)flowAttributesNode.item(0);
    					
    					NodeList flowPointsNode= flowAttributeElement.getElementsByTagName("points");
    					Element  flowPointsElement=(Element)flowPointsNode.item(0);
    					shiftWorkflowElementX("points", flowPointsElement, shiftValue,conditionValue);
    					

    				}
    				
    				
    			}else{// there is more layout net nodes
    				
    			}
    		}else{// there are more layout specification nodes
    			
    		}
			
    		
    		retVal="true";
    		
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}
	
public static String shiftDownWorkflowElements(Document transformedDoc, String shiftValue, String conditionValue){
	
	String retVal="false";
	
	try {

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
				
				//shift input/output conditions
				NodeList vertexNodes=layoutNetElement.getElementsByTagName("vertex");
				for(int i=0; i<vertexNodes.getLength();i++){
					Element vertexElement=(Element)vertexNodes.item(i);
					
					if(vertexElement.hasAttribute("id")){
						NodeList attributesNode=vertexElement.getElementsByTagName("attributes");
    					Element  attributesElement=(Element)attributesNode.item(0);
    					shiftWorkflowElementY("attributes",attributesElement, shiftValue, conditionValue);
    					
					}
					
					
					
				}
				
				
				
				// shift containers
				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
				for(int i=0;i<containerNodes.getLength();i++){
					Element containerElement=(Element)containerNodes.item(i);
					
					shiftWorkflowElementY("container",containerElement, shiftValue, conditionValue);
					

				}
				

				// shift flows
				
				NodeList flowNodes=layoutNetElement.getElementsByTagName("flow");
				for (int i=0;i<flowNodes.getLength();i++){
					
					Element flowElement=(Element)flowNodes.item(i);
					
					NodeList flowAttributesNode=flowElement.getElementsByTagName("attributes");
					Element  flowAttributeElement=(Element)flowAttributesNode.item(0);
					
					NodeList flowPointsNode= flowAttributeElement.getElementsByTagName("points");
					Element  flowPointsElement=(Element)flowPointsNode.item(0);
					shiftWorkflowElementY("points", flowPointsElement, shiftValue,conditionValue);
					

				}
				
				
			}else{// there is more layout net nodes
				
			}
		}else{// there are more layout specification nodes
			
		}
		
		
		retVal="true";
		
	} catch (Exception e) {
		retVal="false";
	}
	
	return retVal;
}

	
	public static String getWorkflowElementsForOneDecomposition(Element processControlElements,Element layoutNetElement){
		String retVal="false";
		
		try {
			
			NodeList inputConditionNode=processControlElements.getElementsByTagName("inputCondition");
			Element  inputConditionElement=(Element)inputConditionNode.item(0);
			
			NodeList inputConditionNameNode=inputConditionElement.getElementsByTagName("name");
			
			if (inputConditionNameNode.getLength()==0){
				
				retVal="inputCondition,"+inputConditionElement.getAttribute("id")+",condition";
				
			}else{
				
				Element inputConditionNameElement=(Element)inputConditionNameNode.item(0);
				
				retVal=getCharacterDataFromElement(inputConditionNameElement)+","+inputConditionElement.getAttribute("id")+",condition";
				
			}
			
			
			
			
			NodeList outputConditionNode=processControlElements.getElementsByTagName("outputCondition");
			Element  outputConditionElement=(Element)outputConditionNode.item(0);
			
			NodeList outputConditiuonNameNode=outputConditionElement.getElementsByTagName("name");
			
			if(outputConditiuonNameNode.getLength()==0){
				retVal=retVal+"?"+"outputCondition,"+outputConditionElement.getAttribute("id")+",condition";
			}else{
				
				Element outputConditionNameElement=(Element)outputConditiuonNameNode.item(0);
				retVal=retVal+"?"+getCharacterDataFromElement(outputConditionNameElement)+","+outputConditionElement.getAttribute("id")+",condition";
			}
			
			
			NodeList tasksNode=processControlElements.getElementsByTagName("task");
			for (int i=0;i<tasksNode.getLength();i++){
				
				Element taskElement=(Element)tasksNode.item(i);
				
				NodeList taskNameNode=taskElement.getElementsByTagName("name");
				Element  taskNameElement=(Element)taskNameNode.item(0);
				
				retVal=retVal+"?"+getCharacterDataFromElement(taskNameElement)+","+taskElement.getAttribute("id")+",task";
			}
			
	
			NodeList conditionsNode=processControlElements.getElementsByTagName("condition");
			
			for (int i=0;i<conditionsNode.getLength();i++){
				
				Element conditionElement=(Element)conditionsNode.item(i);
				
				NodeList conditionNameNode=conditionElement.getElementsByTagName("name");
				Element  conditionNameElement=(Element)conditionNameNode.item(0);
				
				retVal=retVal+"?"+getCharacterDataFromElement(conditionNameElement)+","+conditionElement.getAttribute("id")+",condition";
			}
			
			
			
			
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}

	
	public static String getWorkflowElementTypeForOneDecomposition(String ElementID,Element processControlElements,Element layoutNetElement){
		String retVal="false";
		
		try {
			
			NodeList inputConditionNode=processControlElements.getElementsByTagName("inputCondition");
			Element  inputConditionElement=(Element)inputConditionNode.item(0);
			
			if (inputConditionElement.getAttribute("id").compareToIgnoreCase(ElementID)==0){
				retVal="inputCondition";
				return (retVal);
			}
			
			
		
			
			
			
			NodeList outputConditionNode=processControlElements.getElementsByTagName("outputCondition");
			Element  outputConditionElement=(Element)outputConditionNode.item(0);
		
			if (outputConditionElement.getAttribute("id").compareToIgnoreCase(ElementID)==0){
				retVal="outputCondition";
				return retVal; 
			}
			
			
			
			
			NodeList tasksNode=processControlElements.getElementsByTagName("task");
			for (int i=0;i<tasksNode.getLength();i++){
				
				Element taskElement=(Element)tasksNode.item(i);
				if (taskElement.getAttribute("id").compareToIgnoreCase(ElementID)==0){
					retVal="task";
					return retVal;
				}
			}
			
			
			
			
			
			
			
			
	
			NodeList conditionsNode=processControlElements.getElementsByTagName("condition");
			
			for (int i=0;i<conditionsNode.getLength();i++){
				Element conditionElement=(Element)conditionsNode.item(i);
				if (conditionElement.getAttribute("id").compareToIgnoreCase(ElementID)==0){
					retVal="condition";
					return retVal;
				}
				
			
			}
			
			
			
			
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}

	
	public static String getWorkflowElementsInformation(Element processControlElements,Element layoutNetElement){
		String retVal="false";
		int task=0;
		int condition=0;
		
		int orJoin=0;
		int orSplit=0;
		
		int andJoin=0;
		int andSplit=0;
		
		int xorJoin=0;
		int xorSplit=0;
		
		
		
		try {
			
			NodeList  processControlElementConditionNodes=processControlElements.getElementsByTagName("condition");
			condition=processControlElementConditionNodes.getLength()+2;
			
			NodeList  processControlElementTaskNodes=processControlElements.getElementsByTagName("task");
			task=processControlElementTaskNodes.getLength();
			

			NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
			for(int i=0; i<containerNodes.getLength();i++){
				Element containerElement=(Element)containerNodes.item(i);
				
				NodeList decoratorNodes=containerElement.getElementsByTagName("decorator");
				if (decoratorNodes.getLength()==2){
					 Element firstDecorator=(Element)decoratorNodes.item(0);
					 Element secondDecorator=(Element)decoratorNodes.item(1);
					 String firstDecoratorType=firstDecorator.getAttribute("type");
					 String secondDecoratorType=secondDecorator.getAttribute("type"); 
					 
					 if (firstDecoratorType.compareToIgnoreCase("OR_join")==0){
						 orJoin++;
					 }else  if (firstDecoratorType.compareToIgnoreCase("XOR_join")==0){
						 xorJoin++;
					 }else  if (firstDecoratorType.compareToIgnoreCase("AND_join")==0){
						 andJoin++; 
					 } else  if (firstDecoratorType.compareToIgnoreCase("OR_split")==0){
						 orSplit++;
						 
					 }else if (firstDecoratorType.compareToIgnoreCase("XOR_split")==0){
						 xorSplit++;
					 }else if (firstDecoratorType.compareToIgnoreCase("AND_split")==0){
						 andSplit++;
					 }
					 
					 
					 
					 if (secondDecoratorType.compareToIgnoreCase("OR_join")==0){
						 orJoin++;
					 }else  if (secondDecoratorType.compareToIgnoreCase("XOR_join")==0){
						 xorJoin++;
					 }else  if (secondDecoratorType.compareToIgnoreCase("AND_join")==0){
						 andJoin++; 
					 } else  if (secondDecoratorType.compareToIgnoreCase("OR_split")==0){
						 orSplit++;
						 
					 }else if (secondDecoratorType.compareToIgnoreCase("XOR_split")==0){
						 xorSplit++;
					 }else if (secondDecoratorType.compareToIgnoreCase("AND_split")==0){
						 andSplit++;
					 }
					 
					 
					 
				}else if (decoratorNodes.getLength()==1){
					 Element firstDecorator=(Element)decoratorNodes.item(0);
					 String firstDecoratorType=firstDecorator.getAttribute("type");
					
					 
					 if (firstDecoratorType.compareToIgnoreCase("OR_join")==0){
						 orJoin++;
					 }else  if (firstDecoratorType.compareToIgnoreCase("XOR_join")==0){
						 xorJoin++;
					 }else  if (firstDecoratorType.compareToIgnoreCase("AND_join")==0){
						 andJoin++; 
					 } else  if (firstDecoratorType.compareToIgnoreCase("OR_split")==0){
						 orSplit++;
						 
					 }else if (firstDecoratorType.compareToIgnoreCase("XOR_split")==0){
						 xorSplit++;
					 }else if (firstDecoratorType.compareToIgnoreCase("AND_split")==0){
						 andSplit++;
					 }
					 
				}
				
			}
			
			
			
			retVal="Tasks:"+String.valueOf(task)+";"+"Conditions:"+String.valueOf(condition);
			retVal=retVal+";"+"OR-Join:"+String.valueOf(orJoin)+";"+"OR-Split:"+String.valueOf(orSplit);
			retVal=retVal+";"+"XOR-Join:"+String.valueOf(xorJoin)+";"+"XOR-Split:"+String.valueOf(xorSplit);
			retVal=retVal+";"+"AND-Join:"+String.valueOf(andJoin)+";"+"AND-Split:"+String.valueOf(andSplit);
			
			
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}


	public static String buildTaskCheckForOneDecomposition(String baseFilePath, String transformedFilePath,String taskName, String conditionName , Document transformedDoc , Element processControlElements,Element layoutNetElement){
		String retVal="false";
		
		try {
			
			String lastElementID=findElementsLastID(transformedFilePath);
			String taskID=getWorkflowElementIDFromName(transformedFilePath, taskName);
			String conditionID=getWorkflowElementIDFromName(transformedFilePath, conditionName);
			
			String predecessors=getPredecessors(transformedFilePath, getWorkflowElementIDFromName(transformedFilePath, taskName));
			String successors=getSuccessors(transformedFilePath, getWorkflowElementIDFromName(transformedFilePath, taskName));

			
			String tSplit=getTaskSplitType(transformedFilePath, getWorkflowElementIDFromName(transformedFilePath, taskName));
			String tJoin=getTaskJoinType(transformedFilePath, getWorkflowElementIDFromName(transformedFilePath, taskName));
			
		
			
    		// calculate elements' ID
    		String enabledConditionID="enabled_"+fillSpaceWithUnderline(taskName)+"_"+lastElementID;
    		String activeConditionID="active_"+fillSpaceWithUnderline(taskName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+1);
    		String disabledConditionID="disabled_"+fillSpaceWithUnderline(taskName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+2);
    		String checkConditionID="check_"+fillSpaceWithUnderline(conditionName)+"_"+fillSpaceWithUnderline(taskName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+3);
    		String runTaskID="run_"+fillSpaceWithUnderline(taskName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+4);
    		String proceedTaskID="proceed_"+fillSpaceWithUnderline(taskName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+5);
    		String validateTaskID="validate_"+fillSpaceWithUnderline(taskName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+6);
    		String disableTaskID="disable_"+fillSpaceWithUnderline(taskName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+7);
    		String endTaskID="end_"+fillSpaceWithUnderline(conditionName)+"_"+fillSpaceWithUnderline(taskName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+8);

    		
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
    		Element proceedTaskElement=createWorkflowTask(transformedDoc, proceedTaskID, "proceed_"+taskName, "xor", tSplit.toLowerCase(), nextElementRefsID);
    		processControlElements.appendChild(proceedTaskElement);
	
       		// add validate task
    		nextElementRefsID=endTaskID+","+activeConditionID;
    		Element validateTaskElement=createWorkflowTask(transformedDoc, validateTaskID, "validate_"+taskName, "and", "and", nextElementRefsID);
    		processControlElements.appendChild(validateTaskElement);

    		
       		// add disable task
    		nextElementRefsID=disabledConditionID+","+endTaskID;
    		Element disableTaskElement=createWorkflowTask(transformedDoc, disableTaskID, "disable_"+taskName, "and", "and", nextElementRefsID);
    		processControlElements.appendChild(disableTaskElement);
		
    		// tokens: for disable_t
			Element removesTokensDisableTaskElement=transformedDoc.createElement("removesTokens");
			removesTokensDisableTaskElement.setAttribute("id", disableTaskID);
			
			Element removesTokensDisabledTaskElement=transformedDoc.createElement("removesTokens");
			removesTokensDisabledTaskElement.setAttribute("id", disabledConditionID);
			
			Element removesTokensEnableElement=transformedDoc.createElement("removesTokens");
			removesTokensEnableElement.setAttribute("id", enabledConditionID);
			
			
			
			
			disableTaskElement.appendChild(removesTokensDisableTaskElement);
			disableTaskElement.appendChild(removesTokensEnableElement);
			disableTaskElement.appendChild(removesTokensDisabledTaskElement);
			
			
			// tokens: for validate_t
			Element removeTokensValidateTaskElement=transformedDoc.createElement("removesTokens");
			removeTokensValidateTaskElement.setAttribute("id", validateTaskID);
			
			Element removesTokensActiveTaskElement=transformedDoc.createElement("removesTokens");
			removesTokensActiveTaskElement.setAttribute("id", activeConditionID);
			
			validateTaskElement.appendChild(removeTokensValidateTaskElement);
			validateTaskElement.appendChild(removesTokensActiveTaskElement);
    		
    		// add end  task
    		nextElementRefsID=getWorkflowElementIDFromName(transformedFilePath, "run_"+conditionName);
    		Element endTaskElement=createWorkflowTask(transformedDoc, endTaskID, "end_"+conditionName+"_"+taskName, "xor", "and", nextElementRefsID);
    		processControlElements.appendChild(endTaskElement);
    		
    		
    		
    		
    		// change predecessors link from task to run_t
    		String[] predecessorsList=predecessors.split("\\,");
    		
    		for(int i=0; i<predecessorsList.length;i++){
    			
    		
    			NodeList inputConditionNode=processControlElements.getElementsByTagName("inputCondition");
    			Element  inputConditionElement=(Element)inputConditionNode.item(0);
    			if(inputConditionElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
					NodeList flowsIntoNode=inputConditionElement.getElementsByTagName("flowsInto");
					
					if (flowsIntoNode.getLength()==0){
						
						Element  newFlowsIntoElement =transformedDoc.createElement("flowsInto");
		        		Element  newNextElementRefElement=transformedDoc.createElement("nextElementRef");
		        		newNextElementRefElement.setAttribute("id", runTaskID);
		        		newFlowsIntoElement.appendChild(newNextElementRefElement);
		        		inputConditionElement.appendChild(newFlowsIntoElement);
						
						
					}else{
						for (int j=0;j<flowsIntoNode.getLength();j++){
							Element  flowsIntoElement=(Element)flowsIntoNode.item(j);
							NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
							Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
							
							if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
								nextElementRefElement.setAttribute("id", runTaskID);
							}
						}
					}
					
					
					
					

    			}
    			
    			

    			NodeList  processControlElementConditionNodes=processControlElements.getElementsByTagName("condition");
    			for(int j=0;j<processControlElementConditionNodes.getLength();j++){
    				Element childElement=(Element)processControlElementConditionNodes.item(j);
    				if (childElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
    					
    					NodeList flowsIntoNode=childElement.getElementsByTagName("flowsInto");
    					
    					if (flowsIntoNode.getLength()==0){

    						Element  newFlowsIntoElement =transformedDoc.createElement("flowsInto");
    		        		Element  newNextElementRefElement=transformedDoc.createElement("nextElementRef");
    		        		newNextElementRefElement.setAttribute("id", runTaskID);
    		        		newFlowsIntoElement.appendChild(newNextElementRefElement);
    		        		childElement.appendChild(newFlowsIntoElement);
    					}else{
    						for (int k=0;k<flowsIntoNode.getLength();k++){
    							Element  flowsIntoElement=(Element)flowsIntoNode.item(k);
    	    					
    	    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
    	    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
    	    					
    	    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
    	    						nextElementRefElement.setAttribute("id", runTaskID);
    	    					}
    						}
    					}
    					
    					
    				}
    			}
    			
    			
    			NodeList  processControlElementTaskNodes=processControlElements.getElementsByTagName("task");
    			for(int j=0;j<processControlElementTaskNodes.getLength();j++){
    				Element childElement=(Element)processControlElementTaskNodes.item(j);
    				if (childElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){
    					
    					NodeList flowsIntoNode=childElement.getElementsByTagName("flowsInto");
    					
    					if (flowsIntoNode.getLength()==0){
    						Element  newFlowsIntoElement =transformedDoc.createElement("flowsInto");
    		        		Element  newNextElementRefElement=transformedDoc.createElement("nextElementRef");
    		        		newNextElementRefElement.setAttribute("id", runTaskID);
    		        		newFlowsIntoElement.appendChild(newNextElementRefElement);
    		        		childElement.appendChild(newFlowsIntoElement);
    					}else{
    						for (int k=0;k<flowsIntoNode.getLength();k++){
    							Element  flowsIntoElement=(Element)flowsIntoNode.item(k);
    	    					
    	    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
    	    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
    	    					
    	    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
    	    						nextElementRefElement.setAttribute("id", runTaskID);
    	    					}
    						}
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
	    					
    						for (int k=0;k<flowsIntoNode.getLength();k++){
    							Element  flowsIntoElement=(Element)flowsIntoNode.item(k);
    							
    	    					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
    	    					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
    	    					
    	    					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(successorList[j])==0){
    	    						taskElement.removeChild(flowsIntoElement);
    	    					}
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
    				
    				
    				
    				// tokens: for task
    				Element removesTokensTaskElement=transformedDoc.createElement("removesTokens");
    				removesTokensTaskElement.setAttribute("id", taskID);
    				
    				Element removesTokensDeadElement=transformedDoc.createElement("removesTokens");
    				removesTokensDeadElement.setAttribute("id", getWorkflowElementIDFromName(transformedFilePath, "dead_"+conditionName));
    				
    				Element removesTokensDisableElement=transformedDoc.createElement("removesTokens");
    				removesTokensDisableElement.setAttribute("id", disabledConditionID);
    				
    				
    				taskElement.appendChild(removesTokensTaskElement);
    				taskElement.appendChild(removesTokensDeadElement);
    				taskElement.appendChild(removesTokensDisableElement);

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

    		
    		
    		
			

    		String minY=getWorkflowMinY(transformedDoc);
			shiftDownWorkflowElements(transformedDoc, "200.0", incrementContainerBounds(minY, "-1"));
			
			minY=getWorkflowMinY(transformedDoc);
			String taskXY=getWorkflowElementContainerAttributeXY(transformedDoc, taskID);

			String taskX=taskXY.split("\\,")[0];
			String taskY=taskXY.split("\\,")[1];

			
			
			
			//change the task decorator
			
			NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
			for (int i=0;i<containerNodes.getLength();i++){
				Element containerElement=(Element)containerNodes.item(i);
				if(containerElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
					
					NodeList decoratorNodes=containerElement.getElementsByTagName("decorator");
					if (decoratorNodes.getLength()==0){

						createTaskDecorator(transformedDoc, containerElement, taskID, taskX, taskY, "AND_join", "AND_split");
						
					}else if (decoratorNodes.getLength()==1){
						

							Element decoratorElement=(Element)decoratorNodes.item(0);
							containerElement.removeChild(decoratorElement);

							createTaskDecorator(transformedDoc, containerElement, taskID, taskX, taskY, "AND_join", "AND_split");

					}else if (decoratorNodes.getLength()==2){
							Element decoratorElement0=(Element)decoratorNodes.item(0);
							Element decoratorElement1=(Element)decoratorNodes.item(1);
							containerElement.removeChild(decoratorElement0);
							containerElement.removeChild(decoratorElement1);


							createTaskDecorator(transformedDoc, containerElement, taskID, taskX, taskY, "AND_join", "AND_split");
						}
					
					}
					
				}
			
			
			
			
			
			//run task
			String runTaskX=incrementContainerBounds(taskX, "-100");
			String runTaskY=incrementContainerBounds(minY, "-100");
			
			
			String runTaskJoinType="";
			if (tJoin==""){
				runTaskJoinType="";
			}else{
				runTaskJoinType=tJoin.toUpperCase()+"_join";
			}
			
			Element runTaskContainerElement=createTaskContainer(transformedDoc, runTaskID, runTaskX, runTaskY, runTaskJoinType, "");
			
			layoutNetElement.appendChild(runTaskContainerElement);
			
			
			//enabled condition
			String enabledConditionX=runTaskX;
			String enabledConditionY=incrementContainerBounds(runTaskY, "-100");
			
			Element enabledConditionContainer=createConditionContainer(transformedDoc, enabledConditionID, enabledConditionX, enabledConditionY);
			layoutNetElement.appendChild(enabledConditionContainer);
			
			// disabled condition
			String disabledConditionX=taskX;
			String disabledConditionY=incrementContainerBounds(enabledConditionY, "50");
			
			Element disabledConditionContainerElement=createConditionContainer(transformedDoc, disabledConditionID, disabledConditionX, disabledConditionY);
			layoutNetElement.appendChild(disabledConditionContainerElement);
			
		
			// proceed task
			String processedTaskX=incrementContainerBounds(taskX, "50");
			String processedTaskY=runTaskY;
			
			
			String proceedTaskSplitType="";
			if (tSplit==""){
				proceedTaskSplitType="";
			}else{
				proceedTaskSplitType=tSplit.toUpperCase()+"_split";
			}
			
			Element processedTaskContainerElement=createTaskContainer(transformedDoc, proceedTaskID, processedTaskX, processedTaskY, "", proceedTaskSplitType);
			layoutNetElement.appendChild(processedTaskContainerElement);
			
			
			// active condition
			String activeConditionX=processedTaskX;
			String activeConditionY=incrementContainerBounds(processedTaskY, "-100");
			
			Element activeConditionContainer=createConditionContainer(transformedDoc, activeConditionID, activeConditionX, activeConditionY);
			layoutNetElement.appendChild(activeConditionContainer);
			
			//check condition
			String checkConditionX=incrementContainerBounds(processedTaskX, "100");
			String checkConditionY=incrementContainerBounds(processedTaskY, "-50");
			
			Element checkConditionContainerElement=createConditionContainer(transformedDoc, checkConditionID, checkConditionX, checkConditionY);
			layoutNetElement.appendChild(checkConditionContainerElement);
			
			//disable task
			String disableTaskX=incrementContainerBounds(checkConditionX, "50");
			String disableTaskY=incrementContainerBounds(checkConditionY, "50");
			
			Element disableTaskContainerElement=createTaskContainer(transformedDoc, disableTaskID, disableTaskX, disableTaskY, "AND_join", "AND_split");
			
			addBackgroundColorToContainerElement(transformedDoc, disableTaskContainerElement, "-4144960");
			
			
			layoutNetElement.appendChild(disableTaskContainerElement);
			
			//validate task
			String validateTaskX=incrementContainerBounds(checkConditionX, "50");
			String validateTaskY=incrementContainerBounds(checkConditionY, "-50");
			
			Element validateTaskContainerElement=createTaskContainer(transformedDoc, validateTaskID, validateTaskX, validateTaskY, "AND_join", "AND_split");
			layoutNetElement.appendChild(validateTaskContainerElement);
			
			//end task
			String endTaskX=incrementContainerBounds(checkConditionX, "100");
			String endTaskY=checkConditionY;
			
			Element endTaskContainerElement=createTaskContainer(transformedDoc, endTaskID, endTaskX, endTaskY, "XOR_join", "");
			layoutNetElement.appendChild(endTaskContainerElement);

			

			//flows
			
			//(run , task)
			Element  run_task_layoutNetFlowElement=null;
//			if (getTaskJoinType(transformedFilePath, taskID)==""){
//				  run_task_layoutNetFlowElement=createFlowElement(transformedDoc, runTaskID, taskID, "13", "12");
//			}else{
//				  run_task_layoutNetFlowElement=createFlowElement(transformedDoc, runTaskID, taskID, "13", "2");
//			}
			
			 run_task_layoutNetFlowElement=createFlowElement(transformedDoc, runTaskID, taskID, "13", "2");
			
			layoutNetElement.appendChild(run_task_layoutNetFlowElement);
			
			
			//(enabled , task)
			Element enabled_task_layoutNetFlowElement=null;
//			if (getTaskJoinType(transformedFilePath, taskID)==""){
//				enabled_task_layoutNetFlowElement=createFlowElement(transformedDoc, enabledConditionID, taskID, "13", "12");
//			}else{
//				enabled_task_layoutNetFlowElement=createFlowElement(transformedDoc, enabledConditionID, taskID, "13", "2");
//			}
			
			enabled_task_layoutNetFlowElement=createFlowElement(transformedDoc, enabledConditionID, taskID, "13", "2");

			
			layoutNetElement.appendChild(enabled_task_layoutNetFlowElement);

			
			//(task, enabled)
			
			Element task_enabled_layoutNetFlowElement=null;
			
//			if (getTaskSplitType(transformedFilePath, taskID)==""){
//				task_enabled_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, enabledConditionID, "13", "13");
//			}else{
//				task_enabled_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, enabledConditionID, "2", "13");
//				
//			}
			
			task_enabled_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, enabledConditionID, "2", "13");
			
			layoutNetElement.appendChild(task_enabled_layoutNetFlowElement);
			
			
			//(task, active)
			Element  task_active_layoutNetFlowElement=null;
//			if (getTaskSplitType(transformedFilePath, taskID)==""){
//				task_active_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, activeConditionID, "13", "12");
//			}else{
//				task_active_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, enabledConditionID, "2", "12");
//				
//			}
			
			task_active_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, enabledConditionID, "2", "12");

			
			layoutNetElement.appendChild(task_active_layoutNetFlowElement);
			
			
			// (active , validate)
			
			Element active_validate_layoutNetFlowElement=createFlowElement(transformedDoc, activeConditionID, validateTaskID, "13", "2");
			layoutNetElement.appendChild(active_validate_layoutNetFlowElement);
			
			//(validate, active)
			Element validate_active_layoutNetFlowElement=createFlowElement(transformedDoc, validateTaskID, activeConditionID, "2", "10");
			layoutNetElement.appendChild(validate_active_layoutNetFlowElement);
			
			//(validate, end)
			
			Element validate_end_layoutNetFlowElement=createFlowElement(transformedDoc, validateTaskID, endTaskID, "2", "2");
			layoutNetElement.appendChild(validate_end_layoutNetFlowElement);
			
			//(check,validate)
			
			Element check_validate_layoutNetFlowElement=createFlowElement(transformedDoc, checkConditionID, validateTaskID, "13", "2");
			layoutNetElement.appendChild(check_validate_layoutNetFlowElement);
			
			//(check,disable)
			Element check_disable_layoutNetFlowElement=createFlowElement(transformedDoc, checkConditionID, disableTaskID, "13", "2");
			layoutNetElement.appendChild(check_disable_layoutNetFlowElement);
			
			//(disable,end)
			Element disable_end_layoutNetFlowElement=createFlowElement(transformedDoc, disableTaskID, endTaskID, "2", "2");
			layoutNetElement.appendChild(disable_end_layoutNetFlowElement);
			
			
			//(task,proceed)
			Element task_proceed_layoutNetFlowElement=null;
//			if (getTaskSplitType(transformedFilePath, taskID)==""){
//				
//				task_proceed_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, proceedTaskID, "13", "12");
//			}else{
//			
//
//				task_proceed_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, proceedTaskID, "2", "12");
//
//			}
			
			task_proceed_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, proceedTaskID, "2", "12");

			layoutNetElement.appendChild(task_proceed_layoutNetFlowElement);


			
			
			//(disabled,disable)
			Element disabled_disable_layoutNetFlowElement=createFlowElement(transformedDoc, disabledConditionID, disableTaskID, "13", "2");
			layoutNetElement.appendChild(disabled_disable_layoutNetFlowElement);
			
			//(disable,disabled)
			Element disable_disabled_layoutNetFlowElement=createFlowElement(transformedDoc, disableTaskID, disabledConditionID, "2", "13");
			layoutNetElement.appendChild(disable_disabled_layoutNetFlowElement);
			
			//(init,disabled)
			
			String initID=getWorkflowElementIDFromName(transformedFilePath, "init");
			
			Element init_disabled_layoutNetFlowElement=createFlowElement(transformedDoc, initID, disabledConditionID, "2", "12");
			layoutNetElement.appendChild(init_disabled_layoutNetFlowElement);
			
			//(init, enabled)
			Element init_enabled_layoutNetFlowElement=createFlowElement(transformedDoc, initID, enabledConditionID, "2", "12");
			layoutNetElement.appendChild(init_enabled_layoutNetFlowElement);
			

			
			
			//(task,active_task)
			Element task_activeTask_layoutNetFlowElement=null;
			
//			
//			if(tSplit==""){
//				task_activeTask_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, getWorkflowElementIDFromName(transformedFilePath, "active_task_"+conditionName), "13", "12");
//
//			}else{
//				task_activeTask_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, getWorkflowElementIDFromName(transformedFilePath, "active_task_"+conditionName), "2", "12");
//
//			}
			
			task_activeTask_layoutNetFlowElement=createFlowElement(transformedDoc, taskID, getWorkflowElementIDFromName(transformedFilePath, "active_task_"+conditionName), "2", "12");

			
			layoutNetElement.appendChild(task_activeTask_layoutNetFlowElement);
			
			
			
			//(sync,check)
			
			Element sync_check_layoutNetFlowElement=createFlowElement(transformedDoc, getWorkflowElementIDFromName(transformedFilePath, "sync_"+conditionName), checkConditionID, "2", "12");
			layoutNetElement.appendChild(sync_check_layoutNetFlowElement);
			
			
			
			//(end,run)
			Element  end_run_layoutNetFlowElement=createFlowElement(transformedDoc, endTaskID, getWorkflowElementIDFromName(transformedFilePath, "run_"+conditionName), "13", "2");
			layoutNetElement.appendChild(end_run_layoutNetFlowElement);

			
		
			
			
			
		
			
			
			String deadConditionID=getWorkflowElementIDFromName(transformedFilePath, "dead_"+conditionName);
			
			
			
			for (int i=0;i<containerNodes.getLength();i++){
				Element containerElement=(Element)containerNodes.item(i);
				if(containerElement.getAttribute("id").compareToIgnoreCase(taskID)==0){
					addBackgroundColorToContainerElement(transformedDoc, containerElement, "-4144960");
				}
				
				if(containerElement.getAttribute("id").compareToIgnoreCase(deadConditionID)==0){
					
					addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
					
					
				}
				
				if(containerElement.getAttribute("id").compareToIgnoreCase(disabledConditionID)==0){
					addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
				}
				
			
				
				if(containerElement.getAttribute("id").compareToIgnoreCase(enabledConditionID)==0){
					addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
				}
				
				if(containerElement.getAttribute("id").compareToIgnoreCase(validateTaskID)==0){
					addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
				}
				
				if(containerElement.getAttribute("id").compareToIgnoreCase(disableTaskID)==0){
					addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
				}
				
				if(containerElement.getAttribute("id").compareToIgnoreCase(disabledConditionID)==0){
					addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
				}
				
				if(containerElement.getAttribute("id").compareToIgnoreCase(activeConditionID)==0){
					addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
				}
				
			}
			
			
			// change predecessors link from task to run_t
    		predecessorsList=predecessors.split("\\,");
    		for(int i=0; i<predecessorsList.length;i++){
    			
    			
    			NodeList flowNodes=layoutNetElement.getElementsByTagName("flow");
    			for (int j=0;j<flowNodes.getLength();j++){
    				Element flowElement=(Element)flowNodes.item(j);
    				if((flowElement.getAttribute("source").compareToIgnoreCase(predecessorsList[i])==0) && (flowElement.getAttribute("target").compareToIgnoreCase(taskID)==0)){

    					
    					layoutNetElement.removeChild(flowElement);
    					
    					Element newFlowElement=null;
    					
    					if (tJoin==""){
    						if(getTaskSplitType(transformedFilePath, predecessorsList[i])==""){
    							newFlowElement=createFlowElement(transformedDoc, predecessorsList[i], runTaskID, "13", "12");

    						}else{
    							newFlowElement=createFlowElement(transformedDoc, predecessorsList[i], runTaskID, "2", "12");

    						}
    					}else{
    						if(getTaskSplitType(transformedFilePath, predecessorsList[i])==""){
    							newFlowElement=createFlowElement(transformedDoc, predecessorsList[i], runTaskID, "13", "2");


    						}else{
    							newFlowElement=createFlowElement(transformedDoc, predecessorsList[i], runTaskID, "2", "2");


    						}
    					}
    					
    					layoutNetElement.appendChild(newFlowElement);
    					
    				
    					
    					
    					
    				}
    			}
    			
    		}
    		
    		
    		
    		
    		if (successors!=""){
    			String[] successorList=successors.split("\\,");
    			
    			for(int i=0;i<successorList.length;i++){
    				
    				NodeList flowNodes=layoutNetElement.getElementsByTagName("flow");
	    			for(int j=0;j<flowNodes.getLength();j++){
	    				
	    				Element flowElement=(Element)flowNodes.item(j);
	    				
	    				
	    				if((flowElement.getAttribute("source").compareToIgnoreCase(taskID)==0) && (flowElement.getAttribute("target").compareToIgnoreCase(successorList[i])==0)){
	    				
	    					layoutNetElement.removeChild(flowElement);
	    					
	    				
	    					
	    					Element newFlowElement=null;
	    					
	    					if(tSplit==""){
	    						if(getTaskJoinType(transformedFilePath, successorList[i])==""){
	    							newFlowElement=createFlowElement(transformedDoc, proceedTaskID, successorList[i], "13", "12");
	    							
	    						}else{
	    							newFlowElement=createFlowElement(transformedDoc, proceedTaskID, successorList[i], "13", "2");
	    						}
	    						
	    						
	    					}else{
	    						if(getTaskJoinType(transformedFilePath, successorList[i])==""){
	    							newFlowElement=createFlowElement(transformedDoc, proceedTaskID, successorList[i], "2", "12");
	    							
	    						}else{
	    							newFlowElement=createFlowElement(transformedDoc, proceedTaskID, successorList[i], "2", "2");
	    						}
	    					}
	    					
	    					layoutNetElement.appendChild(newFlowElement);
	    					
	    				}
	    				
	    			}
    			}
    			
    			
    			
    		}
    	


			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}

	public static String buildTaskCheck(String baseFilePath, String transformedFilePath,String taskName, String conditionName){
	String retVal="false";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		   				
		   				
		   				retVal=buildTaskCheckForOneDecomposition(baseFilePath, transformedFilePath, taskName, conditionName, transformedDoc, processControlElements, layoutNetElement);
		   				
		   						   					
	    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
	    			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildTaskCheckForOneDecomposition(baseFilePath, transformedFilePath, taskName, conditionName, transformedDoc, processControlElements, layoutNetElement);
		    				
		    				
	    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
	    			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;
	}
	
	
	public static Boolean checkIfTaskIncludesremovesTokens(String transformedFilePath, String ElementID, String removesTokensElementID){
		Boolean retVal=false;
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=checkIfTaskIncludesremovesTokensForOneDecomposition(transformedFilePath, ElementID, removesTokensElementID, processControlElements);
		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=checkIfTaskIncludesremovesTokensForOneDecomposition(transformedFilePath, ElementID, removesTokensElementID, processControlElements);
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
			retVal=false;
		}

			
			return retVal;
	}
	
	
	
	public static Boolean checkIfTaskIncludesremovesTokensForOneDecomposition(String transformedFilePath, String ElementID, String removesTokensElementID, Element processControlElements){
		Boolean retVal=false;
		Boolean taskFound=false;
		Boolean tokenFound=false;
		
		try {
		
	    		NodeList  taskNodes=processControlElements.getElementsByTagName("task");
	    		for (int i=0; i<taskNodes.getLength() && !taskFound;i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			
	    			if(taskElement.getAttribute("id").compareToIgnoreCase(ElementID)==0){
	    				taskFound=true;
	    				
	    				NodeList removesTokensNodes=taskElement.getElementsByTagName("removesTokens");
	    				for(int j=0;j<removesTokensNodes.getLength() && !tokenFound;j++){
	    					Element removesTokensElemen=(Element)removesTokensNodes.item(j);
	    					if(removesTokensElemen.getAttribute("id").compareToIgnoreCase(removesTokensElementID)==0){
	    						tokenFound=true;
	    					}
	    				}
	    				
	    			}
	    		}
	    		
	    	
		} catch (Exception e) {
			retVal=false;
		}
		
		
		if(!taskFound){
			retVal=false;
		}else{
			if(tokenFound){
				retVal=true;
			}else{
				retVal=false;
			}
		}
		
		return retVal;
	}
	
	
	public static String buildCleanForStops(String baseFilePath, String transformedFilePath, String conditionName){
	
		
		
		
		
		String retVal="false";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildCleanForStopsForOneDecomposition(baseFilePath, transformedFilePath, conditionName, transformedDoc, processControlElements, layoutNetElement);
		    				
		   			  	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
		   			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildCleanForStopsForOneDecomposition(baseFilePath, transformedFilePath, conditionName, transformedDoc, processControlElements, layoutNetElement);
		    				
		   			  	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
		   			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;
		
		
		
			
		
	
		
	}
	
	
	public static String buildCleanForStopsForOneDecomposition(String baseFilePath, String transformedFilePath, String conditionName,Document transformedDoc, Element processControlElements, Element layoutNetElement){
		String retVal="false";
		
		try {
			
			
	    		
	    		
	    		NodeList  taskNodes=processControlElements.getElementsByTagName("task");
	    		
	    		Boolean found=false;
	    		String exitTaskID=getWorkflowElementIDFromName(transformedFilePath, "exit");
	    		String activeConditionID=getWorkflowElementIDFromName(transformedFilePath, "active_"+conditionName);
	    		String activeTaskConditionID=getWorkflowElementIDFromName(transformedFilePath, "active_task_"+conditionName);
	    		String checkOutTaskID=getWorkflowElementIDFromName(transformedFilePath, "check_out_"+conditionName);
	    		String checkActiveConditionID=getWorkflowElementIDFromName(transformedFilePath, "check_active_"+conditionName);
	    		String deadConditionID=getWorkflowElementIDFromName(transformedFilePath, "dead_"+conditionName);
	    		String proceedTaskID=getWorkflowElementIDFromName(transformedFilePath, "proceed_"+conditionName);
	    		String endTaskID=getWorkflowElementIDFromName(transformedFilePath, "end_"+conditionName);
	    		String exitConditionTaskID=getWorkflowElementIDFromName(transformedFilePath, "exit_"+conditionName);
	    		
	    		
	    		
	    		for (int i=0; i<taskNodes.getLength() && !found;i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			
	    			if(taskElement.getAttribute("id").compareToIgnoreCase(exitTaskID)==0){
	    				
	    				// tokens
	    				
	    				
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, exitTaskID)){
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", exitTaskID);
		    				taskElement.appendChild(removesTokensElement);
	    				}
	    				
	    			
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, activeConditionID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", activeConditionID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				

	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, activeTaskConditionID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", activeTaskConditionID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, checkOutTaskID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", checkOutTaskID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, checkActiveConditionID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", checkActiveConditionID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				
	    				
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, deadConditionID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", deadConditionID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				
	    				
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, proceedTaskID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", proceedTaskID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				
	    				
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, endTaskID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", endTaskID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				

	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, exitConditionTaskID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", exitConditionTaskID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    			}
	    		}
	    		
	    		
	    	
	    		
	    				
	    				
	    				Element cancellationTaskElement=transformedDoc.createElement("cancellationtask");
	    				cancellationTaskElement.appendChild(transformedDoc.createTextNode(exitTaskID));
	    				layoutNetElement.appendChild(cancellationTaskElement);
	    				
	    				
	    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
	    				for(int i=0;i<containerNodes.getLength();i++){
	    					
	    					Element containerElement=(Element)containerNodes.item(i);
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(exitTaskID)==0){
	    						addBackgroundColorToContainerElement(transformedDoc, containerElement, "-4144960");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(activeTaskConditionID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(checkOutTaskID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(activeConditionID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(checkActiveConditionID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(deadConditionID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(proceedTaskID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(endTaskID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(exitConditionTaskID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    				}
	    				
	    				
	    		
	    			
	    		retVal="true";

	    	
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
		
	}
	
	public static void addForegroundColorToContainerElement(Document doc,  Element containerElement, String colorString){
		NodeList vertexNodes=containerElement.getElementsByTagName("vertex");
		Element  vertexElement=(Element)vertexNodes.item(0);
		
		NodeList attributesNode=vertexElement.getElementsByTagName("attributes");
		Element  attributeElement=(Element)attributesNode.item(0);
		
		NodeList foregroundColorNode=attributeElement.getElementsByTagName("foregroundColor");
		if(foregroundColorNode.getLength()==0){
			Element foregroundColorElement=doc.createElement("foregroundColor");
			foregroundColorElement.appendChild(doc.createTextNode(colorString));
			attributeElement.appendChild(foregroundColorElement);
			
		}
		
		NodeList decoratorNodes=containerElement.getElementsByTagName("decorator");
		for (int i=0;i<decoratorNodes.getLength();i++){
			Element decoratorElement=(Element)decoratorNodes.item(i);
			
			NodeList decoratorAttributesNode=decoratorElement.getElementsByTagName("attributes");
			Element  decoratorAttributesElement=(Element)decoratorAttributesNode.item(0);
			
			NodeList decoratorForegroundColorNode=decoratorAttributesElement.getElementsByTagName("foregroundColor");
			if (decoratorForegroundColorNode.getLength()==0){
				
				Element decoratorForegroundColorElement=doc.createElement("foregroundColor");
				decoratorForegroundColorElement.appendChild(doc.createTextNode(colorString));
				decoratorAttributesElement.appendChild(decoratorForegroundColorElement);
			}
			
			
		}
		
		
	}
	
	
	public static void addBackgroundColorToContainerElement(Document doc,  Element containerElement, String colorString){
		NodeList vertexNodes=containerElement.getElementsByTagName("vertex");
		Element  vertexElement=(Element)vertexNodes.item(0);
		
		NodeList attributesNode=vertexElement.getElementsByTagName("attributes");
		Element  attributeElement=(Element)attributesNode.item(0);
		
		NodeList foregroundColorNode=attributeElement.getElementsByTagName("backgroundColor");
		if(foregroundColorNode.getLength()==0){
			Element foregroundColorElement=doc.createElement("backgroundColor");
			foregroundColorElement.appendChild(doc.createTextNode(colorString));
			attributeElement.appendChild(foregroundColorElement);
		}
		
		NodeList decoratorNodes=containerElement.getElementsByTagName("decorator");
		for (int i=0;i<decoratorNodes.getLength();i++){
			Element decoratorElement=(Element)decoratorNodes.item(i);
			
			NodeList decoratorAttributesNode=decoratorElement.getElementsByTagName("attributes");
			Element  decoratorAttributesElement=(Element)decoratorAttributesNode.item(0);
			
			NodeList decoratorForegroundColorNode=decoratorAttributesElement.getElementsByTagName("backgroundColor");
			if (decoratorForegroundColorNode.getLength()==0){
				
				Element decoratorForegroundColorElement=doc.createElement("backgroundColor");
				decoratorForegroundColorElement.appendChild(doc.createTextNode(colorString));
				decoratorAttributesElement.appendChild(decoratorForegroundColorElement);
			}
			
			
		}
		
		
	}
	
	public static String buildCleanForStartsForOneDecomposition(String baseFilePath, String transformedFilePath, String taskName,Document transformedDoc, Element   processControlElements, Element  layoutNetElement  ){
		String retVal="false";
		
		try {
			
	    		NodeList  taskNodes=processControlElements.getElementsByTagName("task");
	    		
	    		Boolean found=false;
	    		String exitTaskID=getWorkflowElementIDFromName(transformedFilePath, "exit");
	    		String disabledConditionID=getWorkflowElementIDFromName(transformedFilePath, "disabled_"+taskName);
	    		String enabledConditionID=getWorkflowElementIDFromName(transformedFilePath, "enabled_"+taskName);
	    		String activeConditionID=getWorkflowElementIDFromName(transformedFilePath, "active_"+taskName);
	    		
	    		
	    		
	    		for (int i=0; i<taskNodes.getLength() && !found;i++){
	    			Element taskElement=(Element)taskNodes.item(i);
	    			
	    			if(taskElement.getAttribute("id").compareToIgnoreCase(exitTaskID)==0){
	    				
	    				// tokens
	    				
	    				
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, exitTaskID)){
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", exitTaskID);
		    				taskElement.appendChild(removesTokensElement);
	    				}
	    				
	    			
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, disabledConditionID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", disabledConditionID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				

	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, enabledConditionID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", enabledConditionID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				
	    				if (!checkIfTaskIncludesremovesTokens(transformedFilePath, exitTaskID, activeConditionID)){
	    					
	    					Element  removesTokensElement=transformedDoc.createElement("removesTokens");
	    					removesTokensElement.setAttribute("id", activeConditionID);
		    				taskElement.appendChild(removesTokensElement);
	    					
	    				}
	    				
	    			
	    			}
	    		}
	    		
	    	
	    				
	    				NodeList containerNodes=layoutNetElement.getElementsByTagName("container");
	    				for(int i=0;i<containerNodes.getLength();i++){
	    					
	    					Element containerElement=(Element)containerNodes.item(i);
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(exitTaskID)==0){
	    						addBackgroundColorToContainerElement(transformedDoc, containerElement, "-4144960");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(disabledConditionID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(enabledConditionID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    					if (containerElement.getAttribute("id").compareToIgnoreCase(activeConditionID)==0){
	    						addForegroundColorToContainerElement(transformedDoc, containerElement, "-65536");
	    					}
	    					
	    				}
	    				
	    
	    	
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
		
	}
	
	public static String buildCleanForStarts(String baseFilePath, String transformedFilePath, String taskName  ){
	
	
		
		
		String retVal="false";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildCleanForStartsForOneDecomposition(baseFilePath, transformedFilePath, taskName, transformedDoc, processControlElements, layoutNetElement);
		   			  	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
		   			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildCleanForStartsForOneDecomposition(baseFilePath, transformedFilePath, taskName, transformedDoc, processControlElements, layoutNetElement);
		    				
		   			  	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
		   			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;
		  	
	    		
		
	}
	
	public static String buildConditionCheckForOneDecomposition( String baseFilePath, String transformedFilePath, String conditionName , Document transformedDoc , Element processControlElements,Element layoutNetElement){
		String retVal="false";
		
		try {
			
			String predecessors=getPredecessors(transformedFilePath, getWorkflowElementIDFromName(transformedFilePath, conditionName));
		
			String lastElementID=findElementsLastID(transformedFilePath);
			String conditionID=getWorkflowElementIDFromName(transformedFilePath, conditionName);
			
			
    		String subsConditionID="subs_"+fillSpaceWithUnderline(conditionName)+"_"+lastElementID;
			String syncTaskID="sync_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+1);
			String runTaskID="run_"+fillSpaceWithUnderline(conditionName)+"_"+String.valueOf(Integer.parseInt(lastElementID)+2);
			
			
			
    		// add subs  condition
			Element  subsConditionElement=createWorkflowConditionElement(transformedDoc, subsConditionID, "subs_"+conditionName, syncTaskID);
    	
			// add sync task
			Element syncTaskElement=createWorkflowTask(transformedDoc, syncTaskID, "sync_"+conditionName, "xor", "and", "");
				
	
			//add run task
			
    		Element runTaskElement=createWorkflowTask(transformedDoc, runTaskID, "run_"+conditionName, "and", "and", conditionID);
    		
    		
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
    					
    					for (int k=0;k<flowsIntoNode.getLength();k++){
    						Element  flowsIntoElement=(Element)flowsIntoNode.item(k);
    						NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
        					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
        					
        					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(conditionID)==0){
        						nextElementRefElement.setAttribute("id", subsConditionID);
        					}
    					}
    					
    					
    				
    					
    					
    				}
    			}
    			
    			
    			NodeList  processControlElementTaskNodes=processControlElements.getElementsByTagName("task");
    			for(int j=0;j<processControlElementTaskNodes.getLength();j++){
    				Element childElement=(Element)processControlElementTaskNodes.item(j);
    				if (childElement.getAttribute("id").compareToIgnoreCase(predecessorsList[i])==0){

    					NodeList flowsIntoNode=childElement.getElementsByTagName("flowsInto");
    					for (int k=0;k<flowsIntoNode.getLength();k++){
    						Element  flowsIntoElement=(Element)flowsIntoNode.item(k);
        					
        					NodeList nextElementRefNode=flowsIntoElement.getElementsByTagName("nextElementRef");
        					Element  nextElementRefElement=(Element)nextElementRefNode.item(0);
        					
        					if (nextElementRefElement.getAttribute("id").compareToIgnoreCase(conditionID)==0){
        						nextElementRefElement.setAttribute("id", subsConditionID);
        					}
    					}
    					
    					
    				}
    			}
    			
    			
			
    			
    		
    			
    		}
    		
    		processControlElements.appendChild(runTaskElement);
    		processControlElements.appendChild(syncTaskElement);
    		processControlElements.appendChild(subsConditionElement);


			//layout
			String maxY=getWorkflowMaxY(transformedDoc);
			
			
			
			String conditionXY=getWorkflowElementContainerAttributeXY(transformedDoc, conditionID);

			String conditionX=conditionXY.split("\\,")[0];
			String conditionY=conditionXY.split("\\,")[1];
			
			

			// subs condition
			
			String subsConditionX=conditionX;
			String subsConditionY=incrementContainerBounds(maxY, "100");

			
			Element newSubsContainerElement=createConditionContainer(transformedDoc, subsConditionID,subsConditionX,subsConditionY);
			layoutNetElement.appendChild(newSubsContainerElement);
			
			// sync task
			String syncTaskX=incrementContainerBounds(subsConditionX, "100");
			String syncTaskY=subsConditionY;

			
			Element syncContainerElement=createTaskContainer(transformedDoc, syncTaskID, syncTaskX, syncTaskY, "", "AND_split");
			layoutNetElement.appendChild(syncContainerElement);

			
			// run task
			String runTaskX=incrementContainerBounds(syncTaskX, "100");
			String runTaskY=syncTaskY;

			Element runContainerElement=createTaskContainer(transformedDoc, runTaskID, runTaskX, runTaskY, "AND_join", "AND_split");
			layoutNetElement.appendChild(runContainerElement);

			
			// flows
			
			//(subs, sync)
			Element   subs_SyncLayoutNetFlowElement=createFlowElement(transformedDoc, subsConditionID, syncTaskID, "13", "12");
			layoutNetElement.appendChild(subs_SyncLayoutNetFlowElement);
			
	
			//(run, condition)
			Element  run_ConditionLayoutNetFlowElement=createFlowElement(transformedDoc, runTaskID, conditionID, "2", "11");
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

    		 retVal="true";
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	
	public static String buildConditionCheck( String baseFilePath, String transformedFilePath, String conditionName){
		String retVal="false";
		
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
				Element   decompositionElement=(Element)decompositionNodes.item(0);
					

		    	NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
					
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		   				
		   				
		   				retVal=buildConditionCheckForOneDecomposition(baseFilePath, transformedFilePath, conditionName, transformedDoc, processControlElements, layoutNetElement);
	    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
	    			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
		    		
					
			}else{ // there are more decomposition nodes
				Element   decompositionElement=null;
				for (int i=0; i<decompositionNodes.getLength();i++){
					Element tmpDecompositionElement=(Element)decompositionNodes.item(i);
					if (tmpDecompositionElement.getAttribute("isRootNet").compareToIgnoreCase("true")==0){
						   decompositionElement=tmpDecompositionElement;
					}
				}
				
				NodeList  processControlNodes=decompositionElement.getElementsByTagName("processControlElements");
		   		Element   processControlElements=(Element)processControlNodes.item(0);
		   		
		   		NodeList  layoutNodes=specificationSetElement.getElementsByTagName("layout");
		   		Element   layoutElement=(Element)layoutNodes.item(0);
		    		
		   		NodeList  layoutSpecificationNodes=layoutElement.getElementsByTagName("specification");
		   		if (layoutSpecificationNodes.getLength()==1){
		    		Element layoutSpecificationElement=(Element)layoutSpecificationNodes.item(0);
		    			
		   			NodeList layoutNetNodes=layoutSpecificationElement.getElementsByTagName("net");
		   			if (layoutNetNodes.getLength()==1){
		   				Element  layoutNetElement=(Element)layoutNetNodes.item(0);
		    				
		   				retVal=buildConditionCheckForOneDecomposition(baseFilePath, transformedFilePath, conditionName, transformedDoc, processControlElements, layoutNetElement);

		    				
	    				TransformerFactory transformerFactory=TransformerFactory.newInstance();
		   			    Transformer transformer=transformerFactory.newTransformer();
		   			    DOMSource source=new DOMSource(transformedDoc);
		   			    StreamResult result=new StreamResult(transformedFile);
		   			    transformer.transform(source, result);
	    			    retVal="true";

		    				
	    			}else{// there are more layout net nodes
		    				
	    			}
		    			
		   		}else{ // there are more layout specification nodes
		    			
		   		}
				
				
			}	
			
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		    retVal="false";
		}

			
			return retVal;

	}
	
	
	
	
	public static String runTaskCheck(String baseFile, String transformedFilePath, String taskName, String stopName){
		String retVal="false";
		
		try {

			retVal=buildTaskCheck(baseFile, transformedFilePath, taskName, stopName);
					
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	
	public static String runBuildCleanForStarts(String baseFile, String transformedFilePath, String task){
		String retVal="false";
		
		try {
				retVal=buildCleanForStarts(baseFile, transformedFilePath,task);
					
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	
//	public static String runBuildCleanForStarts(String baseFile, String viewFilePath, String transformedFilePath){
//		String retVal="false";
//		
//		try {
//			
//			
//			String starts=getStarts(transformedFilePath, viewFilePath);
//			if (starts!=""){
//				String[] startList=starts.split("\\,");
//				
//				for (int i=0;i<startList.length;i++){
//					
//					String workflowName=getWorkflowName(transformedFilePath);
//					String stopName=getStopOfStart(viewFilePath, startList[i], workflowName);
//					
//					retVal=buildCleanForStarts(baseFile, transformedFilePath, startList[i]);
//					
//					
//					
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			retVal="false";
//		}
//		
//		return retVal;
//	}
	
	
	
	
	
	public static String runConditionsCheck(String baseFile,String transformedFilePath, String stop){
		String retVal="false";
		
		try {
			
			
			
				retVal=buildConditionCheck(baseFile, transformedFilePath,stop);
				
			
				if(retVal.compareTo("true")==0){
					retVal=buildPresenceCheck(baseFile, transformedFilePath, stop);
				}
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	
//	public static String runConditionsCheck(String baseFile, String viewFilePath, String transformedFilePath){
//		String retVal="false";
//		
//		try {
//			
//			
//			String stops=getStops(transformedFilePath, viewFilePath);
//			
//			if (stops!=""){
//				String[] stopList=stops.split("\\,");
//			
//				for (int i=0;i<stopList.length;i++){
//					
//					retVal=buildConditionCheck(baseFile, transformedFilePath, stopList[i]);
//					if(retVal.compareTo("true")==0){
//						retVal=buildPresenceCheck(baseFile, transformedFilePath, stopList[i]);
//						
//					}
//				}
//			}
//						
//		} catch (Exception e) {
//			e.printStackTrace();
//			retVal="false";
//		}
//		
//		return retVal;
//	}
	
	
//	public static String runConditionsCheck(String baseFile, String viewFilePath, String transformedFilePath){
//		String retVal="false";
//		
//		try {
//			
//			
//			String stops=getStops(transformedFilePath, viewFilePath);
//			
//			if (stops!=""){
//				String[] stopList=stops.split("\\,");
//			
//				for (int i=0;i<stopList.length;i++){
//					
//					retVal=buildConditionCheck(baseFile, transformedFilePath, stopList[i]);
//					if(retVal.compareTo("true")==0){
//						retVal=buildPresenceCheck(baseFile, transformedFilePath, stopList[i]);
//						
//					}
//				}
//			}
//						
//		} catch (Exception e) {
//			e.printStackTrace();
//			retVal="false";
//		}
//		
//		return retVal;
//	}
	
	public static String runBuildCleanForStops(String baseFile,String transformedFilePath, String stop){
		String retVal="false";
		
		try {
					retVal=buildCleanForStops (baseFile,transformedFilePath, stop);
			
		} catch (Exception e) {
			e.printStackTrace();
			retVal="false";
		}
		
		return retVal;
	}
	
	
//	public static String runBuildCleanForStops(String baseFile, String viewFilePath, String transformedFilePath){
//		String retVal="false";
//		
//		try {
//			
//			
//			String stops=getStops(transformedFilePath, viewFilePath);
//			if (stops!=""){
//				String[] stopList=stops.split("\\,");
//				
//				for (int i=0;i<stopList.length;i++){
//					retVal=buildCleanForStops (baseFile,transformedFilePath, stopList[i]);
//					
//				}
//			}
//			
//			
//			
//	
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			retVal="false";
//		}
//		
//		return retVal;
//	}
	
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
    				
    				NodeList vertexNodes=layoutNetElement.getElementsByTagName("vertex");
    				for(int i=0;i<vertexNodes.getLength() && !found;i++){
    					
    					Element vertexElement=(Element)vertexNodes.item(i);
    					if(vertexElement.getAttribute("id").compareToIgnoreCase(ElementID)==0){
    						NodeList attributesNode=vertexElement.getElementsByTagName("attributes");
    						Element  attributesElement=(Element)attributesNode.item(0);
    						
    						NodeList boundsNode=attributesElement.getElementsByTagName("bounds");
    						Element  boundsElement=(Element)boundsNode.item(0);
    						
    						retVal=boundsElement.getAttribute("x")+","+boundsElement.getAttribute("y");
    						return retVal;
    					}
    				}
    				
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
	
	
	
	public static String getWorkflowElementContainerAttributeXY(Document doc, String ElementID){
		String retVal="";
		
		try {
			

		
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
    				
    				NodeList vertexNodes=layoutNetElement.getElementsByTagName("vertex");
    				for(int i=0;i<vertexNodes.getLength() && !found;i++){
    					
    					Element vertexElement=(Element)vertexNodes.item(i);
    					if(vertexElement.getAttribute("id").compareToIgnoreCase(ElementID)==0){
    						NodeList attributesNode=vertexElement.getElementsByTagName("attributes");
    						Element  attributesElement=(Element)attributesNode.item(0);
    						
    						NodeList boundsNode=attributesElement.getElementsByTagName("bounds");
    						Element  boundsElement=(Element)boundsNode.item(0);
    						
    						retVal=boundsElement.getAttribute("x")+","+boundsElement.getAttribute("y");
    						return retVal;
    					}
    				}
    				
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
			
			int x=Integer.valueOf(ElementX.split("\\.")[0])-32;
			int y=Integer.valueOf(ElementY.split("\\.")[0])+32;
			
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
			
			int x=Integer.valueOf(ElementX.split("\\.")[0])-10;
			int y=Integer.valueOf(ElementY.split("\\.")[0]);
			
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
			
			int x=Integer.valueOf(ElementX.split("\\.")[0])+31;
			int y=Integer.valueOf(ElementY.split("\\.")[0]);
			
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
