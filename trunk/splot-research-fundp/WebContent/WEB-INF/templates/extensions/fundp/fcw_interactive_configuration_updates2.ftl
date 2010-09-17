<updates>
<#compress>
<#-- UPDATED CONFIGURATION STEP -->
<#if steps??>
	<#list steps as step>
	 	<step id="${step.step_id}">
			${step.configurationStepElement?xml}
	 	</step>
	</#list>
</#if>

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
<op value="${op}"/>
<done value="${done?string}"/>
<#if countUndoSteps??>
	<countUndoSteps value="${countUndoSteps}"/>
</#if>
<#if countFeatures??>
	<countFeatures value="${countFeatures}"/>
</#if>
<#if countInstantiatedFeatures??>
	<countInstantiatedFeatures value="${countInstantiatedFeatures}"/>
</#if>
</#compress>
</updates>
