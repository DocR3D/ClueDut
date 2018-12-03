package com.example.maxime.testenvoie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        try {
            serveur = new Server(4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread unThread = new Thread(new Client(InetAddress.getByName(MainActivity.connectIp.getText().toString()), MainActivity.connectPseudo.getText().toString()));
            unThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
