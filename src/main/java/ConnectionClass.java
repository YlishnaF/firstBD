import java.io.IOException;
import java.sql.*;

public class ConnectionClass {

    public Connection getFileFromResources() {
        try {
            String driverName = PropManager.getInstance().getProperty("driver");
            String dbConnUrl = PropManager.getInstance().getProperty("url");
            String dbUserName = PropManager.getInstance().getProperty("login");
            String dbPassword = PropManager.getInstance().getProperty("password");
            Class.forName(driverName);
            Connection dbConn = DriverManager.getConnection(
                    dbConnUrl, dbUserName, dbPassword);

            if (dbConn != null) {
                System.out.println("Connection Successful");
            }
            else {
                System.out.println(
                        "Failed to make connection!");
            }
            return dbConn;
        }
        catch(SQLException e){

                e.printStackTrace();
            }
        catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn)
    {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                System.out.println(
                        "SQL Exception in close connection method");
            }
        }
    }
}
