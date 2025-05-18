package com.webserver.async;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.webserver.util.Logger;

/* TODO:
 * Constantly execute accept()
 * hand off the client Socket created to the ThreadController
*/
public class ListenerThread extends ServerThread {

    public ListenerThread(SharedResource sharedResource, ThreadController threadController) {
        super(sharedResource, threadController);
    }

    @Override
    protected void task(){
        System.out.printf("started listener thread\n");
        while(taskRunning){
            System.out.printf("listening for a client\n");

            ServerSocket serverSocket = null;

            SharedResource.lockSemaphore(sharedResource.semaphore);

            serverSocket = sharedResource.serverSocket;

            SharedResource.unlockSemaphore(sharedResource.semaphore);
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                Logger.log("Unable to connect to client!", Logger.LogLevel.WARNING);
                continue;
                // e.printStackTrace();
            }
            System.out.printf("client connected!\n");
            threadController.startClientThread(clientSocket);
        }
    }

}
