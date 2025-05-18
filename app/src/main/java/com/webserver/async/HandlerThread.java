package com.webserver.async;

import java.io.IOException;
import java.net.Socket;

public class HandlerThread extends ServerThread {

    private Socket clientSocket;
    public int id;

    public HandlerThread(SharedResource sharedResource, ThreadController threadController) {
        super(sharedResource, threadController);
    }

    @Override
    protected void task(){
        while(taskRunning){
            System.out.printf("Task %d is running\n", id);
            stop();
        }
    }

    public void reset(){
        clientSocket = null;
        id = -1;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setID(int id){
        this.id = id;
    }

    @Override
    public void stop(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.stop();
    }

}
