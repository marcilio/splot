package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;

import splot.core.Handler;

/** ResponseWorkflowAndFeatureListHandler is used to response a list of the workflows and the tasks .      
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class ResponseWorkflowAndFeatureListHandler extends Handler{

	/** ResponseWorkflowAndFeatureListHandler is a constructor.  
	 * 
	 * @param handlerName	a string containing the handler's name which this class is responsible for that
	 * @param servlet	the servlet which this handler handles part of its actions  
	 */
	public ResponseWorkflowAndFeatureListHandler(String handlerName,
			HttpServlet servlet) {
		super(handlerName, servlet);
		
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
			String resuStringInString="";
			int arrayIndex=-1;
			String selectedType=request.getParameter("selectedType");
			String selectedValue=request.getParameter("selectedValue");
			
			if (selectedType.compareToIgnoreCase("feature")==0){
				String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath");
				File  dir=new File(viewDir);
				String[]  childeren=dir.list();
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
           					if (selectedValue.compareToIgnoreCase(featureModelName)==0){
               					NodeList taskNodes = doc.getElementsByTagName("task");
            					for (int j = 0; j < taskNodes.getLength(); j++){
            						Element taskElement = (Element) taskNodes.item(j);
        							NodeList workflowList = taskElement.getElementsByTagName("workflow");
        							for (int k = 0; k < workflowList.getLength(); k++){
        								Element workflowElement = (Element) workflowList.item(k);
        								String workflowName=(String)workflowElement.getAttribute("name").trim();
        								if (resuStringInString==""){
        									resuStringInString=workflowName;
        								}else{
        									if (resuStringInString.indexOf(workflowName)==-1){
        										resuStringInString=resuStringInString+","+workflowName;
        									}
        									
        								}

        							}
            					}
           					}
           				}
       				}	
       			}	
			}else if (selectedType.compareToIgnoreCase("workflow")==0){
			
				String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath");
				File  dir=new File(viewDir);
				String[]  childeren=dir.list();
				start:
				for (int i=0;i<childeren.length;i++){
					if (childeren!=null){
						if (childeren[i].endsWith(".xml") !=false){
          					String  fileName=childeren[i];
           					File viewFile = new File(viewDir+"/"+fileName);
           					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
           					Document doc = builder.parse(viewFile);
           					NodeList taskNodes = doc.getElementsByTagName("task");
           					for (int j = 0; j < taskNodes.getLength(); j++){
           						Element taskElement = (Element) taskNodes.item(j);
           						NodeList workflowList = taskElement.getElementsByTagName("workflow");
           						for (int k = 0; k < workflowList.getLength(); k++){
           							Element workflowElement = (Element) workflowList.item(k);
           							String workflowName=(String)workflowElement.getAttribute("name").trim();
           							if (workflowName.compareToIgnoreCase(selectedValue)==0){
           	           					NodeList featureModelNodes = doc.getElementsByTagName("feature_model");  // this tag includes workflow's information
           	           					Element  featureModelElement = (Element) featureModelNodes.item(0);
           	           					String   featureModelName=featureModelElement.getAttribute("name");
           	           					resuStringInString=featureModelName;
           	           					break start;
           	           					

           								
           							}
           						}
           					}

							
						}
					}
				}
				
				
			}
			
			response.getWriter().write(resuStringInString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
