package cz.thepatik.welcomeplugin.commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommandPlayer {
    public abstract String getName();
    public abstract String getPermissions();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract boolean hasArguments();
    public abstract List<String> tabComplete(Player player, String[] args);
    public abstract void perform(Player player, String args[]);
}
