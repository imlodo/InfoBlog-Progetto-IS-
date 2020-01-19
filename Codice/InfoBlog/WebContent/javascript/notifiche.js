$(document).ready(function(){
	$.post("/InfoBlog/PrelevaNotificheControl",{e_mail:email},function(jsons){
		for(var i=0;i<jsons.length;i++){
			console.log(jsons[i].stato);
			console.log(caratterePazzo)
			if(jsons[i].stato==="inviato"){
				var number = parseInt($(".numeroNotifiche").text(), 10);
				console.log(number);
				$(".numeroNotifiche").text(number+1);
				if((caratterePazzo==="a") && (jsons[i].contenuto.includes("Richiesta_pubblicazione_articolo"))){
					var number = parseInt($(".numeroNotifiche").text(), 10);
					$(".numeroNotifiche").text(number-1);
				}
				if(number>99)
					$(".numeroNotifiche").text("99+");
			}
		}
	});
	//per autore
	$("#itemNotifiche").click(function () {
			var menu=document.getElementById("lista");
			if(menu){
				menu.remove();
			}
			else{
				$("#itemNotifiche").append("<ul id='lista'/>");
				$.post("/InfoBlog/PrelevaNotificheControl",{e_mail:email},function(jsons){
					for(var i=0;i<jsons.length;i++){
						if(!jsons[i].contenuto.includes("Richiesta_pubblicazione_articolo")){
							$("#lista").append("<li id='notifica"+i+"' class='itemx'/>")
							if(jsons[i].stato==="letto"){
								$("#notifica"+i+"").append("<div class='stato'><span>(LETTO)</span></div>");
							}
							else{
								$("#notifica"+i+"").append("<div class='stato'><span>(NUOVO)</span></div>");
							}
							$("#notifica"+i+"")
								.append("<div class='testo'><a href='/InfoBlog/LetturaNotificaControl?idNotifica="+jsons[i].id+"'>"+jsons[i].contenuto+"</a></div>");
						}
					}
					$("#lista:empty").prepend("<div style='display:flex; color:black; border-style:solid; border-width:thin;'>Non ci sono notifiche...</div>")
				});
			}
	});
	//per moderatore
	$("#itemNotificheMod").click(function () {
		var menu=document.getElementById("lista");
		if(menu){
			menu.remove();
		}
		else{
			$("#itemNotificheMod").append("<ul id='lista'/>");
			$.post("/InfoBlog/PrelevaNotificheControl",{e_mail:email},function(jsons){
				if(jsons.length==0){
					$("#lista").append("<div style='display:flex; color:black; border-style:solid; border-width:thin;'>Non ci sono notifiche...</div>");
				}
				for(var i=0;i<jsons.length;i++){
					$("#lista").append("<li id='notifica"+i+"' class='itemx'/>")
					if(jsons[i].stato==="letto"){
						$("#notifica"+i+"").append("<div class='stato'><span>(LETTO)</span></div>");
					}
					else{
						$("#notifica"+i+"").append("<div class='stato'><span>(NUOVO)</span></div>");
					}
					$("#notifica"+i+"")
						.append("<div class='testo'><a href='/InfoBlog/LetturaNotificaControl?idNotifica="+jsons[i].id+"'>"+jsons[i].contenuto+"</a></div>");
				}
			});
		}
});
	$(".testo").click(function (){
		var number = parseInt($(".numeroNotifiche").text(), 10);
		if(number>0)
			$(".numeroNotifiche").text(number-1);
	});
});