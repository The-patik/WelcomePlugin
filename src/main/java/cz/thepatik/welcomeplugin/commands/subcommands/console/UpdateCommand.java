package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
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

public class UpdateCommand extends SubCommandConsole {
    WelcomePlugin plugin;
    public UpdateCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }
    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.update";
    }

    @Override
    public String getDescription() {
        return "Check for updates";
    }

    @Override
    public String getSyntax() {
        return "/welcome update";
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
        if (plugin.getUpdater().checkForUpdates()) {

            server.getLogger().info("The plugin is up to date!");

            } else {

            server.getLogger().warning("There is an update on: https://www.spigotmc.org/resources/welcomeplugin.112870/");
            server.getLogger().info("You are running WelcomePlugin v" + plugin.getUpdater().getPluginVersion());
            server.getLogger().info("The new version is WelcomePlugin v" + plugin.getUpdater().getNewVersion());

        }
    }
}
