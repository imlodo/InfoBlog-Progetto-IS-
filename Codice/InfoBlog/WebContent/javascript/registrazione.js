/**
 * 
 */

(function ($) {
    "use strict";
//Animazione testo
function animaTesto()
{
	var text = document.getElementById('text');
	var newDom = '';
	var animationDelay = 6;

	for(let i = 0; i < text.innerText.length; i++)
	{
		newDom += '<span class="char">' + (text.innerText[i] == ' ' ? '&nbsp;' : text.innerText[i])+ '</span>';
	}

	text.innerHTML = newDom;
	var length = text.children.length;

	for(let i = 0; i < length; i++)
	{
		text.children[i].style['animation-delay'] = animationDelay * i + 'ms';
	}

}
/*Serve per cambiare l'immagine di sfondo*/
/* Serve ad inizializzare */
var n = $( "input[name='typeUser']:checked" ).val();
if(n == "Utente")
{
	$(".login100-more-reg").css("background-image", "url('icone/utenteLog.jpeg')");
	$(".textDescription2").text("Registrati come utente per leggere,commentare e recensire gli articoli...");
}
if(n == "Autore")
{
	$(".login100-more-reg").css("background-image", "url('icone/autoreLog.jpeg')");
	$(".textDescription2").text("Registrati come autore per pubblicare i tuoi articoli...");
}
$("#logo").removeClass("animazione");
$("#logo").addClass("animazione2"); 
//Lancio l'animazione
animaTesto();

$( "input[type=radio]" ).change(function()
		{
	var classe = $("#logo").attr("class");
	if(classe == "animazione2")
		$("#logo").removeClass("animazione2");
	else
	{
		$("#logo").removeClass("animazione");
	}

	var n = $("input:checked").val();
	if(n == "Utente")
	{
		$(".input100-reg").val("");
		$(".errorLabelReg").text("");
		$(".textDescription2").text("Registrati come utente per leggere,commentare e recensire gli articoli...");
		$(".login100-more-reg").css("background-image", "url('icone/utenteLog.jpeg')");
		//Lancio l'animazione
		animaTesto();

	}
	if(n == "Autore")
	{
		$(".input100-reg").val("");
		$(".errorLabelReg").text("");
		$(".textDescription2").text("Registrati come autore per pubblicare i tuoi articoli...");
		$(".login100-more-reg").css("background-image", "url('icone/autoreLog.jpeg')");
		//Lancio l'animazione
		animaTesto();
	}
	if(classe == "animazione2")
		$("#logo").addClass("animazione"); 
	else
		$("#logo").addClass("animazione2"); 
});

/*==================================================================
[ Focus Contact2 ]*/
$('.input100-reg').each(function(){
    $(this).on('blur', function(){
        if($(this).val().trim() != "") {
            $(this).addClass('has-val');
        }
        else {
            $(this).removeClass('has-val');
        }
    })    
})

/*==================================================================
[ Validate after type ]*/
$('.validate-input .input100-reg').each(function(){
    $(this).on('blur', function(){
        if(validate(this) == false){
            showValidate(this);
        }
        else {
            $(this).parent().addClass('true-validate');
        }
    })    
})

/*==================================================================
[ Validate ]*/
var input = $('.validate-input .input100-reg');

$('.validate-form').on('submit',function(){
	$(".errorLabel").remove();
    var check = true;

    for(var i=0; i<input.length; i++) {
        if(validate(input[i]) == false){
            showValidate(input[i]);
            check=false;
        }
    }

    return check;
});

$('.validate-form .input100-reg').each(function(){
    $(this).focus(function(){
       hideValidate(this);
       $(this).parent().removeClass('true-validate');
    });
});

function validate (input) {
	if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
		if($(input).val().trim().match(/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/) == null) {
			return false;
		}
	}
	if($(input).attr('name') == 'nome' || $(input).attr('name') == 'cognome') 
	{
		if($(input).val().trim().match(/^[A-Z][a-z][^#!@&<>\[\]\"~;$^%{}?{0-9}]{0,30}$/) == null) 
		{
			return false;
		}
	}
	if($(input).attr('name') == 'password') 
	{
		if($(input).val().trim().match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$^+=!*()@%&]).{8,30}$/) == null) 
		{
			return false;
		}
	}
	if($(input).attr('name') == 'username') 
	{
		if($(input).val().trim().match(/^[A-Za-z0-9.]{5,30}$/) == null) 
		{
			return false;
		}
	}
	//Testo la password
}

function showValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).addClass('alert-validate');
}

function hideValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).removeClass('alert-validate');
}

})(jQuery);