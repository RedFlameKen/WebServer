package com.webserver.transaction.request;

import org.json.JSONObject;

public class RequestHeader {

    private String contentType;

    public RequestHeader(){
        initFields();
    }

    public RequestHeader(JSONObject header){
        contentType = header.getString("Content-Type");
    }

    private void initFields(){
        contentType = null;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public JSONObject getJSONObject(){
        JSONObject header = new JSONObject();
        if(contentType != null)
            header.put("Content-Type", contentType);
        return header;
    }

}
