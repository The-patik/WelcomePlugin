package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.commands.SubCommand;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.PlayerChecker.isPlayerOnline;

public class PlayerJoinsCommand extends SubCommand {
    @Override
    public String getName() {
        return "playerjoins";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.playerjoins";
    }

    @Override
    public String getDescription() {
        return "Shows how many times player has joined";
    }

    @Override
    public String getSyntax() {
        return "/welcome playerjoins";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1){
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
                completions.add(onlinePlayer.getName());
            }
        }
        return completions;
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission(getPermissions()) && args.length == 1){
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.GREEN + "You have joined " + ChatColor.GOLD + "%WelcomePlugin_player_joins%" + ChatColor.GOLD + " times!"));
        } else if (player.hasPermission(getPermissions()) && args.length == 2) {
            if (isPlayerOnline(args[1])) {
                player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.GREEN + "The player " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " is now online and have joined " + ChatColor.GOLD + "%WelcomePlugin_player_" + args[1] +  "_joins%" + ChatColor.GREEN + " times!"));
            } else {
                player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.GREEN + "The player " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " is not online and have joined " + ChatColor.GOLD + "%WelcomePlugin_player_" + args[1] + "_joins%" + ChatColor.GREEN + " times!"));
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permissions!");
        }
    }
}
