package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class PlayedTimeCommand extends SubCommandPlayer {

    WelcomePlugin plugin;
    public PlayedTimeCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "playedtime";
    }

    @Override
    public @NotNull String getPermissions() {
        return "welcomeplugin.playedtime";
    }

    @Override
    public String getDescription() {
        return "Shows played time on server";
    }

    @Override
    public String getSyntax() {
        return "/welcome playedtime";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }
    @Override
    public List<String> tabComplete(Player p, String[] args){
        return Collections.emptyList();
    }
    @Override
    public void perform(Player player, String[] args) {

            if (player.hasPermission(getPermissions())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (player, plugin.getMessagesHandler().getMessages
                                        ("command-messages", "played-time"))));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (player, plugin.getMessagesHandler().getMessages
                                        ("command-messages", "no-permissions"))));
            }
        /*/ } else if (sender instanceof ConsoleCommandSender){
            plugin.getLogger().info("This command can issue only players!");
        }/*/
    }
}
