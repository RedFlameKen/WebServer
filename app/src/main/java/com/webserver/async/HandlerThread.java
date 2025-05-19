package com.webserver.async;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.webserver.transaction.Transaction;
import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class HandlerThread extends ServerThread {

    private Socket clientSocket;
    public int id;

    public HandlerThread(SharedResource sharedResource, ThreadController threadController) {
        super(sharedResource, threadController);
    }

    private String readInput(BufferedReader reader) throws IOException{
        String receivedInput = reader.readLine();
        if(receivedInput == null){
            Logger.log("Empty request received", LogLevel.WARNING);
            throw new IOException();
        }
        // Logger.log("[REQUEST] client message: " + request, LogLevel.INFO);
        return receivedInput;
    }

    private void sendResponse(String jsonString) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

    private String handleRequest(String jsonString){
        Transaction transaction = new Transaction(sharedResource);
        String response = transaction.handleRequest(jsonString);
        return response;
    }

    @Override
    protected void task(){
        Logger.log("Handler Thread started, id: " + id, LogLevel.INFO);
        while(taskRunning){
            String input = null;
            String response = null;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                input = readInput(reader);
                response = handleRequest(input);
                sendResponse(response);
            } catch (IOException e) {
                Logger.log(String.format("client %d unnexpectedly disconnected", id), LogLevel.CRITICAL);
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stop();
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
