<%@ page import="model.bean.Evento" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/event.css">
<meta charset="ISO-8859-1">
<title>Evento</title>
</head>
<body>
<%@ include file="header.jsp" %>
<%
	Evento event=(Evento)request.getAttribute("evento");
%>
<div class="containerforevent">
 <div id="newEventFormVisual">
    <label>Event Date</label>
    <input type="text" name="dataEvento" id="eventDate" value="<%=event.getData()%>" readonly="readonly">
    <label>Event Title</label>
    <input type="text" name="event-title" id="eventTitleInput" value="<%=event.getNome()%>" readonly="readonly"/>
    <label>Via</label>
    <input type="text" name="via" id="via" value="<%=event.getVia()%>" readonly="readonly"/>
    <label>Citt&aacute; </label>
    <input type="text" name="citta" id="citta" value="<%=event.getCittà()%>" readonly="readonly"/>
    <label>Argomento</label>
    <textarea name="event-argument" id="eventDescriptionInput" readonly="readonly"><%=event.getArgomento()%></textarea>
    <%
    if(request.getSession().getAttribute("Autore")==null) 
    {
    %>
    <label>Autore</label>
    <input type="text" name="autore" id="autore" value="<%=event.getAutore().getNome()+" "+event.getAutore().getCognome()%>" readonly="readonly"/>
	<%
	} 
	%>
	</div>
  </div>
</body>
</html>