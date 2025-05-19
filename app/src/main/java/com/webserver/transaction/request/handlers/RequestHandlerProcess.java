package com.webserver.transaction.request.handlers;

import com.webserver.async.SharedResource;
import com.webserver.transaction.response.Response;

public interface RequestHandlerProcess {

    public Response processRequest(SharedResource sharedResource);

}
