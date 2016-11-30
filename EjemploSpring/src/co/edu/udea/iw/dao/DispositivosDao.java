package co.edu.udea.iw.dao;


/**
 * Define los metodos para acceder a los dispositivos
 * @author aux10
 */

import java.util.List;

import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.exception.MyDaoException;

public interface DispositivosDao {
	
	/**
	 * Entrega la lista de dispositivos de la bd
	 * @return
	 * @throws MyDaoException
	 */
	public List<Dispositivos> obtener() throws MyDaoException;
	
	/**
	 * Entrega la lista de dispositivos de la bd  disponibles agrupados por modelo
	 * @return
	 * @throws MyDaoException
	 */
	public List<Dispositivos> obtenerDisponiblesPorModelo() throws MyDaoException;
	
	/**
	 * Entrega un dispositivo con el numero de serie ingresado
	 * @return 
	 * @param no_serie   numero de serie del dispositivo 
	 * @throws MyDaoException
	 */
	public Dispositivos obtener(int no_serie) throws MyDaoException;
	
	/**
	 * almacena un dispositivo en la bd
	 * @param dispositivo   dispositivo a guardar en la bd
	 * @throws MyDaoException
	 */
	public void guardar(Dispositivos dispositivo) throws MyDaoException;
	
	/**
	 * elimina un dispositivo de la bd 
	 * @param no_serie   numero de serie del dispositivo a eliminar
	 * @throws MyDaoException
	 */
	public void eliminar(int no_serie) throws MyDaoException;
	
	/**
	 * modifica un dispositivo de la bd 
	 * @param dispositivo   dispositivo a guardar en la bd
	 * @throws MyDaoException
	 */
	public void modificar(Dispositivos dispositivo) throws MyDaoException;

}
