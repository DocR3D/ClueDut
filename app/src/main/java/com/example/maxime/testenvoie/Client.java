package com.example.maxime.testenvoie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by i174085 on 29/11/2018.
 */

public class Client implements Runnable {

    static Socket leSocket;
    static PrintWriter out = null;
    static BufferedReader in = null;
    InetAddress ip;

    public Client(InetAddress ip) throws IOException {
        leSocket = new Socket();
        this.ip = ip;
        Thread unThread = new Thread(this);
        unThread.start();
    }

    public static Socket getLeSocket() {
        return leSocket;
    }

    public static void setOut(PrintWriter out) {
        Client.out = out;
    }

    public static void setIn(BufferedReader in) {
        Client.in = in;
    }

    @Override
    public void run() {
        try {
            Client.getLeSocket().connect(new InetSocketAddress(ip, 9090));
            Client.setIn(new BufferedReader(new InputStreamReader(Client.leSocket.getInputStream())));
            Client.setOut(new PrintWriter(Client.getLeSocket().getOutputStream(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



