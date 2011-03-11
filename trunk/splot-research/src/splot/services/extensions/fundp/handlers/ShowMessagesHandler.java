package splot.services.extensions.fundp.handlers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;

public class ShowMessagesHandler extends FreeMarkerHandler {

	public ShowMessagesHandler(String handlerName, HttpServlet servlet,
			Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buildModel(HttpServletRequest request,
			HttpServletResponse response, Map templateModel)
			throws HandlerExecutionException {
		// TODO Auto-generated method stub
		
		List<Map> messages=new LinkedList<Map>();
		String[] responseMessage=request.getParameter("message").split("\\*");
		for (int i=0;i<responseMessage.length;i++){
			Map message=new HashMap();
			
			message.put("value", responseMessage[i]);
			messages.add(message);
		}
		templateModel.put("messages", messages);
		
	}

}
