package cz.thepatik.welcomeplugin.tasks;

import cz.thepatik.welcomeplugin.utils.handlers.PlayerHandler;
import cz.thepatik.welcomeplugin.WelcomePlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static cz.thepatik.welcomeplugin.WelcomePlugin.database;

public class PlayTimeTask extends BukkitRunnable {
    private Player p;
    private WelcomePlugin plugin;
    private PlayTimeTask(WelcomePlugin plugin){
        this.plugin = plugin;
    }

    private int time;

    private void getTimeFromDB(Player p) throws SQLException{
        try(PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayTime " +
                "FROM PlayerData WHERE PlayerUUID = ?")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            time = resultSet.getInt("PlayTime");
        }
    }
    private void setTimeInDB(Player p, int t) throws SQLException{
        try(PreparedStatement preparedStatement = database.connection.prepareStatement("UPDATE PlayerData " +
                "SET PlayTime = ? WHERE PlayerUUID = ?")) {
            preparedStatement.setInt(1, t);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    PlayerHandler playerHandler;

    public PlayTimeTask(PlayerHandler playerHandler, Player player){
        this.playerHandler = playerHandler;
        p = player;
    }

    @Override
    public void run() {
        try {
            getTimeFromDB(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        time++;
        try {
            setTimeInDB(p, time);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
