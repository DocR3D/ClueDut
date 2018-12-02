package com.example.maxime.testenvoie;

import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by i174085 on 29/11/2018.
 */

public class Client implements Runnable {

    static Socket leSocket;
    static PrintWriter out = null;
    static BufferedReader in = null;
    InetAddress ip;
    String pseudo;

    File leFichier;

    public Client(InetAddress ip, String pseudo) throws IOException {
        leSocket = new Socket();
        this.ip = ip;
        this.pseudo = pseudo;
    }

    public static Socket getLeSocket() {
        return leSocket;
    }

    public void recevoirFichier(){
        DataOutputStream fos = null;

        Socket socket = leSocket;
        DataInputStream fis = null;
        byte[] bytes = null;
        int count;
        long total = 0;
        long size;


        leFichier = new File("/sdcard/map/mapRecu");

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
            fis = new DataInputStream(new BufferedInputStream(leSocket.getInputStream()));

            bytes = new byte[1024];

            System.out.println("réception des données - début");
            size = fis.readLong();
            System.out.println("réception de " + size + " octets");

            while ((count = fis.read(bytes)) > 0) {
                fos.write(bytes, 0, count);
                total += count;
            }
            System.out.println("réception des données - fin");
            System.out.println(total + " octets reçus");

            fos.close();

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void extraireFichier() {

        ZipManager zipManager = new ZipManager();
        zipManager.unzip("/sdcard/map/mapRecu", "/sdcard/map/");

    }
    @Override
    public void run() {
        try {
            Client.getLeSocket().connect(new InetSocketAddress(ip, 9090));
            Client.in = new BufferedReader(new InputStreamReader(Client.leSocket.getInputStream()));
            Client.out = new PrintWriter(Client.getLeSocket().getOutputStream(), true);
            out.print(pseudo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recevoirFichier();
        extraireFichier();
    }




}



