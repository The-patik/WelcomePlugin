package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class UpdateCommand extends SubCommandPlayer {
    Functions functions = new Functions();
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
    public List<String> tabComplete(Player player, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission(getPermissions())) {

            if (functions.welcomePlugin().getUpdater().checkForUpdates()) {

                player.sendMessage("The plugin is up to date!");

            } else {

                TextComponent spigotOpenURL = new TextComponent("Spigot");
                spigotOpenURL.setColor(net.md_5.bungee.api.ChatColor.RED);
                spigotOpenURL.setItalic(true);
                spigotOpenURL.setBold(true);
                spigotOpenURL.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/welcomeplugin.112870/"));
                spigotOpenURL.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Opens URL").create()));

                TextComponent welcomeUpdateMessage = new TextComponent(ChatColor.GREEN + "There is a new version on ");
                spigotOpenURL.setColor(net.md_5.bungee.api.ChatColor.RED);
                spigotOpenURL.setBold(false);

                player.spigot().sendMessage(welcomeUpdateMessage, spigotOpenURL);
                player.sendMessage(ChatColor.GREEN + "You are running WelcomePlugin v" + ChatColor.GOLD + functions.welcomePlugin().getUpdater().getPluginVersion());
                player.sendMessage(ChatColor.GREEN + "The new version is WelcomePlugin v" + ChatColor.GOLD + functions.welcomePlugin().getUpdater().getNewVersion());

            }
        } else {

            player.sendMessage(ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, functions.getMessagesHandler().getMessages
                                    ("command-messages", "no-permissions"))));

        }
    }
}
