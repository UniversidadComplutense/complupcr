


function anotarPlacasSeleccionadas() {

	var numPlacasVisavet = $('#tablaPlacasVisavet tr').length;
	var placasElegidas = '';
	if (numPlacasVisavet > 0) {	 
		for (var i=0; i<numPlacasVisavet;i++){
			var checkSeleccionado='#chkPlaca'+i;
			if ($(checkSeleccionado).is(':checked')) {		
				placasElegidas += $(checkSeleccionado).val() + ":";
			}
		}
	}
	$('#placasSeleccionadas').val(placasElegidas);
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






