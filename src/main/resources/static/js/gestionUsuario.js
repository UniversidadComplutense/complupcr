
function ocultarCentrosInicial(){
//	Todos los radio deben tener el mismo name para poder obtener su valor
	var tipoCentro = document.formularioUsuario.tipoCentroSeleccionado.value;
	
	if (tipoCentro == "C"){
		centroSalud();
	}
	if (tipoCentro == "L"){
		labUcm();
	}
	if (tipoCentro == "V"){
		labVisavet();
	}

}

function centroSalud(){
	document.getElementById("IDCENTROSALUD").disabled = false;
	document.getElementById("IDLABUCM").disabled = true;
	document.getElementById("IDLABVISAVET").disabled = true;
}

function labUcm(){
	document.getElementById("IDCENTROSALUD").disabled = true;
	document.getElementById("IDLABUCM").disabled = false;
	document.getElementById("IDLABVISAVET").disabled = true;
}

function labVisavet(){
	document.getElementById("IDCENTROSALUD").disabled = true;
	document.getElementById("IDLABUCM").disabled = true;
	document.getElementById("IDLABVISAVET").disabled = false;
}


			