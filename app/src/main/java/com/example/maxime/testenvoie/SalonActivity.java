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

    Server serveur;
    static TextView nbClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);

        nbClients = (TextView) findViewById(R.id.nbClient);

        try {
            serveur = new Server(4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread unThread = new Thread(new Client(InetAddress.getByName("0.0.0.0")));
            unThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
