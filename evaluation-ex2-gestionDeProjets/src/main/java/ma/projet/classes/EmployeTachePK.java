package ma.projet.classes;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeTachePK implements Serializable {

    private int idemploye;
    private int idtache;

    public EmployeTachePK() {}

    public EmployeTachePK(int idemploye, int idtache) {
        this.idemploye = idemploye;
        this.idtache = idtache;
    }

    // --- GETTERS ET SETTERS ---
    public int getIdemploye() { return idemploye; }
    public void setIdemploye(int idemploye) { this.idemploye = idemploye; }

    public int getIdtache() { return idtache; }
    public void setIdtache(int idtache) { this.idtache = idtache; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeTachePK that = (EmployeTachePK) o;
        return idemploye == that.idemploye && idtache == that.idtache;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idemploye, idtache);
    }
}