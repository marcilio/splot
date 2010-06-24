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
	
</script>

<script type="text/javascript"> 
<!--
	/******************************************************
	*  Detect conflicts when feature is toggled
	*******************************************************/
	function detectToggleConflicts(toggleFeatureId) {
		setToggleFeature(toggleFeatureId)
		ajaxObj = new sack("/SPLOT/SplotConfigurationServlet");
	  	ajaxObj.method = "GET";
	  	ajaxObj.onCompletion = function() 
	  	{
	  		dojo.byId('conflictingDecisionsDialogContent').innerHTML = ajaxObj.response;
	  		dojo.parser.parse();  // this is required for dojo to recognize dialog buttons
	  		dijit.byId('conflictingDecisionsDialog').show();
	  	}
	  	ajaxObj.runAJAX("action=detect_conflicts" + "&toggleFeature=" + toggleFeatureId);  		
	}
    
	/******************************************************
	*  Reset configuration
	*******************************************************/
	function resetConfiguration() {
	   window.location = "/SPLOT/SplotConfigurationServlet?action=interactive_configuration_main&op=reset";
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
	} 

	/******************************************************
	*  Update configuration elements on page
	*******************************************************/
	function updateConfigurationElements(operation, parameter, value) {
	
		parameters = '';
		if (typeof parameter != 'undefined' && typeof value != 'undefined' ) {
			parameters = '&' + parameter + '=' + value; 
		}
	
	    var xhrArgs = {
            url: "/SPLOT/SplotConfigurationServlet?action=interactive_configuration_updates&op=" + operation + parameters,
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
		dijit.byId('notificationDialog').attr('title', title);
		dijit.byId('notificationDialog').show();
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
				<div id="animHintPost" class="hintBox">
					<h1 class="title"><a title="Click to hide this Hint" onClick="javascript:hideHint()" href="#">Hint</a></h1>
					<div  class="entry">
						<p>In order to simplify the configuration process you can focus on <i>selecting</i> the features that you want and use the
						<i>auto-completion</i> function (<b><i>Less Features</i></b> option) to <i>deselect</i> remaining features 
						automatically. Alternatively, you can use the opposite strategy, i.e., focus on deselecting features and use auto-completion 
						to select remaining features by choosing the <b><i>More Features</i></b> option.<p>
						<p><a onClick="javascript:hideHint()" href="#">Hide this hint</a></p> 					
						<p class="meta"></p>
					</div>
				</div>
				<div class="post"> 
					<h1 class="title"><a href="#">${modelName} (${countFeatures} features)</a></h1>
					<div class="entry">
						<p><table width=820 border=0>
							<tr>
							<td align=left valign=top>
								<div id="fm">
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
												<a target="_new" href="/SPLOT/SplotConfigurationServlet?action=export_configuration_csv">CSV file</a> |  
												<a target="_new" href="/SPLOT/SplotConfigurationServlet?action=export_configuration_xml">XML</a>)
											</span>
											<span style="display:<#if done>none<#else>block</#if>;" id="auto-completion-element">
												<img title="Automatically completes configuration" src="/SPLOT/images/auto-completion.gif"/>
												Auto-completion: 
												<a title="Attempts to DESELECT all remaining features" href="javascript:updateConfigurationElements('completion','precedence','false')">Less Features</a> | 
												<a title="Attempts to SELECT all remaining features" href="javascript:updateConfigurationElements('completion','precedence','true')">More Features</a> 
											</span>
										</span>
									</td></tr>
								</table>	
							</td>
							</tr>
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