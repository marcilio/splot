package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import freemarker.template.Configuration;
import freemarker.template.Template;

import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;


/** LoadViewAllocationInformationHandler is used to parse the workflow specification imported folder, validate specification and save it to parsed folder.  
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ParseWorkflowSpecificationHandler  extends FreeMarkerHandler{
	

	/** ParseWorkflowSpecificationHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 * @param configuration		a Configuration object including information about freemarker template's configuration  
	 * @param template		a Template object including the template which should be used to create the interface of the html page              
	 */
	public ParseWorkflowSpecificationHandler (String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
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
	public void buildModel(HttpServletRequest request,HttpServletResponse response, Map templateModel) throws HandlerExecutionException{
		List<Map> filelist =new LinkedList<Map>();
		String parsedDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows"; //getServlet().getInitParameter("parsedWorkflowPath");  // directory of parsed workflow's xml files: SPLOT/WebContent/extensions/parsed_workflows
		String importedDir=getServlet().getServletContext().getRealPath("/")+ "extensions/imported_workflows"; //getServlet().getInitParameter("importedWorkflowPath");  // directory of workflow's xml files: SPLOT/WebContent/extensions/imported_workflows
		File  dir=new File(importedDir);
		String xmlData="";
        try {
       		String[]  childeren=dir.list();
       		if (childeren!=null){
       			for (int i=0;i<childeren.length;i++){
       				if ((childeren[i].endsWith(".xml") !=false) ||  (childeren[i].endsWith(".yawl") !=false)){
       					String  fileName=childeren[i];
       					File importfile = new File(importedDir+"/"+fileName);
       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       					Document doc = builder.parse(importfile);
       					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
       					Element specificationElement = (Element) specificationNodes.item(0);
       					String workflowName=specificationElement.getAttribute("uri");
       					
       					
       					Map fileInformation=new HashMap(); // 
       					
       					StringWriter stw = new StringWriter(); 
       					Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
       					serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
       					xmlData=stw.toString();
	   					File exportFile=new File(parsedDir+"/"+fileName);
       					if (!exportFile.exists()){
       						try {
       							FileWriter fw=new FileWriter(parsedDir+"/"+fileName);
       							fw.write(xmlData);
       							fw.close();  
       							importfile.delete();
       							fileInformation.put("file_name", fileName);
       							fileInformation.put("workflow_name", workflowName);
       							filelist.add(fileInformation);
            					
       							
       						} catch (Exception e) {
       							
       							e.printStackTrace();
       						}
       						
       					}
       				}	 
       			}
       		}
       		
       		if (filelist.isEmpty()){
       			templateModel.put("file_exists", "false");
       		}else{
       			templateModel.put("file_exists", "true");

       		}
       		
       		templateModel.put("parsed_list", filelist);
        	
        }catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}

	}

}
