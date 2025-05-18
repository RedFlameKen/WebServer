package com.webserver.async;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.webserver.util.Logger;
import static com.webserver.util.Logger.LogLevel.*;

public class ListenerThread extends ServerThread {

    public ListenerThread(SharedResource sharedResource, ThreadController threadController) {
        super(sharedResource, threadController);
    }

    @Override
    protected void task(){
        Logger.log("started listener thread", INFO);
        while(taskRunning){
            Logger.log("listening for a client", INFO);

            ServerSocket serverSocket = null;

            SharedResource.lockSemaphore(sharedResource.semaphore);

            serverSocket = sharedResource.serverSocket;

            SharedResource.unlockSemaphore(sharedResource.semaphore);
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                Logger.log("Unable to connect to client!", WARNING);
                continue;
                // e.printStackTrace();
            }
            Logger.log("client connected!", INFO);
            threadController.startClientThread(clientSocket);
        }
    }

}
