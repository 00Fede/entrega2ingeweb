/**
 * 
 */
package co.edu.udea.iw.bl_imp.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.business_logic.PeticionBl;
import co.edu.udea.iw.dao.PeticionDao;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * @author Federico Ocampo. cc: 1039464102. federico.ocampoo@udea.edu.co
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class PeticionBlImpTest {

	@Autowired
	PeticionBl peticionBl;
	@Autowired
	PeticionDao peticionDao;
	
	/**
	 * Test method for {@link co.edu.udea.iw.bl_imp.PeticionBlImp#verPeticionesDeAcceso()}.
	 */
	@Test
	public void testVerPeticionesDeAcceso() {
		try {
			assertTrue(peticionBl.verPeticionesDeAcceso()!=null);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link co.edu.udea.iw.bl_imp.PeticionBlImp#crearPeticionDeAcceso
	 * (int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, byte[], 
	 * java.lang.String, java.lang.String)}.
	 * @throws SQLException 
	 * @throws MyDaoException 
	 * @throws SerialException 
	 */
	@Test
	public void testCrearPeticionDeAcceso() throws SerialException,SQLException {
		int cedula = 999;
		String nombre = "Tatiana";
		String apellido = "Bernal";
		String contrasena = "contrasena";
		String telefono = "2193939";
		String direccion = "calle rota";
		String email = "correo999@correo.com";
		String usuario = "tabernal";
		
		try {
			peticionBl.crearPeticionDeAcceso(cedula, usuario, nombre, apellido, 
					contrasena, email, null, telefono, direccion);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	/**
	 * Test method for {@link co.edu.udea.iw.bl_imp.PeticionBlImp#evaluarPeticionDeAcceso
	 * (int, java.lang.String, int, java.lang.String)}.
	 */
	@Test
	public void testEvaluarPeticionDeAcceso() {
		int idPeticion = 13;
		String estado = "rechazado";
		int admin = 1039;
		String justificacion = "Hola justificacion";
		
		try{
			peticionBl.evaluarPeticionDeAcceso(idPeticion, estado, admin, justificacion);
			assertTrue(peticionDao.obtener(idPeticion).getEstado().equals(estado));
		}catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
