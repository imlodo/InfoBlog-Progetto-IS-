<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Contatta</title>
<script src="javascript/jquery-3.4.1.min.js"></script>
<script>
	function validaContenuto() {
		var letters= /[a-zA-Z0-9 #&<>~;$^%{}?][^~^]{1,700}$/;
		
		if (!$("#contenuto").val().match(letters)) 
		{
			$("#errore").css("display","none")
			$(".write").css("border-color","red")
			$("#erroreJs").css("display","inline");
			return false;
		} 
		else
		{
			return true;
		}
	}

</script>
<style>
@import url(https://fonts.googleapis.com/css?family=Montserrat:400,700);

.fo { max-width:420px; margin:50px auto; }

.feedback-input {
  color:black;
  font-family: Helvetica, Arial, sans-serif;
  font-weight:500;
  font-size: 18px;
  border-radius: 5px;
  line-height: 22px;
  background-color: transparent;
  border:2px solid black;
  transition: all 0.3s;
  padding: 13px;
  margin-bottom: 15px;
  width:100%;
  box-sizing: border-box;
  outline:0;
}

.feedback-input:focus { border:2px solid #CC4949; }

textarea {
  height: 150px;
  line-height: 150%;
  resize:vertical;
}

[type="submit"] {
  font-family: 'Montserrat', Arial, Helvetica, sans-serif;
  width: 100%;
  background:#CC6666;
  border-radius:5px;
  border:0;
  cursor:pointer;
  color:white;
  font-size:24px;
  padding-top:10px;
  padding-bottom:10px;
  transition: all 0.3s;
  margin-top:-4px;
  font-weight:700;
}
[type="submit"]:hover { background:#CC4949; }
</style>
</head>
<body>
 <%@ include file="header.jsp" %>
<form class="fo" action="InviaMessaggioControl" method="Post" enctype="application/x-www-form-urlencoded" onsubmit="if(validaContenuto()==false){return false;}">   
 <%if(request.getAttribute("ErroreFormato")!=null){
 %> 
 <input name="errore" type="text" class="feedback-input" style="background: red;" value="<%=request.getAttribute("ErroreFormato")%>"/> 
 <%} %>  
  <input name="autore" type="text" class="feedback-input" placeholder="Destinatario" readonly value="<%=request.getAttribute("emailAutore")%>"/>   
  <input name="email" type="text" class="feedback-input" placeholder="Mittente" readonly value="<%=request.getSession().getAttribute("Utente") %>"/>
  <%if(request.getAttribute("ErroreFormato")!=null){%>
  <textarea id="contenuto" name="contenuto" class="feedback-input" placeholder="Comment"><%=request.getAttribute("contenuto")%></textarea>
  <%}else{ %>
  <textarea id="contenuto" name="contenuto" class="feedback-input" placeholder="Comment"></textarea>
  <%} %>
   <input id="erroreJs" type="text" class="feedback-input" style="background: red; display: none;" value="Non rispetta il formato: Lunghezza min:2 caratteri"/> 
  <input type="submit" value="SUBMIT" />
</form>
</body>
</html>

