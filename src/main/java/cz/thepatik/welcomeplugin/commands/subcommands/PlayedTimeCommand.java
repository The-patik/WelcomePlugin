package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommand;
import cz.thepatik.welcomeplugin.database.Database;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayedTimeCommand extends SubCommand {
    @Override
    public String getName() {
        return "playedtime";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.playedtime";
    }

    @Override
    public String getDescription() {
        return "Shows played time on server";
    }

    @Override
    public String getSyntax() {
        return "/welcome playedtime";
    }

    @Override
    public void perform(Player player, String[] args) {
        Database database = WelcomePlugin.database;

        try{
            PreparedStatement preparedStatement = database.connection.prepareStatement("SELECT PlayTime FROM PlayerData WHERE PlayerUUID = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int playTime = resultSet.getInt("PlayTime");

            double playTimeMinutes = (double) playTime /60;

            BigDecimal playTimeDecimal = BigDecimal.valueOf(playTimeMinutes);
            playTimeDecimal = playTimeDecimal.setScale(1, RoundingMode.DOWN);

            if (player.hasPermission(getPermissions())){
                player.sendMessage(ChatColor.GREEN + "You are playing on the server for " + playTimeDecimal);
            }else {
                player.sendMessage(ChatColor.RED + "You do not have permissions!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
