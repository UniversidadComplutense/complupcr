package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.List;

import es.ucm.pcr.utilidades.Utilidades;

public class BeanRolUsuario implements Comparable<BeanRolUsuario>{

	public enum RolUsuario {

		// ROL USUARIO
		ROL_USUARIO_ADMIN(1, "ADMIN"), ROL_USUARIO_GESTOR(2, "GESTOR"),
		ROL_USUARIO_CENTROSALUD(3, "CENTROSALUD"), ROL_USUARIO_RECEPCIONLABORATORIO(4, "RECEPCIONLABORATORIO"),
		ROL_USUARIO_TECNICOLABORATORIO(5, "TECNICOLABORATORIO"), ROL_USUARIO_RESPONSABLEPCR(6, "RESPONSABLEPCR"),
		ROL_USUARIO_JEFESERVICIO(7, "JEFESERVICIO"), ROL_USUARIO_ANALISTALABORATORIO(8, "ANALISTALABORATORIO"),
		ROL_USUARIO_AUDITOR(13, "AUDITOR"), ROL_USUARIO_VOLUNTARIO(14, "VOLUNTARIO");
		
		private Integer id;
		public String nombre;

		private RolUsuario() {

		}
		
		private RolUsuario(Integer id, String nombre) {
			this.id = id;
			this.nombre = nombre;
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

	}

	private RolUsuario rolUsuario;

	public BeanRolUsuario() {

	}
	
	
	
	public BeanRolUsuario(RolUsuario rolUsuario) {
		super();
		this.rolUsuario = rolUsuario;
	}

	public RolUsuario getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(RolUsuario rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	
	
	public BeanRolUsuario asignarRolUsuarioPorCodNum(Integer cod) {

		switch (cod) {
			case 1: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_ADMIN);
				break;
			}
			case 2: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_GESTOR);
				break;
			}
			case 3: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_CENTROSALUD);
				break;
			}
			case 4: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_RECEPCIONLABORATORIO);
				break;
			}
			case 5: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_TECNICOLABORATORIO);
				break;
			}
			case 6: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_RESPONSABLEPCR);
				break;
			}
			case 7: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_JEFESERVICIO);
				break;
			}
			case 8: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO);
				break;
			}
			case 13: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_AUDITOR);
				break;
			}
			case 14: {
				this.setRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO);
				break;
			}
		}

		return this;

	}
	/*
	public BeanRolUsuario asignarTipoEstadoDescripcion(String descripcion) {

		switch (descripcion) {
			case "Pendiente": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_PENDIENTE);
				break;
			}
			case "Negativo": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_NEGATIVO);
				break;
			}
			case "Positivo": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_POSITIVO);
				break;
			}
			case "Débil": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_DEBIL);
				break;
			}
			case "Repetir": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_REPETIR);
				break;
			}
		}

		return this;

	}
	*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rolUsuario == null) ? 0 : rolUsuario.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanRolUsuario other = (BeanRolUsuario) obj;
		if (rolUsuario != other.rolUsuario)
			return false;
		return true;
	}

	
	@Override
    public int compareTo(BeanRolUsuario o) {				
		return this.rolUsuario.compareTo(o.getRolUsuario());
    }


	/**
	 * Posibles roles del usuario
	 * 
	 * @return
	 */
	public static List<BeanRolUsuario> posiblesRolesUsuario() {
		// posibles roles del usuario
		List<BeanRolUsuario> rolesUsuario = new ArrayList<BeanRolUsuario>();
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ADMIN));
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_GESTOR));
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_CENTROSALUD));
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_RECEPCIONLABORATORIO));
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_TECNICOLABORATORIO));
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_RESPONSABLEPCR));
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_JEFESERVICIO));
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO));
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_AUDITOR));		
		rolesUsuario.add(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO));	

		return rolesUsuario;
	}

}