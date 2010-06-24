
<#-- DO *NOT* CHANGE THE <TD> ELEMENTS like adding any attributes, e.g., class="", id="" -->

<#compress>
<tr id="step${step_id}_row">
	<td><span style="text-align: center">${step_id}</span>
	<#if step_id != "1">
	   <img onMouseOver="this.src='/SPLOT/images/undoh.gif'" onMouseOut="this.src='/SPLOT/images/undo.gif'" onclick="javascript:updateConfigurationElements('undo','step',${step_id})" title="Backtracks all decisions up to the previous step" src="/SPLOT/images/undo.gif">
	</#if>
	</td>
	<td>
	<#compress>
	<#list step_manualDecisions as decision>
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
		<#if (step_manualDecisions?size > 1)><br></#if>		
	</#list>
	</#compress>
	</td>
	<td>${step_countCummulativeDecisions} <small><b>(${step_percentageCummulativeDecisions}%)</b></small></td>
	<td>${step_countPropagations}</td>
	<td>${step_Stat!-1}</td>
	<td>${step_runTime!-1} ms</td>
</tr>
</#compress>