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
<script type="text/javascript" src="js/splot_scrollable_table.js"></script>

<#if !hasError>

<style type="text/css">
  .hintBox {
    width: 880px;
    margin-top: 0px;
    color: #292929;
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
		triggerNotificationDialog('wait', 'SPLOT Product Configurator', 
			'SPLOT is now loading the feature model and product catalog...<br>' +
			'This might take up to a few minutes on Microsoft Internet Explorer :-('
		);
		setTimeout('loadFeatureModel()', 300);
	});
	
	function loadFeatureModel() {
		filterProductsBasedOnFeatureModelSelection();
		dijit.byId("confProgressBar").update({progress: ${countInstantiatedFeatures}, maximum: ${countFeatures} });
		buildFeatureModel();
		setHintVisible(true);
		closeNotificationDialog();		
	}
	
	/******************************************************
	*  Show/Hide Hint (animation)
	*******************************************************/
	function setHintVisible(show) {
		if ( show ) {
			document.getElementById("animHintPost").style.display = "block";
		}
		else {
			//document.getElementById("animHintPost").style.display = "none";
			dojo.fx.wipeOut({node: "animHintPost",duration: 1000}).play();
		}
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
	*  Update catalog of products based on feature model current selection
	*******************************************************/
	function filterProductsBasedOnFeatureModelSelection() {
        var xhrArgs = {
            url: "/SPLOT/SplotConfigurationServlet?action=catalog_interactive_configuration_filter_products",
            sync : false,
            handleAs: "text",
            load: function(response, ioArgs) {
                dojo.byId('filteredProductsList').innerHTML = response;                
            },
            error: function(error) {
                closeNotificationDialog();                
                alert('Oops, SPLOT behaved like a bad boy :) If the error persists contact the SPLOT team.');
            }
        }
        dojo.xhrGet(xhrArgs);        
	}
    


	/******************************************************
	*  Detect conflicts when feature is toggled
	*******************************************************/
	function detectToggleConflicts(toggleFeatureId) {
		// TODO
	}
    
	/******************************************************
	*  Reset configuration
	*******************************************************/
	function resetConfiguration() {
	   window.location = "/SPLOT/SplotConfigurationServlet?action=catalog_interactive_configuration_main&op=reset";
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
		var ajaxObj = new sack("/SPLOT/SplotConfigurationServlet");
		ajaxObj.method = "GET";
		
		ajaxObj.onCompletion = function() 
		{
			// Show product catalog
	     	filterProductsBasedOnFeatureModelSelection();

			xmlDoc = ajaxObj.responseXML.documentElement;
			
			// Update Feature Model and list of features included in the current configuration
			features = xmlDoc.getElementsByTagName("feature");
			for( i = 0 ; i < features.length ; i++ ) {
				featureDivElement = features[i].getAttribute("id") + "_main";
				featureDivContent = features[i].childNodes[0].nodeValue;
				featureDivContent = featureDivContent.replace(/(\r\n|[\r\n])/g,'');
				document.getElementById(featureDivElement).innerHTML = featureDivContent;
			}
			
			
			countFeatures = xmlDoc.getElementsByTagName("countFeatures")[0].getAttribute("value");
			countInstantiatedFeatures = xmlDoc.getElementsByTagName("countInstantiatedFeatures")[0].getAttribute("value");
			
			if( (typeof countFeatures != 'undefined') && (typeof countInstantiatedFeatures != 'undefined') ) {
				//progressBarValue = Math.floor(countInstantiatedFeatures/countFeatures);
				dijit.byId("confProgressBar").update({progress: countInstantiatedFeatures, maximum: countFeatures });				
			}
	        
			stepsJSON = xmlDoc.getElementsByTagName("step_updates")[0].childNodes[0].nodeValue;
			
			//alert(stepsJSON);
			
			// Parameters for Steps Table
			configurationSteps = eval( '(' + stepsJSON + ')' ); 					// steps table
			showVisibleSteps();  // @splot_scrollable_table.js
			
            closeNotificationDialog();
	    }
	  
		parameters = '';
		if (typeof parameter != 'undefined' && typeof value != 'undefined' ) {
			parameters = '&' + parameter + '=' + value; 
		}
			 
		triggerNotificationDialog('wait', 'SPLOT Product Configurator', 'Processing ...');
	  	ajaxObj.runAJAX("action=catalog_interactive_configuration_updates&op=" + operation + parameters);
	  	
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

<!-- dialog for conflict resolution -->
<div dojoType="dijit.Dialog"
	 style="display:none" 
	 id="conflictingDecisionsDialog" 
	 title="Configuration Conflict Resolution"
	 execute="toggleFeature(arguments[0])">	 
	 <div id="conflictingDecisionsDialogContent"></div>
</div>

<div id="header"><div id="logo"><img src="images/splot.jpg"></div></div> 


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
					<h1 class="title"><a title="Click to hide this Hint" onClick="javascript:setHintVisible(false)" href="#">Hint</a></h1>
					<div  class="entry">
<!--
						<p>Please wait while the feature model and the product catalog are loaded below. 
						This might take up to a few minutes depending on the amount of information being loaded, your browser, 
						and the speed of your internet connection.</p>
						<p>
-->						
						<p>
						Select or deselect features in the <b>feature model</b> (left-hand side) to filter the products in the <b>catalog</b> 
						(right-hand side) . Please, note that even though the catalog refers to real 
						products we do not claim that the information presented is complete and/or up-to-date with the manufacters's catalog.
						In fact, the primarily purpose of this website is to showcase research on state-of-the-art feature-based 
						configuration techniques.
						</p>        
						<p><a onClick="javascript:setHintVisible(false)" href="#">Hide this hint</a></p> 					
						<p class="meta"></p>
					</div>
				</div>
				<div class="post"> 
					<h1 class="title"><a href="#">${modelName} (${countFeatures} features)</a></h1>
					<div class="entry">
					
					<div class="orangeBackgroundStyle">Configuration Steps Table (<a href="javascript:resetConfiguration();">reset</a>)</div>
					
					<div style="width:820px;overflow:scroll;border: 1px solid;">
					<table id="confStepsTable" class="standardTableStyle">
						<tr>
							<td style="font-weight: bold; background-color: rgb(255,243,211)">#Step</td>
							<#list steps as step>
								<td><span style="text-align: center; font-size: 14px; font-weight: bold;">#${step.step_id}</span></td>
							</#list>
						</tr>
						<tr>
							<td style="font-weight: bold; background-color: rgb(255,243,211)">Decision</td>
							<#list steps as step>
							<td>
								<#compress>
									<#list step.step_manualDecisions as decision>
										  <#if decision.featureId != "auto-completion">
											<#if decision.featureValue == 1>
												<img src="images/checkmark.gif">
											<#else>	
											    <img src="images/crossmark.gif">
											</#if>
										  <#else>
										        <img src="images/auto-completion.gif">
										  </#if >
										${decision.featureName}
										<#if (step.step_manualDecisions?size > 1)><br></#if>		
									</#list>
								</#compress>
							</td>
							</#list>
						</tr>
						<tr>
							<td style="font-weight: bold; background-color: rgb(255,243,211)">Total Decisions</td>
						 	<td>
						 	<#list steps as step>
						 		${step.step_countCummulativeDecisions} <small><b>(${step.step_percentageCummulativeDecisions}%)</b></small></
						 	</#list>
						 	</td>
						</tr>
					</table>
					</div>
					
					<div id="confProgressBar" dojoType="dijit.ProgressBar" style="width:820px" maximum="100"></div>
					
					<table style="width:820px; border: 1px solid; border-color: #FF7900; padding: 4; border-spacing: 0px">
						<tr>
						<td align=left valign=top style="width:410px">
							<div style="font-size: 15px; font-weight: bold; padding: 10px">FEATURE MODEL</div>
							<div id="fm">
							</div>
						</td>
						<td align=right valign=top style="width:410px">
							<div id="filteredProductsList" name="filteredProductsList"></div>
						</td>
						</tr>
					</table>

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