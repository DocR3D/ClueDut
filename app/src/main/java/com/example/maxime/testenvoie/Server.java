package com.example.maxime.testenvoie;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Maxime  on 29/11/2018.
 */

public class Server  {
    static ArrayList<Player> players = new ArrayList<>();
    static int nbJoueur;

    public Server(int nbJoueur) throws IOException {
        this.nbJoueur = nbJoueur;
            Thread thrConnexion = new Thread(new ThreadConnection());
            thrConnexion.start();

    }

    public static int getNbJoueur() {
        return nbJoueur;
    }

    public static void addPlayers(Player unJoueur) {
        players.add(unJoueur);
    }
}

class ThreadConnection implements Runnable {
    ServerSocket socketEcoute;
    public void run() {
        try {
            socketEcoute = new ServerSocket(9090,4);

            for(int i=0; i<Server.getNbJoueur();i++) ajouterJoueur(socketEcoute.accept());

            socketEcoute.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ajouterJoueur(Socket socket){
        Server.addPlayers(new Player(socket));
    }
}
