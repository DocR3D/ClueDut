package com.example.maxime.testenvoie.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by i174085 on 29/11/2018.
 */

class Player {
    private ArrayList<Carte> leJeuDeCarteDuJoueur = new ArrayList();       // cartes armes // état du joueur

    private Socket         socket;               // socket de communication
    private InetAddress    IP;                   // IP du joueur
    private boolean        disconnected = false; // indicateur de déconnexion

    private BufferedReader in;                   // lecture dans le socket
    private PrintWriter    out;                  // ecriture dans le socket

    private State          state;                // état du joueur
    private boolean        gameLost = false;     // je joueur a perdu mais la partie continue
    private String         pseudo = null;        // pseudo du joueur
    private int            color;                // couleur du joueur

    public Carte getLeJeuDeCarteDuJoueur(int numeroCarte) {
        return leJeuDeCarteDuJoueur.get(numeroCarte);
    }


    public Player(Socket socket) {
        setSocket(socket);

        this.state = State.DISCONNECTED; // le joueur ne s'esy pas encore identifié
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        this.IP     = socket.getInetAddress();

        try {
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.disconnected = false;
    }

    public void setDisconnected() {
        this.disconnected = true;
    }

    public boolean disconnected() {
        return this.disconnected;
    }

    public InetAddress getIP() {
        return this.IP;
    }

    public BufferedReader getReader() {
        return this.in;
    }

    public PrintWriter getWriter() {
        return this.out;
    }

    public void closeFlux() {
        try {
            this.in.close();
            this.out.close();

            this.socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addLeJeuDeCarteDuJoueur(Carte uneCarte) {
        leJeuDeCarteDuJoueur.add(uneCarte);
    }

    public enum State {DISCONNECTED, CONNECTED, READY, NOTREADY}
}
