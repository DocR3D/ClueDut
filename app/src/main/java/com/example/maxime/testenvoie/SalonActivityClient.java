package com.example.maxime.testenvoie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.InetAddress;

public class SalonActivityClient extends AppCompatActivity {

    static String lip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_client);

        try {
            Thread unThread = new Thread(new Client(InetAddress.getByName(MainActivity.connectIp.getText().toString()), MainActivity.connectPseudo.getText().toString()));
            unThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
