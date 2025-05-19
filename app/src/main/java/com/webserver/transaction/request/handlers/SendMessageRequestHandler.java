package com.webserver.transaction.request.handlers;

import org.json.JSONObject;

import com.webserver.async.SharedResource;
import com.webserver.transaction.request.Request;
import com.webserver.transaction.response.Response;
import com.webserver.transaction.response.ResponseBuilder;
import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class SendMessageRequestHandler implements RequestHandler {

    private Request request;

    public SendMessageRequestHandler(Request request){
        this.request = request;
    }

    @Override
    public Response processRequest(SharedResource sharedResource) {
        String message = request.getBody().getString("message");
        Logger.log(String.format("Message from the client: %s", message), LogLevel.INFO);
        return generateResponse(200);
    }
    
    public Response generateResponse(int status){
        ResponseBuilder responseBuilder = new ResponseBuilder(status);
        responseBuilder.setBody(new JSONObject());
        return responseBuilder.buildResponse();
    }

}
