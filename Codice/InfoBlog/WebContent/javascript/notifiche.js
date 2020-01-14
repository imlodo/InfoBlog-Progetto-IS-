$(document).ready(function(){
	$.post("/InfoBlog/PrelevaNotificheControl",{e_mail:email},function(jsons){
		for(var i=0;i<jsons.length;i++){
			console.log(jsons[i].stato);
			if(jsons[i].stato==="inviato"){
				var number = parseInt($(".numeroNotifiche").text(), 10);
				console.log(number);
				$(".numeroNotifiche").text(number+1);
				if(number>99)
					$(".numeroNotifiche").text("99+");
			}
		}
	});
	$("#itemNotifiche").click(function () {
			var menu=document.getElementById("lista");
			if(menu){
				menu.remove();
			}
			else{
				$("#itemNotifiche").append("<ul id='lista'/>");
				$.post("/InfoBlog/PrelevaNotificheControl",{e_mail:email},function(jsons){
					for(var i=0;i<jsons.length;i++){
						$("#lista").append("<li id='notifica"+i+"' class='item'/>")
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