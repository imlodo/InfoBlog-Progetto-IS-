<%@page import="utils.Utils"%>
<%@page import="com.mysql.cj.conf.ConnectionUrlParser.Pair"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <link rel="stylesheet" href="css/Header.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/notifiche.css">
    <script type="text/javascript" src="javascript/notifiche.js"></script>
    <%
    	String email = Utils.checkLogin(session, request.getCookies());
    	Cookie[] cookies = request.getCookies();
		String username = "";
		if(email!=null)
		{
	%>
	<script>
			email='<%=email.substring(1)%>';
	</script>
	<%

		}
	%>
	<!-- Da qui inizia la navigation -->
	<div class="container">
		<div class="fixLogo"><img id="logo" src="icone/logo.svg" onclick="location.href='/InfoBlog/homepage.jsp'"></div>
	<%
	if(email != null)
	{
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
	%>
	<%
		if(email.substring(0, 1).equals("u"))
		{
	%>		
			<label class="item">Benvenuto, utente <b><%=username%></b></label>
			<button class="itemButton" onclick="location.href='/InfoBlog/LogoutControl'">Logout</button>
	<%
		}
		if(email.substring(0, 1).equals("a"))
		{
	%>
		<label class="item">Benvenuto, autore <b><%=username%></b></label>
		<button class="itemButton" onclick="location.href='/InfoBlog/LogoutControl'">Logout</button>
	<%
		}
		if(email.substring(0, 1).equals("m"))
		{
	%>
		<label class="item">Benvenuto, moderatore <b><%=username%></b></label>
		<button class="itemButton" onclick="location.href='/InfoBlog/LogoutControl'">Logout</button>
	<%
						
		}
	}
	else
	{
	%>
		<label class="item">Non risulti loggato, effettua il login.</label>
		<button class="itemButton" onclick="location.href='/InfoBlog/login.jsp'">Login</button>
		<button class="itemButton" onclick="location.href='/InfoBlog/registrazione.jsp'">Registrati</button>
	<%		
	}
	%>
	</div>
	
	<!-- Dai qui inizia il menu -->
	<%
		if(email != null)
		{
			if(email.substring(0, 1).equals("u"))
			{
	%>
			<div class="menuBarUtente">
  			<a href="homepage.jsp" class="itemMenuUtente">Homepage</a>
  			<a href="esplora.jsp" class="itemMenuUtente">Esplora</a>
  			<a href="profile.jsp" class="itemMenuUtente">AreaPersonale</a>
  			<form class = "formRicercaUtente" action="RicercaControl" method="post">
  				<input type="text" class="searchBarUtente" placeholder="Search..">
  				<select class="selectBarUtente">
  					<option value="autore">Autore</option>
  					<option value="articolo">Articolo</option>
  				</select>
  				<button type="submit" class="searchButtonUtente"><i class="fa fa-search"></i></button>
  			</form>
  			<a href="#" class="itemMenu"><img class="messageIcon" src="icone/iconaNessunMessaggio.svg" onclick=""></a>
			</div>
	<%
			}
			if(email.substring(0, 1).equals("a"))
			{
	%>
			<div class="menuBarAutore">
  			<a href="homepage.jsp" class="itemMenuAutore">Homepage</a>
  			<a href="esplora.jsp" class="itemMenuAutore">Esplora</a>
  			<a href="profile.jsp" class="itemMenuAutore">AreaPersonale</a>
  			<a href="areapubblicazioni.jsp" class="itemMenuAutore">AreaPubblicazioni</a>
  			<a href="richiestapubblicazione.jsp" class="itemMenuAutore">PubblicaArticolo</a>
  			<a href="organizzaevento.jsp" class="itemMenuAutore">OrganizzaEvento</a>
  			<a href="#" class="itemMenuAutoreIcon"><img class="messageIconAutore" src="icone/iconaNessunMessaggio.svg" onclick=""></a>
  			<a href="#" class="itemMenuAutoreIcon" id="itemNotifiche">
  				<img class="notificaIconAutore" src="icone/iconNotifica.svg">
  				<label class="numeroNotifiche">0</label>
  			</a>
			</div>
			<!-- <form class = "formRicercaAutore" action="RicercaControl" method="post">
  				<input type="text" class="searchBarAutore" placeholder="Search..">
  				<select class="selectBarAutore">
  					<option value="autore">Autore</option>
  					<option value="articolo">Articolo</option>
  				</select>
  				<button type="submit" class="searchButtonAutore"><i class="fa fa-search"></i></button>
  			</form> -->
	<%
			}
			if(email.substring(0, 1).equals("m"))
			{
	%>
			<div class="menuBarModeratore">
  			<a href="homepage.jsp" class="itemMenuModeratore">Homepage</a>
  			<a href="profile.jsp" class="itemMenuModeratore">AreaPersonale</a>
  			<a href="modpanel.jsp" class="itemMenuModeratore">PannelloModerazione</a>
  			<a href="areamoderapubblicazione.jsp" class="itemMenuModeratore">SezionePubblicazioni</a>
  			<a href="#" class="itemMenuModeratoreIcon">
  				<img class="notificaIconModeratore" src="icone/iconNotifica.svg" onclick="">
  				<label class="numeroNotifiche">0</label>
  			</a>
			</div>
			<!-- <form class = "formRicercaModeratore" action="RicercaControl" method="post">
  				<input type="text" class="searchBarModeratore" placeholder="Search..">
  				<select class="selectBarModeratore">
  					<option value="autore">Autore</option>
  					<option value="articolo">Articolo</option>
  				</select>
  				<button type="submit" class="searchButtonModeratore"><i class="fa fa-search"></i></button>
  			</form> -->
	<%
						
			}
		}
		else
		{
	%>
			<div class="menuBar">
  			<a href="homepage.jsp" class="itemMenu">Homepage</a>
  			<form class = "formRicercaGuest" action="RicercaControl" method="post">
  				<input type="text" class="searchBarGuest" placeholder="Search..">
  				<select class="selectBar">
  					<option value="autore">Autore</option>
  					<option value="articolo">Articolo</option>
  				</select>
  				<button type="submit" class="searchButtonGuest"><i class="fa fa-search"></i></button>
  			</form>
			</div>
	<%		
		}
	%>
