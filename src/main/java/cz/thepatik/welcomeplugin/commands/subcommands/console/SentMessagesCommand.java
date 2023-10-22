package cz.thepatik.welcomeplugin.commands.subcommands.console;

import cz.thepatik.welcomeplugin.commands.SubCommandConsole;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class SentMessagesCommand extends SubCommandConsole {
    Functions functions = new Functions();

    @Override
    public String getName() {
        return "sentmessages";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.sentmessages";
    }

    @Override
    public String getDescription() {
        return "Shows how many messages you sent";
    }

    @Override
    public String getSyntax() {
        return "/welcome sentmessages";
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

        server.getLogger().severe("This command can issue only players!");
    }
}
