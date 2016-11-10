package co.edu.udea.iw.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.dao.DispositivosDao;
import co.edu.udea.iw.dto.Dispositivos;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/*
 * @see DispositivosDao
 * @author andres montoya
 */
@Transactional
public class DispositivoDaoHibernate implements DispositivosDao {

	
private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	public List<Dispositivos> obtener() throws MyDaoException {
		
		Session session=null;
		List<Dispositivos> resultado=null;
	

		try{
			session=sessionFactory.openSession();
			
			Criteria crit=session.createCriteria(Dispositivos.class);
			
			resultado=crit.list();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		
		return resultado;
		

	}
	
	@Override
	public List<Dispositivos> obtenerDisponiblesPorModelo() throws MyDaoException {
		
		Session session=null;
		List<Dispositivos> resultado=null;
	

		try{
			session=sessionFactory.openSession();
			
			//Criteria crit=session.createCriteria(Dispositivos.class).setProjection(Projections.projectionList().add( Property.forName("Disponibilidad").as("1")).add(Projections.groupProperty("Modelo")));
			
			//resultado=crit.list();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		
		return resultado;
	}

	@Override
	public Dispositivos obtener(int no_serie) throws MyDaoException {

		Session session;
		Dispositivos resultado=null;
		
		try{
		session=sessionFactory.openSession();
		
		Criteria crit=session.createCriteria(Dispositivos.class).add(Restrictions.eq("Numero_serie", no_serie));
		resultado=(Dispositivos)crit.uniqueResult();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		return resultado;
	}

	@Override
	public void guardar(Dispositivos dispositivo) throws MyDaoException {
		Session session = null;
		Transaction tx = null;


		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(dispositivo);
			tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}

	}

	@Override
	public void eliminar(int no_serie) throws MyDaoException {
		Session session = null;
		Transaction tx = null;
		Dispositivos dispositivo = new Dispositivos();
		dispositivo.setNumero_serie(no_serie);


		try {
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.delete(dispositivo);
			tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}

	}

	

	@Override
	public void modificar(Dispositivos dispositivo) throws MyDaoException {
		Session session = null;
		Transaction tx = null;


		try {
			session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			session.update(dispositivo);
			//tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}

	}

	

}
