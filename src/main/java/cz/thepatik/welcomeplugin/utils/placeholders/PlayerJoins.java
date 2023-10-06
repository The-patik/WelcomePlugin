package cz.thepatik.welcomeplugin.utils.placeholders;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoins {

    public static int getPlayerJoins(Player p){
        int playerJoins = 0;

        SQLiteDatabase database = WelcomePlugin.database;
        try {
            PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayerJoins FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            playerJoins = resultSet.getInt("PlayerJoins");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return playerJoins;
    }
    public static int getElsePlayerJoins(String p){
        int playerJoins = 0;

        SQLiteDatabase database = WelcomePlugin.database;
        try {
            PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayerJoins FROM PlayerData WHERE PlayerName = ?");
            preparedStatement.setString(1, p);
            ResultSet resultSet = preparedStatement.executeQuery();
            playerJoins = resultSet.getInt("PlayerJoins");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return playerJoins;
    }

}
