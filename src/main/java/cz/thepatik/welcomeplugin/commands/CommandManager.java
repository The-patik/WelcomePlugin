package cz.thepatik.welcomeplugin.commands;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.commands.subcommands.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor, TabExecutor {
    WelcomePlugin welcomePlugin;
    private CommandManager(WelcomePlugin welcomePlugin){
        this.welcomePlugin = welcomePlugin;
    }
    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    // Register all commands
    public CommandManager(){
        subcommands.add(new UpdateCommand(WelcomePlugin.getPlugin()));
        subcommands.add(new VersionCommand(WelcomePlugin.getPlugin()));
        subcommands.add(new HelpCommand(WelcomePlugin.getPlugin()));
        subcommands.add(new ShowCreditsToCommand(WelcomePlugin.getPlugin()));
        subcommands.add(new PlayedTimeCommand(WelcomePlugin.getPlugin()));
        subcommands.add(new PlayerJoinsCommand(WelcomePlugin.getPlugin()));
        subcommands.add(new ReloadConfigCommand(WelcomePlugin.getPlugin()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String command = cmd.toString();
        if (sender instanceof Player){
            // Run command if sender is a player

            Player p = (Player) sender;

            if (args.length > 0){
                // Run command

                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(p, args);
                    }
                }
            } else if (args.length == 0){
                // Run when no subcommand

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
        } else if (sender instanceof ConsoleCommandSender) {
            
        }

        return true;

    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

    // Tab complete logic
    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1){
            // Find subcommand
            for (int i = 0; i < getSubcommands().size(); i++){
                completions.add(getSubcommands().get(i).getName());
            }
        } else if (args.length >= 2) {
            // Find corresponding subcommand
            SubCommand subCommand = null;
            for (SubCommand sc : subcommands) {
                if (sc.getName().equalsIgnoreCase(args[0])) {
                    subCommand = sc;
                    break;
                }
            }

            if (subCommand != null) {
                // Autocomplete suggestions for subcommand arguments
                String[] subArgs = new String[args.length - 1];
                System.arraycopy(args, 1, subArgs, 0, subArgs.length);
                completions.addAll(subCommand.tabComplete(subArgs));
            }
        }

        // Filter and return autocomplete suggestions based on what the user has typed
        String currentArg = args[args.length - 1].toLowerCase();
        completions.removeIf(suggestion -> !suggestion.toLowerCase().startsWith(currentArg));

        return completions;
    }
}
