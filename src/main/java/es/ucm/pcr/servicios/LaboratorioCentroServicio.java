package es.ucm.pcr.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioAnalistaBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.GuardarAsignacionPlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroAsignacionesAnalistaBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroAsignacionesBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.Usuario;

public interface LaboratorioCentroServicio {
	
	/**
	 * Hago el mapeo del bean a la entidad LaboratorioCentro
	 * 
	 * @param BeanLaboratorioCentro
	 * @return LaboratorioCentro
	 */
	public LaboratorioCentro mapeoBeanEntidadLaboratorioCentro(BeanLaboratorioCentro beanLaboratorioCentro) throws Exception;
	
	/**
	 * Hago el mapeo de la entidad al bean LaboratorioCentro
	 * 
	 * @param LaboratorioCentro
	 * @return BeanLaboratorioCentro
	 */
	public BeanLaboratorioCentro mapeoEntidadBeanLaboratorioCentro(LaboratorioCentro laboratorioCentro) throws Exception;

	/**
	 * Lista ordenada de bean LaboratorioCentro
	 * 
	 * @param LaboratorioCentro
	 * @return BeanLaboratorioCentro
	 */
	public List<BeanLaboratorioCentro> listaLaboratoriosCentroOrdenada() throws Exception;
	
	/**
	 * Mapa de  LaboratorioCentro
	 * 
	 * @param List<BeanLaboratorioCentro>
	 * @return Map<String,String>
	 */
	public Map<Integer,String> mapaLaboratoriosCentro (List<BeanLaboratorioCentro> laboratoriosCentro) throws Exception;

	/**
	 * Guardar Laboratorio
	 * 
	 * @param LaboratorioCentro
	 * @return void >
	 */
	public LaboratorioCentro save(LaboratorioCentro laboratorioCentro) throws Exception;
	
	/**
	 * Borrar Laboratorio
	 * 
	 * @param Integer idLaboratorioCentro
	 * @return void >
	 */
	public void deleteById(Integer idLaboratorioCentro) throws Exception;
	
	/**
	 * Buscat Laboratorio por Id
	 * 
	 * @param Integer idLaboratorioCentro
	 * @return void Optional <LaboratorioCentro>
	 */
	public Optional <LaboratorioCentro> findById(Integer idLaboratorioCentro) throws Exception;
	
	Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda, Pageable pageable);
	PlacaLaboratorioCentroBean buscarPlaca (Integer id);
	PlacaLaboratorioCentroBean guardarPlaca (PlacaLaboratorioCentroBean placaLaboratorioCentroBean);
	public void finalizarPCR(Integer id);
	public void asignarEquipoPCR(Integer id);
	
	public PlacaLaboratorioCentroAsignacionesBean buscarPlacaAsignaciones(Integer id);
	//public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioJefeBean criteriosBusqueda, Pageable pageable); 
	public Page<PlacaLaboratorioCentroAsignacionesBean> buscarPlacas(BusquedaPlacaLaboratorioJefeBean criteriosBusqueda, Pageable pageable);
	public PlacaLaboratorioCentroBean guardarCogerODevolverPlaca(Integer idPlaca, Integer idUsuario, String accion);	
	public List<PlacaLaboratorioCentroBean> buscarPlacasAsignadasAJefe(Usuario usuario); 
	public List<BeanElemento> buscarPlacasBeanElementoAsignadasAJefe(Usuario usuario);
	public void guardarAsignacionesAnalistasYVoluntariosAPlacaYmuestras(GuardarAsignacionPlacaLaboratorioCentroBean formBeanGuardarAsignacionPlaca);
	public Boolean tienenResultadoDefinitivoLasMuestrasDeLaPlaca(Integer idPlaca);
	
	//public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioAnalistaBean criteriosBusqueda, Pageable pageable);
	public Page<PlacaLaboratorioCentroAsignacionesAnalistaBean> buscarPlacas(BusquedaPlacaLaboratorioAnalistaBean criteriosBusqueda, Pageable pageable);
	public void guardarResultadosPlacaLaboratorio(ElementoDocumentacionBean bean, Integer numAnalistas);
	
	
	
	
	
	
	public BeanLaboratorioCentro buscarLaboratorioById(Integer id);

	public PlacaLaboratorioCentroBean crearPlaca(PlacaLaboratorioCentroBean placaLaboratorioCentroBean);


}	
