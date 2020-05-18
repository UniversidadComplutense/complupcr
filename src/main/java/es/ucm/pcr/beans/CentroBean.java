/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

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
