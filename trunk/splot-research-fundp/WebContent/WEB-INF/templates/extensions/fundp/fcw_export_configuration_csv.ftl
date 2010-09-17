model=${modelName}
Feature ID, Feature Name, Feature Type, Feature Value, Feature Decision Type, Feature Decision Step
<#list features as feature>
${feature.id},${feature.name},${feature.type},${feature.value},${feature.decisionType},${feature.decisionStep},
</#list>
