package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.PlayerChecker.isPlayerOnline;

public class SentMessagesCommand extends SubCommandConsole {
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
    public List<String> tabComplete(String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Server server = sender.getServer();

        server.getLogger().severe("This command can issue only players!");

        /*
        if (args.length == 1){
            server.getLogger().info("You must specify player name!");
            server.getLogger().info("Usage: /welcome sentmessage <playerName>");
        } else if (args.length == 2) {
            if (isPlayerOnline(args[1])) {
                server.getLogger().info("Player " + args[1] + " sent %");
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
        */
    }
}
