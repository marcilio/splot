<#if hasError>
	<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
	<p><a href="javascript:history.back()">Back</a></p>						
<#else>
	<script type="text/javascript"> 
	<!--
	 function sortModels(sortBy) 
	{
		ajax_loadContent('_model_repository','/SPLOT/SplotAnalysesServlet?action=select_model&enableSelection=${enableSelection?string}&selectionMode=${selectionMode}&serviceURL=${serviceURL}&serviceHTTPMethod=${serviceHTTPMethod}&serviceAction=${serviceAction}&sortby='+sortBy);
	} 
	-->
	</script>
	<div id="_model_repository">
		<#compress>
		<#if enableSelection>
			<#if selectionMode="multiple">
			  <form action="${serviceURL}" method="${serviceHTTPMethod}">
			  <input type="hidden" name="action" value="${serviceAction}">
			  <b>Please, type the URL of your own feature models AND/OR select models from the table below and</b> 
			  <input class="standardHighlight1" type="submit" value="Click Here"/>
			  <p style="margin: 10px 0px 0px 0px">Your model's URLs (in the <a href="sxfm.html">SXFM format</a>) starting with "http://" (one per line):</p>
			  <p style="margin: 0px 0px 10px 0px"><textarea name="userModels" rows=3 cols="80"></textarea></p>
			<#elseif selectionMode="single">
			  <form action="${serviceURL}" method="${serviceHTTPMethod}">
			  <input type="hidden" name="action" value="${serviceAction}">
			  <input type="hidden" name="op" value="reset">
			  <b>Type the URL of your own feature model OR select a model from the table below and</b>
			  <input class="standardHighlight1" type="submit" value="Click Here">
			  <p style="margin: 10px 0px 0px 0px">Your model's URL (in the <a href="sxfm.html">SXFM format</a>) starting with "http://":</p>
			  <p style="margin: 0px 0px 10px 0px"><input name="userModels" type="text" size="80"></p>
			</#if>
			<p>If you wish to check for parsing errors in your feature model <a href="parse_feature_model_for_errors.html">click here</a>.</p>
		</#if>
		</#compress>
		
		<table class="standardTableStyle">
		<tr>
			<th>#</th>
			<th><#if sortBy!='name'><a href="javascript:sortModels('name');">Name</a><#else><span class="standardHighlight1">Name</a></#if></TH>
			<th><#if sortBy!='features'><a href="javascript:sortModels('features');">Features</a><#else><span class="standardHighlight1">Features</a></#if></TH>
			<th><#if sortBy!='ecr'><a href="javascript:sortModels('ecr');">CTCR</a><#else><span class="standardHighlight1">CTCR</a></#if></TH>
			<th>Clause Density</TH>
			<th><#if sortBy!='creator'><a href="javascript:sortModels('creator');">Creator</a><#else><span class="standardHighlight1">Creator</a></#if></TH>
			<th>Creation Date</th>
			<#if showModelDetails>
				<th>Details and Download</th>
			</#if>
		</tr>
		<#list models as model>
		<tr onmouseover="this.className='highlightFMRepositoryEntry';" onmouseout="this.className='standardTableStyle';">
			<td>${model_index+1}.</td>
			<TD>
			<#if enableSelection><input class="standardHighlight1" <#if selectionMode="multiple"> type="checkbox"  <#elseif selectionMode="single"> type="radio" </#if> name="selectedModels" value="${model.file}"/></#if>${model.name}
			</td>
			<TD>${model.features}</td>
			<TD>${model.ecr}%</td>
			<TD>${model.clausedensity}</td>
			<TD>${model.creator}&nbsp;</td>
			<TD>${model.date}&nbsp;</td>
			<#if showModelDetails>
			<TD align="center"><a href="/SPLOT/SplotAnalysesServlet?action=show_model_details&modelFile=${model.file}">Click</a></td>
			</#if>
		</tr>	
		</#list>
		</table>
		<#if enableSelection>
			</form>
		</#if>
	</div>
</#if>