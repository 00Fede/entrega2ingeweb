package co.edu.udea.iw.bl_imp.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.business_logic.ReservaBl;
import co.edu.udea.iw.business_logic.UsuarioBl;
import co.edu.udea.iw.dao.UsuariosDao;
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
	ReservaBl reservaDao;
	
	@Test
	public void testCancelarReserva() throws MyDaoException {
		java.util.Date fechaActual = new java.util.Date();
		reservaDao.cancelarReserva(102,fechaActual);
		
	}

	@Test
	public void testModificarReserva() throws MyDaoException {
		int id=9988;
		int Tiempo=5;
		Usuarios user=new Usuarios();
		user.setCedula(777);
		reservaDao.modificarReserva(id, Tiempo);
	}

}
