package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme f) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(f);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Femme f) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(f);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Femme f) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(f);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Femme getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Femme.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Femme> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Femme", Femme.class).list();
        } finally {
            session.close();
        }
    }

    // Femme la plus âgée
    public Femme getFemmeLaPlusAgee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery(
                    "FROM Femme f ORDER BY f.dateNaissance ASC", Femme.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    // Requête native nommée : nbr d'enfants d'une femme entre deux dates
    public int getNbrEnfantsEntreDeuxDates(int femmeId, Date debut, Date fin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createNamedQuery("Femme.nbrEnfantsEntreDeuxDates");
            query.setParameter("femmeId", femmeId);
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);
            Object result = query.uniqueResult();
            return result != null ? ((Number) result).intValue() : 0;
        } finally {
            session.close();
        }
    }

    //  Requête nommée HQL : femmes mariées au moins 2 fois
    public List<Femme> getFemmesMarieesDeuxFoisOuPlus() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createNamedQuery("Femme.marieeDeuxFoisOuPlus", Femme.class).list();
        } finally {
            session.close();
        }
    }
}
