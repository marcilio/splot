package splot.services.extensions.fundp.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import splar.core.fm.FeatureModel;
import splar.core.fm.XMLFeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class CreateFeatureModelViewsHandler extends FreeMarkerHandler {

	public CreateFeatureModelViewsHandler(String handlerName, HttpServlet servlet, Configuration configuration,Template template) {
		super(handlerName, servlet, configuration, template);
	}
	/**
	 * This method implements the handler's logic. It basically fills the templateModel map with the necessary information
	 * that will be later referenced by this handler's freemarker template.
	 */
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
		
        try {
        	
        	// note that these variables will be visible to the freemarker template file
        	
        	// student 1 hash
        	Map student1 = new HashMap();
        	student1.put("name", "Paul");
        	student1.put("age", 24);
        	
        	// student 2 hash
        	Map student2 = new HashMap();
        	student2.put("name", "John");
        	student2.put("age", 28);
        	
        	// list of students
        	List students = new LinkedList();
        	students.add(student1);
        	students.add(student2);

        	// university hash
        	Map university = new HashMap();
        	university.put("name", "FUNDP");
        	university.put("num_programs", 50);
        	university.put("students", students);
        	
        	templateModel.put("university", university);
        	
        }
        catch (Exception e) {
        	throw new HandlerExecutionException(e.getMessage());
		}
	}

}

