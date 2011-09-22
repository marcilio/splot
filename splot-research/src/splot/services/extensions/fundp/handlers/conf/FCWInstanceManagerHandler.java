package splot.services.extensions.fundp.handlers.conf;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import splar.core.fm.FeatureTreeNode;
import splar.core.fm.configuration.ConfigurationEngine;
import splar.core.fm.configuration.ConfigurationEngineException;
import splar.plugins.configuration.sat.sat4j.SATConfigurationEngine;
import splot.core.Handler;
import splot.core.HandlerExecutionException;
import splot.services.extensions.fundp.utilities.FeatureDecisionInfo;
import splot.services.extensions.fundp.utilities.Methods;



public class FCWInstanceManagerHandler extends Handler {
	Timer timer;
	public FCWInstanceManagerHandler(String handlerName, HttpServlet servlet) {
		super(handlerName, servlet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		try {
			String requestType="";
        	String requestQueryString=request.getQueryString();
        	String instanceID="false";
        	String featureModelName="false";
        	String featureModelFileName="false";
        	String operation="false";
        	
       		String modelDir=getServlet().getInitParameter("modelsPath");
    		String configuredModelPath=modelDir+"/configured_models";

       		
       		String newQueryString=requestQueryString.replaceAll("action="+request.getParameter("action"), "");
       		
         	/*********************************************************************
    		 * get request type
    		 *********************************************************************/
       		requestType=Methods.getRequestTypeFromRequest(request);
			
       		/*********************************************************************
    		 * RequestType=configuration
    		 *********************************************************************/
			if(requestType.compareToIgnoreCase("initialization")==0){

	      		/*********************************************************************
	    		 * get userKey(instanceKey)
	    		 *********************************************************************/
				instanceID=Methods.getUserKeyFromRequest(request);
				if(instanceID.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid FCW instance ID");
		        	rd.forward(request, response);

				}

	      		/*********************************************************************
	    		 * get operation type
	    		 *********************************************************************/
				operation=Methods.getOperationFromRequest(request);
				
				
				if(operation.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid configuration operation type");
		        	rd.forward(request, response);

				}
				

	      		/*********************************************************************
	    		 * get featureModelName
	    		 *********************************************************************/
				
				featureModelName=Methods.getFeatureModelNameFromRequest(request);
				if(featureModelName.compareToIgnoreCase("false")==0){
					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid feature model name");
		        	rd.forward(request, response);

				}
    			
	    		/*********************************************************************
	    		 * get feature model file name
	    		 *********************************************************************/
				featureModelFileName=Methods.getFeatureModelFileNameFromRequest(request, modelDir, featureModelName);
				if(featureModelFileName.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=feature model file not found");
		        	rd.forward(request, response);

				}
				
    			if (!(instanceID.compareToIgnoreCase("false")==0)){
    				
		      		/*********************************************************************
		    		 * verify configuration engine
		    		 *********************************************************************/
    				if(operation.compareToIgnoreCase("reset")==0){
    					
        				ServletConfig config = getServlet().getServletConfig();
        	    	    ServletContext sc = config.getServletContext();

        				if ((ConfigurationEngine)sc.getAttribute(instanceID+"_conf_engine")==null){
        					
        					if(!(featureModelFileName.compareToIgnoreCase("false")==0)){

        						String result=initiateConfigurationEngine(getResourcePath()+featureModelFileName, instanceID,configuredModelPath,modelDir);
        						
        						if (result.compareToIgnoreCase("true")==0){
               						RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=interactive_instance_initialization_reset"+newQueryString);
            			        	rd.forward(request, response);
            			        	
            			        	
        						}else{ //result==false
        	     					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=problem in creating configuration engine");
        	    		        	rd.forward(request, response);
        						}
        						
 
        					}//if(!(featureModelFileName.compareToIgnoreCase("false")==0))
        				}else{// ConfigurationEngine is not null

    			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=interactive_instance_initialization_reset"+newQueryString);
    			        	rd.forward(request, response);
        				}
    				}else{
      					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid configuration operation type");
    		        	rd.forward(request, response);

    				}
    			}//if (!(instanceID.compareToIgnoreCase("false")==0))
				
				
    			
    			
    		
				
				
	    	
				
				
	       	/*********************************************************************
	    	* RequestType=configuration
	    	*********************************************************************/
			}else if (requestType.compareToIgnoreCase("configuration")==0){
				
	      		/*********************************************************************
	    		 * get userKey(instanceKey)
	    		 *********************************************************************/
				instanceID=Methods.getUserKeyFromRequest(request);
				if(instanceID.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid FCW instance ID");
		        	rd.forward(request, response);

				}
				
	      		/*********************************************************************
	    		 * get operation type
	    		 *********************************************************************/
				operation=Methods.getOperationFromRequest(request);
				
				
				if(operation.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid configuration operation type");
		        	rd.forward(request, response);

				}
       			ServletConfig config = getServlet().getServletConfig();
    	    	ServletContext sc = config.getServletContext();
    	    	if ((ConfigurationEngine)sc.getAttribute(instanceID+"_conf_engine")==null){
    	    		System.out.println(instanceID+"_conf_engine");
       				RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=Configuration engine must be created first");
    		        rd.forward(request, response);

    			}else{// configuration engine is not null
    				String lock=(String)sc.getAttribute(instanceID+"_lock");
    				if(lock==null){
    					sc.setAttribute(instanceID+"_lock", "locked");
    					System.out.println("1");
      			        RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=instance_interactive_configuration_updates"+newQueryString);
    			        rd.forward(request, response);
    				}else if(lock.compareToIgnoreCase("free")==0){
       					sc.setAttribute(instanceID+"_lock", "locked");
       					System.out.println("2");
      			        RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=instance_interactive_configuration_updates"+newQueryString);
    			        rd.forward(request, response);
    				}else if(lock.compareToIgnoreCase("locked")==0){
    					while (lock.compareToIgnoreCase("locked")==0){
    						lock=(String)sc.getAttribute(instanceID+"_lock");
							
	    					if(lock==null){
	    						sc.setAttribute(instanceID+"_lock", "locked");
	    						System.out.println("3");
	         			        RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=instance_interactive_configuration_updates"+newQueryString);
	    			        	rd.forward(request, response);

	    					}else if(lock.compareToIgnoreCase("free")==0){
	       						sc.setAttribute(instanceID+"_lock", "locked");
	       						System.out.println("4");
	         			        RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=instance_interactive_configuration_updates"+newQueryString);
	    			        	rd.forward(request, response);
	    					}
	    					Thread.sleep(3000);
    					}
    				}
    			}
				
		    /*********************************************************************
		    * RequestType=update
		    *********************************************************************/
			}else if (requestType.compareToIgnoreCase("conflicts")==0){
				
	      		/*********************************************************************
	    		 * get userKey(instanceKey)
	    		 *********************************************************************/
				instanceID=Methods.getUserKeyFromRequest(request);
				if(instanceID.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid FCW instance ID");
		        	rd.forward(request, response);

				}
				
	      		/*********************************************************************
	    		 * get operation type
	    		 *********************************************************************/
				operation=Methods.getOperationFromRequest(request);
				if(operation.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid configuration operation type");
		        	rd.forward(request, response);

				}else if (operation.compareToIgnoreCase("detect")==0){
					ServletConfig config = getServlet().getServletConfig();
    	    	    ServletContext sc = config.getServletContext();
    	    	    

    				if ((ConfigurationEngine)sc.getAttribute(instanceID+"_conf_engine")==null){
       					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=Configuration engine must be created first");
    		        	rd.forward(request, response);

    				}else{// configuration engine is not null
    					String lock=(String)sc.getAttribute(instanceID+"_lock");

    					if(lock==null){
    						sc.setAttribute(instanceID+"_lock", "locked");
       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=detect_instance_conflicts"+newQueryString);
    			        	rd.forward(request, response);

    					}else if(lock.compareToIgnoreCase("free")==0){
       						sc.setAttribute(instanceID+"_lock", "locked");
       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=detect_instance_conflicts"+newQueryString);
    			        	rd.forward(request, response);
	
    					}else if(lock.compareToIgnoreCase("locked")==0){
    						
    						while (lock.compareToIgnoreCase("locked")==0){
    							lock=(String)sc.getAttribute(instanceID+"_lock");
    							
    	    					if(lock==null){
    	    						sc.setAttribute(instanceID+"_lock", "locked");
    	       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=detect_instance_conflicts"+newQueryString);
    	    			        	rd.forward(request, response);

    	    					}else if(lock.compareToIgnoreCase("free")==0){
    	       						sc.setAttribute(instanceID+"_lock", "locked");
    	       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=detect_instance_conflicts"+newQueryString);
    	    			        	rd.forward(request, response);
    	    					}
    	    					
    	    					Thread.sleep(3000);
    	    					
    						}
    					}
    				}	
				}
			
			/*********************************************************************
				 * RequestType=save in repository
			*********************************************************************/
			}else if (requestType.compareToIgnoreCase("save")==0){ 
			
				ServletConfig config = getServlet().getServletConfig();
	    	    ServletContext sc = config.getServletContext();
	    	    
	    		instanceID=Methods.getUserKeyFromRequest(request);
				if(instanceID.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid FCW instance ID");
		        	rd.forward(request, response);

				}
				
	      		/*********************************************************************
	    		 * get operation type
	    		 *********************************************************************/
				operation=Methods.getOperationFromRequest(request);
				if(operation.compareToIgnoreCase("false")==0){
   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid configuration operation type");
		        	rd.forward(request, response);

				}else if (operation.compareToIgnoreCase("repository")==0){
					
					if ((ConfigurationEngine)sc.getAttribute(instanceID+"_conf_engine")==null){
	   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=Configuration engine must be created first");
			        	rd.forward(request, response);

					}else{// configuration engine is not null
						String lock=(String)sc.getAttribute(instanceID+"_lock");

						if(lock==null){
							sc.setAttribute(instanceID+"_lock", "locked");
	   			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuratiom_xml_file"+newQueryString);
				        	rd.forward(request, response);

						}else if(lock.compareToIgnoreCase("free")==0){
							sc.setAttribute(instanceID+"_lock", "locked");
	   			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuratiom_xml_file"+newQueryString);
				        	rd.forward(request, response);
				        	

						}else if(lock.compareToIgnoreCase("locked")==0){
							
							while (lock.compareToIgnoreCase("locked")==0){
								lock=(String)sc.getAttribute(instanceID+"_lock");
								
		    					if(lock==null){
		    						sc.setAttribute(instanceID+"_lock", "locked");
		       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuratiom_xml_file"+newQueryString);
		    			        	rd.forward(request, response);

		    					}else if(lock.compareToIgnoreCase("free")==0){
		       						sc.setAttribute(instanceID+"_lock", "locked");
		       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuratiom_xml_file"+newQueryString);
		    			        	rd.forward(request, response);
		    					}
		    					
		    					Thread.sleep(3000);
		    					
							}
						}
					}	
				

				}else if (operation.compareToIgnoreCase("csv")==0){
					if ((ConfigurationEngine)sc.getAttribute(instanceID+"_conf_engine")==null){
	   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=Configuration engine must be created first");
			        	rd.forward(request, response);

					}else{// configuration engine is not null
						String lock=(String)sc.getAttribute(instanceID+"_lock");

						if(lock==null){
							sc.setAttribute(instanceID+"_lock", "locked");
	   			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuration_csv"+newQueryString);
				        	rd.forward(request, response);

						}else if(lock.compareToIgnoreCase("free")==0){
							sc.setAttribute(instanceID+"_lock", "locked");
	   			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuration_csv"+newQueryString);
				        	rd.forward(request, response);
				        	

						}else if(lock.compareToIgnoreCase("locked")==0){
							
							while (lock.compareToIgnoreCase("locked")==0){
								lock=(String)sc.getAttribute(instanceID+"_lock");
								
		    					if(lock==null){
		    						sc.setAttribute(instanceID+"_lock", "locked");
		       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuration_csv"+newQueryString);
		    			        	rd.forward(request, response);

		    					}else if(lock.compareToIgnoreCase("free")==0){
		       						sc.setAttribute(instanceID+"_lock", "locked");
		       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuration_csv"+newQueryString);
		    			        	rd.forward(request, response);
		    					}
		    					
		    					Thread.sleep(3000);
		    					
							}
						}
					}	
					
				}else if (operation.compareToIgnoreCase("xml")==0){
					if ((ConfigurationEngine)sc.getAttribute(instanceID+"_conf_engine")==null){
	   					RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=Configuration engine must be created first");
			        	rd.forward(request, response);

					}else{// configuration engine is not null
						String lock=(String)sc.getAttribute(instanceID+"_lock");

						if(lock==null){
							sc.setAttribute(instanceID+"_lock", "locked");
	   			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuration_xml"+newQueryString);
				        	rd.forward(request, response);

						}else if(lock.compareToIgnoreCase("free")==0){
							sc.setAttribute(instanceID+"_lock", "locked");
	   			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuration_xml"+newQueryString);
				        	rd.forward(request, response);
				        	

						}else if(lock.compareToIgnoreCase("locked")==0){
							
							while (lock.compareToIgnoreCase("locked")==0){
								lock=(String)sc.getAttribute(instanceID+"_lock");
								
		    					if(lock==null){
		    						sc.setAttribute(instanceID+"_lock", "locked");
		       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuration_xml"+newQueryString);
		    			        	rd.forward(request, response);

		    					}else if(lock.compareToIgnoreCase("free")==0){
		       						sc.setAttribute(instanceID+"_lock", "locked");
		       			        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=export_instance_configuration_xml"+newQueryString);
		    			        	rd.forward(request, response);
		    					}
		    					
		    					Thread.sleep(3000);
		    					
							}
						}
					}	
				}
	    	    

				
				
				
				
				
		    /*********************************************************************
			 * RequestType=empty
			*********************************************************************/
				
			}else if (requestType.compareToIgnoreCase("false")==0){ 
	        
				RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=request type not found");
	        	rd.forward(request, response);

			/*********************************************************************
			* RequestType=invalid
			*********************************************************************/
			}else{// invalid request type
				
	        	RequestDispatcher rd=request.getRequestDispatcher("/MultiplePerspectiveConfigurationViewsServlet?action=show_messages&message=invalid request type");
	        	rd.forward(request, response);

				
			}
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.fillInStackTrace();
			
		}
		
	}
	
	protected ConfigurationEngine createConfigurationEngine(String modelLocatorString) throws HandlerExecutionException {
		try {
			   return new SATConfigurationEngine(modelLocatorString);
		} catch (ConfigurationEngineException e) {
			throw new HandlerExecutionException("Error creating SAT configuration engine.", e);
		}
	}
	
	protected String getResourcePath() {
		return getServlet().getInitParameter("modelsPath");
	}
	
	private String initiateConfigurationEngine(String modelLocatorString ,String instanceID,String  configuredModelPath, String modelDir){
		String retVal="false";
		
		
		try {
			ConfigurationEngine confEngine=null;
			ServletConfig config = getServlet().getServletConfig();
		    ServletContext sc = config.getServletContext();
		    sc.setAttribute(instanceID+"_lock", "locked");
		    if ((ConfigurationEngine)sc.getAttribute(instanceID+"_conf_engine")==null){
	            String configurationFileName=Methods.getConfiguredFileName(configuredModelPath, instanceID);
	            if (configurationFileName.compareToIgnoreCase("false")==0){
	            	
					confEngine=createConfigurationEngine(modelLocatorString);
		        	confEngine.reset();

	            }else{
	            	
					confEngine=createConfigurationEngine(modelLocatorString);
		        	confEngine.reset();

			    	confEngine.getSteps().remove(confEngine.getLastStep());
    				FeatureDecisionInfo decisionResult=new FeatureDecisionInfo();
    				
    				for( FeatureTreeNode feature : confEngine.getModel().getNodes(confEngine.getModel().getRoot())) {
    					decisionResult=Methods.getFeatureDecisionInfo(modelDir, instanceID, feature.getID());

    					if (decisionResult.found){
    						 confEngine.configure(feature.getID(), decisionResult.value.equals("1") ? 1 : 0);
    					}
    				}
	            }
	        	sc.setAttribute(instanceID+"_conf_engine", confEngine);
	        	sc.setAttribute(instanceID+"_lock", "free");
	        	retVal="true";

		    }
		    
		} catch (Exception e) {
			retVal="false";
		}
		
		return retVal;
	}

}
