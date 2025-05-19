package com.webserver.transaction.request.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.webserver.async.SharedResource;
import com.webserver.db.DatabaseManager;
import com.webserver.transaction.request.Request;
import com.webserver.transaction.response.Response;
import com.webserver.transaction.response.ResponseBuilder;
import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class FetchMessagesRequestHandler extends RequestHandler {

    public FetchMessagesRequestHandler(Request request){
        super(request);
    }

    @Override
    public Response processRequest(SharedResource sharedResource) {
        // String lastTimeFetched = request.getBody().getString("last_time_fetched");

        JSONObject body = generateResponseBody(sharedResource);

        ResponseBuilder responseBuilder = new ResponseBuilder(200, body);
        return responseBuilder.buildResponse();
    }

    private JSONObject generateResponseBody(SharedResource sharedResource){
        JSONObject body = new JSONObject();
        SharedResource.lockSemaphore(sharedResource.semaphore);

        JSONArray messages = new JSONArray(gatherMessages(sharedResource.databaseManager));

        SharedResource.unlockSemaphore(sharedResource.semaphore);

        body.put("messages", messages);
        return body;
    }

    private ArrayList<JSONObject> gatherMessages(DatabaseManager db){
        ArrayList<JSONObject> messages = new ArrayList<>();
        PreparedStatement prep = db.prepareStatement("select * from foo");
        try {
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                String message = rs.getString("message");
                LocalDateTime timeSent = rs.getTimestamp("time_sent").toLocalDateTime();
                JSONObject messageJson = new JSONObject();
                messageJson.put("message", message);
                messageJson.put("time_sent", timeSent.toString());
                messages.add(messageJson);
            }
        } catch (SQLException e) {
            Logger.log("Unable to execute the command 'select * from foo'", LogLevel.WARNING);
            e.printStackTrace();
        }
        return messages;
    }

}
