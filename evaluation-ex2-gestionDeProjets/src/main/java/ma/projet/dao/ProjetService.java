package ma.projet.dao;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Projet getById(int id) {
        Session session = null;
        Projet projet = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            projet = (Projet) session.get(Projet.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return projet;
    }

    @Override
    public List<Projet> getAll() {
        Session session = null;
        List<Projet> projets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            projets = session.createQuery("from Projet").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return projets;
    }

    @Override
    public boolean update(Projet o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean delete(Projet o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public void afficherTachesRealisees(Projet p) {
        if (p == null) {
            System.out.println("Projet introuvable.");
            return;
        }

        System.out.println("Projet : " + p.getId() + "      Nom : " + p.getNom() + "     Date début : " + p.getDateDebut());
        System.out.println("Liste des tâches:");
        System.out.println("Num  Nom            Date Début Réelle   Date Fin Réelle");

        // On parcourt les tâches du projet
        if (p.getTaches() != null) {
            p.getTaches().forEach(tache -> {
                // Pour chaque tâche, on regarde dans la table associative EmployeTache
                if (tache.getEmployeTaches() != null) {
                    for (EmployeTache et : tache.getEmployeTaches()) {
                        System.out.printf("%-4d %-14s %-18s %-18s%n",
                                tache.getId(),
                                tache.getNom(),
                                et.getDateDebutReelle(),
                                et.getDateFinReelle());
                    }
                }
            });
        }
    }
}
