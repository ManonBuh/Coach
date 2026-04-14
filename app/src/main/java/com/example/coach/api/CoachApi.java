package com.example.coach.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoachApi {

    // Adresse de l'API pour l'émulateur Android
    private static final String API_URL = "http://10.0.2.2/rest_coach/";

    // Objet Retrofit unique
    private static Retrofit retrofit = null;

    // Objet Gson avec format de date compatible MySQL
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    // Retourne l'objet Retrofit unique
    public static Retrofit getRetrofit() {

        // Si l'objet n'existe pas encore, on le crée
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    // Retourne l'objet Gson
    public static Gson getGson() {
        return gson;
    }
}
