package cz.thepatik.welcomeplugin.utils.placeholders;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.database.DatabaseHandler;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoins {

    WelcomePlugin plugin;

    public PlayerJoins(WelcomePlugin plugin){
        this.plugin = plugin;
    }

    public int getPlayerJoins(Player p){
        int playerJoins = 0;

        if (plugin.databaseType().equals("sqlite")) {

            SQLiteDatabase database = plugin.sqLiteDatabase;
            try {
                PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayerJoins FROM PlayerData WHERE PlayerUUID = ?");
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                playerJoins = resultSet.getInt("PlayerJoins");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return playerJoins;
        } else if (plugin.databaseType().equals("mysql")) {

            DatabaseHandler database = plugin.mySQLDatabase;
            try {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT PlayerJoins FROM PlayerData WHERE PlayerUUID = ?");
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                playerJoins = resultSet.getInt("PlayerJoins");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return playerJoins;

        }
        return playerJoins;
    }
    public int getElsePlayerJoins(String p){
        int playerJoins = 0;

        if (plugin.databaseType().equals("sqlite")) {
            SQLiteDatabase database = plugin.sqLiteDatabase;

            try {
                PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayerJoins FROM PlayerData WHERE PlayerName = ?");
                preparedStatement.setString(1, p);
                ResultSet resultSet = preparedStatement.executeQuery();
                playerJoins = resultSet.getInt("PlayerJoins");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return playerJoins;
        } else if (plugin.databaseType().equals("mysql")){
            DatabaseHandler database = plugin.mySQLDatabase;

            try {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT PlayerJoins FROM PlayerData WHERE PlayerName = ?");
                preparedStatement.setString(1, p);
                ResultSet resultSet = preparedStatement.executeQuery();
                playerJoins = resultSet.getInt("PlayerJoins");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return playerJoins;
        }
        return playerJoins;

    }

}
