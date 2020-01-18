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
		
		var letters= /[a-zA-Z0-9 #.:(),!@&<>'’\è\é\ù\ò\"~;$^%{}?]{5,700}$/;
		
		$("#testoDelMessaggio").val("");
		
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
