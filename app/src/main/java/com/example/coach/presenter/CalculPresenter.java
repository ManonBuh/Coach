package com.example.coach.presenter;

import android.content.Context;
import android.util.Log;

import com.example.coach.api.CoachApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalculPresenter {

    // Référence vers la vue via le contrat
    private final ICalculView vue;

    // Constructeur
    public CalculPresenter(ICalculView vue, Context context) {
        this.vue = vue;
    }

    // === MÉTHODE PRINCIPALE ===
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {

        // Création du modèle (Profil)
        Profil profil = new Profil(poids, taille, age, sexe, new Date());

        // Conversion du profil en JSON
        String profilJson = CoachApi.getGson().toJson(profil);
        Log.d("API", "JSON envoyé : " + profilJson);

        // Création de l'objet d'accès à l'API
        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);

        // Création de la requête HTTP
        Call<ResponseApi<Integer>> call = api.creerProfil(profilJson);

        // Envoi asynchrone de la requête
        call.enqueue(new Callback<ResponseApi<Integer>>() {
            @Override
            public void onResponse(Call<ResponseApi<Integer>> call, Response<ResponseApi<Integer>> response) {

                Log.d("API", "Réponse HTTP creerProfil : " + response.code());

                if (response.body() == null) {
                    Log.e("API", "response.body() est null dans creerProfil");
                }

                // Si la réponse est correcte et qu'une ligne a bien été ajoutée
                if (response.isSuccessful() && response.body() != null && response.body().getResult() == 1) {

                    // Affichage du résultat dans la vue
                    vue.afficherResultat(
                            profil.getImage(),
                            profil.getImg(),
                            profil.getMessage(),
                            profil.normal()
                    );

                } else {
                    Log.e("API", "Erreur API creerProfil : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<Integer>> call, Throwable t) {
                Log.e("API", "Échec d'accès à l'API creerProfil", t);
            }
        });
    }

    // Charge le dernier profil enregistré via l'API
    public void chargerDernierProfil() {

        // Création de l'objet d'accès à l'API
        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);

        // Création de la requête HTTP
        Call<ResponseApi<List<Profil>>> call = api.getProfils();

        // Envoi asynchrone de la requête
        call.enqueue(new Callback<ResponseApi<List<Profil>>>() {
            @Override
            public void onResponse(Call<ResponseApi<List<Profil>>> call, Response<ResponseApi<List<Profil>>> response) {

                Log.d("API", "Réponse HTTP getProfils : " + response.code());

                if (response.body() != null) {
                    Log.d("API", "Code API getProfils : " + response.body().getCode());
                    Log.d("API", "Message API getProfils : " + response.body().getMessage());
                    Log.d("API", "Résultat API getProfils : " + response.body().getResult());
                } else {
                    Log.e("API", "response.body() est null dans getProfils");
                }

                // Vérifie que la réponse est correcte
                if (response.isSuccessful() && response.body() != null) {

                    // Récupère la liste des profils
                    List<Profil> profils = response.body().getResult();

                    // Vérifie que la liste n'est pas vide
                    if (profils != null && !profils.isEmpty()) {

                        // Récupère le profil le plus récent
                        Profil dernier = Collections.max(
                                profils,
                                (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                        );

                        // Envoie les données à la vue
                        vue.remplirChamps(
                                dernier.getPoids(),
                                dernier.getTaille(),
                                dernier.getAge(),
                                dernier.getSexe()
                        );
                    }

                } else {
                    Log.e("API", "Erreur API getProfils : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<List<Profil>>> call, Throwable t) {
                Log.e("API", "Échec d'accès à l'API getProfils", t);
            }
        });
    }
}