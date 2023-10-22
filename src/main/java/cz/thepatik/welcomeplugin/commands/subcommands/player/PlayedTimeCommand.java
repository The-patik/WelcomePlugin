package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PlayedTimeCommand extends SubCommandPlayer {

    Functions functions = new Functions();

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
    public List<String> tabComplete(Player player, String[] args){
        return Collections.emptyList();
    }
    @Override
    public void perform(Player player, String[] args) {

        if (player.hasPermission(getPermissions())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (player, functions.getMessagesHandler().getMessages
                                        ("command-messages", "played-time"))));
        } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (player, functions.getMessagesHandler().getMessages
                                        ("command-messages", "no-permissions"))));
        }
    }
}
