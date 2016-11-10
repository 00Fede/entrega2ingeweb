package co.edu.udea.iw.business_logic;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * Metodos que garantizan la logica del negocio
 * en la interacción con el dispositivo
 * @author Federico
 *
 */
public interface DispositivoBl {
	
	/**
	 * FRQ-0003 - Ver todos los dispositivos
	 * Permite al usuario visualizar todos los dispositivos
	 * agrupados por modelo
	 * @return Lista de dispositivos agrupados por modelo
	 * @throws MyDaoException
	 */
	public List<Dispositivos> verDispositivosPorModelo() throws MyDaoException;
	
	/**
	 * FRQ-0003 - Ver todos los dispositivos disponibles
	 * Permite al usuario visualizar todos los dispositivos disponibles 
	 * agrupados por modelo
	 * @return Lista de dispositivos agrupados por modelo
	 * @throws MyDaoException
	 */
	public List<Dispositivos> verDispositivosDisponiblesPorModelo() throws MyDaoException;
	
	/**
	 * FRQ-0004 - Agregar Dispositivo
	 * Permite a un usuario administrador crear un dispositivo
	 * @param nroSerie obligatorio
	 * @param nombre obligatorio
	 * @param modelo obligatorio
	 * @param peqDesc
	 * @param fotoRAW
	 * @param restriccion
	 * @param observacion
	 * @throws MyDaoException, {@link SQLException, SerialException}
	 */
	public void agregarDispositivo(int nroSerie, String nombre, String modelo, String peqDesc, byte[] fotoRAW,
			String restriccion, String observacion,String estado, String disponibilidad) throws MyDaoException, SerialException, SQLException;

	/**
	 * FRQ-0005 - Eliminar dispositivo
	 * Permite al usuario administrador eliminar un dispositivo
	 * y agregar la justificación de su acción
	 * @param nroSerie
	 * @param justificacion Razon de la eliminación
	 * @throws MyDaoException
	 */
	public void eliminarDispositivoLogicamente(int nroSerie, String justificacion) throws MyDaoException;
	
	/**
	 * FRQ-0025 - Modificar dispositivo
	 * Permite a un administrador modificar un dispositivo y agregar la justificación
	 * de su acción
	 * @param nroSerie obligatorio
	 * @param nombre obligatorio
	 * @param modelo obligatorio
	 * @param peqDesc
	 * @param fotoRAW
	 * @param restriccion
	 * @param observacion
	 * @param justificacion Razón de la modificación
	 * @throws MyDaoException
	 */
	public void modificarDispositivo(int nroSerie, String nombre, String modelo, String peqDesc,
			byte[] fotoRAW,String restriccion, String observacion, String justificacion) throws MyDaoException;
	

}
