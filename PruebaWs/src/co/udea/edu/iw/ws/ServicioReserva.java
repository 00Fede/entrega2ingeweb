package co.udea.edu.iw.ws;

import java.rmi.RemoteException;

import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.business_logic.ReservaBl;
import co.edu.udea.iw.exception.MyDaoException;


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
}
