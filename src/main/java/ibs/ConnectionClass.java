package ibs;

import ibs.PropManager;

import java.sql.*;

class ConnectionClass {
    private Connection dbConn;
    private static ConnectionClass INSTANCE;

    private ConnectionClass() {

    }

    public static ConnectionClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionClass();
        }
        return INSTANCE;
    }

   Connection getConnectionDB() {

        try {
            String driverName = PropManager.getInstance().getProperty("driver");
            String dbConnUrl = PropManager.getInstance().getProperty("url");
            String dbUserName = PropManager.getInstance().getProperty("login");
            String dbPassword = PropManager.getInstance().getProperty("password");
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(
                    dbConnUrl, dbUserName, dbPassword);

            if (dbConn != null) {
                System.out.println("Connection Successful");
            } else {
                System.out.println(
                        "Failed to make connection!");
            }
            return dbConn;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
    void close() {
        if (dbConn != null) {
            try {
                dbConn.close();
            } catch (SQLException e) {
                System.out.println(
                        "SQL Exception in close connection method");
            }
        }
    }
}
