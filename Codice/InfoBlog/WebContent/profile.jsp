<%@page import="utils.Utils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="css/profile.css">
<link rel="stylesheet" type="text/css" href="css/util.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<script src="javascript/jquery-3.4.1.min.js"></script>
<meta charset="ISO-8859-1">
<title>Area personale</title>
</head>
<%
	//Vedo se è stata chiamata la jsp tramite la servlet
	String email = (String) request.getAttribute("email");
	String nome = "";
	String cognome = "";
	String username = "";
	String password = "";
	if(email == null)
	{
		// reindirizzo alla servlet
		RequestDispatcher rd = request.getRequestDispatcher("/VisualizzaDatiPersonaliControl");
     	rd.forward(request, response);
		return;
	}
	else
	{
		nome = (String) request.getAttribute("nome");
		cognome = (String) request.getAttribute("cognome");
		username = (String) request.getAttribute("username");
		password = (String) request.getAttribute("password");
	}
%>
<body>
		<div class="container">
			<img id="logo" src="icone/logo.svg" onclick="location.href='/InfoBlog/homepage.jsp'">
			<div class="topTitle"><label>Area personale</label></div>
			<div class="data">
				<div class="titleTopData"><label>Dati personali</label></div>
			<%
				String error = (String) request.getAttribute("errore");
				if(error != null)
				{
					if(error.equals("FORMATO_DATI_ERRATI"))
					{
		%>
						<div class="errorLabel"><label>Formato dati errato, riprova!</label></div>
		<%
					}
					if(error.equals("DATI_PRESENTI"))
					{
		%>
						<div class="errorLabel"><label>Username presente!</label></div>
	 	<%
					}
					if(error.equals("MODIFICHE_ASSENTI"))
					{
		%>
						<div class="errorLabel"><label>Modifiche Assenti</label></div>
		<%
					}
					
				}
				String success = (String) request.getAttribute("success");
				if(success != null)
				{
					if(success.equals("MODIFICA_SUCCESS"))
					{
		%>				
						<div class="successLabel"><label>Dati modificati con successo!</label></div>
		<%
					}
					
				}
		%>
				<div class="wrap-input100-reg validate-input" data-validate="Il Nome è richiesto">
						<span class="label-input100-reg">Nome</span>
						<input id="nome" class="input100-reg" type="text" name="nome" value=<%=nome %> disabled>
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100-reg validate-input" data-validate = "Il Cognome è richiesto">
						<span class="label-input100-reg">Cognome</span>
						<input id="cognome" class="input100-reg" type="text" name="cognome" value=<%=cognome %> disabled>
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100-reg validate-input" data-validate="L'email è richiesta">
						<span class="label-input100-reg">Email</span>
						<input id="email" class="input100-reg" type="text" name="email" value=<%=email.substring(1) %> disabled>
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100-reg validate-input" data-validate = "L'username è richiesta">
						<span class="label-input100-reg">Username</span>
						<input type="text" id="username" class="input100-reg" name="username" value=<%=username %> disabled>
						<span class="focus-input100"></span>
					</div>
					
					<div class="wrap-input100-reg validate-input" data-validate = "Password is required">
						<span class="label-input100-reg">Password</span>
						<input id="password" class="input100-reg" type="password" name="password" value=<%=password %> disabled>
						<span class="focus-input100"></span>
					</div>
					
			<%
					if(email.substring(0,1).equals("m"))
					{
						String categoria = (String) request.getAttribute("categoria");
			%>
					<div class="wrap-input100-reg validate-input" data-validate = "">
						<span class="label-input100-reg">Categoria Moderata</span>
						<input id="categoria" class="input100-reg" type="text" name="categoria" value=<%=categoria %> disabled>
						<span class="focus-input100"></span>
					</div>
			<%
					}
			%>		
					<div class="fixButton"><button class="button" onclick="onclick=changeView()">Modifica</button></div>
			</div>
		</div>
</body>
<script src="javascript/profile.js"></script>


</html>