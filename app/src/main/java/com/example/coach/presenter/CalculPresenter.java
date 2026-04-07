package com.example.coach.presenter;

import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

public class CalculPresenter {

    // Référence vers la vue via le contrat
    private ICalculView vue;

    // Constructeur : on reçoit la vue et on la stocke
    public CalculPresenter(ICalculView vue) {
        this.vue = vue;
    }

    // === MÉTHODE PRINCIPALE ===
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {

        // Création du modèle (Profil)
        Profil profil = new Profil(poids, taille, age, sexe);

        // Envoi des données à la vue
        vue.afficherResultat(
                profil.getImage(),
                profil.getImg(),
                profil.getMessage(),
                profil.normal()
        );
    }

}
