/**
 * 
 */
package co.edu.udea.iw.dao;

import java.util.List;

import co.edu.udea.iw.dto.PeticionAcceso;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * @author Federico
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
	 * Evalua peticion con id idPeticion, con el estado estado, agregando el responsable admini
	 * y la justificacion de la decision
	 * @param idPeticion - id de peticion a ser aprobada
	 * @param admin - administrador responsable
	 * @param estado - estado de la evaluacion (aprobado, rechazado)
	 * @param justificacion - justificacion de decision
	 * @return true si la transaccion es correcta, falso de lo contrario
	 * @throws MyDaoException
	 */
	public boolean evaluar(int idPeticion, String estado, Usuarios admin, String justificacion) throws MyDaoException;
	
	/**
	 * Crea una nueva peticion
	 * @param peticion - peticion realizada
	 * @return true si la transaccion es correcta, falso de lo contrario
	 * @throws MyDaoException
	 */
	public boolean crear(PeticionAcceso peticion) throws MyDaoException;
	
	
}
