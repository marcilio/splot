package splot.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Handler {
 
	private String handlerName;
	private HttpServlet servlet;
	
	public Handler(String handlerName, HttpServlet servlet) {
		this.handlerName = handlerName;
		this.servlet = servlet;
		HandlerManager.getInstance().addHandler(this);
	}
	
	public HttpServlet getServlet() {
		return servlet;
	}
	
	public String getName() {
		return handlerName;
	}
	
	public boolean canHandle(HttpServletRequest request) {
		return request.getParameter("action").compareToIgnoreCase(handlerName) == 0;
	}
	
	public abstract void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
