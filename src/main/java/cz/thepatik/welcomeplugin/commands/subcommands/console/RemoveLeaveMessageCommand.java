package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.database.MySQLDatabase;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveLeaveMessageCommand extends SubCommandConsole {
    Functions functions = new Functions();
    @Override
    public String getName() {
        return "removeleavemessage";
    }

    @Override
    public String getPermissions() {
        return null;
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
                    functions.sqLitePlayerFunctions().removePlayerLeaveMessage(p);

                }
            }
            if (functions.welcomePlugin().databaseType().equals("mysql")){
                Arrays.sort(args);
                if (Arrays.binarySearch(args, argsPlayer) >= 0) {

                    Player p = Bukkit.getPlayer(argsPlayer);
                    functions.mysqlPlayerFunctions().removePlayerLeaveMessage(p);

                }
            }

            functions.welcomePlugin().getLogger()
                    .info("Successfully removed leave message of player " + argsPlayer);

        }
    }
}
