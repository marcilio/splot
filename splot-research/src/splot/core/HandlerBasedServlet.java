package splot.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HandlerBasedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	
	Set<Handler> handlers = null;
	
    public HandlerBasedServlet() {
        super();
        handlers = new HashSet<Handler>();
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		createHandlers(config);
		
	}	

    public abstract void createHandlers(ServletConfig config);
	
	
    public void addHandler(Handler handler) {
    	handlers.add(handler);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		run(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		run(request, response);
	}
    
    
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		boolean handled = false;
		for( Handler handler : handlers ) {
			if ( handler.canHandle(request) ) {
				handler.run(request, response);
				handled = true;
				break;
			}
		}
		if ( !handled ) {
			writer.println("Bad request!");
			writer.println("<p>Handlers:");
			for( Handler handler : handlers ) {
				writer.println("<br>- " + handler.getName() );
			}
		}
	}    
}
