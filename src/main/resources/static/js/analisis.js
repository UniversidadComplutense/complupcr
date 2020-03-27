function loadAnalistas(id) {

	var url = "";
	var urlAbs = getAbsolutePath();
	var lista = "";
	var texto = "";
	url = '/consultaAnalistas/?id=' + id;

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