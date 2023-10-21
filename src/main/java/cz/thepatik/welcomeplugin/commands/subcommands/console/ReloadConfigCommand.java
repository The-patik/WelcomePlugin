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

public class ReloadConfigCommand extends SubCommandConsole {

    Functions functions = new Functions();

    @Override
    public String getName() {
        return "reloadconfig";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.reload";
    }

    @Override
    public String getDescription() {
        return "Reloads configuration";
    }

    @Override
    public String getSyntax() {
        return "/welcome reloadconfig";
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

        functions.welcomePlugin().saveConfig();

        server.getLogger().info("The plugin was successfully reloaded!");

    }
}
