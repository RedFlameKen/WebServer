package com.webserver.transaction.request.handlers;

import com.webserver.async.SharedResource;
import com.webserver.transaction.request.Request;
import com.webserver.transaction.response.Response;

public class FetchMessagesRequestHandler extends RequestHandler {

    public FetchMessagesRequestHandler(Request request){
        super(request);
    }

    @Override
    public Response processRequest(SharedResource sharedResource) {
        throw new UnsupportedOperationException("Unimplemented method 'processRequest'");
    }

}
