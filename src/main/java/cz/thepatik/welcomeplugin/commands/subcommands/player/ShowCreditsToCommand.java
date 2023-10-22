package cz.thepatik.welcomeplugin.commands.subcommands.player;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShowCreditsToCommand extends SubCommandPlayer {

    private final WelcomePlugin plugin;

    public ShowCreditsToCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "showcreditsto";
    }

    @Override
    public @NotNull String getPermissions() {
        return "welcomeplugin.showcreditsto";
    }

    @Override
    public String getDescription() {
        return "Edits config show-credits-to in-game";
    }

    @Override
    public String getSyntax() {
        return "/welcome showcreditsto <who>";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public List<String> tabComplete(Player p, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1){
            completions.add("newcomers");
            completions.add("everyone");
            completions.add("nobody");
        }
        return completions;
    }

    @Override
    public void perform(Player player, String[] args) {
        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");

        if (player.hasPermission(getPermissions())){
            if (args.length == 1){
                String toWho = cs.getString("show-credits");
                player.sendMessage(ChatColor.GREEN + "Now showing credits to: " + ChatColor.GOLD + toWho);
                player.sendMessage(ChatColor.RED + "To edit you must specify to who -" + ChatColor.DARK_GREEN + " newcomers" + ChatColor.RED + " or" + ChatColor.DARK_GREEN + " everyone" + ChatColor.RED + " or" + ChatColor.DARK_GREEN + " nobody");
            } else if (args.length == 2){
                String toWho = args[1];

                cs.set("show-credits", toWho);
                player.sendMessage(ChatColor.GREEN + "Now showing credits only to " + ChatColor.GOLD + toWho);

            }
        }else {
            player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes
                    ('&', PlaceholderAPI.setPlaceholders
                            (player, plugin.getMessagesHandler().getMessages
                                    ("command-messages", "no-permissions"))));
        }
    }
}
