/**
 * 
 */
package co.edu.udea.iw.dao.hibernate.test;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.dao.AuthDao;
import co.edu.udea.iw.dto.Autenticacion;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * Esta clase será la encargada de las pruebas unitarias del dao
 * AuthDao
 * @author 
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class AuthDaoHibernateTest {
	
	@Autowired
	AuthDao authDao;

	/**
	 * Test method for {@link co.edu.udea.iw.dao.hibernate.AuthDaoHibernate#obtener()}.
	 */
	@Test
	public void testObtener() {
		Autenticacion auth;
		try{
			auth = authDao.obtener();
			assertTrue(auth!=null);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link co.edu.udea.iw.dao.hibernate.AuthDaoHibernate#guardar(co.edu.udea.iw.dto.Autenticacion)}.
	 */
	@Test
	public void testGuardar() {
		Autenticacion auth = new Autenticacion();
		auth.setId(1);
		auth.setFecha_auth(new Date());
		try {
			authDao.guardar(auth);
			assertTrue(authDao.obtener()!=null);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link co.edu.udea.iw.dao.hibernate.AuthDaoHibernate#eliminar(co.edu.udea.iw.dto.Autenticacion)}.
	 */
	@Test
	public void testEliminar() {
		try{
			authDao.eliminar(authDao.obtener());
			assertTrue(authDao.obtener()==null);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}	
	}

}
