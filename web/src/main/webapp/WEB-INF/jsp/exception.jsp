<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntryValue"%>
<%@page import="edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntry"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="java.util.List"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="Distribution" content="Global"/>
<meta name="Author" content="Julien Thibault"/>
<meta name="Robots" content="index,follow"/>
<link type="text/css" rel="stylesheet" href="../style/ibiomes-lite.css"/>
<link type="text/css" rel="stylesheet" href="../style/smoothness/jquery-ui-1.8.20.custom.css"/>

<title>iBIOMES</title>

</head>
<body>
<!-- wrap starts here -->
<div id="wrap">
	<div id="header"><div id="header-content">
		<h1 id="logo"><a href="../index.html">i<span class="gray">BIOMES</span></a></h1>
		<h2 id="slogan">Dictionary for biomolecular simulations</h2>
		<!-- Menu Tabs -->
		<ul>
			<li><a href="lookup" id="current">Dictionary</a></li>
			<li><a href="/mediawiki/index.php">Wiki</a></li>
			<li><a href="/ibiomes-web/">Repository</a></li>
			<li><a href="../contact.html">Contact</a></li>
		</ul>
	
	</div></div>
	
	<br/>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
			
	<div id="main" style="width:100%">
	
	<div class="post">
		<h1><c:out value="${errorTitle}"/></h1>
		<p style="text-align:justify"><c:out value="${errorMsg}" escapeXml="false"/></p>
	</div>
	
	<c:if test="${not empty errorTrace}">
		<br/>
		<div class="post">
			<h3>Details</h3>
			<p style="text-align:justify">
				<c:out value="${errorTrace}" escapeXml="false"/>
			</p>
		</div>
	</c:if>
	<br/>
	
	<br/>
	<br/>
	
	<!-- main ends here -->	
    </div>
    
	<!-- content-wrap ends here -->		
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<div id="footer">
<div id="footer-content">
	<div style="text-align:center">
		&#169; copyright 2013 <strong><a class="link" href="http://www.utah.edu">University of Utah</a></strong><br /> 
		Design by: <a class="link" href="http://www.styleshout.com"><strong>styleshout</strong></a>
</div></div></div>

</body>
</html>
