package es.ucm.pcr.beans;

import java.util.HashSet;
import java.util.Set;

import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.modelo.orm.Documento;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Usuario;

public class BeanCentro implements Comparable<BeanCentro> {
	
	private Integer id;
	private String nombre;
	private String codCentro;
	private String telefono;
	private String email;
	private String direccion;
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);
	private Set<Muestra> muestras = new HashSet<Muestra>(0);
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<Lote> lotes = new HashSet<Lote>(0);
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanCentro() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public BeanCentro(Integer id, String nombre, String codCentro, String telefono, String email, String direccion,
			Set<Usuario> usuarios, Set<Muestra> muestras, Set<Documento> documentos, Set<Lote> lotes, String accion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codCentro = codCentro;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
		this.usuarios = usuarios;
		this.muestras = muestras;
		this.documentos = documentos;
		this.lotes = lotes;
		this.accion = accion;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
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


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public Set<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}


	public Set<Muestra> getMuestras() {
		return muestras;
	}


	public void setMuestras(Set<Muestra> muestras) {
		this.muestras = muestras;
	}


	public Set<Documento> getDocumentos() {
		return documentos;
	}


	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}


	public Set<Lote> getLotes() {
		return lotes;
	}


	public void setLotes(Set<Lote> lotes) {
		this.lotes = lotes;
	}


	public String getAccion() {
		return accion;
	}


	public void setAccion(String accion) {
		this.accion = accion;
	}


	@Override
	public String toString() {
		return "BeanCentro [id=" + id + ", nombre=" + nombre + ", codCentro=" + codCentro + ", telefono=" + telefono
				+ ", email=" + email + ", direccion=" + direccion + ", usuarios=" + usuarios + ", muestras=" + muestras
				+ ", documentos=" + documentos + ", lotes=" + lotes + ", accion=" + accion + "]";
	}
	


	@Override
    public int compareTo(BeanCentro o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }		

}
