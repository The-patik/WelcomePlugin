package cz.thepatik.welcomeplugin.commands;

import cz.thepatik.welcomeplugin.commands.subcommands.HelpCommand;
import cz.thepatik.welcomeplugin.commands.subcommands.UpdateCommand;
import cz.thepatik.welcomeplugin.commands.subcommands.VersionCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;

public class CommandManager implements CommandExecutor, TabExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        subcommands.add(new UpdateCommand());
        subcommands.add(new VersionCommand());
        subcommands.add(new HelpCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(p, args);
                    }
                }
            } else if (args.length == 0){
                TextComponent welcomeHelpMessage = new TextComponent("/welcome help");
                welcomeHelpMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/welcome help"));
                welcomeHelpMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click & run command").create()));
                p.sendMessage(ChatColor.RED + "You must specify a subcommand! For list of commands write" + welcomeHelpMessage);
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        ArrayList<String> emptyList = new ArrayList<>();

        if (args.length == 1){
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubcommands().size(); i++){
                subcommandsArguments.add(getSubcommands().get(i).getName());
            }
            return subcommandsArguments;
        }
        return emptyList;
    }
}
