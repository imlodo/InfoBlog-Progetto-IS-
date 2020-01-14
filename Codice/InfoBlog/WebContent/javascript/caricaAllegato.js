 $(document).ready(function () {
	    $.post("/InfoBlog/AllegatiArticoloControl",{id:"1"},function(jsons){
		for(var i=0;i<jsons.length;i++){
			$("#listaAllegati").append("<div id='item"+i+"' class='item' tabindex='-1'/>")
			$("#item"+i+"").append("<a href='/InfoBlog/ScaricaAllegatoControl?path="+jsons[i].percorsoFile+"'><img class='immagine' src='icone/file.png'/></a>");
			$("#item"+i+"").append("<span class='nome'>"+jsons[i].percorsoFile+"</span>");
		}
	});
	$('button').on('mousedown', 
	    function(event) {
	        event.preventDefault();
	    }
	);
    $("#deleteAllegato").click(function(){
    	for(var i=0;i<$("#listaAllegati").length;i++){
	    	if($("#item"+i+"").is(":focus")){
		    	var id=($(":focus").attr("id"));
		    	var filename=$("#"+id).text();
		    	$.post("/InfoBlog/CancellaAllegatoControl",{id:"1",path:filename},function(data){
		    		if(data==="si"){
		    			location.reload(true);
		    		}
		    	});
	    	}
    	}
    });
});