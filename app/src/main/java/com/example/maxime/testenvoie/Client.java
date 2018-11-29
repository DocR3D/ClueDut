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
    Socket leSocket;
    PrintWriter out = null;
    BufferedReader in  = null;

    public Client(InetAddress ip) throws IOException {
        leSocket = new Socket();
        leSocket.connect(new InetSocketAddress(ip,9090));
        in  = new BufferedReader(new InputStreamReader(leSocket.getInputStream()));
        out = new PrintWriter(leSocket.getOutputStream(), true);
    }
}
