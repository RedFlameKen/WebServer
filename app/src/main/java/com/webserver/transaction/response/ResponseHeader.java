package com.webserver.transaction.response;

import org.json.JSONObject;

public class ResponseHeader {

    private String contentType;

    public ResponseHeader(){
        initFields();
    }

    public ResponseHeader(JSONObject header){
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
