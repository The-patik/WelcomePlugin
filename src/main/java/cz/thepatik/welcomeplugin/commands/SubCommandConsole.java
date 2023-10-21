package cz.thepatik.welcomeplugin.commands;

import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommandConsole {
    Functions functions = new Functions();
    public abstract String getName();
    public abstract String getPermissions();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract boolean hasArguments();
    public abstract List<String> tabComplete(String[] args);
    public abstract void perform(CommandSender sender, String args[]);
}
