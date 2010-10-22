package splot.services.extensions.fundp.handlers;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import freemarker.template.Configuration;
import freemarker.template.Template;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.*;

public class SaveWorkflowImageHandler extends FreeMarkerHandler {

	public SaveWorkflowImageHandler(String handlerName, HttpServlet servlet,
			Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buildModel(HttpServletRequest request,
			HttpServletResponse response, Map templateModel)
			throws HandlerExecutionException {
		// TODO Auto-generated method stub
		String responseMessage=""; 
		boolean validData=false;
		boolean fileCreationStatus=false;

		
		try {
			String imageDir=getServlet().getServletContext().getRealPath("/")+ "extensions/workflow_images";  
			String fileName=(String)request.getParameter("fileName").trim();// received file name
			String fileExtention=(String)request.getParameter("fileExtention").trim();

			fileName=fileName.split("\\.")[0]+fileExtention;

			if ((fileName==null)||(fileName.equals("")) || (fileName.trim()=="") ){
				responseMessage="File name is empty.\n";
				
			}else if ((fileName.endsWith(".png")==false)) {
				responseMessage="Illegal file extension.\n";

			}else {
				
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				
				 		if (!isMultipart)
						{
				 		}else{
				 			FileItemFactory factory = new DiskFileItemFactory();
					   		ServletFileUpload upload = new ServletFileUpload(factory);
					   		List items = null;
					   		
					   		try
					   		{
						   		items = upload.parseRequest(request);
					   		}
					   		catch (FileUploadException e)
					   		{
						   		e.printStackTrace();
					   		}
					   		
					   		Iterator itr = items.iterator();
					   		
					   			   		while (itr.hasNext())
					   			   		{
					   			   			FileItem item = (FileItem) itr.next();
					   		
					   			   			if (item.isFormField())
					   						{
					   			   			}
					   						else
					   						{
					   				   			try
					   				   			{
					   					   			String itemName = item.getName();
					   					   			File savedFile = new File(imageDir+"/"+fileName);
					   					   			item.write(savedFile);
					   					   			responseMessage="<b>"+fileName+"</b>"+"    successfully saved in the repository";
					   		
					   					   			
					   				   			}
					   				   			catch (Exception e)
					   				   			{
					   					   			e.printStackTrace();
					   				   			}
					   				   		}
					   			    	}
					   		
				 		}
				
			}


			
		} catch (Exception e) {
			responseMessage=e.getMessage();
		}
		
		Map message=new HashMap();
		List<Map> messages=new LinkedList<Map>();
		message.put("value", responseMessage);
		messages.add(message);
		templateModel.put("messages", messages);
	}

}
