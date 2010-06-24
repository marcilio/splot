package splot.services.handlers.editor;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import splar.core.fm.FeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ConfigureFeatureModelHandler extends ExportFeatureModelHandler {

	public ConfigureFeatureModelHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}
}
