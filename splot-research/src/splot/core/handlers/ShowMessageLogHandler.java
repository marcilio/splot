package splot.core.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splot.core.ErrorManager;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ShowMessageLogHandler extends FreeMarkerHandler {

	public ShowMessageLogHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration,template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
		try {
			templateModel.put("messages", ErrorManager.getManager().getMessages(getServlet().getInitParameter("logFilePath")));
		}  
		catch( FileNotFoundException exc1 ) {
			throw new HandlerExecutionException("Log file not found");
        }
		catch( IOException exc2 ) {
			throw new HandlerExecutionException("Problems reading log file");
        }
        catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}
	}
	
}
