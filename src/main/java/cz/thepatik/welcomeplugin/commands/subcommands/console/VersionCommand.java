package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class VersionCommand extends SubCommandConsole {
    Functions functions = new Functions();

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.version";
    }

    @Override
    public String getDescription() {
        return "Shows installed version of plugin";
    }

    @Override
    public String getSyntax() {
        return "/welcome version";
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
        if (functions.welcomePlugin().getUpdater().checkForUpdates()) {
            server.getLogger().info("You are running WelcomePlugin v" + functions.welcomePlugin().getUpdater().getPluginVersion());
        } else {
            server.getLogger().info("You are running WelcomePlugin v" + functions.welcomePlugin().getUpdater().getPluginVersion());
            server.getLogger().info("But this version is old... For more info run /welcome update");
        }
    }
}
