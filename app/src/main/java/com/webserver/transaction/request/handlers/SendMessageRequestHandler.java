package com.webserver.transaction.request.handlers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.webserver.async.SharedResource;
import com.webserver.transaction.request.Request;
import com.webserver.transaction.response.Response;
import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class SendMessageRequestHandler extends RequestHandler {

    public SendMessageRequestHandler(Request request){
        super(request);
    }

    @Override
    public Response processRequest(SharedResource sharedResource) {
        String message = request.getBody().getString("message");
        String timeSent = request.getBody().getString("time_sent");
        storeMessage(sharedResource, message, timeSent);
        Logger.log(String.format("Message from the client: %s", message), LogLevel.INFO);
        return generateResponse(200);
    }
    
    private void storeMessage(SharedResource sharedResource, String message, String timeSent){
        SharedResource.lockSemaphore(sharedResource.semaphore);

        PreparedStatement prep = sharedResource.databaseManager
                .prepareStatement("insert into foo (message, time_sent) values (?, ?)");

        LocalDateTime time = LocalDateTime.parse(timeSent);
        try {
            prep.setString(1, message);
            prep.setTimestamp(2, Timestamp.valueOf(time));
            prep.execute();
        } catch (SQLException e) {
            Logger.log("Unable to write message to database", LogLevel.WARNING);
            e.printStackTrace();
        }

        SharedResource.unlockSemaphore(sharedResource.semaphore);
    }

}
