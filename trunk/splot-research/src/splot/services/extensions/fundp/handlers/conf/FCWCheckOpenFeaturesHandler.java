package splot.services.extensions.fundp.handlers.conf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splot.core.Handler;
import splot.services.extensions.fundp.utilities.Methods;

public class FCWCheckOpenFeaturesHandler extends Handler {

	public FCWCheckOpenFeaturesHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			try {
				
				
				String viewName=request.getParameter("viewName");
				String selectedModels=request.getParameter("selectedModels");
				String taskName=request.getParameter("taskName");
				String userKey=request.getParameter("userKey");
				String workflowName=request.getParameter("workflowName");
				String modelName=request.getParameter("modelName");
				String userName=request.getParameter("userName");
				String userID=request.getParameter("userID");
				
				String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; //getServlet().getInitParameter("viewFilesPath");
	    		String modelDir=getServlet().getInitParameter("modelsPath");
	    		String configuredModelPath=modelDir+"/configured_models";
	    		
	            String configurationFileName=Methods.getConfiguredFileName(configuredModelPath, userKey);
	            
				if(configurationFileName.compareToIgnoreCase("false")!=0){
					
					String viewFileName=Methods.getFeatureModelViewFileName(viewDir, modelName);
					
					String retVal=Methods.getOpenFeaturesList(viewDir, modelDir, configuredModelPath, viewName, modelName, viewFileName, selectedModels, configurationFileName, userKey);
					response.getWriter().write(retVal);
					
				}else{
					response.getWriter().write("fileNotFound");
				}
				

				
			} catch (Exception e) {
				// TODO: handle exception
			}
		
	}

}
