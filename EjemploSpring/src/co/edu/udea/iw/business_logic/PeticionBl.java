/**
 * 
 */
package co.edu.udea.iw.business_logic;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import co.edu.udea.iw.dto.PeticionAcceso;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * Metodos que garantizan la logica del negocio en la 
 * interaccion con las peticiones
 * @author Federico Ocampo. cc: 1039464102. federico.ocampoo@udea.edu.co
 * 
 */
public interface PeticionBl {
	
	
	/**
	 * FRQ-0013 - Listar peticiones de acceso
	 * permite al usuario administrador listar todas las peticiones de acceso
	 * registradas
	 * @return Lista con peticiones de acceso registradas
	 * @throws MyDaoException
	 */
	public List<PeticionAcceso> verPeticionesDeAcceso() throws MyDaoException;
	
	/**
	 * FRQ-0008 - Peticion de Acceso
	 * Permite a una persona crear una peticion para acceder al sistema como
	 * investigador
	 * @param peticion
	 * @return true si la creación fue correcta
	 * @throws MyDaoException
	 */
	
	/**
	 * FRQ-0008 - Peticion de Acceso
	 * Permite a una persona crear una peticion para acceder al sistema como
	 * investigador
	 * @param cedula
	 * @param usuario
	 * @param nombre
	 * @param apellido
	 * @param contraseña
	 * @param email
	 * @param fotoRaw
	 * @param telefono
	 * @param direccion
	 * @throws MyDaoException
	 */
	public void crearPeticionDeAcceso(int cedula, String usuario, String nombre, String apellido, 
			String contrasena, String email, byte[] fotoRaw, String telefono, String direccion) throws MyDaoException, SerialException, SQLException;
	
	/**
	 * FRQ-0014 - Evaluar peticiones de acceso
	 * Permite a un usuario administrador evaluar una peticion de acceso dada por idPeticion
	 * y agregar su decision y justificacion
	 * @param idPeticion - id peticion a evaluar
	 * @param estado - resultado de la peticion (aprobado, rechazado)
	 * @param admin - responsable de la evaluacion
	 * @param justificacion - justificacion de decision
	 * @throws MyDaoException
	 */
	public void evaluarPeticionDeAcceso(int idPeticion, String estado, int admin, String justificacion) throws MyDaoException;

}
