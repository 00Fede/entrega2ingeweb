/**
 * 
 */
package co.udea.edu.iw.ws;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.business_logic.UsuarioBl;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;
import co.edu.udea.iw.ws.dto.UsuarioWs;

/**
 * Clase que respondera a todas las peticiones web realizadas
 * relacionadas con los usuarios
 * @author Federico Ocampo. cc: 1039464102. federico.ocampoo@udea.edu.co
 *
 */

@Path("/ServicioUsuario")
@Component
public class ServicioUsuario {
	
	@Autowired
	UsuarioBl userBl;
	
	
	/**
	 * Retorna la lista de investigadores a un usuario administrador activo
	 * @param idResponsable - Usuario que hace la peticion
	 * @return Lista con investigadores
	 * @throws RemoteException 
	 * @throws SQLException - por el manejo del blob
	 * url servicio: http://localhost:8080/PruebaWs/rest/ServicioUsuario/investigadores?id=1039
	 */
	@GET
	@Path("investigadores")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UsuarioWs> obtenerInvestigadores(@QueryParam("id")int idResponsable) throws RemoteException, SQLException{
		List<UsuarioWs> investigadores = new ArrayList<>();
		List<Usuarios> datos = null;
		try {
			datos = userBl.listarInvestigadores(idResponsable);
			Iterator<Usuarios> i = datos.iterator();
			while(i.hasNext()){
				Usuarios aux = i.next();
				UsuarioWs u = new UsuarioWs(aux.getCedula(), 
						aux.getNombre(), aux.getApellido(), aux.getUsuario(), 
						aux.getContrasena(), aux.getRol(), aux.getDireccion(), 
						aux.getEmail(), aux.getTelefono(), aux.getEstado(), 
						aux.getFoto().getBytes(1, (int) aux.getFoto().length()));
				investigadores.add(u);
			}
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(), e);
		}
		
		return investigadores;
	}
	
	/**
	 * Servicio web para registrar un usuario administrado dados todos sus atributos.
	 * Este servicio solo puede ser ejecutado por un administrador.
	 * @param idSuper
	 * @param ced
	 * @param name
	 * @param ape
	 * @param correo
	 * @param username
	 * @param pass
	 * @param foto
	 * @param telefono
	 * @param direccion
	 * @return Mensaje de success si el registro es exitoso
	 * @throws RemoteException
	 */
	@POST
	@Path("registroAdmin")
	@Produces(MediaType.TEXT_HTML)
	public String registroAdmin(@QueryParam("super")int idSuper, @QueryParam("ced")int ced,
			@QueryParam("name")String name, @QueryParam("ape")String ape,@QueryParam("email")String correo,
			@QueryParam("user")String username, @QueryParam("pass")String pass,
			@QueryParam("foto")String foto, @QueryParam("phone")String telefono,
			@QueryParam("dir")String direccion) throws RemoteException{
		
		try{
			userBl.registrarAdministrador(idSuper, ced, name, ape, correo, username, pass, foto.getBytes(), telefono, direccion);
			return "Se ha registrado correctamente administrador " + name +"."; 
		}catch (MyDaoException | SQLException e) {
			throw new RemoteException(e.getMessage(),e);
		}		
	}

	/**
	 * Servicio para eliminar administrador del sistema. Este solo puede ser invocado
	 * por un superusuario
	 * @param idSuper - superusuario
	 * @param id - id administrador
	 * @param justificacion - justificacion
	 * @throws RemoteException
	 * urlServicio: http://localhost:8080/PruebaWs/rest/ServicioUsuario/eliminarAdmin?super=777&id=10189&just=uf fdsnajustificacion
	 */
	@DELETE
	@Path("eliminarAdmin")
	@Produces(MediaType.TEXT_HTML)
	public String eliminarAdmin(@QueryParam("super")int idSuper, @QueryParam("id") int id,
			@QueryParam("just")String justificacion) throws RemoteException{
		try {
			userBl.eliminarAdministrador(idSuper, id, justificacion);
			return "Administrador con id "+id+" eliminado correctamente"; 
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
	}
	/**
	 * Servicio web que permite a un usuario, Administrador o investigador, modificar
	 * algunos de sus datos personales, en caso de no rellenar alguno de los campos, se deja
	 * el que estaba originalmente.
	 * @param idActor
	 * @param iduser
	 * @param pass
	 * @param correo
	 * @param foto
	 * @param telefono
	 * @param direccion
	 * @return Mensaje de operacion exitosa
	 * @throws RemoteException
	 * url ejemplo: http://localhost:8080/PruebaWs/rest/ServicioUsuario/actualizarUser?actor=1039&id=1039&pass=nuevfsf33fsdfapass&correo=micorreito@une.co&foto=mas lindo que nunca&phone=2940090&dir=nueva calle
	 */
	@PUT
	@Path("actualizarUser")
	@Produces(MediaType.TEXT_HTML)
	public String actualizarUser(@QueryParam("actor")int idActor, @QueryParam("id")int iduser,
			@QueryParam("pass")String pass,@QueryParam("correo")String correo,
			@QueryParam("foto")String foto,@QueryParam("phone")String telefono,
			@QueryParam("dir")String direccion) throws RemoteException{
		try {
			userBl.actualizarInformacion(idActor, iduser, pass, correo, foto.getBytes(), telefono, direccion);
			return "Usuario correctamente actualizado";
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	/**
	 * Servicio para FRQ-0023 - Darse de baja logicamente
	 * Permite a usuario investigar eliminarse del sistema. El borrado es lógico no fisico
	 * @param id - id de investigador a eliminar
	 * @return Mensaje de operación exitosa
	 * @throws RemoteException
	 * url ejemplo: http://localhost:8080/PruebaWs/rest/ServicioUsuario/eliminarse?id=1040
	 */
	@DELETE
	@Path("eliminarse")
	@Produces(MediaType.TEXT_HTML)
	public String darseDeBaja(@QueryParam("id")int id) throws RemoteException{
		try {
			userBl.darseDeBajaLogicamenteInvestigador(id);
			return "Usuario " +id+ " eliminado correctamente";
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	/**
	 * Servicio web para RFQ-0024 - Eliminar investigador
	 * Permite a usuario administrador, eliminar un investigador activo en el sistema.
	 * El eliminado es logico.
	 * @param idactor - id de administrador
	 * @param id - id de investigador
	 * @param justificacion - justificacion de decision
	 * @return Mensaje de operacion exitosa
	 * @throws RemoteException
	 * url ejemplo: http://localhost:8080/PruebaWs/rest/ServicioUsuario/eliminarInvestigador?actor=1039&id=1041&just=a simple justificacion
	 */
	@DELETE
	@Path("eliminarInvestigador")
	@Produces(MediaType.TEXT_HTML)
	public String eliminarInvest(@QueryParam("actor")int idactor, @QueryParam("id")int id,
			@QueryParam("just")String justificacion) throws RemoteException{
		try {
			userBl.eliminarInvestigador(idactor, id, justificacion);
			return "Investigador con id "+id+" eliminado correctamente";
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	/**
	 * Servicio web para RFQ-0027 - login
	 * Permite a un usuario loguearse en el sistema, para esto debe ingresar sus 
	 * credenciales y un captcha. Por defecto es n3ur0
	 * @param id - credencial id
	 * @param pass - password
	 * @param captcha - mecanismo contra Robots
	 * @return Mensaje de operacion exitosa
	 * @throws RemoteException
	 * url ejemplo: http://localhost:8080/PruebaWs/rest/ServicioUsuario/login?id=1039&pass=nuevfsf33fsdfapass&captcha=n3ur0
	 */
	@POST
	@Path("login")
	@Produces(MediaType.TEXT_HTML)
	public String login(@QueryParam("id")int id,@QueryParam("pass")String pass,
			@QueryParam("captcha")String captcha) throws RemoteException{
		try {
			if(userBl.login(id, pass, captcha)){
				return "Usuario con id " + id + " registrado exitosamente." ;
			}else{
				return "Un error interno ha ocurrido. Contacte al Administrador.";
			}
		} catch (MyDaoException e) {
			return e.getMessage();
		}
	}
	
	/**
	 * Servicio Web para RFQ-0028 - Cerrar sesión
	 * Este servicio permite el deslogueo de la sesión de un usuario determinado
	 * @param id id del usuario que se encuentra logueado
	 * @return Mensaje de operacion exitosa
	 * @throws RemoteException
	 * url ejemplo: http://localhost:8080/PruebaWs/rest/ServicioUsuario/logout?id=1039
	 */
	@DELETE
	@Path("logout")
	@Produces(MediaType.TEXT_HTML)
	public String logout(@QueryParam("id")int id) throws RemoteException{
		try {
			userBl.cerrarSesion(id);
			return "Sesion de usuario "+id+" cerrada correctamente";
		} catch (MyDaoException e) {
			throw new RemoteException(e.getMessage(),e);
		}
	}
	
	
		
}	
