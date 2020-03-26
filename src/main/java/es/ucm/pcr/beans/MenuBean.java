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
