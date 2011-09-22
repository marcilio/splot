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
	var  show_type="${show_type}";
	var  imageFileName="";

	
	
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
	*  when the Apply Selection Filtering check box is changed
	*******************************************************/
	function onchangeApplySelectionFiltering(){

			try{
							if (document.getElementById("apply_selection_filtering").checked==false){		
							
								var selectedType="both";
								
								workflowImage=document.getElementById("workflow_image_src");
								legendImage=document.getElementById("legend_image_src");
								workflowImage.src="";
								legendImage.src="";
									
								if (window.ActiveXObject){
						 		oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
								}else{
									oXMLRequest = new XMLHttpRequest();
								}
								var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=response_workflow_feature_list&selectedType=" +selectedType+"&selectedValue="+selectedType ;
								oXMLRequest.open("GET",strValidationServiceUrl,true);
								oXMLRequest.onreadystatechange = updateWorkflowAndFeatureModelDetails;
								oXMLRequest.send(null);
							}else{
								featureVal=getListSelectedValue(document.getElementById("feature_list")); 
								workflowVal=getListSelectedValue(document.getElementById("workflow_list")); 
								if((featureVal=='Select') && (workflowVal=='Select')){
									workflowImage=document.getElementById("workflow_image_src");
									legendImage=document.getElementById("legend_image_src");
									workflowImage.src="";
									legendImage.src="";
								
									return;
								}else if ((featureVal!='Select') && (workflowVal!='Select')){
									return;
								}else if(featureVal!='Select'){
									workflowImage=document.getElementById("workflow_image_src");
									legendImage=document.getElementById("legend_image_src");
									workflowImage.src="";
									legendImage.src="";
								}else if (workflowVal!='Select'){
									onchangeWorkflowListSelectedItem();
									workflowImage=document.getElementById("workflow_image_src");
									legendImage=document.getElementById("legend_image_src");
									workflowImage.src="";
									legendImage.src="";
								} 
								
							}
							
			}catch(Error){
				alert(Error);
			}
	}
	
	
	/******************************************************
	*  Receive workflow and feature model info from server 
	*******************************************************/	
	function updateWorkflowAndFeatureModelDetails(){
			if (oXMLRequest.readyState == 4){
			 	if (oXMLRequest.status == 200){
			 			strStatus = oXMLRequest.responseText;
			 			list=strStatus.split("/");
						featurePart=list[0];
						workflowPart=list[1];
						
						try{
							document.getElementById("task_list").length = 0;
						}catch(ex){
							alert(ex);
						}
						
						try{
							document.getElementById("view_list").length = 0;
						}catch(ex){
							alert(ex);
						}
						
						deleteTableAllRows("allocated_view_information");
						
						
						/******************************************************
						*  Update feature model list
						*******************************************************/	
						var featureList=new Array();
						featureList=featurePart.split(",");
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

						
						/******************************************************
						*  Update workflow list
						*******************************************************/	

						var workflowList=new Array();
						workflowList=workflowPart.split(",");
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
						
			 		
			 	}
			 }

	}

	/******************************************************
	*  Updates workflow list whenever a feature model is selected
	*******************************************************/
	
	function onchangeFeatureListSelectedItem(){
		val=getListSelectedValue(document.getElementById("feature_list")); 
		workflowVal=getListSelectedValue(document.getElementById("workflow_list")); 
		
		if (val=='Select'){
			workflowImage=document.getElementById("workflow_image_src");
			legendImage=document.getElementById("legend_image_src");
			workflowImage.src="";
			legendImage.src="";
			return;
		}
		
		try{
			if (document.getElementById("apply_selection_filtering").checked==true){
				if (workflowVal=="Select"){
					workflowImage=document.getElementById("workflow_image_src");
					legendImage=document.getElementById("legend_image_src");
					workflowImage.src="";
					legendImage.src="";
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
			}
		}catch(Error){
			alert(Error);
		}
		
	}
	
	
	
	/******************************************************
	*  Receive workflow info from server based on selected feature model
	*******************************************************/	
	function updateWorkflowDetails(){
			if (oXMLRequest.readyState == 4){
			 	if (oXMLRequest.status == 200){
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
					
				}
		    }		
	}
	



	/******************************************************
	*  Updates feature list whenever a workflow is selected
	*******************************************************/
	
	function onchangeWorkflowListSelectedItem(){
		val=getListSelectedValue(document.getElementById("workflow_list")); 
		featureVal=getListSelectedValue(document.getElementById("feature_list")); 
		
		if (val=='Select'){
			workflowImage=document.getElementById("workflow_image_src");
			legendImage=document.getElementById("legend_image_src");
			workflowImage.src="";
			legendImage.src="";
			return;
		}
		
		try{
			if (document.getElementById("apply_selection_filtering").checked==true){
				if (featureVal=="Select"){
					workflowImage=document.getElementById("workflow_image_src");
					legendImage=document.getElementById("legend_image_src");
					workflowImage.src="";
					legendImage.src="";
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
			}
		}catch(Error){
			alert(Error);
		}
		
	}
	
	
	
	/******************************************************
	*  Receive feature model info from server based on selected workflow
	*******************************************************/	
	function updateFeatureModelDetails(){
			if (oXMLRequest.readyState == 4){
			 	if (oXMLRequest.status == 200){
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
					
				}
		    }		
	}
	
	
	/******************************************************
	*  get list's selected index value 
	*******************************************************/
	function getListSelectedValue(listObj){
	
	return (listObj.options[listObj.selectedIndex].text);
	}
		
		
		
		
	/******************************************************
	*  Set Dependency Set
	*******************************************************/
	function setDependencySet(){
	
	
	try{
	
			featureValue=getListSelectedValue(document.getElementById("feature_list"));
			WorkflowValue=getListSelectedValue(document.getElementById("workflow_list"));	
			if ((WorkflowValue=="Select") || (WorkflowValue=="")){
				alert("Workflow must be selected!");
				return;
			}
			
			
			
			if ((featureValue=="Select") && (featureValue=="")){
				alert("Feature model must be selected!");
				return;
			}
	
	
		
			if (window.ActiveXObject){
				oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
			}else{
				oXMLRequest = new XMLHttpRequest();
			}
			var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=create_dependency_set&featureModel=" +featureValue+"&workflow="+WorkflowValue;
			oXMLRequest.open("GET",strValidationServiceUrl,true);
			oXMLRequest.onreadystatechange =setDependencySetResult;
			oXMLRequest.send(null);
	
	
	
	}catch(Error){
		alert(Error);
	}
	
	}	
		
	
	/******************************************************
	*  Receive setDependencySet from server
	*******************************************************/
	function setDependencySetResult(){
				if (oXMLRequest.readyState == 4){
			 		if (oXMLRequest.status == 200){
						strStatus = oXMLRequest.responseText;
						
						if(strStatus=="true"){
							alert("Dependency sets are calculated successfully!");
						}else{
							alert("There is a problem in dependency sets calculation!");
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
			
			
			featureValue=getListSelectedValue(document.getElementById("feature_list"));
			WorkflowValue=getListSelectedValue(document.getElementById("workflow_list"));	
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
				if (oXMLRequest.readyState == 4){
			 		if (oXMLRequest.status == 200){
						strStatus = oXMLRequest.responseText;
						JSONList=strStatus.split("/");
						viewCount=JSONList[0];
						JSONString=JSONList[1];
						receivedViewList=JSONList[2];
						receivedTaskList=JSONList[3];
						receivedConditionList=JSONList[4];
						imageFileName=JSONList[5];
						
						workflowImage=document.getElementById("workflow_image_src");
						legendImage=document.getElementById("legend_image_src");
						
						workflowImage.src="";
						if (imageFileName!=""){
							
							workflowImage.src="extensions/workflow_images/"+imageFileName;
							legendImage.src="extensions/workflow_images/legend.png";
						}						
					

						
						
						
						
						
						
						try{

							if (document.getElementById("task_list")!=null){
								updateTaskList(receivedTaskList);
								updateViewList(receivedViewList);
								updateConditionList(receivedConditionList);
								
							}

						
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
							
							   		
							   		
							   		
							   		taskStop=taskList[j].split("?");
							   		taskPart=taskStop[0];
							   		StopPart=taskStop[1];
							   		
							   		var cell4 = row.insertCell(3);
							   		cell4.innerHTML =taskPart;
						
									var cell5 = row.insertCell(4);
							  		cell5.innerHTML =StopPart;
							
							   		
							  		
							  		var cell6 = row.insertCell(5);
							  		cell6.innerHTML =viewName;
							  		
							  		var cell7 = row.insertCell(6);
							  		var elementRadio = document.createElement("input");
							  		elementRadio.type = "radio";
							  		elementRadio.name="selectToDelete";
							  		cell7.appendChild(elementRadio);
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
			var chkbox = row.cells[6].childNodes[0];
			if(null != chkbox && true == chkbox.checked) {
						deletedIndex=row.cells[0].innerHTML;
						tableSelectedIndex=deletedIndex;
						deletedFeatureName=row.cells[1].innerHTML;
						deletedWorkflowName=row.cells[2].innerHTML;
						deletedTaskName=row.cells[3].innerHTML;
						deletedStopName=row.cells[4].innerHTML;
						deletedViewName=row.cells[5].innerHTML;
						

						deletedTaskStop=deletedTaskName+'?'+deletedStopName;
						
						
						if (window.ActiveXObject){
							oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
						}else{
							oXMLRequest = new XMLHttpRequest();
						}
		
						var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=delete_view_allocation&featureModel=" +deletedFeatureName+"&workflow="+deletedWorkflowName+"&taskName="+deletedTaskStop+"&viewName="+deletedViewName;		
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
				if (oXMLRequest.readyState == 4){
					if (oXMLRequest.status == 200){
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
	*  Update  condition list
	*******************************************************/		
		function updateConditionList(tList){
			try{
				document.getElementById("condition_list").length = 0;
			}catch(ex){
				alert(ex);
			}
			
			var conditionListBox=document.getElementById("condition_list");	
		
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
					conditionListBox.add(newOption,null);
				}catch(Error){
					conditionListBox.add(newOption);
							
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
				featureValue=getListSelectedValue(document.getElementById("feature_list")); 
				workflowValue=getListSelectedValue(document.getElementById("workflow_list")); 
				taskValue=getListSelectedValue(document.getElementById("task_list")); 
				stopValue=getListSelectedValue(document.getElementById("condition_list")); 
				viewValue=getListSelectedValue(document.getElementById("view_list")); 
				
				stopTaskValue=taskValue+'?'+stopValue;
				
		
				
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
		
						var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=save_view_allocation&featureModel=" +featureValue+"&workflow="+workflowValue+"&taskName="+stopTaskValue+"&viewName="+viewValue;		
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
					if (oXMLRequest.readyState == 4){
						if (oXMLRequest.status == 200){
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
		
		
	/******************************************************
	*  checks views coverage 
	*******************************************************/
	function checkViewsCoverage(){
		featureValue=getListSelectedValue(document.getElementById("feature_list")); 
		if ((featureValue=="Select") || (featureValue=="")){
			alert("Please select a feature model");
			return;
		}
	
	
		if (window.ActiveXObject){
			oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		else{
			oXMLRequest = new XMLHttpRequest();
		}
	    
	    try{ 
		    var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=response_uncovered_features&fm_name="+featureValue+"&type=allocated";
			oXMLRequest.open("GET",strValidationServiceUrl,true);
			oXMLRequest.onreadystatechange = onCheckViewsCoverageResult;
			oXMLRequest.send(null);
		}catch(Error){
			alert(Error);
		}	
	}



	/******************************************************
	*  view coverage result
	*******************************************************/
	function onCheckViewsCoverageResult(){
		       	if (oXMLRequest.readyState == 4){
				 if (oXMLRequest.status == 200){
					try{
					 	  strStatus = oXMLRequest.responseText;
						  if (strStatus==""){
						  	alert("All the FD's features are covered with existing allocated views.")
						  }else{
						  	alert("Uncovered features are:   "+strStatus);
						  }
						  
						  
						
					 	
					  }catch(error){
					  	alert(error);
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
				<a onClick="hideShowHint('show')" href="javascript:void(0)">Show Instructions and Hints</a>
			</div>

			<div id="animHintPost" class="hintBox">
				<h1 class="title"><a title="Click to hide this Hint" onClick="hideShowHint('hide')" href="#">Instructions and Hints</a></h1>
				<div  class="entry">

					<ul>					
					<#if show_type=="readonly">
					  <li>Use the <b> Apply Selection Filtering</b> check box  to filter feature models and workflows.</li>
					  <li>In order to load available view, task, and stop allocations select a feature model, a workflow, and then click on <b>List View Allocations</b>.</li>
					  <li>Each view can be linked to only one task and only one stop.</li>
					  <li>By definition, stops are selected from the workflow's conditions.</li>

					<#else>
					  <li>Use the <b> Apply Selection Filtering</b> check box  to filter feature models and workflows.</li>
					  <li>In order to load available view, task, and stop allocations select a feature model, a workflow, and then click on <b>List View Allocations</b>.</li>
					  <li>Each view can be linked to only one task and only one stop.</li>
					  <li>By definition, stops are selected from the workflow's conditions.</li>
					</#if> 

					
				</ul>
						
					</div>
				<p><a onClick="hideShowHint('hide')" href="javascript:void(0)">Hide instructions and Hints</a></p>
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
							      <td align="left" ><input class="standardHighlight1" type="checkbox" name="apply_selection_filtering"  id="apply_selection_filtering" onchange="onchangeApplySelectionFiltering()"/>Apply Selection Filtering</td>
							    </tr>
								
							    <tr>
							      
							      <td align="left" >Feature Model List:</td>
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
							      <td align="left" >Workflow List:</td>
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
									
									<td align="left" ><input class="standardHighlight1" type="button"  onclick="loadViewAllocationInfo()" value="List View Allocations"/>
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
																<th>Stop Name</th>
																<th>View Name</th>
																<th>Select</th>
																
																
														</tr>
													 </table>
														
											   
										 		
											</tr>
							<#if show_type!="readonly">
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
								  <td align="left" ><label for="condition_list">Stop List: </label>
							      
							      	<select  name="condition_list" id="condition_list"> 
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
										<button  class="standardHighlight1"  onClick="checkViewsCoverage();return false;" type="button">Evaluate Views Coverage</button>
										<button  class="standardHighlight1"  onClick="setDependencySet();return false;" type="button">Dependency Sets</button>
										
										
									</td>	
									
									
												
								</tr>	
									
								
							  	<a href="javascript:void(0)"><b>(*)</b> Mandatory fields if you wish to add your view allocation to the SPLOT's view repository</a>								
					 		</td></tr>

					 	</#if>			
					 	
								
					 		<tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('workflow_image');">Overview of the workflow </span></td></tr>
					 		<tr><td>
					 					<table border=0  id="workflow_image" class="feature_model_edition_table1">
					 					<tr><td width="100%">
					 						<div><img  id="workflow_image_src"> </div>
					 						
					 						<div><img id="legend_image_src"></div>
					 						
					 					</td></tr>
					 					</table>

					 					 		

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



