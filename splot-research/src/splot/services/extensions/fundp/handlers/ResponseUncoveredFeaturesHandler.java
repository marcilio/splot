package splot.services.extensions.fundp.handlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splot.core.Handler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.Methods;

public class ResponseUncoveredFeaturesHandler extends  Handler{

	public ResponseUncoveredFeaturesHandler(String handlerName,
			HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views"; 
		String modelDir=getServlet().getInitParameter("modelsPath");
		 

		String featureModelName=request.getParameter("fm_name");
		String type=request.getParameter("type");
		
		String result = null;
		
		try {
			
			if (type.compareToIgnoreCase("allocated")==0){
				result=Methods.getFeatureModelUncoveredFeaturesInAllocatedViews(featureModelName, viewDir, modelDir);

			}else if (type.compareToIgnoreCase("defined")==0){
				result=Methods.getFeatureModelUncoveredFeaturesInAllViews(featureModelName, viewDir, modelDir);
			}else{
				result=Methods.getFeatureModelUncoveredFeaturesInAllocatedViews(featureModelName, viewDir, modelDir);

			}
			
			 
			 response.getWriter().write(result);
			 
		} catch (HandlerExecutionException e) {
			
			e.printStackTrace();
		}

		
	}

}
