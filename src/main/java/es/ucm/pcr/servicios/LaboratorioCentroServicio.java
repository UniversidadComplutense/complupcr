package es.ucm.pcr.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanEquipo;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BeanResultado;
import es.ucm.pcr.beans.BeanResultadoCarga;
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
	
	Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda, Pageable pageable) throws Exception;
	PlacaLaboratorioCentroBean buscarPlaca (Integer id) throws Exception;
	public boolean finalizarPCR(Integer id) throws Exception;
	public boolean asignarEquipoPCR(Integer idPlaca, Integer idEquipo) throws Exception;
	
	public PlacaLaboratorioCentroAsignacionesBean buscarPlacaAsignaciones(Integer id);
	//public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioJefeBean criteriosBusqueda, Pageable pageable); 
	public Page<PlacaLaboratorioCentroAsignacionesBean> buscarPlacas(BusquedaPlacaLaboratorioJefeBean criteriosBusqueda, Pageable pageable);
	public PlacaLaboratorioCentroBean guardarCogerODevolverPlaca(Integer idPlaca, Integer idUsuario, String accion);	
	public List<PlacaLaboratorioCentroBean> buscarPlacasAsignadasAJefe(Usuario usuario); 
	public List<BeanElemento> buscarPlacasBeanElementoAsignadasAJefe(Usuario usuario);
	public void guardarAsignacionesAnalistasYVoluntariosAPlacaYmuestras(GuardarAsignacionPlacaLaboratorioCentroBean formBeanGuardarAsignacionPlaca);
	public void guardarReemplazoAnalistaDePlacaYmuestras(Integer idPlaca, Integer idUsuarioAQuitar, Integer idUsuarioAPoner);
	public Boolean tienenResultadoDefinitivoLasMuestrasDeLaPlaca(Integer idPlaca);
	
	//public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioAnalistaBean criteriosBusqueda, Pageable pageable);
	public Page<PlacaLaboratorioCentroAsignacionesAnalistaBean> buscarPlacas(BusquedaPlacaLaboratorioAnalistaBean criteriosBusqueda, Pageable pageable);
	public List<BeanResultadoCarga>  guardarResultadosPlacaLaboratorio(ElementoDocumentacionBean bean, Integer numAnalistas)throws Exception ;

	public BeanLaboratorioCentro buscarLaboratorioById(Integer id);
	public boolean placaListaParaAnalizar(Integer id) throws Exception;
	public boolean esEditable(Integer id) throws Exception;
	public Integer espacioLibreParaMuestras(PlacaLaboratorioCentroBean placaLaboratorioCentroBean, Integer capacidadNuevaPlaca) throws Exception;
	public PlacaLaboratorioCentroBean rellenarPlaca(PlacaLaboratorioCentroBean placaLaboratorioCentroBean, Integer capacidadNuevaPlaca) throws Exception;
	public boolean esRellenable(PlacaLaboratorioCentroBean placaLaboratorioCentroBean, Integer capacidadNuevaPlaca) throws Exception;

    public List<BeanEquipo> listaEquiposLaboratorioCentro(LaboratorioCentro laboratorioCentro) throws Exception;
	public boolean haySubidoAlgunDocumentoPorParteDelLaboratorio(Integer id) throws Exception;

}	
