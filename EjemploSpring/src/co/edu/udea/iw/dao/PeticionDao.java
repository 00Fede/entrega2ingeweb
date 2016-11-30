/**
 * 
 */
package co.edu.udea.iw.dao;

import java.util.List;

import co.edu.udea.iw.dto.PeticionAcceso;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * @author Federico Ocampo. cc: 1039464102. federico.ocampoo@udea.edu.co
 *Define los metodos necesarios para acceder a las peticiones de acceso
 */
public interface PeticionDao {
	
	/**
	 * Obtiene todas las peticiones de acceso
	 * @return Lista con las peticiones de acceso
	 * @throws MyDaoException
	 */
	public List<PeticionAcceso> obtener() throws MyDaoException;
	
	/**
	 * Obtiene peticion de acceso con id
	 * @param id
	 * @return
	 * @throws MyDaoException
	 */
	public PeticionAcceso obtener(int id) throws MyDaoException;
	
	/**
	 * Modifica un registro de peticion en la base de datos
	 * @param peticion - peticion a modificar
	 * @return true si la transaccion es correcta, falso de lo contrario
	 * @throws MyDaoException
	 */
	public boolean modificar(PeticionAcceso peticion) throws MyDaoException;
	
	/**
	 * Crea una nueva peticion
	 * @param peticion - peticion realizada
	 * @return true si la transaccion es correcta, falso de lo contrario
	 * @throws MyDaoException
	 */
	public boolean crear(PeticionAcceso peticion) throws MyDaoException;
	
	
}
