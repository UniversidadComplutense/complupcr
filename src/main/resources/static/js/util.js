/** Limpia los campos de un formulario **/
function resetForm(idForm) {
	$('#' + idForm).find("input[type=text], textarea, select").val("");
	$('#' + idForm).find("input[type=date]").val("");
}