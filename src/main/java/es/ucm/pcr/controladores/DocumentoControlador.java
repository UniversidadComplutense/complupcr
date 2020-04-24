package es.ucm.pcr.controladores;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.DocumentoBean;
import es.ucm.pcr.beans.DocumentoBusquedaBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.servicios.DocumentoServicio;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.validadores.DocumentoValidador;

@Controller
@RequestMapping(value = "/documento")
public class DocumentoControlador {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(DocumentoControlador.class);

	@Autowired
	private DocumentoValidador documentoValidador;

	@Autowired
	private DocumentoServicio documentoServicio;
	
	@Autowired
	SesionServicio sesionServicio;

	private Map<Integer, String> ACCIONES_MENSAJE = Stream
			.of(new Object[][] { { ACCION_GUARDAR_DOCUMENTO, "Documento guardado correctamente" },
					{ ACCION_BORRAR_DOCUMENTO_OK, "Documento borrado correctamente" },
					{ ACCION_BORRAR_DOCUMENTO_KO, "Se ha producido un error al borrar el documento" } })
			.collect(Collectors.toMap(d -> (Integer) d[0], d -> (String) d[1]));

	public static final Integer ACCION_GUARDAR_DOCUMENTO = 1;
	public static final Integer ACCION_BORRAR_DOCUMENTO_OK = 2;
	public static final Integer ACCION_BORRAR_DOCUMENTO_KO = 3;
	
	/**
	 * URL'S DE VUELTA DE SUBIDA DE DOCUMENTACION DEPENDIENDO DESDE DONDE SE HA LLAMADO
	 */
	private Map<Integer, String> URL_VOLVER = Stream
			.of(new Object[][] { 
					{ URL_VOLVER_MUESTRA_DESDE_MUESTRAS, "/centroSalud/muestra/list" },
					{ URL_VOLVER_PLACA_LABORATORIO_DESDE_COGERPLACAS_JEFE, "/analisis/cogerPlacas" },
					{ URL_VOLVER_PLACA_LABORATORIO_DESDE_REVISARPLACAS_ANALISTA, "/analisis/listarPlacasAnalista" },
				//	{ URL_VOLVER_PLACA_VISAVET_DESDE, "/centroSalud/lote/list" },
					{ URL_VOLVER_PLACA_VISAVET_DESDE, "/laboratorioUni/buscarPlacas" },
					{ URL_VOLVER_PLACA_LABORATORIO_DESDE_ASIGNAR_RESULTADOS_PCR, "/laboratorioCentro/gestionPlacas/modificar?id=" },
					{ URL_VOLVER_PLACA_LABORATORIO_DESDE_LISTADO_PLACAS, "/laboratorioCentro/gestionPlacas" }})
			.collect(Collectors.toMap(d -> (Integer) d[0], d -> (String) d[1]));
		
	public static final Integer URL_VOLVER_MUESTRA_DESDE_MUESTRAS = 1;
	public static final Integer URL_VOLVER_PLACA_LABORATORIO_DESDE_COGERPLACAS_JEFE = 2;
	public static final Integer URL_VOLVER_PLACA_LABORATORIO_DESDE_REVISARPLACAS_ANALISTA = 3;
	public static final Integer URL_VOLVER_PLACA_VISAVET_DESDE = 4;
	public static final Integer URL_VOLVER_PLACA_LABORATORIO_DESDE_ASIGNAR_RESULTADOS_PCR = 5;
	public static final Integer URL_VOLVER_PLACA_LABORATORIO_DESDE_LISTADO_PLACAS = 6;
	

	@InitBinder("elementoDoc")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session)
			throws Exception {
		binder.setValidator(documentoValidador);
	}

	// TODO - REVISION ROL

	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD', 'JEFESERVICIO', 'ANALISTALABORATORIO', 'VOLUNTARIO')")
	@RequestMapping(value = "/muestra", method = RequestMethod.GET)
	public ModelAndView documentosMuestra(HttpSession session, 
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "url", required = true) Integer url) throws Exception {
		ModelAndView vista = new ModelAndView("VistaDocumentacion");

		ElementoDocumentacionBean elementoDoc = documentoServicio.obtenerDocumentosMuestra(id);
		elementoDoc.setCodiUrl(url);
		elementoDoc.setUrlVolver(URL_VOLVER.get(url));
		vista.addObject("elementoDoc", elementoDoc);
		return vista;
	}

	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	@RequestMapping(value = "/placaVisavet", method = RequestMethod.GET)
	public ModelAndView documentosPlaceVisavet(HttpSession session,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "url", required = true) Integer url) throws Exception {
		ModelAndView vista = new ModelAndView("VistaDocumentacion");

		ElementoDocumentacionBean elementoDoc = documentoServicio.obtenerDocumentosPlacaVisavet(id);
		elementoDoc.setCodiUrl(url);
		elementoDoc.setUrlVolver(URL_VOLVER.get(url));
		vista.addObject("elementoDoc", elementoDoc);
		return vista;
	}

	@PreAuthorize("hasAnyRole('ADMIN','RESPONSABLEPCR', 'JEFESERVICIO', 'ANALISTALABORATORIO', 'VOLUNTARIO')")
	@RequestMapping(value = "/placaLaboratorio", method = RequestMethod.GET)
	public ModelAndView documentosPlacaLaboratorio(HttpSession session,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "url", required = true) Integer url) throws Exception {
		ModelAndView vista = new ModelAndView("VistaDocumentacion");

		ElementoDocumentacionBean elementoDoc = documentoServicio.obtenerDocumentosPlacaLaboratorioYPlacasVisavet(id);
		
		if (url == URL_VOLVER_PLACA_LABORATORIO_DESDE_ASIGNAR_RESULTADOS_PCR) {			
			elementoDoc.setUrlVolver(URL_VOLVER.get(url) + id);
		} else {
			elementoDoc.setUrlVolver(URL_VOLVER.get(url));
		}
		elementoDoc.setCodiUrl(url);
		vista.addObject("elementoDoc", elementoDoc);
		
		// Para destacar en negrilla los documentos del usuario logado
		vista.addObject("idUsuario", sesionServicio.getUsuario().getId());
		return vista;
	}

	@RequestMapping(value = "/descargar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD', 'TECNICOLABORATORIO', 'RESPONSABLEPCR', 'JEFESERVICIO', 'ANALISTALABORATORIO', 'VOLUNTARIO')")
	public void downloadFile(@RequestParam(value = "id", required = true) Integer id, HttpServletResponse response)
			throws Exception {
		byte[] file = null;
		try {
			DocumentoBusquedaBean docBean = new DocumentoBusquedaBean();
			docBean.setId(id);
			List<DocumentoBean> docs = documentoServicio.findByParams(docBean);
			if (CollectionUtils.isNotEmpty(docs)) {
				file = docs.get(0).getFichero();

				ByteArrayInputStream bis = new ByteArrayInputStream(file);
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + docs.get(0).getNombreDocumento() + "\"");

				ServletOutputStream out = response.getOutputStream();
				IOUtils.copy(bis, out);

				out.flush();
				out.close();
			} else {
				log.info("INFO:: descargar documento, no se ha encontrado documento con id " + id);
			}
		} catch (Exception e) {
			log.error("ERROR:: descargar documento " + e);
			throw e;
		}
	}

	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD', 'TECNICOLABORATORIO', 'RESPONSABLEPCR', 'JEFESERVICIO', 'ANALISTALABORATORIO', 'VOLUNTARIO')")
	public ModelAndView guardar(@Valid @ModelAttribute("beanDocumento") ElementoDocumentacionBean bean,
			BindingResult result, RedirectAttributes redirectAttributes) throws Exception {

		if (result.hasErrors()) {
			ModelAndView vista = new ModelAndView("VistaDocumentacion");
			vista.addObject("elementoDoc", bean);
		} else {
			documentoServicio.guardar(bean);
		}

		redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_GUARDAR_DOCUMENTO));
		return redirectDocumentosTipoElemento(bean.getId(), bean.getTipoElemento(), bean.getCodiUrl());
	}

	@RequestMapping(value = "/borrar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD', 'TECNICOLABORATORIO', 'RESPONSABLEPCR', 'JEFESERVICIO', 'ANALISTALABORATORIO', 'VOLUNTARIO')")
	public ModelAndView borrar(HttpSession session, @RequestParam(value = "idDoc", required = true) Integer idDoc,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "tipoElemento", required = true) Integer tipoElemento,
			@RequestParam(value = "url", required = true) Integer url,
			RedirectAttributes redirectAttributes) throws Exception {

		boolean borrado = documentoServicio.borrar(idDoc);
		if (borrado) {
			redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_BORRAR_DOCUMENTO_OK));
		} else {
			redirectAttributes.addFlashAttribute("mensajeError", ACCIONES_MENSAJE.get(ACCION_BORRAR_DOCUMENTO_KO));
		}
		return redirectDocumentosTipoElemento(id, tipoElemento, url);
	}

	/**
	 * Definicion de url de vuelta tras subir/borrar documento
	 * 
	 * @param id
	 * @param tipoElemento
	 * @return
	 */
	private ModelAndView redirectDocumentosTipoElemento(Integer id, Integer tipoElemento, Integer url) {

		if (tipoElemento.equals(ElementoDocumentacionBean.TIPO_ELEMENTO_MUESTRA)) {
			return new ModelAndView(new RedirectView("/documento/muestra/?id=" + id + "&url=" + url, true));
		}
		if (tipoElemento.equals(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_LABORATORIO)) {
			return new ModelAndView(new RedirectView("/documento/placaLaboratorio/?id=" + id + "&url=" + url, true));
		}
		if (tipoElemento.equals(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_VISAVET)) {
			return new ModelAndView(new RedirectView("/documento/placaVisavet/?id=" + id + "&url=" + url, true));
		}
		return null;
	}

}
