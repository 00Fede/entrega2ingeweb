package co.edu.udea.iw.dao.hibernate.test;

import static org.junit.Assert.*;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.dao.DispositivosDao;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * En esta clase realizaremos las pruebas unitarias de nuestro dao
 * DispositivoDao
 * @author andres montoya
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class DispositivoDaoHibernateTest {
	
	@Autowired
	DispositivosDao dao;
	@Test
	public void testObtener() {
		List<Dispositivos> dispositivos;
		
		try{
			dispositivos = dao.obtener();
			assertTrue(dispositivos.size() > 0);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testObtenerInt() {
		Dispositivos dispositivo;
		
		try{
			dispositivo=dao.obtener(2);
			assertTrue(dispositivo != null);
			
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGuardar() throws SerialException, SQLException {
		List<Dispositivos> dispositivo;
		String carro="carros";
		byte[] byteArray =carro.getBytes();
		Blob blob = new SerialBlob(byteArray);
		
		Dispositivos disp=new Dispositivos();
		disp.setNumero_serie(6);
		disp.setNombre("ProtoBoard");
		disp.setModelo("3.02");
		disp.setDescripcion("Bacano");
		disp.setDisponibilidad("Prestamo");
		disp.setEstado("Util");
		disp.setFoto(blob);
		disp.setObservacion("Perfecto estado");
		disp.setRestriccion("none");
			
		
		try{
			dispositivo = dao.obtener();
			int num_user=dispositivo.size();
			dao.guardar(disp);
			dispositivo = dao.obtener();
			int num_user2=dispositivo.size();
			assertTrue(num_user2>num_user);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}		
	}

	@Test
	public void testEliminar() {
		List<Dispositivos> dispositivo;
		try{
			
			dispositivo = dao.obtener();
			int num_user=dispositivo.size();
			dao.eliminar(5);
			dispositivo = dao.obtener();
			int num_user2=dispositivo.size();
			assertTrue(num_user2<num_user);
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}	
	}

	@Test
	public void testModificar() throws SerialException, SQLException {
		
		String carro="carro";
		byte[] byteArray =carro.getBytes();
		Blob blob = new SerialBlob(byteArray);
		Dispositivos disp=new Dispositivos();
		disp.setNumero_serie(3);
		disp.setNombre("ProtoBoard");
		disp.setModelo("3.02");
		disp.setDescripcion("Bacano");
		disp.setDisponibilidad("Prestamo");
		disp.setEstado("Util");
		disp.setFoto(blob);
		disp.setObservacion("Perfecto estado");
		disp.setRestriccion("allfucker");
		
		try{
			dao.modificar(disp);
			Dispositivos resultados=dao.obtener(disp.getNumero_serie());
			assertTrue(resultados.getRestriccion()!=disp.getRestriccion());
		}catch(MyDaoException e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
}
