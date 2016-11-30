package co.edu.udea.iw.bl_imp.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.business_logic.ReservaBl;
import co.edu.udea.iw.business_logic.SancionBl;
import co.edu.udea.iw.business_logic.UsuarioBl;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.exception.MyDaoException;


/**
 * Esta clase será la encargada de hacer las pruebas unitarias de los metodos 
 * de la logicas del negocio relacionados con la entidad Sancion (SancionBl)
 * @author andres montoya
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class SancionBlImpTest {
	
	@Autowired
	SancionBl sancionDao;
	UsuarioBl userBl;
	UsuarioBl usuariosBl;

	@Test
	public void testGenerarSancion_limiteTiempo() {
		java.util.Date fechaActual = new java.util.Date();
		int id_reserva=112;
		int id_sancion=11;
		try {
			sancionDao.generarSancion_limiteTiempo(id_sancion, id_reserva, fechaActual);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	@Test
	public void testGenerarSancion_estadoNoAdecuado() {
		int id_sancion=13;
		int id_reserva=9999;
		try{
			sancionDao.generarSancion_estadoNoAdecuado(id_sancion, id_reserva);
			
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	
	}

	@Test
	public void testGenerarSancion_incumplimientoReserva() {
		java.util.Date fechaActual = new java.util.Date();
		int id_reserva=112;
		int id_sancion=4;
		try {
			sancionDao.generarSancion_incumplimientoReserva(id_sancion, id_reserva, fechaActual);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		//fail("Not yet implemented");
	}

	@Test
	public void testGenerarSancion_cancelacionInoportuna() {
		
		int id_reserva=9;
		int id_sancion=5;
		try {
			sancionDao.generarSancion_cancelacionInoportuna(id_sancion, id_reserva);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		//fail("Not yet implemented");
	}

	@Test
	public void testGenerarSancion_extravio() {
		int id_reserva=91;
		int id_sancion=10;
		try {
			sancionDao.generarSancion_extravio(id_sancion, id_reserva);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		//fail("Not yet implemented");
	}

	@Test
	public void testModificarSancion() {
		int id_administrador=10189;
		int id_sancion=10;
		String razon="multa";
		try {
			sancionDao.modificarSancion(id_sancion, id_administrador, razon);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		//fail("Not yet implemented");
		
	}

	@Test
	public void testRetirarSancion() {
		int id_administrador=10189;
		int id_sancion=2;
		try {
			sancionDao.retirarSancion(id_sancion, id_administrador);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testRetirarSancion_tiempoCulminado() {
		java.util.Date fechaActual = new java.util.Date();
		int id_sancion=8899;
		try {
			sancionDao.retirarSancion_tiempoCulminado(id_sancion, fechaActual);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
