package com.webserver;

import com.webserver.server.ServerController;

public class Main {

    public static void main(String[] args) {
        ServerController controller = new ServerController();
        controller.start();
    }
}
