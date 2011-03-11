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
<meta http-equiv="refresh" content="20000; url=/SPLOT/start.html"/>
<title>Welcome to the Software Product Lines Online Tools Homepage</title>

<link type="text/css" rel="stylesheet" href="splot.css"/>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dijit/themes/nihilo/nihilo.css"/>

<script type="text/javascript"
	 	src="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dojo/dojo.xd.js" 
	 	djConfig="parseOnLoad: true, isDebug: false, dojoBlankHtmlUrl: 'blank.html'">
</script>
<script type="text/javascript" src="js/ajax.js"></script> 

<script type="text/javascript" src="js/ajax-dynamic-content.js"></script>
<script type="text/javascript" src="js/json_utils.js"></script> 
<script type="text/javascript" src="js/utils.js"></script> 
<script type="text/javascript" src="js/json_sans_eval.js"></script> 

<#if !hasError>

<style type="text/css">
  .hintBox {
    margin-top: 0px;
    color: #292929;
    width: 880px;
    border: 1px solid #BABABA;
    background-color: white;
    padding-left: 10px;
    padding-right: 10px;
    margin-left: 10px;
    margin-bottom: 1em;
    -o-border-radius: 10px;
    -moz-border-radius: 12px;
    -webkit-border-radius: 10px;
    -webkit-box-shadow: 0px 3px 7px #adadad;
    border-radius: 10px;
    -moz-box-sizing: border-box;
    -opera-sizing: border-box;
    -webkit-box-sizing: border-box;
    -khtml-box-sizing: border-box;
    box-sizing: border-box;
    overflow: hidden;
  }
</style>

<script type="text/javascript"> 

	dojo.require("dijit.form.Button");
	dojo.require("dijit.Dialog");
	dojo.require("dijit.ProgressBar");	
	dojo.require("dojo.fx"); // for animate showing/hiding the Hint
	dojo.require("dojo.parser");
	
	
	setInterval("updateConfigurationElements('reload','precedence','true')",3000);
	
	
	/******************************************************
	*  On Load
	*******************************************************/
	dojo.addOnLoad(function() {
		
		
		buildFeatureModel();
		dijit.byId("confProgressBar").update({progress: ${countInstantiatedFeatures}, maximum: ${countFeatures} });
	});
	
	
	
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
	function hideHint() {
		dojo.fx.wipeOut({node: "animHintPost",duration: 1000}).play();
		document.getElementById("hintShortText").style.display = "block";
	}
	
	/******************************************************
	*  Hide Configuration Conflict Resolution dialog
	*******************************************************/
	function hideDialog() {
		dijit.byId('conflictingDecisionsDialog').hide();
	}
	
	/******************************************************
	*  Toggle feature as confirmed in the configuration conflict resolution dialog  
	*******************************************************/
	var toggleFeatureId = '';
	function toggleFeature(arguments) {
		updateConfigurationElements('toggle', 'toggleFeature', toggleFeatureId);
	}
	
	/******************************************************
	*  Set feature to be toggled   
	*******************************************************/
	function setToggleFeature(featureId) {
		toggleFeatureId = featureId;
	}
	
	
	/******************************************************
	*  Save Configuration to repository  
	*******************************************************/
	function saveConfigurationToRepository() {
		
		if (window.ActiveXObject){
			oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		else{
			oXMLRequest = new XMLHttpRequest();
		}
		
		
		
		 if(workflowExistence=="true"){
		
		 
		 	    var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=fcw_instance_manager&requestType=save&operation=repository&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&selectedModels="+featureModelFileName+"&taskName="+trimAll(taskName)+"&userKey="+userKey+"&workflowName="+trimAll(workflowName)+"&modelName="+trimAll(featureModelName)+"&userName="+trimAll(userName)+"&userID="+trimAll(userID)+"&newSession=false";
				oXMLRequest.open("GET",strValidationServiceUrl,true);
				oXMLRequest.onreadystatechange = OnSaveConfigurationToRepository;
				oXMLRequest.send(null);
		 
	  	 }else{
			    var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=export_configuratiom_xml_file&actionType=save";
			    
				oXMLRequest.open("GET",strValidationServiceUrl,true);
				oXMLRequest.onreadystatechange = OnSaveConfigurationToRepository;
				oXMLRequest.send(null);	  
		
	   }
		

	}
	
	
	
	/******************************************************
	*  Result:Save Configuration to repository  
	*******************************************************/	
	function OnSaveConfigurationToRepository() {
	      	if (oXMLRequest.readyState == 4){
			 if (oXMLRequest.status == 200){
			 	  strStatus = oXMLRequest.responseText;
			 	  	var resultInJson = strStatus;
					var resultJsonObj = jsonParse(resultInJson);

				    resultStatus=resultJsonObj.result;
				    resultValue=resultJsonObj.value;
				    resultUserKey=resultJsonObj.user_key;
				    
				    if(resultStatus=="true"){
				    	userKey=resultUserKey
				    }
				    
				    
				    alert(resultValue);
				    
				    
			 	  
			 }
			}
			 	  
	
	}
	
	
	
	/******************************************************
	*  Go next task  
	*******************************************************/
	function goNextTask() {
		
		if (window.ActiveXObject){
			oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		else{
			oXMLRequest = new XMLHttpRequest();
		}
		
	    var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=export_configuratiom_xml_file&actionType=next";
	    
		oXMLRequest.open("GET",strValidationServiceUrl,true);
		oXMLRequest.onreadystatechange = OnGoNextTask;
		oXMLRequest.send(null);
	}
	
	
	
	/******************************************************
	*  Result:Go next task  
	*******************************************************/	
	function OnGoNextTask() {
	      	if (oXMLRequest.readyState == 4){
			 if (oXMLRequest.status == 200){
			 	  strStatus = oXMLRequest.responseText;
			 	  	var resultInJson = strStatus;
					var resultJsonObj = jsonParse(resultInJson);

				    resultStatus=resultJsonObj.result;
				    resultValue=resultJsonObj.value;
				    
				    
				    if(resultStatus=="true"){
				    	window.close();
				    }else{
				    	alert(resultValue);
				    }
				    
				    
				    
			 	  
			 }
			}
			 	  
	
	}	
	
	
	
	/******************************************************
	*  Exit window 
	*******************************************************/
	function exitWindow() {
		window.close();
	}
	
	
	
	/******************************************************
	*  Exit configuration 
	*******************************************************/
	function exitConfiguration() {
		
		if (window.ActiveXObject){
			oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		else{
			oXMLRequest = new XMLHttpRequest();
		}
		
	    var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=export_configuratiom_xml_file&actionType=exit";
	    
		oXMLRequest.open("GET",strValidationServiceUrl,true);
		oXMLRequest.onreadystatechange = OnExitConfiguration;
		oXMLRequest.send(null);
	}
	
	

	/******************************************************
	*  Result:Exit configuration 
	*******************************************************/	
	function OnExitConfiguration() {
	      	if (oXMLRequest.readyState == 4){
			 if (oXMLRequest.status == 200){
			 	  strStatus = oXMLRequest.responseText;
			 	  	var resultInJson = strStatus;
					var resultJsonObj = jsonParse(resultInJson);

				    resultStatus=resultJsonObj.result;
				    resultValue=resultJsonObj.value;
				    
				    if(resultStatus=="true"){
				    	window.close();
				    }else{
				    	alert(resultValue);
				    }
				    
				    
				    
			 	  
			 }
			}
			 	  
	
	}	
	
	
</script>

<script type="text/javascript"> 
<!--
	/******************************************************
	*  Detect conflicts when feature is toggled
	*******************************************************/
	function detectToggleConflicts(toggleFeatureId) {
	
		viewName=getListSelectedValue(document.getElementById('view_list'));
		viewType=getListSelectedValue(document.getElementById('visualization_list'));
	
	
		setToggleFeature(toggleFeatureId)
		ajaxObj = new sack("/SPLOT/MultiplePerspectiveConfigurationViewsServlet");
	  	ajaxObj.method = "GET";
	  	ajaxObj.onCompletion = function() 
	  	{
	  		dojo.byId('conflictingDecisionsDialogContent').innerHTML = ajaxObj.response;
	  		dojo.parser.parse();  // this is required for dojo to recognize dialog buttons
	  		dijit.byId('conflictingDecisionsDialog').show();
	  	}
	  	
	  	 if(workflowExistence=="true"){
	  	 	 ajaxObj.runAJAX("action=fcw_instance_manager&requestType=conflicts&operation=detect" + "&toggleFeature=" + toggleFeatureId+"&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&selectedModels="+featureModelFileName+"&taskName="+trimAll(taskName)+"&userKey="+userKey+"&workflowName="+trimAll(workflowName)+"&modelName="+trimAll(featureModelName)+"&userName="+trimAll(userName)+"&userID="+trimAll(userID)+"&newSession=false");  		
	  	 }else{
	  	 	  ajaxObj.runAJAX("action=detect_conflicts" + "&toggleFeature=" + toggleFeatureId+"&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&selectedModels="+featureModelFileName+"&workflowExistence="+workflowExistence+"&userKey="+userKey);  		
	  	 }
	  	
	}
    
	/******************************************************
	*  Reset configuration
	*******************************************************/
	function resetConfiguration() {
		viewName=getListSelectedValue(document.getElementById('view_list'));
		viewType=getListSelectedValue(document.getElementById('visualization_list'));
		
		
	  if(workflowExistence=="true"){
	  	   window.location = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=fcw_instance_manager&requestType=initialization&operation=reset&selectedModels="+trimAll(featureModelFileName)+"&workflowName="+trimAll(workflowName)+"&taskName="+trimAll(taskName)+"&modelName="+trimAll(featureModelName)+"&userKey="+trimAll(userKey)+"&userName="+trimAll(userName)+"&userID="+trimAll(userID)+"&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&newSession=false";
	  
	  }else{
	  	   window.location = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=interactive_configuration_main&op=reset&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&selectedModels="+featureModelFileName+"&workflowExistence="+workflowExistence+"&userKey="+userKey;
	  }			
		
	}
	
	
	
   /******************************************************
	*  export:csv format
	*******************************************************/
	function exportToCSVFile() {
	  	   window.location ="/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=fcw_instance_manager&requestType=save&operation=csv&userKey="+trimAll(userKey);
	}
	
	
	 /******************************************************
	*  export:xml format
	*******************************************************/
	function exportToXMLFile() {
	  	   window.location ="/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=fcw_instance_manager&requestType=save&operation=xml&userKey="+trimAll(userKey);
	}
	
	/******************************************************
	*  Highlight selection button
	*******************************************************/
	function highlightSelectionButton(img,imgname) {
	  img.src = "/SPLOT/images/" + imgname;
	}
	
	/******************************************************
	*  Expand/collapse feature tree subtrees
	*******************************************************/
	function expandcollapse(featureid){
	   var el = document.getElementById(featureid + "_children");
	   var img = document.getElementById(featureid+"_icon"); 
	   if ( el.style.display != 'none' ) {
	       el.style.display = 'none';
	       img.src = "/SPLOT/images/plus.jpg";
	   }
	   else {
	       el.style.display = '';
	       img.src = "/SPLOT/images/minus.jpg";
	   }   
	}
	
	/******************************************************
	*  Build feature model
	*******************************************************/
	function buildFeatureModel() 
	{   
	
	
	
	 
	  <#if viewType=="none"> 
				var featureObj;
				<#assign shift = 0>
				<#assign countSelectedFeatures = 0>
				<#list features as feature>
					<#if feature.feature_decision != "-1">
						<#assign countSelectedFeatures = countSelectedFeatures+1>
					</#if>
				    <#assign parentid = feature.feature_parentid + "_children"> 
					<#if feature.feature_parentid="">
				       <#assign parentid = "fm">
					</#if>
					// ${feature.feature_id} --> ${parentid}
					featureObj = document.getElementById('${parentid}');
					featureObj.innerHTML += 
						"<div id=\"${feature.feature_id}\">" + 
						
						"<div id=\"${feature.feature_id}_main\">" +
						"${feature.configurationFeatureElement?js_string}" + 
						"</div>" + 	
					 	"<div id=\"${feature.feature_id}_children\" style=\"position: relative; left: ${shift}px;\"></div>" +
					 	"</div>";
				</#list>
		
		</#if>
		
		<#if viewType=="greyed"> 
			 	var featureObj;
				<#assign shift = 0>
				<#assign countSelectedFeatures = 0>
				<#list features as feature>
					<#if feature.feature_decision != "-1">
						<#assign countSelectedFeatures = countSelectedFeatures+1>
					</#if>
				    <#assign parentid = feature.feature_parentid + "_children"> 
					<#if feature.feature_parentid="">
				       <#assign parentid = "fm">
					</#if>
					// ${feature.feature_id} --> ${parentid}
						featureObj = document.getElementById('${parentid}');
					featureObj.innerHTML += 
						"<div id=\"${feature.feature_id}\">" + 
						
						"<div id=\"${feature.feature_id}_main\">" +
						"${feature.configurationFeatureElement?js_string}" + 
						"</div>" + 	
					 	"<div id=\"${feature.feature_id}_children\" style=\"position: relative; left: ${shift}px;\"></div>" +
					 	"</div>";
				</#list>
		
		</#if>
		
		<#if viewType=="pruned"> 
			    var fsr="";
				var featureObj;
				<#assign shift = 0>
				<#assign countSelectedFeatures = 0>
				<#list features as feature>
					
					<#if feature.feature_show=="true">
						<#if feature.feature_decision != "-1">
							<#assign countSelectedFeatures = countSelectedFeatures+1>
						</#if>
					    <#assign parentid = feature.feature_parentid + "_children"> 
						<#if feature.feature_parentid="">
					       <#assign parentid = "fm">
						</#if>
						// ${feature.feature_id} --> ${parentid}
						featureObj = document.getElementById('${parentid}');
						featureObj.innerHTML += 
							"<div id=\"${feature.feature_id}\">" + 
							"<div id=\"${feature.feature_id}_main\">" +
							
						"${feature.configurationFeatureElement?js_string}" + 
							"</div>" + 			
						 	"<div id=\"${feature.feature_id}_children\" style=\"position: relative; left: ${shift}px;\"></div>" +
						 	"</div>";
					<#else>
						<#if feature.feature_decision != "-1">
							<#assign countSelectedFeatures = countSelectedFeatures+1>
						</#if>
					    <#assign parentid = feature.feature_parentid + "_children"> 
						<#if feature.feature_parentid="">
					       <#assign parentid = "fm">
						</#if>
						// ${feature.feature_id} --> ${parentid}
						featureObj = document.getElementById('${parentid}');
						featureObj.innerHTML += 
							"<div id=\"${feature.feature_id}\">" + 
							"<div id=\"${feature.feature_id}_main\">" +
						 	"<div id=\"${feature.feature_id}_children\" style=\"position: relative; left: ${shift}px;\"></div>" +
						 	"</div>";
						 						
					</#if> 						
				</#list>		
		</#if>
		
		<#if viewType=="collapsed"> 
			    var fsr="";
				var featureObj;
				<#assign shift = 0>
				<#assign countSelectedFeatures = 0>
				<#list features as feature>
					
					<#if (feature.feature_show=="true") &&   (feature.feature_available=="true") >
						<#if feature.feature_decision != "-1">
							<#assign countSelectedFeatures = countSelectedFeatures+1>
						</#if>
					    <#assign parentid = feature.feature_connectedid + "_children"> 
						<#if feature.feature_connectedid="">
					       <#assign parentid = "fm">
						</#if>
						// ${feature.feature_id} --> ${parentid}
						featureObj = document.getElementById('${parentid}');
						featureObj.innerHTML += 
							"<div id=\"${feature.feature_id}\">" + 
							"<div id=\"${feature.feature_id}_main\">" +
						"${feature.configurationFeatureElement?js_string}" + 
	
							"</div>" + 
							
						
							
																    
							
										
						 	"<div id=\"${feature.feature_id}_children\" style=\"position: relative; left: ${shift}px;\"></div>" +
						 	"</div>";
					<#else>
						<#if feature.feature_decision != "-1">
							<#assign countSelectedFeatures = countSelectedFeatures+1>
						</#if>
					    <#assign parentid = feature.feature_connectedid + "_children"> 
						<#if feature.feature_connectedid="">
					       <#assign parentid = "fm">
						</#if>
						// ${feature.feature_id} --> ${parentid}
						featureObj = document.getElementById('${parentid}');
						featureObj.innerHTML += 
							"<div id=\"${feature.feature_id}\">" + 
							"<div id=\"${feature.feature_id}_main\">" + "</div>" +
						 	"<div id=\"${feature.feature_id}_children\" style=\"position: relative; left: ${shift}px;\"></div>" +
						 	"</div>";
						 						
					</#if> 						
				</#list>		
		</#if>
		
		
		featureModelFileName="${selectedModels}";
		workflowExistence="${workflowExistence}";
		userKey="${userKey}";
		workflowName="${workflowName}";
		newSession="${newSession}";
		taskName="${taskName}";
		featureModelName="${modelName}";
		viewName="${viewName}";
		viewType="${viewType}";
		userName="${userName}";
		userID="${userID}";
		
		
		
		
		
		
		
		
		<#if uncoveredFeatures!="">
			alert("There are uncovered features in the views, and the configuration would not be saved:"+"${uncoveredFeatures}");
		</#if>
	} 
	
	
	/******************************************************
	*  get list selected value
	*******************************************************/
	function getListSelectedValue(listObj){
	
	return (listObj.options[listObj.selectedIndex].text);
	}
	
	
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
	*  Rebuild feature model
	*******************************************************/

	function rebuildFeatureModel(){
	viewName=getListSelectedValue(document.getElementById('view_list'));
	viewType=getListSelectedValue(document.getElementById('visualization_list'));
	
	
	if(workflowExistence=="true"){
	  
	  	   window.location = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=fcw_instance_manager&requestType=initialization&operation=reset&selectedModels="+trimAll(featureModelFileName)+"&workflowName="+trimAll(workflowName)+"&taskName="+trimAll(taskName)+"&modelName="+trimAll(featureModelName)+"&userKey="+trimAll(userKey)+"&userName="+trimAll(userName)+"&userID="+trimAll(userID)+"&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&newSession=false";
	  
	  }else{
		   window.location = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=interactive_configuration_main&op=rebuild&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&selectedModels="+featureModelFileName+"&workflowExistence="+workflowExistence+"&userKey="+userKey;
	  }	
	
	
	}     
	
	
	
	/******************************************************
	*  Reload feature model
	*******************************************************/

	function reloadFeatureModel(){
	viewName=getListSelectedValue(document.getElementById('view_list'));
	viewType=getListSelectedValue(document.getElementById('visualization_list'));
	
	  if(workflowExistence=="true"){
	  
	  	   window.location = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=fcw_instance_manager&requestType=initialization&operation=reset&selectedModels="+trimAll(featureModelFileName)+"&workflowName="+trimAll(workflowName)+"&taskName="+trimAll(taskName)+"&modelName="+trimAll(featureModelName)+"&userKey="+trimAll(userKey)+"&userName="+trimAll(userName)+"&userID="+trimAll(userID)+"&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&newSession=false";
	  
	  }else{
			 window.location = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=interactive_configuration_main&op=reset&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&selectedModels="+featureModelFileName+"&workflowExistence="+workflowExistence+"&userKey="+userKey;
	  }	
	  
	  
	
	}     
	
	
	
	
	

	/******************************************************
	*  Update configuration elements on page
	*******************************************************/
	function updateConfigurationElements(operation, parameter, value) {
		parameters = '';
		if (typeof parameter != 'undefined' && typeof value != 'undefined' ) {
			parameters = '&' + parameter + '=' + value; 
		}
		viewName=getListSelectedValue(document.getElementById('view_list'));
	    viewType=getListSelectedValue(document.getElementById('visualization_list'));
	    tmpURL="";
	    
	    if(workflowExistence=="true"){
	      	     tmpURL="/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=fcw_instance_manager&requestType=configuration&operation=" + operation + parameters+"&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&selectedModels="+featureModelFileName+"&userKey="+userKey+"&modelName="+featureModelName+"&workflowName="+workflowName+"&newSession=false"+"&taskName="+taskName+"&userName="+userName+"&userID="+userID;
	    }else{
	      	     tmpURL="/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=interactive_configuration_updates&op=" + operation + parameters+"&viewType="+trimAll(viewType)+"&viewName="+trimAll(viewName)+"&selectedModels="+featureModelFileName+"&workflowExistence="+workflowExistence+"&userKey="+userKey;
	    }


		if(operation=="reload"){
			    var xhrArgs = {
	       url:tmpURL, 
           sync : false,
            handleAs: "xml",
            load: function(response, ioArgs) {
            	
            	
            	closeNotificationDialog();
				
				
				xmlDoc = response.documentElement;
				
				// Update Feature Model and list of features included in the current configuration
				features = xmlDoc.getElementsByTagName("feature");
				for( i = 0 ; i < features.length ; i++ ) {
					featureDivElement = features[i].getAttribute("id") + "_main";
					featureDivContent = features[i].childNodes[0].nodeValue;
					featureDivContent = featureDivContent.replace(/(\r\n|[\r\n])/g,'');
					document.getElementById(featureDivElement).innerHTML = featureDivContent;
				}
				
				/***********************************************************************************************     
				 * Identify operation: conf, completion, undo, toggle
				 *    conf: new features instantiated, a single new step to add 
				 *    completion: new features instantiated, a single new step to add
				 *    undo: new features to update, number of steps to eliminate from steps table
				 *    toggle: new features to update, number of steps to eliminate, steps to add
				***********************************************************************************************/     
				
				op = xmlDoc.getElementsByTagName("op")[0].getAttribute("value");
			
				countFeatures = xmlDoc.getElementsByTagName("countFeatures")[0].getAttribute("value");
				countInstantiatedFeatures = xmlDoc.getElementsByTagName("countInstantiatedFeatures")[0].getAttribute("value");
				
				if( (typeof countFeatures != 'undefined') && (typeof countInstantiatedFeatures != 'undefined') ) {
					//progressBarValue = Math.floor(countInstantiatedFeatures/countFeatures);
					dijit.byId("confProgressBar").update({progress: countInstantiatedFeatures, maximum: countFeatures });				
				}
			
					        
		        if(op == "reload"){
		        	confStepsTableObj = document.getElementById('confStepsTable');
		        		for(i=confStepsTableObj.rows.length-1;i>3;i--  ) {
			  			confStepsTableObj.deleteRow(i-1);
			  		}
		        
		        
		        }
				confSteps = xmlDoc.getElementsByTagName("step");
				
				for( i = 0 ; i < confSteps.length ; i++ ) {
					// expand configuration steps table 
		        	confStepDivContent = confSteps[i].childNodes[0].nodeValue;
		        	confStepDivContent = confStepDivContent.replace(/(\r\n|[\r\n])/g,'');
		     		
					confStepsTableObj = document.getElementById('confStepsTable');     		
		     		confStepsTableRow = confStepsTableObj.insertRow(confStepsTableObj.rows.length-1);
		     		 
		     		startIndex = confStepDivContent.indexOf("<td>");
		     		endIndex = confStepDivContent.indexOf("</td>", startIndex);
		     		cellIndex = 0;
		     		while( startIndex != -1 && endIndex != -1 ) {
		     			cellContent = confStepDivContent.substring(startIndex+4,endIndex);
					 	cellObj = confStepsTableRow.insertCell(cellIndex++);
					 	cellObj.innerHTML = cellContent;
		     			startIndex = confStepDivContent.indexOf("<td>", startIndex+1);
		     			endIndex = confStepDivContent.indexOf("</td>", endIndex+1)
					}
					
					// check if configuration is done
					done = xmlDoc.getElementsByTagName("done")[0].getAttribute("value");
					if ( done == 'true' ) {
						document.getElementById('auto-completion-element').style.display = 'none';
						document.getElementById('configuration-done-element').style.display ='';				
					}
		     	}
            },
            error: function(error) {
                closeNotificationDialog();                
                alert('Oops, SPLOT behaved like a bad boy :) If the error persists contact the SPLOT team.');
     
            }
        }
        
      	
      
      		triggerNotificationDialog('reload', 'SPLOT Product Configurator', 'Processing ...');
 	        dojo.xhrGet(xhrArgs);   
		
		}else{
		var xhrArgs = {
	       url:tmpURL, 
           sync : false,
            handleAs: "xml",
            load: function(response, ioArgs) {
            	
            	
            	closeNotificationDialog();
				
				
				xmlDoc = response.documentElement;
				
				// Update Feature Model and list of features included in the current configuration
				features = xmlDoc.getElementsByTagName("feature");
				for( i = 0 ; i < features.length ; i++ ) {
					featureDivElement = features[i].getAttribute("id") + "_main";
					featureDivContent = features[i].childNodes[0].nodeValue;
					featureDivContent = featureDivContent.replace(/(\r\n|[\r\n])/g,'');
					document.getElementById(featureDivElement).innerHTML = featureDivContent;
				}
				
				/***********************************************************************************************     
				 * Identify operation: conf, completion, undo, toggle
				 *    conf: new features instantiated, a single new step to add 
				 *    completion: new features instantiated, a single new step to add
				 *    undo: new features to update, number of steps to eliminate from steps table
				 *    toggle: new features to update, number of steps to eliminate, steps to add
				***********************************************************************************************/     
				
				op = xmlDoc.getElementsByTagName("op")[0].getAttribute("value");
			
				countFeatures = xmlDoc.getElementsByTagName("countFeatures")[0].getAttribute("value");
				countInstantiatedFeatures = xmlDoc.getElementsByTagName("countInstantiatedFeatures")[0].getAttribute("value");
				
				if( (typeof countFeatures != 'undefined') && (typeof countInstantiatedFeatures != 'undefined') ) {
					//progressBarValue = Math.floor(countInstantiatedFeatures/countFeatures);
					dijit.byId("confProgressBar").update({progress: countInstantiatedFeatures, maximum: countFeatures });				
				}
			
				if ( op == "undo" || op == "toggle") {
					// Shrink configuration steps table
					confStepsTableObj = document.getElementById('confStepsTable');
					countUndoSteps = parseInt(xmlDoc.getElementsByTagName("countUndoSteps")[0].getAttribute("value"));
					for( i = 0 ; i < countUndoSteps ; i++ ) {
			  			confStepsTableObj.deleteRow(confStepsTableObj.rows.length-2);
			  		}
			  		// update auto-completion and configuration elements
					document.getElementById('auto-completion-element').style.display = '';
					document.getElementById('configuration-done-element').style.display ='none';						  		
		     	}
		        
		        
				confSteps = xmlDoc.getElementsByTagName("step");
				
				for( i = 0 ; i < confSteps.length ; i++ ) {
					// expand configuration steps table 
		        	confStepDivContent = confSteps[i].childNodes[0].nodeValue;
		        	confStepDivContent = confStepDivContent.replace(/(\r\n|[\r\n])/g,'');
		     		
					confStepsTableObj = document.getElementById('confStepsTable');     		
		     		confStepsTableRow = confStepsTableObj.insertRow(confStepsTableObj.rows.length-1);
		     		 
		     		startIndex = confStepDivContent.indexOf("<td>");
		     		endIndex = confStepDivContent.indexOf("</td>", startIndex);
		     		cellIndex = 0;
		     		while( startIndex != -1 && endIndex != -1 ) {
		     			cellContent = confStepDivContent.substring(startIndex+4,endIndex);
					 	cellObj = confStepsTableRow.insertCell(cellIndex++);
					 	cellObj.innerHTML = cellContent;
		     			startIndex = confStepDivContent.indexOf("<td>", startIndex+1);
		     			endIndex = confStepDivContent.indexOf("</td>", endIndex+1)
					}
					
					// check if configuration is done
					done = xmlDoc.getElementsByTagName("done")[0].getAttribute("value");
					if ( done == 'true' ) {
						document.getElementById('auto-completion-element').style.display = 'none';
						document.getElementById('configuration-done-element').style.display ='';				
					}
		     	}
            },
            error: function(error) {
                closeNotificationDialog();                
                alert('Oops, SPLOT behaved like a bad boy :) If the error persists contact the SPLOT team.');
     
            }
        }
        
      	
      
      		triggerNotificationDialog('wait', 'SPLOT Product Configurator', 'Processing ...');
 	        dojo.xhrGet(xhrArgs);   
		
		}

	}		
-->

</script>

<script type="text/javascript"><!--
	/******************************************************
	*  Close notification dialog 
	*******************************************************/
	function closeNotificationDialog() {
		dijit.byId('notificationDialog').hide();
	}
			
	/******************************************************
	*  Trigger notification dialog 
	*******************************************************/
	function triggerNotificationDialog(opType, title, message) {		
		dojo.byId('notificationDialogContent').innerHTML = message;
		if ( opType == 'wait' ) {
			dojo.byId('notificationDialogContentLoadingImage').style.display = 'block';
			dojo.byId('errorImage').style.display = 'none';
			dojo.byId('NotificationDialogOkButton').style.display = 'none';
		}
		else if ( opType == 'message' ) {
			dojo.byId('notificationDialogContentLoadingImage').style.display = 'none';
			dojo.byId('errorImage').style.display = 'none';
			dojo.byId('NotificationDialogOkButton').style.display = 'block';
		} 
		else if ( opType == 'error' ) {
			dojo.byId('errorImage').style.display = 'block';
			dojo.byId('notificationDialogContentLoadingImage').style.display = 'none';
			dojo.byId('NotificationDialogOkButton').style.display = 'block';
		}
		
		if (opType!="reload"){
		dijit.byId('notificationDialog').attr('title', title);
		dijit.byId('notificationDialog').show();
		}
	}
--> 
</script>

</#if>

</head>
<body class="nihilo">

<!-- dialog for conflict resolution -->
<div dojoType="dijit.Dialog"
	 style="display:none" 
	 id="conflictingDecisionsDialog" 
	 title="Configuration Conflict Resolution"
	 execute="toggleFeature(arguments[0])">	 
	 <div id="conflictingDecisionsDialogContent"></div>
</div>

<div id="header"><div id="logo"><img src="images/splot.jpg"/></div></div> 

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

<!-- end #header --> 
<div id="menu"> 
	<ul> 
		<li><a href="index.html">Home</a></li> 
		<li><a href="feature_model_edition.html">Feature Model Editor</a></li> 
		<li><a href="automated_analyses.html">Automated Analysis</a></li> 
		<li class="first"><a href="product_configuration.html">Product Configuration</a></li> 
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
				<ul>
				
					<li>
						<p>
							To load features of a view, first select the view form <b>View List</b>, then choose the <b>Visualization</b> type according to the description below, and <b>Load View</b>:
							<ul>
								<li>
									<b>none</b>: all features of the original feature diagram are loaded.
								</li>
								<li>
									<b>greyed</b>: all features of the original feature diagram are loaded, but the features that do not belong to the view are <i>greyed out</i>. 
								</li>
								<li>
									<b>pruned</b>: features that belong to the view are loaded, features that are not in the view are <i>pruned</i>, but 
												   features that appear on a path between a feature in the view and the root are <i>greyed out</i>. 
												  
								</li>
								
								<li>
									<b>collapsed</b>: all the features that do not belong to the view are <i>pruned</i>. A feature in the view whose parent or ancestors are pruned is connected to the closest ancestor that is still in the view.  
												  
								</li>
								
							</ul>
						</p>
					</li>
					<li>
						<p>
							In order to simplify the configuration process you can focus on <i>selecting</i> the features that you want and use the
							<i>auto-completion</i> function (<b><i>Less Features</i></b> option) to <i>deselect</i> remaining features 
							automatically. Alternatively, you can use the opposite strategy, i.e., focus on deselecting features and use auto-completion 
							to select remaining features by choosing the <b><i>More Features</i></b> option.
						</p>
					</li>
					
					
					
				</ul>
				<p><a onClick="hideShowHint('hide')" href="javascript:void(0)">Hide instructions and Hints</a></p>
			</div>

				
				<div id="view">
						<table width=50% border=0 cellpadding=5 cellspacing=1>
						 <tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('view_option');">View Options</span></td></tr>
					 		<tr><td>					 			 
								<table border=0 id="view_option" class="feature_model_edition_table1">
								
							    <tr>
							    
							    <td align="left" ><label for="view_list">View List: </label></td>
							    
								<#if workflowExistence=="false"> 
								<td align="left" >
							      	<select name="view_list" id="view_list" autocomplete="true"> 
							      	
							      		<#if viewName=="none">
							     	 		<option selected="selected">none</option>
							     	 	<#else>
							     	 		<option>none</option>	
							     	 	</#if>
							     	 	
							     	 			
										<#list fm_views as fm_list>
											<#if  fm_list.fm_name==modelName >
												<#list fm_list.views as fm_view_list>
													<#if viewName==fm_view_list.view_name>
														<option selected="selected">${fm_view_list.view_name}</option>
													<#else>
														<option>${fm_view_list.view_name}</option>
													</#if>	
												</#list>
											</#if>
										</#list>
				
									</select>

							      	
							      </td>
								<#else>
							      <td align="left" >
							      	<select name="view_list" id="view_list" autocomplete="true"> 
							      			<option selected="selected">${viewName}</option>
									</select>
							      </td>
								</#if>
																    
							    
							    
							    
							      
							    
							      <td align="left" ><label for="visualization_list">Visualization: </label></td>
							      <td align="left" >
							      	<select name="visualization_list" id="visualization_list" autocomplete="true"> 
							      		
							      		<#if viewType=="none">
							     	 		<option selected="selected">none</option>
							     	 	<#else>
							     	 		<option>none</option>	
							     	 	</#if>
							     	 	
							     	 	<#if viewType=="greyed">
							     	 		<option selected="selected">greyed</option>
							     	 	<#else>
							     	 		<option>greyed</option>	
							     	 	</#if>
							     	 		
							     	 	<#if viewType=="pruned">
							     	 		<option selected="selected">pruned</option>
							     	 	<#else>
							     	 		<option>pruned</option>	
							     	 	</#if>
							     	 	
							     	 	<#if viewType=="collapsed">
							     	 		<option selected="selected">collapsed</option>
							     	 	<#else>
							     	 		<option>collapsed</option>	
							     	 	</#if>
							     	 	
							     	 	
							     	 	
							     	 	
							      	</select>
							      </td>
							    </tr>
							    
							    <#if workflowExistence=="true">
							    	<tr>
							    		<td align="left" ><label for="workflow_name">Workflow Name: </label></td>
							    		<td align="left" >
							    				<b>${workflowName}</b> 
							    		</td>
							    			
							    	</tr>
							    	
							    	
								    <tr>
							    		<td align="left" ><label for="task_name">Task Name: </label></td>
							    		<td align="left" >
							    				<b>${taskName}</b> 
							    		</td>
							    			
							    	</tr>
							    
							    
								    <tr>
							    		<td align="left" ><label for="user_name">User Name: </label></td>
							    		<td align="left" >
							    				<b>${userName}</b> 
							    		</td>
							    			
							    	</tr>
							    
							    
							    
							    </#if>
 							    			
							    <tr>
							     
							      <td align="left">
							     		
										<input class="standardHighlight1" type="button"  onclick="rebuildFeatureModel()" value="Load View">

							      	
							      </td>
							      
							    
							    
							    </tr>
							 
							 
							    
						  	 </table>
						  	 
							  									
					 	</td></tr>
							
											
						</table>
					
				</div>
				<div class="post"> 
					<h1 class="title"><a href="#">${modelName} (${countFeatures} features)</a></h1>
					<div class="entry">
						<p><table width=820 border=0>
							<tr>
							<td align=left valign=top>
								<div id="fm" disabled="true">
								</div>
							</td>
							<td align=right valign=top>
							<!--	
								<span>
									<p><b>Legend</b>:
									<br><img src="/SPLOT/images/checkmark.gif">select feature&nbsp;&nbsp;&nbsp;
									<br><img src="/SPLOT/images/crossmark.gif">deselect feature&nbsp;&nbsp;&nbsp;
									<br><img src="/SPLOT/images/toggle.gif">toggle feature&nbsp;&nbsp;&nbsp;
									<br><img src="/SPLOT/images/manual.gif">manual decision&nbsp;&nbsp;&nbsp;
									<br><img src="/SPLOT/images/propagated.gif">propagated decision&nbsp;&nbsp;&nbsp;
									<br><img src="/SPLOT/images/auto-completion.gif">auto-completion decision&nbsp;&nbsp;&nbsp;
									</p>
								</span>
							-->
								<#assign numColumns = 6>								
								<table class="standardTableStyle" id="confStepsTable">
									<tr><td colspan='${numColumns}' class="standardHighlight1">
										<b>Configuration Steps</b> [<a title="Reload feature model and restarts configuration" href="javascript:resetConfiguration()">reset</a>]
									</td></tr>
									<tr><td colspan="${numColumns}">
									<div id="confProgressBar" dojoType="dijit.ProgressBar" style="width:100%" maximum="100"></div>
									</td></tr>
									<tr>
										<th>Step</th>
										<th>Decision</th>
										<th>#Decisions<br><small>(cummulative)</small></th>
										<th>#Propagations<br><small>(at step)</small></th>
										<th>#SAT checks<br><small>(at step)</small></th>
										<th>SAT time<br><small>(at step)</small></th>
									</tr>
									<#list steps as step>
										${step.configurationStepElement}
									</#list>
									
								
									
									<tr><td colspan=${numColumns}>
										<span id="last_step_row">
											<span style="display:<#if done>block<#else>none</#if>;" id="configuration-done-element">
												<span class="standardHighlight1">Done!</span>
												(Export configuration: 
												
													<#if workflowExistence=="true">
														<a target="_new" href="javascript:exportToCSVFile()">CSV file</a> |  
														<a target="_new" href="javascript:exportToXMLFile()">XML</a>)
													<#else>
														<a target="_new" href="/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=export_configuration_csv">CSV file</a> |  
														<a target="_new" href="/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=export_configuration_xml">XML</a>)
												 </#if>
												
												<br>
												 
											</span>
											<span style="display:<#if done>none<#else>block</#if>;" id="auto-completion-element">
												<img title="Automatically completes configuration" src="/SPLOT/images/auto-completion.gif"/>
												Auto-completion: 
												<a title="Attempts to DESELECT all remaining features" href="javascript:updateConfigurationElements('completion','precedence','false')">Less Features</a> | 
												<a title="Attempts to SELECT all remaining features" href="javascript:updateConfigurationElements('completion','precedence','true')">More Features</a> 
												<br>
												
											</span>
										</span>
									</td></tr>
								</table>	
							</td>
							</tr>
							 <#if workflowExistence=="true">
							 <tr></tr>
							<tr>
							
								<td>
										<button  class="standardHighlight1"  onClick="saveConfigurationToRepository();return false;" type="button">Save Configuration</button>
										<button  class="standardHighlight1"  onClick="reloadFeatureModel();return false;" type="button">Reload Configuration</button>
										<button  class="standardHighlight1"  onClick="exitWindow();return false;" type="button">Exit Configuration</button>
										
										
								</td>
								
							</tr>
							<tr>
								<td>
										
									
								</td>
							
							</tr>
							
							</#if>
						</table></p>
					</div> <!-- entry -->
				</div> <!-- post -->
				
				
			</#if> <!-- error check --> 
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