<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Evento</title>
<link rel="stylesheet" type="text/css" href="css/event.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="javascript/jquery-3.4.1.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
$(document).ready(function(){
	$("#eventDate").datepicker({
		  dateFormat: 'yy-mm-dd'
	});
});

</script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="containerforevent">
  <div id="newEventHolder">
    <span><i class="fa fa-plus"></i> Add New Event</span>
  </div>
  <div id="newEventForm">
  <%
  	if(request.getAttribute("erroreRegistrazione")!=null)
  	{
  %>
 	<input type="text" id="erroreRegistrazione" value="<%=request.getAttribute("erroreRegistrazione") %>" readonly="readonly" style="color:#b94a48; background:#efd5d4; border-color:#d59392; text-align: center;">
  <%
  	}
  if(request.getAttribute("errore")!=null)
  {
  %>
  <input type="text" id="erroreRegistrazione" value="<%=request.getAttribute("errore") %>" readonly="readonly" style="color:#b94a48; background:#efd5d4; border-color:#d59392; text-align: center;">
    <form method="POST" action="EventInsertServlet" enctype="application/x-www-form-urlencoded" style="display: inline;">
    <label>Event Date</label>
    <input type="text" name="dataEvento" id="eventDate" value="<%=request.getAttribute("data") %>">
    <label>Event Title</label>
    <input type="text" name="event-title" id="eventTitleInput" value="<%=request.getAttribute("titoloEvento") %>"/>
    <label>Via</label>
    <input type="text" name="via" id="via" value="<%=request.getAttribute("via") %>"/>
    <label>Citt&aacute; </label>
    <input type="text" name="citta" id="citta" value="<%=request.getAttribute("citta") %>"/>
    <label>Argomento</label>
    <textarea name="event-argument" id="eventDescriptionInput" placeholder="Argomenti Trattati"><%=request.getAttribute("argomento")%></textarea>
    <button type="submit" name="add-event" id="addEvent">Add New Event</button>
    <button type="reset" id="cancelAddEvent">Cancel</button>
  </form>
  <%}else{%>
   <form method="POST" action="EventInsertServlet" enctype="application/x-www-form-urlencoded" style="display: inline;">
    <label>Event Date</label>
    <input type="text" name="dataEvento" id="eventDate">
    <label>Event Title</label>
    <input type="text" name="event-title" id="eventTitleInput"/>
    <label>Via</label>
    <input type="text" name="via" id="via"/>
    <label>Citt&aacute; </label>
    <input type="text" name="citta" id="citta"/>
    <label>Argomento</label>
    <textarea name="event-argument" id="eventDescriptionInput" placeholder="Argomenti Trattati"></textarea>
    <button type="submit" name="add-event" id="addEvent">Add New Event</button>
    <button type="reset" id="cancelAddEvent">Cancel</button>
  </form>
  <%} %>
  </div>
</div>
</body>
</html>


