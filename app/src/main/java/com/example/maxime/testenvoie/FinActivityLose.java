package com.example.maxime.testenvoie;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinActivityLose extends AppCompatActivity {

    public TextView text;
    public Button retour;
    public FinActivityLose context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);

        context = this;

        text = findViewById(R.id.textFin);
        retour = findViewById(R.id.retourBouton);

        text.setText("Vous avez perdu !");
        text.setTextColor(Color.WHITE);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Menu.class));
            }
        });
    }
}
