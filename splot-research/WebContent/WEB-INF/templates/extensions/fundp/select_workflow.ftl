<#if hasError>
	<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
	<p><a href="javascript:history.back()">Back</a></p>						
<#else>
	<script type="text/javascript"> 
	<!--
	 function sortModels(sortBy) 
	{
		ajax_loadContent('_model_repository','/SPLOT/MultiplePerspectiveConfigurationViewsServlet?action=select_workflow&enableSelection=${enableSelection?string}&selectionMode=${selectionMode}&serviceURL=${serviceURL}&serviceHTTPMethod=${serviceHTTPMethod}&serviceAction=${serviceAction}&sortby='+sortBy);
	} 
	-->
	</script>
	<div id="_model_repository">
		<#compress>
		<#if enableSelection>
			<#if selectionMode="multiple">
			  <form action="${serviceURL}" method="${serviceHTTPMethod}">
			  <input type="hidden" name="action" value="${serviceAction}">
			  <b>Please, select models from the table below and</b> 
			  <input class="standardHighlight1" type="submit" value="Click Here"/>
		<#elseif selectionMode="single">
			  <form action="${serviceURL}" method="${serviceHTTPMethod}">
			  <input type="hidden" name="action" value="${serviceAction}">
			  <input type="hidden" name="op" value="reset">
			  <b>Select a model from the table below and</b>
			  <input class="standardHighlight1" type="submit" value="Click Here">
			</#if>
		</#if>
		</#compress>
		
		<table class="standardTableStyle">
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
		<#if enableSelection>
			</form>
		</#if>
	</div>
</#if>