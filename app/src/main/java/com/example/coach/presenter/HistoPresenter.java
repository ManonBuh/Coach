package com.example.coach.presenter;

import android.util.Log;
import com.example.coach.contract.IHistoView;
import com.example.coach.api.CoachApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;
import com.example.coach.model.Profil;
import com.example.coach.api.ICallbackApi;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Gère la logique de l'historique
public class HistoPresenter {

    // vue liée au presenter
    private final IHistoView vue;

    // constructeur
    public HistoPresenter(IHistoView vue) {
        this.vue = vue;
    }

    // Charge les profils depuis l'API
    public void chargerProfils() {

        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);

        Call<ResponseApi<List<Profil>>> call = api.getProfils();

        call.enqueue(new Callback<ResponseApi<List<Profil>>>() {

            @Override
            public void onResponse(Call<ResponseApi<List<Profil>>> call, Response<ResponseApi<List<Profil>>> response) {

                Log.d("API_HISTO", "Réponse reçue");

                if (response.isSuccessful() && response.body() != null) {

                    Log.d("API_HISTO", "Code : " + response.body().getCode());
                    Log.d("API_HISTO", "Message : " + response.body().getMessage());
                    Log.d("API_HISTO", "Result : " + response.body().getResult());

                    List<Profil> profils = response.body().getResult();

                    if (profils != null && !profils.isEmpty()) {

                        // trie du plus récent au plus ancien
                        Collections.sort(profils, (p1, p2) ->
                                p2.getDateMesure().compareTo(p1.getDateMesure())
                        );

                        // envoie à la vue
                        vue.afficherListe(profils);

                    } else {
                        vue.afficherMessage("échec chargement profils");
                    }

                } else {
                    vue.afficherMessage("échec chargement profils");
                    Log.e("API_HISTO", "Erreur réponse : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<List<Profil>>> call, Throwable t) {
                vue.afficherMessage("échec chargement profils");
                Log.e("API_HISTO", "Erreur API", t);
            }
        });
    }

    // Supprime un profil
    public void supprProfil(Profil profil, ICallbackApi<Void> callback) {

        // transforme le profil en json
        String profilJson = CoachApi.getGson().toJson(profil);

        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);

        Call<ResponseApi<Integer>> call = api.supprProfil(profilJson);

        call.enqueue(new Callback<ResponseApi<Integer>>() {

            @Override
            public void onResponse(Call<ResponseApi<Integer>> call, Response<ResponseApi<Integer>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Integer result = response.body().getResult();

                    if (result == 1) {
                        vue.afficherMessage("profil supprimé");
                        callback.onSuccess(null);
                    } else {
                        vue.afficherMessage("échec suppression profil");
                    }

                } else {
                    vue.afficherMessage("échec suppression profil");
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<Integer>> call, Throwable t) {
                vue.afficherMessage("échec suppression profil");
                Log.e("API_HISTO", "Erreur suppression API", t);
            }
        });
    }

    // Demande à la vue d'ouvrir le profil
    public void transfertProfil(Profil profil) {
        vue.transfertProfil(profil);
    }
}