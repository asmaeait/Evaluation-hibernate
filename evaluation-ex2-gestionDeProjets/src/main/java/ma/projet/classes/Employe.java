package ma.projet.classes;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String telephone;

    // Relation avec Projet (Chef de projet)
    @OneToMany(mappedBy = "chefDeProjet", fetch = FetchType.EAGER)
    private Set<Projet> projets;

    // Relation avec la table associative EmployeTache
    @OneToMany(mappedBy = "employe", fetch = FetchType.EAGER)
    private Set<EmployeTache> employeTaches;

    // --- CONSTRUCTEURS ---
    public Employe() {}

    public Employe(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    // --- GETTERS ET SETTERS---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public Set<Projet> getProjets() {
        return projets;
    }

    public void setProjets(Set<Projet> projets) {
        this.projets = projets;
    }

    public Set<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(Set<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }
}