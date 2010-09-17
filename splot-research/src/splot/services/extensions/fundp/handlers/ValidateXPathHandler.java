package splot.services.extensions.fundp.handlers;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.json.simple.JSONValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import fm.FeatureGroup;
import fm.FeatureModel;
import fm.FeatureTreeNode;
import fm.RootNode;
import fm.XMLFeatureModel;

import splot.core.Handler;

import splot.services.extensions.fundp.utilities.*;
import splot.services.extensions.fundp.utilities.Methods;

/** ValidateXPathHandler is used to validate the XPath expression.  
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ValidateXPathHandler extends Handler {

	/** ValidateXPathHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public ValidateXPathHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	/** run called by the servlet container to start its activity.  
	 * 
	 * @throws	ServletException,IOException 	if an exception has occurred, based on its type, that interferes with the servlet's normal operation or IOException handler.
	 * @param request	a  HttpServletRequest object containing the request received by handler from the client
	 * @param response	a  HttpServletResponse object containing the response should be sent to the client 	
	 */
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String featureModelFileName=request.getParameter("feature_model_file_name").trim();
		String modelDir=getServlet().getInitParameter("modelsPath");
		File featureModelFile = new File(modelDir+"/"+featureModelFileName);
		String featureModelFileString=modelDir+"/"+featureModelFileName;
		Map jsonObj=new LinkedHashMap();
		String xpathExpression=request.getParameter("xpath_expression").trim();
	    EvaluationResult result=new EvaluationResult();

    	String jsonText = "";
		
   
	    
	    if (!featureModelFile.exists()){
	    	jsonObj.put("error_list","Feature model file not found.");	
	    	jsonObj.put("node_list","");
	    	jsonText = JSONValue.toJSONString(jsonObj);
			response.getWriter().write(jsonText);
	    	
	    	
	    }else{
	    	try{
	    	
	    		String featureModelInXML="";
	    		featureModelInXML=SXFMToXML.parse(featureModelFileString);
	
	    		if ((featureModelInXML=="") || (featureModelFileString==null) || (featureModelInXML.compareToIgnoreCase("error")==0)){
	    	    	jsonObj.put("error_list","Error in the SXFM to XML conversion.");	
	    	    	jsonObj.put("node_list","");
	    	    	 jsonText = JSONValue.toJSONString(jsonObj);
	    			response.getWriter().write(jsonText);
	    		}else{
	    			Methods.evaluateXPathExpression(featureModelInXML, xpathExpression, result);
		 			if ((result.nodesList==null) || (result.nodesList.compareToIgnoreCase("null")==0) || (result.nodesList=="")){
		 			   jsonObj.put("node_list","");
		 			}else{
		 				jsonObj.put("node_list",result.nodesList);	
		 			}
		 			
		 			if ((result.error==null) || (result.error.compareToIgnoreCase("null")==0) || (result.error=="")){
		 				   jsonObj.put("error_list","");
		 				}else{
		 					jsonObj.put("error_list",result.error);	
		 				}

		 			  jsonText = JSONValue.toJSONString(jsonObj);
			 		  response.getWriter().write(jsonText);

	    			
	    		}
	    		
	    		
	    	}catch (Exception e) {
		    	jsonObj.put("error_list",e.getMessage());	
		    	jsonObj.put("node_list","");
		    	jsonText = JSONValue.toJSONString(jsonObj);
				response.getWriter().write(jsonText);

			}
	    }
	    
	
	}

	

	
}




	
