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
  <textarea class="paragraph-text" readonly><%=articolo.getContenuto() %></textarea>
  <button style="border-radius: 19px; width: 90px; height: 40px; background:#1583cc;"><a style="color:black; text-decoration:none;" href="ScaricaAllegatoControl?id=<%=articolo.getId()%>">Scarica file</a></button>
  <%if(request.getSession().getAttribute("Moderatore")!=null)
	  {
	%>
	  </div>
	 <button style="border-radius: 19px; width: 90px; height: 40px; background:green; margin-left: 41%; margin-top: 1%; margin-right: 1%;"><a style="color:black; text-decoration:none;" href="Moderazione?azione=accetta&idArticolo=<%=articolo.getId()%>">Accetta</a></button>
	 <button style="border-radius: 19px; width: 90px; height: 40px; background:red;"><a style="color:black; text-decoration:none;" href="Moderazione?azione=rifiuta&idArticolo=<%=articolo.getId()%>">Rifiuta</a></button>
	  <div>
	<%} %>
</div>
<%}
else
	response.sendRedirect("notfound.jsp");
%>
</body>
</html>