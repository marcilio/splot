<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Name       : Compromise
Description: A two-column, fixed-width design with dark color scheme.
Version    : 1.0
Released   : 20081103

-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Welcome to the Software Product Lines Online Tools Homepage</title>

<link type="text/css" rel="stylesheet" href="splot.css"/>

</head>
<body>

<div id="header"><div id="logo"><img src="images/splot.jpg"/></div></div> 

<!-- end #header --> 
<div id="menu"> 
	<ul> 
		<li><a href="index.html">Home</a></li> 
		<li><a href="feature_model_edition.html">Feature Model Editor</a></li> 
		<li><a href="automated_analyses.html">Automated Analysis</a></li> 
		<li><a href="product_configuration.html">Product Configuration</a></li> 
		<li class="first"><a href="feature_model_repository.html">Feature Model Repository</a></li> 
		<li><a href="http://www.marcilio-mendonca.com/contact_splot.asp">Contact Us</a></li> 
	</ul> 
</div> 
<!-- end #menu --> 

<div id="wrapper"> 
<div class="btm"> 
	<div id="page"> 
		<div id="content"> 
		<div class="post">

			<h1 class="title"><a href="#">Feature Model Repository Statistics</a></h1> 
				
			<div class="entry">
				<p>
					This section presents several <b>realtime</b> statistics for SPLOT's online feature model repository. 
					The data shown reflects the current state of the feature model repository and will change whenever a new entry is added
					to the repository. We hope to continuously add new charts to this page as we refine the information we collect about the 
					models in the repository. Please, feel free to save and use any of the chart images in this page. We kindly ask that you 
					make a reference to SPLOT in this case. The official reference to SPLOT can be found in the <a href="/SPLOT/index.html">homepage</a>.
				</p>
				<hr/>			

				<p>
				Currently, SPLOT's feature model repository contains <b>${countModels} models</b> in a total of <b>${totalModelSize} features</b> altogether. 
				The table below shows maximum, minimum, average and mean data for the <i>size</i> and </i>CTCR</i> of the models in the repository. CTCR
				is defined as the ratio of the number of distinct features in the cross-tree constraints to the number of features in the feature model.
				For instance, say a model has 10 features, 2 of which ara part of a single cross-tree constraint attached to the model (e.g. "<i>feature-1 implies feature-2</i>").
				We say the CTCR for this model is 0.2 or (20%). The CTCR metric has proven useful in 
				analyzing the complexity of feature model SAT instances. For details see article: 
				<i><a target="_new" href="/SPLOT/articles/mendonca_sat_analysis_splc_2009.pdf">SAT-based Analysis of Feature Models is Easy</a>. 
				In Proceedings of the 13th International Software Product Line Conference (SPLC'09). Aug 2009, San Francisco, CA, USA.</i>
				</p>
				
				<p>
				<table width="100%" border=0 class="standardTableStyle">
					<tr>
						<td><b>SPLOT's Repository General Stats</td>
						<td>Largest</td>
						<td>Smallest</td>
						<td>Mean</td>
						<td>Average</td>
					</tr>
					<tr>
						<td>Size (number of features)</td>
						<td>${maxModelSize}</td>
						<td>${minModelSize}</td>
						<td>${meanModelSize}</td>
						<td>${avgModelSize}</td>
					</tr>
					<tr>
						<td>CTCR (%)</td>
						<td>${maxModelCTCR}</td>
						<td>${minModelCTCR}</td>
						<td>${meanModelCTCR}</td>
						<td>${avgModelCTCR}</td>
					</tr>
				</table>
				</p>
				
				<p>
				<table border=0 width="100%" cellpadding="10">
				<tr><td align="left">
					<!-- <img src="http://chart.apis.google.com/chart?cht=p3&chd=t:10,20,30,40,50&chdl=30 models or 34|30 models or 34|30 models or 34|30 models or 34|30 models or 34%&chxl=0:|1 to 20 features|21 to 50 features|51 to 100 features|101 to 200 features|201 to 500 features&chbh=50,20,10&chtt=Size+of+Feature+Models&chts=000000,18&chs=600x200&chxt=x,y"/> -->

					<hr>			

					<!-- Chart Feature Model Size -->
					<img src="http://chart.apis.google.com/chart?cht=p3&chd=t:${fmChartBySize.data}&chdl=${fmChartBySize.legend}%&chxl=0:|${fmChartBySize.labels}&chbh=50,20,10&chtt=By+Feature+Model+Size&chts=000000,18&chs=720x200&chxt=x,y"/>
					<hr>
					
					<!-- Chart Feature Model CTCR -->
					<img src="http://chart.apis.google.com/chart?cht=p3&chd=t:${fmChartByCTCR.data}&chdl=${fmChartByCTCR.legend}%&chxl=0:|${fmChartByCTCR.labels}&chbh=50,20,10&chtt=By+Feature+Model+Cross-Tree+Constraints+Representativeness+(CTCR)&chts=000000,18&chs=730x200&chxt=x,y"/>
					<hr>
					
					<!-- Chart Feature Model by Feature Type -->
					<img src="http://chart.apis.google.com/chart?cht=p3&chd=t:${fmChartByFeatType.data}&chdl=${fmChartByFeatType.legend}%&chxl=0:|${fmChartByFeatType.labels}&chbh=50,20,10&chtt=By+Types+of+Features+on+Feature+Models&chts=000000,18&chs=720x200&chxt=x,y"/>
					<hr>
					
					<!-- Chart Feature Model by Source -->
					<img src="http://chart.apis.google.com/chart?cht=p3&chd=t:${fmChartBySource.data}&chdl=${fmChartBySource.legend}%&chxl=0:|${fmChartBySource.labels}&chbh=50,20,10&chtt=By+Feature+Model+Source&chts=000000,18&chs=750x200&chxt=x,y"/>
					<hr>

				</td></tr>
				</table>
				</p>
				
				<p><a href="javascript:history.back()">back</a></p>
					
			</div>
		</div>
		</div> 	<!-- end #content --> 
		<div style="clear: both;">&nbsp;</div> 
	</div>
</div>
</div>

<div id="footer"> 
	<p><a href="http://gsd.uwaterloo.ca/">Generative Software Development Lab</a> / <a href="http://csg.uwaterloo.ca">Computer Systems Group</a>, University of Waterloo, Canada, 2009.</p> 
</div> 
<!-- end #footer --> 
	
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

