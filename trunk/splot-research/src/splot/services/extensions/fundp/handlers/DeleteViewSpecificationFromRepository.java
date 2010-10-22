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



import splar.core.fm.FeatureModelException;
import splot.core.Handler;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


import org.w3c.dom.*;

import org.xml.sax.SAXException;

/** DeleteViewSpecificationFromRepository is used to delete a view from repository.  
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class DeleteViewSpecificationFromRepository extends Handler {
	
	/** DeleteViewSpecificationFromRepository is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public DeleteViewSpecificationFromRepository (String handlerName , HttpServlet servlet) {
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
        		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath"); 
	        	
        		String featureModelFileName=(String)request.getParameter("feature_model_file_name");
	        	String viewFileName=(String)request.getParameter("view_file_name_in_repository");
	        	String relatedFeatureModel=request.getParameter("related_feature_model");
	        	String viewName = request.getParameter("view_name");

	        	File file = new File(viewDir+"/"+viewFileName);
	        	String filePath=viewDir+"/"+viewFileName;
	        	if (!(file.exists())){
	        		response.getWriter().write("The view does not exist in the repository.");
	        	}else{
	        		String result=deleteViewFromFile(viewName,filePath);
	        		response.getWriter().write(result);

	        		
	        	}
	        	

        		
	    }catch (Exception e) {
	        	e.printStackTrace();
			}
	}
	

	private static  String deleteViewFromFile(String viewName, String fileName) throws JSONException, ParseException, FeatureModelException, ParserConfigurationException{
		String retValue="true";
		try{
		   
			
			
		    	
		    	
		    	File file = new File(fileName);	
		      	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = factory.newDocumentBuilder();
		    	Document document = docBuilder.parse(file);
		    	boolean viewExists=false;
		    	
		    	Element featureModelElement=(Element) document.getFirstChild();
		    	Element viewSpecificationElement=(Element) featureModelElement.getFirstChild();

				NodeList  viewNodes = viewSpecificationElement.getElementsByTagName("view"); 
				for (int i = 0; i < viewNodes.getLength(); i++){
					Element element = (Element) viewNodes.item(i);
					if (element.getAttribute("name").trim().compareToIgnoreCase(viewName)==0){
						viewExists=true;
						NodeList  taskNodes = element.getElementsByTagName("task"); 
						if (taskNodes.getLength()>0){
							retValue="This view is used in the view allocation and can not be deleted.";
						}else{
							
							
							if (viewNodes.getLength()==1){
								file.delete();
								return "true";
							}else{

								viewSpecificationElement.removeChild(element);
						    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
							    Transformer transformer=transformerFactory.newTransformer();
							    DOMSource source=new DOMSource(document);
							    StreamResult result=new StreamResult(file);
							    transformer.transform(source, result);
							    retValue="true";
							}
							
							
						}

					}
				}
				
				
				
				
				if (!viewExists){
					retValue="The view does not exist in the repository.";
				}
				
					
					
					
				
					    


		
		}catch (Exception e) {
			retValue=e.getMessage();
		}	
    	
    	

		return retValue;
	}



		
}


