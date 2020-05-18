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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import es.ucm.pcr.modelo.orm.LogMuestras;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.modelo.orm.Usuario;

public class LogMuestraListadoBean {

	private Integer id;
	private Integer idMuestra;
	private String descMuestra;
	private String descRefInternaMuestra;
	private String descLote;
	private String descRefInternaLote;
	private String descCentroSalud;
	private String descPaciente;
	private String nhcPaciente;
	private String descEstadoMuestra;
	private Date fechaCambio;
	private String nombreAutorCambio;
	private Integer idPlacaVisavet;
	private String descPlacaVisavet;
	private Integer idPlacaLaboratorio;
	private String descPlacaLaboratorio;
	private List<LogMuestraListadoBean> logsMuestra;
	private String descResultado;

	public LogMuestraListadoBean() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescMuestra() {
		return descMuestra;
	}

	public void setDescMuestra(String descMuestra) {
		this.descMuestra = descMuestra;
	}

	public String getDescLote() {
		return descLote;
	}

	public void setDescLote(String descLote) {
		this.descLote = descLote;
	}

	public String getDescCentroSalud() {
		return descCentroSalud;
	}

	public void setDescCentroSalud(String descCentroSalud) {
		this.descCentroSalud = descCentroSalud;
	}

	public String getDescPaciente() {
		return descPaciente;
	}

	public void setDescPaciente(String descPaciente) {
		this.descPaciente = descPaciente;
	}

	public String getNhcPaciente() {
		return nhcPaciente;
	}

	public void setNhcPaciente(String nhcPaciente) {
		this.nhcPaciente = nhcPaciente;
	}

	public String getDescEstadoMuestra() {
		return descEstadoMuestra;
	}

	public void setDescEstadoMuestra(String descEstadoMuestra) {
		this.descEstadoMuestra = descEstadoMuestra;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getNombreAutorCambio() {
		return nombreAutorCambio;
	}

	public void setNombreAutorCambio(String nombreAutorCambio) {
		this.nombreAutorCambio = nombreAutorCambio;
	}

	public String getDescPlacaVisavet() {
		return descPlacaVisavet;
	}

	public void setDescPlacaVisavet(String descPlacaVisavet) {
		this.descPlacaVisavet = descPlacaVisavet;
	}

	public String getDescPlacaLaboratorio() {
		return descPlacaLaboratorio;
	}

	public void setDescPlacaLaboratorio(String descPlacaLaboratorio) {
		this.descPlacaLaboratorio = descPlacaLaboratorio;
	}

	public Integer getIdMuestra() {
		return idMuestra;
	}

	public void setIdMuestra(Integer idMuestra) {
		this.idMuestra = idMuestra;
	}

	public String getDescRefInternaMuestra() {
		return descRefInternaMuestra;
	}

	public void setDescRefInternaMuestra(String descRefInternaMuestra) {
		this.descRefInternaMuestra = descRefInternaMuestra;
	}

	public String getDescRefInternaLote() {
		return descRefInternaLote;
	}

	public void setDescRefInternaLote(String descRefInternaLote) {
		this.descRefInternaLote = descRefInternaLote;
	}

	public Integer getIdPlacaVisavet() {
		return idPlacaVisavet;
	}

	public void setIdPlacaVisavet(Integer idPlacaVisavet) {
		this.idPlacaVisavet = idPlacaVisavet;
	}

	public Integer getIdPlacaLaboratorio() {
		return idPlacaLaboratorio;
	}

	public void setIdPlacaLaboratorio(Integer idPlacaLaboratorio) {
		this.idPlacaLaboratorio = idPlacaLaboratorio;
	}

	public List<LogMuestraListadoBean> getLogsMuestra() {
		if (logsMuestra == null) {
			logsMuestra = new ArrayList<>();
		}
		return logsMuestra;
	}

	public void setLogsMuestra(List<LogMuestraListadoBean> logsMuestra) {
		this.logsMuestra = logsMuestra;
	}

	public String getDescResultado() {
		return descResultado;
	}

	public void setDescResultado(String descResultado) {
		this.descResultado = descResultado;
	}

	public static LogMuestraListadoBean modelToBean(LogMuestras l) {
		LogMuestraListadoBean logMuestraBean = new LogMuestraListadoBean();
		logMuestraBean.setId(l.getId());
		logMuestraBean.setDescLote(l.getLote() != null ? l.getLote().getNumeroLote() : "");
		logMuestraBean.setDescPlacaVisavet(l.getPlacaVisavet() != null ? l.getPlacaVisavet().getId().toString() : "");
		logMuestraBean.setDescPlacaLaboratorio(
				l.getPlacaLaboratorio() != null ? String.valueOf(l.getPlacaLaboratorio().getId()) : "");
		logMuestraBean.setDescCentroSalud(l.getMuestra().getCentro().getNombre());
		logMuestraBean.setDescMuestra(l.getMuestra().getEtiqueta());
		Paciente paciente = l.getMuestra().getPaciente();
		logMuestraBean.setDescPaciente(MuestraListadoBean.nombreCompletoPaciente(paciente));
		logMuestraBean.setNhcPaciente(paciente != null ? paciente.getNhc() : "");
		logMuestraBean.setDescEstadoMuestra(l.getEstadoMuestra().getDescripcion());
		logMuestraBean.setFechaCambio(l.getFechaCambio());
		Usuario usuario = l.getAutorCambio();
		logMuestraBean.setNombreAutorCambio(usuario.getNombre() + " " + usuario.getApellido1()
				+ (usuario.getApellido2() != null ? usuario.getApellido2() : ""));
		return logMuestraBean;
	}

	public static LogMuestraListadoBean modelMuestraToBean(Muestra muestra) {
		LogMuestraListadoBean logMuestraBean = new LogMuestraListadoBean();

		Lote loteMuestra = muestra.getLote() != null ? muestra.getLote() : null;
		PlacaVisavet placaVisavetMuestra = loteMuestra != null ? loteMuestra.getPlacaVisavet() : null;

		// TODO EN LA TABLA AUNQUE ESTA COMO N:M, UNA PLACA VISAVET SOLO PUEDE ESTAR EN
		// UNA PLACA DE LABORATORIO??
		PlacaLaboratorio placaLaboratorio = placaVisavetMuestra != null
				&& CollectionUtils.isNotEmpty(placaVisavetMuestra.getPlacaVisavetPlacaLaboratorios())
						? (placaVisavetMuestra.getPlacaVisavetPlacaLaboratorios().iterator().next())
								.getPlacaLaboratorio()
						: null;

		logMuestraBean.setIdMuestra(muestra.getId());
		logMuestraBean.setDescLote(loteMuestra != null ? loteMuestra.getNumeroLote() : "");
		logMuestraBean.setDescRefInternaLote(loteMuestra != null ? loteMuestra.getReferenciaInternaLote() : "");
		logMuestraBean.setIdPlacaVisavet(placaVisavetMuestra != null ? placaVisavetMuestra.getId() : null);
		logMuestraBean
				.setDescPlacaVisavet(placaVisavetMuestra != null ? placaVisavetMuestra.getNombrePlacaVisavet() : "");
		logMuestraBean.setIdPlacaLaboratorio(placaLaboratorio != null ? placaLaboratorio.getId() : null);
		logMuestraBean.setDescCentroSalud(muestra.getCentro().getNombre());
		logMuestraBean.setDescMuestra(muestra.getEtiqueta());
		logMuestraBean.setDescRefInternaMuestra(muestra.getRefInternaVisavet());
		Paciente paciente = muestra.getPaciente();
		logMuestraBean.setDescPaciente(MuestraListadoBean.nombreCompletoPaciente(paciente));
		logMuestraBean.setNhcPaciente(paciente != null ? paciente.getNhc() : "");
		logMuestraBean.setDescEstadoMuestra(muestra.getEstadoMuestra().getDescripcion());
		BeanResultado beanResultado = new BeanResultado();
		logMuestraBean.setDescResultado(beanResultado.asignarTipoEstadoYCodNum(muestra.getResultado()).getResultadoMuestra().getDescripcion());

		// estados muestra
		LogMuestraListadoBean logBean = null;
		for (LogMuestras l : muestra.getLogMuestra()) {
			logBean = new LogMuestraListadoBean();
			logBean.setDescEstadoMuestra(l.getEstadoMuestra().getDescripcion());
			logBean.setFechaCambio(l.getFechaCambio());
			Usuario usuario = l.getAutorCambio();
			logBean.setNombreAutorCambio(usuario.getNombre() + " " + usuario.getApellido1()
					+ (usuario.getApellido2() != null ? usuario.getApellido2() : ""));
			logMuestraBean.getLogsMuestra().add(logBean);
		}
		logMuestraBean.setLogsMuestra(logMuestraBean.getLogsMuestra().stream().sorted(Comparator.comparing(LogMuestraListadoBean::getFechaCambio)).collect(Collectors.toList()));

		return logMuestraBean;
	}
}
