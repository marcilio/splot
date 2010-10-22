package splot.services.extensions.fundp.handlers.conf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import splot.core.Handler;

public class ConfigurationResultHandler extends Handler{

	public ConfigurationResultHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				HttpSession session = request.getSession(true);	
	        	HttpServletResponse mainResponse=(HttpServletResponse)session.getAttribute("response");
	        	HttpServletRequest  mainRequest=(HttpServletRequest)session.getAttribute("request");
	        	



	}

}
