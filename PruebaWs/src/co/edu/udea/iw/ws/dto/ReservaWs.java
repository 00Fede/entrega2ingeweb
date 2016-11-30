/**
 * 
 */
package co.edu.udea.iw.ws.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Federico Ocampo. cc: 1039464102. federico.ocampoo@udea.edu.co
 * POJO Mapping de Jersey para mostrar entidades en formato JSON
 */
@XmlRootElement
public class ReservaWs {
	
	private DispositivoWs dispositivo;
	private int idReserva;
	private Date fechaInicio;
	private Date fechaFin;
	/**
	 * @param dispositivo
	 * @param idDispositivo
	 * @param nombreDispositivo
	 * @param fotoDispositivo
	 * @param idReserva
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public ReservaWs(int idReserva, Date fechaInicio, Date fechaFin, DispositivoWs dispositivo) {
		super();
		this.setDispositivo(dispositivo);
		this.idReserva = idReserva;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	public ReservaWs(){}

	/**
	 * @return the idReserva
	 */
	public int getIdReserva() {
		return idReserva;
	}

	/**
	 * @param idReserva the idReserva to set
	 */
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public DispositivoWs getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(DispositivoWs dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	
	
	
	

}
