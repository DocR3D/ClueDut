package com.example.maxime.testenvoie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;

public class SalonActivity extends AppCompatActivity {

    Button client;
    Server serveur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);

        client = (Button) findViewById(R.id.client);

        try {
            serveur = new Server(4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Client client = new Client(InetAddress.getByName("0.0.0.0"));
                    Log.e("Players","" + serveur.players.size());
                    Log.e("Joueurs","" + serveur.getNbJoueur());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
