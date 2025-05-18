package com.webserver.async;

import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import com.webserver.transaction.Transaction;

public class SharedResource {

    public LinkedList<Transaction> transactionQueue;
    public ServerSocket serverSocket;
    public Semaphore semaphore;

    public SharedResource(LinkedList<Transaction> transactionQueue, ServerSocket serverSocket, Semaphore semaphore) {
        this.transactionQueue = transactionQueue;
        this.serverSocket = serverSocket;
        this.semaphore = semaphore;
    }

    public static void lockSemaphore(Semaphore semaphore){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void unlockSemaphore(Semaphore semaphore){
        semaphore.release();
    }

}
