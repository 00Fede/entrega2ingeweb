package co.edu.udea.iw.bl_imp.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.business_logic.ReservaBl;
import co.edu.udea.iw.business_logic.UsuarioBl;
import co.edu.udea.iw.dao.ReservaDao;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;


/**
 * Esta clase será la encargada de hacer las pruebas unitarias de los metodos
 * de la logica del negocio de la entidad Reserva --->ReservaBl
 * @author aux10
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class ReservaBlImpTest {

	@Autowired
	ReservaBl reservaBl;
	@Autowired
	ReservaDao dao;
	
	@Test
	public void testCancelarReserva() throws MyDaoException {
		java.util.Date fechaActual = new java.util.Date();
		reservaBl.cancelarReserva(102,fechaActual);
		
	}

	@Test
	public void testModificarReserva() throws MyDaoException {
		int id=9988;
		int Tiempo=5;
		Usuarios user=new Usuarios();
		user.setCedula(777);
		reservaBl.modificarReserva(id, Tiempo);
	}
	
	@Test
	public void testVerReservasPorInvest() throws MyDaoException{
		int idInvest = 1040;
		int idResponsable = 1039;
		List<Reserva> resultado;
		try{
			resultado = reservaBl.verReservasPorInvest(idInvest, idResponsable);
			assertTrue(resultado.size()>0);
		}catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void testNotificarDevolucion() throws MyDaoException{
		int idPrestamo = 9988;
		int idAdmin = 1039;
		int estado = 2;
		
		try {
			reservaBl.notificarDevolucion(idPrestamo, idAdmin, estado);
			Reserva prestamoNotificado = dao.obtener(idPrestamo);
			assertTrue(prestamoNotificado.getEstado()==estado);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testRealizarPrestamo() throws MyDaoException{
		
		int idReserva = 3;
		try {
			reservaBl.hacerPrestamo(idReserva);
			assertTrue(dao.obtener(idReserva).getEstado()==1);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testprestamosPorDispositivos() throws MyDaoException{
		int nroSerie = 333;
		int idResponsable = 1039;
		List<Reserva> resultado;
		try{
			resultado = reservaBl.prestamosPorDispositivos(nroSerie, idResponsable);
			assertTrue(resultado.size()==1);
		}catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testCrearReserva() throws MyDaoException, ParseException{
		int idInv = 1040;
		int idDev = 333;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaEntrega = formatter.parse("2016-11-27");
		
		int tiempoPrestamo = 24;
		
		try {
			reservaBl.crearReserva(idInv, idDev, tiempoPrestamo, fechaEntrega);
			Iterator<Reserva> i = dao.obtener().iterator();
			while(i.hasNext()){
				if (i.next().getId_cedula().getCedula()==idInv) assertTrue(true);
			}
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
