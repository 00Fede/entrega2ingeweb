/**
 * 
 */
package co.edu.udea.iw.bl_imp.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.business_logic.PeticionBl;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * @author Federico
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class PeticionBlImpTest {

	@Autowired
	PeticionBl peticionBl;
	
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
	 * Test method for {@link co.edu.udea.iw.bl_imp.PeticionBlImp#crearPeticionDeAcceso(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, byte[], java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCrearPeticionDeAcceso() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link co.edu.udea.iw.bl_imp.PeticionBlImp#evaluarPeticionDeAcceso(int, java.lang.String, int, java.lang.String)}.
	 */
	@Test
	public void testEvaluarPeticionDeAcceso() {
	}

}
