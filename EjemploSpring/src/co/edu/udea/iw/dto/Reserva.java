package co.edu.udea.iw.dto;

import java.util.Date;

/**
 * Esta clase será la clase pojo o dto, para la entidad reserva
 * en esta clase se definen cada uno de los atributos que la entidad requiere
 * y sus respectivos Setter y Getters.
 * La entidad relacionada con esta clase tendrá las siguientes relaciones
 * El primer dato será el atributo de esta clase y segundo será el nombre de el 
 * atributo equivalente en la entidad
 * @author andres montoya
 *
 */

public class Reserva {
	
	private Dispositivos Id_dispositivo;
	private Usuarios Id_cedula;
	private Usuarios Id_responsable;
	private Date Fecha_inicio;
	private int Tiempo_reserva;
	private int estado;
	
	private int Id_reserva;
	public int getId_reserva() {
		return Id_reserva;
	}
	public void setId_reserva(int id_reserva) {
		Id_reserva = id_reserva;
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
	public void setId_responsable(Usuarios id_responsable) {
		Id_responsable = id_responsable;
	}
	public Date getFecha_inicio() {
		return Fecha_inicio;
	}
	public void setFecha_inicio(Date fecha_inicio) {
		Fecha_inicio = fecha_inicio;
	}
	public int getTiempo_reserva() {
		return Tiempo_reserva;
	}
	public void setTiempo_reserva(int tiempo_reserva) {
		Tiempo_reserva = tiempo_reserva;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	
	
	
}
