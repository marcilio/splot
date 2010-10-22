<#if hasError>
	<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
	<p><a href="javascript:history.back()">Back</a></p>						
<#else>


	<link type="text/css" rel="stylesheet" href="splot.css"/>

	<script type="text/javascript" src="js/ajax.js"></script> 
	<script type="text/javascript" src="js/ajax-dynamic-content.js"></script>
	
	<script type="text/javascript"> 
	<!--
	 function sortModels(sortBy) 
	{
		ajax_loadContent('_model_repository','/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=select_workflow&enableSelection=${enableSelection?string}&selectionMode=${selectionMode}&serviceURL=${serviceURL}&serviceHTTPMethod=${serviceHTTPMethod}&serviceAction=${serviceAction}&sortby='+sortBy);
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
	<div id="_model_repository">
		<#compress>
		<#if enableSelection>
			<#if selectionMode="multiple">
			  <form id="selectWorkflowForm" action="${serviceURL}" method="${serviceHTTPMethod}">
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
		<tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('existing_workflow');">Available Workflows </span></td></tr>

		<tr><td>	
		<table class="standardTableStyle" id="existing_workflow"  name="existing_workflow">

		<tr>
			<th>#</th>
			<th><#if sortBy!='name'><a href="javascript:sortModels('name');">Name</a><#else><span class="standardHighlight1">Name</a></#if></TH>
			<th><#if sortBy!='creator'><a href="javascript:sortModels('creator');">Creator</a><#else><span class="standardHighlight1">Creator</a></#if></TH>
			<th><#if sortBy!='version'><a href="javascript:sortModels('version');">Version</a><#else><span class="standardHighlight1">Version</a></#if></TH>
			
			
		</tr>
		<#list models as model>
		<tr onmouseover="this.className='highlightFMRepositoryEntry';" onmouseout="this.className='standardTableStyle';">
			<td>${model_index+1}.</td>
			<TD>
			<#if enableSelection><input class="standardHighlight1" <#if selectionMode="multiple"> type="checkbox"  <#elseif selectionMode="single"> type="radio" </#if> name="selectedModels" value="${model.file}"/></#if>${model.name}
			</td>
		
			<TD>${model.creator}</td>
			<TD>${model.version}</td>
			
	
		</tr>	
		</#list>
		</table>
		</td></tr>
		</table>
		 <input class="standardHighlight1" type="submit" value="View Workflow">
		<#if enableSelection>
			</form>
		</#if>
	</div>
</#if>