/**
 * 
 */
package co.edu.udea.iw.bl_imp;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import co.edu.udea.iw.business_logic.PeticionBl;
import co.edu.udea.iw.dao.AuthDao;
import co.edu.udea.iw.dao.PeticionDao;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.PeticionAcceso;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.util.encode.Cifrar;
import co.edu.udea.iw.util.validations.Validaciones;

/**
 * @author Federico
 *	@see co.edu.udea.iw.business_logic.PeticionBl
 */
public class PeticionBlImp implements PeticionBl {
	
	PeticionDao peticionDao;
	AuthDao authDao;
	UsuariosDao userDao;

	/**
	 * @param peticionDao
	 * @param authDao
	 * @param userDao
	 */
	public PeticionBlImp(PeticionDao peticionDao, AuthDao authDao, UsuariosDao userDao) {
		this.peticionDao = peticionDao;
		this.authDao = authDao;
		this.userDao = userDao;
	}

	/* (non-Javadoc)
	 * @see co.edu.udea.iw.business_logic.PeticionBl#verPeticionesDeAcceso()
	 */
	@Override
	public List<PeticionAcceso> verPeticionesDeAcceso() throws MyDaoException {
		List<PeticionAcceso> peticiones = null;
		
		int idResponsable = authDao.obtener().getId();
		
		if (!matchRol(idResponsable, "administrador")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		peticiones = peticionDao.obtener();
		if(peticiones==null || peticiones.size()==0){
			throw new MyDaoException("No existen peticiones de acceso",null);
		}
		
		return peticiones;
	}

	/* (non-Javadoc)
	 * @see co.edu.udea.iw.business_logic.PeticionBl#crearPeticionDeAcceso(co.edu.udea.iw.dto.PeticionAcceso)
	 */
	@Override
	public void crearPeticionDeAcceso(int cedula, String usuario, String nombre, String apellido, 
			String contrasena, String email, byte[] fotoRaw, String telefono, String direccion) throws MyDaoException, SerialException, SQLException {
		
		if (cedula == 0)throw new MyDaoException("Debe especificar cedula.", null);
		if(existsId(cedula)) throw new MyDaoException("Ya existe una peticion o un usuario con esa cedula", null);
		if(existeEmail(email)) throw new MyDaoException("Ya existe una peticion o un usuario con esa cedula",null);
		if (contrasena.length() < 6) throw new MyDaoException("La contraseña debe contener almenos 6 caracteres", null);
		if (Validaciones.isEmail(email)) throw new MyDaoException("El correo electronico no es valido",null);
		if (email == null || "".equals(email.trim())) throw new MyDaoException("Debe especificar correo electronico", null);
		if (telefono == null || "".equals(telefono.trim())) throw new MyDaoException("Debe especificar telefono", null);
		if (direccion == null || "".equals(direccion.trim())) throw new MyDaoException("Debe especificar direccion", null);
		if (usuario == null || "".equals(usuario.trim())) throw new MyDaoException("Debe especificar nombre de usuario", null);
		
		PeticionAcceso peticion = new PeticionAcceso();
		peticion.setCedula(cedula);
		peticion.setNombre(nombre);
		peticion.setApellido(apellido);
		Cifrar c = new Cifrar();
		peticion.setContrasena(c.encrypt(contrasena));
		peticion.setDireccion(direccion);
		peticion.setTelefono(telefono);
		peticion.setEmail(email);
		peticion.setFoto(new SerialBlob(fotoRaw));
				
		peticionDao.crear(peticion);
	}
	
	/* (non-Javadoc)
	 * @see co.edu.udea.iw.business_logic.PeticionBl#evaluarPeticionDeAcceso(int, java.lang.String, int, java.lang.String)
	 */
	@Override
	public void evaluarPeticionDeAcceso(int idPeticion, String estado, int admin, String justificacion)
			throws MyDaoException {
		if(!isActiveUser(admin)) throw new MyDaoException("No se encuentra activo para hacer esta operacion",null);
		if(!matchRol(admin, "administrador")) throw new MyDaoException("No tiene permisos para hacer esta transaccion",null);
		String mEstado = peticionDao.obtener(idPeticion).getEstado();
		if(mEstado.equals("aprobado") || mEstado.equals("rechazado")) throw new MyDaoException("La petición ya fue evaluada",null);
		if(justificacion ==null || "".equals(justificacion.trim())) throw new MyDaoException("Debe registrar justificacion",null);
		PeticionAcceso peticionEvaluada = peticionDao.obtener(idPeticion);
		peticionEvaluada.setEstado(estado);
		peticionEvaluada.setAdmin(userDao.obtener(admin));
		peticionEvaluada.setJustificacion(justificacion);
		
		peticionDao.modificar(peticionEvaluada);
		
	}

	/**
	 * Verifica si ya existe una peticion de acceso o un usuario con esa cedula
	 * @param cedula - cedula
	 * @return true si ya existe una peticion, false de lo contrario
	 * @throws MyDaoException
	 */
	private boolean existsId(int cedula) throws MyDaoException {
		List<PeticionAcceso> list = peticionDao.obtener();
		Iterator<PeticionAcceso> i = list.iterator();
		while(i.hasNext()){
			if(i.next().getCedula()==cedula){
				return true;
			}
		}
		List<Usuarios> list2 = userDao.obtener();
		Iterator<Usuarios> j = list2.iterator();
		while(j.hasNext()){
			if(j.next().getCedula()==cedula){
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Verifica si el email ingresado ya existe en la bd
	 * @param email
	 * @return true si correo ya existe en bd, false en caso contrario
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
		List<PeticionAcceso> list = peticionDao.obtener();
		Iterator<PeticionAcceso> j = list.iterator();
		while (j.hasNext()) {
			if (j.next().getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	
	
	/**
	 * Revisa si el usuario con cedula idResponsable, es del rol rol
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
	 * Verifica que usuario del id ingresado tiene estado activo
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

}
