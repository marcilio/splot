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
import splot.services.extensions.fundp.utilities.Methods;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import splot.core.Handler;

/** SaveViewAllocationToRepository is used to save the relation between a workflow, task and feature model.  
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class SaveViewAllocationToRepository extends Handler {
	
	/** SaveViewAllocationToRepository is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public SaveViewAllocationToRepository(String handlerName,
			HttpServlet servlet) {
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
			String resultToSend="true";
			Boolean taskExistence=false;
			String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath");
			File  dir=new File(viewDir);
			String[]  childeren=dir.list();
			String  featureModel=request.getParameter("featureModel").trim();
			String  workflow=request.getParameter("workflow").trim();
			String   task=request.getParameter("taskName").trim();
			String  view=request.getParameter("viewName").trim();
			String receivedTaskStop=task.split("\\?")[0];
			start:
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
      								int taskNodesLength=taskNodes.getLength();
      								if (taskNodesLength>=1){
              							Element taskElement =(Element) taskNodes.item(0);
               							NodeList  workflowNodes=taskElement.getElementsByTagName("workflow");
               							int workflowNodesLength=workflowNodes.getLength();
               							if (workflowNodesLength>=1){
               								for (int k = 0; (k < workflowNodes.getLength() && (!taskExistence)); k++){
               									Element workflowElement = (Element) workflowNodes.item(k);
                   								String workflowName=workflowElement.getAttribute("name");
                   								if (workflowName.compareToIgnoreCase(workflow)==0){
                   									NodeList taskNameNodes = workflowElement.getElementsByTagName("task_name");
                   									int taskNameNodesLength=taskNameNodes.getLength();
                   									if (taskNameNodesLength>=1){
                   										for (int t = 0; t < taskNameNodes.getLength(); t++){
                   											Element  taskNameElement=(Element) taskNameNodes.item(t);
                       										String   taskNameElementValue=Methods.getCharacterDataFromElement(taskNameElement);
                       										
                       										
                       										String existingTaskStop=taskNameElementValue.split("\\?")[0];
                       										
                       									                       										
                       										
                       										if ((existingTaskStop.compareToIgnoreCase(receivedTaskStop)==0)   && (workflowName.compareToIgnoreCase(workflow)==0) && (viewName.compareToIgnoreCase(view)==0) && (featureModelName.compareToIgnoreCase(featureModel)==0)  ){
                       											resultToSend="This information already exists in the repository.";
                       											taskExistence=true;
                       											break start;
                       										}else{
                       											
                       											
                       										}
                       										
                   										}
                       										
                   										if (!taskExistence){

                   											Element taskElementToSave=doc.createElement("task_name");
                   											taskElementToSave.appendChild(doc.createTextNode(task));
                   											workflowElement.appendChild(taskElementToSave);
                   											
                   											TransformerFactory transformerFactory=TransformerFactory.newInstance();
                   										    Transformer transformer=transformerFactory.newTransformer();
                   										    DOMSource source=new DOMSource(doc);
                   										    StreamResult result=new StreamResult(viewFile);
                   										    transformer.transform(source, result);
                   										    
                   										    break start;
                   										}
                       										
                   											
                   										
                   									}else{
                   										Element taskElementToSave=doc.createElement("task_name");
               											taskElementToSave.appendChild(doc.createTextNode(task));
               											workflowElement.appendChild(taskElementToSave);
               											
               									    	TransformerFactory transformerFactory=TransformerFactory.newInstance();
               										    Transformer transformer=transformerFactory.newTransformer();
               										    DOMSource source=new DOMSource(doc);
               										    StreamResult result=new StreamResult(viewFile);
               										    transformer.transform(source, result);
               									    	 break start;

                   										
                   									}
                   								}else{
                   									Element workflowElementToSave=doc.createElement("workflow");
                       								workflowElementToSave.setAttribute("name", workflow);
           											taskElement.appendChild(workflowElementToSave);
           											
           											Element taskElementToSave=doc.createElement("task_name");
           											taskElementToSave.appendChild(doc.createTextNode(task));
           											workflowElementToSave.appendChild(taskElementToSave);
           											
           											TransformerFactory transformerFactory=TransformerFactory.newInstance();
           										    Transformer transformer=transformerFactory.newTransformer();
           										    DOMSource source=new DOMSource(doc);
           										    StreamResult result=new StreamResult(viewFile);
           										    transformer.transform(source, result);
           									    	 break start;
                   								}
               								}
               							}else{
               								Element workflowElementToSave=doc.createElement("workflow");
               								workflowElementToSave.setAttribute("name", workflow);
   											taskElement.appendChild(workflowElementToSave);
   											
   											Element taskElementToSave=doc.createElement("task_name");
   											taskElementToSave.appendChild(doc.createTextNode(task));
   											workflowElementToSave.appendChild(taskElementToSave);
   											
   											TransformerFactory transformerFactory=TransformerFactory.newInstance();
   										    Transformer transformer=transformerFactory.newTransformer();
   										    DOMSource source=new DOMSource(doc);
   										    StreamResult result=new StreamResult(viewFile);
   										    transformer.transform(source, result);
   									    	 break start;
               								
               							}
      								}else{
      									Element taskElementToSave=doc.createElement("task");
      							    	viewElement.appendChild(taskElementToSave);
      							    	
          								Element workflowElementToSave=doc.createElement("workflow");
           								workflowElementToSave.setAttribute("name", workflow);
           								taskElementToSave.appendChild(workflowElementToSave);
											
										Element taskNameElementToSave=doc.createElement("task_name");
										taskNameElementToSave.appendChild(doc.createTextNode(task));
										workflowElementToSave.appendChild(taskNameElementToSave);
										
										TransformerFactory transformerFactory=TransformerFactory.newInstance();
										Transformer transformer=transformerFactory.newTransformer();
										DOMSource source=new DOMSource(doc);
										StreamResult result=new StreamResult(viewFile);
										transformer.transform(source, result);
			
										break start;
      							    	
      							    	
      						
      									
      								}

       							}
       						}	
       					}

   					}
   				}
   			}
			
		 response.getWriter().write(resultToSend);	
		} catch (Exception e) {
			//e.printStackTrace();
			response.getWriter().write(e.getMessage());
		}
		
	}
}
