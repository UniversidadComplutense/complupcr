function eliminaFilas()
{
//OBTIENE EL NÚMERO DE FILAS DE LA TABLA
var n=0;
$("#tablaResultados tbody tr").each(function () 
{
n++;

});

//BORRA LAS n-1 FILAS VISIBLES DE LA TABLA
//LAS BORRA DE LA ULTIMA FILA HASTA LA SEGUNDA
//DEJANDO LA PRIMERA FILA VISIBLE, MÁS LA FILA PLANTILLA OCULTA
for(i=n-1;i>0;i--)
{
	
$("#tablaResultados tbody tr:eq('"+i+"')").remove();

};
//$("#tablaResultadosLotes thead").remove();
};
function eliminaFilastrGroup(){
	//OBTIENE EL NÚMERO DE FILAS DE LA TABLA
	var n=0;
	$("#trGroup").each(function () 
	{ 
		$("#trGroup").remove();
	
	n++;
	});

	//BORRA LAS n-1 FILAS VISIBLES DE LA TABLA
	//LAS BORRA DE LA ULTIMA FILA HASTA LA SEGUNDA
	//DEJANDO LA PRIMERA FILA VISIBLE, MÁS LA FILA PLANTILLA OCULTA
	/*for(i=n-1;i>0;i--)
	{
	$("#tablaResultados tbody tr:eq('"+i+"')").remove();

	};*/
}
function eliminarCabecera(){
	$("#tablaResultadosCabecera thead").remove();
	$("#tablaResultadosCabecera").append("<thead><th>#Lote</th><th>Centro</th><th>F.Entrada</th><th>#Muestras</th><th>Test</th><th>Estado</th></thead>");

}
function loadTabla(i) {

	var url = "";
	//var urlAbs = getAbsolutePath();
	
	url =  '/laboratorioUni/actualizarLote?id=' + i;
	sBody=$('#formularioPrueba').serialize();
	$.ajax({
        type:  'POST',
        url:   url,
        dataType: 'html',
        data:  sBody
	}).done(function(respuesta) {
		
		eliminaFilas();
		//$("#tablaResultadosLotes").append("<thead><th>#Lote</th><th>Centro</th><th>F.Entrada</th><th>#Muestras</th><th>Test</th><th>Estado</th></thead>");

		$("#trGroup").html(respuesta);
	});
	}
function habilitarBotonProcesar(){
	// si hay resultados que aparezca el botón procesarBoton
	var nFilas = $("#tablaResultadosLotes tr").length;
	if (nFilas>0) {
		$('#procesarBoton').show();
		for (var i=0; i<nFilas;i++){
		if (!$('#seleccionado'+i).is(':disabled')) {
			$('#procesarBoton').removeAttr("disabled");
		}
		}
		}
}
function buscarResultados(orden,sentidoOrden,numPagina,sizePagina){
	var url = "";
	//var urlAbs = getAbsolutePath();
	
	url =  '/laboratorioUni/buscarLotes';
	//$('#orden').val(orden);
	///$('#sentidoOrden').val(sentidoOrden);
	sBody=$('#formularioPrueba').serialize();

	$.ajax({
        type:  'POST',
        url:   url,
        dataType: 'html',
        data:  sBody
	}).done(function(respuesta) {
	$("#capaResultados").html(respuesta);
	habilitarBotonProcesar();
	});

}
function cambiarOrdenColumna(orden,sentidoOrden,numPagina,sizePagina){
	var url = "";
	//var urlAbs = getAbsolutePath();
	
	url =  '/laboratorioUni/buscarLotesGet?page='+numPagina+'&size='+sizePagina;
	$('#orden').val(orden);
	$('#sentidoOrden').val(sentidoOrden);
	sBody=$('#formularioPrueba').serialize();

	$.ajax({
        type:  'GET',
        url:   url,
        dataType: 'html',
        data:  sBody
	}).done(function(respuesta) {
	//	$("#capaResultados").show();
	//	$("#tablaResultadosCabecera").show();
		
	//	eliminarCabecera();
		//eliminaFilas();
		//$("#tablaResultadosLotes").append("<thead><th>#Lote</th><th>Centro</th><th>F.Entrada</th><th>#Muestras</th><th>Test</th><th>Estado</th></thead>");
	
		
	});

}
// carga el modal de  Confirmar Lote Recibido 
function loadConfirmarEnvio(id, numLote, centroProcedencia){
	
	$("#numeroLote").html(numLote);
	$("#centroModal").html(centroProcedencia);
	$("#id").val(id);
}
function cambiarEstadoaEnviada(id, laboratorio){
	
	$("#idPlacaConfirmar").html(id);
	$("#laboratorio").html(laboratorio);
	$("#idConfirmar").val(id);
}
function confirmarPlacaEnviada(){
	var url = "";
	//var urlAbs = getAbsolutePath();
	
	var id=$("#idConfirmar").val();
	
	url =  '/laboratorioUni/confirmarEnviadaPlaca?id='+id;
	window.location=url;
}
// funcion que  realia una llamada ajax para cambiar el estado de un lote y recargar tabla de resultados 
function confirmarLote(){

	var url = "";
	//var urlAbs = getAbsolutePath();
	
	var id=$("#id").val();
	url =  '/laboratorioUni/confirmarReciboLote?id='+id;
	window.location=url;
}

function consultarMuestras(lote,centroProcedencia,id){
	var url = "";

	
	url =  '/laboratorioUni/mostrarMuestras?id='+id;
	
	sBody=$('#formularioPrueba').serialize();

	$.ajax({
        type:  'GET',
        url:   url,
        dataType: 'html',
        data:  sBody
	}).done(function(respuesta) {
		$("#trMuestra").html(respuesta);
	});
	
	
	$("#mensajeCentroProcedenciaCentroMuestra").html(centroProcedencia);
	$("#modalTitleNombreMuestra").append(lote);
	
	
}

//funcion que obtiene los checkbox pulsados y se los envia al controlador
function procesarLotes() {

	var nFilas = $("#tablaResultados tr").length;
	var lotesProcesar="";
	if (nFilas>0) {
	 
	 for (var i=0; i<nFilas;i++){
		 var seleccionado="#seleccionado"+i;
		if ($(seleccionado).is(':checked')) {
			//lotesProcesar
		
			lotesProcesar+=$(seleccionado).val()+":";
		}
	 }
	}
	var url="/laboratorioUni/procesarLotes?lotes="+lotesProcesar;
	window.location=url;
}

// desde placas
function procesarLotesDesdePlacas(idPlaca) {
	var lotesProcesar="";
		var n=0;
		//lotesProcesar+=$("#lotes"+idPlaca +" input").val()+":";
		
			$("#lotes"+idPlaca +" input").each(function () 
					{ 
				n++;
				
		});
		
	for(i=n-1;i>=0;i--){
		
				
			lotesProcesar+=$("#lote"+idPlaca+"_"+i).val()+":";
			//	alert ("#lotes"+idPlaca+"_"+i");
				
				}		
			
	
	if (lotesProcesar !=""){
	var url="/laboratorioUni/procesarLotes?lotes="+lotesProcesar;
	window.location=url;
	}
}

// funcion que al ser pulsada da de alta una nueva placa vacia
function altaNuevaPlaca(){
	var url = "";
	//var urlAbs = getAbsolutePath();
	
	url =  '/laboratorioUni/altaPlacaVisavet';
	
	
	$.ajax({
        type:  'GET',
        url:   url,
        dataType: 'html'
       
	}).done(function(respuesta) {
	 
	   $("#criteriosBusqueda").show();
		$("#numPlacaSpan").html(respuesta);
		
	});

}

// muestra las muestras con la referencia visavet
function mostrarMuestrasPlacas(numLote){

		if ($("#muestra"+numLote).is(':visible')) {
	$("#muestra"+numLote).hide();}
		else $("#muestra"+numLote).show();
}

function asignarPlaca(){
	$("#criteriosBusqueda").show();
	var url = "";
	//var urlAbs = getAbsolutePath();
	if ($("#tamano option:selected").val() < $("#totalMuestras").text()){
		//alert ("El tamaño de la placa es menor al número de muestras");
		$("#error").show();
	}
	else {
	url="/laboratorioUni/asignarPlaca?idPlaca="+$("#numPlacaSpan").text();
	sBody=$('#formularioPrueba').serialize();
	
	$.ajax({
        type:  'GET',
        url:   url,
        dataType: 'html',
        data:  sBody
	}).done(function(respuesta) {
	eliminaFilas();
	
	    $("#tablaLotes tbody").html(respuesta);
	   // $("#trGroup").load(url, sBody);
	    $("#grabar").attr("disabled", false);
	    $("#grabaryFinalizar").attr("disabled", false);
		
	});
	}
}
function habilitarBotonAsignarLaboratorio(){
if ($("#laboratorioLab option:selected").val()=="") $("#asignarLaboratorio").attr('disabled',true);
else $("#asignarLaboratorio").attr('disabled',false);
	//if ($("#laboratorioLab option:selected").val() != null)
}

function deshabilitarBotones(fechaEnvio){
	$("#asignarLaboratorio").attr('disabled',true);
	$("#laboratorioLab").attr('disabled',true);
	$("#capaFechaEnvio").show();
	$("#fechaEnvioModal").text(fechaEnvio);
}

function consultarOcupacionLaboratorio(idPlaca, idLaboratorio, estado, fechaEnvio){
	
	var url="/laboratorioUni/consultarOcupacionLaboratorios?idPlaca="+idPlaca;
	if(idLaboratorio != null) 
	$('#laboratorioLab option[value='+idLaboratorio+']').attr('selected','selected');
	$.ajax({
        type:  'GET',
        url:   url,
        dataType: 'html'
	}).done(function(respuesta) {
		
		$("#idPlacaLab").val(idPlaca);
		$("#spanIdPlaca").text(idPlaca);
	    $("#tablaLaboratorios tbody").html(respuesta);
	   if (estado == "PLACAVISAVET_ENVIADA") deshabilitarBotones(fechaEnvio);
	    else
	    habilitarBotonAsignarLaboratorio();
	});
}

function asignarLaboratoriodesdeModal(){
	
	var url="/laboratorioUni/asignarLaboratorio?idPlaca="+$("#idPlacaLab").val()+"&laboratorio="+$("#laboratorioLab option:selected").val();
	
	
	/*$.ajax({
        type:  'GET',
        url:   url,
        dataType: 'html'
	}).done(function(respuesta) {
		alert(respuesta);
		
	    $("#trLaboratorio").html(respuesta);
		
	});  */
	window.location=url;
}

function seleccionAccion(accion){
	
	$("#accion").val(accion);

	if (comprobarReferenciasInternas())
	$("#formularioConReferencias").submit();
	
}
function habilitarBotonProcesar(){
	var nFilas = $("#tablaResultados tr").length;
	var lotesProcesar=false;
	if (nFilas>0) {
	 
	 for (var i=0; i<nFilas;i++){
		 var seleccionado="#seleccionado"+i;
		if ($(seleccionado).is(':checked')) {
			//lotesProcesar
		lotesProcesar=true;
		}
	 }
	}
	if (lotesProcesar)
	$("#procesarBoton").attr("disabled", false);
}

function comprobarReferenciasInternas(){
var respuesta=true;
	var nLotes=$("#tablaLotes .trGroupLotes").length;

	for (var i=0;i<nLotes;i++){
	 
		var muestra=$("#l"+i).val();
	    var nMuestras=$("#muestra"+muestra+" .border-tableMuestras tbody tr").length;
        
	    for (var j=0;j<nMuestras;j++){
	    	
		if ($("#ref"+muestra+"_"+j).val().trim() == "") { 
			respuesta=false;
			$("#mensaje"+muestra+"_"+j).show();
		
		}
	
	 }
	
	}
	
	 return respuesta;

}