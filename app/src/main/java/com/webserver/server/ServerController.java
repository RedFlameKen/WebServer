package com.webserver.server;

import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import com.webserver.async.SharedResource;
import com.webserver.async.ThreadController;
import com.webserver.transaction.Transaction;

public class ServerController {

    private ServerSocket serverSocket;
    private LinkedList<Transaction> transactionQueue;
    private SharedResource sharedResource;
    private Semaphore semaphore;
    private ThreadController threadController;

    public ServerController(){
        initComponents();
    }

    private void initComponents(){
        serverSocket = Network.createServerSocket();
        transactionQueue = new LinkedList<Transaction>();
        semaphore = new Semaphore(1);
        sharedResource = new SharedResource(transactionQueue, serverSocket, semaphore);
        threadController = new ThreadController(sharedResource);
    }

    public void start(){
        threadController.startController();
    }

}
