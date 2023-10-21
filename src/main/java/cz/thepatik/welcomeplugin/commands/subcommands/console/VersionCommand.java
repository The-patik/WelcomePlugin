package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class VersionCommand extends SubCommandConsole {
    Functions functions = new Functions();

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.version";
    }

    @Override
    public String getDescription() {
        return "Shows installed version of plugin";
    }

    @Override
    public String getSyntax() {
        return "/welcome version";
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
        if (functions.welcomePlugin().getUpdater().checkForUpdates()) {
            server.getLogger().info("You are running WelcomePlugin v" + functions.welcomePlugin().getUpdater().getPluginVersion());
        } else {
            server.getLogger().info("You are running WelcomePlugin v" + functions.welcomePlugin().getUpdater().getPluginVersion());
            server.getLogger().info("But this version is old... For more info run /welcome update");
        }
    }
}
