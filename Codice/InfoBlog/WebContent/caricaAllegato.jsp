<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<link rel="stylesheet" href="css/Allegato.css">
    <script type="text/javascript" src="javascript/caricaAllegato.js"></script>
	<meta charset="ISO-8859-1">
	<title>Pannello Allegati</title>
</head>
<body>
	<div id="allegatoBlock">
		<p style="padding-left:10px;display:inline;">Lista allegati dell'articolo</p>
		<div style="padding-top:1%;">
			<form id="form" action="CaricaAllegatoControl" method="POST" enctype="multipart/form-data">
				<input type="hidden" name="id" value="1">
				<input type='file' name="file" accept="application/pdf,application/msword" required>
				<input class="invio" type="submit" value="Carica Allegato">
			</form>
			<button id="deleteAllegato" type="button" tabindex="-1">Cancella Allegato</button>
		</div>
		<div id="listaAllegati"></div>
	</div>
</body>
</html>