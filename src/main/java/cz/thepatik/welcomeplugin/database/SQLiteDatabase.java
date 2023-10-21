package cz.thepatik.welcomeplugin.database;

import org.bukkit.entity.Player;

import java.sql.*;

public class SQLiteDatabase {

    public final Connection connection;

    public SQLiteDatabase(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        try (Statement statement = connection.createStatement();) {
            statement.execute("CREATE TABLE IF NOT EXISTS PlayerData " +
                    "(PlayerID INTEGER PRIMARY KEY AUTOINCREMENT, PlayerUUID varchar(255)," +
                    " PlayerName varchar(225), PlayTime int NOT NULL DEFAULT 0," +
                    " PlayerJoins int NOT NULL DEFAULT 0, SentMessages int NOT NULL DEFAULT 0);");
        }
    }
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public void checkMissingColumns(){

        try {
            String[] columnsIntToCheck = {"SentMessages", "HasOwnJoinMessage", "HasOwnLeaveMessage"};
            String[] columnsStringToCheck = {"PlayerJoinMessage", "PlayerLeaveMessage"};

            for (String columnIntName : columnsIntToCheck) {
                ResultSet resultSet = connection.getMetaData().getColumns(null, null, "PlayerData", columnIntName);

                if (!resultSet.next()){
                    try(Statement statement = connection.createStatement()) {
                        statement.execute("ALTER TABLE PlayerData ADD COLUMN " + columnIntName + " int NOT NULL DEFAULT 0");
                    }
                }

            }
            for (String columnStringName : columnsStringToCheck){
                ResultSet resultSet = connection.getMetaData().getColumns(null, null, "PlayerData", columnStringName);

                if (!resultSet.next()){
                    try(Statement statement = connection.createStatement()) {
                        statement.execute("ALTER TABLE PlayerData ADD COLUMN " + columnStringName + " varchar(255)");
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
