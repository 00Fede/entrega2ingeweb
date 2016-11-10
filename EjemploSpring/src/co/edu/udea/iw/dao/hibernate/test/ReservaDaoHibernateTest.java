package co.edu.udea.iw.dao.hibernate.test;

import static org.junit.Assert.*;

import java.sql.Blob;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.HibernateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.dao.ReservaDao;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * En estaclase realizamos las pruebas unitarias de nuestro Dao
 * ReservaDao
 * @author andres montoya
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class ReservaDaoHibernateTest {

	@Autowired
	ReservaDao dao;
	@Test
	public void testObtener() {
		List<Reserva> reserva;
		
		try{
			reserva = dao.obtener();
			assertTrue(reserva.size() > 0);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testObtenerInt() {
		Reserva reserva;
		
		try{
			reserva=dao.obtener(9988);
			assertTrue(reserva != null);
			
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGuardar() {
		List<Reserva> reserva;
		java.util.Date fechaActual = new java.util.Date(); //Fecha actual del sistema
		Reserva reservas=new Reserva();
		Dispositivos dispositivo=new Dispositivos();
		dispositivo.setNumero_serie(6);
		Usuarios user=new Usuarios();
		user.setCedula(1012);
		reservas.setId_reserva(112);
		reservas.setId_dispositivo(dispositivo);
		reservas.setId_cedula(user);
		reservas.setId_responsable(user);
		reservas.setFecha_inicio(fechaActual);
		reservas.setTiempo_reserva(5);
		reservas.setEstado(0);
		
		try{
			reserva = dao.obtener();
			int num_user=reserva.size();
			dao.guardar(reservas);
			reserva = dao.obtener();
			int num_user2=reserva.size();
			assertTrue(num_user2>num_user);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}		
	}

	@Test
	public void testEliminar() {
		List<Reserva> reserva;
		try{
			
			reserva = dao.obtener();
			int num_user=reserva.size();
			dao.eliminar(102);
			reserva = dao.obtener();
			int num_user2=reserva.size();
			assertTrue(num_user2<num_user);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}	
	}

	@Test
	public void testModificar() {
		java.util.Date fechaActual = new java.util.Date(); //Fecha actual del sistema
		Reserva reservas=new Reserva();
		Dispositivos dispositivo=new Dispositivos();
		dispositivo.setNumero_serie(1);
		Usuarios user=new Usuarios();
		user.setCedula(9);
		reservas.setId_reserva(9988);
		reservas.setId_dispositivo(dispositivo);
		reservas.setId_cedula(user);
		reservas.setId_responsable(user);
		reservas.setFecha_inicio(fechaActual);
		reservas.setTiempo_reserva(4);
		reservas.setEstado(0);
		try{
			dao.modificar(reservas);
			Reserva resultados=dao.obtener(reservas.getId_reserva());
			assertTrue(resultados.getTiempo_reserva()!=reservas.getTiempo_reserva());
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
