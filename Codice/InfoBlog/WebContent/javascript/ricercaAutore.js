$(document).ready(function () {
	$("#form").on("submit",function (e){
	    e.preventDefault();
    	$.post("/InfoBlog/RicercaAutoreControl",{text:$("#textfield").val()},function(jsons){
    		const list=document.getElementById("listaAutori");
    		while(list.firstChild){
    			list.removeChild(list.firstChild);
    		}
    		if(jsons.length==0)
    			$("<p style='text-align=center;'>Autori non esistenti...</p>").appendTo("#listaAutori");
    		else{
    			for(var i=0;i<jsons.length;i++){
	    			$("#listaAutori").append("<div id='item"+i+"' class='item' tabindex='-1'/>")
	    			$("#item"+i+"").append("<span class='nome'>"+jsons[i].username+"</span>");
	    		}
    		}
	    });
	});
 });