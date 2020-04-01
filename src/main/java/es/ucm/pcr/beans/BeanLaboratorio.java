package es.ucm.pcr.beans;

public class BeanLaboratorio {

	private Integer idLaboratorio;
	private String codLaboratorio;
	private String desLaboratorio;
	private String telefonoLaboratorio;
	private String responsableLaboratorio;
	private String telefonoResponsableLaboratorio;
	
	public BeanLaboratorio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(Integer idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public String getCodLaboratorio() {
		return codLaboratorio;
	}

	public void setCodLaboratorio(String codLaboratorio) {
		this.codLaboratorio = codLaboratorio;
	}

	public String getDesLaboratorio() {
		return desLaboratorio;
	}

	public void setDesLaboratorio(String desLaboratorio) {
		this.desLaboratorio = desLaboratorio;
	}

	public String getTelefonoLaboratorio() {
		return telefonoLaboratorio;
	}

	public void setTelefonoLaboratorio(String telefonoLaboratorio) {
		this.telefonoLaboratorio = telefonoLaboratorio;
	}

	public String getResponsableLaboratorio() {
		return responsableLaboratorio;
	}

	public void setResponsableLaboratorio(String responsableLaboratorio) {
		this.responsableLaboratorio = responsableLaboratorio;
	}

	public String getTelefonoResponsableLaboratorio() {
		return telefonoResponsableLaboratorio;
	}

	public void setTelefonoResponsableLaboratorio(String telefonoResponsableLaboratorio) {
		this.telefonoResponsableLaboratorio = telefonoResponsableLaboratorio;
	}

	@Override
	public String toString() {
		return "BeanLaboratorio [idLaboratorio=" + idLaboratorio + ", codLaboratorio=" + codLaboratorio
				+ ", desLaboratorio=" + desLaboratorio + ", telefonoLaboratorio=" + telefonoLaboratorio
				+ ", responsableLaboratorio=" + responsableLaboratorio + ", telefonoResponsableLaboratorio="
				+ telefonoResponsableLaboratorio + "]";
	}
	
	
	
}
