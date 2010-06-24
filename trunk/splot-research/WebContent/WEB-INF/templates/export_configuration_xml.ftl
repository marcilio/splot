<!-- 
 This file contains details about the configuration of model ${modelName} and includes selected/deselected features, 
 configuration steps, and the type of decisions made (e.g. manual, automatic, auto-completion). 
 -->
<configuration model="${modelName}">
<#list features as feature>
	<feature id="${feature.id}">
		<name>${feature.name}</name>
		<type>${feature.type}</type>
		<value>${feature.value}</value>
		<decisionType>${feature.decisionType}</decisionType>
		<decisionStep>${feature.decisionStep}</decisionStep>
	</feature>
</#list>
</configuration>
