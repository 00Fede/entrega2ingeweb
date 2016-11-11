/**
 * 
 */
package co.udea.edu.iw.ws;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.business_logic.PeticionBl;
import co.edu.udea.iw.dto.PeticionAcceso;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.ws.dto.PeticionWs;
import co.edu.udea.iw.ws.dto.UsuarioWs;

/**
 * @author Federico
 * Servlet encargado de manejar la funcionalidad de las peticiones desde
 * los servicios web proveidos
 */
@Path("/ServicioPeticion")
@Component
public class ServicioPeticion {
	
	@Autowired
	PeticionBl petBl;
	
	/**
	 * Servicio Web para FRQ-0008 Peticion de Acceso
	 * Este servicio permite a una persona que desee ingresar al sistema de prestamos hacer una peticion donde
	 * solicita el ingreso, para ello agrega todos sus datos personales que permitan verificar su identidad.
	 * @param cedula no de existir en los usuarios o otras peticiones de acceso
	 * @param usuario nombre de usuario no de existir en los usuarios o otras peticiones de acceso
	 * @param nombre 
	 * @param apellido
	 * @param contrasena mayor de 6 caracteres
	 * @param email no de existir en los usuarios o otras peticiones de acceso
	 * @param fotoRaw opcional
	 * @param telefono obligatorio
	 * @param direccion obligatorio
	 * @return Mensaje de operacion exitosa
	 * @throws RemoteException
	 * @throws SerialException Por la insercion de foto
	 * @throws SQLException Por la insercion de foto
	 */
	@POST
	@Path("generar")
	@Produces(MediaType.TEXT_HTML)
	public String generarPeticion(@QueryParam("ced")int cedula, @QueryParam("username")String usuario, 
			@QueryParam("name")String nombre, @QueryParam("ape")String apellido, 
			@QueryParam("pass")String contrasena, @QueryParam("email")String email, 
			@QueryParam("foto")String fotoRaw, @QueryParam("phone")String telefono, 
			@QueryParam("dir")String direccion) throws RemoteException, SerialException, SQLException{
		
		try {
			petBl.crearPeticionDeAcceso(cedula, usuario, nombre, apellido, contrasena, email, 
					fotoRaw.getBytes(), telefono, direccion);
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
		
		return "Peticion de usuario "+nombre+" ingresada correctamente";
	}
	
	/**
	 * Servicio Web para FRQ-0013 - Listar Peticiones de Acceso
	 * Este servicio lista a un administrador todas las peticiones de acceso generadas en el sistema
	 * Puede ver informacion del solicitante y datos del administrador evaluador, en caso que haya sido evaluado.
	 * El sistema verifica que el usuario logueado sea administrador por medio de la tabla autenticacion de la bd
	 * @return Lista de peticiones de acceso
	 * @throws RemoteException
	 * @throws SQLException por el procesamiento de la imagen
	 */
	@GET
	@Path("peticiones")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PeticionWs> listarPeticiones() throws RemoteException, SQLException{
		List<PeticionAcceso> data = null;
		List<PeticionWs> resultados = new ArrayList<>();
		try {
			data = petBl.verPeticionesDeAcceso();
			Iterator<PeticionAcceso> it = data.iterator();
			while (it.hasNext()) {
				 PeticionAcceso auxPet = it.next();
				 Usuarios admin = auxPet.getAdmin();
				 UsuarioWs adminW = new UsuarioWs();
				 if(admin!=null) {
					 adminW.setCedula(admin.getCedula());
					 adminW.setNombre(admin.getNombre());
					 adminW.setApellido(admin.getApellido());
					 adminW.setEmail(admin.getEmail());
				 }
				 PeticionWs peticion = new PeticionWs(auxPet.getId(), auxPet.getCedula(), auxPet.getNombre()
						 ,auxPet.getApellido(), auxPet.getUsuario(), auxPet.getContrasena(), 
						 auxPet.getDireccion(),auxPet.getEmail(), auxPet.getFoto().getBytes(1, (int)auxPet.getFoto().length()),
						 auxPet.getTelefono(), auxPet.getEstado(), adminW, auxPet.getJustificacion());
				 
				 resultados.add(peticion);
			}
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
		return resultados;
	}
	
	@PUT
	@Path("evaluar")
	@Produces(MediaType.TEXT_HTML)
	public String evaluar(@QueryParam("id")int idPeticion,@QueryParam("admin")int idAdmin,
			@QueryParam("estado")String estado, @QueryParam("just")String justificacion) throws RemoteException{
		try {
			petBl.evaluarPeticionDeAcceso(idPeticion, estado, idAdmin, justificacion);
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
		return "Peticion "+idPeticion+" evaluada correctamente con estado "+estado+".";
	}
	
	

}
