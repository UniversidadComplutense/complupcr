package es.ucm.pcr.beans;

import java.util.HashSet;
import java.util.Set;

import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Documento;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;

public class BeanUsuarioGestion implements Comparable<BeanUsuarioGestion>{

	private Integer id;
	private Centro centro;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private String password;
	private Integer idLaboratorioVisavet;
	private Integer idLaboratorioCentro;
	private Integer asignadas;
	private Integer acertadas;
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<UsuarioMuestra> usuarioMuestras = new HashSet<UsuarioMuestra>(0);
	private Set<Rol> rols = new HashSet<Rol>(0);
	private String habilitado;
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA DE USUARIOS
	
	
	public BeanUsuarioGestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public BeanUsuarioGestion(Integer id, Centro centro, String nombre, String apellido1, String apellido2, String email,
			String password, Integer idLaboratorioVisavet, Integer idLaboratorioCentro, Integer asignadas,
			Integer acertadas, Set<Documento> documentos, Set<UsuarioMuestra> usuarioMuestras, Set<Rol> rols,
			String habilitado, String accion) {
		super();
		this.id = id;
		this.centro = centro;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.password = password;
		this.idLaboratorioVisavet = idLaboratorioVisavet;
		this.idLaboratorioCentro = idLaboratorioCentro;
		this.asignadas = asignadas;
		this.acertadas = acertadas;
		this.documentos = documentos;
		this.usuarioMuestras = usuarioMuestras;
		this.rols = rols;
		this.habilitado = habilitado;
		this.accion = accion;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Centro getCentro() {
		return centro;
	}



	public void setCentro(Centro centro) {
		this.centro = centro;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getApellido1() {
		return apellido1;
	}



	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}



	public String getApellido2() {
		return apellido2;
	}



	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Integer getIdLaboratorioVisavet() {
		return idLaboratorioVisavet;
	}



	public void setIdLaboratorioVisavet(Integer idLaboratorioVisavet) {
		this.idLaboratorioVisavet = idLaboratorioVisavet;
	}



	public Integer getIdLaboratorioCentro() {
		return idLaboratorioCentro;
	}



	public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
		this.idLaboratorioCentro = idLaboratorioCentro;
	}



	public Integer getAsignadas() {
		return asignadas;
	}



	public void setAsignadas(Integer asignadas) {
		this.asignadas = asignadas;
	}



	public Integer getAcertadas() {
		return acertadas;
	}



	public void setAcertadas(Integer acertadas) {
		this.acertadas = acertadas;
	}



	public Set<Documento> getDocumentos() {
		return documentos;
	}



	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}



	public Set<UsuarioMuestra> getUsuarioMuestras() {
		return usuarioMuestras;
	}



	public void setUsuarioMuestras(Set<UsuarioMuestra> usuarioMuestras) {
		this.usuarioMuestras = usuarioMuestras;
	}



	public Set<Rol> getRols() {
		return rols;
	}



	public void setRols(Set<Rol> rols) {
		this.rols = rols;
	}



	public String getHabilitado() {
		return habilitado;
	}



	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}



	public String getAccion() {
		return accion;
	}



	public void setAccion(String accion) {
		this.accion = accion;
	}



	@Override
	public String toString() {
		return "BeanUsuario [id=" + id + ", centro=" + centro + ", nombre=" + nombre + ", apellido1=" + apellido1
				+ ", apellido2=" + apellido2 + ", email=" + email + ", password=" + password + ", idLaboratorioVisavet="
				+ idLaboratorioVisavet + ", idLaboratorioCentro=" + idLaboratorioCentro + ", asignadas=" + asignadas
				+ ", acertadas=" + acertadas + ", documentos=" + documentos + ", usuarioMuestras=" + usuarioMuestras
				+ ", rols=" + rols + ", habilitado=" + habilitado + ", accion=" + accion + "]";
	}



	@Override
    public int compareTo(BeanUsuarioGestion o) {
		String p1 = o.getApellido1() + o.getApellido2() + o.getNombre();
		String p2 = getApellido1() + getApellido2() + getNombre();
        String l1 = Utilidades.limpiarStringParaOrdenacion(p1);
        String l2 = Utilidades.limpiarStringParaOrdenacion(p2);
		return l2.compareTo(l1);
    }		

}
