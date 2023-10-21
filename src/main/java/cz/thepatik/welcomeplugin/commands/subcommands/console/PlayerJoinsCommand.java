package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import cz.thepatik.welcomeplugin.utils.Functions;
import cz.thepatik.welcomeplugin.utils.placeholders.PlayerJoins;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinsCommand extends SubCommandConsole {

    Functions functions = new Functions();

    @Override
    public String getName() {
        return "playerjoins";
    }

    @Override
    public String getPermissions() {
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
    public List<String> tabComplete(String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1){
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
                completions.add(onlinePlayer.getName());
            }
        }
        return completions;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        PlayerJoins playerJoins = new PlayerJoins(functions.welcomePlugin());

        Server server = sender.getServer();
            if (args.length == 1) {
                server.getLogger().info("This command can issue only players!");
            } else if (args.length == 2) {
                server.getLogger().info("The player " + args[1] + " has joined " + playerJoins.getElsePlayerJoins(args[1]) + " times!");
            }
    }
}
