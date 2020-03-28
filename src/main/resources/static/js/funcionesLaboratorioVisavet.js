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
		$("#capaResultados").show();
		$("#tablaResultadosCabecera").show();
		
	//	eliminarCabecera();
		//eliminaFilas();
		//$("#tablaResultadosLotes").append("<thead><th>#Lote</th><th>Centro</th><th>F.Entrada</th><th>#Muestras</th><th>Test</th><th>Estado</th></thead>");
	$("#trGroup").html(respuesta);
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
function loadConfirmarEnvio(id, numLote, centroProcedencia){
	$("#mensajeConfirmar").html("Número de lote: "+numLote+" Centro de procedencia "+centroProcedencia);
}
