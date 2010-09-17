package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import splot.services.extensions.fundp.utilities.Methods;


import splot.core.Handler;

/** ResponseViewDetailsHandler is used to response the detail information of a special view.      
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ResponseViewDetailsHandler extends Handler  {
	
	/** ResponseViewDetailsHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public ResponseViewDetailsHandler (String handlerName,HttpServlet servlet) {
		super(handlerName, servlet);
	}

	/** run called by the servlet container to start its activity.  
	 * 
	 * @throws	ServletException,IOException 	if an exception has occurred, based on its type, that interferes with the servlet's normal operation or IOException handler.
	 * @param request	a  HttpServletRequest object containing the request received by handler from the client
	 * @param response	a  HttpServletResponse object containing the response should be sent to the client 	
	 */	
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
			String viewName=request.getParameter("viewName").trim();
			String viewFileName=(String) request.getParameter("viewFileName").trim();
			String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath");
			File   importfile = new File(viewDir+"/"+viewFileName);
			String viewDetails="";
		
			 Map jsonObj=new LinkedHashMap();

			
			
				if (importfile.exists()){
			try{
					
					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					Document doc = builder.parse(importfile);
					NodeList  viewNodes = doc.getElementsByTagName("view"); 
					for (int i = 0; i < viewNodes.getLength(); i++){
						
						Element element = (Element) viewNodes.item(i);
						String elementViewName= (String)element.getAttribute("name").trim();
						
						if (elementViewName.compareTo(viewName)==0 ){
							
							NodeList metaData = element.getElementsByTagName("data");
							for (int j = 0; j < metaData.getLength(); j++){
							

								Element descriptionElement = (Element) metaData.item(0);
								
								jsonObj.put("description",Methods.getCharacterDataFromElement(descriptionElement).trim());	
								
								Element creatorElement = (Element) metaData.item(1);
								jsonObj.put("creator",Methods.getCharacterDataFromElement(creatorElement).trim());	
		
								Element emailElement = (Element) metaData.item(2);
								jsonObj.put("email",Methods.getCharacterDataFromElement(emailElement).trim());	
								

								Element dateElement = (Element) metaData.item(3);
								jsonObj.put("date",Methods.getCharacterDataFromElement(dateElement).trim());	

								Element commentElement = (Element) metaData.item(4);
								jsonObj.put("comment",Methods.getCharacterDataFromElement(commentElement).trim());	


							}
							
							
						     NodeList viewSpecification = element.getElementsByTagName("specification");
						     Element specification = (Element) viewSpecification.item(0);
						    
						     jsonObj.put("specification",Methods.getCharacterDataFromElement(specification).trim());
						     jsonObj.put("viewName",viewName.trim());
						}
					}	

			}catch (Exception e) {
				e.printStackTrace();
			}	
			
	}
		String jsonText = JSONValue.toJSONString(jsonObj);
		response.getWriter().write(jsonText);
		
	
	
	
	}

	
}
