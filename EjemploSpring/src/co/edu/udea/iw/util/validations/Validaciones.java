package co.edu.udea.iw.util.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * Clase para validaciones en general del sistema
 * @author Elver Su�rez Alzate
 */
public class Validaciones {
	
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Valida que el correo electr�nico establecido como par�metro sea un correo electr�nico con formato v�lido
	 * @param correo texto con el correo electr�nico a validar
	 * @return true si el texto tiene un formato de correo electr�nico v�lido, de lo contrario retorna false
	 */
	public static boolean isEmail(String email) {
		 
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }
    
    /**
     * Valida que el texto ingresado sea un texto considerado vacio, para ser considerado vacio debe ser nulo o una cadena de caracteres
     * vacia
     * @param texto texto a validar
     * @return true, si el texto ingresado es nulo o vacio, de lo contrario false
     */
    public static boolean isTextoVacio(String texto){
    	if(null == texto)
    		return true;
    	texto = texto.trim();
    	if("".equals(texto)){
    		return true;
    	}    		
    	return false;
    }
    

	
}
