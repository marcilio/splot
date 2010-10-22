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

public class ShowWorkflowInfoHandler extends FreeMarkerHandler{

	public ShowWorkflowInfoHandler(String handlerName, HttpServlet servlet,
			Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buildModel(HttpServletRequest request,HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
		

		try {
			String viewDir=null;
			String imageDir=null;
			String dirType=(String)request.getParameter("dirType");
			if ((dirType.isEmpty()) || (dirType=="") || (dirType==null)){
				dirType="parsed";
			}
			
			if(dirType.compareToIgnoreCase("parsed")==0){
	     		 viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows";//getServlet().getInitParameter("parsedWorkflowPath");
			}else if(dirType.compareToIgnoreCase("imported")==0) {
	     		 viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/imported_workflows";//getServlet().getInitParameter("parsedWorkflowPath");

			}else{
	     		 viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows";//getServlet().getInitParameter("parsedWorkflowPath");

			}
			
			imageDir=getServlet().getServletContext().getRealPath("/")+ "extensions/workflow_images";
			
			
    		String modelFileName = (String)request.getParameter("selectedModels");
    		if ((modelFileName==null) || (modelFileName=="")){
    			templateModel.put("error", "There is no selected model.");
    		}else{
    			templateModel.put("error", "");
    		
    		
    		
				File file = new File(viewDir+"/"+modelFileName);
				
				String imagefileName=modelFileName.split("\\.")[0]+".png";
				
				File imagefile = new File(imageDir+"/"+imagefileName);
				
				if (imagefile.exists()){
					templateModel.put("imageFileName", imagefileName);
				}else{
					templateModel.put("imageFileName", "false");
				}
				
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
						
						try {
							NodeList title = taskElement.getElementsByTagName("name");
						    Element taskName = (Element) title.item(0);
						    NodeList decomposesToElement = null;
						    String decomposesTo="";
						    List<Map> decomposesList =new LinkedList<Map>();
						    try {
							    decomposesToElement=taskElement.getElementsByTagName("decomposesTo");
							    for(int k=0;k< decomposesToElement.getLength();k++){
								    Element decomposesToElementName = (Element) decomposesToElement.item(k);
								    decomposesTo=decomposesToElementName.getAttribute("id");
								    Map decomposesInfo = new HashMap();
								    decomposesInfo.put("decompose_id", decomposesTo);
								    decomposesList.add(decomposesInfo);
								    
							    }
							    
							  
	
							} catch (Exception e) {
								decomposesTo="";
								decomposesList=null;
							
	
							}
						    
							  Map taskInfo = new HashMap();
							    taskInfo.put("task_name", Methods.getCharacterDataFromElement(taskName));
							    if (decomposesList.isEmpty()) {
							    	 taskInfo.put("decomposes_exists", "false");
							    }else{
							    	 taskInfo.put("decomposes_exists", "true");
	
							    }
							    	
							    taskInfo.put("decomposes", decomposesList);			        
							    tasklist.add(taskInfo);
							
						} catch (Exception e) {
							e.printStackTrace();
	
						}
	
					}
					 decompositionInfo.put("decomp_info", tasklist);
					 decompositionlist.add(decompositionInfo);
				}
				
				templateModel.put("specification_list", decompositionlist);
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Problems locating/acessing the workflow specification repository path", e);

		}
	}

}
