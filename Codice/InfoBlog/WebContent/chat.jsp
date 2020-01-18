<%@page import="java.time.LocalDateTime"%>
<%@page import="model.manager.AutoreManagement"%>
<%@page import="storage.DriverManagerConnectionPool"%>
<%@page import="model.bean.Messagio"%>
<%@page import="model.manager.ConversazioniManagment"%>
<%@page import="model.manager.MessaggioManagment"%>
<%@page import="model.bean.Conversazione"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/chat.css">
<script src="javascript/jquery-3.4.1.min.js"></script>
</head>
<body>
<%
if(request.getSession().getAttribute("Moderatore")==null)
{
	ArrayList<String> contatti=(ArrayList<String>)request.getAttribute("contatti");
	ArrayList<Conversazione> conv=(ArrayList<Conversazione>)request.getSession().getAttribute("chat");
	ArrayList<String> idForClass=new ArrayList<String>();
	String proprietarioChat="";
	
	if(request.getSession().getAttribute("Utente")!=null)
		proprietarioChat=(String)request.getSession().getAttribute("Utente");
	else
		proprietarioChat=(String)request.getSession().getAttribute("Autore");
	
	for(int i=0;i<contatti.size();i++)
	{
		String a=contatti.get(i);
		a=a.replace('.','T');
		a=a.replace('@','T');
		idForClass.add(a);
	}
%>

<%
if(request.getAttribute("Vuoto")==null && contatti!=null && contatti.size()>0)
{	
%>
<input type="hidden" id="proprietario" value="<%=proprietarioChat%>">
<input type="hidden" id="primo" value="<%=idForClass.get(0)%>">
<div class="wrapper">
    <div class="container">
        <div class="left">
            <div class="top">
                <input type="text" placeholder="Search" />
                <a href="javascript:;" class="search"></a>
            </div>
            <ul class="people">
            <%
            	for(int i=0;i<contatti.size();i++)
            	{
            %>
                <li class="person" data-chat=<%=idForClass.get(i)%>>
                    <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/382994/thomas.jpg" alt="" />
                    <span class="name"><%=contatti.get(i) %></span>
                    <% 
                    	int ore=conv.get(i).getMessaggi().get(conv.get(i).getMessaggi().size()-1).getData().getHour();
                    	int minuti=conv.get(i).getMessaggi().get(conv.get(i).getMessaggi().size()-1).getData().getMinute();
                    	String oraEsatta=String.valueOf(ore);
                    	String minutiEsatti=String.valueOf(minuti);
                    	if(ore<10)
                    		oraEsatta="0"+oraEsatta;
                    	if(minuti<10)
                    		minutiEsatti="0"+minutiEsatti;
                    %>
                    <span class="time"><%=oraEsatta+":"+minutiEsatti%></span>
                </li>
              <%} %>
            </ul>
        </div>
        <div class="right">
            <div class="top"><span>To: <span class="name"><%=contatti.get(0) %></span></span></div>
             <%
            	for(int t=0;t<conv.size();t++)
            	{
           		 %>
            <div class="chat" data-chat="<%=idForClass.get(t)%>">
             <div class="conversation-start">
                  <span><%=conv.get(t).getMessaggi().get(0).getData().getMonthValue()+"-"+conv.get(t).getMessaggi().get(0).getData().getDayOfYear() %></span>
             </div>
               <%
               	String orarioPrecedente="";
               for(int j=0;j<conv.get(t).getMessaggi().size();j++)
            		{
            	   		if(conv.get(t).getMessaggi().get(j).getMittente().equals(proprietarioChat))
            	   		{
            	%>
                <div class="bubble me">
                    <%=conv.get(t).getMessaggi().get(j).getContenuto() %>
                </div>
                <%}
            	   		else
            	   		{
            	 %>
            	 <div class="bubble you">
                    <%=conv.get(t).getMessaggi().get(j).getContenuto() %>
                </div>
            	<%
            			}
            	   	}
                %>
            	 <input type="hidden" id="lastMex" value="<%=conv.get(t).getMessaggi().get(conv.get(t).getMessaggi().size()-1).getMittente()%>">
            	 <input type="hidden" id="lastId" value="<%=conv.get(t).getMessaggi().get(conv.get(t).getMessaggi().size()-1).getIdMessaggio()%>">
            	 <input type="hidden" id="state" value="<%=conv.get(t).getMessaggi().get(conv.get(t).getMessaggi().size()-1).getStato()%>">
            	 <input type="hidden" id="destinatarioConversazione" value="<%=contatti.get(t)%>">
            </div>
            <%}%>
            <div class="write">
	                <a href="javascript:;" class="write-link attach"></a>
	                <input type="text" id="testoDelMessaggio" autocomplete="none">
	            	<button type="submit" name="learn" value="myimage" onclick="richiestaAjax()" style="height: 40px;width: 40px;background: white;border: none;">
  						 <img src="icone/image.PNG" style="height: 40px;width: 42px;">
					</button>
					<input type="text" id="errore" value="Formato Errato" style="display: none; height: 13px; text-align: center; margin-top: 2%; display: none;">
            </div>
        </div>
    </div>
</div>
<%}
	else
	{
		if(request.getSession().getAttribute("Utente")!=null)
		{
	%>
		<div class="zeroConversazioni">
			<span  class="zeroConversazionespan">Non ci sono conversazioni.Utilizza la funzione contatta</span> 
		</div>
	<%	}
		else
		{%>
		<div class="zeroConversazioni">
			<span class="zeroConversazionespan">Non ci sono conversazioni.</span> 
		</div>
		<%}
		}
	}
	else
	{
		response.sendRedirect("notfound.jsp");
	}%>
		<button style=" margin-left: 47%;color: black; background:gray; border-radius: 35px;"><a href="homepage.jsp" style="color: black; text-decoration: none;">Go to Home</a></button>
</body>
<script type="text/javascript">

if(document.getElementById('primo')!=null)
{
	var a=document.getElementById('primo').value
	document.querySelector('.chat[data-chat='+a+']').classList.add('active-chat')
	document.querySelector('.person[data-chat='+a+']').classList.add('active')
	
	
	let friends = {
	    list: document.querySelector('ul.people'),
	    all: document.querySelectorAll('.left .person'),
	    name: ''
	  },
	  chat = {
	    container: document.querySelector('.container .right'),
	    current: null,
	    person: null,
	    name: document.querySelector('.container .right .top .name')
	  }
	
	friends.all.forEach(f => {
	  f.addEventListener('mousedown', () => {
	    f.classList.contains('active') || setAciveChat(f)
	  })
	});
	
	function setAciveChat(f) {
	  friends.list.querySelector('.active').classList.remove('active')
	  f.classList.add('active')
	  chat.current = chat.container.querySelector('.active-chat')
	  chat.person = f.getAttribute('data-chat')
	  chat.current.classList.remove('active-chat')
	  chat.container.querySelector('[data-chat="' + chat.person + '"]').classList.add('active-chat')
	  friends.name = f.querySelector('.name').innerText
	  chat.name.innerHTML = friends.name
	
	  var t=chat.container.querySelector('[data-chat="' + chat.person + '"]').children.length
	  if($("#proprietario").val()!=chat.container.querySelector('[data-chat="' + chat.person + '"]').children[t-4].value && chat.container.querySelector('[data-chat="' + chat.person + '"]').children[t-2].value!="letto")
	  {
		var p=chat.container.querySelector('[data-chat="' + chat.person + '"]').children[t-3].value;
		$.ajax({
			url: 'CambiaStato',
			type: 'GET',
			dataType: 'json',
			data: {
				id: p,
			},
			success: function (data)
			{
				if(data=="cambiamentoEffettuato")
					chat.container.querySelector('[data-chat="' + chat.person + '"]').children[t-2].value="letto";
			}
		});
	  }
	}
	
	function richiestaAjax()
	{
		var testo=$("#testoDelMessaggio").val()
		var proprietarioChat=$("#proprietario").val()
		var destinatario=$(".active-chat").children("#destinatarioConversazione").val()
		
		var letters= /[a-zA-Z0-9 #&<>~;$^%{}?][^~^]{1,700}$/;
		if (!testo.match(letters)) 
		{
			$(".write").css("border-color","red")
			$("#errore").css("display","inline");
			
		} 
		else {
			$("#errore").css("display","none");
			$(".write").css("border","1px solid var(--light)")
			$("#testoDelMessaggio").val("")
			
			
		$.ajax({
			url: 'UpdateMessage',
			type: 'GET',
			dataType: 'json',
			data: {
				text: testo,
				prop: proprietarioChat,
				dex: destinatario,
			},
			success: function (data)
			{
				for(i=0;i<$(".active-chat").children().length;)
					$(".active-chat").children()[i].remove();
				
				//var orarioPrecedente="";
				for(i=0;i<data.length;i++)
				{
					//var combinazione=data[i]["data"]["date"]["month"]+"-"+data[i]["data"]["date"]["day"]
					
					//if(orarioPrecedente!=combinazione)
					//{
						//orarioPrecedente=combinazione
						//$(".active-chat").append("<div class=\"conversation-start\"><span>"+combinazione+"</span></div>")
					//}
					if(data[i]["mittente"]==proprietarioChat)
						$(".active-chat").append("<div class=\"bubble me\">"+data[i]["contenuto"]+"</div>")
					else
						$(".active-chat").append("<div class=\"bubble you\">"+data[i]["contenuto"]+"</div>")
				}
				$(".active-chat").append("<input type=\"hidden\" id=\"lastMex\" value=\""+data[data.length-1]["mittente"]+"\">")
				$(".active-chat").append("<input type=\"hidden\" id=\"lastId\" value=\""+data[data.length-1]["idMessaggio"]+"\">")
				$(".active-chat").append("<input type=\"hidden\" id=\"state\" value=\""+data[data.length-1]["stato"]+"\">")
				$(".active-chat").append("<input type=\"hidden\" id=\"destinatarioConversazione\" value=\""+destinatario+"\">")
				var a =$("ul.people").children();
				var contatore=0
				while(contatore<$("ul.people").children().length)
				{
					if(a.attr("class")=="person active")
					{
						var ore=data[data.length-1]["data"]["time"]["hour"];
						var minuti=data[data.length-1]["data"]["time"]["minute"];
						if(ore<10)
							ore="0"+ore
						if(minuti<10)
							minuti="0"+minuti
						a.find("span").next().first().text(ore+":"+minuti)
						break;
					}
					a=a.next();
					contatore=contatore+1;
				}
			}
		});
		return false;
		}
	}
}
</script>
</html>
