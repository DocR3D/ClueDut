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

public class Client {

    static Socket leSocket;
    static PrintWriter out = null;
    static BufferedReader in  = null;

    public Client(InetAddress ip) throws IOException {
        leSocket = new Socket();
        Thread unThread = new Thread(new thrConnexion());
    }

    public static Socket getLeSocket() {
        return leSocket;
    }

    public static  PrintWriter getOut() {
        return out;
    }

    public static BufferedReader getIn() {
        return in;
    }
}
class thrConnexion implements Runnable {



    @Override
    public void run() {
        Client.getLeSocket().connect(new InetSocketAddress(ip,9090));
        //Client.getIn() = new BufferedReader(new InputStreamReader(Client.leSocket.getInputStream()));
        //out = new PrintWriter(leSocket.getOutputStream(), true);
    }
}
