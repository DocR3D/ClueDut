package com.example.maxime.testenvoie;

import java.net.Socket;

/**
 * Created by i174085 on 29/11/2018.
 */

class Player {
    Socket leSocket;
    String ip;
    String pseudo;
    int couleur;

    public Player(Socket leSocket){
        this.leSocket = leSocket;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
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
