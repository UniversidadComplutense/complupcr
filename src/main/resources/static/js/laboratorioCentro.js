


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

function anotarEquipoSeleccionado() {
	
	var idEquipo = $('#selectAsignarEquipo').val();
	$('#idEquipoSeleccionado').val(idEquipo);
}

function cancelarAsignacionEquipoPCR() {
	
	$('#selectAsignarEquipo').val(0);
	$('#idEquipoSeleccionado').val(0);
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

// Inhabilita buscar por fechas si no se ha seleccionado el estado, para buscar
// por fecha de envío o de recepción
function revisarFechas(){
	
	if ($('#selectorEstados option:selected').text() == 'Seleccione'){
		$('#fechaInicio').val(null);
		$('#fechaInicio').attr('disabled','disabled');
		$('#fechaFin').val(null);
		$('#fechaFin').attr('disabled','disabled');		
	} else {
		$('#fechaInicio').removeAttr('disabled');
		$('#fechaFin').removeAttr('disabled');
	}
}

function borrarInputs(){

	$('#selectorCapacidad').val(null);
	$('#selectorEstados').val(0);
	$('#fechaInicio').val(null);
	$('#fechaFin').val(null);
}

function borrarCodigo(){

	$('#codigo').val(null);
}

// Confirmar la recepción de una placa Visavet
function confirmarRecepcionPlacaVisavet(){	
	$("#formGuardarRecepcionPlaca").submit();
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

function confirmarRecepcionPlacaVisavetDesdeModal(idPlaca){
	var url="/laboratorioCentro/recepcionPlacas/recepciona?id=" + idPlaca;
	window.location=url;
}

function asignarEquipoPCRDesdeModal(idPlaca){	
	var selectorEquipos = $("*").find("[data-idPlaca='" + idPlaca + "']"); 
	var idEquipo = selectorEquipos.val();
	var url="/laboratorioCentro/gestionPlacas/asignaEquipo?id=" + idPlaca + "&idEquipo=" + idEquipo;
	window.location=url;
}

function confirmarListaParaAnalizarDesdeModal(idPlaca){
	var url="/laboratorioCentro/gestionPlacas/resultado?id=" + idPlaca;
	window.location=url;
}

function confirmarFinalizadoPCRDesdeModal(idPlaca){
	var url="/laboratorioCentro/gestionPlacas/finalizaPCR?id=" + idPlaca;
	window.location=url;
}






