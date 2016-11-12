package co.edu.udea.iw.dao.hibernate;

import java.util.List;



import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import co.edu.udea.iw.dao.ReservaDao;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.exception.MyDaoException;

/*
 * @see ReservaDao
 * @author andres montoya
 */
public class ReservaDaoHibernate implements ReservaDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Reserva> obtener() throws MyDaoException {
		List<Reserva> resultado;
		Session session=null;
		
		try{
			session=sessionFactory.openSession();
			Criteria crit=session.createCriteria(Reserva.class);
			
			resultado=crit.list();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		return resultado;
	}

	@Override
	public Reserva obtener(int id) throws MyDaoException {
		
		Session session;
		Reserva resultado=null;
		
		
		try{
			session=sessionFactory.openSession();
			Criteria crit=session.createCriteria(Reserva.class).add(Restrictions.eq("Id_reserva", id));
			
			resultado=(Reserva)crit.uniqueResult();
			session.close();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}

		return resultado;
	}

	@Override
	public void guardar(Reserva reserva) throws MyDaoException {
		
		Session session=null;
		Transaction tx = null;
		
		try{
			session=sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(reserva);
			tx.commit();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		
		

	}

	@Override
	public void eliminar(int id) throws MyDaoException {

		
		Session session=null;
		Reserva reserva=new Reserva();
		reserva.setId_reserva(id);
		
		try{
			session=sessionFactory.openSession();
			session.delete(reserva);
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
	}

	@Override
	public void modificar(Reserva reserva) throws MyDaoException {
		Session session = null;
		Transaction tx = null;


		try {
			
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			session.update(reserva);
			tx.commit();
			

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}


	}

}
