package co.edu.udea.iw.dao;

import java.util.List;

import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * 
 * Define los metodos para acceder a la tabla Usuarios en la base de datos
 * 
 * @author Federico
 *
 */
public interface UsuariosDao {

	/**
	 * Entrega una lista con los usuarios alamacenados en la bd
	 * 
	 * @return lista de usuarios
	 * @throws MyDaoException
	 */
	public List<Usuarios> obtener() throws MyDaoException;

	/**
	 * Entrega un usuario asociado a la cedula ingresada
	 * 
	 * @param cedula
	 * @return usuario
	 * @throws MyDaoException
	 */
	public Usuarios obtener(int cedula) throws MyDaoException;

	/**
	 * Guarda un usuario en la base de datos 
	 * @param usuario usuario a guardar
	 * @throws MyDaoException
	 */
	public void guardar(Usuarios usuario) throws MyDaoException;

	/**
	 * Modifica un usuario
	 * @param usuario usuario que contiene los nuevos valores
	 * @throws MyDaoException
	 */
	public void modificar(Usuarios usuario) throws MyDaoException;

	/**
	 * Elimina un usuario de la base de datos
	 * @param cedula, cedula del usuario a eliminar
	 * @throws MyDaoException
	 */
	public void eliminar(int cedula) throws MyDaoException;

}
