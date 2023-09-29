package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.security.auth.login.Configuration;

public class ShowCreditsToCommand extends SubCommand {

    private final WelcomePlugin plugin;

    public ShowCreditsToCommand(WelcomePlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "showcreditsto";
    }

    @Override
    public String getPermissions() {
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
    public void perform(Player player, String[] args) {
        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");

        if (player.hasPermission(getPermissions())){
            if (args.length == 1){
                player.sendMessage(ChatColor.RED + "You must specify to who -" + ChatColor.DARK_GREEN + " newcomers" + ChatColor.RED + " or" + ChatColor.DARK_GREEN + " everyone");
            } else if (args.length == 2){
                String toWho = args[1];

                cs.set("show-credits", toWho);
                player.sendMessage(ChatColor.GREEN + "Now showing credits only to " + ChatColor.GOLD + toWho);

            }
        }else {
            player.sendMessage(ChatColor.RED + "You do not have permissions!");
        }
    }
}
