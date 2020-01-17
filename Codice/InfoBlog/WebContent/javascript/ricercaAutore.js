$(document).ready(function () {
	$(".formRicercaGuest").submit(function (e){
    	e.preventDefault();
		$("#containerDati").empty();
		var scelta=$(".selectBar option:selected").text();
		if (scelta==="Autore"){
			$.post("/InfoBlog/RicercaAutoreControl",{text:$("#myInput").val()},function(jsons){
	    		$("#containerDati").append("<div id='titolo'>Risultato Ricerca</div>")
	    		$("#containerDati").append("<div id='trovati'>Autori trovati: "+jsons.length+"</div>")
	    		$("#containerDati").append("<div id='areaListaDati'></div>")
				$("#areaListaDati").append("<ul id='listaRicerca'></ul>")
	    		for(var i=0;i<jsons.length;i++){
	    			$("#listaRicerca").append("<li id='elemento"+i+"' class='elemento'></li>")
	    			$("#elemento"+i+"").append("<div id='nomeAutore"+i+"' class='s-testo'><span class='fof'>Nome: </span></div>")
	    			$("#elemento"+i+"").append("<div id='cognomeAutore"+i+"' class='s-testo'><span class='fof'>Cognome: </span></div>")
	    			$("#elemento"+i+"").append("<button type='button' class='s-bottone' id='vadoAlPannello"+i+"'>Visualizza</button>")
	    			$("#nomeAutore"+i+"").append("<span>"+jsons[i].nome+"</span>")
	    			$("#cognomeAutore"+i+"").append("<span>"+jsons[i].cognome+"</span>")
	    			var e_mail=jsons[i].email;
	    			$("#vadoAlPannello"+i+"").click(function(){
	    				 window.location.href = "/InfoBlog/PageAutoreServlet?email="+e_mail+"";
	    			});	
	    		}
		    });
		}
		else{
			
		}
	});
 });