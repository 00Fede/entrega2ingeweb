package co.edu.udea.iw.bl_imp.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udea.iw.business_logic.UsuarioBl;
import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:configuracion.xml"})
public class UsuarioBlImpTest {

	@Autowired
	UsuarioBl userBl;
	UsuariosDao userDao;
	
	@Test
	public void testRegistrarAdministrador() throws SerialException, SQLException {
		int cedula = 111;
		int superuserid = 777;
		String name = "Arturo";
		String lastname = "Vidal";
		String nombreUsuario = "fedonf";
		String contrasena = "123456";
		String direccion = "direccion1";
		String correo = "correo@correo.com";
		byte[] fotoRAW = "usuario".getBytes();
		String telefono = "2678392";
		try {
			userBl.registrarAdministrador(superuserid, cedula, name, lastname, correo, nombreUsuario, 
					contrasena, fotoRAW, telefono, direccion);
		} catch (MyDaoException  e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEliminarAdministrador(){
		int idResponsable = 777;
		int idUsuario = 2230;
		String justificacion = "bla bla bla";
		
		try {
			userBl.eliminarAdministrador(idResponsable, idUsuario, justificacion);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLogin(){
		int cedula = 10189;
		String contrasena = "gueuhuig";
		String captcha = "n3ur0";
		
		try {
			assertTrue(userBl.login(cedula, contrasena,captcha));
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	@Test
	public void testCerrarSesion(){
		int cedula = 1039;
		try {
			userBl.cerrarSesion(cedula);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	@Test
	public void testDarseDeBajaLogicamente(){
		try {
			userBl.darseDeBajaLogicamenteInvestigador(1010);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	@Test
	public void testEliminarInvestigador(){
		int idResponsable = 1020;
		int idUsuario = 1010;
		String justificacion = "justificacion si";
		try {
			userBl.eliminarInvestigador(idResponsable, idUsuario, justificacion);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testListarInvestigador(){
		int idResponsable = 1020;
		try {
			assertTrue(userBl.listarInvestigadores(idResponsable)!=null);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
				
	}
	@Test
	public void testBloquear(){
		int id_user=1012;
		int id_administrador=10189;
		int action=1;
		try {
			userBl.bloquear(id_user, id_administrador, action);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
				
	}
	
	@Test
	public void testActualizarUser(){
		int idactor = 1020;
		int id = 1020;
		String correo = "nuevocorreito@une.co";
		try {
			userBl.actualizarInformacion(idactor, id, null, correo, null, null, null);
		} catch (MyDaoException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	

}
