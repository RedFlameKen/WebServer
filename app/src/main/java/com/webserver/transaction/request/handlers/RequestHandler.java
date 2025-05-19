package com.webserver.transaction.request.handlers;

import org.json.JSONObject;

import com.webserver.transaction.request.Request;
import com.webserver.transaction.response.Response;
import com.webserver.transaction.response.ResponseBuilder;

public abstract class RequestHandler implements RequestHandlerProcess {

    protected Request request;

    public RequestHandler(Request request){
        this.request = request;
    }

    public Response generateResponse(int status){
        ResponseBuilder responseBuilder = new ResponseBuilder(status);
        responseBuilder.setBody(new JSONObject());
        return responseBuilder.buildResponse();
    }
    
}
