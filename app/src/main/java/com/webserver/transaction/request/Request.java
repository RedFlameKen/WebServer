package com.webserver.transaction.request;

import org.json.JSONObject;

public class Request {

    private RequestMethod method;
    private RequestType type;
    private String url;
    private RequestHeader header;
    private JSONObject body;

    public Request(RequestMethod method, String url, RequestHeader header) {
        this.method = method;
        this.url = url;
        this.header = header;
    }

    /**
     * In this contructor, it is assumed that json is the entire json structure of
     * the request.
     */
    public Request(JSONObject json){
        extractJson(json);
    }

    private void extractJson(JSONObject json){
        method = RequestMethod.toRequestMethod(json.getString("method"));
        url = json.getString("url");
        type = RequestType.toRequestType(json.getString("request-type"));
        header = new RequestHeader(json.getJSONObject("header"));
        body = json.getJSONObject("body");
    }

    public JSONObject getBody(){
        return body;
    }

    public RequestType getType() {
        return type;
    }

}
