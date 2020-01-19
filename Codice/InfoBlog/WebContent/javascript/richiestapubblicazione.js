/**
 * 
 */
	
	$("[name='titolo']").keyup(function(){
		titoloCheck = $("[name='titolo']");
		valTitolo = validate(titoloCheck);
		if(valTitolo == false)
		{
			titoloCheck.css("cssText", "border: 2px solid red !important;");
			$( window ).resize(function() 
			{
				var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
				if(width <= 692)
				{
					if($(".errorTitle").val() == undefined)
					{
						$("#errorImgTitolo").css("cssText","display: inline-block;position: relative;bottom: auto;top: -25px;right: -130px;font-size: 10px;width: 18px;left: auto;");
					}
					else
					{
						$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-25px;right:-2px;font-size: 10px;width: 18px;left: auto;");
					}
				}
				else
				{
					if($(".errorTitle").val() == undefined)
					{
						$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:4px;right:30px;font-size: 10px;width: 18px;left: auto;");					}
					else
					{
						$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-28px;right:-20px;font-size: 10px;width: 18px;left: auto;");
					}
				}
			});			
			$(".errorTitle").remove();
			$("#errorImgTitolo").remove();
			
			$("<img src='icone/close.svg' id='errorImgTitolo'>").insertAfter(titoloCheck);
			var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
			if(width <= 692)
			{
				$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: 30px;font-size: 10px;width: 18px;left: 135px;");
			}
			else
			{
				$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: -3px;font-size: 10px;width: 18px;left: -25px;");
			}
			$("#errorImgTitolo").click(function(){
				if($(".errorTitle").val() == undefined)
				{
					$("<div class='errorTitle'><label>Titolo errato: Deve iniziare con una lettera maiuscola, lunghezza:(min=4,max=50) caratteri ammessi:(numeri,lettere,^#@&<>\"~;$^%{}?) </label></div>").insertAfter(titoloCheck);
					$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: 29px;font-size: 10px;width: 18px;left: 2px;")
				}
				else
				{			
					$(".errorTitle").remove();
					var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
					if(width <= 692)
					{
						if($(".errorTitle").val() == undefined)
							$("#errorImgTitolo").css("cssText","display: inline-block;position: relative;bottom: auto;top: -25px;right: -130px;font-size: 10px;width: 18px;left: auto;");
						else
						{
							$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:4px;right:30px;font-size: 10px;width: 18px;left: auto;");
						}
					}
					else
					{
						$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:4px;right:30px;font-size: 10px;width: 18px;left: auto;");
					}
				}
			});
			error = true;
		}
		else
		{
			$(".errorTitle").remove();
			$("#errorImgTitolo").remove();
			titoloCheck.css("cssText", "border: 2px solid green !important;");
		}
	});

	$("[name='contenuto']").keyup(function(){
		contenutoCheck = $("[name='contenuto']");
		valContenuto = validate(contenutoCheck);
		if(valContenuto == false)
		{
			contenutoCheck.css("cssText", "border: 2px solid red !important;");
			$( window ).resize(function() 
					{
						var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
						if(width <= 692)
						{
							if($(".errorContenuto").val() == undefined)
							{
								$("#errorImgContenuto").css("cssText","display: inline-block;position: relative;bottom: auto;top: -25px;right: -130px;font-size: 10px;width: 18px;left: auto;");
							}
							else
							{
								$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-25px;right:-2px;font-size: 10px;width: 18px;left: auto;");
							}
						}
						else
						{
							if($(".errorContenuto").val() == undefined)
							{
								$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top: -5px;right:30px;font-size: 10px;width: 18px;left: auto;");					}
							else
							{
								$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-28px;right:-20px;font-size: 10px;width: 18px;left: auto;");
							}
						}
					});			
					$(".errorContenuto").remove();
					$("#errorImgContenuto").remove();
					
					$("<img src='icone/close.svg' id='errorImgContenuto'>").insertAfter(contenutoCheck);
					var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
					if(width <= 692)
					{
						$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: 30px;font-size: 10px;width: 18px;left: 135px;");
					}
					else
					{
						$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: 5px;font-size: 10px;width: 18px;left: -25px;");
					}
					$("#errorImgContenuto").click(function(){
						if($(".errorContenuto").val() == undefined)
						{
							$("<div class='errorContenuto'><label>Contenuto errato: lunghezza:(min=200,max=15000) caratteri ammessi:(numeri,lettere,#.:(),!@&<>'’+\ì\à\è\é\ù\ò\"~;$^%{}?) </label></div>").insertAfter(contenutoCheck);
							$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: 29px;font-size: 10px;width: 18px;left: 2px;")
						}
						else
						{			
							$(".errorContenuto").remove();
							var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
							if(width <= 692)
							{
								if($(".errorContenuto").val() == undefined)
									$("#errorImgContenuto").css("cssText","display: inline-block;position: relative;bottom: auto;top: -25px;right: -130px;font-size: 10px;width: 18px;left: auto;");
								else
								{
									$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-5px;right:30px;font-size: 10px;width: 18px;left: auto;");
								}
							}
							else
							{
								$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-5px;right:30px;font-size: 10px;width: 18px;left: auto;");
							}
						}
					});
			error = true;
		}
		else
		{
			contenutoCheck.css("cssText", "border: 2px solid green !important;");
			$(".errorContenuto").remove();
			$("#errorImgContenuto").remove();
		}
		
	});
	
	$("[name='categoria']").change(function(){
		categoriaCheck = $("[name='categoria']");
		valCategoria = validate(categoriaCheck);
		if(valCategoria == false)
		{
			categoriaCheck.css("cssText", "border: 2px solid red !important;");
			$(".errorCategoria").remove();
			$("#errorImgCategoria").remove();
			
			$("<img src='icone/close.svg' id='errorImgCategoria' style='width:20px;'>").insertAfter(categoriaCheck);
			$(".errorImgCategoria")
			$("#errorImgCategoria").click(function(){
				if($(".errorCategoria").val() == undefined)
				{
					$("<div class='errorCategoria'><label>Devi selezionare almeno una categoria</label></div>").insertAfter(categoriaCheck);
					$(".errorCategoria").css("cssText", "display:block;position: relative;bottom: 30px;font-size: 10px;width: 80%;background: white;padding: 10px;color: red;border: 1px solid red;left: 10%;");
				}
				else
				{
					$(".errorCategoria").remove();
					
				}
			});
			error = true;
		}
		else
		{
			categoriaCheck.css("cssText", "border: 2px solid green !important;");
		}
	});

	
	$("[name='file[]']").change(function(){
		allegatiCheck = $("[name='file[]']");
		valAllegati = validate(allegatiCheck);
		if(valAllegati == false)
		{
			$(".inputFile").css("cssText", "border-top: 2px solid red !important;border-left: 2px solid red !important;border-right: 2px solid red !important;");
			$("#selectedFiles").css("cssText", "border-bottom: 2px solid red !important;border-left: 2px solid red !important;border-right: 2px solid red !important;");
			
			$(".errorAllegati").remove();
			$("#errorImgAllegati").remove();
			
			$("<img src='icone/close.svg' id='errorImgAllegati' style='width:20px;'>").insertAfter(allegatiCheck);
			$("#errorImgAllegati").click(function(){
				if($(".errorAllegati").val() == undefined)
				{
					$("<div class='errorAllegati'><label>Errore selezione: devi selezionare almeno un allegato, garantiscono la correttezza degli articoli. I formati supportati sono: pdf e word(doc e docx) </label></div>").insertAfter(allegatiCheck);
					$(".errorAllegati").css("cssText", "display:block;position: relative;bottom: 3px;font-size: 10px;width: 80%;background: white;padding: 10px;color: red;border: 1px solid red;left: 10%;");
				}
				else
				{
					$(".errorAllegati").remove();
					
				}
			});
			error = true;
		}
		else
		{
			$(".errorAllegati").remove();
			$("#errorImgAllegati").remove();
			$(".inputFile").css("cssText", "border-top: 2px solid green !important;border-left: 2px solid green !important;border-right: 2px solid green !important;");
			$("#selectedFiles").css("cssText", "border-bottom: 2px solid green !important;border-left: 2px solid green !important;border-right: 2px solid green !important;");
		}
	});
	
	var selDiv = "";
		
	document.addEventListener("DOMContentLoaded", init, false);
	
	function init() {
		document.querySelector('#files').addEventListener('change', handleFileSelect, false);
		selDiv = document.querySelector("#selectedFiles");
	}
		
	function handleFileSelect(e) 
	{
		
		if(!e.target.files) return;
		
		selDiv.innerHTML = "";
		
		var files = e.target.files;
		for(var i=0; i<files.length; i++) {
			var f = files[i];
			
			selDiv.innerHTML += "<label id='file"+i+"'>" + f.name + 
			"<br>";
		}
		
	}
	
	function validate (input) {
		if(input.attr('name') == 'titolo') 
		{
			if(input.val().trim().match(/[A-Z][a-zA-Z0-9][^#@&<>\"~;$^%{}?]{4,50}$/) == null) {
				return false;
			}
		}
		if(input.attr('name') == 'contenuto') 
		{
			if(input.val().trim().match(/[a-zA-Z0-9 #.:(),!@&<>'’+\ì\à\è\é\ù\ò\"~;$^%{}?]{200,15000}$/) == null) 
			{
				return false;
			}
		}
		if(input.attr('name') == 'categoria') 
		{
			if(input.val() == null || input.val() == "") 
			{
				return false;
			}
		}
		if(input.attr('name') == 'file[]') 
		{
			esistenti = $("#listaAllegati")[0];
			contatore = esistenti != undefined ? esistenti.childElementCount : 0;
			if(input[0].files.length == 0)
			{
				if(contatore > 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				for(i = 0; i<input[0].files.length; i++)
				{
					if(((input[0].files)[i].name).trim().match(/\.(pdf|doc|docx)$/) == null) 
					{
						return false;
					}
				}
			}
			
		}
	}


	function checkSubmit()
	{
		$(".titleError").remove();
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
			$(".formRichiestaPubblicazione").submit();
		}
		else
		{
			$("<div class='errorLabel'><label>Formato dati errato, correggi i campi evidenziati!</label></div>").insertAfter(".titoloContainer");
		}
	}

	function controllaDati()
	{
		var error = false;
		titoloCheck = $("[name='titolo']");
		contenutoCheck = $("[name='contenuto']");
		categoriaCheck = $("[name='categoria']");
		allegatiCheck = $("[name='file[]']");
		
		valTitolo = validate(titoloCheck);
		valContenuto = validate(contenutoCheck);
		valCategoria = validate(categoriaCheck);
		valAllegati = validate(allegatiCheck);
		
		if(valTitolo == false)
		{
			titoloCheck.css("cssText", "border: 2px solid red !important;");
			$( window ).resize(function() 
			{
				var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
				if(width <= 692)
				{
					if($(".errorTitle").val() == undefined)
					{
						$("#errorImgTitolo").css("cssText","display: inline-block;position: relative;bottom: auto;top: -25px;right: -130px;font-size: 10px;width: 18px;left: auto;");
					}
					else
					{
						$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-25px;right:-2px;font-size: 10px;width: 18px;left: auto;");
					}
				}
				else
				{
					if($(".errorTitle").val() == undefined)
					{
						$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:4px;right:30px;font-size: 10px;width: 18px;left: auto;");					}
					else
					{
						$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-28px;right:-20px;font-size: 10px;width: 18px;left: auto;");
					}
				}
			});			
			$(".errorTitle").remove();
			$("#errorImgTitolo").remove();
			
			$("<img src='icone/close.svg' id='errorImgTitolo'>").insertAfter(titoloCheck);
			var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
			if(width <= 692)
			{
				$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: 30px;font-size: 10px;width: 18px;left: 135px;");
			}
			else
			{
				$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: -3px;font-size: 10px;width: 18px;left: -25px;");
			}
			$("#errorImgTitolo").click(function(){
				if($(".errorTitle").val() == undefined)
				{
					$("<div class='errorTitle'><label>Titolo errato: Deve iniziare con una lettera maiuscola, lunghezza:(min=4,max=50) caratteri ammessi:(numeri,lettere,^#@&<>\"~;$^%{}?) </label></div>").insertAfter(titoloCheck);
					$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: 29px;font-size: 10px;width: 18px;left: 2px;")
				}
				else
				{			
					$(".errorTitle").remove();
					var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
					if(width <= 692)
					{
						if($(".errorTitle").val() == undefined)
							$("#errorImgTitolo").css("cssText","display: inline-block;position: relative;bottom: auto;top: -25px;right: -130px;font-size: 10px;width: 18px;left: auto;");
						else
						{
							$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:4px;right:30px;font-size: 10px;width: 18px;left: auto;");
						}
					}
					else
					{
						$("#errorImgTitolo").css("cssText","display: inline-flex;position: relative;bottom: auto;top:4px;right:30px;font-size: 10px;width: 18px;left: auto;");
					}
				}
			});
			error = true;
		}
		else
		{
			$(".errorTitle").remove();
			$("#errorImgTitolo").remove();
			titoloCheck.css("cssText", "border: 2px solid green !important;");
		}
		
		if(valContenuto == false)
		{
			contenutoCheck.css("cssText", "border: 2px solid red !important;");
			$( window ).resize(function() 
					{
						var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
						if(width <= 692)
						{
							if($(".errorContenuto").val() == undefined)
							{
								$("#errorImgContenuto").css("cssText","display: inline-block;position: relative;bottom: auto;top: -25px;right: -130px;font-size: 10px;width: 18px;left: auto;");
							}
							else
							{
								$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-25px;right:-2px;font-size: 10px;width: 18px;left: auto;");
							}
						}
						else
						{
							if($(".errorContenuto").val() == undefined)
							{
								$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-5px;right:30px;font-size: 10px;width: 18px;left: auto;");					}
							else
							{
								$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-28px;right:-20px;font-size: 10px;width: 18px;left: auto;");
							}
						}
					});			
					$(".errorContenuto").remove();
					$("#errorImgContenuto").remove();
					
					$("<img src='icone/close.svg' id='errorImgContenuto'>").insertAfter(contenutoCheck);
					var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
					if(width <= 692)
					{
						$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: 30px;font-size: 10px;width: 18px;left: 135px;");
					}
					else
					{
						$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: 5px;font-size: 10px;width: 18px;left: -25px;");
					}
					$("#errorImgContenuto").click(function(){
						if($(".errorContenuto").val() == undefined)
						{
							$("<div class='errorContenuto'><label>Contenuto errato: lunghezza:(min=200,max=15000) caratteri ammessi:(numeri,lettere,#.:(),!@&<>'’+\ì\à\è\é\ù\ò\"~;$^%{}?) </label></div>").insertAfter(contenutoCheck);
							$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: 29px;font-size: 10px;width: 18px;left: 2px;")
						}
						else
						{			
							$(".errorContenuto").remove();
							var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
							if(width <= 692)
							{
								if($(".errorContenuto").val() == undefined)
									$("#errorImgContenuto").css("cssText","display: inline-block;position: relative;bottom: auto;top: -25px;right: -130px;font-size: 10px;width: 18px;left: auto;");
								else
								{
									$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-5px;right:30px;font-size: 10px;width: 18px;left: auto;");
								}
							}
							else
							{
								$("#errorImgContenuto").css("cssText","display: inline-flex;position: relative;bottom: auto;top:-5px;right:30px;font-size: 10px;width: 18px;left: auto;");
							}
						}
					});
			error = true;
		}
		else
		{
			contenutoCheck.css("cssText", "border: 2px solid green !important;");
			$(".errorContenuto").remove();
			$("#errorImgContenuto").remove();
		}
		
		if(valAllegati == false)
		{
			$(".inputFile").css("cssText", "border-top: 2px solid red !important;border-left: 2px solid red !important;border-right: 2px solid red !important;");
			$("#selectedFiles").css("cssText", "border-bottom: 2px solid red !important;border-left: 2px solid red !important;border-right: 2px solid red !important;");
			$("#listaAllegati").css("cssText", "border-bottom: 2px solid red !important;border-left: 2px solid red !important;border-right: 2px solid red !important;");


			$(".errorAllegati").remove();
			$("#errorImgAllegati").remove();
			
			$("<img src='icone/close.svg' id='errorImgAllegati' style='width:20px;'>").insertAfter(allegatiCheck);
			$("#errorImgAllegati").click(function(){
				if($(".errorAllegati").val() == undefined)
				{
					$("<div class='errorAllegati'><label>Errore selezione: devi selezionare almeno un allegato, garantiscono la correttezza degli articoli. I formati supportati sono: pdf e word(doc e docx) </label></div>").insertAfter(allegatiCheck);
					$(".errorAllegati").css("cssText", "display:block;position: relative;bottom: 3px;font-size: 10px;width: 80%;background: white;padding: 10px;color: red;border: 1px solid red;left: 10%;");
				}
				else
				{
					$(".errorAllegati").remove();
					
				}
			});
			error = true;
		}
		else
		{
			$(".errorAllegati").remove();
			$("#errorImgAllegati").remove();
			$(".inputFile").css("cssText", "border-top: 2px solid green !important;border-left: 2px solid green !important;border-right: 2px solid green !important;");
			$("#selectedFiles").css("cssText", "border-bottom: 2px solid green !important;border-left: 2px solid green !important;border-right: 2px solid green !important;");
			$("#listaAllegati").css("cssText", "border-bottom: 2px solid green !important;border-left: 2px solid green !important;border-right: 2px solid green !important;");

		}
		
		if(valCategoria == false)
		{
			categoriaCheck.css("cssText", "border: 2px solid red !important;");
			$(".errorCategoria").remove();
			$("#errorImgCategoria").remove();
			
			$("<img src='icone/close.svg' id='errorImgCategoria' style='width:20px;'>").insertAfter(categoriaCheck);
			$(".errorImgCategoria")
			$("#errorImgCategoria").click(function(){
				if($(".errorCategoria").val() == undefined)
				{
					$("<div class='errorCategoria'><label>Devi selezionare almeno una categoria</label></div>").insertAfter(categoriaCheck);
					$(".errorCategoria").css("cssText", "display:block;position: relative;bottom: 30px;font-size: 10px;width: 80%;background: white;padding: 10px;color: red;border: 1px solid red;left: 10%;");
				}
				else
				{
					$(".errorCategoria").remove();
					
				}
			});
			error = true;
		}
		else
		{
			categoriaCheck.css("cssText", "border: 2px solid green !important;");
		}
		
		return error;
	}
	
	