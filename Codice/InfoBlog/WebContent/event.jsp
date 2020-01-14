<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/event.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="javascript/jquery-3.4.1.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
<div class="container">
  
  <div id="newEventHolder">
    <span><i class="fa fa-plus"></i> Add New Event</span>
  </div>
  
  <div id="newEventForm">
    <label for="event-date">Event Date</label>
    <input type="text" id="eventDate">
    <label for="event-title">Event Title</label>
    <input type="text" name="event-title" id="eventTitleInput" maxlength="80"/>
    <label for="event-description">Description</label>
    <textarea name="event-descripton" id="eventDescriptionInput">Event Description</textarea>
    <button name="add-event" id="addEvent">Add New Event</button>
    <button name="cancel-add-event" id="cancelAddEvent">Cancel</button>
  </div>
</div>
</body>
</html>
<script>
var newEventHolder = document.getElementById("newEventHolder");
var eventForm = document.getElementById("newEventForm");
var eventDate = document.getElementById("eventDate");
var addEvent = document.getElementById("addEvent");
var cancel = document.getElementById("cancelAddEvent");
var upcomingEvents = document.getElementById("upcomingEvents");
var eventHolder = document.getElementById("eventHolder");
var removeEvent = document.getElementById("removeEvent");

// Show New Event form
$(newEventHolder).click(function() {
  $(eventForm).slideDown(400);
});

//Add Datepicker to Date input
$("#eventDate").datepicker();

// Close New Event form
$(cancel).click(function() {
  $(eventForm).slideUp(400);
});
</script>