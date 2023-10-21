package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.PlayerChecker.isPlayerOnline;

public class SetJoinMessageCommand extends SubCommandConsole {
    @Override
    public String getName() {
        return "setjoinmessage";
    }

    @Override
    public String getPermissions() {
        return null;
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
    public void perform(CommandSender sender, String[] args) {
        int newArguments = args.length - 3;
        String[] messageArgs = new String[newArguments];

        if (args.length == 1){
            sender.getServer().getLogger().info("You can change message of player that is online!");
            sender.getServer().getLogger().info("Usage: /welcome setjoinmessage <playerName> <message with space>");
        } else if (args.length >= 2 && !isPlayerOnline(args[2])) {
            sender.getServer().getLogger().info("Player " + args[2] + " is not online, or does not exists!");
        } else if (args.length >2 && isPlayerOnline(args[2])) {
            for (int i = 2; i <= args.length; i++) {
                for (int y = 0; y <= args.length; y++) {
                    messageArgs[y] = args[i];
                }
            }
        }
        sender.getServer().getLogger().info(Arrays.toString(messageArgs));
    }
}
