package com.webserver.async;

import java.io.IOException;
import java.net.Socket;

import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class HandlerThread extends ServerThread {

    private Socket clientSocket;
    public int id;

    public HandlerThread(SharedResource sharedResource, ThreadController threadController) {
        super(sharedResource, threadController);
    }

    @Override
    protected void task(){
        Logger.log("Handler Thread started", LogLevel.INFO);
        while(taskRunning){
            Logger.log(String.format("Task %d is running\n", id), LogLevel.INFO);
            stop();
        }
        Logger.log(String.format("Task %d is stopping\n", id), LogLevel.INFO);
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
