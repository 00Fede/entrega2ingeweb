package co.edu.udea.iw.bl_imp;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.business_logic.ReservaBl;
import co.edu.udea.iw.dao.ReservaDao;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.exception.MyDaoException;

/*
 * see ReservaBl
 * En esta clase realizamores la implementacion de la logica del negocio 
 * de nuestra entidad Reserva
 * @author Andres Montoya
 */
@Transactional
public class ReservaBlImp implements ReservaBl {

	ReservaDao reservaDao;
	
	/**
	 * Constructor de la implementacion. Necesario para inyeccion Spring
	 * @param reservaDao
	 */
	public  ReservaBlImp(ReservaDao reservaDao) {
		this.reservaDao = reservaDao;
		
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

}
