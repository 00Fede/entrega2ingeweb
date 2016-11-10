/**
 * 
 */
package co.edu.udea.iw.dao.hibernate;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import co.edu.udea.iw.dao.PeticionDao;
import co.edu.udea.iw.dto.PeticionAcceso;
import co.edu.udea.iw.dto.Usuarios;
import co.edu.udea.iw.exception.MyDaoException;

/**
 * @author Federico
 * Implementacion de la interfaz PeticionDao
 * @see PeticionDao
 */
public class PeticionDaoHibernate implements PeticionDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	/* (non-Javadoc)
	 * @see co.edu.udea.iw.dao.PeticionDao#obtener()
	 */
	@Override
	public List<PeticionAcceso> obtener() throws MyDaoException {
		List<PeticionAcceso> peticiones = null;
		Session session = null;
		
		try {
			session=sessionFactory.openSession();
			Criteria crit=session.createCriteria(PeticionAcceso.class);
			peticiones=crit.list();
		} catch (HibernateException e) {
			throw new MyDaoException(e);
		}
		
		return peticiones;
	}

	/* (non-Javadoc)
	 * @see co.edu.udea.iw.dao.PeticionDao#evaluar(int, java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean evaluar(int idPeticion, String estado, Usuarios admin, String justificacion) throws MyDaoException {
		Session session = null;
		Transaction tx = null;
		PeticionAcceso peticionEvaluada;
		
		try {
			session = sessionFactory.openSession();
			Criteria crit=session.createCriteria(PeticionAcceso.class).add(Restrictions.eq("id", idPeticion));
			peticionEvaluada=(PeticionAcceso)crit.uniqueResult();
			peticionEvaluada.setEstado(estado);
			peticionEvaluada.setAdmin(admin);
			peticionEvaluada.setJustificacion(justificacion);
			tx = session.beginTransaction();
			session.update(peticionEvaluada);
			tx.commit();
			
		} catch (HibernateException e) {
			throw new MyDaoException(e);
		}
		return true;
		
	}

	/* (non-Javadoc)
	 * @see co.edu.udea.iw.dao.PeticionDao#crear(co.edu.udea.iw.dto.PeticionAcceso)
	 */
	@Override
	public boolean crear(PeticionAcceso peticion) throws MyDaoException {
		Session session = null;
		Transaction tx=null;


		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(peticion);
			tx.commit();

		} catch (HibernateException e) {
			throw new MyDaoException(e);

		}
		return true;
	}

}
