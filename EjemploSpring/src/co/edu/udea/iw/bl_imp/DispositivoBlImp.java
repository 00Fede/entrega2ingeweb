package co.edu.udea.iw.bl_imp;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.orm.hibernate3.support.BlobByteArrayType;

import co.edu.udea.iw.business_logic.DispositivoBl;
import co.edu.udea.iw.dao.DispositivosDao;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * @see DispositivoBl
 * @author aux10
 */

public class DispositivoBlImp implements DispositivoBl {

	DispositivosDao dispDao;
	UsuariosDao usuarioDao; // necesario para validar rol de usuario
	private String disponible = "1";  // criterio de disponibilidad de dispositivo

	/**
	 * Constructor de la implementación. Necesario para inyecccion con Spring
	 * 
	 * @param dispDao
	 * @param usuarioDao
	 */
	public DispositivoBlImp(DispositivosDao dispDao, UsuariosDao usuarioDao) {
		this.dispDao = dispDao;
		this.usuarioDao = usuarioDao;
	}

	@Override
	public List<Dispositivos> verDispositivosPorModelo() throws MyDaoException {
		List<Dispositivos> dispList = null;
		List<Dispositivos> listDispModelo = null;
		// usuarioConectado = usuarioDao.obtenerUsuarioConectado();
		// valida si el usuario no esta conectado
		//if (usuarioConectado.equals(null)) {
		//	throw new MyDaoException("Debe ser administrador para acceder a esta función", null);
		//}
		dispList = dispDao.obtener();

		if (dispList.isEmpty()) {
			throw new MyDaoException("No existen dispositivos para mostrar", null);
		}

		Iterator<Dispositivos> iteList = dispList.iterator();
		ArrayList<String> models = new ArrayList<>();
		listDispModelo = new ArrayList<>();
		// toma todos los modelos sin repetir de los dispositivos en una lista
		while (iteList.hasNext()) {
			Dispositivos disp = iteList.next();
			String model = disp.getModelo();
			if (!models.contains(model)) {
				models.add(model);
				listDispModelo.add(disp);
			}
		}

		return listDispModelo;
	}

	@Override
	public void agregarDispositivo(int cedulaResponsable,int nroSerie, String nombre, String modelo, String peqDesc, byte[] fotoRAW,
			String restriccion, String observacion,String estado, String disponibilidad) throws MyDaoException, SerialException, SQLException {
	
		if (!usuarioActivo(cedulaResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacción", null);
		}
		
		if (!matchRol(cedulaResponsable, "administrador")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		
		if(nroSerie == 0){
			throw new MyDaoException("Debe especificar numero de serie de dispositivo",null);
		}
		if(nombre == null || "".equals(nombre.trim())){
			throw new MyDaoException("Debe especificar nombre de dispositivo",null);
		}
		if(modelo == null || "".equals(modelo.trim())){
			throw new MyDaoException("Debe especificar modelo de dispositivo",null);
		}
		
		if(dispDao.obtener(nroSerie)!=null){
			throw new MyDaoException("El dispositivo ya existe",null);
		}
		
		Dispositivos newDisp = new Dispositivos();
		newDisp.setNumero_serie(nroSerie);
		newDisp.setNombre(nombre);
		newDisp.setModelo(modelo);
		newDisp.setDescripcion(peqDesc);
		Blob foto = new SerialBlob(fotoRAW);
		newDisp.setFoto(foto);
		newDisp.setRestriccion(restriccion);
		newDisp.setObservacion(observacion);
		newDisp.setEstado(estado);
		newDisp.setDisponibilidad(disponibilidad);
		
		dispDao.guardar(newDisp);
		
	}

	
	@Override
	public void eliminarDispositivoLogicamente(int cedulaResponsable,int nroSerie, String justificacion) throws MyDaoException {
		
		if (!usuarioActivo(cedulaResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacción", null);
		}
		
		if (!matchRol(cedulaResponsable, "administrador")) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		
		if(!disponibilidadDispositivo(nroSerie)){
			throw new MyDaoException("El dispositivo no puede ser dado de baja en el momento, porque"
					+ " se encuentra como no disponible", null);
		}
		
		if(nroSerie == 0){
			throw new MyDaoException("Debe especificar numero de serie de dispositivo",null);
		}
		
		if(dispDao.obtener(nroSerie)==null){
			throw new MyDaoException("Debe elegir un dispositivo valido para eliminar",null);
		}
		if(justificacion.equals("")||justificacion.equals(" ")){
			throw new MyDaoException("Debe ingresar una justificacion valida",null);
		}
		
		Dispositivos disp = dispDao.obtener(nroSerie);
		disp.setEstado("2");
		dispDao.modificar(disp);
		
	}

	@Override
	public void modificarDispositivo(int nroSerie, String nombre, String modelo, String peqDesc, byte[] fotoRAW,
			String restriccion, String observacion, String justificacion) throws MyDaoException {
		
		

	}

	@Override
	public List<Dispositivos> verDispositivosDisponiblesPorModelo() throws MyDaoException {
		List<Dispositivos> dispList = null;
		List<Dispositivos> listDispModelo = null;
		
		dispList = dispDao.obtener();

		if (dispList.isEmpty()) {
			throw new MyDaoException("No existen dispositivos para mostrar", null);
		}

		Iterator<Dispositivos> iteList = dispList.iterator();
		ArrayList<String> models = new ArrayList<>();
		listDispModelo = new ArrayList<>();
		while (iteList.hasNext()) {
			Dispositivos disp = iteList.next();
			String model = disp.getModelo();
			String disponibl = disp.getEstado();
			if (!models.contains(model) && disponibl.equals("1")) {
				models.add(model);
				listDispModelo.add(disp);
			}
		}

		return listDispModelo;
	}
	
	/**
	 * Revisa si el usuario con cedula idResponsable, es del rol rol
	 * @param id
	 * @param rol
	 * @return
	 * @throws MyDaoException
	 */
	private boolean matchRol(int id, String rol) throws MyDaoException {
		Usuarios userResponsable = usuarioDao.obtener(id);
		if (userResponsable.getRol().equals(rol)) {
			return true;
		}
		return false;
	}
	
	private boolean disponibilidadDispositivo(int id) throws MyDaoException {
		Dispositivos disp = dispDao.obtener(id);
		if(disp.getEstado().equals("0")||disp.getEstado().equals("2")){
			return false;
		}
		return true;
	}

	private boolean usuarioActivo(int id) throws MyDaoException {
		Usuarios userResponsable = usuarioDao.obtener(id);
		if (userResponsable.getEstado().equals("inactivo")) {
			return false;
		}
		return true;
	}
}
