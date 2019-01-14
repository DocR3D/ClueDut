package com.example.maxime.testenvoie.classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.maxime.testenvoie.EnqueteActivity;
import com.example.maxime.testenvoie.JouerActivity;
import com.example.maxime.testenvoie.Menu;
import com.example.maxime.testenvoie.SalonCreerPartie;
import com.example.maxime.testenvoie.SalonRejoindrePartie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by i174085 on 29/11/2018.
 */

public class Client implements Runnable{

    public enum Command {OK, NOK, PLAYER, COLORS, READY, NO, YES, HYP, NOTREADY, GAMEOVER, REJECTED, MOVE, DICE, COLOR, CARTE, START, NUMCOLOR, INITFICHE, WON};

    private static final String SERVEUR_TCP_IP   = new String("0.0.0.0");
    private static final int    SERVEUR_TCP_PORT = 8001;

    protected static boolean    gameOver         = false;
    protected static boolean    disconnected     = false;
    protected PrintWriter       out              = null;
    protected String            command          = null;

    public static int[]         couleurs       = {0,1,2,3};
    public int                  couleur          = -1;
    public int                  nbPlayerReady    = 0;

    private String ip;
    private String pseudo;
    private Joueur joueur;
    private int numJoueur;
    private List<Carte> jeuDeCarte = new ArrayList<>();

    public Client() {
    }

    public static int getCouleur(int numJoueur){
        return couleurs[numJoueur];
    }

    public Client(String ip) {
        this.ip = ip;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public void setCouleur(int couleur){
        this.couleur = couleur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Joueur getJoueur(){
        return this.joueur;
    }

    public void setNumJoueur(int num){
        this.numJoueur = num;
    }

    public int getNumJoueur(){
        return this.numJoueur;
    }

    public void setIP(String ip){
        this.ip = ip;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void connect(){
        command = "CONNECT " + pseudo;
        out.println(command);
    }

    public void hyp(String personnage, String arme, String lieu){
        command = "HYP" + " " + personnage + " " + arme + " " + lieu;
        out.println(command);
    }

    public void acs(String personnage, String arme, String lieu){
         command = "ACS " + personnage + " " + arme + " " + lieu;
        out.println(command);
    }

    public void dice(){
        command = "DICE";
        out.println(command);
    }

    public void no(int numJoueur){
        command = "NO" + " " + numJoueur;
        out.println(command);
    }

    public void yes(String rep, int numJoueur){
        command = "YES " + rep + " " + numJoueur;
        out.println(command);
    }

    public void move(int i, int j){
        command = "MOVE " + i + " " + j;
        out.println(command);
    }

    public void start(){
        command = "START";
        out.println(command);
    }

    public void initjeu(){
        command = "INITJEU";
        out.println(command);
    }

    public void color(int color){
        this.command = "COLOR " + color;
        this.out.println(command);
    }

    public void ready(){
        this.command = "READY";
        this.out.println(command);
    }

    public void run(){
        Thread     thrReceiver       = null;

        InetSocketAddress sa = null;

        Socket            sCom = null;
        BufferedReader    in  = null;
        int i = 0;

        try {
            sa = new InetSocketAddress(InetAddress.getByName(ip), SERVEUR_TCP_PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }

        try {
            // 1 - socket
            sCom = new Socket();

            // 2 - connect
            Log.e("Connection","préparé");
            sCom.connect(sa);
            Log.e("Connection","ok");
            in  = new BufferedReader(new InputStreamReader(sCom.getInputStream()));
            out = new PrintWriter(sCom.getOutputStream(), true);

            System.out.println("Connecté au serveur !");
            Log.e("connection","OK");

            thrReceiver = new Thread(new ThreadListener(in));
            thrReceiver.start();

            //Scanner scanner = new Scanner(socketEcoute.getInputStream());
            while ((! disconnected) && (! gameOver)) {

            }

            if (disconnected)
                System.out.println("Connexion refusée !");
            else
                System.out.println("Game Over !");

            //scanner.close();
            thrReceiver.join();

            in.close();
            out.close();

            sCom.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    class ThreadListener implements Runnable {
        BufferedReader in;

        public ThreadListener(BufferedReader in) {
            this.in = in;
        }

        private Client.Command checkCommand(String[] items) {
            if (items[0].compareToIgnoreCase("OK") == 0)
                if (items.length >= 1)
                    return Client.Command.OK;

            if (items[0].compareToIgnoreCase("NOK") == 0)
                if (items.length >= 1)
                    return Client.Command.NOK;

            if (items[0].compareToIgnoreCase("PLAYER") == 0)
                if (items.length == 3)
                    return Client.Command.PLAYER;

            if (items[0].compareToIgnoreCase("COLORS") == 0)
                if (items.length >= 2)
                    return Client.Command.COLORS;

            if (items[0].compareToIgnoreCase("READY") == 0)
                if (items.length == 1)
                    return Client.Command.READY;

            if (items[0].compareToIgnoreCase("NOTREADY") == 0)
                if (items.length == 2)
                    return Client.Command.NOTREADY;

            if (items[0].compareToIgnoreCase("GAMEOVER") == 0)
                if (items.length == 1)
                    return Client.Command.GAMEOVER;

            if (items[0].compareToIgnoreCase("REJECTED") == 0)
                return Client.Command.REJECTED;

            if (items[0].compareToIgnoreCase("COLOR") == 0)
                if (items.length == 5)
                    return Client.Command.COLOR;

            if (items[0].compareToIgnoreCase("START") == 0)
                if (items.length == 1)
                    return Client.Command.START;
            if (items[0].compareToIgnoreCase("DICE") == 0)
                if (items.length == 2)
                    return Client.Command.DICE;
            if (items[0].compareToIgnoreCase("NUMCOLOR") == 0)
                if (items.length == 3)
                    return Client.Command.NUMCOLOR;
            if (items[0].compareToIgnoreCase("INITFICHE") == 0)
                if (items.length == 1)
                    return Client.Command.INITFICHE;
            if (items[0].compareToIgnoreCase("WON") == 0)
                if (items.length == 1)
                    return Client.Command.WON;
            if (items[0].compareToIgnoreCase("CARTE") == 0)
                if (items.length == 3)
                    return Client.Command.CARTE;
            if (items[0].compareToIgnoreCase("MOVE") == 0)
                if (items.length == 4)
                    return Client.Command.MOVE;
            if (items[0].compareToIgnoreCase("NO") == 0)
                if (items.length == 2)
                    return Client.Command.NO;
            if (items[0].compareToIgnoreCase("YES") == 0)
                if (items.length == 2)
                    return Client.Command.YES;
            if (items[0].compareToIgnoreCase("HYP") == 0)
                if (items.length == 5)
                    return Client.Command.HYP;

            return null;
        }

        // exécution du thread
        public void run(){
            String msg = null;
            String[] items = null;

            while (!Client.gameOver) {
                try {
                    if ((msg = in.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Réponse du serveur : " + msg);

                items = msg.split(" +");

                switch (checkCommand(items)) {
                    case NO:
                        Toast.makeText(JouerActivity.context, "Le joueur " + items[1] + " n'a pas montré de carte",Toast.LENGTH_LONG).show();
                        break;

                    case YES:
                        for (int k = 0; k < EnqueteActivity.checkBoxs.length; k++){
                            if (items[1].equals(EnqueteActivity.checkBoxs[k].getText())){
                                EnqueteActivity.checkBoxs[k].setChecked(true);
                            }
                        }
                        Toast.makeText(JouerActivity.context, "Carte reçu : " + items[1],Toast.LENGTH_LONG).show();
                        break;

                    case HYP:
                        final List<String> rep = new ArrayList<>();
                        for (int i = 0; i < jeuDeCarte.size(); i++){
                            if (jeuDeCarte.get(i).getNom().equals(items[1])){
                                rep.add(items[1]);
                            }
                        }
                        for (int i = 0; i < jeuDeCarte.size(); i++){
                            if (jeuDeCarte.get(i).getNom().equals(items[2])){
                                rep.add(items[2]);
                            }
                        }
                        for (int i = 0; i < jeuDeCarte.size(); i++){
                            if (jeuDeCarte.get(i).getNom().equals(items[3])){
                                rep.add(items[3]);
                            }
                        }
                        final String[] finalItems = items;
                        Thread thread;
                        switch (rep.size()){
                            case 0:
                                thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no(Integer.valueOf(finalItems[4]));
                                    }
                                });
                                thread.start();
                                thread.interrupt();
                                break;
                            case 1:
                                thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        yes(rep.get(0), Integer.valueOf(finalItems[4]));
                                    }
                                });
                                thread.start();
                                thread.interrupt();
                                break;
                            default:
                                thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int i = (int) (Math.random() * rep.size());
                                        yes(rep.get(i), Integer.valueOf(finalItems[4]));
                                    }
                                });
                                thread.start();
                                thread.interrupt();
                                break;
                        }
                        break;

                    case GAMEOVER:
                        System.out.println("LOOOOOOOSE");
                        Client.gameOver = true;
                        JouerActivity.finLose();
                        break;

                    case WON:
                        System.out.println("WOOOOOOOOON");
                        Client.gameOver = true;
                        JouerActivity.finWin();
                        break;

                    case REJECTED:
                        Client.disconnected = true;
                        break;

                    case DICE:
                        Menu.client.getJoueur().deplacementJoueur(Integer.valueOf(items[1]));
                        break;

                    case PLAYER:
                        if (Menu.client.getPseudo().equals(Server.players[0].getPseudo())) {
                            SalonCreerPartie.afficherJoueur(Server.players[Integer.parseInt(items[1])].getPseudo(),
                                    Integer.valueOf(items[1]), Integer.valueOf(items[2]));
                        }
                        else {
                            SalonRejoindrePartie.afficherJoueur(Server.players[Integer.parseInt(items[1])].getPseudo(),
                                    Integer.valueOf(items[1]), Integer.valueOf(items[2]));
                        }
                        setNumJoueur(Integer.valueOf(items[1]));
                    break;

                    case COLOR:
                        setCouleur(Integer.valueOf(items[1]));
                        //SalonRejoindrePartie.afficherJoueur(getPseudo(),Integer.valueOf(items[2]), couleur);
                        if (Menu.client.getPseudo().equalsIgnoreCase(items[3])){
                        } else {
                            SalonRejoindrePartie.afficherJoueurHote(items[3],Integer.valueOf(items[4]));
                        }
                        break;

                    case COLORS:
                        int j =0;
                        for (int i = 0; i < Server.colorsAvailable.length; i++)
                            if (Server.colorsAvailable[i]){
                                couleurs[j] = Integer.valueOf(items[(i+1)]);
                                j++;
                            }
                        break;

                    case READY:
                        if (Menu.client.getPseudo().equalsIgnoreCase(Server.players[0].getPseudo())) {
                            nbPlayerReady += 1;
                            if (nbPlayerReady == Server.NB_PLAYERS - 1)
                                SalonCreerPartie.commencerPartie_btn.setEnabled(true);
                        }
                        break;

                    case NOTREADY:
                        if (Menu.client.getPseudo().equalsIgnoreCase(Server.players[0].getPseudo()))
                            nbPlayerReady -= 1;
                        SalonCreerPartie.commencerPartie_btn.setEnabled(false);
                        break;

                    case START:
                            SalonRejoindrePartie.lancerJeu();
                            JouerActivity.affecterJoueur();
                        break;

                    case NUMCOLOR:
                        System.out.println(Integer.valueOf(items[1]) + " + " + Integer.valueOf(items[2]));
                        Menu.client.couleurs[Integer.valueOf(items[1])] = Integer.valueOf(items[2]);
                        break;

                    case INITFICHE:
                            for (int i = 0; i < jeuDeCarte.size(); i++){
                                for (int k = 0; k < EnqueteActivity.checkBoxs.length; k++){
                                    if (jeuDeCarte.get(i).getNom().equals(EnqueteActivity.checkBoxs[k].getText())){
                                        EnqueteActivity.checkBoxs[k].setChecked(true);
                                    }
                                }
                            }
                        break;

                    case MOVE:
                        JouerActivity.moveJoueur(Integer.valueOf(items[1]), Integer.valueOf(items[2]), Integer.valueOf(items[3]));
                        break;

                    case CARTE:
                        Carte carte = null;
                        switch(items[2]){
                            case "PERSONNAGE":
                                carte = new Carte(items[1],Carte.Type.PERSONNAGE);
                                jeuDeCarte.add(carte);
                                break;
                            case "SALLE":
                                carte = new Carte(items[1],Carte.Type.PERSONNAGE);
                                jeuDeCarte.add(carte);
                                break;
                            case "ARME":
                                carte = new Carte(items[1],Carte.Type.PERSONNAGE);
                                jeuDeCarte.add(carte);
                                break;
                        }
                        break;

                    default:
                        break;
                }
            }
            if (msg == null) {
                System.out.println("Connexion fermée par le serveur");
                Client.gameOver = true;
            }
        }
    }
}



