<%@page import="model.bean.Articolo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modifica Articolo</title>
<link rel="stylesheet" type="text/css" href="css/richiestaPubblicazione.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" />
</head>
<body>

<%
	
%>
<jsp:include page="header.jsp"></jsp:include>
	<div class='containerArea'>
	<div class='titoloContainer'>
		<label>Modifica Articolo</label>
	</div>
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
					case "NESSUN_ALLEGATO" : errorView="Attenzione nessun allegato presente: gli articoli in questo stato non vengono moderati";break;
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
			<input id='idArticolo' type="hidden" name="idArticolo" value=<%=articolo.getId() %>>
			<div class='areaAllegati'>
				<div class="title"><label>Sezione Allegati</label></div>
			<div class='inputFile'>
			<label for="files" class="custom-file-upload">
    			<i class="fa fa-cloud-upload"></i> Seleziona uno o più file
				</label>
			<input id="files" type='file' name="file[]" accept="application/pdf,application/msword" multiple required>
			<br>
			<div id="selectedFiles"></div>
			<label>Seleziona gli allegati da cancellare: </label>
			<div id="listaAllegati"></div>
			</div>
			</div>
			<div class='areaSubmit'>
				<input type='button' onclick='precheckSubmit()' value='Invia modifiche'/>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="javascript/caricaAllegato.js"></script>
<script>
$(document).ready(function()
{
	categoria = "<%= articolo.getCategoria() %>";
	$(".selectCategoria").val(categoria);

});
function precheckSubmit()
{
	
	checkSubmit();
}
</script>
<script src="javascript/richiestapubblicazione.js" charset="UTF-8"></script>
</html>