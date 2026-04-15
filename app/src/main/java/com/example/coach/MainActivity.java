package com.example.coach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coach.view.CalculActivity;
import com.example.coach.view.HistoActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnMonIMG;
    private Button btnMonHistorique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btnMonIMG = findViewById(R.id.btnMonIMG);
        btnMonHistorique = findViewById(R.id.btnMonHistorique);

        btnMonIMG.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculActivity.class);
            startActivity(intent);
        });

        btnMonHistorique.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoActivity.class);
            startActivity(intent);
        });
    }
}