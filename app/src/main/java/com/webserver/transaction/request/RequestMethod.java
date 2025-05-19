package com.webserver.transaction.request;

public enum RequestMethod {
    GET,
    POST,
    PUT,
    DELETE,
    UNDEFINED;

    public static RequestMethod toRequestMethod(String method){
        switch (method) {
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            default:
                return UNDEFINED;
        }
    }

}

