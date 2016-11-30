package co.udea.edu.iw.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/*
 * Claseque respondera las peticiones web para la
 * funcionalidad de saludar. Ejercicio de clase
 * @author andres montoya
 */


@Path("saludo")
public class ServicioSaludo {

	/*
	 * Metodo sencillo para el saludo, primer WS
	 */
	@GET
	@Produces("text/html")
	public String saludar(){
		return "Buenas tardes";
	}
	/*
	 * Metodo para retornar un String con un parametro pasado de la forma /saludo/{parametro}
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("Hola/{nombre}")
	public String digameHola(@PathParam("nombre")String nombre){
		return "hola" +nombre;
	}
	
	/*
	 * Formas de enviar parametros por los servicios web
	 * 1-Path Param-->pasan como tipo path <path url>/servicio/<valor>
	 * 2-QueryParam-->Pasan como tipo query
	 * 3-Form Param-->Se pasan como formularios 
	 */
	
	/*
	 * Metodo creado para retornar un string al cual se le envian parametros de la 
	 * siguiente forma /Hola?nombre=xxx&apellido=zzzz
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("Hola")
	public String digameHolaQuery(@QueryParam("nombre")String nombre,
			@QueryParam("apellido")String apellido){
		return "hola " +nombre+" "+apellido;
	}
	
}
