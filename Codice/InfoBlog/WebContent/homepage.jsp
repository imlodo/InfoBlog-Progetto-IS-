<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="model.bean.Articolo" %>
<%@page import="utils.Utils"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/Homepage.css">
<script type="text/javascript" src="javascript/jquery-3.4.1.min.js"></script>
<title>InfoBlog</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<%
		ArrayList<Articolo> articoli=(ArrayList<Articolo>)request.getAttribute("articoli");
		String emailSession = Utils.checkLogin(request.getSession(), request.getCookies());
		if(emailSession!=null && emailSession.subSequence(0, 1).equals("u")){
			  if(articoli == null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("HomepageArticoliControl");
           		dispatcher.forward(request,response);  
           		return;
		  		}
       	}
	%>
		<div class='containerArticoli'>
	<%
		if(articoli != null)
		{
			int max = 30;
			for(Articolo a:articoli)
			{
				if(max == 30)
   					break;
   	%>			
   				<div class='subContainer'>
					<table>
						<thead>
							<tr>
								<th scope='col'>Titolo</th>
								<th scope='col'>Contenuto</th>
								<th scope='col'>Categoria</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td data-label='Titolo'><%=a.getTitolo() %></td>
								<td data-label='Contenuto'><%=a.getContenuto().substring(1,50) %>...</td>
								<td data-label='Categoria'><%=a.getCategoria() %></td>
							</tr>
							</tbody>
							</table>
							<p class='link'><a href='/InfoBlog/ViewArticleServlet?id=<%=a.getId()%>&Titolo=<%=a.getTitolo()%>'>Visualizza Articolo</a></p>
							</div>
   	<%
			}
		}
	%>
		</div>
</html>