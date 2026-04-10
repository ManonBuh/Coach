package com.example.coach.model;

public class Profil {

    // === Constantes ===
    // Seuils IMG pour une femme
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;

    // Seuils IMG pour un homme
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;

    // Messages à afficher selon le résultat
    // indice 0 = trop faible
    // indice 1 = normal
    // indice 2 = trop élevé
    private static final String[] MESSAGE = {"trop faible", "normal", "trop élevé"};

    // Noms des images à afficher selon le résultat
    // Ces noms doivent correspondre aux images dans drawable
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};

    // === Propriétés ===
    // Données saisies par l'utilisateur
    private final Integer poids;   // poids en kg
    private final Integer taille;  // taille en cm
    private final Integer age;     // âge en années
    private final Integer sexe;    // 0 = femme, 1 = homme

    // Résultat du calcul
    private final double img;      // valeur de l'IMG calculé

    // Indice pour savoir quel message/image utiliser
    // 0 = trop faible, 1 = normal, 2 = trop élevé
    private final int indice;

    // === Constructeur ===
    public Profil(Integer poids, Integer taille, Integer age, Integer sexe) {

        // récupère les valeurs saisies
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;

        // calcule l'IMG automatiquement
        this.img = calculImg();

        // On détermine l'indice (faible / normal / élevé)
        this.indice = calculIndice();
    }

    // === CALCUL IMG ===
    private double calculImg() {

        // Conversion de la taille en mètres (car elle est saisie en cm)
        double tailleMetre = taille / 100.0;

        // Formule IMG
        return (1.2 * poids / (tailleMetre * tailleMetre))
                + (0.23 * age)
                - (10.83 * sexe)
                - 5.4;
    }

    // === CALCUL INDICE ===
    private int calculIndice() {

        // Variables locales pour stocker les seuils minimum et maximum
        int min;
        int max;

        // On choisit les bons seuils selon le sexe
        if (sexe == 0) {
            min = MIN_FEMME;
            max = MAX_FEMME;
        } else {
            min = MIN_HOMME;
            max = MAX_HOMME;
        }

        // On compare l'IMG calculé avec les seuils
        if (img < min) {
            return 0; // trop faible
        } else if (img > max) {
            return 2; // trop élevé
        } else {
            return 1; // normal
        }
    }

    // === GETTERS ET MÉTHODES PUBLIQUES ===
    // Retourne la valeur de l'IMG calculé
    public double getImg() {
        return img;
    }

    // Retourne le message correspondant à l'indice
// 0 = trop faible, 1 = normal, 2 = trop élevé
    public String getMessage() {
        return MESSAGE[indice];
    }

    // Retourne le nom de l'image correspondant à l'indice
    public String getImage() {
        return IMAGE[indice];
    }

    // Retourne true si le profil est normal
    public boolean normal() {
        return indice == 1;
    }

    // === GETTERS DONNÉES SAISIES ===

    // Retourne le poids
    public Integer getPoids() {
        return poids;
    }

    // Retourne la taille
    public Integer getTaille() {
        return taille;
    }

    // Retourne l'âge
    public Integer getAge() {
        return age;
    }

    // Retourne le sexe (0 = femme, 1 = homme)
    public Integer getSexe() {
        return sexe;
    }

}
