package co.udea.edu.iw.ws;

import java.rmi.RemoteException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import co.edu.udea.iw.business_logic.SancionBl;
import co.edu.udea.iw.exception.MyDaoException;

/*
 * Clase creada para implementar las servicios web relacionados con nuestra
 * entidad sanciones
 * @author andres montoya
 * Forma de acceder a esta clase http://localhost:8080/PruebaWs/rest/ServicioSancion
 */

@Path("/ServicioSancion")
@Component
public class ServicioSancion {

	/**
	 * Definicion de nuestra inyeccion de tipo SancionBl
	 * que contendrea todos los metodo de la logica del negocio de la entidad sancion
	 */
	@Autowired
	SancionBl sancionBl;
	
	/**
	 * El siguiente metodo ser� el servicioWeb de nuestro metodo de la logica del negocio llamado
	 * generarSancion_limite de tiempo este metodo generar� una sancion siempre y cuando el usuario
	 * haya sobrepasado el limite de tiempo es decir 8 horas para entregar un instrumento
	 * @param id_sancion
	 * @param id_reserva
	 * @return retornar� "La sancion se ha registrado con exito", cuando se haya creada una sancion
	 * en caso de que no retorne nada, se entender� que los valores no cumpliar las condiciones para registrar una
	 * reserva.
	 * @throws RemoteException
	 * Forma de llamar al servicio 
	 * http://localhost:8080/PruebaWs/rest/ServicioSancion/limiteTiempo?estadoInadecuado?id_sancion=25&id_reserva=112
	 */
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("limiteTiempo")
	public String generarSancion_limiteTiempo(@QueryParam("id_sancion")int id_sancion,
			@QueryParam("id_reserva")int id_reserva) throws RemoteException{
		
		try{
			java.util.Date fechaActual = new java.util.Date();
			sancionBl.generarSancion_limiteTiempo(id_sancion, id_reserva, fechaActual);
			return "La sancion se ha registrado con exito";
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
		
	}
	
	
	/**
	 * El siguiente metodo sera el servicio web para el metodo llamado generarSancion_estadoNoAdecuado
	 * de nuestra implementacion de SancionBl, este metodo crear� una sancion siempre y cuando el retorno de un
	 * instrumento se haya generado de forma inadecuada, cuando esto pasa el estado de reserva ser� 4
	 * @param id_sancion
	 * @param id_reserva
	 * @return retornar� "La sancion se ha registrado exitosamente" cuando cumpla todas las condiciones
	 * en caso de no cumplir ninguna condicion no retornar� nada
	 * @throws RemoteException
	 * Se invoca de la siguiente forma 
	 * http://localhost:8080/PruebaWs/rest/ServicioSancion/estadoInadecuado?id_sancion=25&id_reserva=9999
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("estadoInadecuado")
	public String generarSancion_estadoInadecuado(@QueryParam("id_sancion")int id_sancion,
			@QueryParam("id_reserva")int id_reserva) throws RemoteException{
		
		
		try{
			sancionBl.generarSancion_estadoNoAdecuado(id_sancion, id_reserva);
			return "La sancion se ha registrado exitosamente";
			
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	
	
	/**
	 * El siguiente metodo sera el servicio web para el metodo llamado generarSancion_incumplimientoReserva
	 * de nuestra implementacion de SancionBl, este metodo crear� una sancion siempre y cuando quien haya reservado
	 * no se haya presentado a la hora indicada para la reserva	 * 
	 * @param id_sancion
	 * @param id_reserva
	 * @return retornar� "La sancion se ha registrado exitosamente" cuando cumpla todas las condiciones
	 * en caso de no cumplir ninguna condicion no retornar� nada
	 * @throws RemoteException
	 * Se invoca de la siguiente forma 
	 * http://localhost:8080/PruebaWs/rest/ServicioSancion/incumplimiento?id_sancion=26&id_reserva=9988
	 */
		@POST
		@Produces(MediaType.TEXT_HTML)
		@Path("incumplimiento")
		public String generarSancion_incumplimientoReserva(@QueryParam("id_sancion")int id_sancion,
				@QueryParam("id_reserva")int id_reserva) throws RemoteException{
			
			try{
				java.util.Date fechaActual = new java.util.Date();
				sancionBl.generarSancion_incumplimientoReserva(id_sancion, id_reserva, fechaActual);
				return "La sancion se ha creado exitosamente";
			}catch(MyDaoException e){
				throw new RemoteException(e.getMessage(),e);
			}
		
		}
	
	
	
	
	/**
	 * El siguiente metodo sera el servicio web para el metodo llamado generarSancion_cancelacionInoportuna
	 * de nuestra implementacion de SancionBl, este metodo crear� una sancion siempre y cuando quien haya reservado
	 * haya realizado una cancelacion almenos una hora antes de la hora de la fecha inicial de la reserva 
	 * @param id_sancion
	 * @param id_reserva
	 * @return retornar� "La sancion se ha registrado exitosamente" cuando cumpla todas las condiciones
	 * en caso de no cumplir ninguna condicion no retornar� nada
	 * @throws RemoteException
	 * Se invoca de la siguiente forma 
	 * http://localhost:8080/PruebaWs/rest/ServicioSancion/cancelacion?id_sancion=27&id_reserva=9
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("cancelacion")
	public String generarSancion_cancelacionInoportuna(@QueryParam("id_sancion")int id_sancion
			,@QueryParam("id_reserva")int id_reserva) throws RemoteException{
		
		try{
			sancionBl.generarSancion_cancelacionInoportuna(id_sancion, id_reserva);
			return "La sancion se ha creado exitosamente";
			
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	
	/**
	 * El siguiente metodo sera el servicio web para el metodo llamado generarSancion_extravio
	 * de nuestra implementacion de SancionBl, este metodo crear� una sancion siempre y cuando quien haya 
	 *  realizado un prestamos, el instrumentos prestado haya sido extraviado
	 * @param id_sancion
	 * @param id_reserva
	 * @return retornar� "La sancion se ha registrado exitosamente" cuando cumpla todas las condiciones
	 * en caso de no cumplir ninguna condicion no retornar� nada
	 * @throws RemoteException
	 * Se invoca de la siguiente forma 
	 * http://localhost:8080/PruebaWs/rest/ServicioSancion/extravio?id_sancion=29&id_reserva=91
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("extravio")
	public String generarSancion_extravio(@QueryParam("id_sancion")int id_sancion,
			@QueryParam("id_reserva")int id_reserva) throws RemoteException{
		
		try{
			sancionBl.generarSancion_extravio(id_sancion, id_reserva);
			return "La sancion se ha creado exitosamente";
			
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	/**
	 * El siguiente metodo ser� el servicio web para el metodo llamado retirarSancion de nuestra
	 * implementacion SancionBl, este metodo eliminar� una sancion siempre y cuando quien la quiera eliminar sea un
	 * Administrador
	 * @param id_sancion
	 * @param id_administrador
	 * @return Retornar� "La sancion se ha eliminado exitosamente" si cumple con todos las 
	 * condiciones
	 * @throws RemoteException
	 * http://localhost:8080/PruebaWs/rest/ServicioSancion/retirar?id_sancion=28&id_admin=10189
	 */
	@DELETE
	@Produces(MediaType.TEXT_HTML)
	@Path("retirar")
	public String retirarSancion(@QueryParam("id_sancion")int id_sancion,
			@QueryParam("id_admin")int id_administrador) throws RemoteException{
		
		try{
			sancionBl.retirarSancion(id_sancion, id_administrador);
			return "La sancion se ha eliminado exitosamente";
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	
	
	
	
	/**
	 * El siguiente metodo ser� el servicio web para el metodo llamado retirarSancion_tiempoCulminado de nuestra
	 * implementacion SancionBl, este metodo eliminar� una sancion siempre y cuando la sancion a eliminar ya haya 
	 * terminado su tiempo de penalizacion
	 * @param id_sancion
	 * @return Retornar� "La sancion se ha eliminado exitosamente" si cumple con todos las 
	 * condiciones
	 * @throws RemoteException
	 *http://localhost:8080/PruebaWs/rest/ServicioSancion/tiempoCulminado?id_sancion=4
	 */
	@DELETE
	@Produces(MediaType.TEXT_HTML)
	@Path("tiempoCulminado")
	public String retirarSancion_tiempoCulminado(@QueryParam("id_sancion")int id_sancion) throws RemoteException{
		
		try{
			java.util.Date fechaActual = new java.util.Date();
			sancionBl.retirarSancion_tiempoCulminado(id_sancion, fechaActual);
			return "La sancion se ha eliminado exitosamente";
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
	}
	/**
	 * El siguiente metodo ser� el servicio web para el metodo llamado modificarSancion de nuestra implementacion
	 * SancionBl, este metodo consiste en rebajar el tiempo de penalizacion dependiendo de la razon por la cual 
	 * se quiera rebajar, para poder efectuarse deber� ser un administrador y debera tener una razon valida.
	 * @param id_sancion
	 * @param id_administrador
	 * @param razon
	 * @return
	 * @throws RemoteException
	 * http://localhost:8080/PruebaWs/rest/ServicioSancion/modificar?id_sancion=2&id_administrador=1021&razon=multa
	 */
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Path("modificar")
	public String modificarSancion(@QueryParam("id_sancion")int id_sancion,
			@QueryParam("id_administrador")int id_administrador,
			@QueryParam("razon")String razon) throws RemoteException{
		
		
		try{
			sancionBl.modificarSancion(id_sancion, id_administrador, razon);
			return "La modificacion se ha realizado adecuadamente";
		}catch(MyDaoException e){
			throw new RemoteException(e.getMessage(),e);
		}
		
	}
}
