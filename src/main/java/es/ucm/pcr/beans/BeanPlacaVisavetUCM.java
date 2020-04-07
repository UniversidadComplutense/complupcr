package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public class BeanPlacaVisavetUCM {
// aunque es List suponemos que una placa solo contiene un lote
private List<LoteBeanPlacaVisavet> listaLotes;
private Integer id;
private String tamano;
private Date fechaCreacion;
private BeanEstado estado;
private Integer idLaboratorioCentro;
private BeanLaboratorioCentro laboratorioCentro;

public List<LoteBeanPlacaVisavet> getListaLotes() {
	return listaLotes;
}
public void setListaLotes(List<LoteBeanPlacaVisavet> listaLotes) {
	this.listaLotes = listaLotes;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getTamano() {
	return tamano;
}
public void setTamano(String tamano) {
	this.tamano = tamano;
}
public Date getFechaCreacion() {
	return fechaCreacion;
}
public void setFechaCreacion(Date fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
}
public BeanEstado getEstado() {
	return estado;
}
public void setEstado(BeanEstado estado) {
	this.estado = estado;
}
public Integer getIdLaboratorioCentro() {
	return idLaboratorioCentro;
}
public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
	this.idLaboratorioCentro = idLaboratorioCentro;
}

public BeanLaboratorioCentro getLaboratorioCentro() {
	return laboratorioCentro;
}
public void setLaboratorioCentro(BeanLaboratorioCentro laboratorioCentro) {
	this.laboratorioCentro = laboratorioCentro;
}
public static BeanPlacaVisavetUCM modelToBean(PlacaVisavet placaVisavet) {

	BeanPlacaVisavetUCM bean = new BeanPlacaVisavetUCM();
//
	
	bean.setId(placaVisavet.getId());
//	bean.setTamano(placaVisavet.getTamano());
	bean.setFechaCreacion(placaVisavet.getFechaCreacion());
	BeanEstado beanEstado = new BeanEstado();
	beanEstado.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLaboratorioVisavet,
			placaVisavet.getEstadoPlacaVisavet().getId());
	bean.setEstado(beanEstado);

	LoteBeanPlacaVisavet lote = new LoteBeanPlacaVisavet();
	List<LoteBeanPlacaVisavet> listaLotes = new ArrayList();
	
	for (Lote l:placaVisavet.getLotes()) {
	lote=LoteBeanPlacaVisavet.modelToBean(l);
	listaLotes.add(lote);
	}
	
	if (placaVisavet.getLaboratorioCentro()!= null) {
		BeanLaboratorioCentro laboratorioCentro= new BeanLaboratorioCentro();
		//LaboratorioCentro laboratorioCentro = new BeanLaboratorioCentro();
		laboratorioCentro.setId(placaVisavet.getLaboratorioCentro().getId());
		laboratorioCentro.setNombre(placaVisavet.getLaboratorioCentro().getNombre());
	    bean.setLaboratorioCentro(laboratorioCentro);
	    bean.setIdLaboratorioCentro(placaVisavet.getLaboratorioCentro().getId());
	}
	
	/*List<MuestraListadoPlacasLaboratorioBean> listadoMuestras = new ArrayList<MuestraListadoPlacasLaboratorioBean>();
	Set<Muestra> muestras = placaVisavet.getMuestras();
	
	bean.setMuestras(listadoMuestras);
	*/
	bean.setListaLotes(listaLotes);
	return bean;

}
public static PlacaVisavet beanToModel(BeanPlacaVisavetUCM bean) {

	PlacaVisavet placaVisavet = new PlacaVisavet();
//
	
	placaVisavet.setId(bean.getId());
//	bean.setTamano(placaVisavet.getTamano());
	placaVisavet.setFechaCreacion(bean.getFechaCreacion());
    placaVisavet.setEstadoPlacaVisavet(new EstadoPlacaVisavet(bean.getEstado().getEstado().getCodNum()));
    /* YOLI  Set<Lote> listaLotes =  new HashSet();
   if (bean.getListaLotes() !=null) {
    for (LoteBeanPlacaVisavet l:bean.getListaLotes()) {
    	listaLotes.add(LoteBeanPlacaVisavet.beanToModel(l));
    }
    placaVisavet.setLotes(listaLotes);
    
   }*/
    /*LoteBeanPlacaVisavet lote = new LoteBeanPlacaVisavet();
	List<LoteBeanPlacaVisavet> listaLotes = new ArrayList();
	
	for (Lote l:placaVisavet.getLotes()) {
		LoteBeanPlacaVisavet.modelToBean(l);
	listaLotes.add(lote);
	}
	*/
	/*List<MuestraListadoPlacasLaboratorioBean> listadoMuestras = new ArrayList<MuestraListadoPlacasLaboratorioBean>();
	Set<Muestra> muestras = placaVisavet.getMuestras();
	
	bean.setMuestras(listadoMuestras);
	*/
	return placaVisavet;

}
}
