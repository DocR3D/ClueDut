package com.example.maxime.testenvoie;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button creerPartie;
    Button rejoindrePartie;
    static EditText connectIp;
    static EditText connectPseudo;
    TextView ip;
    Button jouer;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        creerPartie = (Button) findViewById(R.id.creerPartie);
        rejoindrePartie = (Button) findViewById(R.id.rejoindrePartie);
        connectIp = (EditText) findViewById(R.id.connectIp);
        connectPseudo = (EditText) findViewById(R.id.connectPseudo);
        ip = (TextView) findViewById(R.id.ip);
        jouer = (Button) findViewById(R.id.jouer);

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

        ip.setText("Votre Ip : " + Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()));

        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
        }
        int hasWriteContactsPermission2 = checkSelfPermission(Manifest.permission.INTERNET);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

        creerPartie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accèderActivity();
            }
        });

        rejoindrePartie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalonActivityClient.lip = connectIp.getText().toString();

                accèderActivityCient();
            }
        });

        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accèderActivityJouer();
            }
        });
    }

    private void accèderActivity() {
        Intent intent = new Intent(this, SalonActivity.class);
        startActivity(intent);
    }

    private void accèderActivityCient(){
        Intent intent = new Intent(this, SalonActivityClient.class);
        startActivity(intent);
    }

    private void accèderActivityJouer(){
        Intent intent = new Intent(this, JouerActivity.class);
        startActivity(intent);
    }
}
