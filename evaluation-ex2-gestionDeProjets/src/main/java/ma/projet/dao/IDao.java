package ma.projet.dao;

import java.util.List;

public interface IDao<T> {


    // Ajoute un nouvel objet dans la base de données.
    boolean create(T o);


    // Récupère un objet par son identifiant.

    T getById(int id);

    // Récupère la liste complète des objets d'une table.
    List<T> getAll();


    //Met à jour les informations d'un objet existant.
    boolean update(T o);

    // Supprime un objet de la base de données.
    boolean delete(T o);
}