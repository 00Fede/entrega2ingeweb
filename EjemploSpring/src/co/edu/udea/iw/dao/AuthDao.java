package co.edu.udea.iw.dao;

import co.edu.udea.iw.dto.Autenticacion;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * Define los metodos para acceder a Autenticacion en bd
 * @author Federico
 *
 */
public interface AuthDao {
	/**
	 * Obtiene el elemento en Autenticacion que identifica al usuario como logueado
	 * @throws MyDaoException
	 */
	public Autenticacion obtener() throws MyDaoException;
	
	/**
	 * Ingresa el elemento de autenticacion que identifica al usuario como logueado
	 * @param auth - Autenticacion para guardar
	 * @throws MyDaoException
	 */
	public void guardar(Autenticacion auth) throws MyDaoException;
	
	/**
	 * Elimina el elemento de autenticacion que identifica al usuario como logueado
	 * @param auth - Autenticacion para eliminar 
	 * @throws MyDaoException
	 */
	public void eliminar(Autenticacion auth) throws MyDaoException;
}
