package com.webserver.async;

import java.net.Socket;

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
        thread.start();
    }

    private void initThreads(){
        listenerThread = new ListenerThread(sharedResource, this);
        handlerThreadPool = new HandlerThreadPool(HANDLER_THREAD_POOL_SIZE, sharedResource, this);
    }

    public void startController(){
        listenerThread.start();
    }

}
