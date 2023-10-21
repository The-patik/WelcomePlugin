package cz.thepatik.welcomeplugin.database.functions.sqlite;

import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerFunctions {
    Functions functions = new Functions();
    SQLiteDatabase sqlite = functions.sqLiteDatabase();

    public void addPlayer(Player p) throws SQLException{
        try(PreparedStatement preparedStatement = sqlite.connection.prepareStatement("INSERT INTO PlayerData" +
                " (PlayerUUID, PlayerName) VALUES (?, ?)")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            preparedStatement.setString(2, p.getDisplayName());
            preparedStatement.executeUpdate();
        }
    }
    public boolean playerExists(Player p) throws SQLException{
        try(PreparedStatement preparedStatement = sqlite.connection.prepareStatement("SELECT * FROM PlayerData WHERE PlayerUUID = ?")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }
    public void addPlayerJoin(Player p){
        try {
            PreparedStatement preparedStatement = sqlite.connection.prepareStatement("SELECT PlayerJoins" +
                    " FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int playerJoins = resultSet.getInt("PlayerJoins");
            playerJoins++;
            try(PreparedStatement preparedStatement1 = sqlite.connection.prepareStatement("UPDATE PlayerData" +
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
            PreparedStatement preparedStatement = sqlite.connection.prepareStatement("SELECT SentMessages" +
                    " FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int playerMessagesSent = resultSet.getInt("SentMessages");
            playerMessagesSent++;
            try(PreparedStatement preparedStatement1 = sqlite.connection.prepareStatement("UPDATE PlayerData" +
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
    public boolean hasOwnJoinMessage(Player p){
        boolean hasJoinMessage = false;
        try {
            PreparedStatement preparedStatement = sqlite.connection.prepareStatement("SELECT HasOwnJoinMessage FROM " +
                    "PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int numberJoinMessage = resultSet.getInt("HasOwnJoinMessage");
            if (numberJoinMessage == 0){
                hasJoinMessage = false;
            } else if (numberJoinMessage == 1){
                hasJoinMessage = true;
            }
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return hasJoinMessage;
    }
    public boolean hasOwnLeaveMessage(Player p){
        boolean hasLeaveMessage = false;
        try {
            PreparedStatement preparedStatement = sqlite.connection.prepareStatement("SELECT HasOwnLeaveMessage FROM " +
                    "PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int numberJoinMessage = resultSet.getInt("HasOwnLeaveMessage");
            if (numberJoinMessage == 0){
                hasLeaveMessage = false;
            } else if (numberJoinMessage == 1){
                hasLeaveMessage = true;
            }
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return hasLeaveMessage;
    }
    public String getPlayerJoinMessage(Player p){
        String playerMessage = "";
        try {
            PreparedStatement preparedStatement = sqlite.connection.prepareStatement("SELECT PlayerJoinMessage FROM " +
                    "PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            playerMessage = resultSet.getString("PlayerJoinMessage");
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return playerMessage;
    }
    public String getPlayerLeaveMessage(Player p){
        String playerMessage = "";
        try {
            PreparedStatement preparedStatement = sqlite.connection.prepareStatement("SELECT PlayerLeaveMessage FROM " +
                    "PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            playerMessage = resultSet.getString("PlayerLeaveMessage");
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return playerMessage;
    }
}
