package ma.projet.dao;


import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
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
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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

    @Override
    public Employe getById(int id) {
        Session session = null;
        Employe employe = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employe = (Employe) session.get(Employe.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employe;
    }

    @Override
    public List<Employe> getAll() {
        Session session = null;
        List<Employe> employes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employes = session.createQuery("from Employe").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employes;
    }

    // les methodes demandes
     // Affiche la liste des tâches réalisées par un employé.
    public void afficherTachesRealisees(Employe e) {
        System.out.println("Tâches réalisées par l'employé : " + e.getNom() + " " + e.getPrenom());
        if (e.getEmployeTaches() == null || e.getEmployeTaches().isEmpty()) {
            System.out.println("Aucune tâche réalisée.");
        } else {
            for (EmployeTache et : e.getEmployeTaches()) {
                System.out.println("- " + et.getTache().getNom() + " (Début réel : " + et.getDateDebutReelle() + ")");
            }
        }
    }


     //Affiche la liste des projets gérés par un employé (en tant que chef de projet)
    public void afficherProjetsGeres(Employe e) {
        System.out.println("Projets gérés par : " + e.getNom() + " " + e.getPrenom());
        if (e.getProjets() == null || e.getProjets().isEmpty()) {
            System.out.println("Cet employé ne gère aucun projet.");
        } else {
            for (Projet p : e.getProjets()) {
                System.out.println("- " + p.getNom() + " (ID: " + p.getId() + ")");
            }
        }
    }
}