package co.edu.udea.iw.dto;

import java.util.Date;

public class Sancion {

	/**
	 * Esta clase será la clase pojo o dto, relacionoada 
	 * con la entidad llamada sancion, ademas de esto en esta
	 * clase se definen los atributos necesarios para poder 
	 * mapear con ayuda de hibernate la informacion, tambien cuenta
	 * con sus respectivos setter y getters.
	 */
	private int Id_sancion;
	private Dispositivos Id_dispositivo;
	private Usuarios Id_cedula;
	private Usuarios Id_responsable;
	private Date Fecha_inicio;
	private String Razon;
	private int Tiempo_sancion;
	
	public int getId_sancion() {
		return Id_sancion;
	}
	public void setId_sancion(int id_sancion) {
		Id_sancion = id_sancion;
	}
	public Dispositivos getId_dispositivo() {
		return Id_dispositivo;
	}
	public void setId_dispositivo(Dispositivos id_dispositivo) {
		Id_dispositivo = id_dispositivo;
	}
	public Usuarios getId_cedula() {
		return Id_cedula;
	}
	public void setId_cedula(Usuarios id_cedula) {
		Id_cedula = id_cedula;
	}
	public Usuarios getId_responsable() {
		return Id_responsable;
	}
	public void setId_responsable(Usuarios id_responsables) {
		Id_responsable = id_responsables;
	}
	public Date getFecha_inicio() {
		return Fecha_inicio;
	}
	public void setFecha_inicio(Date fecha_inicio) {
		Fecha_inicio = fecha_inicio;
	}
	public String getRazon() {
		return Razon;
	}
	public void setRazon(String razon) {
		Razon = razon;
	}
	public int getTiempo_sancion() {
		return Tiempo_sancion;
	}
	public void setTiempo_sancion(int tiempo_sancion) {
		Tiempo_sancion = tiempo_sancion;
	}
	
	
	
}
