<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="model.bean.Articolo"%>
<%@page import="model.bean.Commento"%>
<%@page import="model.bean.Rating"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Articoli</title>
		<link href="style.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Slabo+27px" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/AllArticle.css">
		<script src="javascript/jquery-3.4.1.min.js"></script>
	</head>
	
	<body>
	<%@ include file="header.jsp" %>
		<%
		if(request.getAttribute("Vuoto")==null)
		{
		%>
		<section>
			<h1>Featured Post</h1>
			<%
				ArrayList<Articolo> articoli=(ArrayList<Articolo>)request.getAttribute("articoli");
				ArrayList<ArrayList<Commento>>commenti=(ArrayList<ArrayList<Commento>>)request.getAttribute("commenti");
				ArrayList<ArrayList<Rating>> ratingCollettivo=(ArrayList<ArrayList<Rating>>)request.getAttribute("rating");
				if(articoli!=null && articoli.size()>0)
				{
			%>
			<div class="article-list">
				<%
					for(int i=0;i<articoli.size();i++)
					{
				%>
				<article>
					<div>
						<p style="display: inline-block;width: 210px; font-weight: bold;">Nome Articolo</p>
						<p style="display: inline-block;width: 167px; font-weight: bold;">Data pubblicazione</p>
						<p style="display: inline-block;width: 140px;margin-left: 8%; font-weight: bold;">Categoria</p>
						<p style="display: inline-block;width: 50px;margin-left: 4%;text-align: center;color: black; font-weight: bold;">Rating</p>
						<p style="display: inline-block;width: 175px;margin-left: 7%; font-weight: bold;">Numero commenti</p>
					</div>
					<div>
						<p style="display: inline-block;width: 210px;"><%=articoli.get(i).getTitolo() %></p>
						<p style="display: inline-block;width: 167px;"><%=articoli.get(i).getData() %></p>
						<p style="display: inline-block;width: 140px;margin-left: 8%;"><%=articoli.get(i).getCategoria()%></p>
						<%if(request.getAttribute("Nocommenti")==null) 
						{
						%>
						<p style="display: inline-block;width: 50px;margin-left: 4%;text-align: center;color: black;">/</p>
						<p style="display: inline-block;width: 175px;margin-left: 11%;">/</p>
						<%}
						else
						{
							if(ratingCollettivo.get(i).size()>0)
							{ 
						%>
						<p style="display: inline-block;width: 175px;margin-left: 7%;"><%=ratingCollettivo.get(i).get(0).getNumeroStelle() %></p>
						<%
						}
						else
						{
						%>
						<p style="display: inline-block;width: 175px;margin-left: 7%;">0</p>
						<%}%>
							<p style="display: inline-block;width: 50px;margin-left:-4%;text-align: center;color: black;"><%=commenti.get(i).size() %></p>
						<% }if(request.getSession().getAttribute("Moderatore")==null) 
						{%>
						<button style="border-radius: 19px; width: 90px; height: 40px; background:#1583cc; margin-left: 3%;"><a href="ViewArticleServlet?id=<%=articoli.get(i).getId()%>&Titolo=<%=articoli.get(i).getTitolo() %>" style="color: black; text-decoration: none;">Visualizza</a></button>
					
						<%}
						if(request.getSession().getAttribute("Moderatore")!=null) 
						{
						%>
						<button style="border-radius: 19px; width: 90px; height: 40px; margin-left:1%; background:#1583cc;"><a href="ViewArticleServlet?id=<%=articoli.get(i).getId() %>&Titolo=<%=articoli.get(i).getTitolo() %>" style="color: black; text-decoration: none;">Moderazione</a></button>
						<%
						} 
						if(request.getSession().getAttribute("Autore")!=null) 
						{%>
						<button style="border-radius: 19px; width: 90px; height: 40px; margin-left:1%; background:#1583cc;"><a href="ModificaArticoloControl?action=richiestaModifica&idArticolo=<%=articoli.get(i).getId()%>" style="color: black; text-decoration: none;">Modifica</a></button>
					</div>
						<%
							if(articoli.get(i).getStato().equals("Pubblicato"))	
							{
						%>
						<button style="border-radius: 19px; width: 117px; height: 21px; margin-left: 48%; background:green; margin-top: 1%;"><%=articoli.get(i).getStato() %></button>
						<%
							}
							else
								if(articoli.get(i).getStato().equals("inModerazione"))	
								{
						%>
						<button style="border-radius: 19px; width: 117px; height: 21px; margin-left: 48%; background:yellow; margin-top: 1%;"><%=articoli.get(i).getStato() %></button>
						<%
								}
								else
								if(articoli.get(i).getStato().equals("respinto"))
								{
						%>
							<button style="border-radius: 19px; width: 117px; height: 21px; margin-left: 48%; background:red; margin-top: 1%;"><%=articoli.get(i).getStato() %></button>
							<%
								}
								else
								{
							%>
							<button style="border-radius: 19px; width: 117px; height: 21px; margin-left: 48%; background:grey; margin-top: 1%;"><%=articoli.get(i).getStato() %></button>
							<%	
								}
						}
						%>
				</article>
			
			<%
			}%>
			</div>
		</section>
		<%}
			}
			else{
				%>
				<section>
				<h1>Articoli Assenti</h1>
				</section>
			<%}%>
			
</body>
</html>