<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="model.bean.Articolo"%>
 <%@ page import="model.bean.Commento"%>
  <%@ page import="model.bean.Rating"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Articolo</title>
<link rel="stylesheet" type="text/css" href="css/article.css">
<link rel="stylesheet" type="text/css" href="css/commenti.css">
<link rel="stylesheet" type="text/css" href="css/insertCommento.css">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/rating.css">
<script src="javascript/jquery-3.4.1.min.js"></script>
</head>
<script>
function validaContenuto() 
{
	var letters= /^[A-Za-z0-9]{8,300}$/;
	
	if (!$("#message").val().match(letters)) 
	{
		$("#message").css("border-color","red")
		$("#erroreJs").css("display","inline");
		$("#erroreJs").css("border-color","red");
		$("#erroreJs").css("background","#ff8989");
		return false;
	} 
	else
	{
		return true;
	}
}
</script>
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
  <p class="article-author">By <span class="uppercase"><%=articolo.getAutore().getNome()+" "+articolo.getAutore().getCognome()%></span></p>
  <p class="paragraph-text" style="overflow-wrap: anywhere;"><%=articolo.getContenuto() %></p>
  <button style="border-radius: 19px; width: 90px; height: 40px; background:#1583cc;"><a style="color:black; text-decoration:none;" href="ScaricaAllegatoControl?id=<%=articolo.getId()%>">Scarica file</a></button>
  <%if(request.getSession().getAttribute("Moderatore")!=null)
	  {
	%>
	  </div>
	 <button style="border-radius: 19px; width: 90px; height: 40px; background:green; margin-left: 45%; margin-top: 1%; margin-right: 1%;"><a style="color:black; text-decoration:none;" href="Moderazione?azione=accetta&idArticolo=<%=articolo.getId()%>">Accetta</a></button>
	 <button style="border-radius: 19px; width: 90px; height: 40px; background:red;"><a style="color:black; text-decoration:none;" href="Moderazione?azione=rifiuta&idArticolo=<%=articolo.getId()%>">Rifiuta</a></button>
	  <div>
	<%} %>
</div>
<%
	if(request.getSession().getAttribute("Autore")!=null)
	{%>
	<div style="margin-top: 1%; height: 100px;">
		<span style="margin-left: 30%;">Rating: <%if(request.getAttribute("VotoRatingArticolo")!=null)  
      	{   
        %>
      <button style="margin-left: 1%; height: 55%; width: 3%; border-radius: 47%;" disabled>0.0</button>
      <%}else
      {%>
      <button style="margin-left: 1%; height: 55%; width: 3%; border-radius: 47%;" disabled><%=((ArrayList<Rating>)request.getAttribute("RatingArticolo")).get(0).getNumeroStelle()%></button>
      <%} %> </span>
	</div>
<%	} %>
<form class="rating-stars" action="RatingControl?idArticolo=<%=articolo.getId()%>" method="post">
  
    <div class="rating-stars__wrapper" style="margin-right: 36%; height: 80px;">
      Rating
      <%if(request.getAttribute("VotoRatingArticolo")!=null)  
      {   
        %>
      <button style="margin-left: 9%; height: 55%; width: 6%; border-radius: 47%;" disabled>0.0</button>
      <%}else
      {%>
      <button style="margin-left: 9%; height: 55%; width: 6%; border-radius: 47%;" disabled><%=((ArrayList<Rating>)request.getAttribute("RatingArticolo")).get(0).getNumeroStelle()%></button>
      <%}
      	if(request.getSession().getAttribute("Utente")!=null){
      %>
       <input type="submit" value="Rating"/>
       <%}
       if(request.getSession().getAttribute("Utente")!=null)
       {
       	if(request.getAttribute("VotoRatingUtente")==null)
       	{
       		Rating rt=(Rating)request.getAttribute("ratingUtente");
       %>
      <input id="rating-5" name="numeroStelle" type="radio" value="5" <%if(rt.getNumeroStelle()==5){%> checked<%} %>/>
      <label for="rating-5" data-value="5">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <input id="rating-4" name="numeroStelle" type="radio" value="4"  <%if(rt.getNumeroStelle()==4){%> checked<%} %>/>
      <label for="rating-4" data-value="4">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <input id="rating-3" name="numeroStelle" type="radio" value="3"  <%if(rt.getNumeroStelle()==3){%> checked<%} %>/>
      <label for="rating-3" data-value="3">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <input id="rating-2" name="numeroStelle" type="radio" value="2"  <%if(rt.getNumeroStelle()==2){%> checked<%} %>/>
      <label for="rating-2" data-value="2">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <input id="rating-1" name="numeroStelle" type="radio" value="1"  <%if(rt.getNumeroStelle()==1){%> checked<%} %>/>
      <label for="rating-1" data-value="1">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <%}
       else
       {%>
        <input id="rating-5" name="numeroStelle" type="radio" value="5"/>
      <label for="rating-5" data-value="5">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <input id="rating-4" name="numeroStelle" type="radio" value="4"/>
      <label for="rating-4" data-value="4">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <input id="rating-3" name="numeroStelle" type="radio" value="3"/>
      <label for="rating-3" data-value="3">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <input id="rating-2" name="numeroStelle" type="radio" value="2"/>
      <label for="rating-2" data-value="2">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
      <input id="rating-1" name="numeroStelle" type="radio" value="1" checked/>
      <label for="rating-1" data-value="1">
        <span class="rating-stars__star">
          <i class="fa fa-star-o"></i>
          <i class="fa fa-star"></i>
        </span>
      </label>
       <%} %>
    </div>     
</form>
<%} %>
<% if(request.getSession().getAttribute("Utente")!=null){%>
<label><h2 style="margin-left: 28%;">Lascia un commento:</h2></label>
<form class="dot" id="enquiry" action="CommentoControl" onsubmit="if(validaContenuto()==false){return false;}">
	<input type="hidden" name="idArticolo" value="<%=articolo.getId()%>">
  <textarea maxlength="300" name="commento" id="message" placeholder="Add your comment!"></textarea>
  <input type="text" style="display: none; height: 35px; width: 281px;" id="erroreJs" value="Lunghezza min.8 caratteri,no caratteri speciali">
  <input type="submit" value="Add Comment">
</form>

<%} %>
<%if(request.getSession().getAttribute("Moderatore")==null)
	{
		if(request.getAttribute("Vuoto")==null)
		{
	%>
	<div class="comments-container">
		<h1>Commenti</h1>
		<ul id="comments-list" class="comments-list">
		<%
			ArrayList<Commento> commenti=(ArrayList<Commento>)request.getAttribute("commenti");
			for(int i=0;i<commenti.size();i++)
			{
		%>
	
			<li>
				<div class="comment-main-level">
					<div class="comment-avatar"><img src="http://i9.photobucket.com/albums/a88/creaticode/avatar_1_zps8e1c80cd.jpg" alt=""></div>
					<div class="comment-box">
						<div class="comment-head">
							<h6 class="comment-name by-author"><%=commenti.get(i).getUtente().getCognome()+"  "+commenti.get(i).getUtente().getNome() %></h6>
							<i class="fa fa-reply"></i>
							<i class="fa fa-heart"></i>
						</div>
						<div class="comment-content">
							<%=commenti.get(i).getContenuto() %>
						</div>
					</div>
				</div>
			</li>
<%
			}
			%>
			</ul>
			</div>			
		<%}
		else
		{
%>
<div class="comments-container">
		<h1>Non ci sono commenti</h1>
</div>
<%
		}
	}
}
else
	response.sendRedirect("notfound.jsp");
%>
</body>
</html>


