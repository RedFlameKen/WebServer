package com.webserver.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Network {

    public static final String IP_ADDRESS = "127.0.0.1";
    public static final int PORT = 3141;

    public static ServerSocket createServerSocket(){
        try {
            ServerSocket socket = new ServerSocket(PORT);
            socket.setReuseAddress(true);
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
