package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cos = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();

        // catégories
        Categorie c1 = new Categorie("C1", "Informatique");
        cs.create(c1);

        // produits
        Produit p1 = new Produit("ES12",120,c1);
        Produit p2 = new Produit("ZR85",100,c1);
        Produit p3 = new Produit("EE85",200,c1);

        ps.create(p1);
        ps.create(p2);
        ps.create(p3);

        // commande
        Commande cmd = new Commande(new Date());
        cos.create(cmd);

        // lignes commande
        LigneCommandeProduit l1 = new LigneCommandeProduit(7,p1,cmd);
        LigneCommandeProduit l2 = new LigneCommandeProduit(14,p2,cmd);
        LigneCommandeProduit l3 = new LigneCommandeProduit(5,p3,cmd);

        lcs.create(l1);
        lcs.create(l2);
        lcs.create(l3);

        // affichage
        System.out.println("Commande : " + cmd.getId() + "     Date : " + cmd.getDate());

        System.out.println("Liste des produits :");
        System.out.println("Reference   Prix    Quantite");

        List<LigneCommandeProduit> lignes = lcs.findAll();

        for(LigneCommandeProduit l : lignes){

            if(l.getCommande().getId() == cmd.getId()){

                System.out.println(
                        l.getProduit().getReference() + "   "
                                + l.getProduit().getPrix() + " DH   "
                                + l.getQuantite()
                );
            }

        }

    }
}