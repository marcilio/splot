package splot.services.extensions.fundp.handlers.conf;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONValue;

import splar.core.fm.configuration.ConfigurationEngine;
import splot.core.Handler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

public class ConfigurationResultHandler extends Handler{

	public ConfigurationResultHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		
		try {
			

			String receivedWorkflowName=request.getParameter("workflowName");
			String receivedTaskName=request.getParameter("taskName");
			String receivedFeatureModelName=request.getParameter("featureModelName");
			String receivedUserKey=request.getParameter("userKey");
			String receivedUserName=request.getParameter("userName");
			String sessionKey=request.getParameter("sessionKey");
			
			String modelDir=getServlet().getInitParameter("modelsPath");
    		String configuredModelPath=modelDir+"configured_models";
    		
	    	

    		receivedFeatureModelName=receivedFeatureModelName.replace("?", " ");

 	    	String pureString=getConfigurationStatus(configuredModelPath, receivedUserKey, receivedFeatureModelName, receivedWorkflowName, receivedTaskName, receivedUserName,sessionKey);
 	    	response.getWriter().write(pureString);
	
	    	
	    		

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getConfigurationStatus(String configuredModelPath, String userKey, String featureModelName, String workflowName,String taskName,String userName, String sessionKey){
		String retVal="";
		
		
		String configuredFileName=Methods.getConfiguredFileName(configuredModelPath, userKey, featureModelName, workflowName);
		if (configuredFileName.compareToIgnoreCase("false")==0){
			 retVal=taskName+"*"+"false"+"*"+"false"+"*"+"The configuration file not found."+"*"+sessionKey;
		}else{
			
			String result=Methods.getTaskConfigurationResult(configuredModelPath, configuredFileName, taskName,userName,sessionKey);
			if ((result.compareToIgnoreCase("fasle")==0) || (result=="")){
				retVal=taskName+"*"+"false"+"*"+"false"+"*"+"The requested information not found."+"*"+sessionKey;
			}else{
    			//String serverKey=Methods.getConfiguredFileServerKey(configuredModelPath, userKey, featureModelName, workflowName);
    			//retVal=taskName+"*"+serverKey+"*"+"true"+"*"+result+"*"+sessionKey;
			}
		}	

		return retVal;
	}

}
