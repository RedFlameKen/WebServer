package com.webserver.transaction.request;

import static com.webserver.transaction.response.ResponseCode.FORBIDDEN;
import static com.webserver.transaction.response.ResponseCode.METHOD_NOT_ALLOWED;

import org.json.JSONObject;

public class RequestBuilder {

    private Request request;

    public RequestBuilder(JSONObject json){
        buildRequest(json);

    }

    public int buildRequest(JSONObject json){
        String requestMethod = json.getString("method");
        String requestType = json.getString("request-type");
        int validationStatus = validateRequest(requestMethod, requestType);
        request = new Request(json);
        return validationStatus;
    }

    private int validateRequest(String requestMethod, String requestType){
        if(RequestMethod.toRequestMethod(requestMethod) == RequestMethod.UNDEFINED){
            return METHOD_NOT_ALLOWED;
        }
        if(RequestType.toRequestType(requestType) == RequestType.UNDEFINED){
            return FORBIDDEN;
        }
        return 0;
    }

    private RequestHeader generateHeader(){
        JSONObject headerJson = new JSONObject();
        headerJson.put("Content-Type", "application/json");
        return new RequestHeader(headerJson);
    }

    public Request getRequest() {
        return request;
    }

}
