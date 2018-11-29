package com.example.maxime.testenvoie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by i174085 on 29/11/2018.
 */

class Player {
    Socket leSocket;
    InetAddress ip;
    String pseudo;
    int couleur;
    private BufferedReader in;                   // lecture dans le socket
    private PrintWriter out;                  // ecriture dans le socket

    public Player(Socket leSocket){
        this.leSocket = leSocket;
        this.ip = leSocket.getInetAddress();

        try {
            this.in = new BufferedReader(new InputStreamReader(this.leSocket.getInputStream()));
            this.out = new PrintWriter(this.leSocket.getOutputStream(), true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
}