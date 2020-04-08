


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
}





