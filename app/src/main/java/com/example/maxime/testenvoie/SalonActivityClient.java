/*package com.example.maxime.testenvoie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.InetAddress;

public class SalonActivityClient extends AppCompatActivity {

    static String lip;
    static ProgressBar progressBar;
    static Button ready;
    static boolean statut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_client);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ready = (Button) findViewById(R.id.ready);
        ready.setEnabled(false);

        try {
            Thread unThread = new Thread(new Client(InetAddress.getByName(MainActivity.connectIp.getText().toString()), MainActivity.connectPseudo.getText().toString()));
            unThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statut) {
                    ready.setText("not ready");
                    statut = true;
                } else {
                    ready.setText("ready");
                    statut = false;
                }
            }
        });
    }
}
*/