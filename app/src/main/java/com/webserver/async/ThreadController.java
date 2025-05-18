package com.webserver.async;

import java.io.IOException;
import java.net.Socket;

import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class ThreadController {

    public static final int HANDLER_THREAD_POOL_SIZE = 3;

    private SharedResource sharedResource;
    private ListenerThread listenerThread;
    private HandlerThreadPool handlerThreadPool;

    public ThreadController(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
        initThreads();
    }

    /**
     * Starts a HandlerThread process by calling start()
     *
     * @param clientSocket the client socket that the thread will use.
     *
     * @see {@link ServerThread.start}
     */
    public void startClientThread(Socket clientSocket){
        HandlerThread thread = handlerThreadPool.add(clientSocket);
        if(thread == null){
            Logger.log("The HandlerThreadPool is full! cancelling connection!", LogLevel.CRITICAL);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        thread.start();
    }
    
    public void removeClientThread(int id){
        if(handlerThreadPool.remove(id) == -1){
            Logger.log("Could not remove HandlerThread from the pool, invalid ID!", LogLevel.CRITICAL);
            return;
        }
    }

    private void initThreads(){
        listenerThread = new ListenerThread(sharedResource, this);
        handlerThreadPool = new HandlerThreadPool(HANDLER_THREAD_POOL_SIZE, sharedResource, this);
    }

    public void startController(){
        listenerThread.start();
    }

}
