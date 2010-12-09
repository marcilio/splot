package splot.services.extensions.fundp.handlers.conf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splot.core.Handler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

public class ViewsConfigurattionStatusHandler extends Handler {

	public ViewsConfigurattionStatusHandler(String handlerName,
			HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		String workflowName=request.getParameter("workflowName");
		String taskName=request.getParameter("taskName");
		String featureModelName=request.getParameter("featureModelName");
		String userKey=request.getParameter("userKey");
		String serverKey=request.getParameter("serverKey");
		String userName=request.getParameter("userName");
		String placeType=request.getParameter("placeType");
		
		String stopAllocatedViewsResult="";
		
		featureModelName=featureModelName.replace("?", " ");

		
		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; 
		String modelDir=getServlet().getInitParameter("modelsPath");
		String configuredModelPath=modelDir+"configured_models";

		
		if ((placeType.compareToIgnoreCase("stop")==0)) {
			String configuredFileName=Methods.getConfiguredFileName(configuredModelPath, serverKey);
			
			 try {
				stopAllocatedViewsResult=Methods.checkConfigurationCompletionInStopPlace(featureModelName, viewDir, modelDir, taskName, placeType, workflowName, configuredFileName);
				response.getWriter().write("true?"+stopAllocatedViewsResult);

			} catch (HandlerExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
