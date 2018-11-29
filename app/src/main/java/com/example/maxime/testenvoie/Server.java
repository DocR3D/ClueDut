package com.example.maxime.testenvoie;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Maxime  on 29/11/2018.
 */

public class Server  {

    static ArrayList<Player> players;

    static int nbJoueur;


    public Server(int nbJoueur) throws IOException {
        players = new ArrayList<>();
        this.nbJoueur = nbJoueur;
        Thread thrConnexion = new Thread(new ThreadConnection());
        thrConnexion.start();
    }



    public static int getNbJoueur() {
        return nbJoueur;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }


}

class ThreadConnection implements Runnable {
    ServerSocket socketEcoute;
    DataInputStream in = null;

    public void run() {
        try {
            socketEcoute = new ServerSocket(9090, Server.getNbJoueur());
            ajouterJoueur(socketEcoute.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ajouterJoueur(Socket socket){
        Server.getPlayers().add(new Player(socket));
    }
}
