package co.edu.udea.iw.dto;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Id;

/*
 * Esta clase será la clase pojo o dto, para la entidad dispositivo
 * en esta clase se definen cada uno de los atributos que la entidad requeire
 * y sus respectivos Setter y Getters.
 * La entidad relacionada con esta clase tendrá las siguientes relaciones
 * El primer dato será el atributo de esta clase y segundo será el nombre de el 
 * atributo equivalente en la entidad
 * 
 */

public class Dispositivos {
	
	private int Numero_serie;
	private String Nombre;
	private String Modelo;
	private String Descripcion;
	private String Restriccion;
	private String Observacion;
	private String Estado;
	private String Disponibilidad;
	private Blob foto;
	
	

	public int getNumero_serie() {
		return Numero_serie;
	}
	public void setNumero_serie(int numero_serie) {
		Numero_serie = numero_serie;
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
	public Blob getFoto() {
		return foto;
	}
	public void setFoto(Blob foto) {
		this.foto = foto;
	}
	
	
	
	
}
