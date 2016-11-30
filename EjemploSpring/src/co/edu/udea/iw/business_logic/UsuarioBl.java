package co.edu.udea.iw.business_logic;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * Metodo que garantiza la logica del negocio en
 * la interaccion con el usuario
 * roles que puede tomar un usuario administrador, superusuario, investigador
 * @author estudiantelis
 */

public interface UsuarioBl {

	
	/**
	 * FRQ-006- Registro de Administrador
	 * 
	 * @param cedulaResponsable cedula de quien va a registrar administrador Obligatorio, obtenido por el sistema
	 * @param cedula Obligatorio
	 * @param nombreUsuario Obligatorio
	 * @param contrasena Obligatorio
	 * @param nombre Obligatorio
	 * @param apellido Obligatorio
	 * @param correo Obligatorio
	 * @param fotoRAW 
	 * @param telefono Obligatorio
	 * @param direccion Obligatorio
	 * @throws MyDaoException
	 * @throws SQLException 
	 * @throws SerialException 
	 */	
	public void registrarAdministrador(int cedulaResponsable, int cedula, String nombre, String apellido, 
			String correo, String nombreUsuario, String contrasena, byte[] fotoRAW, String telefono, String direccion) throws MyDaoException, SerialException, SQLException;
	
	/**
	 * FRQ-007- Eliminar  Administrador
	 * 
	 * @param idUsuario - id de lo que se va a eliminar
	 * @param justificacion 
	 * @param idResponsable - id de quien hara eliminacion
	 * @throws MyDaoException
	 */	
	public void eliminarAdministrador(int idResponsable, int idUsuario, String justificacion) throws MyDaoException;
	
	/**
	 * FRQ-015- Actualizaci��n de Informaci��n de usuario
	 * 
	 * @param idUsuario
	 * @param idResponsable
	 * @param nuevacontrasena
	 * @param nuevoCorreo
	 * @param nuevaFotoRAW
	 * @param nuevoTelefono
	 * @param nuevaDireccion
	 * @throws MyDaoException
	 */	
	public void actualizarInformacion(int idResponsable, int idUsuario, String nuevaContrasena, 
			String nuevoCorreo, byte[] nuevaFotoRAW, String nuevoTelefono, String nuevaDireccion) throws MyDaoException;
	
	/**
	 * FRQ-007- Darse de baja logicamente * 
	 * @param idUsuario
	 * @throws MyDaoException	
	 */	
	public void darseDeBajaLogicamenteInvestigador(int idUsuario) throws MyDaoException;
		
	/**
	 * FRQ-024- Eliminar investigador * 
	 * @param idUsuario -- id de quien sera eliminado logicamente
	 * @param idResponsable -- id de quien hara la eliminacion logica
	 * @param justificacion
	 * @throws MyDaoException	
	 */	
	public void eliminarInvestigador(int idResponsable, int idUsuario, String justificacion) throws MyDaoException;
	
	/**
	 * FRQ-026- Listar investigadores
	 * @param idUsuario
	 * @return list<Usuarios>
	 * @throws MyDaoException	
	 */	
	public List<Usuarios> listarInvestigadores(int idResponsable) throws MyDaoException;
	
	/**
	 * FRQ-0027- Iniciar sesion
	 * @param cedula 
	 * @param contrasena
	 * @param captcha - para evitar robots
	 * @return  true si el login es exitoso, false de lo contrario.
	 * @throws MyDaoException	
	 */	
	public boolean login(int cedula, String contrasena, String captcha) throws MyDaoException;
	
	/**
	 * FRQ-028- Cerrar sesi��n
	 * @param cedula - cedula de quien va a cerrar sesion.
	 * @throws MyDaoException	
	 */	
	public void cerrarSesion(int cedula) throws MyDaoException;
	
	/**
	 * El siguiente metodo tendrá la funcion de bloquear o desbloquear un 
	 * usuario
	 * @param id_user, id del usuario a desbloquar o bloquear
	 * @param action será una entero si es 0--> indica bloqueado, si es 1--->indica desbloqueado
	 * @param id_administrador será la identificacion de quien bloquea o desbloquea
	 * @throws MyDao Exception
	 */
	public void bloquear(int id_user,int id_administrador, int action) throws MyDaoException;
}
