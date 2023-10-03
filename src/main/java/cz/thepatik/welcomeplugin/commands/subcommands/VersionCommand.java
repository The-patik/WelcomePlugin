package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommand;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Double.parseDouble;

public class VersionCommand extends SubCommand {
    WelcomePlugin plugin;
    public VersionCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }
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
    public void perform(Player player, String[] args) {
        if (player.hasPermission(getPermissions())) {
            if (Objects.equals(plugin.getVersionCheck().getPluginVersion(), plugin.getVersionCheck().getCurrentOnlineVersion())) {
                player.sendMessage("The plugin version is: " + plugin.getVersionCheck().getPluginVersion());
            } else {
                TextComponent updateMessage = new TextComponent("/welcome update");
                updateMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/welcome update"));
                updateMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Runs command /welcome update").create()));

                player.sendMessage("The plugin version is: " + plugin.getVersionCheck().getPluginVersion());
                player.sendMessage("But this version is old... Look at " + updateMessage + " for more info");
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, plugin.getMessagesHandler().getMessages
                                    ("command-messages", "no-permissions"))));
        }
    }
}
