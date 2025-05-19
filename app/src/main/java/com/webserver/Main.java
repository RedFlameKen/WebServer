package com.webserver;

import com.webserver.db.DatabaseManager;
import com.webserver.server.ServerController;

public class Main {

    public static void main(String[] args) {
        String username = "root";
        String password = "root";
        if(DatabaseManager.loadDriver() == -1){
            return;
        }
        if(DatabaseManager.initDatabase(username, password) == -1){
            return;
        }
        DatabaseManager databaseManager = new DatabaseManager(username, password);
        if (databaseManager.connect() == -1){
            return;
        }
        databaseManager.useDatabase();
        ServerController controller = new ServerController(databaseManager);
        controller.start();
    }
}
