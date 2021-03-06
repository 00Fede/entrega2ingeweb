package co.udea.edu.iw.ws;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.business_logic.DispositivoBl;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.ws.dto.DispositivoWs;


/*
* Clase creada para implementar las servicios web relacionados con nuestra
* entidad Dispositivos
* @author estudiantelis
* Forma de acceder a esta clase http://localhost:8080/PruebaWs/rest/ServicioReserva
*/


@Path("servicioDispositivo")
@Component
public class ServicioDispositivo {

	
	/**
	 * Definicion de nuestra inyeccion de tipo dispositivoBl
	 * que contendrea todos los metodo de la logica del negocio de la entidad Dispositivos
	 */
	@Autowired
	DispositivoBl dispositivoBl;

	/**FRQ-0003 - Ver todos los dispositivos
	 * Servicio encargado de obtener  todos los dispositivos agrupados por modelos
	 * @return listado de todos los dispositivos agrupados por modelo
	 * @throws RemoteException
	 * link: http://localhost:8080/PruebaWs/rest/servicioDispositivo/todosLosDispositivos
	 **/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("todosLosDispositivos")
	public List<DispositivoWs> obtenerDispositivosPorModelo() throws RemoteException {
		List<DispositivoWs> resultado = new ArrayList<>();
		List<Dispositivos> dispositivos = null;
		try {
			dispositivos = dispositivoBl.verDispositivosPorModelo();

			for (Dispositivos dispositivo : dispositivos) {
				DispositivoWs dispositivoWs = new DispositivoWs(dispositivo.getNombre(), dispositivo.getModelo(),
						dispositivo.getDescripcion(), dispositivo.getRestriccion(), dispositivo.getObservacion(),
						dispositivo.getEstado(), dispositivo.getDisponibilidad());

				resultado.add(dispositivoWs);
			}

		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(), e);
		}
		return resultado;
	}

	/**
	 * FRQ-0003 - Ver todos los dispositivos disponibles 
	 * Servicio que Permite al usuario visualizar todos los dispositivos disponibles 
	 * agrupados por modelo
	 * @return Lista de dispositivos agrupados por modelo
	 * @throws MyDaoException
	 * Link de muestra: http://localhost:8080/PruebaWs/rest/servicioDispositivo/dispositivosDisponiblesPorModelo
	 **/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("dispositivosDisponiblesPorModelo")
	public List<DispositivoWs> obtenerDispositivosDisponiblesPorModelo() throws RemoteException {
		List<DispositivoWs> resultado = new ArrayList<>();
		List<Dispositivos> disponibles = null;
		try {
			disponibles = dispositivoBl.verDispositivosDisponiblesPorModelo();

			for (Dispositivos dispositivo : disponibles) {
				DispositivoWs dispositivoWs = new DispositivoWs(dispositivo.getNombre(), dispositivo.getModelo(),
						dispositivo.getDescripcion(), dispositivo.getRestriccion(), dispositivo.getObservacion(),
						dispositivo.getEstado(), dispositivo.getDisponibilidad());

				resultado.add(dispositivoWs);
			}

		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(), e);
		}
		return resultado;
	}

	/**
	 * FRQ-0004 - Agregar Dispositivo
	 * Servicio que Permite a un usuario administrador crear un dispositivo
	 * @param nroSerie 
	 * @param nombre 
	 * @param modelo 
	 * @param peqDesc
	 * @param fotoRAW
	 * @param restriccion
	 * @param observacion
	 * @throws MyDaoException,SQLException,SerialException
	 * Link de muestra:
	 	http://localhost:8080/PruebaWs/rest/servicioDispositivo/agregarDispositivo?cedResp=1010&nroSerie=1
	 	&nombre=linterna&modelo=1.5g&peqDesc=una_muy_buena_linterna&restriccion=no_dejar_caer&
	 	observacion=nuevas_pilas&estado=0&disponibilidad=1
	 **/
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("agregarDispositivo")
	public String agregarNuevoDispositivo(@QueryParam("cedResp") int cedResp, @QueryParam("nroSerie") int nroSerie,
			@QueryParam("nombre") String nombre, @QueryParam("modelo") String modelo,
			@QueryParam("peqDesc") String peqDesc, @QueryParam("restriccion") String restriccion,
			@QueryParam("observacion") String observacion, @QueryParam("estado") String estado,
			@QueryParam("disponibilidad") String disponibilidad) throws RemoteException, SerialException, SQLException {

		try {
			byte[] fotoRAW = { '1', '2' };
			dispositivoBl.agregarDispositivo(cedResp, nroSerie, nombre, modelo, peqDesc, fotoRAW, restriccion,
					observacion, estado, disponibilidad);

			return "Se agrego exitosamente el dispositivo";
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(), e);
		}

	}

	/**
	 * FRQ-0005 - Eliminar dispositivo
	 * Servicio que Permite al usuario administrador eliminar un dispositivo
	 * @param nroSerie
	 * @param cedulaResponsable
	 * @param justificacion Razon de la eliminación
	 * @throws MyDaoException
	 *  Link de muestra:
	 * http://localhost:8080/PruebaWs/rest/servicioDispositivo/eliminarDispositivoLogicamente?cedulaResponsable=1039&nroSerie=777&justificacion=se_fracturo
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("eliminarDispositivoLogicamente")
	public String eliminarDispositivoLogicamente(@QueryParam("cedulaResponsable") int cedulaResponsable,
			@QueryParam("nroSerie") int nroSerie, @QueryParam("justificacion") String justificacion)
			throws RemoteException {
		try {

			dispositivoBl.eliminarDispositivoLogicamente(cedulaResponsable, nroSerie, justificacion);
			return "La dispositivo fue dado de baja exitosamente";
		} catch (MyDaoException e) {

			throw new RemoteException(e.getMessage(), e);
		}
	}
	
	/**
	 * Servicio Web para FRQ-0025 Modificar dispositivo
	 * Este servicio permite la modificacion de un dispositivo.
	 * @param cedResp
	 * @param nroSerie
	 * @param nombre
	 * @param modelo
	 * @param peqDesc
	 * @param restriccion
	 * @param observacion
	 * @param estado
	 * @param disponibilidad
	 * @return
	 * @throws RemoteException
	 * @throws SerialException
	 * @throws SQLException
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("modificarDispositivo")
	public String modificarDispositivo(@QueryParam("cedResp") int cedResp, @QueryParam("nroSerie") int nroSerie,
			@QueryParam("nombre") String nombre, @QueryParam("modelo") String modelo,
			@QueryParam("peqDesc") String peqDesc, @QueryParam("restriccion") String restriccion,
			@QueryParam("observacion") String observacion, @QueryParam("estado") String estado,
			@QueryParam("disponibilidad") String disponibilidad) 
			throws RemoteException, SerialException, SQLException {
		try {

			byte[] fotoRAW = { '1', '2' };
			dispositivoBl.modificarDispositivo(cedResp, nroSerie, nombre, modelo, peqDesc, fotoRAW, restriccion,
					observacion, estado, disponibilidad);
			return "El dispositivo fue modificado exitosamente";
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}
	
	

}
