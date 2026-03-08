package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;

import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(Produit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(Produit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public Produit findById(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Produit p = session.get(Produit.class, id);

        session.close();

        return p;
    }

    @Override
    public List<Produit> findAll() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Produit> produits =
                session.createQuery("from Produit").list();

        return produits;
    }

    public List<Produit> findByCategorie(int idCat){

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Produit> produits =
                session.createQuery("from Produit p where p.categorie.id = :id")
                        .setParameter("id", idCat)
                        .list();

        return produits;

    }


    public List<Produit> produitsEntreDates(Date d1, Date d2){

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Produit> produits =
                session.createQuery(
                                "select l.produit from LigneCommandeProduit l where l.commande.date between :d1 and :d2")
                        .setParameter("d1", d1)
                        .setParameter("d2", d2)
                        .list();

        return produits;

    }


    public List<LigneCommandeProduit> produitsParCommande(int idCommande){

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<LigneCommandeProduit> list =
                session.createQuery(
                                "from LigneCommandeProduit l where l.commande.id = :id")
                        .setParameter("id", idCommande)
                        .list();

        return list;

    }

    public List<Produit> prixSup100(){

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Produit> produits =
                session.createNamedQuery("Produit.prixSup").list();

        return produits;

    }

}