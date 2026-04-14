package com.example.coach.api;

// Classe générique pour récupérer la réponse de l'API
public class ResponseApi<T> {

    // Code HTTP (200, 500…)
    private int code;

    // Message associé (OK, erreur…)
    private String message;

    // Résultat (type variable)
    private T result;

    // Getter pour code
    public int getCode() {
        return code;
    }

    // Getter pour message
    public String getMessage() {
        return message;
    }

    // Getter pour result
    public T getResult() {
        return result;
    }
}