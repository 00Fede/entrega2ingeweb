package co.edu.udea.iw.bl_imp.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.business_logic.DispositivoBl;
import co.edu.udea.iw.dto.Dispositivos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:configuracion.xml" })
public class DispositivoBlImpTest {

	@Autowired
	DispositivoBl dispBl;

	@Test
	public void testAgregarDispositivo() {
		try {

			String nombre = "mouse";
			String modelo = "genius";
			String restriccion = "restriccion";
			String observacion = "observacion";
			String descripcion = "descripcion";
			String estado = "0";
			String disponibilidad = "disponibilidad";
			byte[] fotodata = { 'c', 'd' };
			int nroSerie = 111;
			int cedulaResponsable = 1010;

			dispBl.agregarDispositivo(cedulaResponsable, nroSerie, nombre, modelo, descripcion, fotodata, restriccion,
					observacion, estado, disponibilidad);
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
			assertTrue(dispPorModelo.size() == 0);
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

	@Test
	public void testmodificarDispositivo() {
		try {
			int nroSerie = 222;
			int cedulaResponsable = 1039;
			String nombre = "lampara  2";
			String modelo = "19er";
			String restriccion = "restriccion";
			String observacion = "No dejar caer";
			String peqDesc = "descripcion";
			String estado = "1";
			String disponibilidad = "disponibilidad";
			byte[] fotoRAW = { 'a', 'c' };
			String justificacion = "el dispositivo se rompio";
			dispBl.modificarDispositivo(cedulaResponsable, nroSerie, nombre, modelo, peqDesc, fotoRAW, restriccion,
					observacion, estado, disponibilidad);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testRealizarPrestamoDispositivo() {
		try {
			int nroSerie = 222;
			int cedulaResponsable = 1039;
			int cedulaI = 1020;
			java.util.Date fechaInicio = new java.util.Date();
			int duracionhoras = 6;
			int idReserva = 45;
            dispBl.realizarPrestamoDispositivo(cedulaResponsable, cedulaI, nroSerie, (Date) fechaInicio, duracionhoras, idReserva);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
