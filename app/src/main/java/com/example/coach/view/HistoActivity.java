package com.example.coach.view;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coach.R;
import com.example.coach.model.Profil;
import com.example.coach.contract.IHistoView;
import com.example.coach.presenter.HistoPresenter;

import java.util.List;

// Gère l'écran de l'historique
public class HistoActivity extends AppCompatActivity implements IHistoView {

    // presenter de l'historique
    private HistoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histo);
        init();
    }

    // Initialise l'écran
    private void init() {
        presenter = new HistoPresenter(this);
        presenter.chargerProfils();
    }

    @Override
    public void afficherListe(List<Profil> profils) {

        android.util.Log.d("HISTO_VIEW", "afficherListe appelée");

        if (profils != null) {

            RecyclerView lstHisto = findViewById(R.id.lstHisto);

            HistoListAdapter adapter = new HistoListAdapter(profils, this);

            lstHisto.setAdapter(adapter);
            lstHisto.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void afficherMessage(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }

    @Override
    public void transfertProfil(Profil profil) {
        Intent intent = new Intent(HistoActivity.this, CalculActivity.class);
        intent.putExtra("profil", profil);
        startActivity(intent);
    }
}