package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.dao.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Test {
    public static void main(String[] args) {
        // 1. Instanciation des services
        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // 2. Création d'un employé (Chef de projet)
            Employe emp1 = new Employe("Alami", "Sami", "0612345678");
            es.create(emp1);

            // 3. Création d'un projet
            //le constructeur est : Projet(String nom, Date debut, Date fin, Employe chef)
            Projet proj1 = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("31/12/2013"), emp1);
            ps.create(proj1);

            // 4. Création des tâches
            Tache t1 = new Tache("Analyse", sdf.parse("15/01/2013"), sdf.parse("01/02/2013"), 1200.0, proj1);
            Tache t2 = new Tache("Conception", sdf.parse("02/02/2013"), sdf.parse("15/02/2013"), 1500.0, proj1);
            Tache t3 = new Tache("Développement", sdf.parse("16/02/2013"), sdf.parse("30/03/2013"), 2500.0, proj1);

            ts.create(t1);
            ts.create(t2);
            ts.create(t3);

            // 5. Réalisation des tâches (Table associative EmployeTache)
            // On utilise les dates de l'exemple de l'énoncé
            ets.create(new EmployeTache(emp1, t1, sdf.parse("10/02/2013"), sdf.parse("20/02/2013")));
            ets.create(new EmployeTache(emp1, t2, sdf.parse("10/03/2013"), sdf.parse("15/03/2013")));
            ets.create(new EmployeTache(emp1, t3, sdf.parse("10/04/2013"), sdf.parse("25/04/2013")));

            // --- APPEL DES MÉTHODES D'AFFICHAGE DEMANDÉES ---

            System.out.println("=================================================");
            System.out.println("AFFICHAGE DU PROJET :");
            // On recharge le projet pour avoir les listes à jour
            Projet pResult = ps.getById(proj1.getId());
            ps.afficherTachesRealisees(pResult);

            System.out.println("\n=================================================");
            System.out.println("TÂCHES DONT LE PRIX > 1000 DH :");
            ts.getTachesPrixSuperieur1000().forEach(t -> {
                System.out.println("- " + t.getNom() + " (" + t.getPrix() + " DH)");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
