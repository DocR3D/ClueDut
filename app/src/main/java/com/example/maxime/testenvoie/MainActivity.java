package com.example.maxime.testenvoie;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    Button envoi;
    Button reception;
    ImageView laPhoto;
    File leFichier;
    Button leChoix;
    Uri theUri;
    TextView ip;
    EditText connectIp;
    String lip;
    String leChemin;
    public static final int PICK_IMAGE = 1;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        envoi = findViewById(R.id.Envoie);
        reception = findViewById(R.id.Reception);
        laPhoto = findViewById(R.id.imageView2);
        leChoix = findViewById(R.id.choix);
        ip = findViewById(R.id.textView);
        connectIp = findViewById(R.id.editText);


        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        ip.setText("Votre Ip : " + Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()));

        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
        }
        int hasWriteContactsPermission2 = checkSelfPermission(Manifest.permission.INTERNET);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
        }
        envoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lip = connectIp.getText().toString();
                    envoi();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        reception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Thread thrConnexion = new Thread(new ThreadConnection());
                    thrConnexion.start();
            }
        });
        leChoix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
                leChemin = data.getData().getPath();

            theUri = data.getData();
        }
    }

    class ThreadConnection implements Runnable {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void run() {
            DataOutputStream fos = null;

            ServerSocket socketEcoute;
            Socket socket = null;
            DataInputStream in = null;
            byte[] bytes = null;
            int count;
            long total = 0;
            long size;


            leFichier = new File("/sdcard/getpng.png");

            try {
                fos = new DataOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(leFichier)));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Création du fichier impossible !");
                System.exit(-1);
            }

            try {
                socketEcoute = new ServerSocket(9002, 6);
                System.out.println("socket d'écoute créé");
                System.out.println("Ip socket : " + socketEcoute.getInetAddress() + " Port : " + socketEcoute.getLocalSocketAddress());

                socket = socketEcoute.accept();
                System.out.println("client connecté");

                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                bytes = new byte[1024];

                System.out.println("réception des données - début");
                size = in.readLong();
                System.out.println("réception de " + size + " octets");

                while ((count = in.read(bytes)) > 0) {
                    fos.write(bytes, 0, count);
                    total += count;
                }
                System.out.println("réception des données - fin");
                System.out.println(total + " octets reçus");

                fos.close();

                in.close();
                socketEcoute.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread thread = new Thread()
            {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() //run on ui thread
                    {
                        public void run()
                        {
                            laPhoto.setImageURI(Uri.fromFile(leFichier));

                        }
                    });
                }
            };
            thread.start();


            return ;
        }
    }

    private void envoi() throws IOException, URISyntaxException {
        File file   = null;
        DataInputStream fis    = null;

        InetSocketAddress sa     = null;
        Socket socket = null;
        DataOutputStream out    = null;
        byte[]            bytes  = null;
        int			     count;

        if(theUri== null) file = new File("/sdcard/png.PNG");
        else{
            file = new File(leChemin);
        }

        try {
            fis = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Pas de fichier !");
            System.exit(-1);
        }

        System.out.println("Taille du fichier = " + file.length());

        try {
            sa = new InetSocketAddress(InetAddress.getByName(lip), 9002);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // 1 - socket
        System.out.println("socket créé");


        // 2 - connect
        try {
            socket = new Socket();
            socket.connect(sa);
            System.out.println("socket connecté");

            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bytes = new byte[1024];

        System.out.println("envoi des données - début");
        try {

            out.writeLong(file.length());
            if(file.length()>0) {
                while ((count = fis.read(bytes)) > 0) {
                    out.write(bytes, 0, count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("envoi des données - fin");

        try {
            if(fis != null) fis.close();

            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


}
