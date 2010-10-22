
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Workflow Specifications</title>

<link type="text/css" rel="stylesheet" href="splot.css"/>

<link rel="stylesheet" type="text/css" href="http://download.dojotoolkit.org/release-1.5.0b3/dojo-release-1.5.0b3/dijit/themes/claro/claro.css"/>
<style type="text/css">
            body, html { font-family:helvetica,arial,sans-serif; font-size:90%; }
</style>
<script type="text/javascript" src="http://archive.dojotoolkit.org/nightly/dojotoolkit/dojox/widget/SortList.js"></script>
 
<link rel="stylesheet" href="http://archive.dojotoolkit.org/nightly/dojotoolkit/dojox/widget/SortList/SortList.css"/>

<script type="text/javascript"
	 	src="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dojo/dojo.xd.js" 
	 	djConfig="parseOnLoad: true, isDebug: false, dojoBlankHtmlUrl: 'blank.html'">
</script>


<script type="text/javascript">
  // your script goes here
</script>

<script type="text/javascript">
  dojo.require("dojo.widget.SortList");
</script>






</head>
<body class="claro">

<div id="header"><div id="logo"><img src="images/splot.jpg"></div></div> 

<!-- end #header --> 
<div id="menu"> 
	<ul> 
		<li><a href="index.html">Home</a></li>  
		<li><a href="feature_model_edition.html">Feature Model Editor</a></li> 
		<li class="first"><a href="automated_analyses.html">Automated Analysis</a></li> 
		<li><a href="product_configuration.html">Product Configuration</a></li> 
		<li><a href="feature_model_repository.html">Feature Model Repository</a></li> 
		<li><a href="http://www.marcilio-mendonca.com/contact_splot.asp">Contact Us</a></li> 
	</ul> 
</div> 
<!-- end #menu --> 



	

<div id="wrapper"> 
<div class="btm"> 
	<div id="page"> 
		<div id="content"> 
			<div class="post"> 
				
				<h1 class="title"><a href="#">Workflow Specification</a></h1> 
				<#if error!="">
					${error}
				<#else>	
					<h1 class="orangeFont"><a href="#">${name}</a></h1>
				
				
					<#if imageFileName !="false">
						<div><img src="extensions/workflow_images/${imageFileName}"> </div>
						<div><img src="extensions/workflow_images/legend.png"> </div>
					</#if>
				</#if>
				<div class="entry"> 
					 <div>
					 	<table border="0" width="100%" cellpadding="1">
						 	<tr><td align="left" width="100%">
								 <div>
								 <#if error=="">
								 <ul dojoType="dojox.widget.SortList" id="workflows"  title="Workflow Specification" sortable="false" style="width:300px; height=300px;">
								   		<#list specification_list as specification>
								   			<li>
								   				<h1 class="disabledRow"><a href="#">${specification.decomp_name}</a></h1>
								   				<#assign index=0> 
								   				
								   				<#list specification.decomp_info as tasks>
								   				<#assign index=index+1> 
								   				
								   					<ul>
								   					   <div>
								   						 <#if tasks.decomposes_exists=="true">
								   						 	<b>${index}:</b> ${tasks.task_name}
								   						 	
								   					 		
								   					 		<ul>
								   					 			<b> decomposes to:</b> 
								   					 				<#list tasks.decomposes as decomposesList>
								   					 			<ul>
								   					 				
								   					 					*: ${decomposesList.decompose_id}
								   					 			</ul>
								   					 				</#list>
								   					 		</ul>	
								   						 <#else>
								   							<b>${index}:</b> ${tasks.task_name}
								   						 </#if>
								   						 </div> 
								   					</ul>
								   				</#list>		
								   				
								   				 
								   			</li>	
								   		</#list>		
								   			
								   
								   
								   
								  </ul>
								  
								</#if>
								</div>	 
	 						</td>
	 						
	 						</tr>
	 					</table>
					</div>
			
				</div> 
			</div> 
		</div> 
		<!-- end #content --> 
		<div style="clear: both;">&nbsp;</div> 
	</div> 
	<!-- end #page --> 
</div> 
</div>


 
<div id="footer"> 
	<p><a href="http://gsd.uwaterloo.ca/">Generative Software Development Lab</a> / <a href="http://csg.uwaterloo.ca">Computer Systems Group</a>, University of Waterloo, Canada, 2009.</p> 
</div> 
<!-- end #footer --> 

<!-- Google Analytics extensions to track down visitors -->

<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-1626595-6");
pageTracker._trackPageview();
} catch(err) {}
</script>
</body>
</html>
