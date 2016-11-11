package co.udea.edu.iw.ws;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.business_logic.ReservaBl;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.ws.dto.DispositivoWs;
import co.edu.udea.iw.ws.dto.ReservaWs;


/*
 * Clase creada para implementar las servicios web relacionados con nuestra
 * entidad reservas
 * @author andres montoya
 * Forma de acceder a esta clase http://localhost:8080/PruebaWs/rest/ServicioReserva
 */


@Path("ServicioReserva")
@Component
public class ServicioReserva {

	/**
	 * Definicion de nuestra inyeccion de tipo reservaBl
	 * que contendrea todos los metodo de la logica del negocio de la entidad reserva
	 */
	@Autowired
	ReservaBl reservaBl;
	
	
	
	/**
	 * Este metodo permitirá modificar el tiempo de prestamos de una reserva, siempre y cuando aun este
	 * en estado de reserva, recuerden que el estado de reserva se definira en el campo estado cuando sea igual
	 * a 0, si cumple esta condicion el usuario podrá modificar el tiempo de prestamos de un instrumento
	 * @param id, identificacion de la reserva
	 * @param tiempo, tiempo el cual se prestará el instrumento
	 * @return retornará "Se ha modificado exitosamente la reserva" cuando cumpla todas las condiciones
	 * @throws RemoteException
	 * http://localhost:8080/PruebaWs/rest/ServicioReserva/modificar?id=9988&tiempo=1021
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("modificar")
	public String modificarReserva(@QueryParam("id")int id,
			@QueryParam("tiempo")int tiempo
			) throws RemoteException{
		
		try{
			reservaBl.modificarReserva(id, tiempo);
			return "Se ha modificado exitosamente la reserva";
			
		}catch(MyDaoException e){
			throw new RemoteException(  e.getMessage(),e);
		}
	}
	
	
	/**
	 * Este metodo hace referencia al metodo cancelarReserva de la implementacion de la logica del negocio
	 * de ReservaBl, este metodo eliminará una reserva "logicamente" cuando esta se haya cancelado por uno de sus
	 * usuarios, esta tendrá una vez eliminado estado de 5 si la reserva se cancelo inoportunamente
	 * o estado de 3 si la reserva se cancelo oportunamente, se considera inoportuna cunado cancela con una hora
	 * antes de la fecha del prestamos
	 * @param id
	 * @return retornará "La reserva ha sido cancelada con exito" cuando cumpla todas las condiciones
	 * @throws RemoteException
	 * http://localhost:8080/PruebaWs/rest/ServicioReserva/eliminar?id=2
	 * Será @PUT debido a que es un borrado logico
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("eliminar")
	public String cancelarReserva(@QueryParam("id")int id) throws RemoteException{
		
		try{
			java.util.Date fechaActual = new java.util.Date();
			reservaBl.cancelarReserva(id,fechaActual);
			return "La reserva ha sido cancelada con exito";
		}catch(MyDaoException e){
			
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	
	
	/**
	 * Este metodo hace referencia al metodo cancelarPrestamo de la implementacion de la logica del negocio
	 * de ReservaBl, este metodo eliminará un prestamo "logicamente" cuando esta se haya culminado,
	 *  esta tendrá una vez eliminado estado de 2,4,6 dependiendo de el estado ingresado
	 *  estado 2--->estado== "adecuado"
	 *  estado 4--->estado== "inadecuado"
	 *  estado 6--->estado== "extravio"
	 * @param id, identificacion de la reserva 
	 * @return retornará "Se ha registrado la terminacion del prestamo" cuando cumpla todas las condiciones
	 * @throws RemoteException
	 * http://localhost:8080/PruebaWs/rest/ServicioReserva/cancelarPrestamo?id=2&estado=adecuado
	 * Será @PUT debido a que es un borrado logico
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("cancelarPrestamo")
	public String cancelarPrestamos(@QueryParam("id_reserva")int id_reserva,
			@QueryParam("estado")String estado) throws RemoteException{
		
		try{
			reservaBl.cancelarPrestamo(id_reserva, estado);
			return "Se ha registrado la terminacion del prestamo";
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	/**
	 * Servicio Web para  FRQ-0030 - Ver historial de prestamos por investigador
	 * Permite a un usuario administrador ver el listado de los prestamos y reservas realizadas
	 * por un investigador determinado.
	 * @param idActor - id del administrador
	 * @param id - id del investigador
	 * @return Lista de reservas con los siguientes campos: id de reserva, fecha inicio, fecha fin (si aplica)
	 * id dispositivo, foto dispositivo y nombre dispositivo.
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@GET
	@Path("reservasinv")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReservaWs> reservasInvestigador(@QueryParam("actor")int idActor,
			@QueryParam("id")int id) throws RemoteException, SQLException{
		List<Reserva> data = null;
		List<ReservaWs> reservas = new ArrayList<>();
		try{
			data = reservaBl.verReservasPorInvest(id, idActor); //se buscan reservas por un investigador
			Iterator<Reserva> i = data.iterator();
			
			while(i.hasNext()){
				Reserva r = i.next();
				Dispositivos d = r.getId_dispositivo();
				//podria iniciar d aqui y tomar valores en dws
				if(r.getId_cedula().getCedula()==id){
					
					DispositivoWs disp = new DispositivoWs();
					disp.setId(d.getNumero_serie());
					disp.setNombre(d.getNombre()); 
					disp.setFoto(d.getFoto().getBytes(1,(int)d.getFoto().length())); 
					ReservaWs rw = new ReservaWs(r.getId_reserva(), 
							r.getFecha_inicio(), r.getFecha_entrega(),disp);
					
					reservas.add(rw);
				}
			}
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
		return reservas;		
	}
	
	/**
	 * Servicio web para FRQ-0031 Notificar Devolucion
	 * Este servicio permite a un usuario administrador notificar al sistema la devolucion
	 * de un dispositivo asociado a una reserva. El administrador debe agregar el id de la reserva
	 * y el estado de la reserva: 
	 * @param idAdmin - id del administrador
	 * @param id - id de la reserva
	 * @param estado - 2(prestamo terminado),3(reserva cancelada),4(prestamo terminado, dispositivo
	 * en mal estado), 5(cancelo reserva inoportunamente), 6(extravio de dispositivo)
	 * @return Mensaje de operacion exitosa
	 * @throws RemoteException
	 */
	@PUT
	@Path("devolucion")
	@Produces(MediaType.TEXT_HTML)
	public String notificarDevolucion(@QueryParam("admin")int idAdmin, @QueryParam("id")int id,
			@QueryParam("estado")int estado)throws RemoteException{
		try {
			reservaBl.notificarDevolucion(id, idAdmin, estado);
			
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
		return "La reserva con id "+id+" fue actualizada correctamente"
		+ " con estado "+estado+".";
	}
}
