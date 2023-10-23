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
    public List<String> tabComplete(Player player, String[] args) {
        List<String> completitions = new ArrayList<>();

        if (args.length == 1 && player.hasPermission("welcomeplugin.others-custom-message")){
            for (Player playerOnline : Bukkit.getOnlinePlayers()){
                completitions.add(playerOnline.getName());
            }
        }
        return completitions;
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

            // If player has permissions
            // If command without arguments
            if (args.length == 1) {

                // Player has permission to change others join message
                if (player.hasPermission("welcomeplugin.others-custom-message")) {
                    player.sendMessage(ChatColor.GREEN
                            + "You can change your join message, or message of player that is online!");
                    player.sendMessage(ChatColor.GREEN + "Usage: "
                            + ChatColor.YELLOW + "/welcome setjoinmessage <playerName> <message with space>");

                } else {

                    // If he doesn't have permission
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("command-messages",
                                                    "set-join-message-usage"))));

                }
            } else if (args.length >= 2) {

                String argsPlayer = args[0];

                 /* Check if player has permission to change others join message
                    and if player wants to change others join message
                  */
                if (player.hasPermission("welcomeplugin.others-custom-message") && isPlayerOnline(args[0])) {
                    messageArgs = Arrays.copyOfRange(args, 2, args.length);
                    joinMessage = String.join(" ", messageArgs);

                    // If database is SQLite
                    if (functions.welcomePlugin().databaseType().equals("sqlite")) {

                        // Check if player is really online
                        Arrays.sort(args);
                        if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                            // Args into player
                            Player p = Bukkit.getPlayer(argsPlayer);
                            // Set player join message
                            functions.sqLitePlayerFunctions().setPlayerJoinMessage(p, joinMessage);

                        }

                    } else if (functions.welcomePlugin().databaseType().equals("mysql")){

                        // Check if player is really online
                        Arrays.sort(args);
                        if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                            // Args into player
                            Player p = Bukkit.getPlayer(argsPlayer);
                            // Set player join message
                            functions.mysqlPlayerFunctions().setPlayerJoinMessage(p, joinMessage);
                        }
                    }

                    // Cast success message to player
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("command-messages",
                                                    "set-join-message-others").replace("$1", args[0]))));

                } else if (player.hasPermission("welcomeplugin.others-custom-message") && !isPlayerOnline(args[0])){

                    // If player not online, cast player not online
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("error-messages",
                                                    "player-not-online"))));

                } else if (!player.hasPermission("welcomeplugin.others-custom-message") && isPlayerOnline(args[1])) {

                    // No permissions message
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("error-messages",
                                                    "no-permissions"))));

                } else {

                    // In all other cases...
                    messageArgs = Arrays.copyOfRange(args, 1, args.length);
                    joinMessage = String.join(" ", messageArgs);

                    // If database sqlite
                    if (functions.welcomePlugin().databaseType().equals("sqlite")) {

                        // Set player message
                        functions.sqLitePlayerFunctions().setPlayerJoinMessage(player, joinMessage);

                    } else if (functions.welcomePlugin().databaseType().equals("mysql")){

                        // If database mysql
                        // Set player message
                        functions.mysqlPlayerFunctions().setPlayerJoinMessage(player, joinMessage);

                    }

                    // Send success message
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            PlaceholderAPI.setPlaceholders(player,
                                    functions.getMessagesHandler().
                                            getMessages("command-messages", "set-join-message-own"))));

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
