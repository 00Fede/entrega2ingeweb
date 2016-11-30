/**
 * 
 */
package co.edu.udea.iw.ws.dto;

import java.sql.Blob;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Federico Ocampo. cc: 1039464102. federico.ocampoo@udea.edu.co
 * Esta entidad tipo POJO hace posible representar las entidades de la base
 * de datos en objetos JSON, ademas puede modificarse para solo obtener los valores
 * necesarios por la peticion web realizada
 */

@XmlRootElement
public class PeticionWs {
	
	private int id;
	private int cedula;
	private String nombre;
	private String apellido;
	private String usuario;
	private String contrasena;
	private String direccion;
	private String email;
	private byte[] foto;
	private String telefono;
	private String estado; //default pendiente
	private UsuarioWs admin;
	private String justificacion;
	
	public PeticionWs(){} //necesario constructor vacio
	
	/**
	 * con este constructor se generara el json para visualizacion web
	 * @param id
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param usuario
	 * @param contrasena
	 * @param direccion
	 * @param email
	 * @param foto
	 * @param telefono
	 * @param estado
	 * @param admin
	 * @param justificacion
	 */
	public PeticionWs(int id, int cedula, String nombre, String apellido, String usuario, String contrasena,
			String direccion, String email, byte[] foto, String telefono, String estado, UsuarioWs admin,
			String justificacion) {
		super();
		this.id = id;
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.direccion = direccion;
		this.email = email;
		this.foto = foto;
		this.telefono = telefono;
		this.estado = estado;
		this.admin = admin;
		this.justificacion = justificacion;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCedula() {
		return cedula;
	}
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public UsuarioWs getAdmin() {
		return admin;
	}
	public void setAdmin(UsuarioWs admin) {
		this.admin = admin;
	}
	public String getJustificacion() {
		return justificacion;
	}
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	
	

}
