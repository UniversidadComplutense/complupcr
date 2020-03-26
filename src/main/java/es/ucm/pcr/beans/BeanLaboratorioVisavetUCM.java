package es.ucm.pcr.beans;

import java.util.List;

public class BeanLaboratorioVisavetUCM {
private List<BeanLote> listaLotes;
private String nombre;
private String id;
private String universidad;
public List<BeanLote> getListaLotes() {
	return listaLotes;
}
public void setListaLotes(List<BeanLote> listaLotes) {
	this.listaLotes = listaLotes;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getUniversidad() {
	return universidad;
}
public void setUniversidad(String universidad) {
	this.universidad = universidad;
}

}
