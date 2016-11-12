package co.edu.udea.iw.bl_imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.business_logic.ReservaBl;
import co.edu.udea.iw.dao.AuthDao;
import co.edu.udea.iw.dao.ReservaDao;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Autenticacion;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.util.validations.Validaciones;

/*
 * see ReservaBl
 * En esta clase realizamores la implementacion de la logica del negocio 
 * de nuestra entidad Reserva
 * @author Andres Montoya
 */
@Transactional
public class ReservaBlImp implements ReservaBl {

	ReservaDao reservaDao;
	UsuariosDao usuariosDao;
	AuthDao auth;
	
	/**
	 * Constructor de la implementacion. Necesario para inyeccion Spring
	 * @param reservaDao
	 */
	public  ReservaBlImp(ReservaDao reservaDao, UsuariosDao userDao, AuthDao authDao) {
		this.reservaDao = reservaDao;
		this.usuariosDao = userDao;
		this.auth = authDao;
	}
	
	@Override
	public void cancelarReserva(int id,Date fecha) throws MyDaoException {
		

		if(id == 0){
			throw new MyDaoException("El identificador ingresado no es valido", null);
		}
		
		if(fecha == null){
			throw new MyDaoException("Fecha incorrecta",null);
		}
		
		Reserva reserva;
		reserva=reservaDao.obtener(id);
		
		if(reserva == null){
			throw new MyDaoException("La reserva no se ha encontrado", null);
		}
		
		//Para que el usuario pueda cancelar una reserva inicialmente
		//aun debera ser reserva por ende estado ser� 0
		
		if(reserva.getEstado() != 0){ //aun es reserva
			
			throw new MyDaoException("Esta entidad no tiene estado de reserva", null);
		}
		//es diferente de reserva, es decir o ya esta en prestemo activo o inactivo o en cancelado
		
		long time_cancelacion=fecha.getTime();
		long time_prestamo=reserva.getFecha_inicio().getTime();
		
		if(time_cancelacion>(time_prestamo -3600000)&&(time_cancelacion<time_prestamo)){
			reserva.setEstado(5);
		}else{
			reserva.setEstado(3);
		}
		
		
		reservaDao.modificar(reserva);

	}

	@Override
	public void modificarReserva(int id, int Tiempo) throws MyDaoException {
		if(id == 0){
			throw new MyDaoException("El identificador ingresado no es valido", null);
		}
		
		if(Tiempo == 0){
			throw new MyDaoException("Debe especfiicar el tiempo de sancion", null);
		}
		
		Reserva reserva;
		reserva = reservaDao.obtener(id);
		
		if(reserva == null){
			throw new MyDaoException("La reserva con ese identificador no existe",null);
		}
		
		if(reserva.getEstado() != 0){
			throw new MyDaoException("No tiene estado de reserva",null);
		}
		
		reserva.setTiempo_reserva(Tiempo);
		reservaDao.modificar(reserva);
		
		
		
	}

	@Override
	public void cancelarPrestamo(int id_reserva, String estado) throws MyDaoException {
		
		if(id_reserva == 0){
			throw new MyDaoException("No se ha definiado el identificador de reserva",null);
		}
		
		if (estado == null || "".equals(estado.trim())) {
			throw new MyDaoException("Debe especificar el estado", null);
		}
		
		Reserva reserva;
		reserva=reservaDao.obtener(id_reserva);
		
		if(reserva == null){
			throw new MyDaoException("No existe la reserva con ese identificador",null);
		}
		
		if(reserva.getEstado() != 1){
			throw new MyDaoException("No tiene estado de prestamo",null);
		}
		
		if(estado.equals("adecuado")){
			reserva.setEstado(2);
		}
		
		if(estado.equals("inadecuado")){
			reserva.setEstado(4);
		}
		
		if(estado.equals("extravio")){
			reserva.setEstado(6);
		}
		
		reservaDao.modificar(reserva);
	}

	@Override
	public List<Reserva> verReservasPorInvest(int idInvest, int idResponsable)
			throws MyDaoException {
		if(!isActiveUser(idResponsable)) throw new MyDaoException("No se encuentra activo",null);
		if(!matchRol(idResponsable, "administrador")) throw new MyDaoException("No tiene permiso para ver este listado",null);
		List<Reserva> resultado = new ArrayList<Reserva>();
		List<Reserva> todas = reservaDao.obtener();
		if(todas==null||todas.size()==0) throw new MyDaoException("No existen reservas de investigador con cedula "
				+ idInvest,null);
		
		
		Iterator<Reserva> i = todas.iterator();
		while(i.hasNext()){
			Reserva r = i.next();
			if(r.getId_cedula().getCedula()==idInvest){
				resultado.add(r);
			}
		}
		return resultado;
	}
	
	@Override
	public void notificarDevolucion(int idReserva, int idAdmin, int estado) throws MyDaoException {
		if(!isActiveUser(idAdmin)) throw new MyDaoException("No se encuentra activo para hacer esta solicitud", null);
		if(!matchRol(idAdmin, "administrador")) throw new MyDaoException("No tiene permisos para realizar la operacion", null);
		Reserva reservaNotificada = reservaDao.obtener(idReserva);
		if(reservaNotificada==null) throw new MyDaoException("No exista reserva con id " + idReserva, null);
		if(!isActiveUser(reservaNotificada.getId_cedula().getCedula())) throw new MyDaoException("Usuario investigador no se encuentra activo", null);
		
		if(estado<2||estado>6) throw new MyDaoException("El estado "+estado+" no es valido",null);
		
		Usuarios adminNotificador = usuariosDao.obtener(idAdmin);
		
		reservaNotificada.setEstado(estado);//nuevo estado de la reserva
		reservaNotificada.setFecha_entrega(new Date()); //fecha entrega
		reservaNotificada.setId_responsable(adminNotificador);//el nuevo responsable de la reserva
		
		reservaDao.modificar(reservaNotificada);
	}
	
	@Override
	public void hacerPrestamo(int idReserva) throws MyDaoException {
		
		if(idReserva==0) throw new MyDaoException("No se ingreso id de reserva",null);
		if(auth.obtener()==null) throw new MyDaoException("Se ha cerrado su sesion, vuelva a loguearse para continuar",null);
		if(matchRol(auth.obtener().getId(), "administrador")) throw new MyDaoException("No tiene permiso para realizar esta operacion",null);
		
		Date fechaActual = new Date();
		
		Reserva reserva = reservaDao.obtener(idReserva);
		if(reserva.getEstado()!=0) throw new MyDaoException("Operacion no permitida, "
				+ "estado de reserva " +reserva.getEstado(),null);
		//si reclama el dispositivo despues de la fecha_entrega
		if(reserva.getFecha_entrega().after(fechaActual)){
			reserva.setEstado(3);
			reservaDao.modificar(reserva);
			throw new MyDaoException("Sobrepaso la fecha de entrega, se cancela reserva",null);
		}
		
		reserva.setFecha_inicio(fechaActual); //actualiza fecha de inicio de reserva
		long horas = TimeUnit.HOURS.toMillis(reserva.getTiempo_reserva()); //convierte horas de tiempo de entrega en ms
		reserva.setFecha_entrega(new Date(horas+fechaActual.getTime())); //la fecha entrega es fecha de inicio mas horas de entrega
		reserva.setEstado(1);
		//el nuevo responsable del prestamo sera el administrador que lo realiza
		reserva.setId_responsable(usuariosDao.obtener(auth.obtener().getId()));
		
		reservaDao.modificar(reserva);
	}

	
	/**
	 * Revisa si el usuario con cedula idResponsable, es del rol rol
	 * @param id
	 * @param rol
	 * @return
	 * @throws MyDaoException
	 */
	public boolean matchRol(int id, String rol) throws MyDaoException {
		Usuarios userResponsable = usuariosDao.obtener(id);
		if (userResponsable.getRol().equals(rol)) {
			return true;
		}
		return false;
	}


	/**
	 * Verifica que usuario del id ingresado tiene estado activo
	 * @param id
	 * @return true si usuario tiene estado activo, false en caso contrario
	 * @throws MyDaoException
	 */
	public boolean isActiveUser(int id) throws MyDaoException {
		Usuarios userResponsable = usuariosDao.obtener(id);
		if (userResponsable.getEstado().equals("inactivo")) {
			return false;
		}
		return true;
	}
	
	
	@Override
	public List<Reserva> prestamosPorDispositivos(int nroSerie, int idResponsable)
			throws MyDaoException {
		if(!isActiveUser(idResponsable)) throw new MyDaoException("No se encuentra activo",null);
		if(!matchRol(idResponsable, "administrador")) throw new MyDaoException("No tiene permiso para ver este listado",null);
		List<Reserva> resultado = new ArrayList<Reserva>();
		List<Reserva> todas = reservaDao.obtener();
		if(todas==null||todas.size()==0) throw new MyDaoException("No existen prestamos  para el dispositivo con numero de serie  "
				+ nroSerie,null);
		
		Iterator<Reserva> i = todas.iterator();
		while(i.hasNext()){
			Reserva r = i.next();
			if((r.getId_dispositivo().getNumero_serie()== nroSerie) & (r.getEstado()==1 || r.getEstado()==2
					|| r.getEstado()== 4 || r.getEstado()==6 )){
				resultado.add(r);
			}
		}
		return resultado;
	}

	

	

}
