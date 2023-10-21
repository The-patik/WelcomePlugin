package cz.thepatik.welcomeplugin.utils.placeholders;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.database.MySQLDatabase;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static cz.thepatik.welcomeplugin.utils.TimeConverter.convertSecondsToTime;

public class PlayedTime {

    Functions functions = new Functions();

    public String getPlayedTime(Player p, WelcomePlugin plugin){

        if (plugin.databaseType().equals("sqlite")) {

            int playTime = 0;
            String playedTime = null;

            SQLiteDatabase database = plugin.sqLiteDatabase;
            try {
                PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayTime FROM PlayerData WHERE PlayerUUID = ?");
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                playTime = resultSet.getInt("PlayTime");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            playedTime = convertSecondsToTime(playTime, functions.getMessagesHandler());
            return playedTime;
        } else if (plugin.databaseType().equals("mysql")){
            int playTime = 0;
            String playedTime = null;

            MySQLDatabase database = plugin.mySQLDatabase;
            try {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT PlayTime FROM PlayerData WHERE PlayerUUID = ?");
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                playTime = resultSet.getInt("PlayTime");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            playedTime = convertSecondsToTime(playTime, functions.getMessagesHandler());
            return playedTime;
        }
        return "This is not allowed!";
    }
}
