
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>FUNDP SPLOT Extensions</title>

<link type="text/css" rel="stylesheet" href="splot.css"/>

<script type="text/javascript">
  // your script goes here
</script>

</head>
<body>

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
				<h1 class="title"><a href="#">FUNDP SPLOT Extensions</a></h1> 
				<div class="entry"> 
				
				<p>Let's use the freemarker properties created by handler CreateFeatureModelViewsHandler.java</p>
				
				<p>
				
				<p>University: ${university.name}</p>
				<p>Total Programs: ${university.num_programs}</p>
				
				<#list university.students as student>
					<p>Student: ${student.name}, Age: ${student.age}</p>
				</#list>
				
				</p>
				
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
