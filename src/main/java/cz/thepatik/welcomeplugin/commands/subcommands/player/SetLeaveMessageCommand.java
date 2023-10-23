package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.PlayerChecker.isPlayerOnline;

public class SetLeaveMessageCommand extends SubCommandPlayer {
    Functions functions = new Functions();
    @Override
    public String getName() {
        return "setleavemessage";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.custom-message";
    }

    @Override
    public String getDescription() {
        return "Set leave message for player";
    }

    @Override
    public String getSyntax() {
        return "/welcome setleavemessage <toWho> <message>";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public List<String> tabComplete(Player player, String[] args) {
        List<String> completitions = new ArrayList<>();

        if (args.length == 1 && player.hasPermission("welcomeplugin.custom-message.others")){
            for (Player playerOnline : Bukkit.getOnlinePlayers()){
                completitions.add(playerOnline.getName());
            }
        }
        return completitions;
    }

    @Override
    public void perform(Player player, String[] args) {
        String leaveMessage = "";
        String[] messageArgs;
        if (args.length > 3) {
            int newArguments = args.length - 3;
            messageArgs = new String[newArguments];
        } else {
            messageArgs = new String[0];
        }

        // Check if player has permission
        if (player.hasPermission(getPermissions())) {

            // If command without arguments
            if (args.length == 1) {

                // Player has permission to change others join message
                if (player.hasPermission("welcomeplugin.custom-message.others")) {
                    player.sendMessage(ChatColor.GREEN
                            + "You can change your leave message, or message of player that is online!");
                    player.sendMessage(ChatColor.GREEN + "Usage: "
                            + ChatColor.YELLOW + "/welcome setleavemessage <playerName> <message with space>");

                } else {

                    // If he doesn't have permission
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("command-messages",
                                                    "set-leave-message-usage"))));

                }
            } else if (args.length >= 2) {

                String argsPlayer = args[1];

                 /* Check if player has permission to change others join message
                    and if player wants to change others join message
                  */
                if (player.hasPermission("welcomeplugin.custom-message.others") && isPlayerOnline(args[1])) {
                    messageArgs = Arrays.copyOfRange(args, 2, args.length);
                    leaveMessage = String.join(" ", messageArgs);

                    // Check if database is SQLite
                    if (functions.welcomePlugin().databaseType().equals("sqlite")) {

                        // Check if player is really online
                        Arrays.sort(args);
                        if (Arrays.binarySearch(args, argsPlayer) >= 0){

                            // Set players leave message
                            Player p = Bukkit.getPlayer(argsPlayer);
                            functions.sqLitePlayerFunctions().setPlayerLeaveMessage(p, leaveMessage);

                        }

                    } else if (functions.welcomePlugin().databaseType().equals("mysql")){

                        // If database is mysql
                        // Check if player is really online
                        Arrays.sort(args);
                        if (Arrays.binarySearch(args, argsPlayer) >= 0){

                            // Set players leave message
                            Player p = Bukkit.getPlayer(argsPlayer);
                            functions.mysqlPlayerFunctions().setPlayerLeaveMessage(p, leaveMessage);

                        }
                    }

                    // Send success message
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("command-messages",
                                                    "set-leave-message-others").replace("$1", args[1]))));

                } else if (player.hasPermission("welcomeplugin.others-custom-message") && !isPlayerOnline(args[0])) {

                    // If player not online, cast player not online
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("error-messages",
                                                    "player-not-online"))));

                } else if (!player.hasPermission("welcomeplugin.custom-message.others") && isPlayerOnline(args[0])) {

                    // No permissions message
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("error-messages",
                                                    "no-permissions"))));

                } else {

                    // In other cases...
                    messageArgs = Arrays.copyOfRange(args, 1, args.length);
                    leaveMessage = String.join(" ", messageArgs);

                    if (functions.welcomePlugin().databaseType().equals("sqlite")) {

                        functions.sqLitePlayerFunctions().setPlayerLeaveMessage(player, leaveMessage);

                    } else if (functions.welcomePlugin().databaseType().equals("mysql")){

                        functions.mysqlPlayerFunctions().setPlayerLeaveMessage(player, leaveMessage);

                    }

                    // Send success message
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("command-messages",
                                                    "set-leave-message-own"))));

                }
            }
        } else {
            // If player doesn't have permissions...
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    PlaceholderAPI.setPlaceholders(player, functions.getMessagesHandler()
                            .getMessages("error-messages", "no-permissions"))));
        }
    }
}