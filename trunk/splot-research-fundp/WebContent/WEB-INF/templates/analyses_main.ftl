
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


<script type="text/javascript"> 
<!--

/************************************************
* UPDATE ANALYSES ELEMENTS
*************************************************/

function updateData(action) {
<#if !hasError>
	<#list loadedModels as model>
		updateModelData(action, ${model.index});
	</#list>
</#if>
}

function updateModelData(action, modelIndex) {
        //The parameters to pass to xhrPost, the message, and the url to send it to
        //Also, how to handle the return and callbacks.
        var xhrArgs = {
            url: "/SPLOT/SplotAnalysesServlet?action=" + action + "&modelIndex=" + modelIndex,
            sync : true,
            handleAs: "xml",
            headers: { "Content-Type": "application/x-www-form-urlencoded; charset=utf-8" },
            load: function(response, ioArgs) {
				xmlDoc = response.documentElement;
				dataList = xmlDoc.getElementsByTagName("data");
				for( i = 0 ; i < dataList.length ; i++ ) {	    
					cellElement = document.getElementById("model" + modelIndex +"_" + dataList[i].getAttribute("name") );
					if ( cellElement != null ) {
						cellElement.innerHTML = dataList[i].getAttribute("value");
					}
				    if ( action == 'run_analyses' ) {
				    	if ( dataList[i].getAttribute('name') == 'deadfeature_result' ) { 
							if ( dataList[i].getAttribute("value") != 0 && dataList[i].getAttribute("value") != "N/A" ) {
								 viewObj = document.getElementById("model" + modelIndex +"_deadfeature_result_view" );
								 viewObj.style.display = '';
							}
						}
				    	else if ( dataList[i].getAttribute('name') == 'commonfeature_result' ) { 
							if ( dataList[i].getAttribute("value") != 0 && dataList[i].getAttribute("value") != "N/A" ) {
								 viewObj = document.getElementById("model" + modelIndex +"_commonfeature_result_view" );
								 viewObj.style.display = '';
							}
						}
					}
				} 
            },
            error: function(error) {
                alert('Oops, SPLOT behaved like a bad boy :) If the error persists contact the SPLOT team.');
            }
        }
        dojo.xhrGet(xhrArgs);
}


function renderFeatureModel(modelIndex,highlight) 
{
	window.open("/SPLOT/SplotAnalysesServlet?action=render_model&modelIndex="+modelIndex+"&highlight="+highlight, "model_"+modelIndex, "width=400,height=400,scrollbars=yes,toolbar=no,location=no,menubar=no" );
} 
-->
</script>

</head>
<body onload="javascript:updateData('run_statistics');">

<div id="header"><div id="logo"><img src="images/splot.jpg"></div></div> 


<!-- end #header --> 
<div id="menu"> 
	<ul> 
		<li><a href="index.html">Home</a></li>  
		<li><a href="feature_model_edition.html">Feature Model Editor</a></li> 
		<li class="first"><a href="automated_analyses.html">Automated Analysis</a></li> 
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
			<div class="post"> 
				<h1 class="title"><a href="#">Automated Analysis</a></h1> 
				<div class="entry"> 
					<#if hasError>
						<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
						<p><a href="javascript:history.back()">Back</a></p>						
					<#else>
						<p>
							Click the <b><i>"Click to Run"</i></b> links below to run feature model analysis based on SAT solvers and Binary Decision Diagrams.
						</p>
						<#assign colSpan = loadedModels?size + 1>
						<table class="standardTableStyle">
						<tr>
							<th>Data</th>
							<#list loadedModels as model>
								<th class="header">${model.name}<br>(<a href="" onclick="javascript:renderFeatureModel(${model.index},'normal');return false;">view</span>)</th>
							</#list>
						</tr><tr>
							<td colspan=${colSpan} class="standardHighlight1">Statistics</td>
						</tr><tr>
							<td>#Features</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelFeatures">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>&nbsp;&nbsp;- Optional</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelCountOptional">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>&nbsp;&nbsp;- Mandatory</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelCountMandatory">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>&nbsp;&nbsp;- Grouped</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelCountGrouped">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>&nbsp;&nbsp;- Groups</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelCountGroups">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>Tree Depth</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelTreeDepth">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>ECR (%)</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelEcr">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>#Extra constraints</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelECConstraints">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>#Distinct extra constraints variables</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelECVariables">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>Clause Density</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelClausedensity">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>#CNF Clauses</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_modelCountFMCNFClauses">&nbsp;</span></td>
							</#list>	
						</tr><tr>
						</tr><tr>
							<td colspan=${colSpan} class="standardHighlight1">Debugging Analyses (<a href="javascript:updateData('run_analyses')">Click to Run</a> the SAT solver)</td>
						</tr><tr>
							<td><b>Consistency</b></td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_consistency_result">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td>Running Time (ms)</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_consistency_running_time">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td><b>#Dead Features</b></td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_deadfeature_result">&nbsp;</span><br><a id="model${model.index}_deadfeature_result_view" style="display:none" href="" onclick="javascript:renderFeatureModel(${model.index},'dead');return false;">view</a></td>
							</#list>	
						</tr><tr>
							<td>&nbsp;&nbsp;- Running Time (ms)</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_deadfeature_running_time">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td><b>#Common Features</b></td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_commonfeature_result">&nbsp;</span><br><a id="model${model.index}_commonfeature_result_view" style="display:none" href="" onclick="javascript:renderFeatureModel(${model.index},'common');return false;">view</a></td>
							</#list>	
						</tr><tr>
							<td>&nbsp;&nbsp;- Running Time (ms)</td>
							<#list loadedModels as model>
								<td><span id="model${model.index}_commonfeature_running_time">&nbsp;</span></td>
							</#list>	
						</tr><tr>
							<td colspan=${colSpan} class="standardHighlight1">Metrics (<a href="javascript:updateData('run_metrics')">Click to run</a> the BDD engine)</td>
						</tr><tr>
							<td><b>Count Configurations</b></td>
							<#list loadedModels as model>
								<#if (model.size > maxModelSizeForBDD)>
									<td class="disabledRow"><span title="Model is too large to be processed (>${maxModelSizeForBDD} features)">Too large</span></td>
								<#else>
									<td><span id="model${model.index}_countconf_result">&nbsp;</span></td>
								</#if>
							</#list>	
						</tr><tr>
							<td>&nbsp;&nbsp;- Running Time (ms)</td>
							<#list loadedModels as model>
								<#if (model.size > maxModelSizeForBDD)>
									<td class="disabledRow"><span title="Model is too large to be processed (>${maxModelSizeForBDD} features)">Too large</span></td>
								<#else>
									<td><span id="model${model.index}_countconf_running_time">&nbsp;</span></td>
								</#if>
							</#list>	
						</tr><tr>
							<td><b><span title="Number of valid configurations divided by 2^n, where n is the number of features in the model">Variability Degree (%)</span></b></td>
							<#list loadedModels as model>
								<#if (model.size > maxModelSizeForBDD)>
									<td class="disabledRow"><span title="Model is too large to be processed (>${maxModelSizeForBDD} features)">Too large</span></td>
								<#else>
									<td><span id="model${model.index}_vardegree_result">&nbsp;</span></td>
								</#if>
							</#list>	
						</tr><tr>
							<td>&nbsp;&nbsp;- Running Time (ms)</td>
							<#list loadedModels as model>
								<#if (model.size > maxModelSizeForBDD)>
									<td class="disabledRow"><span title="Model is too large to be processed (>${maxModelSizeForBDD} features)">Too large</span></td>
								<#else>
									<td><span id="model${model.index}_vardegree_running_time">&nbsp;</span></td>
								</#if>
							</#list>	
						</tr><tr>
							<td>#BDD Nodes</td>
							<#list loadedModels as model>
								<#if (model.size > maxModelSizeForBDD)>
									<td class="disabledRow"><span title="Model is too large to be processed (>${maxModelSizeForBDD} features)">Too large</span></td>
								<#else>
									<td><span id="model${model.index}_bddnodes">&nbsp;</span></td>
								</#if>
							</#list>	
						</tr><tr>
							<td>#BDD Variable Order Heuristic</td>
							<#list loadedModels as model>
								<#if (model.size > maxModelSizeForBDD)>
									<td class="disabledRow"><span title="Model is too large to be processed (>${maxModelSizeForBDD} features)">Too large</span></td>
								<#else>
									<td><span id="model${model.index}_bddheuristic">&nbsp;</span></td>
								</#if>
							</#list>	
						</tr>
						</table>
					</#if>
				</div> 
			</div> 
		</div> 
		<!-- end #content --> 
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
