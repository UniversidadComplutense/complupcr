/**
 * 
 */function eliminaFilas()
{
//OBTIENE EL NÚMERO DE FILAS DE LA TABLA
var n=0;
$("#tablaResultadosLotes tbody tr").each(function () 
{
n++;
});
//BORRA LAS n-1 FILAS VISIBLES DE LA TABLA
//LAS BORRA DE LA ULTIMA FILA HASTA LA SEGUNDA
//DEJANDO LA PRIMERA FILA VISIBLE, MÁS LA FILA PLANTILLA OCULTA
for(i=n-1;i>0;i--)
{
$("#tablaResultadosLotes tbody tr:eq('"+i+"')").remove();
};
$("#tablaResultadosLotes thead").remove();
};
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
	$("#trGroup").html(respuesta);
	});

}
/* carga el modal de  Confirmar Lote Recibido */
function loadConfirmarEnvio(id, numLote, centroProcedencia){
	
	$("#mensajeConfirmar").html(numLote);
	$("#mensajeCentroProcedenciaCentro").html(centroProcedencia);
	$("#id").val(id);
}
/* funcion que  realia una llamada ajax para cambiar el estado de un lote y recargar tabla de resultados */
function confirmarLote(){
	var url = "";
	//var urlAbs = getAbsolutePath();
	var id=$("#id").val();
	url =  '/laboratorioUni/confirmarReciboLote?id='+id;
	sBody=$('#formularioPrueba').serialize();

	$.ajax({
        type:  'GET',
        url:   url,
        dataType: 'html',
        data:  sBody
	}).done(function(respuesta) {
		$("#capaResultados").html(respuesta);
		habilitarBotonProcesar();
	});
}
function consultarMuestras(lote,centroProcedencia,id){
	var url = "";
	//var urlAbs = getAbsolutePath();
	
	url =  '/laboratorioUni/mostrarMuestras?id='+id;
	
	sBody=$('#formularioPrueba').serialize();

	$.ajax({
        type:  'GET',
        url:   url,
        dataType: 'html',
        data:  sBody
	}).done(function(respuesta) {
		alert(respuesta);
		$("#trMuestra").html(respuesta);
	});
	
	
	$("#mensajeCentroProcedenciaCentroMuestra").html(centroProcedencia);
	$("#modalTitleNombreMuestra").append(lote);
	
	
}
//funcion que obtiene los checkbox pulsados y se los envia al controlador
function procesarLotes() {
	var nFilas = $("#tablaResultadosLotes tr").length;
	var lotesProcesar="";
	if (nFilas>0) {
	 
	 for (var i=0; i<nFilas;i++){
		 var seleccionado="#seleccionado"+i;
		if ($(seleccionado).is(':checked')) {
			//lotesProcesar
			lotesProcesar+=$(seleccionado).val()+"&";
		}
	 }
	}
	var url="/laboratorioUni/procesarLotes?lotes="+lotesProcesar;
	window.location=url;
}
