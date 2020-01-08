<%@page import="storage.DriverManagerConnectionPool"%>
<%@page import="model.manager.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<meta charset="UTF-8">
	<title> LOGIN UTENTE</title>
	<link rel="stylesheet" href="css/Login.css">
	<script type="text/javascript" src="javascript/jquery-3.4.1.min.js"></script>
	</head>
<body>
	<form name="formLogin" action="LoginControl" method="post">
		<a href="homepage.jsp">
			<img id="logo" src="icone/logo.svg">
		</a>
		<br>
		<label>Login Panel</label>
		<%
			String utenteLog = (String) request.getAttribute("UtenteLog");
			String autoreLog = (String) request.getAttribute("AutoreLog");
			String moderatoreLog = (String) request.getAttribute("ModeratoreLog");
			if(utenteLog != null || autoreLog != null || moderatoreLog != null)
			{
				String email = (utenteLog != null) ? utenteLog : ((autoreLog != null) ? autoreLog : moderatoreLog);
		%>
				<br>
				<label class="errorLabel">Sei già loggato, la tua email è: <%=email%></label>	
		<%
			}
			else
			{
				String error = (String) request.getAttribute("errore");
				if(error != null)
				{
					if(error.equals("DATI_ERRATI"))
					{
		%>
						<br>
						<label class="errorLabel">Email o password errata, riprova!</label>
		<%
					}
					
				}
			}
		%>
		<br>
		<input type='text' name='email' placeholder="Email" value='' autocomplete='off' required>
		<br>
		<input type='password' name='password' placeholder="Password" required>
		<br> 
		<%
			String checked = (String) request.getAttribute("checked");
			if(checked == null)
			{
						
		%>
			<input type="radio" name="typeUser" value="Utente" checked>Utente
			<input type="radio" name="typeUser" value="Autore">Autore
			<input type="radio" name="typeUser" value="Moderatore">Moderatore
		<%
			}
			else
			{
				switch(checked)
				{
					case "UtenteCheck":
					{
		%>
						<input type="radio" name="typeUser" value="Utente" checked>Utente
						<input type="radio" name="typeUser" value="Autore">Autore
						<input type="radio" name="typeUser" value="Moderatore">Moderatore	
		<%				
					}break;
							
					case "AutoreCheck":
					{
		%>
						<input type="radio" name="typeUser" value="Utente">Utente
						<input type="radio" name="typeUser" value="Autore" checked>Autore
						<input type="radio" name="typeUser" value="Moderatore">Moderatore
		<%				
					}break;
							
					case "ModeratoreCheck":
					{
		%>
						<input type="radio" name="typeUser" value="Utente">Utente
						<input type="radio" name="typeUser" value="Autore">Autore
						<input type="radio" name="typeUser" value="Moderatore" checked>Moderatore
		<% 
					}break;
							
					default: 
					{
								
		%>
						<input type="radio" name="typeUser" value="Utente" checked>Utente
						<input type="radio" name="typeUser" value="Autore">Autore
						<input type="radio" name="typeUser" value="Moderatore">Moderatore
		<%
					}break;
							
				}
			}
		%>
		<br>
		<input type='submit' value='Accedi'>
	</form>
</body>
</html>