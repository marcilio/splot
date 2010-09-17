<#if hasError>
  Oops, SPLOT behaved like a bad boy :) If the problem persists contact the SPLOT team.
<#else>
{
	"isConsistent" : ${isConsistent}, 
	"hasDeadFeatures" : ${hasDeadFeatures},
	"deadFeatures" : [ <#list deadFeatures as dead>"${dead}"<#if dead_has_next>,</#if></#list> ],
	"coreFeatures" : [ <#list coreFeatures as core>"${core}"<#if core_has_next>,</#if></#list> ],
	"countValidConfigurations" : "${countValidConfigurations}"
}
</#if>
