
<#-- Generate Output: Step Index -->
<#function genStepId id>
	<#assign output = '#<span style=\'font-size: 14px; font-weight: bold;\'>' + id + '</span>'>
	<#if id != '1'>
		<#assign output = output + ' <img onMouseOver="this.src=\'/SPLOT/images/undoh.gif\'" onMouseOut="this.src=\'/SPLOT/images/undo.gif\'" onclick="javascript:updateConfigurationElements(\'undo\',\'step\',\'' + id + '\')" title="Undo all decisions and backtracks to previous step" src="/SPLOT/images/undo.gif">'>
	</#if>
	<#return output?js_string?xml>
</#function> 

<#-- Generate Output: Manual Decisions -->
<#function genManualDecisions step>
	<#assign output = ''>
	<#list step.step_manualDecisions as decision>
		<#if decision.featureId != "auto-completion">
			<#if decision.featureValue == 1> 
				<#assign output = output + '<img src="/SPLOT/images/checkmark.gif">'> 
			<#else>	
				<#assign output = output + '<img src="/SPLOT/images/crossmark.gif">'>
			</#if> 
		<#else>
		 	<#assign output = output + '<img src="/SPLOT/images/auto-completion.gif">'>
		</#if> 
		<#assign output = output + ' ' + decision.featureName>
		<#if decision_has_next>
			<#assign output = output + '<br>'>
		</#if>
	</#list>
	<#return output?js_string?xml>	
</#function>		
		
<#function genStats step>
	<#assign output = step.step_countCummulativeDecisions?string + ' <small><b>(' + step.step_percentageCummulativeDecisions + '%)</b></small>'>
	<#return output?js_string?xml>	
</#function>
		
<updates>

<#-- UPDATED CONFIGURATION STEPS TABLE -->
<step_updates>
[ ['#Step','Decision','Total Decisions'], 
<#list steps as step> 
   [ '${genStepId(step.step_id)}', '${genManualDecisions(step)}', '${genStats(step)}'] <#if step_has_next>,</#if> 
</#list> ]
</step_updates>

<#-- UPDATED FEATURES --> 
<#assign countSelectedFeatures=0>
<#list features as feature>
	<feature id="${feature.feature_id}" value="${feature.feature_decision}">
		${feature.configurationFeatureElement?xml}
		<#if feature.feature_decision != "-1">
			<#assign countSelectedFeatures = countSelectedFeatures+1>
		</#if>
	</feature>
</#list>

<#-- GENERAL PARAMETERS -->
<done value="${done?string}"/>
<#if countFeatures??>
	<countFeatures value="${countFeatures}"/>
</#if>
<#if countInstantiatedFeatures??>
	<countInstantiatedFeatures value="${countInstantiatedFeatures}"/>
</#if>


</updates>
