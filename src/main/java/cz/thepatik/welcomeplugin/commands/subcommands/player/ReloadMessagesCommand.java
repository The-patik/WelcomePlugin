package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadMessagesCommand extends SubCommandPlayer {
    Functions functions = new Functions();
    @Override
    public String getName() {
        return "reloadmessages";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.reloadmessages";
    }

    @Override
    public String getDescription() {
        return "Reloads the messages config file";
    }

    @Override
    public String getSyntax() {
        return "/welcome reloadmessages";
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
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    PlaceholderAPI.setPlaceholders(player, functions.getMessagesHandler()
                            .getMessages("command-messages", "reload-messages-command"))));
            functions.getMessagesHandler().reloadConfig(WelcomePlugin.getPlugin());
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    PlaceholderAPI.setPlaceholders(player, functions.getMessagesHandler()
                            .getMessages("error-messages", "no-permissions"))));
        }
    }
}
