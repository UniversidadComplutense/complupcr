package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.EstadoPlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Service
public class LaboratorioCentroServicioImp implements LaboratorioCentroServicio{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LaboratorioCentroServicioImp.class);

	@Autowired
	PlacaLaboratorioRepositorio placaLaboratorioRepositorio;
	
	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;
	
	@Autowired
	SesionServicio sesionServicio;
	
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;	
	
	@Autowired
	EstadoPlacaLaboratorioRepositorio estadoPlacaLaboratorioRepositorio;
	
	public LaboratorioCentro mapeoBeanEntidadLaboratorioCentro(BeanLaboratorioCentro beanLaboratorioCentro) throws Exception{
		
		LaboratorioCentro laboratorioCentro = new LaboratorioCentro();
		
		laboratorioCentro.setId(beanLaboratorioCentro.getId());
		laboratorioCentro.setNombre(beanLaboratorioCentro.getNombre());	
		laboratorioCentro.setDocumentos(beanLaboratorioCentro.getDocumentos());
		laboratorioCentro.setEquipos(beanLaboratorioCentro.getEquipos());
		laboratorioCentro.setPlacaLaboratorios(beanLaboratorioCentro.getPlacaLaboratorios());
		
		return laboratorioCentro;
		
	}
	
	public BeanLaboratorioCentro mapeoEntidadBeanLaboratorioCentro(LaboratorioCentro laboratorioCentro) throws Exception{
		
		BeanLaboratorioCentro beanLaboratorioCentro = new BeanLaboratorioCentro();
		
		beanLaboratorioCentro.setId(laboratorioCentro.getId());
		beanLaboratorioCentro.setNombre(laboratorioCentro.getNombre());
		beanLaboratorioCentro.setDocumentos(laboratorioCentro.getDocumentos());
		beanLaboratorioCentro.setEquipos(laboratorioCentro.getEquipos());
		beanLaboratorioCentro.setPlacaLaboratorios(laboratorioCentro.getPlacaLaboratorios());
		
		return beanLaboratorioCentro;
		
	}
	
	public List<BeanLaboratorioCentro> listaLaboratoriosCentroOrdenada() throws Exception{
		List<BeanLaboratorioCentro> listaLaboratorioCentro = new ArrayList<BeanLaboratorioCentro>();
		for (LaboratorioCentro laboratorioCentro: laboratorioCentroRepositorio.findAll())
		{
			listaLaboratorioCentro.add(new BeanLaboratorioCentro(
					laboratorioCentro.getId(), 
					laboratorioCentro.getNombre(),
					laboratorioCentro.getDocumentos(),
					laboratorioCentro.getPlacaLaboratorios(),
					laboratorioCentro.getEquipos(),
					"L"));
		}
		//	Ordeno por nombre
		Collections.sort(listaLaboratorioCentro);
		return listaLaboratorioCentro;
	}
	
	public Map<Integer,String> mapaLaboratoriosCentro (List<BeanLaboratorioCentro> laboratoriosCentro) throws Exception{
		Map<Integer, String> mapalaboratorioCentro = new HashMap<Integer, String>();
		for (BeanLaboratorioCentro laboratorioCentro :laboratoriosCentro)
		{
			mapalaboratorioCentro.put(laboratorioCentro.getId(), laboratorioCentro.getNombre());
		}
		return mapalaboratorioCentro;
	}
	
	@Override
	public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda,
			Pageable pageable) {
		
		List<PlacaLaboratorioCentroBean> listaPlacasLaboratorioCentroBean = new ArrayList<PlacaLaboratorioCentroBean>();		
		Page<PlacaLaboratorio> PagePlacasLaboratorioCentro = placaLaboratorioRepositorio.findByParams(criteriosBusqueda, pageable); 		
		for (PlacaLaboratorio placa : PagePlacasLaboratorioCentro.getContent()) {
			listaPlacasLaboratorioCentroBean.add(PlacaLaboratorioCentroBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioCentroBean> placasLaboratorioCentro = new PageImpl<>(listaPlacasLaboratorioCentroBean, pageable, PagePlacasLaboratorioCentro.getTotalElements());		
		return placasLaboratorioCentro;
	}

	
	@Override
	public PlacaLaboratorioCentroBean guardarPlaca(PlacaLaboratorioCentroBean placaLaboratorioCentroBean) {
		PlacaLaboratorio placa = PlacaLaboratorioCentroBean.beanToModel(placaLaboratorioCentroBean);						

		// Placa nueva
		if (placaLaboratorioCentroBean.getId() == null) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_INICIADA.getCodNum()));
			placa.setLaboratorioCentro(new LaboratorioCentro(sesionServicio.getUsuario().getIdLaboratorioCentro()));
		}
		placa = placaLaboratorioRepositorio.save(placa);
		return PlacaLaboratorioCentroBean.modelToBean(placa);

	}
	
	
	@Override
	public PlacaLaboratorioCentroBean buscarPlaca(Integer id) {
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			return PlacaLaboratorioCentroBean.modelToBean(placa.get());
		}
		return new PlacaLaboratorioCentroBean();
	}


	//Diana- metodos para jefe de servicio (replica de metodos de Javi con mi bean)
	@Override
	public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioJefeBean criteriosBusqueda,
			Pageable pageable) {
		
		List<PlacaLaboratorioCentroBean> listaPlacasLaboratorioCentroBean = new ArrayList<PlacaLaboratorioCentroBean>();		
		Page<PlacaLaboratorio> PagePlacasLaboratorioCentro = placaLaboratorioRepositorio.findByParams(criteriosBusqueda, pageable); 		
		for (PlacaLaboratorio placa : PagePlacasLaboratorioCentro.getContent()) {
			listaPlacasLaboratorioCentroBean.add(PlacaLaboratorioCentroBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioCentroBean> placasLaboratorioCentro = new PageImpl<>(listaPlacasLaboratorioCentroBean, pageable, PagePlacasLaboratorioCentro.getTotalElements());		
		return placasLaboratorioCentro;
	}
	
	@Override
	public PlacaLaboratorioCentroBean guardarCogerODevolverPlaca(Integer idPlaca, Integer idUsuario, String accion) {
	//metodo que recibe el idPlaca, y el id de usuario que la quiere coger
	
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(idPlaca);		
				
		if(accion.equals("coger")) {
			//asocia la placa al usuario, le cambia el estado de la placa a PLACA_ASIGNADA_PARA_ANALISIS y pone a todas sus muestras a estado pendente de analizar
			Usuario usuario = usuarioRepositorio.getOne(idUsuario);
			placa.setUsuario(usuario);
			EstadoPlacaLaboratorio estadoPlacaLab = estadoPlacaLaboratorioRepositorio.getOne(BeanEstado.Estado.PLACA_ASIGNADA_PARA_ANALISIS.getCodNum());
			System.out.println("el estado que le vamos a asignar a la placa es: " + estadoPlacaLab.getDescripcion());
			placa.setEstadoPlacaLaboratorio(estadoPlacaLab);
		}else if(accion.equals("devolver")) {
			//desasocia la placa del usuario, le cambia el estado de la placa a PLACA_LISTA_PARA_ANALISIS (estado anterior) y ¿Qué hacemos con las muestras?
			placa.setUsuario(null);
			EstadoPlacaLaboratorio estadoPlacaLab = estadoPlacaLaboratorioRepositorio.getOne(BeanEstado.Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum());
			//TODO que hacemos con las muestras cuando devolvermos la placa?
			System.out.println("el estado que le vamos a asignar a la placa es: " + estadoPlacaLab.getDescripcion());
			placa.setEstadoPlacaLaboratorio(estadoPlacaLab);
		}
		placa = placaLaboratorioRepositorio.save(placa);			
		
		
		return PlacaLaboratorioCentroBean.modelToBean(placa);

	}
	
	
	@Override
	public List<PlacaLaboratorioCentroBean> buscarPlacasAsignadasAJefe(Usuario usuario) {	
		//busca la lissta de placas que se ha asignado el Jefe (que estan bajo su responsabilidad)
		List<PlacaLaboratorioCentroBean> listaBeanPlacasLaboratorioDeJefe = new ArrayList<PlacaLaboratorioCentroBean>();
		List<PlacaLaboratorio> lisaPlacasJefe =  placaLaboratorioRepositorio.findByUsuario(usuario);
		for(PlacaLaboratorio placa: lisaPlacasJefe) {
			PlacaLaboratorioCentroBean placaBean = PlacaLaboratorioCentroBean.modelToBean(placa);
			listaBeanPlacasLaboratorioDeJefe.add(placaBean);			
		}		
		return listaBeanPlacasLaboratorioDeJefe;
	}
	
	@Override
	public List<BeanElemento> buscarPlacasBeanElementoAsignadasAJefe(Usuario usuario) {	
		//busca la lissta de placas que se ha asignado el Jefe (que estan bajo su responsabilidad)
		List<BeanElemento> listaBeanPlacasLaboratorioDeJefe = new ArrayList<BeanElemento>();
		List<PlacaLaboratorio> lisaPlacasJefe =  placaLaboratorioRepositorio.findByUsuario(usuario);		
		for(PlacaLaboratorio placa: lisaPlacasJefe) {
			BeanElemento beanElementoPlaca = new BeanElemento();
			beanElementoPlaca.setCodigo(placa.getId());
			beanElementoPlaca.setDescripcion("Placa " + placa.getId() + ", muestras: " + placa.getNumeromuestras());
			listaBeanPlacasLaboratorioDeJefe.add(beanElementoPlaca);			
		}		
		//añado el elemento seleccione al principio
		BeanElemento seleccione = new BeanElemento();
		seleccione.setCodigo(null);
		seleccione.setDescripcion("Seleccione");
		listaBeanPlacasLaboratorioDeJefe.add(0,seleccione);
		return listaBeanPlacasLaboratorioDeJefe;
	}
	
	//Fin Diana- metodos para jefe de servicio
}
