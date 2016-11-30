package co.edu.udea.iw.ws.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClienteWs {
	
	private String nombres;
	private String apellidos;
	private String identificacion;
	private String email;
	
	
	public ClienteWs(String nombres, String apellidos, String identificacion, String email) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.identificacion = identificacion;
		this.email = email;
	}
	
	
	
	
	public ClienteWs() {
	}




	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
