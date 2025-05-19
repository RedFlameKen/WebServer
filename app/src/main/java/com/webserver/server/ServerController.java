package com.webserver.server;

import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

import com.webserver.async.SharedResource;
import com.webserver.async.ThreadController;
import com.webserver.db.DatabaseManager;

public class ServerController {

    private ServerSocket serverSocket;
    private SharedResource sharedResource;
    private Semaphore semaphore;
    private ThreadController threadController;
    private DatabaseManager databaseManager;

    public ServerController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        initComponents();
    }

    private void initComponents(){
        serverSocket = Network.createServerSocket();
        semaphore = new Semaphore(1);
        sharedResource = new SharedResource(serverSocket, databaseManager, semaphore);
        threadController = new ThreadController(sharedResource);
    }

    public void start(){
        threadController.startController();
    }

}
