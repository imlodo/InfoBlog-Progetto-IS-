<%@page import="utils.Utils"%>
<%@page import="com.mysql.cj.conf.ConnectionUrlParser.Pair"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="css/Homepage.css">
	<script type="text/javascript" src="javascript/jquery-3.4.1.min.js"></script>
	<title>InfoBlog</title>
</head>
<body>
	<div id="navigazione">
		<img id="logo" src="icone/logo.svg" onclick="location.href='/InfoBlog/homepage.jsp'">
		<div id="subNav">
			<%
				String email = Utils.checkLogin(session, request.getCookies());
				if(email != null)
				{
					Cookie[] cookies = request.getCookies();
					String username = "";
					if(cookies != null)
					{
						for(Cookie cookie : cookies)
						{
							if(cookie.getName().equals("Username"))
							{
								username = cookie.getValue();
							}
						}
					}
					if(email.substring(0, 1).equals("u"))
					{
			%>
					<label>Benvenuto, utente <b><%=username%></b></label>
					<button class='buttonNav' onclick="location.href='/InfoBlog/LogoutControl'">Logout</button>
			<%
					}
					if(email.substring(0, 1).equals("a"))
					{
			%>
					<label>Benvenuto, autore <b><%=username%></b></label>
					<button class='buttonNav' onclick="location.href='/InfoBlog/LogoutControl'">Logout</button>
			<%
					}
					if(email.substring(0, 1).equals("m"))
					{
			%>
					<label>Benvenuto, moderatore <b><%=username%></b></label>
					<button class='buttonNav' onclick="location.href='/InfoBlog/LogoutControl'">Logout</button>
			<%
						
					}
				}
				else
				{
			%>
					<label>Non risulti loggato, effettua il login.</label>
					<button class='buttonNav' onclick="location.href='/InfoBlog/login.jsp'">Login</button>
					<button class='buttonNav' onclick="location.href='/InfoBlog/registrazione.jsp'">Registrati</button>
			<%		
				}
			%>
		</div>
	</div>
</body>
</html>