package com.example.coach.api;

// Sert à exécuter du code après un retour API
public interface ICallbackApi<T> {

    void onSuccess(T result);

    void onError();
}