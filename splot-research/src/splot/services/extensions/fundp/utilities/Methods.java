package splot.services.extensions.fundp.utilities;

import java.io.File;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

import com.sun.tools.javac.resources.compiler;



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
					nodeExist=true;
					tempResult=tempResult+node+"\n";
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
	
	
	public static String checkConfigurationCompletionInStopPlace(String featureModelName , String viewDir,String modelDir, String placeName ,String placeType , String workflow, String configuredFileName) throws HandlerExecutionException {
		String retVal="";
		
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
					
					if (retVal==""){
						retVal=taskName+":"+status;
					}else{
						retVal=retVal+","+taskName+":"+status;

					}

					
				
				}


			}
			
			
		} catch (Exception e) {
			retVal="";
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

	
	  public static String  getConfiguredFileName(String configuredModelsPath,String serverKey){
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
		
	  public static String  getConfiguredFileServerKey(String configuredModelsPath,String userKey, String featureModel, String workflow){
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
			       					String   ServerKey= featureModelElement.getAttribute("server_key");

			       					
			       					if ((featureModel.compareToIgnoreCase(FMName)==0)&&(workflow.compareToIgnoreCase(workflowValue)==0)&&(userKey.compareToIgnoreCase(userKeyValue)==0)){
			       						retVal=ServerKey;
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
	  
		public static FeatureDecisionInfo getFeatureDecisionInfo(String modelDir, String serverKey, String featureID){
			
			FeatureDecisionInfo result=new FeatureDecisionInfo();

			try {
				String configurationFileName=Methods.getConfiguredFileName(modelDir+"/configured_models", serverKey);
				
				if (configurationFileName.compareToIgnoreCase("false")==0){
					result.found=false;
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
								
								NodeList userList=featureDecisionElement.getElementsByTagName("user");
								Element  userElement=(Element)userList.item(0);
								
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
								result.user=Methods.getCharacterDataFromElement(userElement);
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
}
