package splot.services.handlers.analyses;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splot.core.Handler;

public class RecordFeatureModelDownloadRequestHandler extends Handler {

	public RecordFeatureModelDownloadRequestHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
	}

	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String modelFileName = (String)request.getParameter("modelFile");
		if ( modelFileName != null ) {
    		String modelLocatorString = getServlet().getInitParameter("modelsPath") + modelFileName;
    		try {
    			
    			// record feature model download
    			// TODO: create a component to record feature model downloads
    			
    			// prevents from injection attacks
    			if ( modelFileName.indexOf("/") != -1 || modelFileName.indexOf("\\") != -1) {
    				throw new Exception("Invalid model filename: '" + modelFileName + "'. Don't use '\\' or '/'.");
    			}
    			
    			// sent feature model bytes to client
    			downloadFeatureModel(response, modelLocatorString);
    			
//    			ByteBuffer buffer = new ;
//    			ByteInputStream inputStream = new 
    			
//	    		FeatureModel model = new XMLFeatureModel(modelLocatorString, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
//	    		model.loadModel();
//	    		
//	    		// convert feature model to a byte array
//	    		ByteArrayOutputStream out = new ByteArrayOutputStream();
//	    		PrintStream savedOut = System.out;
//	    		System.setOut(new PrintStream(out));
//	    		model.dumpXML();
//    			byte[] fmBytes = out.toByteArray();
//	    		try {
//	    			out.close();
//	    		}
//	    		catch(Exception ex) {}
//	    		finally {
//	    			System.setOut(savedOut);
//	    		}
//	    		
//	    		// send feature model bytes to servlet output stream
//	    		response.setContentType("text/plain");
//	    		Writer outputStream = response.getWriter();
//	    		outputStream.write(Arrays.toString(fmBytes));
//	    		outputStream.flush();
//	    		try {
//	    			outputStream.close();
//	    		}
//	    		catch(Exception ex) {}
	    		
    		}
    		catch( Exception e ) {
    			e.printStackTrace();
    		}
		}
	}
	
	private void downloadFeatureModel(HttpServletResponse response, String modelLocatorString) throws Exception {
		FileInputStream fileStream = new FileInputStream(modelLocatorString);
		response.setContentType("text/plain");
		Writer outputStream = response.getWriter();
		int b = fileStream.read();
		while( b != -1 ) {
			outputStream.write(b);
    		b = fileStream.read();
		}
		try {
			fileStream.close();
		}
		catch(Exception e) {}
	}

}

