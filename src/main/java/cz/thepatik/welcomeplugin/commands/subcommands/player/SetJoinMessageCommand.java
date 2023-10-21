package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.PlayerChecker.isPlayerOnline;

public class SetJoinMessageCommand extends SubCommandPlayer {
    Functions functions = new Functions();
    @Override
    public String getName() {
        return "setjoinmessage";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.custom-message";
    }

    @Override
    public String getDescription() {
        return "Set join message for player";
    }

    @Override
    public String getSyntax() {
        return "/welcome setjoinmessage <toWho> <message>";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {
        String joinMessage = "";
        String[] messageArgs;
        if (args.length > 3) {
            int newArguments = args.length - 3;
             messageArgs = new String[newArguments];
        } else {
            messageArgs = new String[0];
        }

        if (player.hasPermission(getPermissions())) {

            // If command without arguments
            if (args.length == 1) {
                // Player has permission to change others join message
                if (player.hasPermission("welcomeplugin.others-custom-message")) {
                    player.sendMessage(ChatColor.GREEN + "You can change your join message, or message of player that is online!");
                    player.sendMessage(ChatColor.GREEN + "Usage: " + ChatColor.YELLOW + "/welcome setjoinmessage <playerName> <message with space>");
                } else {
                    // If he doesn't have permission
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("ingame-messages", "set-join-message-usage"))));
                }
            } else if (args.length >= 2) {
                 /* Check if player has permission to change others join message
                    and if player wants to change others join message
                  */
                if (player.hasPermission("welcomeplugin.others-custom-message") && isPlayerOnline(args[1])) {
                    messageArgs = Arrays.copyOfRange(args, 2, args.length);
                    joinMessage = String.join(" ", messageArgs);

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("command-messages",
                                                    "set-join-message-others").replace("$1", args[1]))));
                } else {
                    messageArgs = Arrays.copyOfRange(args, 1, args.length);
                    joinMessage = String.join(" ", messageArgs);

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("command-messages",
                                                    "set-join-message-own"))));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        PlaceholderAPI.setPlaceholders(player, functions.getMessagesHandler()
                                .getMessages("ingame-messages", "no-permissions"))));
            }
            player.sendMessage(joinMessage);
        }
    }
}
