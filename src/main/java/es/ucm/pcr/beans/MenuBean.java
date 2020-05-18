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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author abanet
 *
 * Clase MenuBean. Abstracción de un menú usando la estructura de árbol.
 */
public class MenuBean implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String textoMenu;
	private String url;
	private List<MenuBean> hijos;
	
	
	public MenuBean() {
		super();
		this.hijos = new ArrayList<MenuBean>();
	}


	public MenuBean(String textoMenu, String url, List<MenuBean> hijos) {
		super();
		this.textoMenu = textoMenu;
		this.url = url;
		this.hijos = hijos;
	}


	public String getTextoMenu() {
		return textoMenu;
	}


	public void setTextoMenu(String textoMenu) {
		this.textoMenu = textoMenu;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public List<MenuBean> getHijos() {
		return hijos;
	}


	public void setHijos(List<MenuBean> hijos) {
		this.hijos = hijos;
	}


	@Override
	public String toString() {
		return "MenuBean [textoMenu=" + textoMenu + ", url=" + url + ", hijos=" + hijos + "]";
	}
	
	
	
	
}
