package com.webserver.transaction.request;

public enum RequestType {
    SEND_MESSAGE_REQUEST,
    FETCH_MESSAGES_REQUEST,
    UNDEFINED;

    public static RequestType toRequestType(String type){
        switch (type) {
            case "SendMessage":
                return SEND_MESSAGE_REQUEST;
            case "FetchMessages":
                return FETCH_MESSAGES_REQUEST;
            default:
                return UNDEFINED;
        }
    }

}
