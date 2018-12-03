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

    public Player(Socket leSocket, String pPseudo) throws IOException {
        this.leSocket = leSocket;
        this.ip = leSocket.getInetAddress();
        this.couleur = -1;
        this.pseudo = pPseudo;

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
