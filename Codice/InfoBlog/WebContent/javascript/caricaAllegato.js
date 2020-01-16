$(document).ready(function () 
{
	 	idArticolo = $("#idArticolo").val()
	    $.post("/InfoBlog/AllegatiArticoloControl",{id:idArticolo},function(jsons){
		for(var i=0;i<jsons.length;i++){
			
			$("#listaAllegati").append("<div id='item"+i+"' class='item' tabindex='-1'/>")
			$("#item"+i+"").append('<input type="checkbox" name="fileDelete" value="'+jsons[i].percorsoFile+'"><img class="immagine" src="icone/file.png"/></a>');
			$("#item"+i+"").append("<span class='nome'>"+jsons[i].percorsoFile+"</span>");
		}
	});
	$('button').on('mousedown', 
	    function(event) {
	        event.preventDefault();
	    }
	);
//    $("#deleteAllegato").on('click', function(){
//    	if($("#listaAllegati")[0].childElementCount > 1)
//    	{
//    		for(var i=0;i<$("#listaAllegati")[0].childElementCount;i++){
//    	    	if($("#item"+i+"").is(":focus")){
//    		    	var id=($(":focus").attr("id"));
//    		    	var filename=$("#"+id).text();
//    		    	$.post("/InfoBlog/CancellaAllegatoControl",{id:idArticolo,path:filename},function(data){
//    		    		if(data==="si")
//    		    		{	
//    		    			location.reload(true);
//    		    		}
//    		    	});
//    	    	}
//        	}
//    	}
//    	else
//    	{
//    		alert("Non puoi eliminare l'ultimo allegato");
//    	}
//    });
    
//    $("#caricaAllegato").click(function(event)
//    {
//    	    var inputs = $(document).find("#files");
//    	    $.each(inputs, function (obj, v) {
//             $.each($(v)[0].files, function(i, file) {
//            	 alert("Caricato file: " + file.name);
//            	 var data = new FormData();
//                 data.append(v.name, file);
//                 data.append('id',$('#idArticolo').val());
//                 $.ajax({
//         	        type: 'POST',               
//         	        processData: false, // important
//         	        contentType: false, // important
//         	        data: data,
//         	        url: "CaricaAllegatoControl",
//         	        contentType: false, 
//         	     	processData: false,
//         	        success: function(jsonData)
//         	        {
//         	        	
//         	        }
//         	    }); 
//             });
//    	    });
//    	    location.reload(true);
//	});			
});