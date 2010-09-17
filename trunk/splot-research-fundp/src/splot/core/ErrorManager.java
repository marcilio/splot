package splot.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ErrorManager {

	private static Object lock = new Object();
	private static ErrorManager manager = null;
	
	private ErrorManager() {
	}

	public static ErrorManager getManager() {
		synchronized (lock) {
			if ( manager == null ) {
				manager = new ErrorManager();
			}
		}
		return manager;
	}
	
	
	public void logError( String logFile, Handler handler, String message, String user ) {
		synchronized (lock) {
			try {
				PrintWriter writer = new PrintWriter(new FileWriter(logFile,true));
			    Calendar cal = Calendar.getInstance();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				writer.println( sdf.format(cal.getTime()) + ": [user=" + user + ", handler=" + handler.getName() +",message=" + message+"]");
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> getMessages(String logFile) throws FileNotFoundException, IOException {
		List<String> messages = new LinkedList<String>();
		LineNumberReader reader = new LineNumberReader( new FileReader(logFile) );
		String message = reader.readLine();
		while( message != null ) {
			messages.add(message);
			message = reader.readLine();
		}
		reader.close();		
		return messages;
	}
}
