<#compress>
<#if hasError>
	Problems detecting conflicts. Please, <b>reset</b> configuration.
<#else>
	<p>
	<#if conflicting_features?size == 0>
		Decision <#if toggleFeatureNewValue==1><img src="images/checkmark.gif"><#else><img src="images/crossmark.gif"></#if><b><span title="Feature Id: ${toggleFeatureId}">${toggleFeatureName}</span></b> 
		does <b>not</b> conflict with any other decision
	<#else>
		Decision <#if toggleFeatureNewValue==1><img src="images/checkmark.gif"><#else><img src="images/crossmark.gif"></#if><b><span title="Feature Id: ${toggleFeatureId}">${toggleFeatureName}</span></b> conflicts with:
		<ul>
		<#list conflicting_features as feature>
			<li><#if feature.feature_decision == "1"><img src="images/checkmark.gif"><#else><img src="images/crossmark.gif"></#if><span title="Feature Id: ${feature.feature_id}">${feature.feature_name}</span> (<b>step ${feature.feature_decisionStep}</b>)</li>
		</#list>
		</ul>
	</#if>
	</p>	
	<p>Do you confirm this decision?</p>
	<p><button dojoType="dijit.form.Button" type="submit">Yes, go ahead</button>
	<button dojoType="dijit.form.Button" onClick="hideDialog()">No, cancel</button></p>
	</p>
</#if>
</#compress>