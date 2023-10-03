package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommand;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadConfigCommand extends SubCommand {

    WelcomePlugin plugin;
    public ReloadConfigCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }
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
        return "Reloads configuration and messages";
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
    public void perform(Player player, String[] args) {

        if (player.hasPermission(getPermissions())){
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, plugin.getMessagesHandler().getMessages
                                    ("command-messages", "reloadconfig-command"))));
        }
        plugin.getMessagesHandler().saveMessagesConfig(plugin);
        plugin.getMessagesHandler().reloadConfig(plugin);
    }
}
