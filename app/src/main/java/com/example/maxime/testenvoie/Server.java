package com.example.maxime.testenvoie;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Maxime  on 29/11/2018.
 */

public class Server implements Runnable {
    //Information indispensable
    static int nbJoueur;
    static ArrayList<Player> players = new ArrayList<>();

    //Pour le serveur
    ServerSocket socketEcoute;
    static PrintWriter out = null;
    static BufferedReader in = null;

    //Pour le fichier zip
    DataOutputStream fos = null;
    DataInputStream fis = null;

    byte[] bytes = null;
    int count;
    long total = 0;
    long size;

    File leFichier = new File("/sdcard/map/map");

    public Server(int nbJoueur) throws IOException {
        this.nbJoueur = nbJoueur;
        Thread thrConnexion = new Thread(this);
        thrConnexion.start();

        String[] s = new String[20];
        s[0] = "/sdcard/cuisine.png";
        s[1] = "/sdcard/sallemanger1.png";
        s[2] = "/sdcard/sallemanger2.png";
        s[3] = "/sdcard/salon1.png";
        s[4] = "/sdcard/salon2.png";
        s[5] = "/sdcard/bibliotheque1.png";
        s[6] = "/sdcard/bibliotheque2.png";
        s[7] = "/sdcard/bibliotheque3.png";
        s[8] = "/sdcard/bureau.png";
        s[9] = "/sdcard/clickcase.png";
        s[10] = "/sdcard/cluedo.png";
        s[11] = "/sdcard/deplacement.png";
        s[12] = "/sdcard/fond.png";
        s[13] = "/sdcard/hall.png";
        s[14] = "/sdcard/pawn.png";
        s[15] = "/sdcard/sallebal1.png";
        s[16] = "/sdcard/sallebal2.png";
        s[17] = "/sdcard/sallebillard.png";
        s[18] = "/sdcard/veranda1.png";
        s[19] = "/sdcard/veranda2.png";
        //s[1] = "/sdcard/map/unText.txt";
        ZipManager zipManager = new ZipManager();
        zipManager.zip(s, "/sdcard/map/map");

    }

    public static int getNbJoueur() {
        return nbJoueur;
    }


    public void run() {
        try {
            socketEcoute = new ServerSocket(9090, 4);


            for (int i = 0; i < Server.getNbJoueur(); i++) {
                Socket leSocket = socketEcoute.accept();
                ajouterJoueur(leSocket);
                envoieFichier(leSocket);
            }
            recupererJoueur();
            socketEcoute.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recupererJoueur() throws IOException {
        String lesJoueurs = "";
        for (Player unJoueur : players) {
            in = new BufferedReader(new InputStreamReader(unJoueur.leSocket.getInputStream()));
            out = new PrintWriter(unJoueur.leSocket.getOutputStream(), true);;
            lesJoueurs = lesJoueurs + " " + in.readLine();
            out.write(lesJoueurs);
            in.close();
            out.close();
        }
        Log.e("Joueurs ", lesJoueurs);
    }

    public void ajouterJoueur(Socket socket) throws IOException {
        addPlayers(new Player(socket));
    }

    public static void addPlayers(Player unJoueur) {
        players.add(unJoueur);
    }

    public void envoieFichier(Socket leSocket) throws IOException {
        try {
            fis = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(leFichier)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Pas de fichier !");
            System.exit(-1);
            System.out.println("Taille du fichier = " + leFichier.length());
        }

        fos = new DataOutputStream(new BufferedOutputStream(leSocket.getOutputStream()));


        bytes = new byte[1024];

        System.out.println("envoi des données - début");
        try {

            fos.writeLong(leFichier.length());
            if(leFichier.length()>0) {
                while ((count = fis.read(bytes)) > 0) {
                    fos.write(bytes, 0, count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("envoi des données - fin");

        try {
            if(fis != null) fis.close();

            if (fos != null) fos.close();
            if (leSocket != null) leSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
