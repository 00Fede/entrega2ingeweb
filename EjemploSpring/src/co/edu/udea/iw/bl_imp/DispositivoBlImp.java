package co.edu.udea.iw.bl_imp;

import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.orm.hibernate3.support.BlobByteArrayType;

import co.edu.udea.iw.business_logic.DispositivoBl;
import co.edu.udea.iw.dao.DispositivosDao;
import co.edu.udea.iw.dao.ReservaDao;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.util.encode.Cifrar;

/**
 * @see DispositivoBl
 * @author aux10
 */

public class DispositivoBlImp implements DispositivoBl {

	DispositivosDao dispDao;
	UsuariosDao usuarioDao;
	ReservaDao reservaDao;// necesario para validar rol de usuario
	private String disponible = "1"; // criterio de disponibilidad de
										// dispositivo

	/**
	 * Constructor de la implementación. Necesario para inyecccion con Spring
	 * 
	 * @param dispDao
	 * @param usuarioDao
	 */
	/*
	public DispositivoBlImp(DispositivosDao dispDao, UsuariosDao usuarioDao, ReservaDao reservaDao) {
		this.dispDao = dispDao;
		this.usuarioDao = usuarioDao;
		this.reservaDao = reservaDao;
	}
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
		// if (usuarioConectado.equals(null)) {
		// throw new MyDaoException("Debe ser administrador para acceder a esta
		// función", null);
		// }
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
	public void agregarDispositivo(int cedulaResponsable, int nroSerie, String nombre, String modelo, String peqDesc,
			byte[] fotoRAW, String restriccion, String observacion, String estado, String disponibilidad)
			throws MyDaoException, SerialException, SQLException {

		if (!usuarioActivo(cedulaResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacción", null);
		}
		

		if (!(matchRol(cedulaResponsable, "administrador")|| matchRol(cedulaResponsable, "superusuario"))) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}

		if (nroSerie == 0) {
			throw new MyDaoException("Debe especificar numero de serie de dispositivo", null);
		}
		if (nombre == null || "".equals(nombre.trim())) {
			throw new MyDaoException("Debe especificar nombre de dispositivo", null);
		}
		if (modelo == null || "".equals(modelo.trim())) {
			throw new MyDaoException("Debe especificar modelo de dispositivo", null);
		}

		if (dispDao.obtener(nroSerie) != null) {
			throw new MyDaoException("El dispositivo ya existe", null);
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
	public void eliminarDispositivoLogicamente(int cedulaResponsable, int nroSerie, String justificacion)
			throws MyDaoException {

		if (!usuarioActivo(cedulaResponsable)) {
			throw new MyDaoException("No se encuentra activo para eliminar el dispositivo", null);
		}

		if (!(matchRol(cedulaResponsable, "administrador")|| matchRol(cedulaResponsable, "superusuario"))) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}

		if (!disponibilidadDispositivo(nroSerie)) {
			throw new MyDaoException("El dispositivo no puede ser dado de baja en el momento, porque"
					+ " se encuentra como no disponible", null);
		}

		if (nroSerie == 0) {
			throw new MyDaoException("Debe especificar numero de serie de dispositivo", null);
		}

		if (dispDao.obtener(nroSerie) == null) {
			throw new MyDaoException("Debe elegir un dispositivo valido para eliminar", null);
		}
		if (justificacion.equals("") || justificacion.equals(" ")) {
			throw new MyDaoException("Debe ingresar una justificacion valida", null);
		}

		Dispositivos disp = dispDao.obtener(nroSerie);
		disp.setEstado("2");
		dispDao.modificar(disp);

	}

	@Override
	public void modificarDispositivo(int cedulaResponsable, int nroSerie, String nombre, String modelo, String peqDesc,
			byte[] fotoRAW, String restriccion, String observacion, String estado, String disponibilidad)
			throws MyDaoException, SerialException, SQLException {

		if (!usuarioActivo(cedulaResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacción", null);
		}

		if (!(matchRol(cedulaResponsable, "administrador")|| matchRol(cedulaResponsable, "superusuario"))) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		
		if (dispDao.obtener(nroSerie) == null) {
			throw new MyDaoException("El numero de serie ingresado no corresponde a ningun dispositivo", null);
		}

		if (nombre == null || "".equals(nombre.trim())) {
			throw new MyDaoException("Debe especificar nombre para el dispositivo", null);
		}
		if (modelo == null || "".equals(modelo.trim())) {
			throw new MyDaoException("Debe especificar un modelo para el dispositivo", null);
		}
		if (peqDesc == null || "".equals(peqDesc.trim())) {
			throw new MyDaoException("Debe especificar un descripción del dispositivo", null);
		}
		if (restriccion == null || "".equals(restriccion.trim())) {
			throw new MyDaoException("Debe especificar una restricción para el dispositivo", null);
		}
		if (observacion == null || "".equals(observacion.trim())) {
			throw new MyDaoException("Debe especificar una observacion para el dispositivo", null);
		}
		if (estado == null || "".equals(estado.trim())) {
			throw new MyDaoException("Debe especificar un estado para el dispositivo", null);
		}
		if (fotoRAW == null) {
			throw new MyDaoException("Debe ingresar una imagen asociada al dispositivo", null);
		}
		if (disponibilidad == null || "".equals(estado.trim())) {
			throw new MyDaoException("Debe especificar una disponibilidad para el dispositivo", null);
		}

		Dispositivos updateDispositivo = dispDao.obtener(nroSerie);
		updateDispositivo.setNombre(nombre);
		updateDispositivo.setModelo(modelo);
		updateDispositivo.setDescripcion(peqDesc);
		updateDispositivo.setRestriccion(restriccion);
		updateDispositivo.setObservacion(observacion);
		updateDispositivo.setEstado(estado);
		Blob foto = new javax.sql.rowset.serial.SerialBlob(fotoRAW);
		updateDispositivo.setFoto(foto);
		dispDao.modificar(updateDispositivo);

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
	 * 
	 * @param id
	 * @param rol
	 * @return
	 * @throws MyDaoException
	 */
	private boolean matchRol(int id, String rol) throws MyDaoException {
		Usuarios userResponsable = usuarioDao.obtener(id);
		if (userResponsable != null) {
			if (userResponsable.getRol().equals(rol)) {
				return true;
			}
		}
		return false;
	}

	private boolean disponibilidadDispositivo(int id) throws MyDaoException {
		Dispositivos disp = dispDao.obtener(id);
		if (disp != null) {
			if (disp.getEstado().equals("0") || disp.getEstado().equals("2")) {
				return false;
			}
		}
		return true;
	}

	private boolean usuarioActivo(int id) throws MyDaoException {
		Usuarios userResponsable = usuarioDao.obtener(id);
		if (userResponsable != null) {
			if (userResponsable.getEstado().equals("inactivo")) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void realizarPrestamoDispositivo(int cedulaResponsable,int cedulaI, int nroSerie, Date fechaInicio, int duracionHoras, int idReserva)
			throws MyDaoException {
		
		if (!usuarioActivo(cedulaResponsable)) {
			throw new MyDaoException("No se encuentra activo para hacer esta transacción", null);
		}

		if (!(matchRol(cedulaResponsable, "administrador")|| matchRol(cedulaResponsable, "superusuario"))) {
			throw new MyDaoException("No tiene permisos para hacer esta transaccion", null);
		}
		
		if (!disponibilidadDispositivo(nroSerie)) {
			throw new MyDaoException("El dispositivo no puede ser prestado porque no se encuentra disponible", null);
		}
		
		if(duracionHoras > 8){
			throw new MyDaoException("La duracion del prestamo no puede ser superior a 8 horas", null);
		}
		
		Dispositivos disPrestamo = dispDao.obtener(nroSerie);
		disPrestamo.setEstado("0");
		
		Usuarios usuario = usuarioDao.obtener(cedulaI);
		Usuarios administrador = usuarioDao.obtener(cedulaResponsable);
		
		Reserva prestamo = new Reserva();
		prestamo.setEstado(1);
		prestamo.setFecha_entrega(fechaInicio);
		prestamo.setId_cedula(usuario);
		prestamo.setId_responsable(administrador);
		prestamo.setId_reserva(idReserva);
		prestamo.setId_dispositivo(disPrestamo);
		prestamo.setFecha_inicio(fechaInicio);
		prestamo.setTiempo_reserva(duracionHoras);
		prestamo.setEstado(1);
		
		reservaDao.guardar(prestamo);
		dispDao.modificar(disPrestamo);
			
	}


}
