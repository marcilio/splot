package splot.services.extensions.fundp.handlers.conf;



import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureTreeNode;
import splar.core.fm.XMLFeatureModel;
import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationStep;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;


import freemarker.template.Configuration;
import freemarker.template.Template;

import splot.services.extensions.fundp.utilities.*;




public abstract class FCWInteractiveConfigurationMainHandler2 extends FreeMarkerHandler {

	protected FCWInteractiveConfigurationElementsProducer confElementProducer = null;

	public FCWInteractiveConfigurationMainHandler2(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	} 
	
	protected abstract ConfigurationEngine createConfigurationEngine(String modelLocatorString) throws HandlerExecutionException;
	protected abstract String getResourcePath();	
	protected abstract String getFeatureTemplateFile();
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {
				
				
        try {	
        	
            HttpSession session=null;
        	String  newSession="false";
        	String workflowExistence="false";
        	String requestQueryString=request.getQueryString();
        	String  workflow="false";
        	String viewType="none";
        	String viewName="none";
        	String  task="false";
        //	String  placeType="false";
        	//String stopAllocatedViewsResult="";

        	String  featureModelName="false";
        	String featureModelFileName="false";
        	String  userKey="false";
        	String  serverKey="";
        	String newConfiguration="false";
        	String  user="guest";
        	String  sessionKey="";
        	
    		String viewDir=getServlet().getServletContext().getRealPath("/")+ "extensions/views/"; //getServlet().getInitParameter("viewFilesPath");
    		String modelDir=getServlet().getInitParameter("modelsPath");
    		String configuredModelPath=modelDir+"/configured_models";

 
    		

        	
       		/*********************************************************************
    		 * Check if the user starts a new session
    		 *********************************************************************/
            	if (!(requestQueryString.indexOf("newSession")==-1)){
            		newSession=(String)request.getParameter("newSession");
            		if (!((newSession==null)) && (!(newSession=="")) && (newSession.compareToIgnoreCase("true")==0)){
            			newSession="true";
            		}else{
            			newSession="false";

            		}
            		
            	}else{
            		newSession="false";
            	}
        	
            	
            	if (newSession.compareToIgnoreCase("true")==0){
            		session=request.getSession();
            		

            	}else{
            		session=request.getSession(true);

            	}
        	
            	session.setAttribute("newSession", newSession);
            	

            	/*********************************************************************
        		 * Check if the configuration is workflow based or not
        		 *********************************************************************/

            	if (!(requestQueryString.indexOf("workflowExistence")==-1)){
                	workflowExistence=(String)request.getParameter("workflowExistence");
                	if ((workflowExistence==null)|| (workflowExistence=="") || (workflowExistence.compareToIgnoreCase("true")!=0)){
                		workflowExistence="false";
                	}
            	}else{
            		workflowExistence="false";
            	}

            	session.setAttribute("workflowExistence", workflowExistence);
            	
            	

            	
           		/*********************************************************************
        		 * workflow name
        		 *********************************************************************/
            	

            	
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
            			if (requestQueryString.indexOf("workflowName")==-1){
            				workflow="false";

        					throw new HandlerExecutionException("Problem finding the workflow name");
            			}else{
            				workflow=(String)request.getParameter("workflowName");
            				if ((workflow=="") || (workflow==null) || (workflow.compareToIgnoreCase("false")==0)){
                				workflow="false";
            					throw new HandlerExecutionException("Problem finding the workflow name");

            				}
            			}
            		}else{
            			
            			if (requestQueryString.indexOf("workflowName")==-1){
                			workflow=(String)session.getAttribute("workflowName");
            				if ((workflow=="") || (workflow==null) ||(workflow.compareToIgnoreCase("false")==0)){
                				workflow="false";
            					throw new HandlerExecutionException("Problem finding the workflow name");

            				}else{
            					
            				}
            				
            			}else{

            				workflow=(String)request.getParameter("workflowName");
            				if ( (workflow=="") || (workflow==null)||(workflow.compareToIgnoreCase("false")==0)){
                				workflow="false";
            					throw new HandlerExecutionException("Problem finding the workflow name");

            				}
            			}
             		}
            	}else{
    				workflow="false";
            	}
            	
            	session.setAttribute("workflowName", workflow);
            	
           		/*********************************************************************
        		 * task name
        		 *********************************************************************/
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
            			if (requestQueryString.indexOf("taskName")==-1){
            				task="false";
        	            	throw new HandlerExecutionException("Problem finding the task name");
            			}else{
                			task=(String)request.getParameter("taskName");
                			if ((task=="") || (task==null)||(task.compareToIgnoreCase("false")==0)){
                				task="false";
            	            	throw new HandlerExecutionException("Problem finding the task name");
                			}
            			}

            		}else{
            			
            			if (requestQueryString.indexOf("taskName")==-1){
                			
                			task=(String)session.getAttribute("taskName");
               				if ((task=="") || (task==null) ||(task.compareToIgnoreCase("false")==0)){
                				task="false";
            					throw new HandlerExecutionException("Problem finding the task name");

            				}
               				
            			}else{
                			task=(String)request.getParameter("taskName");
                			if ((task=="") || (task==null)||(task.compareToIgnoreCase("false")==0)){
                				task="false";
            	            	throw new HandlerExecutionException("Problem finding the task name");
                			}
            			}
             		}
            	}else{
            		task="false";
            	}
            	session.setAttribute("taskName", task);
            
            	
            	
           		/*********************************************************************
        		 * session key
        		 *********************************************************************/

            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		sessionKey=request.getParameter("sessionKey");
            		
            	}
            	

 

           		/*********************************************************************
        		 * feature model name
        		 *********************************************************************/
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
            			if (requestQueryString.indexOf("featureModelName")==-1){
            				featureModelName="false";
        	            	throw new HandlerExecutionException("Problem finding the feature model name");
            			}else{
                			featureModelName=(String)request.getParameter("featureModelName");
                			featureModelName=featureModelName.replace("?", " ");

                			if ( (featureModelName=="") || (featureModelName==null) ||(featureModelName.compareToIgnoreCase("false")==0)){
                   				featureModelName="false";
            	            	throw new HandlerExecutionException("Problem finding the feature model name");
                			}
            			}

           
            		}else{
            			if (requestQueryString.indexOf("featureModelName")==-1){
            				featureModelName=(String)session.getAttribute("featureModelName");
              				if ((featureModelName=="") || (featureModelName==null)||(featureModelName.compareToIgnoreCase("false")==0)){
              					featureModelName="false";
            					throw new HandlerExecutionException("Problem finding the feature model name");

            				}

            			}else{
                			featureModelName=(String)request.getParameter("featureModelName");
                			featureModelName=featureModelName.replace("?", " ");

                			if ((featureModelName=="") || (featureModelName==null)||(featureModelName.compareToIgnoreCase("false")==0)){
                   				featureModelName="false";
            	            	throw new HandlerExecutionException("Problem finding the feature model name");
                			}
            			}
            		}
            	}else{
        			if (requestQueryString.indexOf("featureModelName")==-1){
                		featureModelName=(String)session.getAttribute("featureModelName");
                   
                		if ((featureModelName=="") || (featureModelName==null)||(featureModelName.compareToIgnoreCase("false")==0)){
                        	featureModelName="false";
                		}
        			}else{
            			featureModelName=(String)request.getParameter("featureModelName");
            			featureModelName=featureModelName.replace("?", " ");

                		if ((featureModelName=="") || (featureModelName==null)|| (featureModelName.compareToIgnoreCase("false")==0)){
                			featureModelName="false";
                		}
        				
        			}
            	}

            	session.setAttribute("featureModelName", featureModelName);

	        	
	    		/*********************************************************************
	    		 * Feature model file name
	    		 *********************************************************************/
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
            			if (featureModelName.compareToIgnoreCase("false")==0){
    	        			featureModelFileName="false";
        	            	throw new HandlerExecutionException("Problem finding the feature model name");
            			}else{
            				featureModelFileName=Methods.getfeatureModelFileName(modelDir, featureModelName);
        	        		if ((featureModelFileName==null) ||(featureModelFileName=="")  || (featureModelFileName.compareToIgnoreCase("false")==0)){
        	        			featureModelFileName="false";
            	            	throw new HandlerExecutionException("Problem finding the feature model file name");
        	        		}
            				
            			}
            			
            		}else{
    	        		featureModelFileName=(String)session.getAttribute("selectedModels");
    	        		if ((featureModelFileName==null) ||(featureModelFileName=="")  || (featureModelFileName.compareToIgnoreCase("false")==0)){
       	        			featureModelFileName="false";
        	            	throw new HandlerExecutionException("Problem finding the feature model file name");
    	        		}
            		}

            	}else{
    	        	if (requestQueryString.indexOf("selectedModels")!=-1){
    	        		featureModelFileName=(String)request.getParameter("selectedModels");
    	        		if ((featureModelFileName==null) ||(featureModelFileName=="") || (featureModelFileName.compareToIgnoreCase("false")==0)){
    	        			featureModelFileName="false";
        	            	throw new HandlerExecutionException("Problem finding the feature model file name");

    	        		}
    	        	}else{
    	        		featureModelFileName=(String)session.getAttribute("selectedModels");
    	        		if ((featureModelFileName==null) ||(featureModelFileName=="") || (featureModelFileName.compareToIgnoreCase("false")==0)){
    	        			featureModelFileName="false";
        	            	throw new HandlerExecutionException("Problem finding the feature model file name");

    	        			
    	        		}
    	        	}

            	}

            	session.setAttribute("selectedModels", featureModelFileName);
            	
	        	ConfigurationEngine confEngine = (ConfigurationEngine)session.getAttribute("conf_engine");

	        	
	    		/*********************************************************************
	    		 * RESET configuration
	    		 *********************************************************************/
	            String op = (String)request.getParameter("op");        
//	            if ( op == null || op.compareToIgnoreCase("reset") != 0) {
//	            	throw new HandlerExecutionException("Paremeter 'op' must be specified to 'reset'");
//	            }
//	            
	            if ( op == null) {
	            	op="reset";
	            }
	            
	            
	            if(op.compareToIgnoreCase("reset")==0){
	            	if(confEngine == null  ){
			        	confEngine = createConfigurationEngine(getResourcePath()+featureModelFileName);
			        	confEngine.reset();
			    		session.setAttribute("conf_engine", confEngine);
			    		
		        	}
		    		else {
		    			
		    			confEngine.reset();
		    		}	
	            
	            }else if (op.compareToIgnoreCase("rebuild")==0){
	            	
	            }
	            
	        	
	        	
				FeatureModel featureModel = null;
				featureModel = new XMLFeatureModel(modelDir+featureModelFileName, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				featureModel.loadModel();

        		if ((featureModelName==null) ||(featureModelName=="") || (featureModelName.compareToIgnoreCase("false")==0)){
        			featureModelName=confEngine.getModel().getName();
        		}
	        	
        		
           		/*********************************************************************
        		 * view name
        		 *********************************************************************/
            	if ((workflowExistence.compareToIgnoreCase("true")==0 )   ){
            		if (newSession.compareToIgnoreCase("true")==0){
            			
            			
                		if (requestQueryString.indexOf("viewName")==-1){

                			if ((workflow.compareToIgnoreCase("false")==0) || (featureModelName.compareToIgnoreCase("false")==0) || (task.compareToIgnoreCase("false")==0)){
                				viewName="none";
            	            	throw new HandlerExecutionException("Problem finding the view name");

                			}else{
                				String tmpViewName=	Methods.getTaskViewName(viewDir, workflow, featureModelName, task);
                				

                				if ((tmpViewName.compareToIgnoreCase("false")==0) ||(tmpViewName==null) || (tmpViewName=="") ){
                    				viewName="none";
                	            	throw new HandlerExecutionException("Problem finding the view name");
                				}else{
                					viewName=tmpViewName;
                				}
                			}
                			
                		}else{
                			viewName=(String)request.getParameter("viewName");
                			if ((viewName==null) ||(viewName=="") ){
                				viewName="none";
                			}

                		}
            		}else{
                		if (requestQueryString.indexOf("viewName")==-1){
                			viewName=(String)session.getAttribute("viewName");
                			if ((viewName==null) ||(viewName=="") ){
                				viewName="none";
                			}
                		}else{
                			viewName=(String)request.getParameter("viewName");
                			if ((viewName==null) ||(viewName=="") ){
                				viewName="none";
                			}
                		}
            		}
            	}else{
            		if (requestQueryString.indexOf("viewName")==-1){
            			viewName=(String)session.getAttribute("viewName");
            			if ((viewName==null) ||(viewName=="") ){
            				viewName="none";
            			}
            		}else{
            			viewName=(String)request.getParameter("viewName");
            			if ((viewName==null) ||(viewName=="") ){
            				viewName="none";
            			}

            		}
            	}

            	session.setAttribute("viewName", viewName);
            	
            	
            	
            	
            	
            	
            	
            	
            	
           		/*********************************************************************
        		 * view type
        		 *********************************************************************/
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
                		if (requestQueryString.indexOf("viewType")==-1){
                			viewType="none";
                		}else{
                			viewType=(String)request.getParameter("viewType");
                			if ((viewType==null) ||(viewType=="") ){
                    			viewType="none";
                			}

                		}
             		}else{
                 		if (requestQueryString.indexOf("viewType")==-1){
                			viewType=(String)session.getAttribute("viewType");
                			if ((viewType==null) ||(viewType=="") ){
                    			viewType="none";
                			}
                		}else{
                			viewType=(String)request.getParameter("viewType");
                			if ((viewType==null) ||(viewType=="") ){
                    			viewType="none";
                			}

                		}
            			
            		}

            	}else{
             		if (requestQueryString.indexOf("viewType")==-1){
            			viewType=(String)session.getAttribute("viewType");
            			if ((viewType==null) ||(viewType=="") ){
                			viewType="none";
            			}
            		}else{
            			viewType=(String)request.getParameter("viewType");
            			if ((viewType==null) ||(viewType=="") ){
                			viewType="none";
            			}

            		}
            	}

            	if (viewName.compareToIgnoreCase("none")==0){
        			viewType="none";
            	}
            	
            	session.setAttribute("viewType", viewType);


            	
            	
            	
            	
            	
           		/*********************************************************************
        		 * user key
        		 *********************************************************************/
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
                 		if (requestQueryString.indexOf("userKey")==-1){
                 			userKey="false";
        	            	throw new HandlerExecutionException("Problem finding the user key");

                 		}else{
                 			userKey=(String)request.getParameter("userKey");
                			if ((userKey==null) ||(userKey=="")  || (userKey.compareToIgnoreCase("false")==0)){
                     			userKey="false";
            	            	throw new HandlerExecutionException("Problem finding the user key");
                			}

                 		}

            		}else{
                 		if (requestQueryString.indexOf("userKey")==-1){
                 			userKey=(String)session.getAttribute("userKey");
                			if ((userKey==null) ||(userKey=="")  || (userKey.compareToIgnoreCase("false")==0)){
                     			userKey="false";
            	            	throw new HandlerExecutionException("Problem finding the user key");
                			}
        	            	

                 		}else{
                 			userKey=(String)request.getParameter("userKey");
                 			if ((userKey==null) ||(userKey=="")  || (userKey.compareToIgnoreCase("false")==0)){
                     			userKey="false";
            	            	throw new HandlerExecutionException("Problem finding the user key");
                			}

                 		}
            		}

            	}else{
            		userKey="false";
            	}
            	session.setAttribute("userKey", userKey);

            	
            	
            	
           		/*********************************************************************
        		 * server key
        		 *********************************************************************/
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
                 		if (requestQueryString.indexOf("serverKey")==-1){
                 			newConfiguration="true";
                 			serverKey="";
                 		}else{
                			serverKey=(String)request.getParameter("serverKey");
                			if ( (serverKey!=null) && (serverKey!="")){
                    			newConfiguration="false";
                    		}else{
                    			newConfiguration="true";
                    			serverKey="";
                    		}
                 			
                 		}
            		}else{
            			
            			if (requestQueryString.indexOf("serverKey")==-1){
            				serverKey=(String)session.getAttribute("serverKey");
                			if ( (!(serverKey!=null)) && (serverKey!="")){
                    			newConfiguration="false";
                    		}else{
                    			newConfiguration="true";
                    			serverKey="";
                    		}
            			}else{
            				serverKey=(String)request.getParameter("serverKey");
                			if ( (serverKey!=null) && (serverKey!="")){
                    			newConfiguration="false";
                    		}else{
                    			newConfiguration="true";
                    			serverKey="";
                    		}
            			}
            			
             			
            		}

            	}else{
            		serverKey="";
            		if (newSession.compareToIgnoreCase("true")==0){
            			newConfiguration="true";
            		}else{
            			newConfiguration="false";

            		}

            	}
            	session.setAttribute("serverKey", serverKey);
            	
            	
           		/*********************************************************************
        		 * user
        		 *********************************************************************/
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
            			if (requestQueryString.indexOf("userName")==-1){
            				user="guest";
            			}else{
            				user=(String)request.getParameter("userName");
                			if ((user==null) ||(user=="") ){
                				user="guest";
                			}
            			}
            			
            		}else{
            			
            			if (requestQueryString.indexOf("user")==-1){
            				user=(String)session.getAttribute("userName");
                			if ((user==null) ||(user=="") ){
                				user="guest";
                			}
            			}else{
            				user=(String)request.getParameter("userName");
                			if ((user==null) ||(user=="") ){
                				user="guest";
                			}
            			}
            		}

            	}else{
            		user="guest";
            	}
            		 
            	session.setAttribute("userName", user);

            	
            	
           		/*********************************************************************
        		 * Process Status
        		 *********************************************************************/
            	if (workflowExistence.compareToIgnoreCase("true")==0){
            		if (newSession.compareToIgnoreCase("true")==0){
                    	session.setAttribute("processStatus", "progress");

            		}
            	}

	            
           		/*********************************************************************
        		 * uncovered features
        		 *********************************************************************/

	            String uncoveredFeatures="";
	            if ((newSession.compareToIgnoreCase("true")==0) && (workflowExistence.compareToIgnoreCase("true")==0)){
	            	
	            	uncoveredFeatures=Methods.getFeatureModelUncoveredFeaturesInAllocatedViews(featureModelName, viewDir, modelDir);
	            }
	        
	            
	            session.setAttribute("uncoveredFeatures", uncoveredFeatures);
	            

	            
	            
	            
	    		
				// Producer of parts of the template
	        	if ( confElementProducer == null ) {
	        		confElementProducer = new FCWInteractiveConfigurationElementsProducer(getTemplateConfiguration());
	        	}
				
				// Traverses steps and identify several related parameters
	        
	        	
	    	
	        	List<Map> featuresList = new LinkedList<Map>();	
	    		
	        	
	    	
	        	if (workflowExistence.compareToIgnoreCase("true")==0){
	        		
	        		if(op.compareToIgnoreCase("reset")==0){
	        			if (newConfiguration.compareToIgnoreCase("true")==0){
	        				List<Map> stepsList = new LinkedList<Map>();
				        	for( ConfigurationStep step : confEngine.getSteps() ) {
				        		Map stepData = new HashMap();
				    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
				        		stepData.put("configurationStepElement", stepElementData);
				        		stepsList.add(stepData);

				        		
				        	}
				        	templateModel.put("steps", stepsList);
				        
				        	
							LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
							getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

				    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
				    			Map featureData = new HashMap();
				    			
				    		
				    		
				    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
				    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
				    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
				    			featureData.put("configurationFeatureElement", featureElementData);
				    			featuresList.add(featureData);
				    			
				    		}
	        			}else{//newConfiguration==false
	        				
	        				List<ConfigurationStep> stepsToUpdateList1 = new LinkedList<ConfigurationStep>();
	        				FeatureDecisionInfo decisionResult=new FeatureDecisionInfo();
	    	    			
	        				for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
	        					decisionResult=Methods.getFeatureDecisionInfo(modelDir, serverKey, feature.getID());
	        					if (decisionResult.found){
	        						stepsToUpdateList1.add( confEngine.configure(feature.getID(), decisionResult.value.equals("1") ? 1 : 0) );
	        					}
	        				}
	    	    			
	        	        	
	        				
	        				List<Map> stepsList = new LinkedList<Map>();
	        	        	for( ConfigurationStep step : stepsToUpdateList1 ) {
	        	        		Map stepData = new HashMap();
	        	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
	        	        		stepData.put("configurationStepElement", stepElementData);
	        	        		stepsList.add(stepData);
	        	        	}
	        	        	templateModel.put("steps", stepsList);
	        	        	
	        	        	LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
	    					getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

	    		    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
	    		    			Map featureData = new HashMap();
	    		    			
	    		    		
	    		    		
	    		    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
	    		    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
	    		    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
	    		    			featureData.put("configurationFeatureElement", featureElementData);
	    		    			featuresList.add(featureData);
	    		    			
	    		    		}
	        			}
	        		}else if (op.compareToIgnoreCase("rebuild")==0){
	        			if(newConfiguration.compareToIgnoreCase("true")==0){
	        				
	        				List<Map> stepsList = new LinkedList<Map>();
				        	for( ConfigurationStep step : confEngine.getSteps() ) {
				        		Map stepData = new HashMap();
				    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
				        		stepData.put("configurationStepElement", stepElementData);
				        		stepsList.add(stepData);

				        		
				        	}
				        	templateModel.put("steps", stepsList);
				        
				        	
							LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
							getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

				    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
				    			Map featureData = new HashMap();
				    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
				    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
				    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
				    			featureData.put("configurationFeatureElement", featureElementData);
				    			featuresList.add(featureData);
				    			
				    		}
	        			}else{//newConfiguration==false
					    	confEngine = (ConfigurationEngine)session.getAttribute("conf_engine");
			    			FeatureModel model = confEngine.getModel();
			    			List<ConfigurationStep> stepsToUpdateList2 = new LinkedList<ConfigurationStep>();
			    			for( FeatureTreeNode featureNode : model.getNodes() ) {
				        		if ( featureNode.isInstantiated() ) {
				    				stepsToUpdateList2.add( confEngine.configure(featureNode.getID(), featureNode.getValue()) );
				        		}
				        	}
			    			
			    			List<Map> stepsList = new LinkedList<Map>();
			    			if(stepsToUpdateList2==null){
			    				for( ConfigurationStep step : confEngine.getSteps() ) {
					        		Map stepData = new HashMap();
					    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
					        		stepData.put("configurationStepElement", stepElementData);
					        		stepsList.add(stepData);
					        	}
			    			}else{
			    				for( ConfigurationStep step : stepsToUpdateList2 ) {
			    	        		Map stepData = new HashMap();
			    	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
			    	        		stepData.put("configurationStepElement", stepElementData);
			    	        		stepsList.add(stepData);
			    	        	}
			    			}
		    	        	templateModel.put("steps", stepsList);
		    	        	
		    	        	LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
							getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

							
		    	        	Boolean viewLoadStatus=true;
				    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
				    			Map featureData = new HashMap();
				    			
				    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
				    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
				    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
				    			featureData.put("configurationFeatureElement", featureElementData);
				    			featuresList.add(featureData);
				    			
				    		}
		    	        	
	        			}
	        			
	        			
	        		}
	        		
	        	}else{//workflowExistence==false
	        		if (op.compareToIgnoreCase("reset")==0){
	        			
        				List<Map> stepsList = new LinkedList<Map>();
			        	for( ConfigurationStep step : confEngine.getSteps() ) {
			        		Map stepData = new HashMap();
			    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
			        		stepData.put("configurationStepElement", stepElementData);
			        		stepsList.add(stepData);

			        		
			        	}
			        	templateModel.put("steps", stepsList);
			        
			        	
						LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
						getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

			    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
			    			Map featureData = new HashMap();
			    			
			    		
			    		
			    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
			    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
			    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
			    			featureData.put("configurationFeatureElement", featureElementData);
			    			featuresList.add(featureData);
			    			
			    		}

	        		}else if (op.compareToIgnoreCase("rebuild")==0){

	    	    		List<ConfigurationStep> stepsToUpdateList3 = new LinkedList<ConfigurationStep>();

				    	confEngine = (ConfigurationEngine)session.getAttribute("conf_engine");
		    			FeatureModel model = confEngine.getModel();
		    			for( FeatureTreeNode featureNode : model.getNodes() ) {
			        		if ( featureNode.isInstantiated() ) {
			    				stepsToUpdateList3.add( confEngine.configure(featureNode.getID(), featureNode.getValue()) );
			        		}
			        	}
		    			
		    			 
		    			
		    			List<Map> stepsList = new LinkedList<Map>();
		    			int i=0;
		    			
		    			if(stepsToUpdateList3==null){
		    				
		    				for( ConfigurationStep step : confEngine.getSteps() ) {
		    					i++;
				        		Map stepData = new HashMap();
				    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
				        		stepData.put("configurationStepElement", stepElementData);
				        		stepsList.add(stepData);
				        	}
		    			}else{
		    				
		    				for( ConfigurationStep step : stepsToUpdateList3 ) {
		    	        		Map stepData = new HashMap();
		    					i++;

		    	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
		    	        		stepData.put("configurationStepElement", stepElementData);
		    	        		stepsList.add(stepData);
		    	        	}
		    			}
	    	        	templateModel.put("steps", stepsList);
	    	        	
	    	        	LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
						getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);

						
	    	        	Boolean viewLoadStatus=true;
			    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
			    			Map featureData = new HashMap();
			    			
			    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
			    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
			    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
			    			featureData.put("configurationFeatureElement", featureElementData);
			    			featuresList.add(featureData);
			    			
			    		}
	        		}
	        		
	        		
	        	}
	        	

	        	
//	        	if ((workflowExistence.compareToIgnoreCase("true")==0) && (newConfiguration.compareToIgnoreCase("false")==0)){
//	    			
//	    			FeatureDecisionInfo decisionResult=new FeatureDecisionInfo();
//	    			
//    				for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
//    					decisionResult=Methods.getFeatureDecisionInfo(modelDir, serverKey, feature.getID());
//    					if (decisionResult.found){
//    						stepsToUpdateList.add( confEngine.configure(feature.getID(), decisionResult.value.equals("1") ? 1 : 0) );
//    					}
//    				}
//	    			
//    	        	
//    				
//    				List<Map> stepsList = new LinkedList<Map>();
//    	        	for( ConfigurationStep step : stepsToUpdateList ) {
//    	        		Map stepData = new HashMap();
//    	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
//    	        		stepData.put("configurationStepElement", stepElementData);
//    	        		stepsList.add(stepData);
//    	        	}
//    	        	templateModel.put("steps", stepsList);
//    	        	
//    	        	
//    				
//    	        	LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
//					getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);
//
//		        	
//		    		
//		    		Boolean viewLoadStatus=true;
//		    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
//		    			Map featureData = new HashMap();
//		    			
//		    		
//		    		
//		    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
//		    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
//		    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
//		    			featureData.put("configurationFeatureElement", featureElementData);
//		    			featuresList.add(featureData);
//		    			
//		    		}
//		    		
//		    		
//	    		
//	            	
//	            }else{
//
//	            	if (requestQueryString.indexOf("reload")==-1){
//	            		List<Map> stepsList = new LinkedList<Map>();
//			        	for( ConfigurationStep step : confEngine.getSteps() ) {
//			        		Map stepData = new HashMap();
//			    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
//			        		stepData.put("configurationStepElement", stepElementData);
//			        		stepsList.add(stepData);
//
//			        		
//			        	}
//			        	templateModel.put("steps", stepsList);
//			        
//			    		// Traverses features and identify several related parameters
//			        	
//						LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
//						getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);
//
//			        	
//			    		
//			    		Boolean viewLoadStatus=true;
//			    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
//			    			Map featureData = new HashMap();
//			    			
//			    		
//			    		
//			    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
//			    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
//			    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
//			    			featureData.put("configurationFeatureElement", featureElementData);
//			    			featuresList.add(featureData);
//			    			
//			    		}
//		    		}else{
//		    			
//		    			
//				    	confEngine = (ConfigurationEngine)session.getAttribute("conf_engine");
//		    			FeatureModel model = confEngine.getModel();
//		    			
//
//		    			for( FeatureTreeNode featureNode : model.getNodes() ) {
//			    		
//
//			        		if ( featureNode.isInstantiated() ) {
//				    			
//				        		String decisionType=featureNode.getValue() == -1 ? "" : (String)featureNode.getProperty("decisionType");
//			    				stepsToUpdateList.add( confEngine.configure(featureNode.getID(), featureNode.getValue()) );
//				    		
//
//				        		
//			        		}
//			        	}
//		    			
//		    		
//		    			
//
//		    			
//		    			List<Map> stepsList = new LinkedList<Map>();
//		    			if(stepsToUpdateList==null){
//		    				for( ConfigurationStep step : confEngine.getSteps() ) {
//				        		Map stepData = new HashMap();
//				    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
//				        		stepData.put("configurationStepElement", stepElementData);
//				        		stepsList.add(stepData);
//
//				        		
//				        	}
//		    			}else{
//		    				for( ConfigurationStep step : stepsToUpdateList ) {
//		    	        		Map stepData = new HashMap();
//		    	    			String stepElementData = confElementProducer.produceStepElement(step, stepData);
//		    	        		stepData.put("configurationStepElement", stepElementData);
//		    	        		stepsList.add(stepData);
//		    	        	}
//		    			}
//		    			
//		    			
//	    	        	
//	    	        	templateModel.put("steps", stepsList);
//	    	        	
//	    	        	LinkedList<FeatureTreeNode> fmChilds=new LinkedList<FeatureTreeNode>();
//						getFeatureModelChilds(featureModel.getRoot(), fmChilds,viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,viewType);
//
//						
//	    	        	Boolean viewLoadStatus=true;
//			    		for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
//			    			Map featureData = new HashMap();
//			    			
//			    		
//			    		
//			    			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
//			    			String featureElementData = confElementProducer.produceFeatureElement(feature, featureData, getFeatureTemplateFile(), viewDir,modelDir,featureModelFileName,confEngine.getModel().getName(),viewName,featureInViewCheckingResult,viewType,fmChilds, serverKey);
//			    			featureElementData = featureElementData.replaceAll("[\r][\n]", "");
//			    			featureData.put("configurationFeatureElement", featureElementData);
//			    			featuresList.add(featureData);
//			    			
//			    		}
//	    	        	
//		    			
//		    		}
//	    		
//	            	
//	            }
//	            
//	    		
//	    		
	    		
			
	    	
	    		
	    		

	    		templateModel.put("features", featuresList);
	    		templateModel.put("modelName", confEngine.getModel().getName());
	        	templateModel.put("countFeatures", confEngine.getModel().countFeatures());
	        	templateModel.put("countInstantiatedFeatures", confEngine.getModel().getInstantiatedNodes().size());
				templateModel.put("done", confEngine.isDone());
				templateModel.put("fm_views", Methods.getFeatureModelViews(viewDir));
				templateModel.put("viewType", viewType.toLowerCase());
				templateModel.put("viewName", viewName);
				templateModel.put("selectedModels", featureModelFileName);
				templateModel.put("workflowName", workflow);
				templateModel.put("taskName", task);
//				placeType="stop";
//				templateModel.put("placeType", placeType);
				//templateModel.put("uncompletedViews",stopAllocatedViewsResult); 
				templateModel.put("userName", user);
				templateModel.put("workflowExistence", workflowExistence);
				templateModel.put("serverKey", serverKey);
				templateModel.put("uncoveredFeatures", uncoveredFeatures);
				templateModel.put("sessionKey", sessionKey);
	        
				
 			
		} catch (HandlerExecutionException e1) {
			throw e1;
		} catch (Exception e2) {
			e2.printStackTrace();
			throw new HandlerExecutionException("Problems locating/acessing the feature model repository path", e2);
		}
	}
	
	protected String getFeatureParent(fm.FeatureTreeNode feature) {
		fm.FeatureTreeNode parent = (fm.FeatureTreeNode)feature.getParent();
		if ( parent == null ) {
			return "";
		}
		return parent.getID();		
	}
	
	protected String getFeatureName( fm.FeatureTreeNode feature ) {
		if ( feature instanceof fm.FeatureGroup ) {
			int min = ((fm.FeatureGroup)feature).getMin();
			int max = ((fm.FeatureGroup)feature).getMax();
			max = ( max==-1 ) ? feature.getChildCount() : max; 
			return "[" + min + ".." + max +"]";
		}
		return feature.getName();
	}
	
	protected String getFeatureType( fm.FeatureTreeNode feature ) {
		if ( feature.isRoot() ) { 
			return "root";				
		}
		else if ( feature instanceof fm.SolitaireFeature) {
			if ( ((fm.SolitaireFeature) feature).isOptional()) {
				return "optional";
			}
			else {
				return "mandatory";
			}
		}
		else if ( feature instanceof fm.FeatureGroup ){
			return "group";
		}
		else if ( feature instanceof fm.GroupedFeature ){
			return "grouped";
		}
		return "error";
	}
	
	

	
	
	public static void getFeatureModelChilds(FeatureTreeNode featureTreeNode,LinkedList<FeatureTreeNode> featureList , String viewDir,String modelDir,String featureModelFileName, String featureModelName, String viewName,
			   String visualizationType){
		
		try{
		if ( featureTreeNode.isRoot() ) {
			featureList.add(featureTreeNode);
		}
		for( int i = 0 ; i < featureTreeNode.getChildCount() ; i++ ) {
			
			FeatureInViewCheckingResult featureInViewCheckingResult=new FeatureInViewCheckingResult();
			Methods.checkFeatureInViewStatus((FeatureTreeNode)featureTreeNode.getChildAt(i),viewDir,modelDir,featureModelFileName,featureModelName,viewName,featureInViewCheckingResult,visualizationType); 
			if (featureInViewCheckingResult.show){
				featureList.add((FeatureTreeNode)featureTreeNode.getChildAt(i));
			}
			
			getFeatureModelChilds((FeatureTreeNode) featureTreeNode.getChildAt(i),featureList,viewDir,modelDir,featureModelFileName,featureModelName,viewName,visualizationType);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
}	



