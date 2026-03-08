package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

// Requête nommée HQL : femmes mariées au moins 2 fois
@NamedQuery(
        name = "Femme.marieeDeuxFoisOuPlus",
        query = "SELECT f FROM Femme f WHERE " +
                "(SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
)

// Requête native nommée : nombre total d'enfants d'une femme entre deux dates
@NamedNativeQuery(
        name = "Femme.nbrEnfantsEntreDeuxDates",
        query = "SELECT SUM(m.nbr_enfant) FROM mariage m " +
                "WHERE m.femme_id = :femmeId " +
                "AND m.date_debut >= :debut " +
                "AND m.date_debut <= :fin"
)

@Entity
@Table(name = "femme")
public class Femme extends Personne {

    // Une femme peut avoir plusieurs mariages
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Femme() {}

    public Femme(String nom, String prenom, String telephone,
                 String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}