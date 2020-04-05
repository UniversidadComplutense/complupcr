package es.ucm.pcr.beans;

import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Lote;

public class CentroBean {
private int id;
private String nombre;
private String codCentro;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getCodCentro() {
	return codCentro;
}
public void setCodCentro(String codCentro) {
	this.codCentro = codCentro;
}
public static CentroBean modelToBean(Centro centro) {
	CentroBean beanCentro= new CentroBean();
	beanCentro.setId(centro.getId());
	beanCentro.setNombre(centro.getNombre());
	beanCentro.setCodCentro(centro.getCodCentro());
	return beanCentro;
}
public static Centro beanToModel(CentroBean centroBean) {
	Centro centro= new Centro();
	centro.setId(centroBean.getId());
	centro.setNombre(centroBean.getNombre());
	centro.setCodCentro(centroBean.getCodCentro());
	return centro;
}
}
