package com.webserver.transaction;

import static com.webserver.transaction.response.ResponseCode.BAD_REQUEST;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.webserver.async.SharedResource;
import com.webserver.transaction.request.Request;
import com.webserver.transaction.request.RequestBuilder;
import com.webserver.transaction.request.handlers.FetchMessagesRequestHandler;
import com.webserver.transaction.request.handlers.RequestHandler;
import com.webserver.transaction.request.handlers.SendMessageRequestHandler;
import com.webserver.transaction.response.Response;
import com.webserver.transaction.response.ResponseBuilder;
import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class Transaction {

    private Request request;
    private SharedResource sharedResource;

    public Transaction(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    /**
     * handle the request then return a response json.
     *
     * @param jsonString the string input from the client. This is expected to be in
     *                   the form of a json.
     *
     * @return response string in the form of a json
     */
    public String handleRequest(String jsonString){
        Response response = null;
        try {
            JSONObject json = new JSONObject(new JSONTokener(jsonString));
            int validateStatus = initRequest(json);
            if(validateStatus != 0)
                response = generateGenericResponse(validateStatus);
            else
                response = processRequest();
        } catch (JSONException e){
            Logger.log("BAD REQUEST", LogLevel.WARNING);
            response = generateGenericResponse(BAD_REQUEST);
        }
        return response.getJSONString();
    }


    public int initRequest(JSONObject json){
        RequestBuilder builder = new RequestBuilder();
        int validateStatus = builder.buildRequest(json);
        if(validateStatus != 0){
            return validateStatus;
        }
        request = builder.getRequest();
        return 0;
    }

    public Response processRequest(){
        RequestHandler handler = getRequestHandler();
        Response response = handler.processRequest(sharedResource);
        return response;
    }

    public Response generateGenericResponse(int status){
        ResponseBuilder builder = new ResponseBuilder(status);
        return builder.buildResponse();
    }

    public Response generateGenericResponse(int status, JSONObject body){
        ResponseBuilder builder = new ResponseBuilder(status, body);
        return builder.buildResponse();
    }

    public RequestHandler getRequestHandler(){
        switch (request.getType()) {
            case FETCH_MESSAGES_REQUEST:
                return new FetchMessagesRequestHandler(request);
            case SEND_MESSAGE_REQUEST:
                return new SendMessageRequestHandler(request);
            case UNDEFINED:
                return null;
            default:
                return null;
        }
    }

}
