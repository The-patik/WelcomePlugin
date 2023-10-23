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

public class RemoveLeaveMessageCommand extends SubCommandPlayer {
    Functions functions = new Functions();
    @Override
    public String getName() {
        return "removeleavemessage";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.custom-message";
    }

    @Override
    public String getDescription() {
        return "Reset player leave message";
    }

    @Override
    public String getSyntax() {
        return "/welcome removeleavemessage <who>";
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

        if (player.hasPermission(getPermissions())){
            if (args.length == 1) {
                if (functions.welcomePlugin().databaseType().equals("sqlite")) {

                    functions.sqLitePlayerFunctions().removePlayerLeaveMessage(player);

                } else if (functions.welcomePlugin().databaseType().equals("mysql")){

                    functions.mysqlPlayerFunctions().removePlayerLeaveMessage(player);

                }

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        PlaceholderAPI.setPlaceholders(player,
                                functions.getMessagesHandler().
                                        getMessages("command-messages",
                                                "remove-leave-message-own"))));

            } else if (args.length == 2 && player.hasPermission("welcomeplugin.custom-message.others") && isPlayerOnline(args[1])) {

                String argsPlayer = args[1];
                if (functions.welcomePlugin().databaseType().equals("sqlite")) {

                    Arrays.sort(args);
                    if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                        Player p = Bukkit.getPlayer(argsPlayer);
                        functions.sqLitePlayerFunctions().removePlayerLeaveMessage(p);

                    }

                } else if (functions.welcomePlugin().databaseType().equals("mysql")) {

                    Arrays.sort(args);
                    if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                        Player p = Bukkit.getPlayer(argsPlayer);
                        functions.mysqlPlayerFunctions().removePlayerLeaveMessage(p);

                    }
                }

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        PlaceholderAPI.setPlaceholders(player,
                                functions.getMessagesHandler().
                                        getMessages("command-messages",
                                                "remove-leave-message-others").replace("$1", argsPlayer))));

            } else if (args.length == 2 && player.hasPermission("welcomeplugin.custom-message.others") && !isPlayerOnline(args[1])){

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        PlaceholderAPI.setPlaceholders(player,
                                functions.getMessagesHandler().
                                        getMessages("error-messages",
                                                "player-not-online"))));

            } else if (args.length == 2 && !player.hasPermission("welcomeplugin.custom-message.others") && isPlayerOnline(args[1])) {

                // No permissions message
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        PlaceholderAPI.setPlaceholders(player,
                                functions.getMessagesHandler().
                                        getMessages("error-messages",
                                                "no-permissions"))));

            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    PlaceholderAPI.setPlaceholders(player, functions.getMessagesHandler()
                            .getMessages("error-messages", "no-permissions"))));
        }
    }
}
