<%@page import="utils.Utils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Richiesta Pubblicazione Articolo</title>
<link rel="stylesheet" type="text/css" href="css/richiestaPubblicazione.css">
<script src="javascript/jquery-3.4.1.min.js"></script>
</head>
<body>
<%	
	//Controllo che è un autore che vuole pubblicare l'articolo
	String log = Utils.checkLogin(request.getSession(), request.getCookies());
	log = log == null ? "" : log.substring(0,1);
	if(!log.equals("a"))
		response.sendRedirect("/InfoBlog/");
%>
<jsp:include page="header.jsp"></jsp:include>
	<div class='containerArea'>
		<div class='titoloContainer'>
			<label>Richiesta di pubblicazione articolo</label>
		</div>
		<form class='formRichiestaPubblicazione' action='PubblicazioneControl' enctype="multipart/form-data" method='POST'>
		<%
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
					<input type='text' name='titolo' placeholder="titolo articolo..." value="<%=titolo %>" autocomplete='off' required>
				<%
				}
				else
				{
				%>
					<input type='text' name='titolo' placeholder="titolo articolo..."  autocomplete='off' required>
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
					<textarea  name='contenuto' style='resize:none;' placeholder="contenuto..." autocomplete='off' required><%=contenuto%></textarea>
				<%
				}
				else
				{
				%>
					<textarea  name='contenuto' style='resize:none;' placeholder="contenuto..." autocomplete='off' required></textarea>
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
			<div class='areaAllegati'>
				<div class="title"><label>Sezione Allegati</label></div>
				<div class='inputFile'>
				<label for="files" class="custom-file-upload">
    			<i class="fa fa-cloud-upload"></i> Seleziona uno o più file
				</label>
				<input type="file" id="files" name="file[]" multiple accept="application/pdf,application/msword" required /></div>
				<div id="selectedFiles"></div>
			</div>
			<div class='areaSubmit'>
				<input type='button' onclick='checkSubmit()' value='Invia richiesta pubblicazione'/>
			</div>
		</form>
	</div>
</body>
<script src="javascript/richiestapubblicazione.js" charset="UTF-8"></script>
</html>