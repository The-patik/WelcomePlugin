package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.commands.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.VersionCheck.getCurrentOnlineVersion;
import static cz.thepatik.welcomeplugin.utils.VersionCheck.pluginVersion;
import static java.lang.Double.parseDouble;

public class UpdateCommand extends SubCommand {

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
    public void perform(Player player, String[] args) {
        if (player.hasPermission(getPermissions())) {
            TextComponent pluginURL = new TextComponent("Spigot");
            pluginURL.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://spigotmc.org"));
            pluginURL.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Opens URL").create()));
            if (pluginVersion == parseDouble(getCurrentOnlineVersion())) {
                player.sendMessage("The plugin is up to date!");
            } else {
                player.sendMessage("There is a new version on" + pluginURL);
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permissions!");
        }
    }
}
