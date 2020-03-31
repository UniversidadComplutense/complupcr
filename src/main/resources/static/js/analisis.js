
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