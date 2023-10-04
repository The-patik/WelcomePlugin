package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SentMessagesCommand extends SubCommandConsole {
    WelcomePlugin plugin;
    public SentMessagesCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }
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
    }
}
