
<#if hasError>
<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
<#else>
<div style="font-size: 15px; font-weight: bold; padding: 10px">PRODUCT CATALOG</span> <a href="#">(<b>${products?size}</b> products)</a>
<div style="width:410px;height:1000px;overflow:auto;">
	<table width=100%>
	<#list products as product>
	<tr>
	<td style="background-color:rgb(255,243,211)">
		${product_index+1}. <b>${product.id!}</b>
		<br>
		Price (US$): ${product.price!}
		<br>
		<a href="${product.url!}" target="_new">View this model</a>
	</td><td style="background-color:rgb(255,243,211)">
		 <img width=123 height=80 src="${product.image!}">
	</td>
	</tr>
	</#list>
</div>
</#if>
