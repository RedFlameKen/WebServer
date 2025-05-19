package com.webserver.transaction.response;

import org.json.JSONObject;

public class ResponseBuilder {

    private int status;
    private ResponseHeader header;
    private JSONObject body;

    public ResponseBuilder(){
    }

    public ResponseBuilder(int status){
        this.status = status;
        generateHeader();
    }

    public ResponseBuilder(int status, JSONObject body){
        this.status = status;
        generateHeader();
        this.body = body;
    }

    public Response buildResponse(){
        return new Response(status, header, body);
    }

    private void generateHeader(){
        JSONObject headerJson = new JSONObject();
        headerJson.put("Content-Type", "application/json");
        header = new ResponseHeader(headerJson);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

}
