package ma.test;

import ma.projet.beans.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainTest {

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        FemmeService femmeService   = new FemmeService();
        HommeService hommeService   = new HommeService();
        MariageService mariageService = new MariageService();

        // ===== CRÉER 10 FEMMES =====
        Femme f1  = new Femme("RAMI",   "SALIMA",  "06001", "Rabat",  sdf.parse("03/05/1970"));
        Femme f2  = new Femme("ALI",    "AMAL",    "06002", "Fes",    sdf.parse("10/08/1975"));
        Femme f3  = new Femme("ALAOUI", "WAFA",    "06003", "Casa",   sdf.parse("04/02/1980"));
        Femme f4  = new Femme("ALAMI",  "KARIMA",  "06004", "Rabat",  sdf.parse("15/09/1965"));
        Femme f5  = new Femme("SAIDI",  "NADIA",   "06005", "Meknes", sdf.parse("20/03/1972"));
        Femme f6  = new Femme("OMAR",   "HIND",    "06006", "Tanger", sdf.parse("01/01/1968"));
        Femme f7  = new Femme("BAKI",   "LAYLA",   "06007", "Agadir", sdf.parse("12/07/1983"));
        Femme f8  = new Femme("ZAKI",   "SARA",    "06008", "Oujda",  sdf.parse("25/11/1990"));
        Femme f9  = new Femme("TAHIR",  "RIM",     "06009", "Casa",   sdf.parse("08/04/1985"));
        Femme f10 = new Femme("OUALI",  "FATIMA",  "06010", "Rabat",  sdf.parse("30/06/1960"));

        femmeService.create(f1);  femmeService.create(f2);
        femmeService.create(f3);  femmeService.create(f4);
        femmeService.create(f5);  femmeService.create(f6);
        femmeService.create(f7);  femmeService.create(f8);
        femmeService.create(f9);  femmeService.create(f10);
        System.out.println("10 femmes créées");

        // ===== CRÉER 5 HOMMES =====
        Homme h1 = new Homme("SAFI",   "SAID",   "07001", "Rabat",  sdf.parse("01/01/1960"));
        Homme h2 = new Homme("BENALI", "OMAR",   "07002", "Casa",   sdf.parse("15/05/1965"));
        Homme h3 = new Homme("TAHIRI", "AHMED",  "07003", "Fes",    sdf.parse("20/08/1970"));
        Homme h4 = new Homme("CHAOUI", "YOUSSEF","07004", "Agadir", sdf.parse("10/03/1975"));
        Homme h5 = new Homme("RAJI",   "KARIM",  "07005", "Tanger", sdf.parse("05/11/1980"));

        hommeService.create(h1); hommeService.create(h2);
        hommeService.create(h3); hommeService.create(h4);
        hommeService.create(h5);
        System.out.println(" 5 hommes créés");

        // ===== CRÉER DES MARIAGES =====
        // h1 (SAFI SAID) - 4 mariages dont 1 échoué
        mariageService.create(new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f4));  // échoué
        mariageService.create(new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f1));  // en cours
        mariageService.create(new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f2));  // en cours
        mariageService.create(new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f3));  // en cours

        // h2 - 2 mariages
        mariageService.create(new Mariage(sdf.parse("01/01/1995"), null, 2, h2, f5));
        mariageService.create(new Mariage(sdf.parse("10/06/2000"), null, 1, h2, f6));

        // h3 - 2 mariages (pour tester femmes mariées 2 fois)
        mariageService.create(new Mariage(sdf.parse("05/05/1998"), sdf.parse("01/01/2005"), 1, h3, f1)); // f1 mariée 2x
        mariageService.create(new Mariage(sdf.parse("10/10/2005"), null, 3, h3, f7));

        // h4 - 1 mariage
        mariageService.create(new Mariage(sdf.parse("20/03/2002"), null, 2, h4, f8));

        // h5 - 1 mariage
        mariageService.create(new Mariage(sdf.parse("15/07/2010"), null, 0, h5, f9));

        System.out.println(" Mariages créés\n");

        // ========== TEST 1 : Liste de toutes les femmes ==========
        System.out.println("========== TEST 1 : Liste des femmes ==========");
        List<Femme> femmes = femmeService.getAll();
        for (Femme f : femmes) {
            System.out.println("  -> " + f.getNom() + " " + f.getPrenom());
        }

        // ========== TEST 2 : Femme la plus âgée ==========
        System.out.println("\n========== TEST 2 : Femme la plus âgée ==========");
        Femme agee = femmeService.getFemmeLaPlusAgee();
        System.out.println("  -> " + agee.getNom() + " " + agee.getPrenom()
                + " | Née le : " + sdf.format(agee.getDateNaissance()));

        // ========== TEST 3 : Épouses de h1 entre deux dates ==========
        System.out.println("\n========== TEST 3 : Épouses de SAFI SAID entre 1989 et 2001 ==========");
        List<Femme> epouses = hommeService.getEpousesEntreDeuxDates(
                h1.getId(), sdf.parse("01/01/1989"), sdf.parse("31/12/2001")
        );
        for (Femme f : epouses) {
            System.out.println("  -> " + f.getNom() + " " + f.getPrenom());
        }

        // ========== TEST 4 : Nbr enfants de f1 entre deux dates ==========
        System.out.println("\n========== TEST 4 : Nbr enfants de RAMI SALIMA entre 1989 et 2000 ==========");
        int nbr = femmeService.getNbrEnfantsEntreDeuxDates(
                f1.getId(), sdf.parse("01/01/1989"), sdf.parse("31/12/2000")
        );
        System.out.println("  -> Nombre d'enfants : " + nbr);

        // ========== TEST 5 : Femmes mariées 2 fois ou plus ==========
        System.out.println("\n========== TEST 5 : Femmes mariées au moins 2 fois ==========");
        List<Femme> mariees2fois = femmeService.getFemmesMarieesDeuxFoisOuPlus();
        for (Femme f : mariees2fois) {
            System.out.println("  -> " + f.getNom() + " " + f.getPrenom());
        }

        // ========== TEST 6 : Hommes mariés à 4 femmes entre deux dates ==========
        System.out.println("\n========== TEST 6 : Hommes mariés à 4 femmes entre 1985 et 2005 ==========");
        List<Homme> h4femmes = hommeService.getHommesMariesAQuatreFemmes(
                sdf.parse("01/01/1985"), sdf.parse("31/12/2005")
        );
        for (Homme h : h4femmes) {
            System.out.println("  -> " + h.getNom() + " " + h.getPrenom());
        }

        // ========== TEST 7 : Mariages de h1 avec tous les détails ==========
        System.out.println("\n========== TEST 7 : Mariages de SAFI SAID ==========");
        hommeService.afficherMariagesHomme(h1.getId());

        System.out.println("\n Tous les tests terminés !");
    }
}