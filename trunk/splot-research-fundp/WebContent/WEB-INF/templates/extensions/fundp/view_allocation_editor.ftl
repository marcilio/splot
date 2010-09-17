<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Name       : Compromise
Description: A two-column, fixed-width design with dark color scheme.
Version    : 1.0
Released   : 20081103

-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Welcome to the Software Product Lines Online Tools Homepage</title>

<link type="text/css" rel="stylesheet" href="splot.css"/>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dijit/themes/nihilo/nihilo.css"/>

<#if !hasError> 



<script type="text/javascript"
	 	src="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dojo/dojo.xd.js" 
	 	djConfig="parseOnLoad: true, isDebug: false, dojoBlankHtmlUrl: 'blank.html'">
</script>

<script type="text/javascript" src="js/ajax.js"></script> 
<script type="text/javascript" src="js/json_utils.js"></script> 
<script type="text/javascript" src="js/utils.js"></script> 
<script type="text/javascript" src="js/json_sans_eval.js"></script> 
<script type="text/javascript" src="js/json_sans_eval.js"></script> 


<script type="text/javascript">

	dojo.require("dijit.form.Form");	
	dojo.require("dijit.form.Button");	
	dojo.require("dijit.form.TextBox");	
	dojo.require("dijit.form.ComboBox");
	dojo.require("dijit.form.SimpleTextarea");	
	dojo.require("dijit.Dialog");
	dojo.require("dijit.Menu");
	dojo.require("dijit.form.DateTextBox");
	dojo.require("dojo.fx"); // for animate showing/hiding the Hint
	dojo.require("dojo.parser");
	
	
	
	
		
	/******************************************************
	*  variables
	*******************************************************/
	var  tableSelectedIndex=0;
	
	
	/******************************************************
	*  trim function
	*******************************************************/	
	
	function trimAll(sString){ 
		while (sString.substring(0,1) == ' '){ 
			sString = sString.substring(1, sString.length); 
		} 
		
		while (sString.substring(sString.length-1, sString.length) == ' '){ 
			sString = sString.substring(0,sString.length-1); 
		} 
	return sString; 
	} 
	
	
	/******************************************************
	*  Expand/Collapse Feature Model Parts
	*******************************************************/
	function expandCollapseElement(partId) {
		if ( dojo.byId(partId).style.display == 'none' ) {			
			dojo.byId(partId).style.display = 'block';
		}
		else {
			dojo.byId(partId).style.display = 'none';
		}
	}	
	
	/******************************************************
	*  Hide and Show and element on the page
	*******************************************************/
	function hideShowElement(elementId) {
		if( dojo.byId(elementId).style.display == 'none' ) {
			dojo.byId(elementId).style.display = 'inline';
		}
		else {
			dojo.byId(elementId).style.display = 'none';
		}
	}	

	/******************************************************
	*  Show/Hide Hint (animation)
	*******************************************************/
	function hideShowHint(option) {
		if (option == 'hide') {
			dojo.fx.wipeOut({node: "animHintPost",duration: 1000}).play();
			dojo.byId('instructions').style.display = 'inline';
		}
		else if (option == 'show') {
			dojo.byId('instructions').style.display = 'none';
			dojo.fx.wipeIn({node: "animHintPost",duration: 1000}).play();
		} 
		//document.getElementById("hintShortText").style.display = "block";
	}




</script>



<script type="text/javascript">
	/******************************************************
	*  Updates workflow list whenever a feature model is selected
	*******************************************************/
	
	function onchangeFeatureListSelectedItem(){
		val=document.getElementById("feature_list").value; 
		if (val=='Select'){
			return;
		}
		
		try{
			if (document.base_info.base_info_radio[0].checked==true){
					var selectedType="feature";
					if (window.ActiveXObject){
 						oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");  
					}else{
						oXMLRequest = new XMLHttpRequest();
					}    
					var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=response_workflow_feature_list&selectedType=" +selectedType+"&selectedValue="+val ;
					oXMLRequest.open("GET",strValidationServiceUrl,true);
					oXMLRequest.onreadystatechange = updateWorkflowDetails;
					oXMLRequest.send(null);   
			}
		}catch(Error){
			alert(Error);
		}
		
	}
	
	
	
	/******************************************************
	*  Receive workflow info from server based on selected feature model
	*******************************************************/	
	function updateWorkflowDetails(){
			if (oXMLRequest.readyState  == 4){
            	if (oXMLRequest.status  == 200){
						strStatus = oXMLRequest.responseText; 
						var workflowList=new Array();
						workflowList=strStatus.split(",");
						workflowLength=workflowList.length;
						try{
							document.getElementById("workflow_list").length = 0;
						}catch(ex){
							alert(ex);
						}
						var workflowListBox=document.getElementById("workflow_list");
						var defaultOption=document.createElement("option");
						defaultOption.text="Select";
						try{
							workflowListBox.add(defaultOption,null);
						}catch(Error){
							workflowListBox.add(defaultOption);
						}
						
						
							
						
						for (i=0;i<workflowLength;i++){
							
							try{
								var newOption=document.createElement("option");
								newOption.text=workflowList[i];
								
							}catch(excep){
								alert(excep);
							}
							try{
								workflowListBox.add(newOption,null);
							}catch(Error){
								workflowListBox.add(newOption);
							
							}
							
						}
                    try{ 
					}catch(Error){
						alert(Error);
					} 
				}
		    }		
	}
	



	/******************************************************
	*  Updates feature list whenever a workflow is selected
	*******************************************************/
	
	function onchangeWorkflowListSelectedItem(){
		val=document.getElementById("workflow_list").value; 
		if (val=='Select'){
			return;
		}
		
		try{
			if (document.base_info.base_info_radio[1].checked==true){
					var selectedType="workflow";
					if (window.ActiveXObject){
 						oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");  
					}else{
						oXMLRequest = new XMLHttpRequest();
					}    
					var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=response_workflow_feature_list&selectedType=" +selectedType+"&selectedValue="+val ;
					oXMLRequest.open("GET",strValidationServiceUrl,true);
					oXMLRequest.onreadystatechange = updateFeatureModelDetails;
					oXMLRequest.send(null);   
			}
		}catch(Error){
			alert(Error);
		}
		
	}
	
	
	
	/******************************************************
	*  Receive workflow info from server based on selected feature model
	*******************************************************/	
	function updateFeatureModelDetails(){
			if (oXMLRequest.readyState  == 4){
            	if (oXMLRequest.status  == 200){
						strStatus = oXMLRequest.responseText; 
						var featureList=new Array();
						featureList=strStatus.split(",");
						featureLength=featureList.length;
						try{
							document.getElementById("feature_list").length = 0;
						}catch(ex){
							alert(ex);
						}
						var featureListBox=document.getElementById("feature_list");	
						var defaultOption=document.createElement("option");
						defaultOption.text="Select";
						try{
							featureListBox.add(defaultOption,null);
						}catch(Error){
							featureListBox.add(defaultOption);
						}
						
						
						for (i=0;i<featureLength;i++){
							
							try{
								var newOption=document.createElement("option");
								newOption.text=featureList[i];
								
							}catch(excep){
								alert(excep);
							}
							try{
								featureListBox.add(newOption,null);
							}catch(Error){
								featureListBox.add(newOption);
							
							}
							
						}
                    try{ 
					}catch(Error){
						alert(Error);
					} 
				}
		    }		
	}
	
	
		
	/******************************************************
	*  Load view allocation information 
	*******************************************************/
	
	 function loadViewAllocationInfo() 
	{
		try{	
			featureValue=document.getElementById("feature_list").value; 
			WorkflowValue=document.getElementById("workflow_list").value; 
			
			if ((WorkflowValue=="Select") || (WorkflowValue=="")){
				alert("Workflow must be selected!");
				return;
			}
			
			if ((featureValue=="Select") && (WorkflowValue=="Select")){
				alert("One or both of feature model and workflow should be selected!");
				return;
			}
			
			if ((featureValue=="") && (WorkflowValue=="")){
				alert("One or both of feature model and workflow should be selected!");
				return;
			}
			
			
			
			if (window.ActiveXObject){
 				oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");  
			}else{
				oXMLRequest = new XMLHttpRequest();
			}    
		
			var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=load_view_allocation_information&featureModel=" +featureValue+"&workflow="+WorkflowValue;
			oXMLRequest.open("GET",strValidationServiceUrl,true);
			oXMLRequest.onreadystatechange =listViewAllocationInfo;
			oXMLRequest.send(null);   
		}catch(Error){
			alert(Error);
		}	
		
		
	} 
	
	
	/******************************************************
	*  Receive view allocation information from server
	*******************************************************/
	function listViewAllocationInfo(){
				if (oXMLRequest.readyState  == 4){
            		if (oXMLRequest.status  == 200){
						strStatus = oXMLRequest.responseText;
						
					
						JSONList=strStatus.split("/");
						viewCount=JSONList[0];
						JSONString=JSONList[1];
						receivedViewList=JSONList[2];
						receivedTaskList=JSONList[3];
						
					
						
						
						try{ 
						
						    
							
							updateTaskList(receivedTaskList);
							updateViewList(receivedViewList);
							
						
							var rowIndex=1;
							var myJsonObj = jsonParse(JSONString);
							var table = document.getElementById("allocated_view_information");
							deleteTableAllRows("allocated_view_information");
							var rowCount = table.rows.length;
							
							
							for (i=0;i<viewCount;i++){
								viewName=myJsonObj[i].view_name;
								taskString=myJsonObj[i].task_list;
								taskList=taskString.split(",");  
								taskListLength=taskList.length;
								for (j=0;j<taskListLength;j++){
									rowIndex++;
								}
							}
							
							
           					
							for (i=0;i<viewCount;i++){
								viewName=myJsonObj[i].view_name;
								workflowName=myJsonObj[i].workflow_name;
								featureName=myJsonObj[i].feature_name;
								
								taskString=myJsonObj[i].task_list;
								taskList=taskString.split(",");  
								taskListLength=taskList.length;

								
								for (j=0;j<taskListLength;j++){
									var row = table.insertRow(rowCount);
									
									
									rowIndex--;							
							   		var cell1 = row.insertCell(0);
							  		cell1.innerHTML =rowIndex;
							  
							   		var cell2 = row.insertCell(1);
							   		cell2.innerHTML =featureName;
							   
							   		var cell3 = row.insertCell(2);
							   		cell3.innerHTML =workflowName;
							
							   		var cell4 = row.insertCell(3);
							   		cell4.innerHTML =taskList[j];
							
							   		var cell5 = row.insertCell(4);
							  		cell5.innerHTML =viewName;
							  		
							  		var cell6 = row.insertCell(5);
							  		var elementRadio = document.createElement("input");
							  		elementRadio.type = "radio";
							  		elementRadio.name="selectToDelete";
							  		cell6.appendChild(elementRadio);
								} 
							}
						}catch(Error){
							alert(Error);
						}	
					}
				}		
	}
	
	
	
	/******************************************************
	*  Delete table's all rows
	*******************************************************/	
	
	 function deleteTableAllRows(tableID) {

            try {
	            var table = document.getElementById(tableID);
	            var rowCount = table.rows.length;
	            for(var i=1; i<rowCount; i++) {
	                        table.deleteRow(i);
							rowCount--;
                    		i--;
	
                }
              }catch(e) {
                alert(e);
            }
        }
	


	/******************************************************
	*  Delete table's selected rows
	*******************************************************/
        function deleteRow(tableID, index) {
            try {
	            var table = document.getElementById(tableID);
	            var rowCount = table.rows.length;
	            for(var i=0; i<rowCount; i++) {
	                var row = table.rows[i];
	                var indexValue = row.cells[0].innerHTML;
	                if(indexValue==index) {
	                    table.deleteRow(i);
	                    rowCount--;
	                    i--;
	                }
    	        }

				rowIndex=0;
				var rowCount = table.rows.length;
				for(var i=1; i<rowCount; i++) {
					var row = table.rows[i];
					rowIndex++;
					row.cells[0].innerHTML=rowIndex;
					
				}

            }catch(e){
                alert(e);

            }

        }



	/******************************************************
	*  Delete a view allocation
	*******************************************************/
		function  deleteSelectedRows(){
		
		     try {
	            var table = document.getElementById("allocated_view_information");
	            var rowCount = table.rows.length;
	            for(var i=0; i<rowCount; i++) {
	                var row = table.rows[i];
	                var chkbox = row.cells[5].childNodes[0];
	                if(null != chkbox && true == chkbox.checked) {
						deletedIndex=row.cells[0].innerHTML;
						tableSelectedIndex=deletedIndex;
						deletedFeatureName=row.cells[1].innerHTML;
						deletedWorkflowName=row.cells[2].innerHTML;
						deletedTaskName=row.cells[3].innerHTML;
						deletedViewName=row.cells[4].innerHTML;
						
						
						
						
						if (window.ActiveXObject){
			 				oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");  
						}else{
							oXMLRequest = new XMLHttpRequest();
						}    
		
						var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=delete_view_allocation&featureModel=" +deletedFeatureName+"&workflow="+deletedWorkflowName+"&taskName="+deletedTaskName+"&viewName="+deletedViewName;		
						oXMLRequest.open("GET",strValidationServiceUrl,true);
						oXMLRequest.onreadystatechange =updateTableAfterDelete;
						oXMLRequest.send(null);   
	                }
    	        }
            }catch(e){
                alert(e);

            }
		}



	/******************************************************
	*  Update  view allocation after delete
	*******************************************************/
		function updateTableAfterDelete(){
				if (oXMLRequest.readyState  == 4){
            		if (oXMLRequest.status  == 200){
						strStatus = oXMLRequest.responseText;
						
						try{
							if (strStatus=="true"){
								deleteRow("allocated_view_information",tableSelectedIndex);
							}else{
								alert(strStatus);
							}
						
						}catch(Error){
						}
					}
				}	
		
		}
		
		
		
	/******************************************************
	*  Update  task list
	*******************************************************/		
		function updateTaskList(tList){
		
			try{
				document.getElementById("task_list").length = 0;
			}catch(ex){
				alert(ex);
			}
			
			var taskListBox=document.getElementById("task_list");	
		
			tListString=tList.split(","); 
			tListLength=tListString.length;
			for (i=0;i<tListLength;i++){
				try{
					var newOption=document.createElement("option");
					newOption.text=tListString[i];
					
				}catch(ex){
					alert(ex)
				}
				
				
				try{
					taskListBox.add(newOption,null);
				}catch(Error){
					taskListBox.add(newOption);
							
				}
				
									
			}
			
			 
		}
		
	
	/******************************************************
	*  Update  view list
	*******************************************************/		
		function updateViewList(vList){
		
			try{
				document.getElementById("view_list").length = 0;
			}catch(ex){
				alert(ex);
			}
			
			var viewListBox=document.getElementById("view_list");	
		
			vListString=vList.split(","); 
			vListLength=vListString.length;
			for (i=0;i<vListLength;i++){
				try{
					var newOption=document.createElement("option");
					newOption.text=vListString[i];
					
				}catch(ex){
					alert(ex)
				}
				
				
				try{
					viewListBox.add(newOption,null);
				}catch(Error){
					viewListBox.add(newOption);
							
				}
				
									
			}
			
			 
		}
		
		
		
	/******************************************************
	*  Save  view allocation
	*******************************************************/		
		
		function  saveViewAllocationToRepository(){
				featureValue=document.getElementById("feature_list").value; 
				workflowValue=document.getElementById("workflow_list").value; 
				taskValue=document.getElementById("task_list").value; 
				viewValue=document.getElementById("view_list").value; 
				
				
				
		
				
				if ((featureValue=="Select") || (featureValue=="")){
					alert("Feature model must be selected!");
					return;
				}
				
				
				if ((workflowValue=="Select") || (workflowValue=="")){
					alert("Workflow must be selected!");
					return;
				}
				
				if (taskValue==""){
					alert("Task must be selected!");
					return;
				}
				
				if (viewValue==""){
					alert("View must be selected!");
					return;
				}
				
				
				
				
				try{
				
						if (window.ActiveXObject){
			 				oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");  
						}else{
							oXMLRequest = new XMLHttpRequest();
						}    
		
						var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=save_view_allocation&featureModel=" +featureValue+"&workflow="+workflowValue+"&taskName="+taskValue+"&viewName="+viewValue;		
						oXMLRequest.open("GET",strValidationServiceUrl,true);
						oXMLRequest.onreadystatechange =saveViewAllocationToRepositoryResult;
						oXMLRequest.send(null);   
				
				
				}catch(error){
					alert(error);
				}
		
		
		
		}
		
	/******************************************************
	*  Save view allocation reslut
	*******************************************************/		
	function saveViewAllocationToRepositoryResult(){
					if (oXMLRequest.readyState  == 4){
            		if (oXMLRequest.status  == 200){
						strStatus = oXMLRequest.responseText;
						
						try{
							if (strStatus=="true"){
								alert("View allocation is stored successfully in repository.")
								loadViewAllocationInfo();
							}else{
								alert(strStatus);
							}
						
						}catch(Error){
						}
					}
				}	
	
	}
		
		
			
</script>

</#if>

</head>
<body class="nihilo">

<#if !hasError>

<!--  Notification Dialog -->
<div dojoType="dijit.Dialog"
	 style="display:none" 
	 id="notificationDialog" 
	 title=""
	 execute="">	
	 <div>
	 	<table border="0" width="100%" cellpadding="10">
	 	<tr><td align="center">
		 	<img id="errorImage" style="display:none" src="images/error_icon.jpg"/>
			<span id="notificationDialogContent"></span>
	 	</td></tr>
	 	<tr><td align="center">
	 		<img id="notificationDialogContentLoadingImage" style="display:none" src="images/loading.gif"/>
	 		<span id="NotificationDialogOkButton">
	    		<button dojoType="dijit.form.Button" type="button" onclick="dijit.byId('notificationDialog').hide();return false;">OK</button>
	    	</span>
	 	</td></tr>
	 	</table>
	</div>
</div>

<!--  Dojo/Dijit Menus -->



</#if>

<div id="header"><div id="logo"><img src="images/splot.jpg"/></div></div> 

<!-- end #header --> 
<div id="menu"> 
	<ul> 
		<li><a href="index.html">Home</a></li> 
		<li class="first"><a href="feature_model_edition.html">Feature Model Editor</a></li> 
		<li><a href="automated_analyses.html">Automated Analysis</a></li> 
		<li><a href="product_configuration.html">Product Configuration</a></li> 
		<li><a href="feature_model_repository.html">Feature Model Repository</a></li> 
		<li><a href="http://www.marcilio-mendonca.com/contact_splot.asp">Contact Us</a></li> 
	</ul> 
</div> 
<!-- end #menu --> 

<div id="wrapper"> 
<div class="btm"> 
	<div id="page"> 
		<div id="content">
		 
		 	<#if hasError>
		 	
				<div class="post"> 
					<div  class="entry">
						<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
						<p><a href="javascript:history.back()">Back</a></p>
					</div>
				</div>						
		 	
		 	<#else>
		 
			<div id="instructions" class="hintBox" style="display:none;">
				<a onClick="hideShowHint('show')" href="javascript:void(0)">Show Instructions</a>
			</div>

			<div id="animHintPost" class="hintBox">
				<h1 class="title"><a title="Click to hide this Hint" onClick="hideShowHint('hide')" href="#">Instructions</a></h1>
				<div  class="entry">
					<ul>
					
					
					<li>In order to <b> define a new allocation</b> between a feature model and a workflow,do not tick <i> Feature Model List</i>  and  <i>Workflow List</i>'s check box.</li>
					<li>In order to <b> list existing allocation</b> between a feature model and a workflow, tick <i> Feature Model List</i>  or  <i>Workflow List</i>'s check box.</li>
					
				</ul>
						
					</div>
				<p><a onClick="hideShowHint('hide')" href="javascript:void(0)">Hide instructions</a></p>
			</div>
			<div class="post"> 
				<div  class="entry">
				
					<table border="0" width="100%">
						<tr><td><span class="hintBox2" style="display:none;" id="topMessage2"></span></td></tr>
						<tr><td><span class="hintBox2" style="display:none;" id="topMessage1"></span></td></tr>
					</table>					

					<table width=100% border=0 cellpadding=5 cellspacing=1>					
					
					<!--*********************************  
					     Left-hand side TABLES 
					**********************************-->
					
				 	<tr><td width="60%" align="left" valign="top">
				 	
				 		<table border=0 width=100% cellpadding=3 cellspacing=5>
					 	
							<!--*********************************  
								     Base Information
							**********************************-->
							<form name="base_info">	
					 		<tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('base_information');">Feature Model and Workflow Selection</span></td></tr>
					 		<tr><td>					 			 
								<table border=0 id="base_information" class="feature_model_edition_table1">
							    <tr>
							      
							      <td align="left" ><input class="standardHighlight1" type="radio" name="base_info_radio" value="feature_radio" id="feature_list_radio"/>Feature Model List:</td>
							      <td align="left" >
							      	<select  name="feature_list" id="feature_list"  onchange="onchangeFeatureListSelectedItem()"> 
							      		<option>Select</option>
							     	 	<#list featureModelList as featurelist>
							     	 			<option>${featurelist.feature_model_name}</option>
							     	 	</#list>
								    	
								    </select>
							      	
							      (*)</td>
							      
							    </tr>
							    
							    <tr>
							      <td align="left" ><input class="standardHighlight1" type="radio" name="base_info_radio" value="workflow_radio" id="workflow_list_radio"/>Workflow List:</td>
							      <td align="left" >
							      	<select  name="workflow_list" id="workflow_list" onchange="onchangeWorkflowListSelectedItem()">
							      		<option>Select</option> 
							     	 	<#list workflowlist as workflowlist>
							     	 			<option>${workflowlist.workflow_name}</option>
							     	 	</#list>
								    	
								    </select>
							      	
							      (*)</td>
							      
							    </tr>
							    <tr>
							    	<td align="left" >
											<b>Select feature model OR workflow and</b>
									</td>
									
									<td align="left" ><input class="standardHighlight1" type="button"  onclick="loadViewAllocationInfo()" value="Click Here"/>
									</td>
								</tr>
							    
							    
							    <tr>
						  	 </table>
							  									
					 		</td></tr>
					 		</form>
					 	  <!--*********************************  
								     View Allocation Information
							**********************************-->
								
					 		<tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('view_allocation_information');">View Allocation </span></td></tr>
					 		<tr><td>
									<table border=0 id="view_allocation_information" class="feature_model_edition_table1">
											
										<tr>
												
													<table class="standardTableStyle"   id="allocated_view_information"  name="allocated_view_information">
														<tr>
																<th>#</th>
																<th>Feature Model Name</th>
																<th>Workflow Name</th>
																<th>Task Name</th>
																<th>View Name</th>
																<th>Select</th>
																
																
														</tr>
													 </table>
														
											   
										 		
											</tr>
											
								<tr>
									<td>
										To assign a view to a task, select the task, then the view and click on <b>Save</b>.
									</td>
								</tr>
								
								<tr>
									<td>
										To delete view, first select it in the table and then click on <b>Delete</b>.
									</td>
								</tr>				
								<tr>
								  <td align="left" ><label for="task_list">Task List: </label>
							    
							      	<select  name="task_list" id="task_list"> 
								    </select>
							      (*)</td>

								</tr>
								
								<tr>
								  <td align="left" ><label for="view_list">View List: </label>
							      
							      	<select  name="view_list" id="view_list"> 
								    </select>
							      (*)</td>

								</tr>
								
								<tr>
									<td>
										<input class="standardHighlight1" type="button"  onclick="saveViewAllocationToRepository()" value="Save">
										<input class="standardHighlight1" type="button"  onclick="deleteSelectedRows()" value="Delete">
										<input class="standardHighlight1" type="button"  onclick="loadViewAllocationInfo()" value="Refresh">
										
									</td>	
									
									
												
								</tr>	
									
								
							  	<a href="javascript:void(0)"><b>(*)</b> Mandatory fields if you wish to add your view allocation to SPLOT's view allocation repository</a>								
					 		</td></tr>
					 		
					 		
					 	</table>
				 	</td>
				 	
					<!--*********************************  
					     Right-hand side TABLES 
					**********************************-->
				 	
				 	<td width="40%" align="right" valign="top">
				 	
			 			<table border=0 width=100% cellpadding=3 cellspacing=5>

						<!--*********************************  
						     View Log
						**********************************-->

				 	
						
			  	
						  		
						
						</table>
						 
				 	</td></tr>
				 	</table>
					
					<p class="meta"></p>
					
					
						
				</div>
			</div> <!-- post -->
			
			</#if>
			
		</div> <!-- content --> 
		<div style="clear: both;">&nbsp;</div> 
	</div> 
	<!-- end #page --> 
</div> 
</div> 

<div id="footer"> 
	<p><a href="http://gsd.uwaterloo.ca/">Generative Software Development Lab</a> / <a href="http://csg.uwaterloo.ca">Computer Systems Group</a>, University of Waterloo, Canada, 2009.</p> 
</div> 
<!-- end #footer --> 
	
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-1626595-6");
pageTracker._trackPageview();
} catch(err) {}

</script>
</body>
</html>



