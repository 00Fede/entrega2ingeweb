package co.edu.udea.iw.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.dao.UsuariosDao;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * see UsuariosDao
 * @author andres montoya
 */
public class UsuarioDaoHibernate implements UsuariosDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public List<Usuarios> obtener() throws MyDaoException {
		
		Session session=null;
		List<Usuarios> resultado=null;
	

		try{
			session=sessionFactory.openSession();
			
			Criteria crit=session.createCriteria(Usuarios.class);
			
			resultado=crit.list();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		
		return resultado;
	}

	@Override
	public Usuarios obtener(int cedula) throws MyDaoException {
		
		Session session;
		Usuarios resultado=null;
		
		try{
		session=sessionFactory.openSession();
		
		Criteria crit=session.createCriteria(Usuarios.class).add(Restrictions.eq("cedula", cedula));
		resultado=(Usuarios)crit.uniqueResult();
			
		}catch(HibernateException e){
			throw new MyDaoException(e);
		}
		return resultado;
	}

	@Override
	public void guardar(Usuarios usuario) throws MyDaoException {
		Session session = null;
		Transaction tx=null;


		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(usuario);
			tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}

	}

	@Override
	public void modificar(Usuarios usuario) throws MyDaoException {
		Session session = null;
		Transaction tx = null;
		

		try {
			session = session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//Actualiza el objeto ciudad en la base de datos
			session.update(usuario);
			tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}

	}

	@Override
	public void eliminar(int cedula) throws MyDaoException {
		Session session = null;
		Usuarios usuario = new Usuarios();
		Transaction tx = null;
		usuario.setCedula(cedula);


		try {
			
			session = sessionFactory.openSession();
			//elimina el objeto ciudad en la base de datos
			//Solo busca por clave primaria.
			tx = session.beginTransaction();
			session.delete(usuario);
			tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}

	}

}
