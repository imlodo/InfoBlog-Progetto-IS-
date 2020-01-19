$(document).ready(function () {
		$(".formRicercaGuest").submit(function (e){
	    	e.preventDefault();
 			if($("#containerDati").children().length > 0){
 				console.log("Svuoto");
	    		$("#containerDati").empty();
	    		return;
 			}
	    	var scelta=$(".selectBar option:selected").text();
			if (scelta==="Autore"){
				$.post("/InfoBlog/RicercaAutoreControl",{text:$("#myInput").val()},function(jsons){
		    		$("#containerDati").append("<div class='titolo'>Risultato Ricerca</div>")
		    		$("#containerDati").append("<div class='trovati'>Autori trovati: "+jsons.length+"</div>")
		    		$("#containerDati").append("<div id='areaListaDati'></div>")
					$("#areaListaDati").append("<ul id='listaRicerca'></ul>")
		    		for(var i=0;i<jsons.length;i++){
		    			$("#listaRicerca").append("<li id='elemento"+i+"' class='elemento'></li>")
		    			$("#elemento"+i+"").append("<div id='nomeAutore"+i+"' class='s-testo'><span class='fof'>Nome: </span></div>")
		    			$("#elemento"+i+"").append("<div id='cognomeAutore"+i+"' class='s-testo'><span class='fof'>Cognome: </span></div>")
		    			$("#elemento" + i + "").append("<input type='button' class='s-bottone' id='inviaModulo"+i+"' value='Visualizza'>")
		    			$("#inviaModulo"+i+"").on("click",{e_mail: jsons[i].email},function(event){
		    				location.href="/InfoBlog/PageAutoreServlet?email="+event.data.e_mail+"";
		    			});
		    			$("#nomeAutore"+i+"").append("<span>"+jsons[i].nome+"</span>")
		    			$("#cognomeAutore"+i+"").append("<span>"+jsons[i].cognome+"</span>")
		    		}
			    });
			}
			//Ricerca Articoli
			else{
				$.post("/InfoBlog/RicercaArticoloControl",{titolo:$("#myInput").val()},function(jsons){
					var articoli=jsons[0];
					var commenti=jsons[1];
					var ratings=jsons[2];
					var numeroArticoli=0;
					
					for(var i=0;i<articoli.length;i++){
						if(articoli[i].stato==="daPubblicare"){
							continue;
						}
						else numeroArticoli+=1;
					}
					$("#containerDati").append("<div class='titolo'>Risultato Ricerca</div>")
		    		$("#containerDati").append("<div class='trovati'>Articoli trovati: "+numeroArticoli+"</div>")
		    		
		    		$("#containerDati").append("<div id='areaListaDati'></div>")
					$("#areaListaDati").append("<ul id='listaRicerca'></ul>")
		    		for(var i=0;i<articoli.length;i++){
		    			if(articoli[i].stato=="daPubblicare"){
		    				continue;
		    			}
		    			numeroArticoli+=1;
		    			$("#listaRicerca").append("<li id='elemento"+i+"' class='elemento'></li>")
		    			$("#elemento"+i+"").append("<div id='nomeArticolo"+i+"' class='s2-testo'><span class='fof'>Titolo articolo: </span></div>")
		    			$("#elemento"+i+"").append("<div id='autore"+i+"' class='s2-testo'><span class='fof'>Nome e Cognome: </span></div>")
		    			$("#elemento"+i+"").append("<div id='dataPubblicazione"+i+"' class='s2-testo'><span class='fof'>Data pubblicazione: </span></div>")
		    			$("#elemento"+i+"").append("<div id='categoria"+i+"' class='s2-testo'><span class='fof'>Categoria: </span></div>")
		    			$("#elemento"+i+"").append("<div id='rating"+i+"' class='s2-testo'><span class='fof'>Rating: </span></div>")
		    			$("#elemento"+i+"").append("<div id='commenti"+i+"' class='s2-testo'><span class='fof'>Commenti: </span></div>")
		    			$("#elemento" + i + "").append("<input type='button' class='s-bottone' id='inviaModulo"+i+"' value='Visualizza'>")
		    			$("#inviaModulo"+i+"").on("click",{id: articoli[i].id, title: articoli[i].titolo},function(event){
		    				location.href="/InfoBlog/ViewArticleServlet?id="+event.data.id+"&Titolo="+event.data.title+"";
		    			});
		    			
		    			//Aggancio i dati
		    			$("#nomeArticolo"+i+"").append("<span>"+articoli[i].titolo+"</span>");
		    			$("#autore"+i+"").append("<span>"+articoli[i].autore.nome+" "+articoli[i].autore.cognome+"</span>");
		    			$("#dataPubblicazione"+i+"").append("<span>"+articoli[i].data.day+"/"+articoli[i].data.month+"/"+articoli[i].data.year+"</span>");
		    			$("#categoria"+i+"").append("<span>"+articoli[i].categoria+"</span>");
		    			$("#rating"+i+"").append("<span>"+ratings[i][0].numeroStelle+"</span>");
		    			$("#commenti"+i+"").append("<span>"+commenti[i].length+"</span>");
		    		}
				});
			}
		});
	//Ricerca Utente
	$(".formRicercaUtente").submit(function (e){
    	e.preventDefault();
    	if($("#containerDati").children().length > 0){
			console.log("Svuoto");
    		$("#containerDati").empty();
    		return;
		}
    	var scelta=$(".selectBarUtente option:selected").text();
		if (scelta==="Autore"){
			$.post("/InfoBlog/RicercaAutoreControl",{text:$("#myInput").val()},function(jsons){
				$("#containerDati").append("<div class='titolo'>Risultato Ricerca</div>")
	    		$("#containerDati").append("<div class='trovati'>Autori trovati: "+jsons.length+"</div>")
	    		$("#containerDati").append("<div id='areaListaDati'></div>")
				$("#areaListaDati").append("<ul id='listaRicerca'></ul>")
	    		for(var i=0;i<jsons.length;i++){
	    			$("#listaRicerca").append("<li id='elemento"+i+"' class='elemento'></li>")
	    			$("#elemento"+i+"").append("<div id='nomeAutore"+i+"' class='s-testo'><span class='fof'>Nome: </span></div>")
	    			$("#elemento"+i+"").append("<div id='cognomeAutore"+i+"' class='s-testo'><span class='fof'>Cognome: </span></div>")
	    			$("#elemento" + i + "").append("<input type='button' class='s-bottone' id='inviaModulo"+i+"' value='Visualizza'>")
	    			$("#inviaModulo"+i+"").on("click",{e_mail: jsons[i].email},function(event){
	    				location.href="/InfoBlog/PageAutoreServlet?email="+event.data.e_mail+"";
	    			});
	    			$("#nomeAutore"+i+"").append("<span>"+jsons[i].nome+"</span>")
	    			$("#cognomeAutore"+i+"").append("<span>"+jsons[i].cognome+"</span>")
	    		}
		    });
		}
		//Ricerca Articoli
		else{
			$.post("/InfoBlog/RicercaArticoloControl",{titolo:$("#myInput").val()},function(jsons){
				var articoli=jsons[0];
				var commenti=jsons[1];
				var ratings=jsons[2];
				var numeroArticoli=0;
				
				for(var i=0;i<articoli.length;i++){
					if(articoli[i].stato==="daPubblicare"){
						continue;
					}
					else numeroArticoli+=1;
				}
				$("#containerDati").append("<div class='titolo'>Risultato Ricerca</div>")
	    		$("#containerDati").append("<div class='trovati'>Articoli trovati: "+numeroArticoli+"</div>")
	    		
	    		$("#containerDati").append("<div id='areaListaDati'></div>")
				$("#areaListaDati").append("<ul id='listaRicerca'></ul>")
	    		for(var i=0;i<articoli.length;i++){
	    			if(articoli[i].stato=="daPubblicare"){
	    				continue;
	    			}
	    			numeroArticoli+=1;
	    			$("#listaRicerca").append("<li id='elemento"+i+"' class='elemento'></li>")
	    			$("#elemento"+i+"").append("<div id='nomeArticolo"+i+"' class='s2-testo'><span class='fof'>Titolo articolo: </span></div>")
	    			$("#elemento"+i+"").append("<div id='autore"+i+"' class='s2-testo'><span class='fof'>Nome e Cognome: </span></div>")
	    			$("#elemento"+i+"").append("<div id='dataPubblicazione"+i+"' class='s2-testo'><span class='fof'>Data pubblicazione: </span></div>")
	    			$("#elemento"+i+"").append("<div id='categoria"+i+"' class='s2-testo'><span class='fof'>Categoria: </span></div>")
	    			$("#elemento"+i+"").append("<div id='rating"+i+"' class='s2-testo'><span class='fof'>Rating: </span></div>")
	    			$("#elemento"+i+"").append("<div id='commenti"+i+"' class='s2-testo'><span class='fof'>Commenti: </span></div>")
	    			$("#elemento" + i + "").append("<input type='button' class='s-bottone' id='inviaModulo"+i+"' value='Visualizza'>")
	    			$("#inviaModulo"+i+"").on("click",{id: articoli[i].id, title: articoli[i].titolo},function(event){
	    				location.href="/InfoBlog/ViewArticleServlet?id="+event.data.id+"&Titolo="+event.data.title+"";
	    			});
	    			
	    			//Aggancio i dati
	    			$("#nomeArticolo"+i+"").append("<span>"+articoli[i].titolo+"</span>");
	    			$("#autore"+i+"").append("<span>"+articoli[i].autore.nome+" "+articoli[i].autore.cognome+"</span>");
	    			$("#dataPubblicazione"+i+"").append("<span>"+articoli[i].data.day+"/"+articoli[i].data.month+"/"+articoli[i].data.year+"</span>");
	    			$("#categoria"+i+"").append("<span>"+articoli[i].categoria+"</span>");
	    			$("#rating"+i+"").append("<span>"+ratings[i][0].numeroStelle+"</span>");
	    			$("#commenti"+i+"").append("<span>"+commenti[i].length+"</span>");
	    		}
			});
		}
	});
 });