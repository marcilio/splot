
package splot.services.handlers.conf;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import splar.core.fm.FeatureModel;
import splar.plugins.configuration.bdd.javabdd.BDDConfigurationEngine;
import splar.plugins.configuration.bdd.javabdd.catalog.Product;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import freemarker.template.Configuration;
import freemarker.template.Template;


public class BDDInteractiveConfigurationFilterProductsHandler extends FreeMarkerHandler {

	public BDDInteractiveConfigurationFilterProductsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

        try {	        		        	
        	HttpSession session = request.getSession(false);
        	
        	BDDConfigurationEngine confEngine = (BDDConfigurationEngine)session.getAttribute("conf_engine"); 
//        	do {
//        		confEngine = (BDDConfigurationEngine)session.getAttribute("conf_engine");
//        		try {
//        			if ( confEngine == null ) {
//        				Thread.currentThread().sleep(500);
//        			}
//				} catch (InterruptedException e) {
//				}
//        	} while (confEngine == null);
        	
        	FeatureModel model = confEngine.getModel();
    		if (confEngine == null) {
    			throw new HandlerExecutionException("Configuration engine must be created first");
    		}

        	List templateProducts = new LinkedList();
			List<Product> products = confEngine.getCatalog().filterProductsBasedOnFeatureModelSelection();
			for( Product product : products ) {
        		Map productData = new HashMap();
        		productData.put("id", product.getID());
        		productData.put("name", product.getName());
        		for( String attrName : product.getAttributes().keySet() ) {
        			productData.put(attrName, product.getAttributes().get(attrName));
        		}
        		templateProducts.add(productData);        		
			}

			templateModel.put("products", templateProducts);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HandlerExecutionException("Error filtering products from catalog based on feature model selection", e);
		}
	}
}

