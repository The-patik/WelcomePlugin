package cz.thepatik.welcomeplugin.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommandConsole {

    public abstract String getName();
    public abstract String getPermissions();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract boolean hasArguments();
    public abstract List<String> tabComplete(String[] args);
    public abstract void perform(CommandSender sender, String args[]);
}
