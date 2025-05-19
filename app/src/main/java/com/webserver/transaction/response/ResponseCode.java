package com.webserver.transaction.response;

public class ResponseCode {

    public static final int CONTINUE = 100;

    // Success
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NO_CONTENT = 204;

    // Client Erros
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int CONFLICT = 409;
    public static final int GONE = 410;
    public static final int LENGTH_REQUIRED = 411;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    
    public static boolean responseHasError(int responseCode){
        switch (responseCode) {
            case BAD_REQUEST:
            case UNAUTHORIZED:
            case FORBIDDEN:
            case NOT_FOUND:
            case METHOD_NOT_ALLOWED:
            case REQUEST_TIMEOUT:
            case CONFLICT:
            case GONE:
            case LENGTH_REQUIRED:
            case UNSUPPORTED_MEDIA_TYPE:
                return true;
            default:
                return false;
        }
    }

    public static String getResponseCodeText(int responseCode){
        switch (responseCode) {
            case CONTINUE:
                return "continue";
            case OK:
                return "ok";
            case CREATED:
                return "created";
            case ACCEPTED:
                return "accepted";
            case NO_CONTENT:
                return "no content";
            case BAD_REQUEST:
                return "bad request";
            case UNAUTHORIZED:
                return "unauthorized";
            case FORBIDDEN:
                return "forbidden";
            case NOT_FOUND:
                return "not found";
            case METHOD_NOT_ALLOWED:
                return "method not allowed";
            case REQUEST_TIMEOUT:
                return "request timeout";
            case CONFLICT:
                return "conflict";
            case GONE:
                return "gone";
            case LENGTH_REQUIRED:
                return "length required";
            case UNSUPPORTED_MEDIA_TYPE:
                return "unsupported media type";
            default:
                return "undefined";
        }
    }

}
