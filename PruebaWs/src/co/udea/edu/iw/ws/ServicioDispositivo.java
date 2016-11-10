package co.udea.edu.iw.ws;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.business_logic.DispositivoBl;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.ws.dto.DispositivoWs;



@Path("servicioDispositivo")
@Component
public class ServicioDispositivo  {

		@Autowired
		DispositivoBl dispositivoBl;
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("todosLosDispositivos")
		public List<DispositivoWs> obtener() throws RemoteException{
			List<DispositivoWs> resultado = new ArrayList<>();
			List<Dispositivos> datos = null;
			try {
				datos = dispositivoBl.verDispositivosPorModelo();
						
					for (Dispositivos dispositivo:datos){
						DispositivoWs dispositivoWs = new DispositivoWs(dispositivo.getNombre(),dispositivo.getModelo(),
								dispositivo.getDescripcion(),dispositivo.getRestriccion(),dispositivo.getObservacion(),dispositivo.getEstado(),
								dispositivo.getDisponibilidad());
		
						resultado.add(dispositivoWs);
					}
	
			} catch (MyDaoException e) {
				throw new RemoteException(e.getMessage(),e);
			}
			return resultado;
		}
		
}
