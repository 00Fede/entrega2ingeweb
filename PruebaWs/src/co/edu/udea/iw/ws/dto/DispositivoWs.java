package co.edu.udea.iw.ws.dto;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DispositivoWs {


	private int id;
	private String Nombre;
	private String Modelo;
	private String Descripcion;
	private String Restriccion;
	private String Observacion;
	private String Estado;
	private String Disponibilidad;
	private byte[] foto;
	
	public DispositivoWs() {
		
	}
	
	public DispositivoWs(String nombre, String modelo, String descripcion, String restriccion, String observacion,
			String estado, String disponibilidad) {
		super();
		Nombre = nombre;
		Modelo = modelo;
		Descripcion = descripcion;
		Restriccion = restriccion;
		Observacion = observacion;
		Estado = estado;
		Disponibilidad = disponibilidad;
		
	}
	
	public DispositivoWs(String name, int id, byte[] foto){
		this.Nombre = name;
		this.id = id;
		this.setFoto(foto);
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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getModelo() {
		return Modelo;
	}
	public void setModelo(String modelo) {
		Modelo = modelo;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getRestriccion() {
		return Restriccion;
	}
	public void setRestriccion(String restriccion) {
		Restriccion = restriccion;
	}
	public String getObservacion() {
		return Observacion;
	}
	public void setObservacion(String observacion) {
		Observacion = observacion;
	}
	public String getEstado() {
		return Estado;
	}
	public void setEstado(String estado) {
		Estado = estado;
	}
	public String getDisponibilidad() {
		return Disponibilidad;
	}
	public void setDisponibilidad(String disponibilidad) {
		Disponibilidad = disponibilidad;
	}
	
}
