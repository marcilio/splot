package splot.services.extensions.fundp.workflow.handlers;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

/** ShowWorkflowListHandler shows the selected workflow specification's information and tasks list to the user. 
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ShowWorkflowListHandler extends FreeMarkerHandler{

	/** ShowWorkflowListHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 * @param configuration		a Configuration object including information about freemarker template's configuration  
	 * @param template		a Template object including the template which should be used to create the interface of the html page              
	 */
	public ShowWorkflowListHandler(String handlerName, HttpServlet servlet,
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
		

		try {
     		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views";//getServlet().getInitParameter("viewFilesPath");
    		String modelFileName = (String)request.getParameter("selectedModels");
			File file = new File(viewDir+"/"+modelFileName);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList specificationNodes = doc.getElementsByTagName("specification");  
			Element specificationElement = (Element) specificationNodes.item(0);
			
			templateModel.put("name", specificationElement.getAttribute("uri"));
			
			List<Map> decompositionlist =new LinkedList<Map>();  
			
			NodeList decompositionNodes = doc.getElementsByTagName("decomposition"); 
			
			

			for(int i=0;i< decompositionNodes.getLength();i++){
				Map decompositionInfo=new HashMap();
				Element decompositionElement = (Element) decompositionNodes.item(i);
				decompositionInfo.put("decomp_name", decompositionElement.getAttribute("id"));
				
				NodeList taskNodes = decompositionElement.getElementsByTagName("task"); 
				List<Map> tasklist =new LinkedList<Map>();
				for(int j=0;j< taskNodes.getLength();j++){
					Element taskElement = (Element) taskNodes.item(j);	
					NodeList title = taskElement.getElementsByTagName("name");
				    Element taskName = (Element) title.item(0);
				    
				    NodeList decomposesToElement=taskElement.getElementsByTagName("decomposesTo");
				    Element decomposesToElementName = (Element) decomposesToElement.item(0);
				    String decomposesTo="";
				    if (decomposesToElement==null){
				    	 decomposesTo="";
				    }else{
				    	 decomposesTo=decomposesToElementName.getAttribute("id");
				    }
				    Map taskInfo = new HashMap();
				    taskInfo.put("task_name", Methods.getCharacterDataFromElement(taskName));
				    taskInfo.put("decomposes", decomposesTo);			        
				    tasklist.add(taskInfo);
				    decompositionInfo.put("decomp_info", tasklist);
				    
				    decompositionlist.add(decompositionInfo);
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Problems locating/acessing the workflow specification repository path", e);

		}
	}

}
