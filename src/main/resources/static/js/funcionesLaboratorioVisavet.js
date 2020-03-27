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
};
function loadTabla(i) {
  alert (i);
	var url = "";
	//var urlAbs = getAbsolutePath();
	
	url =  'https://localhost:8443/laboratorioUni/actualizarLote?id=' + i;
	
sBody=$('#formularioPrueba').serialize();
	$.ajax({
        type:  'POST',
        url:   url,
        dataType: 'html',
        data:  sBody
	}).done(function(respuesta) {
		alert(respuesta);
		eliminaFilas();
	$("#trGroup").html(respuesta);
		
	
	});
	
}