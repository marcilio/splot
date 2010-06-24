<#if hasError>
	<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
<#else>
	<#if fmHasError>
		 <p><b>Feature Model is Incorrect or URL cannot be found</b> 
		 <br>${fmErrorMessage}</p>
	<#else>
		<p><b>Congratulations!</b> Your feature model is syntactically correct.</p>
	</#if>
</#if>