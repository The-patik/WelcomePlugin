package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.CommandManager;
import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.commands.SubCommandPlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends SubCommandConsole {
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
    public void perform(CommandSender sender, String[] args) {
        Server server = sender.getServer();

        server.getLogger().info("");
        server.getLogger().info("Welcome Plugin command list");
        server.getLogger().info("");
        server.getLogger().info("--------------------------------------------------");
        server.getLogger().info("");
        server.getLogger().info("/welcome help - Shows all commands");
        server.getLogger().info("/welcome playedtime - Shows time you spent playing on server");
        server.getLogger().info("/welcome playerjoins - Shows how many times you joined the server");
        server.getLogger().info("/welcome playerjoins <playerName> - Shows how many times specified player joined the server");
        server.getLogger().info("/welcome showcreditsto <who> - Set config show-credits in game! For more info run without who");
        server.getLogger().info("/welcome update - Checks for updates");
        server.getLogger().info("/welcome version - Shows version of plugin");
        server.getLogger().info("");
        server.getLogger().info("--------------------------------------------------");
        server.getLogger().info("");
    }
}
