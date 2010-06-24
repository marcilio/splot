<updates>
<#if !maxModelSizeReached>
	<data name="countconf_result" value="<#if (countconf_result <= 9999999)>${countconf_result?string("#,###,###")}<#else>${countconf_result?string("0.##E0")}</#if>"/>
	<data name="countconf_running_time" value="${countconf_running_time}"/>
	<data name="vardegree_result" value="${vardegree_result?string("0.####E0")}"/>
	<data name="vardegree_running_time" value="${vardegree_running_time}"/>
	<data name="bddnodes" value="${bddnodes?string}"/>
	<data name="bddheuristic" value="${bddheuristic}"/>
</#if>
</updates>


