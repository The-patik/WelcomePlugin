package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static cz.thepatik.welcomeplugin.utils.PlayerChecker.isPlayerOnline;

public class PlayerJoinsCommand extends SubCommandPlayer {

    WelcomePlugin plugin;
    public PlayerJoinsCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "playerjoins";
    }

    @Override
    public @NotNull String getPermissions() {
        return "welcomeplugin.playerjoins";
    }

    @Override
    public String getDescription() {
        return "Shows how many times player has joined";
    }

    @Override
    public String getSyntax() {
        return "/welcome playerjoins";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public List<String> tabComplete(Player p, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1 && p.hasPermission("welcomeplugin.playerjoins.others")){
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
                completions.add(onlinePlayer.getName());
            }
        }
        return completions;
    }

    @Override
    public void perform(Player player, String[] args) {
            if (player.hasPermission(getPermissions()) && args.length == 1) {
                player.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (player, plugin.getMessagesHandler().getMessages
                                        ("command-messages", "player-joins"))));
            } else if (player.hasPermission(getPermissions()) && args.length == 2 && player.hasPermission("welcomeplugin.playerjoins.other")) {
                if (isPlayerOnline(args[1])) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', PlaceholderAPI.setPlaceholders
                                    (player, plugin.getMessagesHandler()
                                            .getMessages("command-messages", "other-player-joins")
                                            .replace("$1", args[1]))));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', PlaceholderAPI.setPlaceholders
                                    (player, plugin.getMessagesHandler().getMessages
                                            ("command-messages", "player-not-online"))));
                }
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
