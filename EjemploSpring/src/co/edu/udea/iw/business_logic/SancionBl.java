package co.edu.udea.iw.business_logic;

import java.util.Date;

import co.edu.udea.iw.exception.MyDaoException;

/*
 * En esta clase se presentará la firma de todos los metodos
 * correspondientes a la logica del negocio de nuestra entidad sancion
 */

public interface SancionBl {

	
	/**
	 * Este metodo generará una sancion cuando el usuario sobrepase el 
	 * tiempo limite de prestamo, recuerden que la reserva tendra estado en 1 cuando aun este en prestamos
	 * por ende para cumplir este metodo la reserva indicada debera tener estado == 1
	 * @param id_reserva, identificador de una reserva en especial
	 * @param fecha, fecha en la cual se realiza el tramite
	 * @param id_sancion, identificador de una sancion en especial
	 * @throws MyDaoException
	 */
	public void generarSancion_limiteTiempo(int id_sancion,int id_reserva,Date fecha) throws MyDaoException;

	/**
	 * Este metodo generará una sancion cuando el usuario 
	 * retorne el dispositivo en un estado inadecuado, receruden el estado será no adecuado cuando
	 * la reserva este en estado = 4
	 * @param id_reserva, identificador de una reserva en especial
	 * @param id_sancion, identificador de una sancion en especial
	 * @throws MyDaoException
	 */
	public void generarSancion_estadoNoAdecuado(int id_sancion,int id_reserva) throws MyDaoException;
	
	/**
	 * Este metodo generará una sancion cuando el 
	 * usuario haya hecho una reserva y no haya cumplido
	 * con esta(reservo y no uso), es decir nunca llego a realizar el prestamos
	 * @param id_reserva, identificador de una reserva en especial
	 * @param fecha, fecha en la cual se realiza la validacion de el incumplimiento
	 * @throws MyDaoException
	 */
	public void generarSancion_incumplimientoReserva(int id_sancion,int id_reserva,Date fecha) throws MyDaoException;
	
	/**
	 * Este metodo generará una sancion cuando
	 * el usaurio generá una cancelacion de una reserva
	 * en un tiempo muy cercado a la fecha de la reserva
	 * ,recuerde que cuando el estado de una reserva sea = 5
	 * @param id_reserva, identificador de la reserva a cancelar
	 * @param fecha, fecha en la cual cancelará la reserva
	 * @throws MyDaoException
	 */
	public void generarSancion_cancelacionInoportuna(int id_sancion,int id_reserva) throws MyDaoException;
	
	/**
	 * Este metodo generará una sancion cuando
	 * el usuario haya extraviado un dispositivo
	 * prestado
	 * @param id_reserva, identificaodr de una reserva en especial
	 * @param id_sancion, identificador de una sancion en especial
	 * @throws MyDaoException
	 */
	public void generarSancion_extravio(int id_sancion,int id_reserva) throws MyDaoException;
	
	
	/**
	 * Este metodo permitir� modificar el tiempo
	 * de la sancion debido a una razon que debe ser 
	 * especificada.
	 * @param id_sancion, identificador de la sancion a modificar
	 * @param id_administrador, identificador de un id de administrador
	 * @param razon, razon por la cual disminuirá el tiempo de sancion, puede ser multa o retorno
	 * @throws MyDaoException
	 */
	public void modificarSancion(int id_sancion,int id_administrador, String razon) throws MyDaoException;
	
	/**
	 * Este metodo permitirá remover las 
	 * sanciones previamentes creadas, ya sea por retorno
	 * de dispositivo extraviado,o pago de multa.
	 * @param id_sancion, identificador de una sancion en especial
	 * @param id_administrador, identificador de el administrador
	 * @throws MyDaoException
	 */
	public void retirarSancion(int id_sancion,int id_administrador) throws MyDaoException;
	
	/**
	 * Este metodo permitir� eliminar las sanciones que ya
	 * hayan terminado su tiempo de multa
	 * @param id_sancion, identificador de una sancion en especial
	 * @param fecha, fecha en la cual se hará la validacion de este metodo
	 * @throw MyDaoException
	 */
	public void retirarSancion_tiempoCulminado(int id_sancion, Date fecha) throws MyDaoException;
}
