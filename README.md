# Évaluation Hibernate — Gestion avec JPA/Hibernate

> **Technologie :** Java · Hibernate 5.6 · MySQL (XAMPP) · IntelliJ IDEA  
> **Objectif :** Maîtriser la couche de persistance avec Hibernate (entités, relations, services, requêtes HQL/nommées/natives/Criteria)

---

## 📋 Table des matières

- [Exercice 1 — Gestion de Stock](#exercice-1--gestion-de-stock)
- [Exercice 2 — Gestion de Projets](#exercice-2--gestion-de-projets)
- [Exercice 3 — Gestion de l'État Civil](#exercice-3--gestion-de-létat-civil)
- [Configuration commune](#configuration-commune)
- [Technologies utilisées](#technologies-utilisées)

---

## Exercice 1 — Gestion de Stock

### Contexte
Développer une application pour gérer le stock d'un magasin de produits informatiques.

### Diagramme de classes

<img width="908" height="517" alt="image" src="https://github.com/user-attachments/assets/ce5bfecd-907a-424a-b2a7-2c6eb40e5578" />


### Structure du projet

<img width="583" height="866" alt="image" src="https://github.com/user-attachments/assets/78a91ce0-131c-4102-9ca6-ce51ce448503" />


### Relations entre entités

| Entité | Relation | Entité |
|--------|----------|--------|
| Produit | `@ManyToOne` | Categorie |
| LigneCommande | `@ManyToOne` | Commande |
| LigneCommande | `@ManyToOne` | Produit |

### Fonctionnalités implémentées

-  CRUD complet pour toutes les entités
-  Afficher les produits par catégorie
-  Afficher les produits commandés entre deux dates
-  Afficher les produits d'une commande donnée
-  Afficher les produits dont le prix > 100 DH (**requête nommée `@NamedQuery`**)


### Screenshots

La base de donnee cree:

<img width="791" height="138" alt="interface2-ev-bd" src="https://github.com/user-attachments/assets/7da3aaaf-ac87-4fd1-988c-23beb7f69581" />

L'affichage des resultat:

<img width="839" height="409" alt="interface1-ev" src="https://github.com/user-attachments/assets/f988a164-84b5-4e08-b025-26731f8c0e23" />



---

## Exercice 2 — Gestion de Projets

### Contexte
Un bureau d'études souhaite suivre le temps passé sur les projets et calculer les coûts globaux.

### Diagramme de classes
<img width="908" height="517" alt="image" src="https://github.com/user-attachments/assets/a69cf2f7-6531-4a42-a2c9-02da84d5699c" />

### Structure du projet

<img width="530" height="651" alt="image" src="https://github.com/user-attachments/assets/9814c747-d8b6-4968-bae5-45d8158478e1" />


### Relations entre entités

| Entité | Relation | Entité |
|--------|----------|--------|
| Projet | `@ManyToOne` | Employe (chef de projet) |
| Tache | `@ManyToOne` | Projet |
| EmployeTache | `@ManyToOne` | Employe |
| EmployeTache | `@ManyToOne` | Tache |

### Fonctionnalités implémentées

**EmployeService :**
-  Afficher les tâches réalisées par un employé
-  Afficher les projets gérés par un employé (chef de projet)

**ProjetService :**
-  Afficher les tâches planifiées pour un projet
-  Afficher les tâches réalisées avec les dates réelles (format tableau)

**TacheService :**
-  Afficher les tâches dont le prix > 1000 DH (**requête nommée `@NamedQuery`**)
-  Afficher les tâches réalisées entre deux dates

### Exemple d'affichage

```
Projet : 4      Nom : Gestion de stock     Date début : 14 Janvier 2013
Liste des tâches:
Num  Nom            Date Début Réelle   Date Fin Réelle
12   Analyse        10/02/2013          20/02/2013
13   Conception     10/03/2013          15/03/2013
14   Développement  10/04/2013          25/04/2013
```

### Screenshots

quelque exemples d'interfaces: (un dossier d'interfaces contient d'autres)

<img width="789" height="133" alt="db-tables" src="https://github.com/user-attachments/assets/b6496c8e-e06d-4cc5-a8af-da8c13937685" />

<img width="574" height="336" alt="8-resultat" src="https://github.com/user-attachments/assets/6427a023-3274-4ddb-9ffb-7b0b133e9683" />

<img width="499" height="316" alt="lastone" src="https://github.com/user-attachments/assets/ff9f4d4d-0528-4607-8025-c4d4484dc20a" />




## Exercice 3 — Gestion de l'État Civil

### Contexte
Créer une application permettant de gérer les citoyens et leurs relations matrimoniales dans une province.

### Diagramme de classes
<img width="521" height="396" alt="image" src="https://github.com/user-attachments/assets/a56e3987-65a4-4bf5-9425-e909d962b2be" />


### Structure du projet

<img width="651" height="739" alt="image" src="https://github.com/user-attachments/assets/1908db54-6545-41b8-9c0c-b06de8278d76" />


### Relations entre entités

| Entité | Relation | Entité |
|--------|----------|--------|
| Homme | hérite | Personne |
| Femme | hérite | Personne |
| Mariage | `@ManyToOne` | Homme |
| Mariage | `@ManyToOne` | Femme |

### Stratégie d'héritage
Utilisation de `@Inheritance(strategy = InheritanceType.JOINED)` → chaque sous-classe a sa propre table liée à la table `personne` par clé étrangère.

```
personne (id, nom, prenom, telephone, adresse, dateNaissance)
    ├── homme (id → FK personne)
    └── femme (id → FK personne)
```

### Fonctionnalités implémentées

**HommeService :**
-  CRUD complet
-  Afficher les épouses d'un homme entre deux dates **(HQL)**
-  Afficher tous les mariages d'un homme avec détails (en cours / échoués)
-  Afficher les hommes mariés à 4 femmes entre deux dates **(HQL groupBy/having)**


**FemmeService :**
-  CRUD complet
-  Afficher la femme la plus âgée
-  Nombre d'enfants d'une femme entre deux dates **(`@NamedNativeQuery`)**
-  Femmes mariées au moins 2 fois **(`@NamedQuery` HQL)**

### Exemple d'affichage

```
Nom : SAFI SAID
Mariages En Cours :
1. Femme : SALIMA RAMI    Date Début : 03/09/1990    Nbr Enfants : 4
2. Femme : AMAL ALI       Date Début : 03/09/1995    Nbr Enfants : 2
3. Femme : WAFA ALAOUI    Date Début : 04/11/2000    Nbr Enfants : 3

Mariages échoués :
1. Femme : KARIMA ALAMI   Date Début : 03/09/1989
   Date Fin : 03/09/1990    Nbr Enfants : 0
```

### Screenshots

<img width="786" height="143" alt="baseDonee" src="https://github.com/user-attachments/assets/46740e71-c972-48e7-8f96-b146153b681e" />

<img width="517" height="373" alt="test1" src="https://github.com/user-attachments/assets/e2f70f27-09ca-4053-8202-0b5da95a45d2" />

<img width="560" height="380" alt="test6-2-test7" src="https://github.com/user-attachments/assets/a8d238d9-adc1-44a9-9c97-5557799abcb8" />

<img width="489" height="373" alt="test7-2" src="https://github.com/user-attachments/assets/2c6cfc48-c0c9-4599-b599-d11f82d27b4a" />

<img width="526" height="244" alt="last-one" src="https://github.com/user-attachments/assets/adbe562b-794f-4ed7-84e1-e44738115645" />


---

## Configuration commune

### `hibernate.cfg.xml`

```xml
<property name="hibernate.connection.url">
    jdbc:mysql://localhost:3306/NOM_BASE?createDatabaseIfNotExist=true
</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password"></property>
<property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
<property name="hibernate.hbm2ddl.auto">update</property>
<property name="hibernate.show_sql">true</property>
```

### `HibernateUtil.java`

```java
public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    return sessionFactory;
}
```

### Dépendances Maven (`pom.xml`)

```xml
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.6.15.Final</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

---

## Technologies utilisées

| Technologie | Version | Rôle |
|-------------|---------|------|
| Java | 1.8 | Langage de programmation |
| Hibernate ORM | 5.6.15 | Framework de persistance |
| MySQL | 8.0 | Base de données |
| XAMPP | - | Serveur local MySQL |
| IntelliJ IDEA | 2025.3 | IDE |
| Maven | - | Gestion des dépendances |

---

## Concepts Hibernate utilisés

| Concept | Exercice |
|---------|----------|
| `@Entity`, `@Table`, `@Id`, `@GeneratedValue` | Ex1, Ex2, Ex3 |
| `@OneToMany`, `@ManyToOne` | Ex1, Ex2, Ex3 |
| `@Inheritance(JOINED)` | Ex3 |
| `@NamedQuery` (HQL) | Ex2, Ex3 |
| `@NamedNativeQuery` (SQL natif) | Ex3 |
| `@Temporal(DATE)` | Ex1, Ex2, Ex3 |
| HQL avec `GROUP BY` / `HAVING` | Ex3 |
| Requêtes paramétrées | Ex1, Ex2, Ex3 |

---

*Évaluation réalisée avec Java 8 + Hibernate 5.6 + MySQL via XAMPP*

---
Auteur : Asma Ait Elmahjoub
