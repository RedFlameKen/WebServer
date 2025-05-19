package com.webserver.transaction;

import org.json.JSONObject;

import com.webserver.async.SharedResource;
import com.webserver.transaction.request.Request;
import com.webserver.transaction.request.RequestBuilder;
import com.webserver.transaction.request.handlers.RequestHandler;
import com.webserver.transaction.request.handlers.SendMessageRequestHandler;
import com.webserver.transaction.response.Response;

public class Transaction {

    private Request request;
    private SharedResource sharedResource;

    public Transaction(SharedResource sharedResource, JSONObject json){
        initRequest(json);
    }

    // public Transaction(SharedResource sharedResource, String json){
    //     initRequest(json);
    // }
    //
    public void initRequest(JSONObject json){
        RequestBuilder builder = new RequestBuilder(json);
        request = builder.getRequest();
    }

    public Response processRequest(){
        RequestHandler handler = getRequestHandler();
        Response response = handler.processRequest(sharedResource);
        return response;
    }

    public Response getResponse(){
        return null;
    }

    public RequestHandler getRequestHandler(){
        switch (request.getType()) {
            case FETCH_MESSAGES_REQUEST:
                return null;
            case SEND_MESSAGE_REQUEST:
                return new SendMessageRequestHandler(request);
            case UNDEFINED:
                return null;
            default:
                return null;
        }
    }

}
