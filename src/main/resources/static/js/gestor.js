$('#confirm-borrar').on('show.bs.modal', function(e) {
	    $(this).find('.btn-borrar').attr('href', $(e.relatedTarget).data('href'));
});

function vaciar(control)
{
  control.value='';
}
	