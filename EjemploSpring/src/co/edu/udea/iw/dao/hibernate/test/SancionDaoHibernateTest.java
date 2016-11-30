package co.edu.udea.iw.dao.hibernate.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.dao.SancionDao;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Sancion;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * En esta clase realizaremos las pruebas unitarias de nuestro dao
 * SancionDao
 * @author andres montoya
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class SancionDaoHibernateTest {

	@Autowired
	SancionDao dao;
	@Test
	public void testObtener() {
		List<Sancion> sancion;
		
		try{
			sancion = dao.obtener();
			assertTrue(sancion.size() > 0);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testObtenerInt() {
		Sancion sancion;
		
		try{
			sancion=dao.obtener(8899);
			assertTrue(sancion != null);
			
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}



	@Test
	public void testGuardar() {
		List<Sancion> sanciones;
		java.util.Date fechaActual = new java.util.Date(); //Fecha actual del sistema
		Sancion sancion=new Sancion();
		Dispositivos dispositivo=new Dispositivos();
		dispositivo.setNumero_serie(6);
		Usuarios user=new Usuarios();
		user.setCedula(1012);
		sancion.setId_sancion(386);
		sancion.setId_cedula(user);
		sancion.setId_dispositivo(dispositivo);
		sancion.setId_responsable(user);
		sancion.setFecha_inicio(fechaActual);
		sancion.setRazon("oh yeahhh");
		sancion.setTiempo_sancion(1);
		
			
		
		try{
			sanciones = dao.obtener();
			int num_user=sanciones.size();
			dao.guardar(sancion);
			sanciones = dao.obtener();
			int num_user2=sanciones.size();
			assertTrue(num_user2>num_user);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}		
	}
	
	@Test
	public void testEliminar() {
		List<Sancion> sancion;
		try{
			
			sancion = dao.obtener();
			int num_user=sancion.size();
			dao.eliminar(386);
			sancion = dao.obtener();
			int num_user2=sancion.size();
			assertTrue(num_user2<num_user);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}	
	}

	@Test
	public void testModificar() {
		fail("Not yet implemented");
	}

}
