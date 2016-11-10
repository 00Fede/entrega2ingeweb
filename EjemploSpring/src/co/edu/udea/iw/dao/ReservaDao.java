package co.edu.udea.iw.dao;

import java.util.List;


import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.exception.MyDaoException;


/*
 * Definicion de metodos para acceder a la capa de acceso a datos de 
 * la entidad Reserva
 */
public interface ReservaDao {
	
	/**
	 * Este metodo se encargará de retornar todos los registros
	 * existentes en la entidad reserva
	 * @return List<Reserva>
	 * @throws MyDaoException
	 */
	public List<Reserva> obtener() throws MyDaoException;
	
	/**
	 * Este metodo se encargará de buscar una reserva en especifico,
	 * se podrá filtar por medio de el identificador unico de la reserva(id_reserva).
	 * @param id
	 * @return Reserva
	 * @throws MyDaoException
	 */
	public Reserva obtener(int id) throws MyDaoException;
	
	/**
	 * Este metodo se encargará de guardar una nueva reserva en
	 * nuestra base de datos
	 * @param reserva
	 * @throws MyDaoException
	 */
	public void guardar(Reserva reserva) throws MyDaoException;
	
	/**
	 * Este metodo se encargará de eliminar una reserva en especifico 
	 * de nuestra base de datos, para saber que reserva eliminar debere-
	 * mos pasar un idetificador unico de reserva (id_reserva)
	 * @param id
	 * @throws MyDaoException
	 */
	public void eliminar(int id) throws MyDaoException;
	
	/**
	 * Este metodo se encargará de la modificacion de una reserva.
	 * @param reserva
	 * @throws MyDaoException
	 */
	public void modificar(Reserva reserva) throws MyDaoException;

}
