package cz.thepatik.welcomeplugin.database.functions.mysql;

import cz.thepatik.welcomeplugin.database.MySQLDatabase;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerFunctions {
    Functions functions = new Functions();
    MySQLDatabase mysql = functions.mySQLDatabase();

    // Add player into database
    public void addPlayer(Player p) throws SQLException{
        try(PreparedStatement preparedStatement = mysql.getConnection().prepareStatement("INSERT INTO PlayerData" +
                " (PlayerUUID, PlayerName) VALUES (?, ?)")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            preparedStatement.setString(2, p.getDisplayName());
            preparedStatement.executeUpdate();
        }
    }

    // Check if player exists in database
    public boolean playerExists(Player p) throws SQLException{
        try(PreparedStatement preparedStatement = mysql.getConnection().prepareStatement("SELECT * FROM PlayerData WHERE PlayerUUID = ?")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    // Add +1 to PlayerJoins
    public void addPlayerJoin(Player p){
        try {
            PreparedStatement preparedStatement = mysql.getConnection().prepareStatement("SELECT PlayerJoins" +
                    " FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int playerJoins = resultSet.getInt("PlayerJoins");
            playerJoins++;
            try(PreparedStatement preparedStatement1 = mysql.getConnection().prepareStatement("UPDATE PlayerData" +
                    " SET PlayerJoins = ? WHERE PlayerUUID = ?")) {
                preparedStatement1.setInt(1, playerJoins);
                preparedStatement1.setString(2, p.getUniqueId().toString());
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Add +1 to MessagesSent
    public void addMessagesSent(Player p){
        try {
            PreparedStatement preparedStatement = mysql.getConnection().prepareStatement("SELECT SentMessages" +
                    " FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int playerMessagesSent = resultSet.getInt("SentMessages");
            playerMessagesSent++;
            try(PreparedStatement preparedStatement1 = mysql.getConnection().prepareStatement("UPDATE PlayerData" +
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

    // Check if player has own join message
    public boolean hasOwnJoinMessage(Player p){
        boolean hasJoinMessage = false;
        try {
            PreparedStatement preparedStatement = mysql.getConnection().prepareStatement("SELECT HasOwnJoinMessage FROM " +
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

    // Check if player has own leave message
    public boolean hasOwnLeaveMessage(Player p){
        boolean hasLeaveMessage = false;
        try {
            PreparedStatement preparedStatement = mysql.getConnection().prepareStatement("SELECT HasOwnLeaveMessage FROM " +
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

    // Get players join message
    public String getPlayerJoinMessage(Player p){
        String playerMessage = "";
        try {
            PreparedStatement preparedStatement = mysql.getConnection().prepareStatement("SELECT PlayerJoinMessage FROM " +
                    "PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            playerMessage = resultSet.getString("PlayerJoinMessage");
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return playerMessage;
    }

    // Get players leave message
    public String getPlayerLeaveMessage(Player p){
        String playerMessage = "";
        try {
            PreparedStatement preparedStatement = mysql.connection.prepareStatement("SELECT PlayerLeaveMessage FROM " +
                    "PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            playerMessage = resultSet.getString("PlayerLeaveMessage");
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return playerMessage;
    }

    // Set players join message
    public void setPlayerJoinMessage(Player p, String message){
        try {
            PreparedStatement preparedStatement = mysql.connection.prepareStatement("UPDATE PlayerData "
                    + "SET PlayerJoinMessage = ? WHERE PlayerUUID = ?");
            preparedStatement.setString(1, message);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = mysql.connection.prepareStatement("UPDATE PlayerData "
                    + "SET HasOwnJoinMessage = ? WHERE PlayerUUID = ?");
            preparedStatement1.setInt(1, 1);
            preparedStatement1.setString(2, p.getUniqueId().toString());
            preparedStatement1.executeUpdate();
        } catch (SQLException e){
            functions.welcomePlugin().getLogger().severe("There was a problem with setting " + p.getDisplayName() + "'s join message!");
            e.printStackTrace();
        }
    }

    // Set players leave message
    public void setPlayerLeaveMessage(Player p, String message){
        try {
            PreparedStatement preparedStatement = mysql.connection.prepareStatement("UPDATE PlayerData "
                    + "SET PlayerLeaveMessage = ? WHERE PlayerUUID = ?");
            preparedStatement.setString(1, message);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = mysql.connection.prepareStatement("UPDATE PlayerData "
                    + "SET HasOwnLeaveMessage = ? WHERE PlayerUUID = ?");
            preparedStatement1.setInt(1, 1);
            preparedStatement1.setString(2, p.getUniqueId().toString());
            preparedStatement1.executeUpdate();
        } catch (SQLException e){
            functions.welcomePlugin().getLogger().severe("There was a problem with setting " + p.getDisplayName() + "'s leave message!");
            e.printStackTrace();
        }
    }

    public void removePlayerJoinMessage(Player p){
        try {
            PreparedStatement preparedStatement = mysql.connection.prepareStatement("UPDATE PlayerData "
                    + "SET HasOwnJoinMessage = ? WHERE PlayerUUID = ?");
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            functions.welcomePlugin().getLogger().severe("There was a problem with setting " + p.getDisplayName() + "'s join message!");
            e.printStackTrace();
        }
    }

    // Remove player leave message
    public void removePlayerLeaveMessage(Player p){
        try {
            PreparedStatement preparedStatement = mysql.connection.prepareStatement("UPDATE PlayerData "
                    + "SET HasOwnLeaveMessage = ? WHERE PlayerUUID = ?");
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            functions.welcomePlugin().getLogger().severe("There was a problem with setting " + p.getDisplayName() + "'s leave message!");
            e.printStackTrace();
        }
    }
}
