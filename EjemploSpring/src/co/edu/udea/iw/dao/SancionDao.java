package co.edu.udea.iw.dao;

import java.util.List;

import co.edu.udea.iw.dto.Sancion;
import co.edu.udea.iw.exception.MyDaoException;

/*
 * Definicion de metodos para acceder a la capa de acceso a datos de 
 * la entidad Reserva
 */

public interface SancionDao {
	
	/**
	 * Este metodo se encargará de retornar todos los registros
	 * existentes en la entidad Sancion
	 * @return List<Sancion>
	 * @throws MyDaoException
	 */
	public List<Sancion> obtener() throws MyDaoException;
	
	/**
	 * La funcion de este metodo será encontrar una sancion
	 * en especifico dado un identificador unico de sancion(id_sancion)
	 * @param id
	 * @return
	 * @throws MyDaoException
	 */
	public Sancion obtener(int id) throws MyDaoException;
	
	/**
	 * Este metodo tiene como objetivo eliminar una sancion de la
	 * nuestra base de datos
	 * @param id
	 * @throws MyDaoException
	 */
	public void eliminar(int id) throws MyDaoException;
	
	
	/**
	 * Este metodo tiene como objetivo adicionar una nueva sancion
	 * a nuestra base de datos
	 * @param sancion
	 * @throws MyDaoException
	 */
	public void guardar(Sancion sancion) throws MyDaoException;
	
	/**
	 * Este metodo tiene como objetivo actualizar o modificar la una
	 * sancion
	 * @param sancion
	 * @throws MyDaoException
	 */
	public void modificar(Sancion sancion) throws MyDaoException;

}
