package splot.services.extensions.fundp.handlers.conf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splot.core.Handler;
import splot.services.extensions.fundp.utilities.Methods;

public class GetTasksConfigurationStatusHandler extends Handler {

	public GetTasksConfigurationStatusHandler(String handlerName,
			HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result="";
		int count=0;
		try {
			String modelDir=getServlet().getInitParameter("modelsPath");
    		String configuredModelPath=modelDir+"configured_models";
    		String parsedDir=getServlet().getServletContext().getRealPath("/")+ "extensions/parsed_workflows"; //getServlet().getInitParameter("parsedWorkflowPath");  // directory of parsed workflow's xml files: SPLOT/WebContent/extensions/parsed_workflows

    		
    		
			String featureModel=request.getParameter("fm");
			String workflow=request.getParameter("workflow");
			String instanceID=request.getParameter("id");
			
			featureModel=featureModel.replace("?", " ");
			workflow=workflow.replace("?", " ");
			
			String ConfigurationFileName=Methods.getConfiguredFileName(configuredModelPath, instanceID, featureModel, workflow);
			
			if (ConfigurationFileName.compareToIgnoreCase("false")==0){
				result="false";
			}else{
				String workflowTasks=Methods.getWorkflowTasks(workflow, parsedDir);
				if (workflowTasks.compareToIgnoreCase("false")==0){
					result="false";
				}else{
					String[] taskList=workflowTasks.split("\\,");
					for (int i=0; i<taskList.length;i++){
						System.out.println(taskList[i]);
						String taskStatus=Methods.getTaskLastConfigurationResult(configuredModelPath, ConfigurationFileName, taskList[i]);
						if (!(taskStatus.compareToIgnoreCase("false")==0)){
							count++;
							if (result==""){
								result=taskStatus;
							}else{
								result=result+"*"+taskStatus;
							}
						}
					}
				}
			}
			
			
			
			
			
			
			
		} catch (Exception e) {
			result="false";
		}
		
		if ((!(result.compareToIgnoreCase("false")==0)) && (result!="")) {
			result=Integer.toString(count)+"?"+result;
		}
		
		
		response.getWriter().write(result);
	}

}
