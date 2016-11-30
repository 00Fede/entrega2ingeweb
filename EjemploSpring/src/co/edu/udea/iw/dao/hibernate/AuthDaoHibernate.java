/**
 * 
 */
package co.edu.udea.iw.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import co.edu.udea.iw.dao.AuthDao;
import co.edu.udea.iw.dto.Autenticacion;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * @see AuthDao
 * @author aux10
 */
public class AuthDaoHibernate implements AuthDao {

	private SessionFactory sessionFactory;


	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.udea.iw.dao.AuthDao#obtener()
	 */
	@Override
	public Autenticacion obtener() throws MyDaoException {
		Session session=null;
		Autenticacion resultado=null;
		try{
			session=sessionFactory.openSession();
			
			Criteria crit=session.createCriteria(Autenticacion.class);
			
			resultado= (Autenticacion) crit.uniqueResult();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.udea.iw.dao.AuthDao#guardar(co.edu.udea.iw.dto.Autenticacion)
	 */
	@Override
	public void guardar(Autenticacion auth) throws MyDaoException {
		Session session = null;
		Transaction tx=null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(auth);
			tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.udea.iw.dao.AuthDao#eliminar(co.edu.udea.iw.dto.Autenticacion)
	 */
	@Override
	public void eliminar(Autenticacion auth) throws MyDaoException {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.delete(auth);
			tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}
	}

}
