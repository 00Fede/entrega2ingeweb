/**
 * 
 */
package co.edu.udea.iw.dao.hibernate.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.dao.PeticionDao;
import co.edu.udea.iw.dto.PeticionAcceso;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * @author aux10
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class PeticionDaoHibernateTest {

	@Autowired
	PeticionDao dao;
	/**
	 * Test method for {@link co.edu.udea.iw.dao.hibernate.PeticionDaoHibernate#obtener()}.
	 */
	@Test
	public void testObtener() {
		List<PeticionAcceso> peticiones;
		try{
			peticiones = dao.obtener();
			assertTrue(peticiones.size() > 0);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link co.edu.udea.iw.dao.hibernate.PeticionDaoHibernate#modificar(PeticionAcceso)}.
	 */
	@Test
	public void testModificar() {
		PeticionAcceso peticion = new PeticionAcceso();
		peticion.setCedula(4586);
		peticion.setId(11);
		peticion.setNombre("cristian");
		peticion.setApellido("herrera");
		peticion.setUsuario("cristihe");
		peticion.setContrasena("herreras");
		peticion.setDireccion("direccion2342");
		peticion.setEmail("corre20@correo.com");
		peticion.setTelefono("13124324");
		try {
			assertTrue(dao.modificar(peticion));
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link co.edu.udea.iw.dao.hibernate.PeticionDaoHibernate#crear(co.edu.udea.iw.dto.PeticionAcceso)}.
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@Test
	public void testCrear() throws SerialException, SQLException {
		PeticionAcceso peticion = new PeticionAcceso();
		peticion.setCedula(4586);
		peticion.setNombre("cristina");
		peticion.setApellido("herrera");
		peticion.setUsuario("cristihe");
		peticion.setContrasena("herreras");
		peticion.setDireccion("direccion2342");
		peticion.setEmail("corre20@correo.com");
		String u = "cristi";
		peticion.setFoto(new SerialBlob(u.getBytes()));
		peticion.setTelefono("26783392");
		try {
			assertTrue(dao.crear(peticion));
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testObtenerPeticion(){
		try {
			assertTrue(dao.obtener(1)!=null);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}



}
