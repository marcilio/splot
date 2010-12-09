package splot.services.extensions.fundp.handlers.conf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import splot.services.extensions.fundp.utilities.Methods;
import splot.core.Handler;

public class CheckTaskViewListHandler extends Handler {

	public CheckTaskViewListHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		boolean retValue=false;
		try {
			String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; 
			String workflow=request.getParameter("workflow");
			String featureModelName=request.getParameter("feature_model");
			String taskName=request.getParameter("task");
			
			featureModelName=featureModelName.replace("?", " ");
			
			retValue=Methods.taskHasView(viewDir, workflow, featureModelName, taskName);
			if(retValue){
				response.getWriter().write("true");
			}else{
				response.getWriter().write("false");
			}
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
