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

@Path("servicioDispositivo")
@Component
public class ServicioDispositivo {

	@Autowired
	DispositivoBl dispositivoBl;

	/**
	 * 
	 * Link de muestra:
	 * http://localhost:8080/PruebaWs/rest/servicioDispositivo/todosLosDispositivos
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
	 * 
	 * Link de muestra:
	 * http://localhost:8080/PruebaWs/rest/servicioDispositivo/dispositivosDisponiblesPorModelo
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
	 * 
	 * Link de muestra:
	 * http://localhost:8080/PruebaWs/rest/servicioDispositivo/agregarDispositivo?cedResp=1010&nroSerie=1
	 * &nombre=linterna&modelo=1.5g&peqDesc=una_muy_buena_linterna&restriccion=no_dejar_caer&
	 * observacion=nuevas_pilas&estado=0&disponibilidad=1
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
	 * 
	 * Link de muestra:
	 * http://localhost:8080/PruebaWs/rest/servicioDispositivo/eliminarDispositivoLogicamente?cedulaResponsable=1039&nroSerie=777&justificacion=se_fracturo
	 **/

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

}
