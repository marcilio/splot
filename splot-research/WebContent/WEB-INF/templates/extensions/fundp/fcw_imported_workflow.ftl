<#if hasError>
	<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
	<p><a href="javascript:history.back()">Back</a></p>						
<#else>


	<link type="text/css" rel="stylesheet" href="splot.css"/>

	<script type="text/javascript" src="js/ajax.js"></script> 
	<script type="text/javascript" src="js/ajax-dynamic-content.js"></script>

	<script type="text/javascript"> 
	<!--
function sortModels_imported(sortBy) 
	{
		ajax_loadContent('_model_repository_imported','/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=show_imported_workflow&enableSelection=${enableSelection?string}&selectionMode=${selectionMode}&serviceURL=${serviceURL}&serviceHTTPMethod=${serviceHTTPMethod}&serviceAction=${serviceAction}&sortby='+sortBy);
	} 
	
	
	
function parseWorkflows() 
{

workflowName="";
	try{
			var table = document.getElementById("uploaded_workflow");
			if (table!=null){
				var rowCount = table.rows.length;
				for(var i=0; i<rowCount; i++) {
					var row = table.rows[i];
					var chkbox = row.cells[1].childNodes[1];
					if((null != chkbox) && (true == chkbox.checked)) {
						workflowName=row.cells[1].childNodes[1].title;
					}
				}
					
			}


	if(workflowName!=""){
		
		if (window.ActiveXObject){
			oXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}else{
			oXMLRequest = new XMLHttpRequest();
		}
		
		var strValidationServiceUrl = "/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=parse_selected_workflow&workflow="+workflowName ;		
		oXMLRequest.open("GET",strValidationServiceUrl,true);
		oXMLRequest.onreadystatechange =onParseWorkflows;
		oXMLRequest.send(null);
	}
	
	}catch(error){
		alert(error);
	}

}
	
function onParseWorkflows(){
				if (oXMLRequest.readyState == 4){
					if (oXMLRequest.status == 200){
						strStatus = oXMLRequest.responseText;
						
						try{
							if (strStatus=="true"){
								pageOnLoadActions(); 		

							}else{
								alert(strStatus);
							}
						
						}catch(Error){
						}
					}
				}	
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



	
	
	-->
	</script>
	<div id="_model_repository_imported">
		<#compress>
		<#if enableSelection>
			<#if selectionMode="multiple">
			  <form action="${serviceURL}" method="${serviceHTTPMethod}">
			  <input type="hidden" name="action" value="${serviceAction}">
			  <input type="hidden" name="dirType" value="${dirType}">
			  <b>Please, select models from the table below and</b> 
			  <input class="standardHighlight1" type="submit" value="Click Here"/>
		<#elseif selectionMode="single">
			  <form action="${serviceURL}" method="${serviceHTTPMethod}">
			  <input type="hidden" name="action" value="${serviceAction}">
			  <input type="hidden" name="dirType" value="${dirType}">
			  <input type="hidden" name="op" value="reset">
			  
			 
			</#if>
		</#if>
		</#compress>
	
		<table>
		<tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('uploaded_workflow');">Uploaded Workflows </span></td></tr>

		<tr><td>	
		<table class="standardTableStyle" id="uploaded_workflow"  name="uploaded_workflow">
		<tr>
			<th>#</th>
			<th><#if sortBy!='name'><a href="javascript:sortModels_imported('name');">Name</a><#else><span class="standardHighlight1">Name</a></#if></TH>
			<th><#if sortBy!='creator'><a href="javascript:sortModels_imported('creator');">Creator</a><#else><span class="standardHighlight1">Creator</a></#if></TH>
			<th><#if sortBy!='version'><a href="javascript:sortModels_imported('version');">Version</a><#else><span class="standardHighlight1">Version</a></#if></TH>
			
			
		</tr>
		<#list models as model>
		<tr onmouseover="this.className='highlightFMRepositoryEntry';" onmouseout="this.className='standardTableStyle';">
			<td>${model_index+1}.</td>
			<TD>
			<#if enableSelection><input class="standardHighlight1" <#if selectionMode="multiple"> type="checkbox"  <#elseif selectionMode="single"> type="radio" </#if> name="selectedModels" value="${model.file}"  title="${model.name}"/></#if>${model.name}
			</td>
		
			<TD>${model.creator}</td>
			<TD>${model.version}</td>
			
	
		</tr>	
		</#list>
		</table>
		
		<table>
			<tr>
				<td>
				<input type="button" class="standardHighlight1" onclick="parseWorkflows();return false;" value="Import" >
				 <input class="standardHighlight1" type="submit" value="View Workflow">
				</td>
			</tr>
			<tr>
				<td>
				
				</td>
			</tr>
		</table>
		</td></tr>	
		</table>
		<#if enableSelection>
			</form>
		</#if>
	</div>
</#if>