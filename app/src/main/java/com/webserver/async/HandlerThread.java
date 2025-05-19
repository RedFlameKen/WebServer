package com.webserver.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.webserver.transaction.Transaction;
import com.webserver.transaction.response.Response;
import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class HandlerThread extends ServerThread {

    private Socket clientSocket;
    public int id;

    public HandlerThread(SharedResource sharedResource, ThreadController threadController) {
        super(sharedResource, threadController);
    }

    private String readInput() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String receivedInput = reader.readLine();
        if(receivedInput == null){
            Logger.log("Empty request received", LogLevel.WARNING);
            reader.close();
            throw new IOException();
        }
        // Logger.log("[REQUEST] client message: " + request, LogLevel.INFO);
        return receivedInput;
    }

    /**
     * handle the request then return a response json.
     *
     * @param jsonString the string input from the client. This is expected to be in
     *                   the form of a json.
     *
     * @return response string in the form of a json
     */
    private String handleRequest(String jsonString){
        JSONObject json = new JSONObject(new JSONTokener(jsonString));
        Transaction transaction = new Transaction(sharedResource, json);
        Response response = transaction.processRequest();
        return response.getJSONString();
    }

    @Override
    protected void task(){
        Logger.log("Handler Thread started, id: " + id, LogLevel.INFO);
        while(taskRunning){
            String input = null;
            try {
                input = readInput();
            } catch (IOException e) {
                Logger.log(String.format("client %d unnexpectedly disconnected", id), LogLevel.CRITICAL);
                stop();
            }
            handleRequest(input);
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
