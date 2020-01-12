<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<link rel="stylesheet" href="css/Allegato.css">
    <script type="text/javascript">
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
	</script>
	<meta charset="ISO-8859-1">
	<title>Pannello Allegati</title>
</head>
<body>
	<div id="allegatoBlock">
		<p style="padding-left:10px;display:inline;">Lista allegati dell'articolo</p>
		<div style="padding-top:1%;">
			<form id="form" action="CaricaAllegatoControl" method="POST" enctype="multipart/form-data">
				<input type="hidden" name="id" value="1">
				<input type='file' name="file" accept="application/pdf,application/msword" required>
				<input class="invio" type="submit" value="Carica Allegato">
			</form>
			<button id="deleteAllegato" type="button" tabindex="-1">Cancella Allegato</button>
		</div>
		<div id="listaAllegati"></div>
	</div>
</body>
</html>