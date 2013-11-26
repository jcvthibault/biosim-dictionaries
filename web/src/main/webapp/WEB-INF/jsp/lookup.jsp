<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="Distribution" content="Global"/>
<meta name="Author" content="Julien Thibault"/>
<meta name="Robots" content="index,follow"/>
<link type="text/css" rel="stylesheet" href="../style/ibiomes-dictionary.css"/>
<link type="text/css" rel="stylesheet" href="../style/smoothness/jquery-ui-1.8.20.custom.css"/>

<style>
.ui-autocomplete {
    max-height: 300px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}
/* IE 6 doesn't support max-height
 * we use height instead, but this forces the menu to always be this tall
 */
* html .ui-autocomplete {
    height: 300px;
}
.ui-autocomplete-loading { 
	background:url('../images/anims/indicator.gif') no-repeat right center 
}
</style>

<script type="text/javascript" src="../jquery/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../jquery/js/jquery-ui-1.8.20.custom.min.js"></script>
<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
	
	function loadAutocompleteAVU(inputId, dictionaryUrl)
	{
		$("#"+ inputId).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				$.ajax({
					url: dictionaryUrl,
					dataType: "json",
					data: {
						term: request.term
					},
					success: function( data ) {
						response( $.map( data, function( item ) {
							return {
								label: item.term + ' (' + item.attributeType +')',
								value: item.uid
							};
						}));
					}
				});
			},
			select: function( event, ui ) {
				showEntryDetails(ui.item.value);
				ui.item.value = ui.item.label;
		      }
		});
	}
	
	function showEntryDetails(entryUid)
	{
		$("#entryDetailsDiv").empty();
		$.ajax({
			url: contextPath + "/rest/services/lookup/byuid",
			dataType: "json",
			data: {
				uid: entryUid,
				htmlEncoding: true
			},
			success: function( data ) {
				var html = '<h3>Entry details</h3><table>';
				var e = 0;
				for (e=0;e<data.length;e++){
					var field = data[e];
					html += '<tr>';
					html += '<td style="text-align:right"><strong>' + field.attribute + '</strong></td>';
					html += '<td></td>';
					html += '<td>' + field.value;
					if (field.attribute == 'ATTRIBUTE_TYPE')
						html += ' <a class="link" href="list?name='+field.value+'">[show all entries]</a>';
					html += '</td></tr>';
				}
				html += '</table>';
				$("#entryDetailsDiv").html(html);
			}
		});
			
	}
	
</script>
<script type="text/javascript">
$(document).ready(function() {
	//alert(contextPath);
	loadAutocompleteAVU("lookupterm", contextPath + "/rest/services/lookup/byterm");
	loadAutocompleteAVU("lookupdesc", contextPath + "/rest/services/lookup/bydesc");
	
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
			<li><a href="lookup" id="current">Dictionary</a></li>
			<li><a href="/mediawiki/index.php">Wiki</a></li>
			<li><a href="/ibiomes-web/">Repository</a></li>
			<li><a href="../contact.html">Contact</a></li>
		</ul>
	
	</div></div>
	
	<br/>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
		<div id="sidebar" >
			<div class="sidebox">	
				<h1 class="clear">Browse</h1>
				<p><strong>Computational methods and parameters</strong></p>
				<ul class="sidemenu">
					<li><a class="link" href="list?name=method">Computational methods</a></li>
					<li><a class="link" href="list?name=method_class">Classes of computational methods</a></li>
					<li><a class="link" href="list?name=method_family">Families of computational methods</a></li>
					<li><a class="link" href="list?name=calculation">Calculations</a></li>
					<li><a class="link" href="list?name=calculation_type">Calculation types</a></li>
					<li><i><strong>Quantum chemistry</strong></i></li>
					<li><a class="link" href="list?name=basis_set">Basis sets</a></li>
					<li><a class="link" href="list?name=basis_set_type">Basis set types</a></li>
					<li><i><strong>Molecular dynamics</strong></i></li>
					<li><a class="link" href="list?name=force_field">Force field parameter sets</a></li>
					<li><a class="link" href="list?name=force_field_type">Force field types</a></li>
					<li><a class="link" href="list?name=barostat">Barostats</a></li>
					<li><a class="link" href="list?name=thermostat">Thermostats</a></li>
					<li><a class="link" href="list?name=ensemble">Ensemble types</a></li>
					<li><a class="link" href="list?name=constraint_algorithm">Constraint algorithms</a></li>
					<li><a class="link" href="list?name=data_generating_method">Data generating methods</a></li>
					<li><a class="link" href="list?name=analysis_algorithm">Analysis methods</a></li>
				</ul>
				<p><strong>Structural and chemical information</strong></p>
				<ul class="sidemenu">
					<li><a class="link" href="list?name=residue">Residues</a></li>
					<li><a class="link" href="list?name=atomic_element">Atomic elements (periodic table)</a></li>
					<li><a class="link" href="list?name=element_family">Atomic element families</a></li>
					<li><a class="link" href="list?name=functional_group">Functional groups</a></li>
				</ul>
				<p><strong>Software and hardware information</strong></p>
				<ul class="sidemenu">
					<li><a class="link" href="list?name=software">Software packages</a></li>
					<li><a class="link" href="list?name=file_format">Chemical file formats</a></li>
				</ul>
			</div>
		</div>
	<div id="main">
	
	<div class="post">
	
	<h1>Dictionary lookup</h1>
	<p><i>This pages enables dictionary lookups using keywords. 
	The current implementation allows searches based on the term or description representing the entry.</i></p>

	<h3>Search</h3>
	<table>
        <tr>
        	<td>By term</td>
			<td><input id="lookupterm" name="lookupterm" value="" type="text" style="width:300px"/></td>
        </tr>
        <tr>
        	<td>By description</td>
			<td><input id="lookupdesc" name="lookupdesc" value="" type="text" style="width:300px"/></td>
        </tr>
    </table>
	
	</div>
	
	<br/>
	<div id="entryDetailsDiv"></div>
	
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
