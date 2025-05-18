package com.webserver.async;

import java.net.Socket;
import java.util.ArrayList;

public class HandlerThreadPool {

    public ArrayList<HandlerThread> activeThreads;
    public ArrayList<HandlerThread> inactiveThreads;
    private SharedResource sharedResource;
    private int size;
    private int activeSize;
    private int inactiveSize;
    private ThreadController threadController;

    public HandlerThreadPool(int size, SharedResource sharedResource, ThreadController threadController){
        this.size = size;
        activeThreads = new ArrayList<>(size);
        inactiveThreads = new ArrayList<>(size);
        this.threadController = threadController;
        initPool();
    }

    private void initPool(){
        for(int i = 0; i < size; i++){
            inactiveThreads.add(new HandlerThread(sharedResource, threadController));
        }
        inactiveSize = size;
    }

    /**
     * Moves the last thread from the inactive region to the active region. This
     * method will also set the id of the thread to a new generated id using
     * genThreadID()
     *
     * @param clientSocket the client socket that the thread will use.
     *
     * @see {@link genThreadID}
     *
     * @return A reference to the HandlerThread added to the active region. null if
     *         the active region is full
     */
    public HandlerThread add(Socket clientSocket){
        if(activeThreads.size() == size)
            return null;
        HandlerThread thread = inactiveThreads.getLast();
        inactiveThreads.removeLast();
        thread.setClientSocket(clientSocket);
        thread.id = genThreadID();
        activeThreads.add(thread);
        return thread;
    }

    /**
     * Moves a thread from the active region to the inactive region through its id.
     * This method will also reset the state of the thread.
     *
     * @param id id of the HandlerThread to remove
     *
     * @return The success status of the method. -1 on failure
     */
    public int remove(int id){
        int threadIndex = getThreadIndexByID(id);
        if(threadIndex == -1)
            return -1;
        HandlerThread thread = activeThreads.remove(threadIndex);
        thread.reset();
        inactiveThreads.add(thread);
        return 0;
    }

    private boolean isIDAvailable(int id){
        for (HandlerThread thread : activeThreads)
            if(thread.id == id)
                return false;
        return true;
    }

    private int getThreadIndexByID(int id){
        for (int i = 0; i < activeThreads.size(); i++) {
            if(activeThreads.get(i).id == id)
                return i;
        }
        return -1;
    }

    private int genThreadID(){
        int id = -1;
        do {
            id = (int) Math.random() % 10000;
        } while(id == -1 || !isIDAvailable(id));
        return id;
    }

}
