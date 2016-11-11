/**
 * 
 */
package co.edu.udea.iw.ws.dto;

import java.sql.Blob;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Federico
 * Clase tipo POJO que contiene los atributos necesarios
 * para consumir varios servicios web
 */

@XmlRootElement
public class UsuarioWs {
	private int cedula;
	private String nombre;
	private String apellido;
	private String usuario;
	private String contrasena;
	private String rol;
	private String direccion;
	private String email;
	private byte[] foto;
	private String telefono;
	private String estado;
	/**
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param usuario
	 * @param contrasena
	 * @param rol
	 * @param direccion
	 * @param email
	 * @param foto
	 * @param telefono
	 * @param estado
	 */
	public UsuarioWs(int cedula, String nombre, String apellido, String usuario, String contrasena, String rol,
			String direccion, String email, String telefono, String estado, byte[] foto) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.rol = rol;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
		this.estado = estado;
		this.foto = foto;
	}
	
	public UsuarioWs(){}
	/**
	 * @return the cedula
	 */
	public int getCedula() {
		return cedula;
	}
	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}
	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the contrasena
	 */
	public String getContrasena() {
		return contrasena;
	}
	/**
	 * @param contrasena the contrasena to set
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}
	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the fot
	 */
	public byte[] getFoto() {
		return foto;
	}
	/**
	 * @param foto the foto to set
	 */
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	

}
