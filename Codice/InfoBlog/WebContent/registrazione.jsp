<%@page import="utils.Utils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Pagina di registrazione</title>
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
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
<!--===============================================================================================-->
</head>
<%if(Utils.checkLogin(request.getSession(), request.getCookies()) != null)
	response.sendRedirect("/InfoBlog/"); %>
<body style="background-color: #999999;">
	
	<div class="limiter">
		<div class="container-login100">
		<div class="login100-more-reg">
			<img id="logo" class='animazione' alt="Logo" src="icone/logo.svg" onclick="location.href='/InfoBlog/'" style="cursor:pointer">
			<label id="text" class="textDescription2"></label>
			</div>

			<div class="wrap-login100 p-l-50 p-r-50 p-t-20 p-b-20">
				<form class="login100-form validate-form" action="RegistrazioneControl" method="post">
					<span class="login100-form-title-reg p-b-10">
						Registrazione
					</span>
			<%
				String nome = request.getAttribute("nome") != null ? (String) request.getAttribute("nome") : "";
				String cognome = request.getAttribute("cognome") != null ? (String) request.getAttribute("cognome") : "";
				String username = request.getAttribute("username") != null ? (String) request.getAttribute("username") : "";
				String password = request.getAttribute("password") != null ? (String) request.getAttribute("password") : "";
				String email = request.getAttribute("email") != null ? (String) request.getAttribute("email") : "";
				
				String error = (String) request.getAttribute("errore");
				if(error != null)
				{
					switch(error)
					{
						case "DATI_PRESENTI":
						{
			%>
						<label class="errorLabelReg">L'email o l'username è già presente!</label>
			<%
						}break;
						case "FORMATO_DATI_ERRATI":
						{
			%>
						<label class="errorLabelReg">Il formato dei dati è errato</label>	
			<%		
						}break;
					}
				}
			%>
					<div class="wrap-input100-reg validate-input" data-validate="Errore formato: Sono ammessi solo caratteri e il primo carattere deve essere Maiuscolo">
						<span class="label-input100-reg">Nome</span>
						<input class="input100-reg" type="text" name="nome" value="<%=nome %>" placeholder="Nome">
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100-reg validate-input" data-validate = "Errore formato: Sono ammessi solo caratteri e il primo carattere deve essere Maiuscolo">
						<span class="label-input100-reg">Cognome</span>
						<input class="input100-reg" type="text" name="cognome" value="<%=cognome %>" placeholder="Cognome">
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100-reg validate-input" data-validate="Formato email non valido, example: ex@abc.xyz">
						<span class="label-input100-reg">Email</span>
						<input class="input100-reg" type="text" name="email" value="<%=email %>" placeholder="Indirizzo Email">
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100-reg validate-input" data-validate = "Errore formato: Sono ammessi solo caratteri e numeri">
						<span class="label-input100-reg">Username</span>
						<input class="input100-reg" name="username" value="<%=username %>" placeholder="Username">
						<span class="focus-input100"></span>
					</div>
					
					<div class="wrap-input100-reg validate-input" data-validate = "Formato Non valido: Lunghezza:(min=8,max=30) Richiesto almeno: un carattere speciale, una lettera maiuscola e sun numero">
						<span class="label-input100-reg">Password</span>
						<input class="input100-reg" type="password" name="password" value="<%=password %>" placeholder="*************">
						<span class="focus-input100"></span>
					</div>
					<div class="radioGroup-reg">
		<%
			String checked = (String) request.getAttribute("checked");
			if(checked == null)
			{
						
		%>
				<input type="radio" name="typeUser" value="Utente" checked><label>Utente</label>
				<input type="radio" name="typeUser" value="Autore"><label>Autore</label>
		<%
			}
			else
			{
				switch(checked)
				{
					case "UtenteCheck":
					{
		%>				<input type="radio" name="typeUser" value="Utente" checked><label>Utente</label>
						<input type="radio" name="typeUser" value="Autore"><label>Autore</label>
		<%				
					}break;
					
					case "AutoreCheck":
					{
		%>
						<input type="radio" name="typeUser" value="Utente"><label>Utente</label>
						<input type="radio" name="typeUser" value="Autore" checked><label>Autore</label>
		<%				
					}break;
					default: 
					{
								
		%>	
						<input type="radio" name="typeUser" value="Utente"checked><label>Utente</label>
						<input type="radio" name="typeUser" value="Autore"><label>Autore</label>
		<%
					}break;
							
				}
			}
		%>
				</div>
					<div class="container-login100-form-btn-reg">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn-reg"></div>
							<button class="login100-form-btn">
								Registrati
							</button>
						</div>

						<a href="/InfoBlog/login.jsp" class="dis-block txt3Reg hov1Reg p-r-30 p-t-10 p-b-10 p-l-30">
							Login
							<i class="fa fa-long-arrow-right m-l-5"></i>
						</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	
<!--===============================================================================================-->
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/daterangepicker/moment.min.js"></script>
	<script src="vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="javascript/registrazione.js"></script>

</body>
</html>