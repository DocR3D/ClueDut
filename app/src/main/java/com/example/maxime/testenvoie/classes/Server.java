
package com.example.maxime.testenvoie.classes;

import android.util.Log;

import com.example.maxime.testenvoie.EnqueteActivity;
import com.example.maxime.testenvoie.JouerActivity;
import com.example.maxime.testenvoie.Menu;
import com.example.maxime.testenvoie.SalonCreerPartie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Handler;


/**
 * Created by Maxime  on 29/11/2018.
 */

public class Server implements Runnable{

    public enum Command {CONNECT, DISCONNECT, COLOR, READY, NOTREADY, HYP, ACS, NO, YES, MOVE, START, DICE, INITJEU, NULL}

    protected static ArrayList<Carte> cartesReponses = new ArrayList();


    protected static final int           SERVEUR_TCP_PORT = 8001;  // port d'écoute du serveur

    protected static final int           MAX_COLORS       = 4;     // nombre max de couleurs
    public static final int              NB_PLAYERS       = 4;     // nombre de joueurs de la partie

    private static   Thread              thrConnexion     = null;  // thread de supervision du jeu
    protected static Thread[]            thrPlayers       = null;  // thread de réception pour chacun des joueurs

    protected static boolean             gameOver         = false; // fin de partie

    protected static Semaphore semClient        = null;  // commande reçue d'un joueur

    protected static LinkedList<Message> queueCommands    = null;  // file des commandes reçues

    protected static int                 nbPlayers        = 0;     // nombre de joueurs prêts à jouer
    public static Player[]               players          = null;  // joueurs

    protected static int playerTour = 0;

    public static boolean[]           colorsAvailable  = null;  // couleurs disponibles
    protected static JeuDeCarte unJeuDeCarte = new JeuDeCarte();

    private static String getAvalaibleColors() {
        // calcul des couleurs disponibles
        String colors = new String();

        for (int i = 0; i < Server.colorsAvailable.length; i++)
            if (Server.colorsAvailable[i])
                colors += " " + i;

        return colors;
    }

    private static boolean compareCard(String cartePersonnage, String carteArme, String carteSalle){
        return cartesReponses.get(0).getNom().equals(cartePersonnage) && cartesReponses.get(1).getNom().equals(carteArme) && cartesReponses.get(2).getNom().equals(carteSalle);
    }

    protected static void sendToPlayer(int player, String response) {
        // envoi d'un message à un joueur
        Server.players[player].getWriter().println(response);
    }

    protected static void sendToAllPlayers(int excludedPlayer, String response) {
        // envoi d'un message à l'ensemble des joueurs
        for (int i = 0; i < Server.nbPlayers; i++)
            if ((i != excludedPlayer) && (!Server.players[i].disconnected()))
                Server.players[i].getWriter().println(response);
    }

    public Server() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        ServerSocket socketEcoute = null;  // socket d'ecoute
        ArrayList JeuDeCartes = new ArrayList<Carte>();

        int player;                         // numéro de joueur ayant envoyé une commande
        int playersReady = 0;    // joueurs ayant fourni un pseudo et choisi une couleur
        String response = null; // réponse envoyée au joueur
        String responseToAllPlayers = null; // réponse envoyée à l'ensemble des joueurs
        Message msg = null; // message reçu d'un joueur
        String[] items = null; // éléments de la commande reçue


        // création du sémaphore
        semClient = new Semaphore(0);

        // initialisation de la file des messsages client
        queueCommands = new LinkedList<Message>();

        // initialisation du nombre de joueurs
        players = new Player[NB_PLAYERS];
        thrPlayers = new Thread[NB_PLAYERS];
        cartesReponses = unJeuDeCarte.takeAllTypeCard();
        unJeuDeCarte.melanger();

        // initialisation des couleurs disponibles
        colorsAvailable = new boolean[Server.MAX_COLORS];
        for (int i = 0; i < MAX_COLORS; i++) colorsAvailable[i] = true;

        // Création du socket d'écoute
        try {
            socketEcoute = new ServerSocket(SERVEUR_TCP_PORT, NB_PLAYERS);
        } catch (IOException e) {
            System.out.println("Démarrage du serveur impossible !");
            e.printStackTrace();
            return;
        }

        // affichage des informations du socket d'écoute
        System.out.println("Serveur TCP en écoute sur l'IP    " + socketEcoute.getInetAddress().toString());
        System.out.println("Serveur TCP en écoute sur le port " + socketEcoute.getLocalPort());

        // création du thread de connexion
        thrConnexion = new Thread(new ThreadConnection(socketEcoute));

        // démarrage du thread de connexion
        thrConnexion.start();

        System.out.println("Démarrage du thread de connexion");

        // tant que le nobre de joueurs de la partie n'est pas atteint
        while (playersReady < NB_PLAYERS) {

            // attente de réception d'une commande d'un joueur
            Server.semClient.P();

            // obtention du numéro de joueur
            msg = Server.queueCommands.poll();
            player = msg.getIndex();

            // obtention des élements de la commande
            items = msg.getCommandItems();

            // examen de la commande
            switch (msg.getCommand()) {
                case CONNECT:
                    response = new String("OK");
                    //responseToAllPlayers = new String(" " + player + " " + items[1]);

                    Server.players[player].setPseudo(items[1]);
                    Server.players[player].setState(Player.State.CONNECTED);

                    // calcul des couleurs disponibles
                    response += getAvalaibleColors();

                    // envoi de la réponse au joueur
                    Server.sendToPlayer(player, response);

                    // envoi aux autres joueurs du pseudo
                    //Server.sendToAllPlayers(player, responseToAllPlayers);
                    break;

                case COLOR:
                    int color = Integer.valueOf(items[1]);

                    if (Server.colorsAvailable[color]) {
                        response = new String("COLOR " + color + " " + player + " " + Server.players[0].getPseudo() + " " + Server.players[0].getColor());
                        responseToAllPlayers = new String("PLAYER " + " " + player + " " + color);

                        Server.players[player].setColor(color);
                        Server.colorsAvailable[color] = false;

                        // envoi de la réponse au joueur
                        sendToPlayer(player, response);

                        if (player == 0){
                            response = new String("PLAYER " + " " + player + " " + color);
                            sendToPlayer(player, response);
                        }

                        // calcul des couleurs disponibles
                        //responseToAllPlayers += getAvalaibleColors();

                        // envoi aux autres joueurs des couleurs disponibles
                        sendToAllPlayers(player, responseToAllPlayers);

                    } else {
                        response = new String("NOK");

                        // calcul des couleurs disponibles
                        response += getAvalaibleColors();

                        // envoi de la réponse au joueur
                        Server.sendToPlayer(player, response);
                    }
                    break;

                case READY:
                    Server.players[player].setState(Player.State.READY);
                    playersReady ++;
                    if (player != 0)
                        Server.sendToAllPlayers(player, "READY");
                    break;

                case NOTREADY:
                    Server.players[player].setState(Player.State.NOTREADY);
                    playersReady --;
                    Server.sendToAllPlayers(player, "NOTREADY");
                    break;

                default:
                    response = new String("NOK");

                    // envoi de la réponse au joueur
                    Server.sendToPlayer(player, response);
            }
        }
        // la partie peut démarrer

        // démarrage de la partie
        System.out.println("Le jeu peut démarrer");
        // distribution des cartes
        while (unJeuDeCarte.getSizeJeuDeCarte() + 1 > 1) {
            for (int i = 0; i < Server.NB_PLAYERS; i++) {
                if (unJeuDeCarte.getSizeJeuDeCarte() != 0) {
                    Carte uneCarte = unJeuDeCarte.takeCard();
                    Server.players[i].addLeJeuDeCarteDuJoueur(uneCarte);
                    response = new String("CARTE " + " " + uneCarte.getNom() + " " + uneCarte.getType());
                    Server.sendToPlayer(i,response);
                }
            }
        }
        System.out.println(gameOver);
        // tant que le jeu n'est pas terminÃ©
        while (!gameOver) {
            System.out.println("Ca rentre ici");
            for (int i = 0; i < Server.NB_PLAYERS; i++) {
                Server.playerTour = i;
                // attente de rÃ©ception d'une commande d'un joueur
                Server.semClient.P();
            }

        }

            Server.gameOver = true;
                // fintantque

                // fermeture du socket d'Ã©coute
                try {
                    socketEcoute.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // attente de terminaison des threads
                try {
                    for (int i = 0; i < nbPlayers; i++) {
                        thrPlayers[i].join();
                        nbPlayers--;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Fermeture du serveur");
        }


    class ThreadConnection implements Runnable {
        ServerSocket socketEcoute;

        public ThreadConnection(ServerSocket socketEcoute) {
            this.socketEcoute = socketEcoute;
        }

        // exécution du thread
        public void run() {
            while (! Server.gameOver) {
                try {
                    int i;
                    InetAddress IP;

                    // attente de connection d'un joueur
                    Socket socket = this.socketEcoute.accept();
                    IP = socket.getInetAddress();

                    if (Server.nbPlayers == Server.NB_PLAYERS) {
                        // le nombre maximum de joueurs est atteint
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("REJECTED");
                        out.close();
                        socket.close();
                    }
                    else {
                        // création d'un joueur
                        System.out.println("Connection du joueur " + Server.nbPlayers);

                        Server.players[Server.nbPlayers] = new Player(socket);

                        // création d'un thread de communication avec le joueur
                        Server.thrPlayers[Server.nbPlayers] = new Thread(new ThreadPlayer(Server.nbPlayers));

                        // démarrage du thread
                        Server.thrPlayers[Server.nbPlayers].start();

                        System.out.println("Lancement d'un thread client");

                        // incrémentation du nombre de joueurs
                        Server.nbPlayers++;
                    }
                //}
			}
                catch (IOException e) {
                    if (Server.nbPlayers < Server.NB_PLAYERS) {
                        System.out.println("Communication avec le client impossible !");
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }

    class ThreadPlayer implements Runnable {
        private Player player;    // le joueur
        private int numJoueur; // son numéro

        private BufferedReader in;        // lecteur sur le socket

        public ThreadPlayer(int numJoueur) {
            this.player = Server.players[numJoueur];
            this.numJoueur = numJoueur;
            this.in = this.player.getReader();
            System.out.println("Client #" + this.numJoueur + " " + Server.players[numJoueur].getSocket().getInetAddress().getHostAddress() + " " + Server.players[numJoueur].getSocket().getPort() + " connecté...");
        }

        // exécution du thread
        public void run() {
            String command = null; // ligne de commande
            Message message = null; // message reçu d'un joueur

            try {
                // attente de réception d'une commande
                        while ((command = in.readLine()) != null) {
                    System.out.println("Joueur " + this.numJoueur + " Command = " + command);

                    // construction d'un nouveau message
                    message = new Message(this.numJoueur, command, Server.players[numJoueur].getState());

                    String [] items = message.getCommandItems();

                    // examen de la commande
                    switch (message.getCommand()) {
                        case HYP :
                            Server.sendToAllPlayers(numJoueur,"HYP " + items[1] + " " + items[2] + " " + items[3] + " " + numJoueur);
                            Server.semClient.V();
                            break;

                        case NO:
                            Server.sendToPlayer(Integer.valueOf(items[1]),"NO " + numJoueur);
                            break;
                        case YES:
                            Server.sendToPlayer(Integer.valueOf(items[2]), "YES " + items[1]);
                            break;

                        case START:
                                for (int i = 0; i < Server.nbPlayers; i++) {
                                    String response = new String("NUMCOLOR " + " " + i + " " + Server.players[i].getColor());
                                    sendToPlayer(i, response);
                                    response = new String("NUMCOLOR " + " " + i + " " + Server.players[i].getColor());
                                    sendToAllPlayers(i, response);
                                }
                                Server.sendToAllPlayers(this.numJoueur, "START");
                            SalonCreerPartie.lancerJeu();
                            JouerActivity.affecterJoueur();
                            break;

                        case DICE:
                            if(verifTour(numJoueur)) {
                                int dice = (int) (Math.random() * 5) + 1;
                                String response = new String("DICE" + " " + dice);
                                Server.sendToPlayer(this.numJoueur, response);
                            }
                            break;

                        case INITJEU:
                            String response = new String("INITFICHE");
                            Server.sendToPlayer(this.numJoueur, response);
                            break;

                        case ACS:
                                if (Server.compareCard("" + items[1], items[2], items[3])) {
                                    Server.sendToPlayer(this.numJoueur, "WON");
                                    Server.sendToAllPlayers(this.numJoueur, "GAMEOVER");
                                    for (int k = 0; k < Server.NB_PLAYERS; k++)
                                        Server.thrPlayers[k].interrupt();
                                    gameOver = true;
                                    break;
                                } else {
                                    Server.sendToPlayer(this.numJoueur, "GAMEOVER");
                                    Server.nbPlayers--;
                                    Server.thrPlayers[this.numJoueur].interrupt();
                                    if (Server.nbPlayers == 1){
                                        Server.sendToAllPlayers(this.numJoueur, "WON");
                                    }
                            }
                            Server.semClient.V();
                            break;

                        default:
                            // ajout de la commande reçue à la file
                            Server.queueCommands.add(message);

                            // information du superviseur de la disponibilité d'une commande
                            Server.semClient.V();
                            break;
                    }
                }
                if (command == null) {
                    System.out.println("Déconnexion du joueur " + numJoueur);

                    Server.players[this.numJoueur].setDisconnected();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean verifTour(int joueur) {
        System.out.println(Server.playerTour + " " + joueur);
        if (Server.playerTour == joueur) {
            System.out.println("Je suis la");
            return true;
        } else {
            return false;
        }
    }
}
