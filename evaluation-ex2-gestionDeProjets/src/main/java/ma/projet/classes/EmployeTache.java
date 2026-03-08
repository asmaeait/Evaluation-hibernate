package ma.projet.classes;
import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTachePK;
import ma.projet.classes.Tache;
import javax.persistence.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK id;

    @ManyToOne
    @MapsId("idemploye")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @MapsId("idtache")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    // Constructeur important pour l'instanciation
    public EmployeTache(Employe employe, Tache tache, Date debut, Date fin) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = debut;
        this.dateFinReelle = fin;
        this.id = new EmployeTachePK(employe.getId(), tache.getId());
    }

    public EmployeTache() {}

    // Getters et Setters...

    public EmployeTachePK getId() {
        return id;
    }

    public void setId(EmployeTachePK id) {
        this.id = id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }
}