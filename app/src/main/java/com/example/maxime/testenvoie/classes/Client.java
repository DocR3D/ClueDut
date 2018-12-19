package com.example.maxime.testenvoie.classes;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by i174085 on 29/11/2018.
 */

public class Client implements Runnable{

    public enum Command {OK, NOK, PLAYER, COLORS, READY, NOTREADY, GAMEOVER, REJECTED, DICE};

    private static final String SERVEUR_TCP_IP   = new String("0.0.0.0");
    private static final int    SERVEUR_TCP_PORT = 8001;

    protected static boolean    gameOver         = false;
    protected static boolean    disconnected     = false;
    protected PrintWriter       out              = null;
    protected String            command          = null;

    private String ip;
    private String pseudo;

    public Client(String pseudo, String ip) {
        this.pseudo = pseudo;
        this.ip = ip;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void connect(){
        this.command = "Connect " + pseudo;
        this.out.println(command);
    }

    public void color(){
        this.command = "Color " + 2;
        this.out.println(command);
    }

    public void ready(){
        this.command = "Ready";
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
                if (items.length == 2)
                    return Client.Command.READY;

            if (items[0].compareToIgnoreCase("NOTREADY") == 0)
                if (items.length == 2)
                    return Client.Command.NOTREADY;

            if (items[0].compareToIgnoreCase("GAMEOVER") == 0)
                if (items.length == 1)
                    return Client.Command.GAMEOVER;

            if (items[0].compareToIgnoreCase("REJECTED") == 0)
                return Client.Command.REJECTED;

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
                    case GAMEOVER:
                        Client.gameOver = true;
                        break;

                    case REJECTED:
                        Client.disconnected = true;
                        break;

                    case DICE:
                        //Client.joueur.dice = items[1];

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



