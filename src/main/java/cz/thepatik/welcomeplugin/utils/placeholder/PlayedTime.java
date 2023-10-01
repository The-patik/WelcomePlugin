package cz.thepatik.welcomeplugin.utils.placeholder;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.database.Database;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static cz.thepatik.welcomeplugin.utils.TimeConverter.convertSecondsToTime;

public class PlayedTime {

    public static String getPlayedTime(Player p){
        int playTime = 0;
        String playedTime = null;

        Database database = WelcomePlugin.database;
        try {
            PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayTime FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            playTime = resultSet.getInt("PlayTime");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        playedTime = convertSecondsToTime(playTime);
        return playedTime;
    }
}
