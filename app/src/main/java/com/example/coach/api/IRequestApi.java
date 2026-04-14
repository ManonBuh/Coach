package com.example.coach.api;

import com.example.coach.model.Profil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IRequestApi {

    // 🔹 Récupérer tous les profils (GET)
    @GET("profil")
    Call<ResponseApi<List<Profil>>> getProfils();

    // 🔹 Créer un profil (POST)
    @FormUrlEncoded
    @POST("profil")
    Call<ResponseApi<Integer>> creerProfil(@Field("champs") String profilJson);
}