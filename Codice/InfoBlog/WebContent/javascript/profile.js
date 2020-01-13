/**
 * 
 */
var p,nome,cognome,email,username,password;
function changeView()
{
	nome = $("#nome").val();
	cognome=$("#cognome").val();
	email=$("#email").val();
	username=$("#username").val();
	password=$("#password").val();
	
	$(".fixButton").remove();
	p = $(".data").detach();
	$("<form class='formModificaDati' action='ModificaDatiPersonaliControl' method='POST'>").insertAfter($(".topTitle"));
	$(".formModificaDati").append(p);
	$(".data").append('<div class="fixButton"></div>');
	$(".fixButton").append("<button class='button' onclick='controlSubmit()'>Salva Modifiche</button>" +
	"<button class='button' onclick='cancel();'>Cancella Modifiche</button>");
	$(".input100-reg").prop( "disabled", false );
	$("#email").prop("disabled",true);
	$("#categoria").prop("disabled",true);
	$("#password").prop( "type", "text" );
}

function cancel()
{
	$("#nome").val(nome);
	$("#cognome").val(cognome);
	$("#email").val(email);
	$("#username").val(username);
	$("#password").val(password);
	$(".input100-reg").prop( "disabled", true );
	$(".fixButton").remove();
	$(".formModificaDati").remove();
	$(p).append('<div class="fixButton"></div>');
	$(p).insertAfter($(".topTitle"));
	$(".fixButton").append("<button class='button' onclick='onclick=changeView()'>Modifica</button>");	
	$("#password").prop( "type", "password" );
}

function controlSubmit()
{
	submit();
}