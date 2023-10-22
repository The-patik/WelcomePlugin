package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveJoinMessageCommand extends SubCommandConsole {
    Functions functions = new Functions();
    @Override
    public String getName() {
        return "removejoinmessage";
    }

    @Override
    public String getPermissions() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Reset player join message";
    }

    @Override
    public String getSyntax() {
        return "/welcome removejoinmessage <who>";
    }

    @Override
    public boolean hasArguments() {
        return false;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (args.length == 1) {
            String argsPlayer = args[0];

            if (functions.welcomePlugin().databaseType().equals("sqlite")) {
                Arrays.sort(args);
                if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                    Player p = Bukkit.getPlayer(argsPlayer);
                    functions.mysqlPlayerFunctions().removePlayerJoinMessage(p);

                }
            }
            if (functions.welcomePlugin().databaseType().equals("mysql")){
                Arrays.sort(args);
                if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                    Player p = Bukkit.getPlayer(argsPlayer);
                    functions.mysqlPlayerFunctions().removePlayerJoinMessage(p);

                }
            }

            functions.welcomePlugin().getLogger()
                    .info("Successfully removed join message of player " + argsPlayer);

        }
    }
}
