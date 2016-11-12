package co.edu.udea.iw.business_logic;

import java.util.Date;
import java.util.List;

import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.exception.MyDaoException;

/*
 * En esta clase se presentan los metodos pertinentes para la capa
 * de negocio de nuestra entidad Reserva
 * Para esta entidad usaremos una notacion para el campo
 * estado, será la siguiente
 * estado = 0--->estado de reserva
 * estado = 1--->prestamos activo
 * estado = 2--->prestamo terminado
 * estado = 3--->reserva cancelada.
 * estado = 4--->prestamos terminado, se retorno el instrumento de forma inadecuada.
 * estado = 5--->cancelo la reserva inoportunamente.
 * estado = 6--->Extravio el objeto.
 */
public interface ReservaBl {
	
	/**
	 * metodo para FRQ-0001 - Realizar prestamo de un Dispositivo
	 * Este metodo permite a un administrador hacer un prestamo a un investigador.
	 * Esto se realiza cambiando el estado de la reserva a 1 (prestamo activo), debe 
	 * actualizarse la fecha de inicio, y la fecha de entrega, tambien cambia el admin responsable
	 * @param idReserva id de la reserva que se hara prestamo
	 * @throws MyDaoException
	 */
	public void hacerPrestamo(int idReserva) throws MyDaoException;
	
	/**
	 * Este metodo permitirá cancelar una reserva brindandole
	 * un identificar unico y la fecha en la que se realiza,
	 * recuerde que la eliminacion de las reservas será logica
	 * @param id, identificador de la reserva a cancelar
	 * @param fecha, fecha en la cual se cancelara la reserva
	 * @throws MyDaoException
	 */
	public void cancelarReserva(int id,Date fecha) throws MyDaoException;
	
	
	/**
	 * Este metodo permite al usuario modificar una
	 * reserva, usuario podrá moficar los campos tiempo de reserva
	 * @param id, identificador de la reserva a modificar
	 * @param Tiempo, tiempo por el cual tendrá prestado el instrumento
	 * @throws MyDaoException
	 */
	public void modificarReserva(int id,int Tiempo) throws MyDaoException;
	
	
	/**
	 * Este metodo será el que cancela un prestamo activo, una vez sea retornado
	 * para este metodo deberemos tener el numero de la reserva y estado en que se genera
	 * los estados pueden ser:
	 * adecuado--->El instrumento esta en perfectas condiciones
	 * inadecuado->El instrumento se retonor en condiciones no optimas
	 * extravio--->El usuario extravio el instrumento.
	 * @param id_reserva, identificador de una reserva en especial
	 * @param estado, estado de retonro del instrumento, adecuado, inadecuado o extravio
	 * @throws MyDaoException
	 */
	public void cancelarPrestamo(int id_reserva,String estado) throws MyDaoException;
	
	
	/**
	 * FRQ-0030 - Ver historial de prestamos por investigador
	 * Este metodo permite a un usuario tipo Administrador, ver un listado de las reservas
	 * asociadas a un determinado investigador.
	 * @param idResponsable - Administrador que llama el metodo
	 * @param idInvest - Investigador al cual se le va a hacer la consulta
	 * @return Lista de dispositivos asociados a investigador.
	 * @throws MyDaoException
	 */
	public List<Reserva> verReservasPorInvest(int idInvest, int idResponsable) throws MyDaoException;
	
	/**
	 * FRQ-0031 - Notificar Devolucion
	 * Este metodo permite a un usuario administrar notificar o registrar la devolucion
	 * de un dispositivo por parte de un investigar. El administrador debe definir  el estado 
	 * en que se recibio el dispositivo. 
	 * @param idReserva - reserva a ser notificada
	 * @param idAdmin - administrador responsable
	 * @param idInvest - investigador que hace la devolucion 
	 * @param estado - estado de la reserva
	 * @throws MyDaoException
	 */
	public void notificarDevolucion(int idReserva, int idAdmin, int estado) throws MyDaoException; 
	
}
