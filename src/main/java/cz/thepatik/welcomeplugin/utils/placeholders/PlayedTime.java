package cz.thepatik.welcomeplugin.utils.placeholders;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static cz.thepatik.welcomeplugin.utils.TimeConverter.convertSecondsToTime;

public class PlayedTime {

    WelcomePlugin plugin;
    public PlayedTime(WelcomePlugin plugin){
        this.plugin = plugin;
    }

    public static String getPlayedTime(Player p, WelcomePlugin plugin){
        int playTime = 0;
        String playedTime = null;

        SQLiteDatabase database = WelcomePlugin.database;
        try {
            PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayTime FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            playTime = resultSet.getInt("PlayTime");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        playedTime = convertSecondsToTime(playTime, plugin.getMessagesHandler());
        return playedTime;
    }
}
