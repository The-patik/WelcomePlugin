package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadConfigCommand extends SubCommandPlayer {

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
    public List<String> tabComplete(Player player, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {

        if (player.hasPermission(getPermissions())){
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, functions.getMessagesHandler().getMessages
                                    ("command-messages", "reload-config-command"))));
            functions.welcomePlugin().saveConfig();
            functions.welcomePlugin().reloadConfig();
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, functions.getMessagesHandler().getMessages
                                    ("error-messages", "no-permissions"))));
        }
    }
}
