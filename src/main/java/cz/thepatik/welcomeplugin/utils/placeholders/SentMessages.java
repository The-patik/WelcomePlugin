package cz.thepatik.welcomeplugin.utils.placeholders;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.database.DatabaseHandler;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SentMessages {

    WelcomePlugin plugin;
    public SentMessages(WelcomePlugin plugin){
        this.plugin = plugin;
    }

    public int getMessagesSent(Player p) {
        int playerMessagesSent = 0;

        // SQLite database
        if (plugin.databaseType().equals("sqlite")) {
            SQLiteDatabase database = plugin.sqLiteDatabase;

            try {
                PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT SentMessages" +
                        " FROM PlayerData WHERE PlayerUUID = ?");
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                playerMessagesSent = resultSet.getInt("SentMessages");
            } catch (SQLException e) {
                System.out.println("There was a problem with getting messages sent by player!");
            }
            return playerMessagesSent;
        } // MySQL database
        else if (plugin.databaseType().equals("mysql")) {
            DatabaseHandler database = plugin.mySQLDatabase;

            try {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT SentMessages" +
                        " FROM PlayerData WHERE PlayerUUID = ?");
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                playerMessagesSent = resultSet.getInt("SentMessages");
            } catch (SQLException e) {
                System.out.println("There was a problem with getting messages sent by player!");
            }
            return playerMessagesSent;
        }
        return 0;
    }

}
