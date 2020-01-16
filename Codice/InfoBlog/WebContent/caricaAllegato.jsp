<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<link rel="stylesheet" href="css/Allegato.css">
	<meta charset="ISO-8859-1">
	<title>Pannello Allegati</title>
</head>
<body>
	<div id="allegatoBlock">
		<p style="padding-left:10px;display:inline;">Lista allegati dell'articolo</p>
		<div style="padding-top:1%;">
			<input id='idArticolo' type="hidden" name="id" value=<%=64 %>>
			<input id="files" type='file' name="files[]" accept="application/pdf,application/msword" multiple required>
			<input id="caricaAllegato" class="invio" type="submit" value="Carica Allegato">
			<button id="deleteAllegato" type="button" tabindex="-1">Cancella Allegato</button>
		</div>
		<div id="listaAllegati"></div>
	</div>
<script type="text/javascript" src="javascript/caricaAllegato.js"></script>
</body>
</html>