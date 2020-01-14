<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="javascript/ricercaAutore.js"></script>
<meta charset="ISO-8859-1">
<title>Ricerca Autore</title>
</head>
<body>
	<form id="form">
		<input type="text" id="textfield" name="word" required>
		<input type="submit" value="Cerca">
	</form>
	<div id="listaAutori">
	</div>
</body>
</html>