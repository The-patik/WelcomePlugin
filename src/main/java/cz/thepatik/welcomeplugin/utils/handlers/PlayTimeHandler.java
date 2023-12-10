package cz.thepatik.welcomeplugin.utils.handlers;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static cz.thepatik.welcomeplugin.utils.TimeConverter.convertSecondsToTime;

public class PlayTimeHandler {
    private final Player p;

    Functions functions = new Functions();
    WelcomePlugin plugin = functions.welcomePlugin();

    private final String playedTime;

    public PlayTimeHandler(Player p){
        this.p = p;

        int time = 0;
        if (plugin.databaseType().equals("sqlite")) {
            try (PreparedStatement preparedStatement = plugin.sqLiteDatabase.connection.prepareStatement("SELECT PlayTime " +
                    "FROM PlayerData WHERE PlayerUUID = ?")) {
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                time = resultSet.getInt("PlayTime");
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        } else if (plugin.databaseType().equals("mysql")) {
            try(PreparedStatement preparedStatement = plugin.mySQLDatabase.getConnection().prepareStatement("SELECT PlayTime FROM PlayerData WHERE PlayerUUID = ?")) {
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                time = resultSet.getInt("PlayTime");
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        this.playedTime = convertSecondsToTime(time, functions.getMessagesHandler());
    }

    public String getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(int t){
        if (plugin.databaseType().equals("sqlite")) {
            try {
                PreparedStatement preparedStatement = plugin.sqLiteDatabase.connection.prepareStatement("UPDATE PlayerData " +
                        "SET PlayTime = ? WHERE PlayerUUID = ?");
                preparedStatement.setInt(1, t);
                preparedStatement.setString(2, p.getUniqueId().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (plugin.databaseType().equals("mysql")) {
            try {
                PreparedStatement preparedStatement = plugin.mySQLDatabase.connection.prepareStatement("UPDATE PlayerData " +
                        "SET PlayTime = ? WHERE PlayerUUID = ?");
                preparedStatement.setInt(1, t);
                preparedStatement.setString(2, p.getUniqueId().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
