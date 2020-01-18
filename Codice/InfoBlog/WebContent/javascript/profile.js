/**
 * 
 */
var p,nome,cognome,email,username,password;
function changeView()
{
	errorLabel = $(".errorLabel");
	successLabel = $(".successLabel");
	if(errorLabel != null)
	{
		errorLabel.remove()
	}
	if(successLabel != null)
	 {
		successLabel.remove()
	 }
	
	nome = $("#nome").val();
	cognome=$("#cognome").val();
	email=$("#email").val();
	username=$("#username").val();
	password=$("#password").val();
	controllaDati();
	$(".fixButton").remove();
	p = $(".data").detach();
	$("<form class='formModificaDati' action='ModificaDatiPersonaliControl' method='POST'>").insertAfter($(".topTitle"));
	$(".formModificaDati").append(p);
	$(".data").append('<div class="fixButton"></div>');
	$(".fixButton").append("<input type='button' class='button' value='Salva Modifiche' onclick='controlSubmit()'/>" +
	"<button class='button' onclick='cancel();'>Cancella Modifiche</button>");
	$(".input100-reg").prop( "disabled", false );
	$("#email").prop("disabled",true);
	$("#categoria").prop("disabled",true);
	$("#password").prop( "type", "text" );
	
	$('input[type="text"]').keyup(function() 
	{
		controllaDati();
	});
	$(".formModificaDati").keypress(function(e) {
		  if (e.which == 13) {
		    controlSubmit();
		  }
	});
}

function cancel()
{
	$('input[type="text"]').css("cssText", "background-color: #c3c3c3!important;");
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
	
	errorLabel = $(".errorLabel");
	successLabel = $(".successLabel");
	if(errorLabel != null)
	{
		errorLabel.remove()
	}
	if(successLabel != null)
	 {
		successLabel.remove()
	 }
	
}

function validate (input) {
	if(input.attr('type') == 'email' || input.attr('name') == 'email') 
	{
		if(input.val().trim().match(/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/) == null) {
			return false;
		}
	}
	if(input.attr('name') == 'nome' || input.attr('name') == 'cognome') 
	{
		if(input.val().trim().match(/^[A-Z][a-z][^#!@&<>\[\]\"~;$^%{}?{0-9}]{0,30}$/) == null) 
		{
			return false;
		}
	}
	if(input.attr('name') == 'password') 
	{
		if(input.val().trim().match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$^+=!*()@%&]).{8,30}$/) == null) 
		{
			return false;
		}
	}
	if(input.attr('name') == 'username') 
	{
		if(input.val().trim().match(/^[A-Za-z0-9.]{5,30}$/) == null) 
		{
			return false;
		}
	}
}


function controlSubmit()
{
	errorLabel = $(".errorLabel");
	successLabel = $(".successLabel");
	if(errorLabel != null)
	{
		errorLabel.remove()
	}
	if(successLabel != null)
	 {
		successLabel.remove()
	 }
	error = controllaDati();
	if(error == false)
	{
		$(".formModificaDati").submit();
	}
	else
	{
		$("<div class='errorLabel'><label>Formato dati errato, correggi i campi evidenziati!</label></div>").insertAfter(".titleTopData");
	}
}

function controllaDati()
{
	var error = false;
	nomeCheck = $("#nome");
	cognomeCheck=$("#cognome");
	emailCheck=$("#email");
	if(emailCheck.attr('disabled') == "disabled"){}
	else
	{
		valEmail = validate(emailCheck);
		if(valEmail == false)
		{
			emailCheck.css("cssText", "background-color: red !important;");
			emailCheck.css("color", "white");
		}
		else
		{
			emailCheck.css("cssText", "background-color: green !important;");
			emailCheck.css("color", "white");
		}
	}
	usernameCheck=$("#username");
	passwordCheck=$("#password");
	
	valNome = validate(nomeCheck);
	valCognome = validate(cognomeCheck);
	valUsername = validate(usernameCheck);
	valPassword = validate(passwordCheck);

	if(valNome == false)
	{
		nomeCheck.css("cssText", "background-color: red !important;");
		nomeCheck.css("color", "white");
		error = true;
	}
	else
	{
		nomeCheck.css("cssText", "background-color: green !important;");
		nomeCheck.css("color", "white");
	}
	if(valCognome == false)
	{
		cognomeCheck.css("cssText", "background-color: red !important;");
		cognomeCheck.css("color", "white");
		error = true;
	}
	else
	{
		cognomeCheck.css("cssText", "background-color: green !important;");
		cognomeCheck.css("color", "white");
		
	}
	if(valUsername == false)
	{
		usernameCheck.css("cssText", "background-color: red !important;");
		usernameCheck.css("color", "white");
		error = true;
	}
	else
	{
		usernameCheck.css("cssText", "background-color: green !important;");
		usernameCheck.css("color", "white");
	}
	if(valPassword == false)
	{
		passwordCheck.css("cssText", "background-color: red !important;");
		passwordCheck.css("color", "white");
		error = true;
	}
	else
	{
		passwordCheck.css("cssText", "background-color: green !important;");
		passwordCheck.css("color", "white");
	}
	return error;
}