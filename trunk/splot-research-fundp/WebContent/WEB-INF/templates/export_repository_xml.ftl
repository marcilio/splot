<#if hasError>
	${errorMessage}
<#else>
<repository>
<#list models as model>
	<model>
		<index>${model.index}</index>
		<name>${model.name}</name>
		<file>${model.file}</file>
		<url>${model.url}</url>
		<description>${model.description}</description>
		<creator>${model.creator}</creator>
		<email>${model.email}</email>
		<date>${model.date}</date>
		<department>${model.department}</department>
		<organization>${model.organization}</organization>
		<address>${model.address}</address>
		<phone>${model.phone}</phone>
		<website>${model.website}</website>
		<reference>${model.reference}</reference>
		<num_features>${model.features}</num_features>
		<ctcr>${model.ecr}</ctcr>
		<clause_density>${model.clausedensity}</clause_density>
	</model>    
</#list>
</repository>
</#if>