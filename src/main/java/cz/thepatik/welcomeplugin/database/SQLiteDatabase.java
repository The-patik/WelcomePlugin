package cz.thepatik.welcomeplugin.database;

import cz.thepatik.welcomeplugin.WelcomePlugin;
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

    public void addPlayer(Player p){
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PlayerData" +
                " (PlayerUUID, PlayerName) VALUES (?, ?)")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            preparedStatement.setString(2, p.getDisplayName());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("There was an error while adding player to database");
            throw new RuntimeException(e);
        }
    }
    public boolean playerExists(Player p) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PlayerData WHERE PlayerUUID = ?")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public void addPlayerJoin(Player p){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT PlayerJoins" +
                    " FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int playerJoins = resultSet.getInt("PlayerJoins");
            playerJoins++;
            try(PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE PlayerData" +
                    " SET PlayerJoins = ? WHERE PlayerUUID = ?")) {
                preparedStatement1.setInt(1, playerJoins);
                preparedStatement1.setString(2, p.getUniqueId().toString());
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessagesSent(Player p){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT SentMessages" +
                    " FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int playerMessagesSent = resultSet.getInt("SentMessages");
            playerMessagesSent++;
            try(PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE PlayerData" +
                    " SET SentMessages = ? WHERE PlayerUUID = ?")) {
                preparedStatement1.setInt(1, playerMessagesSent);
                preparedStatement1.setString(2, p.getUniqueId().toString());
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e){
            System.out.println("There was a problem with adding message to database!");
            throw new RuntimeException(e);
        }
    }

    public void checkMissingColumns(){
        try {

            String[] columnsIntToCheck = {"SentMessages"};

            for (String columnIntName : columnsIntToCheck) {
                ResultSet resultSet = connection.getMetaData().getColumns(null, null, "PlayerData", columnIntName);

                if (!resultSet.next()){
                    try(Statement statement = connection.createStatement()) {
                        statement.execute("ALTER TABLE PlayerData ADD COLUMN " + columnIntName + " int NOT NULL DEFAULT 0");
                    }
                }

            }
        } catch (SQLException e){
            WelcomePlugin.getPlugin().getLogger().severe("Error while checking/adding");
        }
    }
}