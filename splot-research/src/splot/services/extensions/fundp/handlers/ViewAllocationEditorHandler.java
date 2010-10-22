package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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

import freemarker.template.Configuration;
import freemarker.template.Template;

import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;

/** ViewAllocationEditorHandler is used to manage the view allocation editor. This also prepares the required information for the editor.  
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ViewAllocationEditorHandler  extends FreeMarkerHandler {

	/** ParseWorkflowSpecificationHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 * @param configuration		a Configuration object including information about freemarker template's configuration  
	 * @param template		a Template object including the template which should be used to create the interface of the html page              
	 */
	public ViewAllocationEditorHandler(String handlerName, HttpServlet servlet,
			Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * buildModel implements the handler's logic. It basically fills the templateModel map with the necessary information
	 * that will be later referenced by this handler's freemarker template.
	 * 
	 * @throws	HandlerExecutionException	if an exception has occurred that interferes with the handler's normal operation
	 * @param request	a  HttpServletRequest object containing the request received by handler from the client
	 * @param response	a  HttpServletResponse object containing the response should be sent to the client 	
	 * @param templateModel	a template including the information's structure should be sent to the client
	 */
	public void buildModel(HttpServletRequest request,HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
		List<Map> featureModelList =new LinkedList<Map>();
		try {
			
			String showType=(String)request.getParameter("show_type");
			String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath");
			File  dir=new File(viewDir);
       		String[]  childeren=dir.list();
    		if (childeren!=null){
       			for (int i=0;i<childeren.length;i++){
       				if (childeren[i].endsWith(".xml")==true){
       					String  fileName=childeren[i];
       					File file = new File(viewDir+"/"+fileName);
       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       					Document doc = builder.parse(file);
       					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
       					Element  featureModelElement = (Element) featureModelNodes.item(0);
       					String   featureModelName=featureModelElement.getAttribute("name");
       					Map featureModel=new HashMap(); //
       					featureModel.put("feature_model_name", featureModelName);
					    featureModelList.add(featureModel);

       				}
       				
       				
   					 
   				 }

    		}

    		templateModel.put("featureModelList", featureModelList);
    		templateModel.put("show_type", showType);
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing view repository path":e.getMessage());

		}
		
		
		
        try {
    			List<Map> workflowlist =new LinkedList<Map>();  // a list of workflow specifications
    			String xmlDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows"; //getServlet().getInitParameter("parsedWorkflowPath");  // directory of parsed workflow's xml files: SPLOT/WebContent/extensions/parsed_workflows
    			File  dir=new File(xmlDir);
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
         					  workflowlist.add(workflow);
        				 }
        			}
        		}
        	
        	templateModel.put("workflowlist", workflowlist);
        }
        catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing workflow repository path":e.getMessage());
        	
		}
 	}
	
}
