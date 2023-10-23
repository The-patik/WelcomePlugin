package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.PlayerChecker.isPlayerOnline;

public class SetLeaveMessageCommand extends SubCommandConsole {
    Functions functions = new Functions();
    @Override
    public String getName() {
        return "setleavemessage";
    }

    @Override
    public String getPermissions() {
        return null;
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
    public List<String> tabComplete(String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        int newArguments = args.length - 3;
        String[] messageArgs = new String[newArguments];
        String leaveMessage = "";

        if (args.length == 1){
            sender.getServer().getLogger().info("You can change message of player that is online!");
            sender.getServer().getLogger().info("Usage: /welcome setleavemessage <playerName> <message with space>");
        } else if (!isPlayerOnline(args[1])) {
            sender.getServer().getLogger().info("Player " + args[1] + " is not online, or does not exists!");
        } else if (args.length > 2 && isPlayerOnline(args[1])) {
            String argsPlayer = args[1];
            messageArgs = Arrays.copyOfRange(args, 2, args.length);
            leaveMessage = String.join(" ", messageArgs);

            if (functions.welcomePlugin().databaseType().equals("sqlite")) {
                // Check if player is really online
                Arrays.sort(args);
                if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                    // Args into player
                    Player p = Bukkit.getPlayer(argsPlayer);
                    // Set player join message
                    functions.sqLitePlayerFunctions().setPlayerLeaveMessage(p, leaveMessage);

                }
            } else if (functions.welcomePlugin().databaseType().equals("mysql")){
                // Check if player is really online
                Arrays.sort(args);
                if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                    // Args into player
                    Player p = Bukkit.getPlayer(argsPlayer);
                    // Set player join message
                    functions.mysqlPlayerFunctions().setPlayerLeaveMessage(p, leaveMessage);

                }
            }

            functions.welcomePlugin().getLogger()
                    .info("Successfully changed leave message of player " + argsPlayer);

        }
    }
}
