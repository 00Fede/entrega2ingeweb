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
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.PeticionAcceso;
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
	@Autowired
	UsuariosDao userDao;
	
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
		int cedula = 1039464100;
		String nombre = "Mauricio";
		String apellido = "Quintero";
		String contrasena = "123456";
		String telefono = "2773632";
		String direccion = "alguna direccion";
		String email = "mauricioinvest@gmail.com";
		String usuario = "mauricioq";
		
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
		int idPeticion = 11;
		String estado = PeticionAcceso.USUARIO_APROBADO;
		int admin = 1039;
		String justificacion = "Mauricio es investigador";
		
		try{
			peticionBl.evaluarPeticionDeAcceso(idPeticion, estado, admin, justificacion);
			PeticionAcceso peticionEvaluada = peticionDao.obtener(idPeticion);
			assertTrue(peticionEvaluada.getEstado().equals(estado) &&
					userDao.obtener(peticionEvaluada.getCedula())!=null);
		}catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
