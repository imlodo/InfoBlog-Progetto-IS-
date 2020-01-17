<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="model.bean.Articolo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Articolo</title>
<link rel="stylesheet" type="text/css" href="css/article.css">
</head>
<body>
<%@ include file="header.jsp" %>
<%
Articolo articolo=(Articolo)request.getAttribute("articolo");

if(articolo!=null)
{
%>
<div class="containerArticle">
  <h1 class="main-title">@InfoBlog</h1>
  <h3 class="article-title"><%=articolo.getTitolo()%></h3>
  <p class="article-author">By <span class="uppercase"><%=articolo.getAutore().getNome()+" "+articolo.getAutore().getCognome()%></p>
  <textarea class="paragraph-text"><%=articolo.getContenuto() %></textarea>
  <button><a href="ScaricaAllegatoControl">Scarica file</a></button>
</div>
<%} %>
</body>
</html>