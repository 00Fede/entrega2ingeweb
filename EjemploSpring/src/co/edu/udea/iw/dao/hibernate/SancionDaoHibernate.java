package co.edu.udea.iw.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.dao.SancionDao;
import co.edu.udea.iw.dto.Reserva;
import co.edu.udea.iw.dto.Sancion;
import co.edu.udea.iw.exception.MyDaoException;

/*
 * @see SancionDao
 * @author andres montoya
 */
@Transactional
public class SancionDaoHibernate implements SancionDao {

	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Sancion> obtener() throws MyDaoException {
		List<Sancion> resultado;
		Session session=null;
		
		try{
			session=sessionFactory.getCurrentSession();
			Criteria crit=session.createCriteria(Sancion.class);
			
			resultado=crit.list();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		return resultado;
	}

	@Override
	public Sancion obtener(int id) throws MyDaoException {
		Session session;
		Sancion resultado=null;
		
		
		try{
			session=sessionFactory.getCurrentSession();
			Criteria crit=session.createCriteria(Sancion.class).add(Restrictions.eq("Id_sancion", id));
			
			resultado=(Sancion)crit.uniqueResult();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}

		return resultado;
	}

	@Override
	public void eliminar(int id) throws MyDaoException {
		Session session=null;
		Sancion sancion=new Sancion();
		sancion.setId_sancion(id);
		
		
		try{
			session=sessionFactory.getCurrentSession();
			session.delete(sancion);
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}

	}

	@Override
	public void guardar(Sancion sancion) throws MyDaoException {
		Session session=null;
		
		try{
			session=sessionFactory.getCurrentSession();
			session.save(sancion);
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}

	}

	@Override
	public void modificar(Sancion sancion) throws MyDaoException {
		Session session = null;



		try {
			session=sessionFactory.getCurrentSession();

			session.update(sancion);

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}


	}

}
