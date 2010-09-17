package splot.services.extensions.fundp.utilities;

import java.io.StringWriter;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import splar.core.fm.FeatureTreeNode;

/** SXFMToXML is used to convert a SXFM format to an XML format. 
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/

public class SXFMToXML{
	
	/** parse  loads feature model file, gets SXFM text from file, converts SXFM to XML using convertSXFMToXML method and returns the XML text as a string value.
	 * 
	 * @param featureModelFileName  a string including the file name in which the model is stored
	 * @return returns XML resulted from conversion as a string value            
	 */
	public static String parse( String featureModelFileName) {
		String retValue="";
		
		
		Boolean hasError=false;
		
		Document document=null;
			try {
		    	DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
		    	DocumentBuilder documentBuilder = null;
				try {
					documentBuilder = documentBuilderFactory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {
					hasError=true;
					
				}
		    	document=documentBuilder.newDocument();
				fm.FeatureModel featureModel = new fm.XMLFeatureModel(featureModelFileName, fm.XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();
				String  rootName=featureModel.getRoot().getName();
				rootName=rootName.replace(" ", ".");
		    	Element rootElement=document.createElement(rootName);
		    	
		    	try{
		    		convertSXFMToXML(featureModel.getRoot(), 0, rootElement,document);
		    	}catch (Exception e) {
		    		hasError=true;
					
				}
			} catch (Exception e) {
				hasError=true;
				
				
			}
	    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
		    Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e) {
				hasError=true;
			
				
			}
		    DOMSource source=new DOMSource(document);
		    StringWriter result=new StringWriter();
		    try {
				transformer.transform(source, new StreamResult(result));
				retValue= result.toString();
				
			} catch (TransformerException e) {
				hasError=true;
				
				
			}
			
			if ((hasError) || (retValue=="") || (retValue.isEmpty()) || (retValue==null)){
				retValue="error";
			}
		
			
			return retValue;
		}	
		
	
	/** convertSXFMToXML  is a recursive method which parses all the features in a feature model and adds each feature as a element to an XML document 
	 * 
	 * @param featureTreeNode  a feature of the feature model
	 * @param tab an integer value including the number of tabs before the featureTreeNode in the SXFM format
	 * @param parentElement an element of XML document that  featureTreeNode should be add to it as a new child 
	 * @param document a Document object containing the XML data 
	 */	
	public static void convertSXFMToXML(fm.FeatureTreeNode featureTreeNode, int tab, Element parentElement , Document document){
	
		try{
		if ( featureTreeNode.isRoot() ) {
			document.appendChild(parentElement);
		}
		for( int i = 0 ; i < featureTreeNode.getChildCount() ; i++ ) {
				
			
			
			String featureName="";
			
			if ((fm.FeatureTreeNode) featureTreeNode.getChildAt(i) instanceof fm.FeatureGroup){
				featureName="feature.group";	
				
			}else{
				 featureName=((fm.FeatureTreeNode) featureTreeNode.getChildAt(i)).getName().trim();
				 featureName=featureName.replace(" ", ".");
			}
				Element newElement = document.createElement(featureName);
				parentElement.appendChild(newElement);
				convertSXFMToXML((fm.FeatureTreeNode) featureTreeNode.getChildAt(i), tab+1,newElement,document);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	

	
	
		
}