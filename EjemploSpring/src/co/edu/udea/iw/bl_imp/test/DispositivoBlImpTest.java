package co.edu.udea.iw.bl_imp.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.business_logic.DispositivoBl;
import co.edu.udea.iw.dto.Dispositivos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:configuracion.xml"})
public class DispositivoBlImpTest {
	
	@Autowired
	DispositivoBl dispBl;

	@Test
	public void testAgregarDispositivo() {
		try {
			
			String nombre = "lampara";
			String modelo = "19er";
			String restriccion = "restriccion";
			String observacion = "observacion";
			String descripcion = "descripcion";
			String estado = "1";
			String disponibilidad = "disponibilidad";					
			byte[] fotodata = {'a','b'};
			int nroSerie = 888;
			int cedulaResponsable = 1039;
			
			dispBl.agregarDispositivo(cedulaResponsable,nroSerie, nombre, modelo,descripcion, fotodata, restriccion, observacion,estado,disponibilidad);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testVerDispositivosPorModelo() {
		try {
			List<Dispositivos> dispPorModelo;
			dispPorModelo = dispBl.verDispositivosPorModelo();
			assertTrue(dispPorModelo.size() == 3);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testVerDispositivosDisponiblesPorModelo() {
		try {
			List<Dispositivos> dispPorModelo;
			dispPorModelo = dispBl.verDispositivosDisponiblesPorModelo();
			assertTrue(dispPorModelo.size() == 0 );
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEliminarDispositivoLogicamente() {
		try {
			int nroSerie = 777;
			int cedulaResponsable = 1039;
			String justificacion = "el dispositivo se rompio";
			dispBl.eliminarDispositivoLogicamente(cedulaResponsable, nroSerie, justificacion);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
