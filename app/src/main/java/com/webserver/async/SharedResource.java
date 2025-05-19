package com.webserver.async;

import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

import com.webserver.db.DatabaseManager;

public class SharedResource {

    public ServerSocket serverSocket;
    public Semaphore semaphore;
    public DatabaseManager databaseManager;

    public SharedResource(ServerSocket serverSocket, DatabaseManager databaseManager, Semaphore semaphore) {
        this.serverSocket = serverSocket;
        this.databaseManager = databaseManager;
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
