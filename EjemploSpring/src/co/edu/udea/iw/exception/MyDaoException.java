package co.edu.udea.iw.exception;

import org.apache.log4j.Logger;

/**
 * Esta clase tendrá la funcion de manejar las excepciones de nuestro
 * programa
 * @author andres montoya
 *
 */

public class MyDaoException extends Exception {
	
	Logger log = Logger.getLogger(this.getClass());

	public MyDaoException(String message, Throwable cause) {
		super(message, cause);
		log.error(message);
	}

	public MyDaoException(Throwable cause) {
		super(cause);
		log.error(cause.getMessage());
	}
	
	

}
