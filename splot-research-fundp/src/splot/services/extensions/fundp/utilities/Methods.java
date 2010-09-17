package splot.services.extensions.fundp.utilities;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import fm.SolitaireFeature;

import splar.core.fm.FeatureGroup;
import splar.core.fm.FeatureTreeNode;
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
	       					File viewFile = new File(viewDir+fileName);
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
	
}
