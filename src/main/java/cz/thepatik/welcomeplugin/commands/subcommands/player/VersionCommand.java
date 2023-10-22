package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class VersionCommand extends SubCommandPlayer {
    WelcomePlugin plugin;
    public VersionCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }
    @Override
    public String getName() {
        return "version";
    }

    @Override
    public @NotNull String getPermissions() {
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
    public List<String> tabComplete(Player p, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission(getPermissions())) {
            if (plugin.getUpdater().checkForUpdates()) {
                player.sendMessage("The plugin version is: " + plugin.getUpdater().getPluginVersion());
            } else {
                TextComponent updateMessage = new TextComponent("/welcome update");
                updateMessage.setColor(net.md_5.bungee.api.ChatColor.RED);
                updateMessage.setItalic(true);
                updateMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/welcome update"));
                updateMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click & run command").create()));

                TextComponent updateMessageFirst = new TextComponent(ChatColor.YELLOW + "But this version is old... For more info run ");
                updateMessageFirst.setColor(net.md_5.bungee.api.ChatColor.RED);
                updateMessageFirst.setBold(false);

                player.sendMessage(ChatColor.GREEN + "The plugin version is: " + ChatColor.GOLD +plugin.getUpdater().getPluginVersion());
                player.spigot().sendMessage(updateMessageFirst, updateMessage);
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, plugin.getMessagesHandler().getMessages
                                    ("command-messages", "no-permissions"))));
        }
    }
}
