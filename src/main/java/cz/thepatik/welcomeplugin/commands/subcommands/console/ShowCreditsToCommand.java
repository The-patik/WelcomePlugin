package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ShowCreditsToCommand extends SubCommandConsole {

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
    public boolean hasArguments() {
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1){
            completions.add("newcomers");
            completions.add("everyone");
            completions.add("nobody");
        }
        return completions;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");

        Server server = sender.getServer();

        if (args.length == 1){
            String toWho = cs.getString("show-credits");
            server.getLogger().info("Now showing credits to: " + toWho);
            server.getLogger().info("To edit you must specify to who show credits! See docs!");
        } else if (args.length == 2){
            String toWho = args[1];

            cs.set("show-credits", toWho);
            server.getLogger().info("Now showing credits only to " + toWho);

        }
    }
}
