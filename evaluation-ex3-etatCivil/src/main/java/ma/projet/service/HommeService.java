package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme h) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(h);
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
    public boolean delete(Homme h) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(h);
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
    public boolean update(Homme h) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(h);
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
    public Homme getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Homme.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Homme> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Homme", Homme.class).list();
        } finally {
            session.close();
        }
    }

    // Épouses d'un homme entre deux dates
    public List<Femme> getEpousesEntreDeuxDates(int hommeId, Date debut, Date fin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery(
                    "SELECT m.femme FROM Mariage m " +
                            "WHERE m.homme.id = :id " +
                            "AND m.dateDebut >= :debut " +
                            "AND m.dateDebut <= :fin",
                    Femme.class
            );
            query.setParameter("id", hommeId);
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Afficher tous les mariages d'un homme avec détails
    public void afficherMariagesHomme(int hommeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme non trouvé !");
                return;
            }

            Query<Mariage> query = session.createQuery(
                    "FROM Mariage m WHERE m.homme.id = :id ORDER BY m.dateDebut",
                    Mariage.class
            );
            query.setParameter("id", hommeId);
            List<Mariage> mariages = query.list();

            System.out.println("Nom : " + homme.getNom().toUpperCase()
                    + " " + homme.getPrenom().toUpperCase());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Séparer mariages en cours et échoués
            List<Mariage> enCours = new ArrayList<>();
            List<Mariage> ecoues = new ArrayList<>();
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) enCours.add(m);
                else ecoues.add(m);
            }

            System.out.println("Mariages En Cours :");
            int i = 1;
            for (Mariage m : enCours) {
                System.out.printf("%d. Femme : %-15s Date Début : %s    Nbr Enfants : %d%n",
                        i++,
                        m.getFemme().getNom().toUpperCase() + " " + m.getFemme().getPrenom().toUpperCase(),
                        sdf.format(m.getDateDebut()),
                        m.getNbrEnfant()
                );
            }

            System.out.println("\nMariages échoués :");
            i = 1;
            for (Mariage m : ecoues) {
                System.out.printf("%d. Femme : %-15s Date Début : %s%n",
                        i++,
                        m.getFemme().getNom().toUpperCase() + " " + m.getFemme().getPrenom().toUpperCase(),
                        sdf.format(m.getDateDebut())
                );
                System.out.printf("   Date Fin : %s    Nbr Enfants : %d%n",
                        sdf.format(m.getDateFin()),
                        m.getNbrEnfant()
                );
            }

        } finally {
            session.close();
        }
    }

    // Hommes mariés à exactement 4 femmes entre deux dates (Criteria API)
    public List<Homme> getHommesMariesAQuatreFemmes(Date debut, Date fin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // On utilise HQL ici car Criteria pour COUNT+GROUP BY est plus complexe
            Query<Homme> query = session.createQuery(
                    "SELECT m.homme FROM Mariage m " +
                            "WHERE m.dateDebut >= :debut AND m.dateDebut <= :fin " +
                            "GROUP BY m.homme " +
                            "HAVING COUNT(m.femme) = 4",
                    Homme.class
            );
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);
            return query.list();
        } finally {
            session.close();
        }
    }
}
