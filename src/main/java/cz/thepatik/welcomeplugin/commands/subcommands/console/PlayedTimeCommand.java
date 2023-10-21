package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PlayedTimeCommand extends SubCommandConsole {

    Functions functions = new Functions();

    @Override
    public String getName() {
        return "playedtime";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.playedtime";
    }

    @Override
    public String getDescription() {
        return "Shows played time on server";
    }

    @Override
    public String getSyntax() {
        return "/welcome playedtime";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }
    @Override
    public List<String> tabComplete(String[] args){
        return Collections.emptyList();
    }
    @Override
    public void perform(CommandSender sender, String[] args) {
        Server server = sender.getServer();

        server.getLogger().severe("This command can issue only players!");
    }
}
