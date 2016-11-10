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
			
			String nombre = "disp 1";
			String modelo = "modelo1";
			String restriccion = "restriccion";
			String observacion = "observacion";
			String descripcion = "descripcion";
			String estado = "estado";
			String disponibilidad = "disponibilidad";					
			byte[] fotodata = {'a','b'};
			int nroSerie = 123;
			
			dispBl.agregarDispositivo(nroSerie, nombre, modelo,descripcion, fotodata, restriccion, observacion,estado,disponibilidad);
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
			assertTrue(dispPorModelo.size() >= 2 );
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
