package cz.thepatik.welcomeplugin.commands;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.subcommands.HelpCommand;
import cz.thepatik.welcomeplugin.commands.subcommands.ShowCreditsToCommand;
import cz.thepatik.welcomeplugin.commands.subcommands.UpdateCommand;
import cz.thepatik.welcomeplugin.commands.subcommands.VersionCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor, TabExecutor {
    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        subcommands.add(new UpdateCommand());
        subcommands.add(new VersionCommand());
        subcommands.add(new HelpCommand());
        subcommands.add(new ShowCreditsToCommand(WelcomePlugin.getPlugin()));
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
                welcomeHelpMessage.setColor(ChatColor.RED);
                welcomeHelpMessage.setBold(false);
                welcomeHelpMessage.setFont("minecraft:uniform");
                welcomeHelpMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/welcome help"));
                welcomeHelpMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click & run command").create()));

                TextComponent welcomeHelpMessageFirst = new TextComponent("You must specify a subcommand! For list of commands write ");
                welcomeHelpMessage.setColor(ChatColor.RED);
                welcomeHelpMessage.setBold(false);
                welcomeHelpMessage.setFont("minecraft:uniform");
               sender.spigot().sendMessage(welcomeHelpMessageFirst, welcomeHelpMessage);
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
