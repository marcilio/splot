package splot.services.extensions.fundp.handlers;

import java.io.File;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import splot.services.extensions.fundp.utilities.Methods;

import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/** ShowWorkflowListHandler shows the selected workflow specification's information and tasks list to the user. 
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ShowWorkflowListHandler extends FreeMarkerHandler {
	
	/** ShowWorkflowListHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 * @param configuration		a Configuration object including information about freemarker template's configuration  
	 * @param template		a Template object including the template which should be used to create the interface of the html page              
	 */
	public ShowWorkflowListHandler (String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}

	/**
	 * buildModel implements the handler's logic. It basically fills the templateModel map with the necessary information
	 * that will be later referenced by this handler's freemarker template.
	 * 
	 * @throws	HandlerExecutionException	if an exception has occurred that interferes with the handler's normal operation
	 * @param request	a  HttpServletRequest object containing the request received by handler from the client
	 * @param response	a  HttpServletResponse object containing the response should be sent to the client 	
	 * @param templateModel	a template including the information's structure should be sent to the client
	 */
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
	
		List<Map> workflowlist =new LinkedList<Map>();  // a list of workflow specifications
		String xmlDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows"; //getServlet().getInitParameter("parsedWorkflowPath");  // directory of parsed workflow's xml files: SPLOT/WebContent/extensions/parsed_workflows
		File  dir=new File(xmlDir);
        try {
        		String[]  childeren=dir.list();
        		if (childeren!=null){
        			for (int i=0;i<childeren.length;i++){
        				 if ((childeren[i].indexOf(".xml") !=-1) || (childeren[i].indexOf(".yawl") !=-1)){
        					 String  fileName=childeren[i];
        					 File file = new File(xmlDir+"/"+fileName);
        					  DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        					  Document doc = builder.parse(file);
        					  
        					  
        					  
        					  NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
        					  Element specificationElement = (Element) specificationNodes.item(0);
        					  
        					  Map workflow=new HashMap(); // 
        					  workflow.put("workflow_name", specificationElement.getAttribute("uri")); // "uri" is an attribute, contains workflow's name 
        					  
        	        			 
        					  
        					  List<Map> tasklist =new LinkedList<Map>(); // a list if workflow's name
        					  
        					  
        					  NodeList taskNodes = doc.getElementsByTagName("task"); // workflow's task are defined with "task" tag
        					  for (int j = 0; j < taskNodes.getLength(); j++) {
        					       Element element = (Element) taskNodes.item(j);
        					       NodeList title = element.getElementsByTagName("name");
        					       Element line = (Element) title.item(0);
        					       
        					       Map taskName = new HashMap();
        					       taskName.put("task_name", Methods.getCharacterDataFromElement(line));
        				           tasklist.add(taskName);
        					     }
        					  workflow.put("tasks",tasklist);
        					  workflowlist.add(workflow);
        				 }
        			}
        		}
        	
        	templateModel.put("workflowlist", workflowlist);
        }
        catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}
        

       
	}
	
	
	
}
