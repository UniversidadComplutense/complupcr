
function vacio(s) {
	if (s == null || s == "" || esblanco(s))
		return true;
	return false;
}
function esblanco(s) {
	for (var i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if ((c != ' ') && (c != '\n') && (c != '\t'))
			return false;
	}
	return true;
}


//añadido Diana metodo que se ejecuta al marcar o desmarcar el check de cerrar de un registro de la lista de muestras
//añade un nuevo elemento o lo quita de la lista de codnumsmuestra de elementos a cerrar
function AniadirOQuitarElementoAListaDeMuestrasACerrar(input, idmuestra) {
	//recibe el id de la muestra y el input sobre el que veremos si ha marcado llamado o no
	//alert("estoy en AniadirOQuitarElementoAListaDeMuestrasACerrar");
	
	//alert("Clicked, new value = " + input.checked);
	//alert("id muestra =  " + idmuestra);
	var listaCodnumMuestrasMarcadasParaCerrar = $('#idListaCodnumMuestrasMarcadasParaCerrar').val();
	//alert("ANTES listaCodnumMuestrasMarcadasParaCerrar =  " + listaCodnumMuestrasMarcadasParaCerrar);
	
	//if (EstaConectado()) {	
		
		if(input.checked== true){			
			//alert("añado elemento a la lista");
			//guardamos su codnum de muestra en la lista de elementos a cerrar
			listaCodnumMuestrasMarcadasParaCerrar += vacio(listaCodnumMuestrasMarcadasParaCerrar) ? idmuestra : "," + idmuestra;
			//alert("DESPUES listaCodnumMuestrasMarcadasParaCerrar =  " + listaCodnumMuestrasMarcadasParaCerrar);
			//volvemos a asignarlo al elemento en la vista
			$('#idListaCodnumMuestrasMarcadasParaCerrar').val(listaCodnumMuestrasMarcadasParaCerrar);
		}
		else{
			//si le quita el check tenemos que eliminar su codnum de la listaCodnumMuestrasMarcadasParaCerrar			
			//alert("quito elemento a la lista");
			var tieneComaAntes = listaCodnumMuestrasMarcadasParaCerrar.indexOf(","+idmuestra) != -1;
			var tieneComaDespues = listaCodnumMuestrasMarcadasParaCerrar.indexOf(idmuestra+",") != -1;			
			//var noTieneComas = !tieneComaAntes && !tieneComaDespues;
			//miro si idmuestra es el unico elemento de la lista (si no tiene comas ni atras ni alante)
			if(!tieneComaAntes && !tieneComaDespues){
				//alert("el emento es unico, lo quito");
				listaCodnumMuestrasMarcadasParaCerrar = listaCodnumMuestrasMarcadasParaCerrar.replace(idmuestra, "");
			}
			if(!tieneComaAntes && tieneComaDespues){
				//alert("es el primer elemento, lo quito con su coma de despues");
				listaCodnumMuestrasMarcadasParaCerrar = listaCodnumMuestrasMarcadasParaCerrar.replace(idmuestra+",", "");
			}
			if(tieneComaAntes && !tieneComaDespues){
				//alert("es el ultimo elemento, lo quito con su coma de antes");
				listaCodnumMuestrasMarcadasParaCerrar = listaCodnumMuestrasMarcadasParaCerrar.replace(","+idmuestra, "");
			}
			if(tieneComaAntes && tieneComaDespues){
				//alert("es un elemento de enmedio, lo quito con su coma de despues por ejemplo");
				listaCodnumMuestrasMarcadasParaCerrar = listaCodnumMuestrasMarcadasParaCerrar.replace(idmuestra+",", "");
			}
			
			//alert("DESPUES listaCodnumMuestrasMarcadasParaCerrar =  " + listaCodnumMuestrasMarcadasParaCerrar);
			//volvemos a asignarlo al elemento en la vista
			$('#idListaCodnumMuestrasMarcadasParaCerrar').val(listaCodnumMuestrasMarcadasParaCerrar);
		}
		
	//}	
	
}

//TODO esto lo necesitaré cuando haya paginacion para no perder los marcados al movernos de una pagina a otra
//Diana marcar llamamientos usuario cada vez que pulsamos en los botones de paginar
//para no perder al paginar los registros que ha marcado el usuario para llamar
//lo llamamos desde paginaRegistroTablaGestorListaEspera
function Marcarllamamientos() {
	
	//alert("estoy en ejecutar Marcarllamamientos");	
	
	//recogemos la lista de codnumlee de los registros que ha marcado para llamar en cada pagina
	var listaCodnumLEEMarcadosParaLlamamiento = $('#idListaCodnumLEEMarcadosParaLlamamiento').val();
	//alert("listaCodnumLEEMarcadosParaLlamamiento =  " + listaCodnumLEEMarcadosParaLlamamiento);		
	
	if (EstaConectado()) {
			
			var estudioRows = $('#listadoestudios tr');		
			//recorremos todas las filas
			for (var i = 0; i < estudioRows.length; i++) {
				var leeCodnum = $('#leeRow' + i).find(".leeIDCell").html();
				//alert(leeCodnum);
				
				//si el leeCodnum esta en la lista de Codnumlee marcados para el llamamiento le ponemos el checked
				
				if (listaCodnumLEEMarcadosParaLlamamiento.indexOf(leeCodnum) != -1) {
					//alert ("hay que marcar el leecodnum!" + leeCodnum);
					//$('#leeRow' + i).find(".leeLlamadocheckbox").is(':checked')= true;
					$('#leeRow' + i).find(".leeLlamadocheckbox").prop('checked', true);
					//input.checked = true;
				}
				
			}					
	}
	return false;
}

//de titulos loadUploadAjaxDocumSecret loadUploadAjaxDocumSecret(codNumDocAportar, codNumTipoDoc, descTipoDoc) {
function cargarModalCerrarMuestrasOLD() {
	alert("estoy en  cargarModalCerrarMuestrasOLD")
	
	/*
	// vamos a hacer una llamada ajax que nos devuelva un json para saber si tenemos sesion o ha expirado 
	$.ajax({
		type: 'GET',
        dataType : 'html',
		url: "/gestor/getSession",
		
	}).done(function(resp) {
		
	    if (resp.search('OK') == -1) // no tiene sesion le redirigimos a la pagina de login
	      window.location.href = "/gestor/buscarPreinscripciones";
	    else {
	    	
	*/
	/*   
	var $modalSubirDoc = $("#modalCerrarMuestras");
	$modalSubirDoc.html("");
	
	$modalSubirDoc.html("HOLAAAAAA");
	*/
	var $modalCerrarMuestrasBody = $("#modalCerrarMuestrasBody");
	$modalCerrarMuestrasBody.html("");
	
	$modalCerrarMuestrasBody.html("HOLAAAAAA");
	
	
	
	
	/*
	//var $form = $("#formSecretDocumentVal");
	var $form = $("#formBusqueda");
	
	
	var url = "/gestor/formulariosubirdocSecre?codNumDocAportar=" + codNumDocAportar 
	+ "&codNumTipoDoc=" + codNumTipoDoc + "&descTipoDoc=" + descTipoDoc;
	*/
	
	/*
	if ($form.length){
		//showLoading();
		$.ajax({
			type: 'GET',
            dataType : 'html',
			url: url,			
		}).done(function(resp) {
			$modalSubirDoc.html(resp);
			
		
			//hideLoading();
			//document.getElementById("file2Upload").focus();
		}).fail(function(resp) {
			$modalSubirDoc.html(resp);
			//hideLoading();
		});
	}
	*/
	
	
	/*
	    }  
    	
    }).fail(function(resp) {
    	
	});
	*/	   
}


//TODO si queremos mostrar un modal con las muestras marcadas para cerrar
//comprueba que se hayan marcado registros para cerrar (si no hay marcados muestra modal alert) y si hay marcadas para cerrar se abre modal para solicitar confirmacion
function cargarModalCerrarMuestras(){
	
	//alert("estoy en  cargarModalCerrarMuestras")
	
	//if (EstaConectado()) {
		
		//comprobación de si se ha marcado algún registro nuevo para incluirlo en la lista de muestras a cerrar
		//miramos si hay algo en la idListaCodnumMuestrasMarcadasParaCerrar de los registros que ha marcado para cerrar en todas las paginas
		var claves = $('#idListaCodnumMuestrasMarcadasParaCerrar').val();
		//alert("ListaCodnumMuestrasMarcadasParaCerrar =  " + claves);
		
		//si no hemos marcado nigun registro para cerrar
		if (vacio(claves)){			
			//alert("debe marcar al menos un registro para cerrar");
			//VentanaModalAviso("idventanaemergente", 300, "<div class=\"advertencia\" style=\"background-position-y: center;\">Debe marcar al menos un registro para el llamamiento</div>");
			
			// Display error message to the user in a modal
			$('#alert-modal-title').html("Cerrar muestras");
			$('#alert-modal-body').html("Debe marcar al menos un registro para cerrar");
			$('#alert-modal').modal('show');
			
			//var $modalCerrarMuestrasBody = $("#modalCerrarMuestrasBody");
			//$modalCerrarMuestrasBody.html("");			
			//$modalCerrarMuestrasBody.html("<div class=\"warning\" style=\"background-position-y: center;\">Debe marcar al menos un registro para cerrar</div>");
			return false;			
		}
		//si hemos marcado registros de muestras para cerrar, abrimos un modal para mostrar las muestras marcadas para cerrar y solicitar confirmación
		else{
			MostrarMuestrasACerrar();			
		}
	
	//}
	//return false;
}

function MostrarMuestrasACerrar() {
	
	//alert("estoy en MostrarMuestrasACerrar");
	
	//if (EstaConectado()) {
		
		//cogemos la lista de codnum de muestras que se han marcado para cerrar en todas las paginas
		var claves = $('#idListaCodnumMuestrasMarcadasParaCerrar').val();
			
		//alert(claves);
		
		//$('#idavisos').html("Salvando modificaciones del usuario").css('display', 'block');	
			
		//url = $('#idrealformulariobusqueda').attr('action')  + "/buscarAlumnosAIncluirEnLlamamiento";
		url = "/analisis/buscarMuestrasACerrar";
		
			
			sBody = $('#formBusqueda').serialize() + "&claves=" + claves;
			$.ajax({
				type : 'POST',
				url : url,
				//beforeSend : function() {
					//showSpinner();
				//},
				dataType : 'html',
				data : sBody,
				//complete : function() {
					//hideSpinnerAsistente();
				//}
			}).done(function(respuesta) {			
				//alert("estoy en done");
				//VentanaModalAvisoSinBoton("idventanaemergente", 640, respuesta);
				$('#exampleModalScrollableTitle').html("");
				$('#exampleModalScrollableTitle').html("Muestras a cerrar");				
				$("#modalCerrarMuestrasBody").html("");			
				$("#modalCerrarMuestrasBody").html(respuesta);
				var $modalCerrarMuestras = $("#modalCerrarMuestras");
				$modalCerrarMuestras.modal('show');
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				//$('#idavisos').html("ERROR: No he podido conectar con el servidor para realizar la acción").css('color', "#f00");
			});
		//}
	
	return false;
}

//diana cerrar muestras con paginacion 
//(no recogemos los que hay marcados para llamar en la vista porque puede haber marcados en otras pagians, los tenemos todos en idListaCodnumMuestrasMarcadasParaCerrar)
	
function EjecutarCierreMuestras() {
	
	//alert("estoy en EjecutarCierreMuestraso");
	
	//if (EstaConectado()) {			
			
			//nulificarInputsVentanaAsistente();
			$("#modalCerrarMuestras").modal('hide');
			
			//recogemos la lista de codnums de los registros que ha marcado para cerrar en todas las paginas
			var listaCodnumMuestrasMarcadasParaCerrar = $('#idListaCodnumMuestrasMarcadasParaCerrar').val();
			alert("listaCodnumMuestrasMarcadasParaCerrar =  " + listaCodnumMuestrasMarcadasParaCerrar);
					
						
			//$('#idavisos').html("Salvando modificaciones del usuario").css('display', 'block');	
			
			//url = $('#idrealformulariobusqueda').attr('action')  + "/ejecutarllamamiento";
			url = "/analisis/ejecutarCierreMuestras";
			
			sBody = $('#formBusqueda').serialize() + "&claves=" + listaCodnumMuestrasMarcadasParaCerrar;		
			
			$.ajax({
				type : 'POST',
				url : url,
				//beforeSend : function() {
					//showSpinner();
				//},
				dataType : 'html',
				data : sBody,
				//complete : function() {
					//hideSpinnerAsistente();
				//}
			}).done(function(respuesta) {
				//CloseModalFade();
				$("#modalCerrarMuestras").modal('hide');
				//despues de ejecutar el cierre de muestras tenemos que vaciar la lista de los marcados para cerrar
				$("#idListaCodnumMuestrasMarcadasParaCerrar").val("");				
				/*
				$('#idbotones').html('');
				$('#idcuerpo').html(respuesta);
				$('#idavisos').html("").css('display', 'none');
				$('#idbotones').html($('#parabotones').html());
				$('td.cursorpointer').each(function() {
					$(this).hover(function() {
						$(this).css('cursor', 'pointer');
					}, function() {
						$(this).css('cursor', 'auto');
					});
				
				});*/
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				//$('#idavisos').html("ERROR: No he podido conectar con el servidor para realizar la acción").css('color', "#f00");
			});
		
	//}
	return false;
}


function cargarModalReemplazarAnalista(idPlaca, idUsuarioAReemplazar){
	
	alert("estoy en  cargarModalReemplazarAnalista");
	alert("el usurio a reemplazar es: "+ idUsuarioAReemplazar);
	alert("en la placa con id : "+ idPlaca);
	
	
	url = "/analisis/reemplazarAnalista?idUsuarioAReemplazar=" + idUsuarioAReemplazar + "&idPlaca=" + idPlaca;
				
		//sBody = $('#formularioGuardarAsignacion').serialize() + "&idUsuarioAReemplazar=" + idUsuarioAReemplazar + "&idPlaca=" + idPlaca;
		$.ajax({
			type : 'GET',
			url : url,
			//beforeSend : function() {
				//showSpinner();
			//},
			dataType : 'html',
			//data : sBody,
			//complete : function() {
				//hideSpinnerAsistente();
			//}
		}).done(function(respuesta) {			
			//alert("estoy en done");
			//VentanaModalAvisoSinBoton("idventanaemergente", 640, respuesta);
			$('#exampleModalScrollableTitle').html("");
			$('#exampleModalScrollableTitle').html("Reemplazar analista");				
			$("#modalReemplazarAnalistaBody").html("");			
			$("#modalReemplazarAnalistaBody").html(respuesta);
			var $modalReemplazarAnalista = $("#modalReemplazarAnalista");
			//$('.selectpicker').selectpicker('refresh');
			//$('.selectpicker').selectpicker();
			$('#analistasLabSeleccionadoReemp').selectpicker('refresh');
			$('#analistasVolSeleccionadoReemp').selectpicker('refresh');
			$('#analistasVolSinLabCentroSeleccionadoReemp').selectpicker('refresh');	
			$modalReemplazarAnalista.modal('show');
			
		}).fail(function(jqXHR, textStatus, errorThrown) {
			//$('#idavisos').html("ERROR: No he podido conectar con el servidor para realizar la acción").css('color', "#f00");
		});
	//}
	
	return false;
}


function EjecutarReemplazoAnalista(){
	
	alert("estoy en  EjecutarReemplazoAnalista");
	//alert("el usurio a reemplazar es: "+ idUsuarioAReemplazar);
	//alert("en la placa con id : "+ idPlaca);
	
	//cojo los input
	var idPlaca = $('#idPlaca').val();
	var idUsuarioAReemplazar = $('#idUsuarioAReemplazar').val();
	var analistasLabSeleccionadoReemp = $('#analistasLabSeleccionadoReemp').val();
	var analistasVolSeleccionadoReemp = $('#analistasVolSeleccionadoReemp').val();
	var analistasVolSinLabCentroSeleccionadoReemp = $('#analistasVolSinLabCentroSeleccionadoReemp').val();
	
	alert("analistasLabSeleccionadoReemp: " +analistasLabSeleccionadoReemp);
	alert("analistasVolSeleccionadoReemp: " + analistasVolSeleccionadoReemp);
	alert("analistasVolSinLabCentroSeleccionadoReemp: " + analistasVolSinLabCentroSeleccionadoReemp);
	
	alert("tamaño analistasLabSeleccionadoReemp: " + analistasLabSeleccionadoReemp.length);
	alert("tamaño analistasVolSeleccionadoReemp: " + analistasVolSeleccionadoReemp.length);
	alert("tamaño analistasVolSinLabCentroSeleccionadoReemp: " + analistasVolSinLabCentroSeleccionadoReemp.length);
		
	
	if (analistasLabSeleccionadoReemp.length + analistasVolSeleccionadoReemp.length + analistasVolSinLabCentroSeleccionadoReemp.length != 1) {
		alert("solo se puede escoger un analista!!");
		$('#labelErrorAnalistasPermitidos').html("solo se puede escoger un analista");
		$('#labelErrorAnalistasPermitidos').show();
		//$('#divEstudiosError').show();
	}
	else{		
		$('#labelErrorAnalistasPermitidos').hide();
		
		var idUsuarioAPoner;
		if(analistasLabSeleccionadoReemp.length == 1){
			idUsuarioAPoner = analistasLabSeleccionadoReemp;
		}
		if(analistasVolSeleccionadoReemp.length == 1){
			idUsuarioAPoner = analistasVolSeleccionadoReemp;
		}
		if(analistasVolSinLabCentroSeleccionadoReemp.length == 1){
			idUsuarioAPoner = analistasVolSinLabCentroSeleccionadoReemp;
		}
	
		alert("el usuario a poner tiene el id: " + idUsuarioAPoner);
		
		url = "/analisis/guardaReemplazarAnalista";
					
			sBody = $('#formularioReemplazarAsignacion').serialize() + "&idUsuarioAQuitar=" + idUsuarioAReemplazar + "&idUsuarioAPoner=" + idUsuarioAPoner + "&idPlaca=" + idPlaca;
			$.ajax({
				type : 'POST',
				url : url,
				//beforeSend : function() {
					//showSpinner();
				//},
				dataType : 'html',
				data : sBody,
				//complete : function() {
					//hideSpinnerAsistente();
				//}
			}).done(function(respuesta) {
				$("#modalReemplazarAnalista").modal('hide');
				//alert("estoy en done");				
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				//$('#idavisos').html("ERROR: No he podido conectar con el servidor para realizar la acción").css('color', "#f00");
			});
		//}
		
		return false;
	}
}


/*function EjecutarReemplazoAnalista() {
	alert("estoy en EjecutarReemplazoAnalista");
	
	//metodo que se ejecuta cuando pulsamos sobre el botón guardar del modal de reemplazar analista
	
	url = "/analisis/guardaReemplazarAnalista";
	
	//sBody = $('#formBusqueda').serialize() + "&claves=" + listaCodnumMuestrasMarcadasParaCerrar;		
	
	$.ajax({
		type : 'POST',
		url : url,
		//beforeSend : function() {
			//showSpinner();
		//},
		dataType : 'html',
		//data : sBody,
		//complete : function() {
			//hideSpinnerAsistente();
		//}
		success: function (result) {	
		   //por aquí entrará tanto si se cumplen los validadores como si no
	 	   //console.log("ha pasado por done");
	 	   if (result == "") { 		
	 		   //si no tenemos errores de validacion (hemos devuelto un null como modelAndView; su html es el "") 
	 		   //loadDetailPreinscripDiv('documentVal');
	 		  $("#modalReemplazarAnalista").modal('hide');
	 	   }
	 	   else { 
	 		   //si nos dan errores de validacion seguimos mostrando el modal de $("#modalReemplazarAnalista") con los errores de validacion
	 		  $("#modalReemplazarAnalista").html("");
	 		  $("#modalReemplazarAnalista").html(result);			  
	 	   }
		},
        error: function (error) {
     	   //por aquí entrará cuando se produzca una excepcion y nos redireccionará a la pagina de tratamiento de excepciones
     	   //console.log("ha pasado por error");
     	   $('html').html("");
     	   $('html').html(error.responseText);
     	   hideLoading();
        }
    });	
	
}*/
		
		
	


/*
function EjecutarReemplazoAnalista() {
	
	alert("estoy en EjecutarReemplazoAnalista");
	
	//if (EstaConectado()) {			
			
			//nulificarInputsVentanaAsistente();
			$("#modalReemplazarAnalista").modal('hide');
			
			//recogemos la lista de codnums de analistas lab, analistas vol y analistas vol sin centro que ha marcado para el reemplazo
			var listaCodnumMuestrasMarcadasParaCerrar = $('#idListaCodnumMuestrasMarcadasParaCerrar').val();
			alert("listaCodnumMuestrasMarcadasParaCerrar =  " + listaCodnumMuestrasMarcadasParaCerrar);
					
						
			//$('#idavisos').html("Salvando modificaciones del usuario").css('display', 'block');	
			
			//url = $('#idrealformulariobusqueda').attr('action')  + "/ejecutarllamamiento";
			url = "/analisis/ejecutarCierreMuestras";
			
			sBody = $('#formBusqueda').serialize() + "&claves=" + listaCodnumMuestrasMarcadasParaCerrar;		
			
			$.ajax({
				type : 'POST',
				url : url,
				//beforeSend : function() {
					//showSpinner();
				//},
				dataType : 'html',
				data : sBody,
				//complete : function() {
					//hideSpinnerAsistente();
				//}
			}).done(function(respuesta) {
				//CloseModalFade();
				$("#modalCerrarMuestras").modal('hide');
				//despues de ejecutar el cierre de muestras tenemos que vaciar la lista de los marcados para cerrar
				$("#idListaCodnumMuestrasMarcadasParaCerrar").val("");				
				
				
			}).fail(function(jqXHR, textStatus, errorThrown) {
				//$('#idavisos').html("ERROR: No he podido conectar con el servidor para realizar la acción").css('color', "#f00");
			});
		
	//}
	return false;
}
*/

function loadAnalistas(id) {

	var url = "";
	var urlAbs = getAbsolutePath();
	var lista = "";
	var texto = "";
	url = '/analisis/consultaAnalistas/?id=' + id;

	$.ajax({
		type : 'GET',
		url : url,
		dataType : 'html'
	}).done(function(respuesta) {
		switch (id) {
		case 'LAB':
			$("#analistasLabSeleccionado").html(respuesta);
			break;
		case 'PDI':
			$("#centroPDIselccionado").html(respuesta);
			break;
		case 'PAS':
			$("#centroPASselccionado").html(respuesta);
			break;
		}
		$('.selectpicker').selectpicker('refresh');
	});

};

function loadEstudiosCentros() {

	var url = "";
	var urlAbs = getAbsolutePath();
	var texto = "";
	var lista = "";
	centrosEselccionado = $('#centroEselccionado').val();
	url = urlAbs + '/consultaEstudiosUsuario/?ids=' + centrosEselccionado;

	$.ajax({
		type : 'GET',
		url : url,
		dataType : 'html'
	}).done(function(respuesta) {

		$("#estudiosEselccionado").html(respuesta);
		$('.selectpicker').selectpicker('refresh');

	});

};

function getAbsolutePath() {

	return window.location.origin;
};