package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import splot.core.Handler;
import splot.services.extensions.fundp.utilities.Methods;



public class CreateDependencySetHandler extends Handler {

	public CreateDependencySetHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		try {
			String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; //getServlet().getInitParameter("viewFilesPath");
    		String modelDir=getServlet().getServletContext().getRealPath("/")+ "models";
    		String workflowDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows"; 

    		
			
			String  featureModel=request.getParameter("featureModel").trim();
			String  workflow=request.getParameter("workflow").trim();
			
			
			
			String viewFileName=Methods.getFeatureModelViewFileName(viewDir, featureModel);
			String viewFilePath=viewDir+"/"+viewFileName;

			String featureModelFileName=Methods.getfeatureModelFileName(modelDir, featureModel);
			String featureModelPath=modelDir+"/"+featureModelFileName;
			
			
			String workflowFileName=Methods.getWorkflowFileName(workflow, workflowDir);
			String workflowPath=workflowDir+"/"+workflowFileName;
			
			
			
			
			String retVal=Methods.setDependencySet(workflowPath, viewFilePath, featureModelPath,featureModel,workflow);
			
			
			response.getWriter().write(retVal);
			
			
		} catch (Exception e) {
			response.getWriter().write("false");
		}

		

		
		
		
	}

}
