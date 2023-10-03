package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommand;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends SubCommand {
    WelcomePlugin plugin;
    public HelpCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermissions() {
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
    public List<String> tabComplete(String[] args) {
        return Collections.emptyList();
    }

    @Override
    public void perform(Player player, String[] args) {

            if (player.hasPermission(getPermissions())) {
                player.sendMessage("");
                player.sendMessage("Welcome Plugin command list");
                player.sendMessage("");
                player.sendMessage("--------------------------------------------------");
                player.sendMessage("");
                player.sendMessage("/welcome help - Shows all commands");
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
