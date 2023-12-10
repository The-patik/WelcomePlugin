/**
package cz.thepatik.welcomeplugin.tasks;

import cz.thepatik.welcomeplugin.utils.Functions;
import cz.thepatik.welcomeplugin.utils.listeners.PlayerListener;
import cz.thepatik.welcomeplugin.WelcomePlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayTimeTask extends BukkitRunnable {
    private Player p;
    Functions functions = new Functions();
    WelcomePlugin plugin = functions.welcomePlugin();

    private int time;

    private void getTimeFromDB(Player p) throws SQLException{
        try(PreparedStatement preparedStatement = plugin.sqLiteDatabase.connection.prepareStatement("SELECT PlayTime " +
                "FROM PlayerData WHERE PlayerUUID = ?")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            time = resultSet.getInt("PlayTime");
        }
    }

    private void setTimeInDB(Player p, int t) throws SQLException{
        try(PreparedStatement preparedStatement = plugin.sqLiteDatabase.connection.prepareStatement("UPDATE PlayerData " +
                "SET PlayTime = ? WHERE PlayerUUID = ?")) {
            preparedStatement.setInt(1, t);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    PlayerListener playerHandler;

    public PlayTimeTask(PlayerListener playerHandler, Player player){
        this.playerHandler = playerHandler;
        p = player;
    }

    @Override
    public void run() {
        try {
            getTimeFromDB(p);
        } catch (SQLException e) {
            plugin.getLogger().severe("There was a problem with taking PlayTime from database: " + e);
        }
        time++;
        try {
            setTimeInDB(p, time);
        } catch (SQLException e) {
            plugin.getLogger().severe("There was a problem with putting PlayTime from database: " + e);
        }
    }
}
**/