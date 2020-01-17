<%@page import="utils.Utils"%>
<%@page import="storage.DriverManagerConnectionPool"%>
<%@page import="model.manager.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8">
	<title> LOGIN UTENTE</title>
	<script type="text/javascript" src="javascript/jquery-3.4.1.min.js"></script>
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
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
<!--===============================================================================================-->
</head>
<body>
	
	<%
		if(Utils.checkLogin(request.getSession(), request.getCookies()) != null)
			response.sendRedirect("/InfoBlog/");
	
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		email = email == null ? "" : email;
		password = password == null ? "" : password;
	%>
		
		<div class="limiter">
		<div class="container-login100">
			<div class="login100-more">
			<img id="logo" class='animazione' alt="Logo" src="icone/logo.svg" onclick="location.href='/InfoBlog/'" style="cursor:pointer">
			<label id="text" class="textDescription"></label>
			</div>
			<div class="wrap-login100 p-l-50 p-r-50 p-t-72 p-b-50">
				<form class="login100-form validate-form" action="LoginControl" method="post" autocomplete="off">
					<span class="login100-form-title p-b-20">
						Login Panel
					</span>

		<%
				String error = (String) request.getAttribute("errore");
				if(error != null)
				{
					switch(error)
					{
						case "DATI_ERRATI": 
						{
		%>
							<label class="errorLabel">Email o password errata, riprova!</label>
		<%
						}break;
						case "FORMATO_DATI_ERRATO":
						{
		%>
							<label class="errorLabel">Il formato di uno o più campi è errato, riprova!</label>
		<%
						}break;
					}
				}
		%>
			<div class="wrap-input100 validate-input" data-validate = "Formato email non valido, example: ex@abc.xyz">
						<span class="label-input100" >Email</span>
						<input class="input100" type="text" name="email" placeholder="Inserisci Email" value="<%=email %>" autocomplete="on" required>
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input" data-validate = "Formato Non valido: Lunghezza:(min=8,max=30) Richiesto almeno: un carattere speciale, una lettera maiuscola e sun numero">
						<span class="label-input100">Password</span>
						<input class="input100" type="password" name="password" value="<%=password %>" placeholder="*************" required>
						<span class="focus-input100"></span>
					</div>
		<%
			String checked = (String) request.getAttribute("checked");
			if(checked == null)
			{
						
		%>
			<div class="radioGroup">
				<input type="radio" name="typeUser" value="Utente" checked><label>Utente</label>
				<input type="radio" name="typeUser" value="Autore"><label>Autore</label>
				<input type="radio" name="typeUser" value="Moderatore"><label>Moderatore</label>
			</div>
		<%
			}
			else
			{
				switch(checked)
				{
					case "UtenteCheck":
					{
		%>				<div class="radioGroup">
							<input type="radio" name="typeUser" value="Utente" checked><label>Utente</label>
							<input type="radio" name="typeUser" value="Autore"><label>Autore</label>
							<input type="radio" name="typeUser" value="Moderatore"><label>Moderatore</label>
						</div>
		<%				
					}break;
							
					case "AutoreCheck":
					{
		%>
						<div class="radioGroup">
							<input type="radio" name="typeUser" value="Utente"><label>Utente</label>
							<input type="radio" name="typeUser" value="Autore" checked><label>Autore</label>
							<input type="radio" name="typeUser" value="Moderatore"><label>Moderatore</label>
						</div>
		<%				
					}break;
							
					case "ModeratoreCheck":
					{
		%>
						<div class="radioGroup">
							<input type="radio" name="typeUser" value="Utente"><label>Utente</label>
							<input type="radio" name="typeUser" value="Autore"><label>Autore</label>
							<input type="radio" name="typeUser" value="Moderatore"checked><label>Moderatore</label>
						</div>
		<% 
					}break;
							
					default: 
					{
								
		%>
						<div class="radioGroup">
							<input type="radio" name="typeUser" value="Utente" checked><label>Utente</label>
							<input type="radio" name="typeUser" value="Autore"><label>Autore</label>
							<input type="radio" name="typeUser" value="Moderatore"><label>Moderatore</label>
						</div>
		<%
					}break;
							
				}
			}
		%>
					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn">
								Login
							</button>
						</div>
					</div>
					<a href="/InfoBlog/" class="dis-block txt3 hov1 p-r-30 p-t-10 p-b-10 p-l-30">
						<i class="fa fa-long-arrow-left m-l-5" aria-hidden="true"></i>
						Homepage
						</a>
						<a href="/InfoBlog/registrazione.jsp" class="dis-block txt3 hov1 p-r-30 p-t-10 p-b-10 p-l-30">
							Registrati
							<i class="fa fa-long-arrow-right m-l-5"></i>
						</a>
				</form>
			</div>
		</div>
	</div>
	
	<!--===============================================================================================-->	
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<!--===============================================================================================-->
	<script src="javascript/main.js"></script>
	<script>
	
	</script>
</body>
</html>