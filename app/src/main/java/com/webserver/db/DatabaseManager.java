package com.webserver.db;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.webserver.util.Logger;
import com.webserver.util.Logger.LogLevel;

public class DatabaseManager {

    public static final String DB_NAME = "webserver_db";
    public static final String DB_URL_FORMAT = "jdbc:mysql://%s:%d/";
    public static final String DB_ADDRESS = "localhost";
    public static final int DB_PORT = 3306;

    private final String username;
    private final String password;
    private Connection connection;

    public DatabaseManager(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Loads the MySQL connector driver. Should be called only once, preferably
     * during startup.
     *
     * @return exit status. -1 on error
     */
    public static int loadDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Logger.log("Unable to load MySQL connector/j Driver", LogLevel.CRITICAL);
            return -1;
        }
        return 0;
    }

    /**
     * Initialize the database and all the necessary tables. This method is meant to
     * only be ran once.
     *
     * @return exit status. -1 on error
     */
    public static int initDatabase(String username, String password){
        DatabaseManager manager = new DatabaseManager(username, password);
        manager.connect();
        try {
            manager.execute("create database if not exists " + DB_NAME);
            manager.execute("use " + DB_NAME);
            createTestTable(manager);
        } catch (SQLException e) {
            Logger.log("Unable to initialize Database", LogLevel.CRITICAL);
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static void createTestTable(DatabaseManager manager) throws SQLException{
        PreparedStatement prep = manager.prepareStatement("create table if not exists foo(message varchar(1024), time_sent datetime)");
        prep.execute();
    }

    /**
     * Connects the manager to the database
     *
     * @return exit status. -1 on error
     */
    public int connect(){
        String url = String.format(DB_URL_FORMAT, DB_ADDRESS, DB_PORT);
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            Logger.log("Unable to connect to database", LogLevel.WARNING);
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public void useDatabase(){
        execute("use " + DB_NAME);
    }

    /**
     * Creates a PreparedStatement. Returns a null PreparedStatement if unable to
     * make one.
     *
     * @param command the SQL command to be executed by the statement
     *
     * @return the PreparedStatement 
     */
    public PreparedStatement prepareStatement(String command) {
        PreparedStatement prep = null;
        try {
            prep = connection.prepareStatement(command);
        } catch (SQLException e) {
            Logger.log("Could not prepare statement for the command: '" + command + "'", LogLevel.WARNING);
            e.printStackTrace();
        }
        return prep;
    }

    public void execute(String command){
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(command);
        } catch (SQLException e) {
            Logger.log("Could not execute the command: '" + command + "'", LogLevel.WARNING);
            e.printStackTrace();
        }
    }

}
