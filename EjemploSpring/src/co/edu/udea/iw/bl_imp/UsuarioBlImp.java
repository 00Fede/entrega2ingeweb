package co.edu.udea.iw.bl_imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import com.mysql.jdbc.UpdatableResultSet;

import co.edu.udea.iw.business_logic.UsuarioBl;
import co.edu.udea.iw.dao.AuthDao;
import co.edu.udea.iw.dao.ReservaDao;
import co.edu.udea.iw.dao.SancionDao;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Autenticacion;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Sancion;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.util.encode.Cifrar;
import co.edu.udea.iw.util.validations.Validaciones;

/**
 * @see UsuarioBlImp
 * @author Federico Ocampo. cc: 1039464102. federico.ocampoo@udea.edu.co
 *
 */
public class UsuarioBlImp implements UsuarioBl {

	private static final String CAPTCHA_CODE = "n3ur0";
	UsuariosDao userDao;
	ReservaDao reservaDao;
	SancionDao sancionDao;
	AuthDao authDao;

	/**
	 * constructor para la inyeccion
	 * 
	 * @param userDao
	 * @param reservaDao
	 * @param sancionDao
	 */
	public UsuarioBlImp(UsuariosDao userDao, ReservaDao reservaDao, SancionDao sancionDao, AuthDao authDao) {
		this.userDao = userDao;
		this.reservaDao = reservaDao;
		this.sancionDao = sancionDao;
		this.authDao = authDao;
	}

	@Override
	public void registrarAdministrador(int cedulaResponsable, int cedula, String nombre, String apellido, String correo,
			String nombreUsuario, String contrasena, byte[] fotoRAW, String telefono, String direccion)
			throws MyDaoException, SerialException, SQLException {
		if (!isActiveUser(cedulaResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacci��n", null);
		}
		if (!matchRol(cedulaResponsable, "superusuario")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		if (cedula == 0) {
			throw new MyDaoException("Debe especificar cedula.", null);
		}
		if (nombreUsuario == null || "".equals(nombreUsuario.trim())) {
			throw new MyDaoException("Debe especificar nombre de usuario", null);
		}
		if (contrasena == null || "".equals(contrasena.trim())) {
			throw new MyDaoException("Debe especificar contrase��a", null);
		}
		if (correo == null || "".equals(correo.trim())) {
			throw new MyDaoException("Debe especificar correo electronico", null);
		}
		if (telefono == null || "".equals(telefono.trim())) {
			throw new MyDaoException("Debe especificar telefono", null);
		}
		if (direccion == null || "".equals(direccion.trim())) {
			throw new MyDaoException("Debe especificar direccion", null);
		}
		if (!Validaciones.isEmail(correo)) {
			throw new MyDaoException("El correo electronico no es valido", null);
		}
		if (contrasena.length() < 6) {
			throw new MyDaoException("La contrase��a debe contener almenos 6 caracteres", null);
		}
		if (userDao.obtener(cedula) != null) {
			throw new MyDaoException("El usuario ya existe.", null);
		}

		List<Usuarios> users = userDao.obtener();
		Iterator<Usuarios> i = users.iterator();
		while (i.hasNext()) {
			if (i.next().getUsuario().equals(nombreUsuario)) {
				throw new MyDaoException("El nombre de usuario ya existe", null);
			}
		}
		if (existeEmail(correo)) {
			throw new MyDaoException("El correo electronico ingresado ya existe", null);
		}

		Usuarios user = new Usuarios();
		user.setCedula(cedula);
		user.setNombre(nombre);
		Cifrar c = new Cifrar();
		user.setContrasena(c.encrypt(contrasena));
		user.setUsuario(nombreUsuario);
		user.setApellido(apellido);
		user.setDireccion(direccion);
		user.setEstado("activo");
		user.setRol("administrador");
		user.setTelefono(telefono);
		user.setFoto(new SerialBlob(fotoRAW));
		user.setEmail(correo);

		userDao.guardar(user);
	}

	@Override
	public void eliminarAdministrador(int idResponsable, int idUsuario, String justificacion) throws MyDaoException {
		Usuarios userResponsable = userDao.obtener(idResponsable);
		if (!isActiveUser(idResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacci��n", null);
		}
		if (!matchRol(idResponsable, "superusuario")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		if (!userResponsable.getRol().equals("superusuario")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		Usuarios olduser = userDao.obtener(idUsuario);
		if (!olduser.getRol().equals("administrador")) {
			throw new MyDaoException("El usuario a eliminar no es administrador", null);
		}
		if (justificacion.equals(null) || "".equals(justificacion.trim())) {
			throw new MyDaoException("Debe ingresar una justificacion", null);
		}

		olduser.setEstado("inactivo"); // eliminado logico
		userDao.modificar(olduser);
	}

	@Override
	public void actualizarInformacion(int idResponsable, int idUsuario, String nuevaContrasena, String nuevoCorreo,
			byte[] nuevaFotoRAW, String nuevoTelefono, String nuevaDireccion) throws MyDaoException {
		if (!isActiveUser(idResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacci��n", null);
		}
		if (idResponsable != idUsuario) {
			throw new MyDaoException("No puede modificar la informaci��n de otro usuario", null);
		}
		if (!Validaciones.isEmail(nuevoCorreo)) {
			throw new MyDaoException("El correo electronico no es valido", null);
		}
		if (existeEmail(nuevoCorreo)) {
			throw new MyDaoException("El correo electronico ingresado ya existe", null);
		}

		Usuarios updatedUser = userDao.obtener(idUsuario);
		if (nuevaContrasena != null) {
			System.out.println("contraseña actualizada: " + nuevaContrasena);
			if (nuevaContrasena.length() < 6 || "".equals(nuevaContrasena.trim())) {
				throw new MyDaoException("La contrase��a debe contener almenos 6 caracteres", null);
			}
			Cifrar c = new Cifrar();
			updatedUser.setContrasena(c.encrypt(nuevaContrasena));
		}
		if (nuevoCorreo != null) {
			if (!"".equals(nuevoCorreo.trim()))
				updatedUser.setEmail(nuevoCorreo);
		}
		if (nuevoTelefono != null) {
			if (!"".equals(nuevoTelefono.trim()))
				updatedUser.setTelefono(nuevoTelefono);
		}
		if (nuevaDireccion != null) {
			if (!"".equals(nuevaDireccion.trim()))
				updatedUser.setDireccion(nuevaDireccion);
		}

		userDao.modificar(updatedUser);

	}

	/**
	 * Verifica si el email ingresado ya existe en la bd
	 * 
	 * @param email
	 * @return true si usuario ya existe en bd, false en caso contrario
	 * @throws MyDaoException
	 */
	private boolean existeEmail(String email) throws MyDaoException {
		List<Usuarios> users = userDao.obtener();
		Iterator<Usuarios> i = users.iterator();
		while (i.hasNext()) {
			if (i.next().getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica que usuario del id ingresado tiene estado activo
	 * 
	 * @param id
	 * @return true si usuario tiene estado activo, false en caso contrario
	 * @throws MyDaoException
	 */
	private boolean isActiveUser(int id) throws MyDaoException {
		Usuarios userResponsable = userDao.obtener(id);
		if (userResponsable.getEstado().equals("inactivo")) {
			return false;
		}
		return true;
	}

	/**
	 * Revisa si el usuario del id tiene reservas asociadas
	 * 
	 * @param id
	 * @return true si tiene reservas asociadas, false en caso contrario
	 * @throws MyDaoException
	 */
	private boolean hasActiveReserves(int id) throws MyDaoException {
		List<Reserva> r = reservaDao.obtener();
		Iterator<Reserva> i = r.iterator();
		while (i.hasNext()) {
			if (i.next().getId_cedula().getCedula() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Revisa si el usuario del id tiene sanciones asociadas
	 * 
	 * @param id
	 * @return true si tiene sanciones asociadas, false en caso contrario
	 * @throws MyDaoException
	 */
	private boolean hasActiveSanctions(int id) throws MyDaoException {
		List<Sancion> s = sancionDao.obtener();
		Iterator<Sancion> i = s.iterator();
		while (i.hasNext()) {
			if (i.next().getId_cedula().getCedula() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Revisa si el usuario con cedula idResponsable, es del rol rol
	 * 
	 * @param id
	 * @param rol
	 * @return
	 * @throws MyDaoException
	 */
	private boolean matchRol(int id, String rol) throws MyDaoException {
		Usuarios userResponsable = userDao.obtener(id);
		if (userResponsable.getRol().equals(rol)) {
			return true;
		}
		return false;
	}

	/**
	 * Revisa si existe por lo menos un investigador en el sistema
	 * 
	 * @return true si existe por lo menos un investigador, false en caso
	 *         contrario.
	 * @throws MyDaoException
	 */
	private boolean existInvest() throws MyDaoException {
		List<Usuarios> u = userDao.obtener();
		Iterator<Usuarios> i = u.iterator();
		while (i.hasNext()) {
			if (i.next().getRol().equals("investigador")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Revisa si el usuario con cedula ingresada existe en el sistema
	 * 
	 * @param cedula
	 *            - cedula del usuario
	 * @return true si existe, false en caso contrario
	 * @throws MyDaoException
	 */
	private boolean userExist(int cedula) throws MyDaoException {
		return (userDao.obtener(cedula) != null);
	}

	@Override
	public void darseDeBajaLogicamenteInvestigador(int idUsuario) throws MyDaoException {
		if (idUsuario == 0) {
			throw new MyDaoException("Debe especificar cedula.", null);
		}
		if (!userExist(idUsuario)) {
			throw new MyDaoException("El usuario no existe", null);
		}
		if (!isActiveUser(idUsuario)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacci��n", null);
		}

		if (!matchRol(idUsuario, "investigador")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		if (hasActiveReserves(idUsuario)) {
			throw new MyDaoException("El usuario tiene reservas activas", null);
		}
		if (hasActiveSanctions(idUsuario)) {
			throw new MyDaoException("El usuario tiene sanciones activas", null);
		}

		Usuarios delInv = userDao.obtener(idUsuario);
		delInv.setEstado("inactivo");
		userDao.modificar(delInv);

	}

	@Override
	public void eliminarInvestigador(int idResponsable, int idUsuario, String justificacion) throws MyDaoException {
		if (idResponsable == 0) {
			throw new MyDaoException("Debe especificar cedula de responsable.", null);
		}
		if (!userExist(idResponsable)) {
			throw new MyDaoException("El usuario responsable no existe", null);
		}
		if (!isActiveUser(idResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacci��n", null);
		}
		if (idUsuario == 0) {
			throw new MyDaoException("Debe especificar cedula de usuario.", null);
		}
		if (!userExist(idUsuario)) {
			throw new MyDaoException("El usuario a eliminar no existe", null);
		}
		if (!isActiveUser(idUsuario)) {
			throw new MyDaoException("El usuario ya fue eliminado", null);
		}
		if (!matchRol(idResponsable, "administrador")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		if (!matchRol(idUsuario, "investigador")) {
			throw new MyDaoException("No puede eliminar este tipo de usuario", null);
		}
		if (hasActiveReserves(idUsuario)) {
			throw new MyDaoException("El usuario tiene reservas activas", null);
		}
		if (hasActiveSanctions(idUsuario)) {
			throw new MyDaoException("El usuario tiene sanciones activas", null);
		}
		if (justificacion.equals(null) || "".equals(justificacion.trim())) {
			throw new MyDaoException("Debe ingresar una justificacion", null);
		}

		Usuarios delInv = userDao.obtener(idUsuario);
		delInv.setEstado("inactivo");
		userDao.modificar(delInv);
	}

	@Override
	public List<Usuarios> listarInvestigadores(int idResponsable) throws MyDaoException {
		if (!isActiveUser(idResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacciones", null);
		}
		if (!matchRol(idResponsable, "administrador")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		if (!existInvest()) {
			throw new MyDaoException("No existen investigadores", null);
		}
		List<Usuarios> userList = userDao.obtener();
		List<Usuarios> invList = new ArrayList<>();
		Iterator<Usuarios> i = userList.iterator();
		while (i.hasNext()) {
			Usuarios u = i.next();
			if (u.getRol().equals("investigador")) {
				invList.add(u);
			}
		}
		return invList;
	}

	@Override
	public boolean login(int cedula, String contrasena, String captcha) throws MyDaoException {
		if (cedula == 0) {
			throw new MyDaoException("Debe especificar cedula.", null);
		}
		if (contrasena == null || "".equals(contrasena.trim())) {
			throw new MyDaoException("Debe especificar contraseña", null);
		}
		if (contrasena.length()<6) {
			throw new MyDaoException("La contraseña debe tener mas de 6 caracteres", null);
		}
		if (!userExist(cedula)) {
			throw new MyDaoException("El usuario no existe", null);
		}
		if (!isActiveUser(cedula)) {
			throw new MyDaoException("El usuario no se encuentra activo.", null);
		}
		if (captcha == null || "".equals(captcha)) {
			throw new MyDaoException("Debe ingresar el texto del captcha", null);
		}
		if (!captcha.equals(CAPTCHA_CODE)) {
			throw new MyDaoException("Debe ingresar correctamente el captcha", null);
		}
		Usuarios user = userDao.obtener(cedula);
		Cifrar c = new Cifrar();
		String encryptedPass = c.encrypt(contrasena);	
		if (!user.getContrasena().equals(encryptedPass)) {
			throw new MyDaoException("Contraseña Incorrecta.", null);
			
		}
		if (authDao.obtener() != null) {
			throw new MyDaoException("Ya existe una sesion abierta, porfavor cierrela para continuar", null);
		}
		
		Autenticacion auth = new Autenticacion();
		auth.setId(cedula);
		auth.setFecha_auth(new Date());
		authDao.guardar(auth);
		return true;
		
	}

	@Override
	public void cerrarSesion(int cedula) throws MyDaoException {
		if (cedula == 0) {
			throw new MyDaoException("Debe especificar cedula.", null);
		}
		if (!userExist(cedula)) {
			throw new MyDaoException("El usuario no existe", null);
		}
		if (!isActiveUser(cedula)) {
			throw new MyDaoException("El usuario no se encuentra activo.", null);
		}
		if(authDao.obtener()==null) throw new MyDaoException("No hay sesiones abiertas, puede continuar",null);
		if (authDao.obtener().getId() != cedula) {
			throw new MyDaoException("Su sesion no esta abierta", null);
		}
		authDao.eliminar(authDao.obtener()); // elimina la unica instancia de
												// autenticacion que debe haber
	}

	@Override
	public void bloquear(int id_user, int id_administrador, int action) throws MyDaoException {
		if (id_user == 0) {
			throw new MyDaoException("El identificador del usuario es invalido", null);
		}
		if (id_administrador == 0) {
			throw new MyDaoException("El identificador del administrador es invalido", null);
		}
		if (action > 2) {
			throw new MyDaoException("El parametro es 'action' inavlido ", null);
		}

		Usuarios user;
		user = userDao.obtener(id_user);

		if (user == null) {
			throw new MyDaoException("No existe ninguna usuario con ese identificador", null);
		}

		Usuarios admin;
		admin = userDao.obtener(id_administrador);

		if (admin == null) {
			throw new MyDaoException("No existe ningun administrador con ese identificador", null);
		}

		if (!admin.getRol().equals("administrador")) {
			throw new MyDaoException("El identificador ingreasdo no pertenece a un administrador", null);
		}
		if (action == 0) {
			user.setEstado("Bloqueado");
		}
		if (action == 1) {
			user.setEstado("activo");
		}
		userDao.modificar(user);

	}

}
