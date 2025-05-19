package com.webserver.transaction.response;

import org.json.JSONObject;

public class Response {

    private int status;
    private ResponseHeader header;
    private JSONObject body;

    public Response(int status, ResponseHeader header, JSONObject body) {
        this.status = status;
        this.header = header;
        this.body = body;
    }

    public String getJSONString(){
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("statusText", ResponseCode.getResponseCodeText(status));
        json.put("header", header.getJSONObject().toString());
        json.put("body", body);
        return json.toString();
    }

}
