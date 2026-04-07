package com.example.coach;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup grpRadioSexe;
    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;
    private Button btnCalc;
    private ImageView imgSmiley;
    private TextView lblResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        grpRadioSexe = findViewById(R.id.grpRadioSexe);
        rdHomme = findViewById(R.id.rdHomme);
        rdFemme = findViewById(R.id.rdFemme);
        txtPoids = findViewById(R.id.txtPoids);
        txtTaille = findViewById(R.id.txtTaille);
        txtAge = findViewById(R.id.txtAge);
        btnCalc = findViewById(R.id.btnCalc);
        imgSmiley = findViewById(R.id.imgSmiley);
        lblResultat = findViewById(R.id.lblResultat);

        btnCalc.setOnClickListener(v -> {

            // récupérer les valeurs saisies
            String poidsStr = txtPoids.getText().toString();
            String tailleStr = txtTaille.getText().toString();
            String ageStr = txtAge.getText().toString();

            if (poidsStr.isEmpty() || tailleStr.isEmpty() || ageStr.isEmpty()) {
                lblResultat.setText("Veuillez remplir tous les champs");
                lblResultat.setTextColor(android.graphics.Color.RED);
                return;
            }

            // Conversion en nombres
            int poids = Integer.parseInt(poidsStr);
            int taille = Integer.parseInt(tailleStr);
            int age = Integer.parseInt(ageStr);

            // Conversion de la taille en mètres (car saisie en cm)
            double tailleM = taille / 100.0;
            int sexe = 0;

            // Détermination du sexe (1 = homme, 0 = femme)
            if (rdHomme.isChecked()) {
                sexe = 1;
            }

            // Calcul de l'IMG
            double img = (1.2 * poids / (tailleM * tailleM))
                    + (0.23 * age)
                    - (10.83 * sexe)
                    - 5.4;

            // Déclaration du message et de la couleur
            String message;
            int couleur;

            // Interprétation du résultat selon le sexe
            if (rdHomme.isChecked()) {

                if (img < 15) {
                    message = "IMG = " + String.format("%.2f", img) + " : trop maigre";
                    couleur = android.graphics.Color.RED;
                    imgSmiley.setImageResource(R.drawable.maigre);
                }
                else if (img <= 20) {
                    message = "IMG = " + String.format("%.2f", img) + " : normal";
                    couleur = android.graphics.Color.GREEN;
                    imgSmiley.setImageResource(R.drawable.normal);
                }
                else {
                    message = "IMG = " + String.format("%.2f", img) + " : trop de graisse";
                    couleur = android.graphics.Color.RED;
                    imgSmiley.setImageResource(R.drawable.graisse);
                }

            } else { // femme

                if (img < 25) {
                    message = "IMG = " + String.format("%.2f", img) + " : trop maigre";
                    couleur = android.graphics.Color.RED;
                    imgSmiley.setImageResource(R.drawable.maigre);
                }
                else if (img <= 30) {
                    message = "IMG = " + String.format("%.2f", img) + " : normal";
                    couleur = android.graphics.Color.GREEN;
                    imgSmiley.setImageResource(R.drawable.normal);
                }
                else {
                    message = "IMG = " + String.format("%.2f", img) + " : trop de graisse";
                    couleur = android.graphics.Color.RED;
                    imgSmiley.setImageResource(R.drawable.graisse);
                }
            }

            // Affichage du résultat
            lblResultat.setText(message);
            lblResultat.setTextColor(couleur);

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
