package cz.thepatik.welcomeplugin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase {

    private static Connection connection;
    private static final String host = "localhost";
    private static final String port = "3306";
    private static final String database = "my_database";
    private static final String username = "username";
    private static final String passwd = "password";

    public static Connection getConnection(){

        if (connection == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, passwd);
            } catch (ClassNotFoundException | SQLException e ){
                throw new RuntimeException(e);
            }
        }
        return connection;

    }

    public static void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

}
