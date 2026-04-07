package com.example.coach.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coach.R;
import com.example.coach.contract.ICalculView;
import com.example.coach.presenter.CalculPresenter;

public class MainActivity extends AppCompatActivity implements ICalculView {

    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;
    private Button btnCalc;
    private ImageView imgSmiley;
    private TextView lblResultat;
    // Presenter : fait le lien entre la vue et le modèle
    private CalculPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    // Cette méthode relie les variables Java aux objets du XML
    private void chargeObjetsGraphiques() {
        rdHomme = findViewById(R.id.rdHomme);
        rdFemme = findViewById(R.id.rdFemme);
        txtPoids = findViewById(R.id.txtPoids);
        txtTaille = findViewById(R.id.txtTaille);
        txtAge = findViewById(R.id.txtAge);
        btnCalc = findViewById(R.id.btnCalc);
        imgSmiley = findViewById(R.id.imgSmiley);
        lblResultat = findViewById(R.id.lblResultat);
    }

    // Initialise l'écran au démarrage
    private void init() {
        chargeObjetsGraphiques();
        presenter = new CalculPresenter(this);
        btnCalc.setOnClickListener(v -> btnCalc_clic());
    }

    // Méthode appelée lors du clic sur le bouton Calculer
    private void btnCalc_clic() {

        // Valeurs par défaut
        Integer poids = 0;
        Integer taille = 0;
        Integer age = 0;
        Integer sexe = 0; // 0 = femme par défaut

        // On essaie de récupérer et convertir les valeurs saisies
        try {
            poids = Integer.parseInt(txtPoids.getText().toString());
            taille = Integer.parseInt(txtTaille.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (Exception ignored) {
            // Si conversion impossible, on laisse les valeurs à 0
        }

        // Si le bouton Homme est coché, alors sexe = 1
        if (rdHomme.isChecked()) {
            sexe = 1;
        }

        // Si un champ est vide ou invalide, on affiche un message
        if (poids == 0 || taille == 0 || age == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            // Sinon, on demande au presenter de créer le profil
            presenter.creerProfil(poids, taille, age, sexe);
        }
    }

    @Override
    public void afficherResultat(String image, double img, String message, boolean normal) {

        // On récupère l'id de l'image à partir de son nom
        int imageId = getResources().getIdentifier(image, "drawable", getPackageName());

        // Si l'image n'est pas trouvée, on affiche l'image "normal" par défaut
        if (imageId == 0) {
            imgSmiley.setImageResource(R.drawable.normal);
        } else {
            imgSmiley.setImageResource(imageId);
        }

        // Construction du texte à afficher
        String texte = String.format("%.01f", img) + " : IMG " + message;

        // Affichage du texte
        lblResultat.setText(texte);

        // Couleur du texte : vert si normal, rouge sinon
        if (normal) {
            lblResultat.setTextColor(Color.GREEN);
        } else {
            lblResultat.setTextColor(Color.RED);
        }
    }

}
