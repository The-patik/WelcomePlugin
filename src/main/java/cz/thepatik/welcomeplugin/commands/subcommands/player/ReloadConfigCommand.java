package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ReloadConfigCommand extends SubCommandPlayer {

    WelcomePlugin plugin;
    public ReloadConfigCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }
    @Override
    public String getName() {
        return "reloadconfig";
    }

    @Override
    public @NotNull String getPermissions() {
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
    public List<String> tabComplete(Player p, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {

        if (player.hasPermission(getPermissions())){
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, plugin.getMessagesHandler().getMessages
                                    ("command-messages", "reloadconfig-command"))));
            plugin.saveConfig();
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, plugin.getMessagesHandler().getMessages
                                    ("command-messages", "no-permissions"))));
        }
    }
}
