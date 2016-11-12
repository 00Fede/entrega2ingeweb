package co.edu.udea.iw.bl_imp;

import java.util.Date;



import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.business_logic.SancionBl;
import co.edu.udea.iw.dao.ReservaDao;
import co.edu.udea.iw.dao.SancionDao;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Sancion;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;


/**
 * En esta clase realizamores la implementacion de los metodos de la
 * logica de lnegocio de nuestra entidad SancionBl
 * @author andres montoya
 *
 */
@Transactional
public class SancionBlImp implements SancionBl {

	
	SancionDao sancionDao;
	ReservaDao reservaDao;
	UsuariosDao usuariosDao;
	
	/**
	 * Constructor de la implementacion. Necesario para inyeccion Spring
	 * @param sancionDao
	 * @author andres montoya
	 */
	public  SancionBlImp(SancionDao sancionDao,ReservaDao reservaDao,UsuariosDao usuariosDao) {
		this.sancionDao = sancionDao;
		this.reservaDao= reservaDao;
		this.usuariosDao= usuariosDao;
		
	}
	
	@Override
	public void generarSancion_limiteTiempo(int id_sancion,int id_reserva, Date fecha) throws MyDaoException {
		
		if(id_reserva == 0){
			throw new MyDaoException("debe especificar un identificador", null);
		}
		
		if(id_reserva == 0){
			throw new MyDaoException("debe especificar un identificador para la sancion", null);
		}
		
		Reserva reserva=new Reserva();
		reserva=reservaDao.obtener(id_reserva);
		
		if(reserva == null){
			throw new MyDaoException("La reserva no existe",null);
		}
		
		if(fecha == null){
			throw new MyDaoException("Debe especificar la fecha", null);
		}
		
		if(reserva.getEstado() != 1){ //aun es reserva
			
			throw new MyDaoException("Esta entidad no tiene estado de reserva", null);
		}
		long time_actual=fecha.getTime();
		long time_inicial=reserva.getFecha_inicio().getTime();
		long time_difference=time_actual-time_inicial; //Retorna la diferencia en milisegundos
		long time_max=new Long(28800000);
		
		if(time_difference > time_max){// Se generar� la sancion
			Sancion sancion= new Sancion();
			sancion.setFecha_inicio(fecha);
			sancion.setId_dispositivo(reserva.getId_dispositivo());
			sancion.setId_cedula(reserva.getId_cedula());
			sancion.setId_responsable(reserva.getId_responsable());
			sancion.setRazon("Ha sobrepasado el tiempo de prestamos");
			sancion.setTiempo_sancion(2);
			sancion.setId_sancion(id_sancion);
			
			sancionDao.guardar(sancion);
			
		}
		
		
		
	}

	@Override
	public void generarSancion_estadoNoAdecuado(int id_sancion,int id_reserva) throws MyDaoException {
		if(id_sancion == 0){
			throw new MyDaoException("El identificador de sanciones es invalido",null);
		}
		
		if(id_reserva == 0){
			throw new MyDaoException("El identificador de reserva es invalido",null);
		}
		Sancion verficarSancion;
		verficarSancion=sancionDao.obtener(id_sancion);
		
		if(verficarSancion != null){
			throw new MyDaoException("Una sancion con ese identificador ya se encuentra registrada",null);
		}
		Reserva reserva;
		reserva=reservaDao.obtener(id_reserva);
		
		if(reserva == null){
			throw new MyDaoException("No existe una reserva con ese identificador",null);
		}
		
		int estado=reserva.getEstado();
		if(estado == 4){// cuando el estado de una reserva es 4 indica que el retorno fue inadecuado y se crea la sancion
			java.util.Date fechaActual = new java.util.Date();
			Sancion sancion=new Sancion();
			sancion.setId_dispositivo(reserva.getId_dispositivo());
			sancion.setId_cedula(reserva.getId_cedula());
			sancion.setId_responsable(reserva.getId_responsable());
			sancion.setRazon("Ha retornado un objeto de manera inadecuada");
			sancion.setTiempo_sancion(10);
			sancion.setId_sancion(id_sancion);
			sancion.setFecha_inicio(fechaActual);
			
			sancionDao.guardar(sancion);
		}
	}

	@Override
	public void generarSancion_incumplimientoReserva(int id_sancion,int id_reserva, Date fecha) throws MyDaoException {
		
		if(id_sancion == 0){
			throw new MyDaoException("El identificador de sanciones es invalido",null);
		}
		
		if(id_reserva == 0){
			throw new MyDaoException("El identificador de reserva es invalido",null);
		}
		Sancion verficarSancion;
		verficarSancion=sancionDao.obtener(id_sancion);
		
		if(verficarSancion != null){
			throw new MyDaoException("Una sancion con ese identificador ya se encuentra registrada",null);
		}
		Reserva reserva;
		reserva=reservaDao.obtener(id_reserva);
		
		if(reserva == null){
			throw new MyDaoException("No existe una reserva con ese identificador",null);
		}
		
		if(fecha == null){
			throw new MyDaoException("Debe especificar la fecha", null);
		}
		
		long time_actual=fecha.getTime();
		long time_inicial=reserva.getFecha_inicio().getTime();
		int estado=reserva.getEstado();
		
		
		if((time_actual>time_inicial) && (estado == 0)){//estado en 0 implica que aun es una reserva
														// y que el tiempo actual sea mayor implica que no ha hecho
														//el prestamo aun.
			
			
			java.util.Date fechaActual = new java.util.Date();
			Sancion sancion=new Sancion();
			sancion.setId_dispositivo(reserva.getId_dispositivo());
			sancion.setId_cedula(reserva.getId_cedula());
			sancion.setId_responsable(reserva.getId_responsable());
			sancion.setRazon("Ha incumplido una reserva");
			sancion.setTiempo_sancion(6);
			sancion.setId_sancion(id_sancion);
			sancion.setFecha_inicio(fechaActual);
			
			sancionDao.guardar(sancion);
			
			
		}
		
		
	}

	@Override
	public void generarSancion_cancelacionInoportuna(int id_sancion,int id_reserva) throws MyDaoException {
		
		if(id_sancion == 0){
			throw new MyDaoException("El identificador de sanciones es invalido",null);
		}
		
		if(id_reserva == 0){
			throw new MyDaoException("El identificador de reserva es invalido",null);
		}
		Sancion verficarSancion;
		verficarSancion=sancionDao.obtener(id_sancion);
		
		if(verficarSancion != null){
			throw new MyDaoException("Una sancion con ese identificador ya se encuentra registrada",null);
		}
		Reserva reserva;
		reserva=reservaDao.obtener(id_reserva);
		
		if(reserva == null){
			throw new MyDaoException("No existe una reserva con ese identificador",null);
		}
		
				
		int estado=reserva.getEstado();
		
		if(estado == 5){
			java.util.Date fechaActual = new java.util.Date();
			Sancion sancion=new Sancion();
			sancion.setId_dispositivo(reserva.getId_dispositivo());
			sancion.setId_cedula(reserva.getId_cedula());
			sancion.setId_responsable(reserva.getId_responsable());
			sancion.setRazon("Cancelacion inoportuna");
			sancion.setTiempo_sancion(2);
			sancion.setId_sancion(id_sancion);
			sancion.setFecha_inicio(fechaActual);
			
			sancionDao.guardar(sancion);
			
			
		}
		
		
	}

	@Override
	public void generarSancion_extravio(int id_sancion,int id_reserva) throws MyDaoException {
		
		if(id_sancion == 0){
			throw new MyDaoException("El identificador de sanciones es invalido",null);
		}
		
		if(id_reserva == 0){
			throw new MyDaoException("El identificador de reserva es invalido",null);
		}
		Sancion verficarSancion;
		verficarSancion=sancionDao.obtener(id_sancion);
		
		if(verficarSancion != null){
			throw new MyDaoException("Una sancion con ese identificador ya se encuentra registrada",null);
		}
		Reserva reserva;
		reserva=reservaDao.obtener(id_reserva);
		
		if(reserva == null){
			throw new MyDaoException("No existe una reserva con ese identificador",null);
		}
		
		int estado=reserva.getEstado();
		
		if(estado == 6){ //Extravio el objeto
			
			java.util.Date fechaActual = new java.util.Date();
			Sancion sancion=new Sancion();
			sancion.setId_dispositivo(reserva.getId_dispositivo());
			sancion.setId_cedula(reserva.getId_cedula());
			sancion.setId_responsable(reserva.getId_responsable());
			sancion.setRazon("Extravio el objeto");
			sancion.setTiempo_sancion(999);
			sancion.setId_sancion(id_sancion);
			sancion.setFecha_inicio(fechaActual);
			
			sancionDao.guardar(sancion);
			
			
		}
	}

	@Override
	public void modificarSancion(int id_sancion, int id_administrador, String razon) throws MyDaoException {
		if(id_sancion == 0){
			throw new MyDaoException("El identificador de sancion es invalido",null);
		}
		
		if(id_administrador == 0){
			throw new MyDaoException("El identificador de el administrador es invalido",null);
		}
		
		if (razon == null || "".equals(razon.trim())) {
			throw new MyDaoException("Debe especificar la razon", null);
		}
		
		Sancion verificarSancion;
		verificarSancion=sancionDao.obtener(id_sancion);
		
		if(verificarSancion == null){
			throw new MyDaoException("No existe una sancion con ese identificador",null);
		}
		
		Usuarios admin;
		admin=usuariosDao.obtener(id_administrador);
		
		if(admin == null){
			throw new MyDaoException("No existe usuarios con ese identificador",null);
		}
		
		if(!admin.getRol().equals("administrador")){
			throw new MyDaoException("El id no corresponde a un administrador",null);
		}
		
		Sancion sancion;
		sancion=sancionDao.obtener(id_sancion);
		
			if(razon.equals("multa")){// Si pago la multa, rebajaremos dos dias de sancion
				int tiempo_sancion=sancion.getTiempo_sancion();
				tiempo_sancion=tiempo_sancion-2;
				sancion.setTiempo_sancion(tiempo_sancion);
				sancionDao.modificar(sancion);
			}
			if(razon.equals("extravio")){//Cuando retorne el objeto quitaremos el estado de bloqueado
											//y le dejaremos un tiempo de penitencia de solo 3 dias
				sancion.setTiempo_sancion(3);
				sancionDao.modificar(sancion);
			}
			
		
	}

	@Override
	public void retirarSancion(int id_sancion, int id_administrador) throws MyDaoException {
		
		if(id_sancion == 0){
			throw new MyDaoException("El identificador de sancion es invalido",null);
		}
		
		if(id_administrador == 0){
			throw new MyDaoException("El identificador del administrador es invalido",null);
		}
		
		Sancion verficarSancion;
		verficarSancion=sancionDao.obtener(id_sancion);
		
		if(verficarSancion == null){
			throw new MyDaoException("No existe una sancion con ese identificador",null);
		}
		
		
		Usuarios user;
		user=usuariosDao.obtener(id_administrador);
		
		if(user == null){
			throw new MyDaoException("No existe un usuario con ese identificador",null);
		}
		
		String rol=user.getRol();
		
		if(rol.equals("administrador")){
			sancionDao.eliminar(id_sancion);
		}
		else{
			throw new MyDaoException("La identificacion no corresponde a un adminsitrador",null);
		}
	}

	@Override
	public void retirarSancion_tiempoCulminado(int id_sancion, Date fecha) throws MyDaoException {
		
		if(id_sancion ==0){
			throw new MyDaoException("El identificador de la sancion es invalido",null);
		}
		
		if(fecha == null){
			throw new MyDaoException("Debe especificar la fecha",null);
		}
		
		Sancion verficarSancion;
		verficarSancion=sancionDao.obtener(id_sancion);
		
		if(verficarSancion == null){
			throw new MyDaoException("No existe una sancion con ese identificador",null);
		}
		
		Sancion sancion=sancionDao.obtener(id_sancion);
		int tiempo_sancion=sancion.getTiempo_sancion();
		Date fecha_sancion=sancion.getFecha_inicio();
		
		long time_now=fecha.getTime();
		long time_begin=fecha_sancion.getTime();
		long time_difference=time_now-time_begin;	

		int miliseconds=tiempo_sancion*24*60*60*1000;
		long time_miliseconds_sancion=new Long(miliseconds);
		
		if(time_difference>time_miliseconds_sancion){// Si se cumple esto ha caducado su sancion
			sancionDao.eliminar(id_sancion);
		}
		
	}


}
