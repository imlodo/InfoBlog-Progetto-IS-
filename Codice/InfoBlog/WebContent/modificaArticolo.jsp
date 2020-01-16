<%@page import="model.bean.Articolo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modifica Articolo</title>
<link rel="stylesheet" type="text/css" href="css/richiestaPubblicazione.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" />
<link rel="stylesheet" href="css/Allegato.css">
</head>
<body>

<%
	
%>
<jsp:include page="header.jsp"></jsp:include>
<div class='titoloContainer'>
		<label>Modifica Articolo</label>
	</div>
	<div class='containerArea'>
		<form class='formRichiestaPubblicazione' action='ModificaArticoloControl' enctype="multipart/form-data" method='POST'>
		<%
			Articolo articolo = (Articolo) request.getAttribute("articolo");
			String titolo = (String) request.getAttribute("titolo");
			String contenuto = (String) request.getAttribute("contenuto");
			String error = (String) request.getAttribute("errore");
			String errorView = "";
			if(error != null)
			{
				switch(error)
				{
					case "FORMATO_DATI_ERRATO" : errorView="Formato dati errato";break;
					case "TITOLO_PRESENTE" : errorView="Titolo presente";break;
					case "CONTENUTO_ARTICOLO_PRESENTE" : errorView="Contenuto presente";break;
					case "FORMATO_ALLEGATI_ERRATO" : errorView="Formato allegati errato";break;
					case "ALLEGATO_PRESENTE" : errorView="Allegato presente";break;
					case "FILE_ALLEGATO_PRESENTE" : errorView="File allegato presente";break;
					case "NESSUN_MODERATORE_ESISTENTE" : errorView="Non esiste un moderatore per la categoria selezionata!";break;
					case "MODIFICHE_ASSENTI" : errorView="Modifiche Assenti";break;
				}
		%>
				<div class='area'>
					<div class="titleError"><label><%=errorView %></label></div>
				</div>
		<%
			}
		%>
			<div class='area'>
				<div class="title"><label>Titolo Argomento</label></div>
				<%
				if(titolo != null)
				{
				%>
					<input type='text' name='titolo' placeholder="titolo articolo..." value=<%=titolo %> required>
				<%
				}
				else
				{
				%>
					<input type='text' name='titolo' placeholder="titolo articolo..." value=<%=articolo.getTitolo() %> required>
				<%	
				}
				%>
			</div>
			<div class='area'>
			<div class="title"><label>Contenuto</label></div>
				<%
				if(contenuto != null)
				{
				%>
					<textarea  name='contenuto' style='resize:none;' placeholder="contenuto..." required><%=contenuto%></textarea>
				<%
				}
				else
				{
				%>
					<textarea  name='contenuto' style='resize:none;' placeholder="contenuto..." required><%=articolo.getContenuto() %></textarea>
				<%	
				}
				%>
			</div>
			<div class='area'>
				<div class="title"><label>Seleziona Categoria</label></div>
				<select name='categoria' class="selectCategoria" required>
  					<option value="hardware">Hardware</option>
  					<option value="software">Software</option>
  					<option value="algoritmi">Algoritmi</option>
  					<option value="reti">Reti</option>
  					<option value="iot">Iot</option>
  					<option value="sicurezza">Sicurezza</option>
  					<option value="cloud">Cloud</option>
  					<option value="machineLearning">Machine Learning</option>
  				</select>
			</div>
			<div id="allegatoBlock">
		<p style="padding-left:10px;display:inline;">Lista allegati dell'articolo</p>
		<div style="padding-top:1%;">
			<input id='idArticolo' type="hidden" name="id" value=<%=articolo.getId() %>>
			<input id="files" type='file' name="files[]" accept="application/pdf,application/msword" multiple required>
			<br>
			<label>Seleziona gli allegati da cancellare: </label>
		</div>
		<div id="listaAllegati"></div>
		</div>
			<div class='areaSubmit'>
				<input type='button' onclick='checkSubmit()' value='Invia modifiche'/>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="javascript/caricaAllegato.js"></script>
<!-- <script type="text/javascript" src="javascript/modificaArticolo.js"></script> -->
<script>
$(document).ready(function()
{
	categoria = "<%= articolo.getCategoria() %>";
	$(".selectCategoria").val(categoria);

});
function checkSubmit()
{
	if($("#listaAllegati")[0].childElementCount > 0 || $("#files")[0].files.length > 0)
	{
		$("#files").attr("required", "false");
		$(".formRichiestaPubblicazione").submit();
	}
	else
	{
		$("#files").attr("required", "true");
	}
}
</script>
</html>