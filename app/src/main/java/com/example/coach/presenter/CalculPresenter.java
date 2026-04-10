package com.example.coach.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;
import com.google.gson.Gson;

public class CalculPresenter {

    // Référence vers la vue via le contrat
    private final ICalculView vue;

    // Nom du fichier de sauvegarde
    private static final String NOM_FIC = "coach_fic";

    // Clé pour stocker le profil en JSON
    private static final String PROFIL_CLE = "profil_json";

    // Objet pour convertir en JSON
    private Gson gson;

    // Objet pour gérer le fichier de sauvegarde
    private SharedPreferences prefs;

    // Constructeur
    public CalculPresenter(ICalculView vue, Context context) {
        this.vue = vue;

        // Création du fichier de sauvegarde
        this.prefs = context.getSharedPreferences(NOM_FIC, Context.MODE_PRIVATE);

        // Création de l'objet Gson
        this.gson = new Gson();
    }

    // Sauvegarde le profil au format JSON
    private void sauvegarderProfil(Profil profil) {

        // Conversion en JSON
        String json = gson.toJson(profil);

        // Sauvegarde dans le fichier
        prefs.edit().putString(PROFIL_CLE, json).apply();
    }

    // === MÉTHODE PRINCIPALE ===
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {

        // Création du modèle (Profil)
        Profil profil = new Profil(poids, taille, age, sexe);

        // Sauvegarde du profil
        sauvegarderProfil(profil);

        // Envoi des données à la vue
        vue.afficherResultat(
                profil.getImage(),
                profil.getImg(),
                profil.getMessage(),
                profil.normal()
        );
    }
    public void chargerProfil() {

        // Récupération du JSON
        String json = prefs.getString(PROFIL_CLE, null);

        // Si un profil existe
        if (json != null) {

            // Conversion JSON → objet Profil
            Profil profil = gson.fromJson(json, Profil.class);

            // Envoi des données à la vue
            vue.remplirChamps(
                    profil.getPoids(),
                    profil.getTaille(),
                    profil.getAge(),
                    profil.getSexe()
            );
        }
    }
}
