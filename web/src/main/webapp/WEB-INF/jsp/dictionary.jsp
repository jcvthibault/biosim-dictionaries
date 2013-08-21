<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@page import="edu.utah.bmi.ibiomes.dictionary.web.HTMLUtils"%>
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
<link type="text/css" rel="stylesheet" href="../style/ibiomes-dictionary.css"/>
<link type="text/css" rel="stylesheet" href="../style/smoothness/jquery-ui-1.8.20.custom.css"/>
<link type="text/css" rel="stylesheet" href="../style/tooltipster.css" />

<script type="text/javascript" src="../jquery/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../jquery/js/jquery-ui-1.8.20.custom.min.js"></script>
<script type="text/javascript" src="../jquery/js/jquery.tooltipster.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("img[name='citationImg']").tooltipster({
		trigger: 'click',
		maxWidth: 400
	});
});
</script>

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
			<li><a href="lookup">Dictionary</a></li>
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
	
	<h1>[Dictionary] <%= request.getAttribute("dictionaryName") %></h1>
	<p><i><%= request.getAttribute("dictionaryDescription") %></i></p>
	<%
		List<String> dataHeaders = (List<String>)request.getAttribute("dataHeaders");
		List<DictionaryEntry> dataTable = (List<DictionaryEntry>)request.getAttribute("dataTable");
		out.println("<p>Number of entries: "+ dataTable.size() +"</p>");
	%>
	<table>
		<tr>
		<%
			for (String header : dataHeaders){
				out.println("<th>"+ header +"</th>");
			}
		%>
		</tr>
		<%
		int r=0;
		for (DictionaryEntry entry : dataTable){
			String rowClass = "row-a";
			if (r%2==0) rowClass = "row-b";
			r++;
			
			out.println("<tr class='"+rowClass+"'>");
			for (DictionaryEntryValue entryValue : entry){
				out.println("<td>");
				if (entryValue.getValue()!=null 
						&& entryValue.getValue().trim().length()>0
						&& !entryValue.getValue().equals("null"))
				{
					String entryValueString = entryValue.getValue();
					if (entryValue.getAttribute().equals("CITATION")){
						String citation = entryValueString.replaceAll("\"", "'");
						out.println("<img class=\"pointer\" name=\"citationImg\" src=\"../images/icons/info_small.png\" title=\""+ citation +"\"/>");
					}
					else out.println(entryValueString);
				}
				out.println("</td>");
			}
			out.println("</tr>");
		}
		%>
	</table>
		
	</div>
	
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
