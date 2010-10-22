package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

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

import splot.core.Handler;


public class ParseSelectedWorkflowSpecificationHandler extends Handler {

	public ParseSelectedWorkflowSpecificationHandler(String handlerName,
			HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String parsedDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows"; 
			String importedDir=getServlet().getServletContext().getRealPath("/")+ "extensions/imported_workflows"; 
			String workflowName=(String)request.getParameter("workflow");
			String result=parseWorkflow(parsedDir, importedDir, workflowName);
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	protected String parseWorkflow(String parsedDir, String importedDir, String workflow){
		String retVal="false";
		boolean found=false;
		String  workflowFileName=null;
		String xmlData=null;


		try {
			
			if((workflow.isEmpty())|| (workflow=="") || (workflow==null)){
				retVal="Workflow name is empty";
			}else{
				File  dir=new File(importedDir);
	       		String[]  childeren=dir.list();
	       		if (childeren!=null){
	       			for (int i=0;(i<childeren.length) && (!found);i++){
	       				if ((childeren[i].endsWith(".xml") !=false) ||  (childeren[i].endsWith(".yawl") !=false)){
	       					String  fileName=childeren[i];
	       					File importfile = new File(importedDir+"/"+fileName);
	       					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	       					Document doc = builder.parse(importfile);
	       					NodeList specificationNodes = doc.getElementsByTagName("specification");  // this tag includes workflow's information
	       					Element specificationElement = (Element) specificationNodes.item(0);
	       					String workflowName=specificationElement.getAttribute("uri");
	       					
	       					if (workflowName.compareToIgnoreCase(workflow)==0){
	       						found=true;
	       						workflowFileName=fileName;
	           					StringWriter stw = new StringWriter(); 
	           					Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
	           					serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
	           					xmlData=stw.toString();

	       						break;
	       					}
	       					

	       				}

	       			}

	       		}

	       		if(found){
	       			if((xmlData==null) || (xmlData.isEmpty()) || (xmlData=="")){
	       				retVal="File content is empty";
	       			}else{
	   					File exportFile=new File(parsedDir+"/"+workflowFileName);
       					File importfile = new File(importedDir+"/"+workflowFileName);

       					if (exportFile.exists()){
       						retVal="File exists in the parsed workflow files";
       					}else{
   							FileWriter fw=new FileWriter(parsedDir+"/"+workflowFileName);
   							fw.write(xmlData);
   							fw.close();  
   							importfile.delete();
   							retVal="true";
       					}
	       			}
	       			
	       		}else{
	       			retVal="Workflow file name not found";
	       		}
			}
			
		} catch (Exception e) {
			retVal=e.getMessage();
		}
		return retVal;
	}

}
