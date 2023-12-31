package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class UpdateCommand extends SubCommandConsole {
    Functions functions = new Functions();

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.update";
    }

    @Override
    public String getDescription() {
        return "Check for updates";
    }

    @Override
    public String getSyntax() {
        return "/welcome update";
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
        if (functions.getUpdater().checkForUpdates()) {

            server.getLogger().info("The plugin is up to date!");

            } else {

            server.getLogger().warning("There is an update on: https://www.spigotmc.org/resources/welcomeplugin.112870/");
            server.getLogger().info("You are running WelcomePlugin v" + functions.getUpdater().getPluginVersion());
            server.getLogger().info("The new version is WelcomePlugin v" + functions.getUpdater().getNewVersion());

        }
    }
}
