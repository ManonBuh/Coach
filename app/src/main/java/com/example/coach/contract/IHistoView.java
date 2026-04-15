package com.example.coach.contract;

import com.example.coach.model.Profil;

import java.util.List;

// Contrat de la vue historique
public interface IHistoView extends IAllView {

    // Affiche la liste des profils
    void afficherListe(List<Profil> profils);

    // Envoie un profil vers l'ecran de calcul
    void transfertProfil(Profil profil);
}