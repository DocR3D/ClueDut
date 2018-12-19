package com.example.maxime.testenvoie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.maxime.testenvoie.classes.Client;
import com.example.maxime.testenvoie.classes.Server;

public class MainActivity extends AppCompatActivity {

    Button creerServer;
    Button creerClient;
    static EditText connectIp;
    static EditText connectPseudo;
    TextView ip;
    Button commandeConnect;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        creerServer = (Button) findViewById(R.id.creerServer);
        creerClient = (Button) findViewById(R.id.creerClient);
        connectPseudo = (EditText) findViewById(R.id.connectPseudo);
        commandeConnect = (Button) findViewById(R.id.commandeConnect);
        connectIp = (EditText) findViewById(R.id.ip);

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

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

        creerServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server server = new Server();
            }
        });

        creerClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Client client = new Client(""+connectPseudo.getText(), "" + connectIp.getText());
            }
        });

        commandeConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
