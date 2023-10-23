package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.PlayerChecker.isPlayerOnline;

public class SentMessagesCommand extends SubCommandPlayer {
    Functions functions = new Functions();
    @Override
    public String getName() {
        return "sentmessages";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.sentmessages";
    }

    @Override
    public String getDescription() {
        return "Shows how many messages you sent";
    }

    @Override
    public String getSyntax() {
        return "/welcome sentmessages";
    }

    @Override
    public boolean hasArguments() {
        return false;
    }

    @Override
    public List<String> tabComplete(Player player, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1 && player.hasPermission("welcomeplugin.sentmessages.others")){
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
                completions.add(onlinePlayer.getName());
            }
        }
        return completions;
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission(getPermissions()) && args.length == 1){
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, functions.getMessagesHandler().getMessages
                                    ("command-messages", "sent-messages"))));
        } else if (player.hasPermission(getPermissions()) && args.length == 2 && player.hasPermission("welcomeplugin.sentmessages.others")) {
            if (isPlayerOnline(args[1])) {
                player.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (player, functions.getMessagesHandler()
                                        .getMessages("command-messages", "other-sent-messages")
                                        .replace("$1", args[1]))));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (player, functions.getMessagesHandler().getMessages
                                        ("error-messages", "player-not-online"))));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, functions.getMessagesHandler().getMessages
                                    ("error-messages", "no-permissions"))));
        }
    }
}
