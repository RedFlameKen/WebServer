package com.webserver.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class HandlerThread extends ServerThread {

    private Socket clientSocket;
    public int id;

    public HandlerThread(SharedResource sharedResource, ThreadController threadController) {
        super(sharedResource, threadController);
    }

    private void readInput() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String request = reader.readLine();
        if(request == null){
            Logger.log("Empty request received", LogLevel.WARNING);
            reader.close();
            throw new IOException();
        }
        Logger.log("[REQUEST] client message: " + request, LogLevel.INFO);
    }

    @Override
    protected void task(){
        Logger.log("Handler Thread started, id: " + id, LogLevel.INFO);
        while(taskRunning){
            try {
                readInput();
            } catch (IOException e) {
                Logger.log(String.format("client %d unnexpectedly disconnected", id), LogLevel.CRITICAL);
                stop();
            }
        }
        Logger.log(String.format("Task %d is stopping", id), LogLevel.INFO);
        threadController.removeClientThread(id);
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
