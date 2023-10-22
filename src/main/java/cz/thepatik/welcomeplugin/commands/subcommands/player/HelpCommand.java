package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends SubCommandPlayer {
    WelcomePlugin plugin;
    public HelpCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public @NotNull String getPermissions() {
        return "welcomeplugin.help";
    }

    @Override
    public String getDescription() {
        return "Shows all commands";
    }

    @Override
    public String getSyntax() {
        return "/welcome help";
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
                player.sendMessage("");
                player.sendMessage("Welcome Plugin Help Page");
                player.sendMessage("");
                player.sendMessage("--------------------------------------------------");
                player.sendMessage("");
                player.sendMessage("/welcome help - Shows all commands");
                player.sendMessage("/welcome playedtime - Shows time you spent playing on server");
                player.sendMessage("/welcome playerjoins - Shows how many times you joined the server");
                player.sendMessage("/welcome playerjoins <playerName> - Shows how many times specified player joined the server");
                player.sendMessage("/welcome showcreditsto <who> - Set config show-credits in game! For more info run without who");
                player.sendMessage("/welcome update - Checks for updates");
                player.sendMessage("/welcome version - Shows version of plugin");
                player.sendMessage("");
                player.sendMessage("--------------------------------------------------");
                player.sendMessage("");
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (player, plugin.getMessagesHandler().getMessages
                                        ("command-messages", "no-permissions"))));
            }
        /*/else {
            plugin.getLogger().info("");
            plugin.getLogger().info("Welcome Plugin command list");
            plugin.getLogger().info("");
            plugin.getLogger().info("--------------------------------------------------");
            plugin.getLogger().info("");
            plugin.getLogger().info("/welcome help - Shows all commands");
            plugin.getLogger().info("/welcome update - Checks for updates");
            plugin.getLogger().info("/welcome version - Shows version of plugin");
            plugin.getLogger().info("");
            plugin.getLogger().info("--------------------------------------------------");
            plugin.getLogger().info("");
        }/*/
    }
}
