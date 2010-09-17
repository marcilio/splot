package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.IOException;

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

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import splot.services.extensions.fundp.utilities.Methods;

import splot.core.Handler;

/** DeleteViewAllocationHandler is used to delete the relation between a task and a view from the view's related file.    
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class DeleteViewAllocationHandler  extends Handler {

	/** DeleteViewAllocationHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public DeleteViewAllocationHandler(String handlerName, HttpServlet servlet) {
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
		try {
			String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views";
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
			String  featureModel=request.getParameter("featureModel").trim();
			String  workflow=request.getParameter("workflow").trim();
			String  task=request.getParameter("taskName").trim();
			String  view=request.getParameter("viewName").trim();
			
   			for (int i=0;i<childeren.length;i++){
   				if (childeren!=null){
   					if (childeren[i].endsWith(".xml") !=false){
   						String  fileName=childeren[i];
       					File viewFile = new File(viewDir+"/"+fileName);
       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       					Document doc = builder.parse(viewFile);
       					
       					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
       					Element  featureModelElement = (Element) featureModelNodes.item(0);
       					String   featureModelName=featureModelElement.getAttribute("name");
       					if (featureModel.compareToIgnoreCase(featureModelName)==0){
       						NodeList viewNodes = doc.getElementsByTagName("view");
       						for (int j = 0; j < viewNodes.getLength(); j++){
       							Element viewElement = (Element) viewNodes.item(j);
       							String   viewName=viewElement.getAttribute("name");
       							if (viewName.compareToIgnoreCase(view)==0){
       								NodeList taskNodes =  viewElement.getElementsByTagName("task");
           							Element taskElement =(Element) taskNodes.item(0);
           							NodeList  workflowNodes=taskElement.getElementsByTagName("workflow");
           							for (int k = 0; k < workflowNodes.getLength(); k++){
           								Element workflowElement = (Element) workflowNodes.item(k);
           								String workflowName=workflowElement.getAttribute("name");
           								if (workflowName.compareToIgnoreCase(workflow)==0){
           									NodeList taskNameNodes = workflowElement.getElementsByTagName("task_name");
           									int workflowTaskCount=taskNameNodes.getLength();
           									for (int t = 0; t < taskNameNodes.getLength(); t++){
           										Element  taskNameElement=(Element) taskNameNodes.item(t);
           										String   taskNameElementValue=Methods.getCharacterDataFromElement(taskNameElement);
           										if (taskNameElementValue.compareToIgnoreCase(task)==0){
           											taskNameElement.getParentNode().removeChild(taskNameElement);
           											if (workflowTaskCount==1){
           												workflowElement.getParentNode().removeChild(workflowElement);
           											}
           										}
           									}	
           									
           								}
           							}	
       							}
       						}	
       					}
       			    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
       				    Transformer transformer=transformerFactory.newTransformer();
       				    DOMSource source=new DOMSource(doc);
       				    StreamResult result=new StreamResult(viewFile);
       				    transformer.transform(source, result);

   					}
   				}
   			}

			


		response.getWriter().write("true");	
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
		}
		
	}
	



}
