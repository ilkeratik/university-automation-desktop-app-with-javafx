package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connector {
    private String jbdcURL = "jdbc:mysql://localhost:3306/";
    private String username = "root";
    private String password = "12345";
    private String schema = "university";
    public Connection connection;
    private static DB_Connector instance;
    private DB_Connector(){
        try {
            connection = DriverManager.getConnection(jbdcURL+schema, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }
    public static DB_Connector getInstance() throws SQLException {
        if (instance == null) {
            instance = new DB_Connector();
        } else if (instance.getConnection().isClosed()) {
            instance = new DB_Connector();
        }
        return instance;
    }
}
