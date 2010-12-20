package splot.services.extensions.fundp.handlers.conf;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

import splot.core.FreeMarkerHandler;
import splot.core.Handler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

public class ViewsConfigurattionStatusHandler extends FreeMarkerHandler {

	public ViewsConfigurattionStatusHandler(String handlerName,
			HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buildModel(HttpServletRequest request,
			HttpServletResponse response, Map templateModel)
			throws HandlerExecutionException {
		// TODO Auto-generated method stub
		String workflowName=request.getParameter("workflowName");
		String taskName=request.getParameter("taskName");
		String featureModelName=request.getParameter("featureModelName");
		String userKey=request.getParameter("userKey");
		String userName=request.getParameter("userName");
		String userID=request.getParameter("userID");

		String placeType=request.getParameter("placeType");
		
		String stopAllocatedViewsResult="";
		
		featureModelName=featureModelName.replace("?", " ");

		
		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; 
		String modelDir=getServlet().getInitParameter("modelsPath");
		String configuredModelPath=modelDir+"configured_models";
	
		
			if ((placeType.compareToIgnoreCase("stop")==0)) {
				String configuredFileName=Methods.getConfiguredFileName(configuredModelPath, userKey);
				System.out.println(configuredFileName);
				if(configuredFileName.compareToIgnoreCase("false")==0){
					Map message=new HashMap();
					List<Map> messages=new LinkedList<Map>();
					message.put("value", "The configuration file not found");
					messages.add(message);
					templateModel.put("messages", messages);

				}else{
					stopAllocatedViewsResult=Methods.checkConfigurationCompletionInStopPlace(featureModelName, viewDir, modelDir, configuredModelPath, taskName, placeType, workflowName, configuredFileName, userName, userID);
					if(stopAllocatedViewsResult.compareToIgnoreCase("true")==0){
						Map message=new HashMap();
						List<Map> messages=new LinkedList<Map>();
						message.put("value", "Configuration status of tasks has been checked");
						messages.add(message);
						templateModel.put("messages", messages);
					}else{
						Map message=new HashMap();
						List<Map> messages=new LinkedList<Map>();
						message.put("value", "Problem in checking of configuration status of the tasks");
						messages.add(message);
						templateModel.put("messages", messages);
					}
				}
				
			 

			}	
				
			
	}
	

	

}
