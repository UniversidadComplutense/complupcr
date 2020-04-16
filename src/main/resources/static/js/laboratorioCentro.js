


function anotarPlacasSeleccionadas() {

	var numPlacasVisavet = $('#tablaPlacasVisavetParaCombinar tr').length;
	
	var placasElegidas = '';
	if (numPlacasVisavet > 0) {	 
		for (var i=0; i<numPlacasVisavet;i++){
			var checkSeleccionado='#chkPlacaVisavet'+i;
			if ($(checkSeleccionado).is(':checked')) {		
				placasElegidas += $(checkSeleccionado).val() + ":";
			}
		}
	}
	$('#placasSeleccionadas').val(placasElegidas);
	$('#placasSeleccionadasParaRellenar').val(placasElegidas);
	revisarEstadoBotonCrearPlaca();
	revisarEstadoBotonRellenarPlaca();
}

function revisarEstadoBotonCrearPlaca(){
	
	if ($('#placasSeleccionadas').val() != "" && $('#capacidadPlacaLab').val() != ""){
		$('#btnCrearPlaca').removeAttr('disabled');  
	} else {
		$('#btnCrearPlaca').attr('disabled','disabled');
	}
}

function revisarEstadoBotonRellenarPlaca(){
	
	if ($('#placasSeleccionadas').val() != ""){
		$('#btnRellenarPlaca').removeAttr('disabled');  
	} else {
		$('#btnRellenarPlaca').attr('disabled','disabled');
	}
}

// Dar por finalizado PCR de una placa
function finalizarPCR() {
    $("#formGuardarFinalizadoPCR").submit();
}

// Asignar equipo PCR a una placa
function asignarEquipoPCR() {
    $("#formGuardarAsignarEquipoPCR").submit();
}

// La placa está lista para analizar
function listaParaAnalizar() {
    $("#formGuardarListaParaAnalizar").submit();
}

// Rellenar una placa con más placas Visavet
function rellenarPlaca() {
    $("#formGuardarRellenarPlaca").submit();
}





