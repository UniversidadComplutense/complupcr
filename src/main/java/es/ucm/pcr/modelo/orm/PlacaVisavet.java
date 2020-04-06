package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 17:36:56 by Hibernate Tools 5.2.12.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PlacaVisavet generated by hbm2java
 */
@Entity
@Table(name = "placaVisavet")
public class PlacaVisavet implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8538316150884775260L;
	private Integer id;
	private EstadoPlacaVisavet estadoPlacaVisavet;
	private LaboratorioVisavet laboratorioVisavet;
	private LaboratorioCentro laboratorioCentro;
	private Integer numeromuestras;

	private Set<PlacaVisavetPlacaLaboratorio> placaVisavetPlacaLaboratorios = new HashSet<PlacaVisavetPlacaLaboratorio>(
			0);
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<Lote> lotes = new HashSet<Lote>(0);
	private Date fechaCreacion;
	private Set<Muestra> muestras = new HashSet<Muestra>(0);
	

	public PlacaVisavet() {
	}

	public PlacaVisavet(LaboratorioVisavet laboratorioVisavet) {
		this.laboratorioVisavet = laboratorioVisavet;
	}

	public PlacaVisavet(EstadoPlacaVisavet estadoPlacaVisavet, LaboratorioVisavet laboratorioVisavet,
			Integer numeromuestras, Set<PlacaVisavetPlacaLaboratorio> placaVisavetPlacaLaboratorios,
			Set<Documento> documentos, Set<Lote> lotes,Date fechaCreacion,LaboratorioCentro laboratorioCentro) {
		this.estadoPlacaVisavet = estadoPlacaVisavet;
		this.laboratorioVisavet = laboratorioVisavet;
		this.laboratorioCentro = laboratorioCentro;
		this.numeromuestras = numeromuestras;
		this.placaVisavetPlacaLaboratorios = placaVisavetPlacaLaboratorios;
		this.documentos = documentos;
		this.lotes = lotes;
		this.fechaCreacion=fechaCreacion;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idEstadoPlacasVisavet")
	public EstadoPlacaVisavet getEstadoPlacaVisavet() {
		return this.estadoPlacaVisavet;
	}

	public void setEstadoPlacaVisavet(EstadoPlacaVisavet estadoPlacaVisavet) {
		this.estadoPlacaVisavet = estadoPlacaVisavet;
	}

	@ManyToOne
	@JoinColumn(name = "idLaboratorioVisavet", nullable = false)
	public LaboratorioVisavet getLaboratorioVisavet() {
		return this.laboratorioVisavet;
	}

	public void setLaboratorioVisavet(LaboratorioVisavet laboratorioVisavet) {
		this.laboratorioVisavet = laboratorioVisavet;
	}
	
	@ManyToOne
	@JoinColumn(name = "idLaboratorioCentro", nullable = false)
	public LaboratorioCentro getLaboratorioCentro() {
		return this.laboratorioCentro;
	}

	public void setLaboratorioCentro(LaboratorioCentro laboratorioCentro) {
		this.laboratorioCentro = laboratorioCentro;
	}
	
	@Column(name = "numeromuestras")
	public Integer getNumeromuestras() {
		return this.numeromuestras;
	}

	public void setNumeromuestras(Integer numeromuestras) {
		this.numeromuestras = numeromuestras;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "placaVisavet")
	public Set<PlacaVisavetPlacaLaboratorio> getPlacaVisavetPlacaLaboratorios() {
		return this.placaVisavetPlacaLaboratorios;
	}

	public void setPlacaVisavetPlacaLaboratorios(Set<PlacaVisavetPlacaLaboratorio> placaVisavetPlacaLaboratorios) {
		this.placaVisavetPlacaLaboratorios = placaVisavetPlacaLaboratorios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "placaVisavet")
	public Set<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "placaVisavet")
	public Set<Lote> getLotes() {
		return this.lotes;
	}

	public void setLotes(Set<Lote> lotes) {
		this.lotes = lotes;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion", length = 10)
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "placaVisavet")
	public Set<Muestra> getMuestras() {
		return this.muestras;
	}

	public void setMuestras(Set<Muestra> muestras) {
		this.muestras = muestras;
	}
	
}
