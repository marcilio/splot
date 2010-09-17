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

public class ExportFeatureModelHandler extends FreeMarkerHandler {

	public ExportFeatureModelHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	private static final String tempModelsDir = "temp_models";
		
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {		
        try {
    		templateModel.put("model_url", "");
			templateModel.put("model_filename", "");    		
        	String featureModelJSONString = request.getParameter("featureModelJSONString");
        	if ( featureModelJSONString != null ) {	
        		JSONFeatureModel featureModel = new JSONFeatureModel(featureModelJSONString);
        		featureModel.loadModel();		    		
        		String location = getServlet().getInitParameter("modelsPath") + tempModelsDir;
				HttpSession session = request.getSession(true);
				String featureModelFileName = (String)session.getAttribute("modelFileName");
				if ( featureModelFileName == null ) {
					featureModelFileName = genFeatureModelFileName(featureModel) + ".xml";
					session.setAttribute("modelFileName", featureModelFileName);
				}
    			templateModel.put("model_url", "/SPLOT/models/" + tempModelsDir + "/" + featureModelFileName);
				templateModel.put("model_filename", featureModelFileName);
    			saveFeatureModel(featureModel, location + System.getProperty("file.separator") + featureModelFileName);        		
        	}
        }
        catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}
	}
	
	public static String genFeatureModelFileName(FeatureModel featureModel) {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		return "model_" + format.format(new Date()) + "_" + Math.abs(new Random().nextInt());
	}
	
	protected void saveFeatureModel(FeatureModel fm, String location) {		
		DateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy - h:mm a");
		try {
			PrintStream stream = null;
			PrintStream standartOut = System.out;
			stream = new PrintStream(location);
			System.setOut(stream);
			System.out.println("<!-- This model was created online using SPLOT's Feature Model Editor (http://www.splot-research.org) on " + format.format(new Date())+ "  -->");
			fm.dumpXML();
			System.setOut(standartOut);
			stream.flush();
			stream.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}



//Reader reader = request.getReader();
//char cbuf[] = new char[100];
//StringBuffer str = new StringBuffer(100);
//int counter = reader.read(cbuf);
//while( counter != -1 ) {
//	System.out.println("entrei");
//	str.append(new String(cbuf, 0, counter));
//	counter = reader.read(cbuf);
//}