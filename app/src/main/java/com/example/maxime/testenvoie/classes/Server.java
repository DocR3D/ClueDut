
package com.example.maxime.testenvoie.classes;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.example.maxime.testenvoie.classes.Server.Command.ANSWER;


/**
 * Created by Maxime  on 29/11/2018.
 */

public class Server implements Runnable{

    public enum Command {CONNECT, DISCONNECT, COLOR, READY, NOTREADY, HYP, ACS, ANSWER, MOVE, NULL}

    protected static ArrayList<Carte> cartesReponses = new ArrayList();


    protected static final int           SERVEUR_TCP_PORT = 8001;  // port d'écoute du serveur

    protected static final int           MAX_COLORS       = 6;     // nombre max de couleurs
    protected static final int           NB_PLAYERS       = 4;     // nombre de joueurs de la partie

    private static   Thread              thrConnexion     = null;  // thread de supervision du jeu
    protected static Thread[]            thrPlayers       = null;  // thread de réception pour chacun des joueurs

    protected static boolean             gameOver         = false; // fin de partie

    protected static Semaphore semClient        = null;  // commande reçue d'un joueur

    protected static LinkedList<Message> queueCommands    = null;  // file des commandes reçues

    protected static int                 nbPlayers        = 0;     // nombre de joueurs prêts à jouer
    public static Player[]               players          = null;  // joueurs

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
        return cartesReponses.get(0).getNom() == cartePersonnage && cartesReponses.get(1).getNom() == carteArme && cartesReponses.get(2).getNom() == carteSalle;
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

        unJeuDeCarte.melanger();
        // création du sémaphore
        semClient = new Semaphore(0);

        // initialisation de la file des messsages client
        queueCommands = new LinkedList<Message>();

        // initialisation du nombre de joueurs
        players = new Player[NB_PLAYERS];
        thrPlayers = new Thread[NB_PLAYERS];
        cartesReponses = unJeuDeCarte.takeAllTypeCard();

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
                        response = new String("COLOR " + color + " " + player);
                        responseToAllPlayers = new String("PLAYER " + " " + player + " " + color);

                        Server.players[player].setColor(color);
                        Server.colorsAvailable[color] = false;
                        Server.players[player].setState(Player.State.READY);

                        // envoi de la réponse au joueur
                        sendToPlayer(player, response);

                        // calcul des couleurs disponibles
                        //responseToAllPlayers += getAvalaibleColors();

                        // envoi aux autres joueurs des couleurs disponibles
                        sendToAllPlayers(player, responseToAllPlayers);

                        // un joueur supplémentaire est prêt à jouer
                        playersReady++;
                        System.out.println("Nombre de joueurs prêts " + playersReady);
                    } else {
                        response = new String("COLORS");

                        // calcul des couleurs disponibles
                        response += getAvalaibleColors();

                        // envoi de la réponse au joueur
                        Server.sendToPlayer(player, response);
                    }
                    break;

                default:
                    response = new String("NOK");

                    // envoi de la réponse au joueur
                    Server.sendToPlayer(player, response);
            }
        }
        // la partie peut démarrer

        // tri des joueurs selon l'ordre des couleurs
        for (int i = 1; i < Server.nbPlayers; i++)
            for (int k = 0; k < i; k++)
                if (Server.players[k].getColor() > Server.players[k + 1].getColor()) {
                    int color = Server.players[k].getColor();
                    Server.players[k].setColor(Server.players[k + 1].getColor());
                    Server.players[k + 1].setColor(color);
                }

        // dÃ©marrage de la partie
        System.out.println("Le jeu peut démarrer");
        // distribution des cartes
        while (unJeuDeCarte.getSizeJeuDeCarte() != 0) {
            for (int i = 0; i < Server.NB_PLAYERS; i++)
                Server.players[i].addLeJeuDeCarteDuJoueur(unJeuDeCarte.takeCard());
        }
        // tant que le jeu n'est pas terminÃ©
        while (!gameOver) {
            for (int i = 0; i < Server.nbPlayers; i++) {
                // pour chacun des joueurs
                int dice = (int) (Math.random() * 5) + 1;
                // envoi des dés

                Server.players[i].getWriter().println("DICE " + dice);

                // attente de la rÃ©ponse du joueur (dÃ©placement + hypothÃ¨se)

                // attente de rÃ©ception d'une commande d'un joueur
                Server.semClient.P();

                // obtention du numÃ©ro de joueur
                msg = Server.queueCommands.poll();
                player = msg.getIndex();

                items = msg.getCommandItems();

                // examen de la commande
                switch (msg.getCommand()) {

                    case MOVE:
                        for (int j = 0; j < Server.nbPlayers; j++){
                            if (j != player){
                                Server.players[j].getWriter().println("MOVE " + player + items[0]);
                            }
                        }
                    // si accusation
                    // alors
                    // si confirmation
                    // alors
                    // envoi GAMEOVER WON au joueur ayant formulÃ© l'hypothÃ¨se
                    // envoi GAMEOVER Ã  tous les autres joueurs
                    // arrÃªt des threads Player
		/*
								for (int k = 0; k < Server.NB_PLAYERS; i++)
									Server.thrPlayers[k].interrupt();
		*/
                    case ACS:
                        if (Server.compareCard(items[0], items[1], items[2])) {
                            Server.players[player].getWriter().println("WON");
                            for (int j = 0; j < Server.nbPlayers; j++ ){
                                if (j != player)
                                    Server.players[j].getWriter().println("GAMEOVER");
                            }
                            for (int k = 0; k < Server.NB_PLAYERS; k++)
                                Server.thrPlayers[k].interrupt();
                            gameOver = true;
                            break;
                        } else {
                            Server.players[player].getWriter().println("GAMEOVER");
                            Server.thrPlayers[player].interrupt();
                        }

                        // sinon
                        // je ne sais pas ce que l'on fait
                        // fsi
                    case HYP :
                            for (int j = 0; j < Server.nbPlayers; j++){
                                if (j != player){
                                    Server.players[j].getWriter().println("HYP : P A L");
                                    Server.semClient.P();
                                    msg = Server.queueCommands.poll();
                                    int player1 = msg.getIndex();
                                    items = msg.getCommandItems();
                                    if(msg.getCommand() == ANSWER){
                                        Server.players[player].getWriter().println("ANSWER " + items[0]);
                                        if (items[0] != null){
                                            for (int k = 0; k < Server.nbPlayers; k++){
                                                if (k != player && k != player1)
                                                    Server.players[k].getWriter().println("REPPLY " + player1 + " affirmative");
                                            }
                                        } else {
                                            for (int k = 0; k < Server.nbPlayers; k++){
                                                if (k != player && k != player1)
                                                    Server.players[k].getWriter().println("REPPLY " + player1 + " negative");
                                            }
                                        }
                                    }
                                }
                            }

                        // sinon
                        // envoi Ã  tous les autres joueurs du dÃ©placement + de l'hypothÃ¨se
                        // pour chacun des autres joueurs
                        // demande d'une carte (personnage salle arme)
                        // attente de la rÃ©ponse
                        // envoi de la carte au joueur ayant formulÃ© l'hyptohÃ¨se
                        // envoi aux autres joueurs d'une rÃ©ponse affirmative ou nÃ©gative
                        // finpour
                        // finsi
                        //}
                }
            }
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
                    Log.e("ThredCo","Ok");
                    Socket socket = this.socketEcoute.accept();
                    Log.e("ThredCo","Ok");
                    IP = socket.getInetAddress();

                    /*partie permettant de déterminer l'IP du joueur
 * et de déterminer s'il s'agit d'une reconnexion*/
				/*i = 0;
				// recherche d'une perte de connexion
				while ((i < Server.nbPlayers) && (! IP.equals(Server.players[i].getIP()))) i++;

				if (i < Server.nbPlayers) {
					// reconnexion d'un client
					System.out.println("Reconnection du joueur " + i);

					Server.players[i].setSocket(socket);

					// création d'un thread de communication avec le joueur
					Server.thrPlayers[i] = new Thread(new ThreadPlayer(i));

					// démarrage du thread
					Server.thrPlayers[i].start();

					System.out.println("Lancement d'un thread client");
				}
				else {*/
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

                    // examen de la commande
                    switch (message.getCommand()) {
                        case READY:
                            Server.players[numJoueur].setState(Player.State.READY);
                            Server.sendToAllPlayers(this.numJoueur, "READY " + this.numJoueur);
                            break;

                        case NOTREADY:
                            Server.players[numJoueur].setState(Player.State.NOTREADY);
                            Server.sendToAllPlayers(this.numJoueur, "NOTREADY " + this.numJoueur);
                            break;

                        default:
                            // ajout de la commande reçue à la file
                            Server.queueCommands.add(message);

                            // information du superviseur de la disponibilité d'une commande
                            Server.semClient.V();
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
}
