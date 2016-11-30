package co.edu.udea.iw.dto;

import java.util.Date;

/**
 * Clase para gestionar la autenticacion en el sistema
 * @author aux10
 *
 */
public class Autenticacion {
	
	private int id_auth;
	private int id;
	private Date fecha_auth;
	
	/**
	 * @return the id_auth
	 */
	public int getId_auth() {
		return id_auth;
	}
	/**
	 * @param id_auth the id_auth to set
	 */
	public void setId_auth(int id_auth) {
		this.id_auth = id_auth;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the fecha_auth
	 */
	public Date getFecha_auth() {
		return fecha_auth;
	}
	/**
	 * @param fecha_auth the fecha_auth to set
	 */
	public void setFecha_auth(Date fecha_auth) {
		this.fecha_auth = fecha_auth;
	}
	

}
