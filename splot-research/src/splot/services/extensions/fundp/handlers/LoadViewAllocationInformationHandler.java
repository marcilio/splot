package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONValue;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import splot.services.extensions.fundp.utilities.Methods;

import com.google.gson.JsonObject;

import splot.core.Handler;

/** LoadViewAllocationInformationHandler is used to load the views associated to a special feature model and workflow.      
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class LoadViewAllocationInformationHandler extends Handler {

	/** LoadViewAllocationInformationHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public LoadViewAllocationInformationHandler(String handlerName,
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
		int viewCount = 0;
		String  featureModel=(String)request.getParameter("featureModel").trim();
		String  workflow=(String)request.getParameter("workflow").trim();
		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath");
		
		String parsedWorkflowDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows"; //getServlet().getInitParameter("parsedWorkflowPath");
		String imageDir=getServlet().getServletContext().getRealPath("/")+ "extensions/workflow_images";

		String taskList="";
		String viewList="";
		String conditionList="";
		File  dir=new File(viewDir);
		String[]  childeren=dir.list();
		taskList=getWorkflowTasks(workflow,parsedWorkflowDir);
		conditionList=getWorkflowConditions(workflow, parsedWorkflowDir);
		String workflowFileName=getWorkflowFileName(workflow, parsedWorkflowDir);
		
		String imagefileName=workflowFileName.split("\\.")[0]+".png";
		
		File imagefile = new File(imageDir+"/"+imagefileName);
		if (!(imagefile.exists())){
			imagefileName="";
		}



		
		try {
				List<Map> jsonObj=new LinkedList<Map>();
				
				if ((featureModel.compareToIgnoreCase("Select")!=0) && (featureModel!="") && (! featureModel.isEmpty()) ){
					if ((workflow.compareToIgnoreCase("Select")!=0) && (workflow!="") && (! workflow.isEmpty()) ){
						
						
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
		           							String viewElementName=viewElement.getAttribute("name");
		           							if (viewList==""){
		           								viewList=viewElementName;
		           							}else{
		           								viewList=viewList+","+viewElementName;
		           							}
		           							
		           							NodeList taskNodes =  viewElement.getElementsByTagName("task");
		           							
		           							if (taskNodes.getLength()>0){
		             								Element taskElement =(Element) taskNodes.item(0);
		           									NodeList  workflowNodes=taskElement.getElementsByTagName("workflow");
				           							for (int k = 0; k < workflowNodes.getLength(); k++){
				           								Element workflowElement = (Element) workflowNodes.item(k);
				           								String workflowName=workflowElement.getAttribute("name");
				           								if (workflowName.compareToIgnoreCase(workflow)==0){
				           									NodeList taskNameNodes = workflowElement.getElementsByTagName("task_name");
				           									Map viewDetail=new HashMap(); 
				           									viewDetail.put("view_name", viewElement.getAttribute("name"));
				           									viewDetail.put("feature_name", featureModel);
				           									viewDetail.put("workflow_name", workflow);
				           									viewCount++;
				           									
				           									String tasksInString="";
				           									for (int t = 0; t < taskNameNodes.getLength(); t++){
				           										Element  taskNameElement=(Element) taskNameNodes.item(t);
				           										String   taskNameElementValue=Methods.getCharacterDataFromElement(taskNameElement);
				           										
				           										if (tasksInString==""){
				           											tasksInString=taskNameElementValue;
				           											
				           										}else{
				           											
				           												tasksInString=tasksInString+","+taskNameElementValue;
				           												
				           											
				           										}
				           										
				           									}
				           									viewDetail.put("task_list", tasksInString);
			           										jsonObj.add(viewDetail);
			           										
				           								}
				           								
				           							}
		           							
		           							}
		           							
		           							
		           						}
		           						
		           					}

		       					}
		       				}
		       			}

						
						
						
					}
					
				}
			String jsonText = JSONValue.toJSONString(jsonObj);
			response.getWriter().write(viewCount+"/"+jsonText+"/"+viewList+"/"+taskList+"/"+conditionList+"/"+imagefileName);	
			
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());	
		}
		
	}

	/** getWorkflowTasks returns a comma-separated list including the tasks of a special workflow.
	 * 
	 * @param workflowName	the name of the workflow which its tasks should be returned
	 * @param workflowDir	a string containing the real path to the views folder
	 * @return  a comma-separated list of tasks
	 */	
	public static String getWorkflowTasks(String workflowName, String workflowDir){
		String retValue="";
		String taskList="";
		File  dir=new File(workflowDir);
		String[]  childeren=dir.list();
		try {
   			for (int i=0;i<childeren.length;i++){
   				if (childeren!=null){
   					if ((childeren[i].endsWith(".xml") !=false)  || (childeren[i].endsWith(".yawl") !=false)  ){
   						String  fileName=childeren[i];
       					File workflowFile = new File(workflowDir+"/"+fileName);
       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       					Document doc = builder.parse(workflowFile);
       					
       					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
       					Element specificationElement = (Element) specificationNodes.item(0);
       					if (specificationElement.getAttribute("uri").trim().compareToIgnoreCase(workflowName)==0){
       						NodeList taskNodes = doc.getElementsByTagName("task");
       						for (int j = 0; j < taskNodes.getLength(); j++) {
       								Element element = (Element) taskNodes.item(j);
       								NodeList title = element.getElementsByTagName("name");
       								if (title.getLength()>=1){
       									Element line = (Element) title.item(0);
           								String taskName=Methods.getCharacterDataFromElement(line);
           								if (taskList==""){
           									taskList=taskName;
           								}else{
           									taskList=taskList+","+taskName;
           								}
       								}
       						}
					  }
   					}
   					
   				}
   			}

			
			
		} catch (Exception e) {
			return e.getMessage();
		}
			
		

		retValue=taskList;
		return retValue; 
	}
	
	
	public static String getWorkflowFileName(String workflowName, String workflowDir){
		String retValue="";
		String workflowFileName="";
		File  dir=new File(workflowDir);
		String[]  childeren=dir.list();
		try {
   			for (int i=0;i<childeren.length;i++){
   				if (childeren!=null){
   					if ((childeren[i].endsWith(".xml") !=false)  || (childeren[i].endsWith(".yawl") !=false)  ){
   						String  fileName=childeren[i];
       					File workflowFile = new File(workflowDir+"/"+fileName);
       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       					Document doc = builder.parse(workflowFile);
       					
       					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
       					Element specificationElement = (Element) specificationNodes.item(0);
       					if (specificationElement.getAttribute("uri").trim().compareToIgnoreCase(workflowName)==0){
       						workflowFileName=fileName;
					  }
   					}
   					
   				}
   			}

			
			
		} catch (Exception e) {
			return e.getMessage();
		}
			
		

		retValue=workflowFileName;
		return retValue; 
	}
	
	public static String getWorkflowConditions(String workflowName, String workflowDir){
		String retValue="";
		String conditionList="";
		File  dir=new File(workflowDir);
		String[]  childeren=dir.list();
		try {
   			for (int i=0;i<childeren.length;i++){
   				if (childeren!=null){
   					if ((childeren[i].endsWith(".xml") !=false)  || (childeren[i].endsWith(".yawl") !=false)  ){
   						String  fileName=childeren[i];
       					File workflowFile = new File(workflowDir+"/"+fileName);
       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       					Document doc = builder.parse(workflowFile);
       					
       					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
       					Element specificationElement = (Element) specificationNodes.item(0);

       					if (specificationElement.getAttribute("uri").trim().compareToIgnoreCase(workflowName)==0){
       						NodeList conditionNodes = specificationElement.getElementsByTagName("condition");  
           					if (conditionNodes.getLength()>0){
           						for (int j = 0; j < conditionNodes.getLength(); j++) {
           							Element conditionElement = (Element) conditionNodes.item(j);
           							
           							NodeList conditionNameNodes=conditionElement.getElementsByTagName("name");
           							if(conditionNameNodes.getLength()>0){
           								Element conditionNameElement=(Element) conditionNameNodes.item(0);
           								if (conditionList==""){
           									conditionList=Methods.getCharacterDataFromElement(conditionNameElement);
           								}else{
           									conditionList=conditionList+","+Methods.getCharacterDataFromElement(conditionNameElement);
           								}
           							}
           							
           							
           						}
           					}else{
           						conditionList="";
           					}
           					
           					NodeList outputConditionNodes = specificationElement.getElementsByTagName("outputCondition");  
           					if (outputConditionNodes.getLength()>0){
           						for (int j = 0; j < outputConditionNodes.getLength(); j++) {
           							Element conditionElement = (Element) outputConditionNodes.item(j);
           							
           							NodeList conditionNameNodes=conditionElement.getElementsByTagName("name");
           							if(conditionNameNodes.getLength()>0){
           								Element conditionNameElement=(Element) conditionNameNodes.item(0);
           								if (conditionList==""){
           									conditionList=Methods.getCharacterDataFromElement(conditionNameElement);
           								}else{
           									conditionList=conditionList+","+Methods.getCharacterDataFromElement(conditionNameElement);
           								}
           							}else{
          								if (conditionList==""){
           									conditionList=conditionElement.getAttribute("id");
           								}else{
           									conditionList=conditionList+","+conditionElement.getAttribute("id");
           								}

           							}
           							
           							
           						}
           					}else{
           						conditionList="";
           					}
           					
           					
       					}
       					
       				
       				
   					}
   					
   				}
   			}
		} catch (Exception e) {
			return e.getMessage();
		}
			
		

		retValue=conditionList;
		return retValue; 
	}

}
