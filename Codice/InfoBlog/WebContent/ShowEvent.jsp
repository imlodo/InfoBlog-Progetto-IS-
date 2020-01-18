<%@page import="java.util.ArrayList"%>
<%@ page import="model.bean.Evento" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/showAllEvent.css"> 
<title>Eventi</title>
</head>
<body>
<%@ include file="header.jsp" %>
<%
	if(request.getSession().getAttribute("Moderatore")==null)
	{
		ArrayList<Evento> eventi=(ArrayList<Evento>)request.getAttribute("eventi");
		if(eventi!=null && eventi.size()>0)
		{
			for(int i=0;i<eventi.size();i++)
			{
				System.out.println(eventi.size());
				if(i==0)
				{
%>
<div class="row" style="padding-top: 3%;padding-left: 19%;padding-right: 33%;">
  <div class="small-9 columns small-centered">
    <article class="event">
        <div class="event-date" style="width: 148px">
          <p class="event-month"><%=eventi.get(i).getData().getMonth()%></p>
          <p class="event-day"><%=eventi.get(i).getData().getDayOfMonth() %></p>
        </div>

        <div class="event-desc" style="padding-top: 4%; width:200px;">
          <h4 class="event-desc-header"><%=eventi.get(i).getNome() %></h4>
         </div>
         <div class="event-desc" style="width:170px;">
          <p class="event-desc-detail" style="padding-top: 25%; padding-left: 0%;"><span class="event-desc-time"></span><%=eventi.get(i).getAutore().getNome()+" "+eventi.get(i).getAutore().getCognome() %></p>
          </div> 
         <%
         if(request.getSession().getAttribute("Autore")!=null)
         {
         %>
          <div class="event-desc" style="padding-top: 3%; padding-left: 9%;">
         	<button style="background:#1583cc; border-radius: 16px; height: 35px; width: 100px;"><a href="EventCancelServlet?idEvento=<%=eventi.get(i).getIdEvento()%>&data=<%=eventi.get(i).getData()%>&via=<%=eventi.get(i).getVia()%>&citta=<%=eventi.get(i).getCittà()%>" style="color:black; text-decoration: none;">Elimina</a></button>
        </div>
         <div class="event-desc" style="padding-top: 3%; padding-left: 11%;">
         	<button style="background:#1583cc; border-radius: 16px; height: 35px; width: 100px;"><a href="ViewForModifyEvent?data=<%=eventi.get(i).getData()%>&via=<%=eventi.get(i).getVia()%>&citta=<%=eventi.get(i).getCittà()%>" style="color:black; text-decoration: none;">Modifica</a></button>
        </div>
        <%} %>
        <div class="event-desc" style="padding-top: 3%; padding-left: 14%;">
         	<button style="background:#1583cc; border-radius: 16px; height: 35px; width: 100px;"><a href="SingleEventShowServlet?data=<%=eventi.get(i).getData()%>&via=<%=eventi.get(i).getVia()%>&citta=<%=eventi.get(i).getCittà()%>" style="color:black; text-decoration: none;">Visualizza</a></button>
        </div>
        <input type="hidden" name="id" value="<%=eventi.get(i).getIdEvento()%>">
      </article>
	<br>
      <hr>
  </div>
  <%
 	 }
			else
			{
  %>
  <div class="row" style="padding-top: 3%;">
  <div class="small-9 columns small-centered">
    <article class="event">

        <div class="event-date" style="width: 148px">
          <p class="event-month"><%=eventi.get(i).getData().getMonth()%></p>
          <p class="event-day"><%=eventi.get(i).getData().getDayOfMonth() %></p>
        </div>

        <div class="event-desc" style="padding-top: 4%; width:200px;">
          <h4 class="event-desc-header"><%=eventi.get(i).getNome() %></h4>
         </div>
         <div class="event-desc" style="padding-top: 4%; width:170px;">
          <p class="event-desc-detail" style="padding-left: 0%;"><span class="event-desc-time"></span><%=eventi.get(i).getAutore().getNome()+" "+eventi.get(i).getAutore().getCognome() %></p>
          </div>
         <%
         if(request.getSession().getAttribute("Autore")!=null)
         {
         %>
          <div class="event-desc" style="padding-top: 3%; padding-left: 9%;">
         	<button style="background:#1583cc; border-radius: 16px; height: 35px; width: 100px;"><a href="EventCancelServlet?idEvento=<%=eventi.get(i).getIdEvento()%>&data=<%=eventi.get(i).getData()%>&via=<%=eventi.get(i).getVia()%>&citta=<%=eventi.get(i).getCittà()%>" style="color:black; text-decoration: none;">Elimina</a></button>
         </div>
         <div class="event-desc" style="padding-top: 3%; padding-left: 11%;">
         	<button style="background:#1583cc; border-radius: 16px; height: 35px; width: 100px;"><a href="ViewForModifyEvent?data=<%=eventi.get(i).getData()%>&via=<%=eventi.get(i).getVia()%>&citta=<%=eventi.get(i).getCittà()%>" style="color:black; text-decoration: none;">Modifica</a></button>
        </div>
        <%} %>
		 <div class="event-desc" style="padding-top: 3%; padding-left: 14%;">
         	<button style="background:#1583cc; border-radius: 16px; height: 35px; width: 100px;"><a href="SingleEventShowServlet?data=<%=eventi.get(i).getData()%>&via=<%=eventi.get(i).getVia()%>&citta=<%=eventi.get(i).getCittà()%>" style="color:black; text-decoration: none;">Visualizza</a></button>
        </div>
      </article>
	<br>
      <hr>
  </div>
</div>
<%
				}
			}
		}
	}
	else
	{
		response.sendRedirect("notfound.jsp");
	}
%>
</body>
</html>