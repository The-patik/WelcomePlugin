package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommand;
import cz.thepatik.welcomeplugin.database.Database;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

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
    public boolean hasArguments() {
        return true;
    }
    @Override
    public List<String> tabComplete(String[] args){
        return Collections.emptyList();
    }
    @Override
    public void perform(Player player, String[] args) {
       if (player.hasPermission(getPermissions())){
                player.sendMessage(ChatColor.GREEN + "You are playing on the server for " + ChatColor.GOLD + PlaceholderAPI.setPlaceholders(player.getPlayer(), "%WelcomePlugin_played_time%"));
       }else {
                player.sendMessage(ChatColor.RED + "You do not have permissions!");
       }
    }
}
