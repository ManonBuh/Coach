package com.example.coach.contract;

public interface ICalculView {

    // Méthode qui permettra d'afficher le résultat dans la vue
    void afficherResultat(String image, double img, String message, boolean normal);

    // Permet de remplir les champs avec un profil enregistré
    void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe);

}
