package cz.thepatik.welcomeplugin.commands;

import cz.thepatik.welcomeplugin.commands.subcommands.player.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor, TabExecutor {
    private final ArrayList<SubCommandPlayer> playersubcommands = new ArrayList<>();
    private final ArrayList<SubCommandConsole> consolesubcommands = new ArrayList<>();

    // Register all commands
    public CommandManager(){
        playersubcommands.add(new UpdateCommand());
        playersubcommands.add(new VersionCommand());
        playersubcommands.add(new HelpCommand());
        playersubcommands.add(new ShowCreditsToCommand());
        playersubcommands.add(new PlayedTimeCommand());
        playersubcommands.add(new PlayerJoinsCommand());
        playersubcommands.add(new ReloadConfigCommand());
        playersubcommands.add(new SentMessagesCommand());
        playersubcommands.add(new SetJoinMessageCommand());
        playersubcommands.add(new SetLeaveMessageCommand());
        playersubcommands.add(new RemoveJoinMessageCommand());
        playersubcommands.add(new RemoveLeaveMessageCommand());
        playersubcommands.add(new ReloadMessagesCommand());

        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.HelpCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.PlayedTimeCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.PlayerJoinsCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.ReloadConfigCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.ReloadMessagesCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.RemoveJoinMessageCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.RemoveLeaveMessageCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.SentMessagesCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.SetJoinMessageCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.SetLeaveMessageCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.ShowCreditsToCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.UpdateCommand());
        consolesubcommands.add(new cz.thepatik.welcomeplugin.commands.subcommands.console.VersionCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        String command = cmd.toString();
        if (sender instanceof Player){
            // Run command if sender is a player

            Player p = (Player) sender;

            if (args.length > 0){
                // Run command

                for (int i = 0; i < getPlayerSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getPlayerSubcommands().get(i).getName())){
                        getPlayerSubcommands().get(i).perform(p, args);
                    }
                }
            } else if (args.length == 0){
                // Run when no subcommand

                TextComponent welcomeHelpMessage = new TextComponent("/welcome help");
                welcomeHelpMessage.setColor(ChatColor.RED);
                welcomeHelpMessage.setItalic(true);
                welcomeHelpMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/welcome help"));
                welcomeHelpMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click & run command").create()));

                TextComponent welcomeHelpMessageFirst = new TextComponent("You must specify a subcommand! For list of commands write ");
                welcomeHelpMessage.setColor(ChatColor.RED);
                welcomeHelpMessage.setBold(false);
               sender.spigot().sendMessage(welcomeHelpMessageFirst, welcomeHelpMessage);
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length > 0){
                for (int i = 0; i < getConsoleSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getConsoleSubcommands().get(i).getName())) {
                        getConsoleSubcommands().get(i).perform(sender, args);
                    }
                }
            } else if (args.length == 0) {
                Server server = sender.getServer();

                server.getLogger().warning("You must specify a subcommand! For list of commands write /welcome help");
            }
        }

        return true;

    }

    public ArrayList<SubCommandPlayer> getPlayerSubcommands(){
        return playersubcommands;
    }

    public ArrayList<SubCommandConsole> getConsoleSubcommands() {
        return consolesubcommands;
    }

    // Tab complete logic
    @Override
    public ArrayList<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args){
        ArrayList<String> completions = new ArrayList<>();

        if (sender instanceof Player p) {
            if (args.length == 1) {
                // Find subcommand
                for (int i = 0; i < getPlayerSubcommands().size(); i++) {
                    if (p.hasPermission(getPlayerSubcommands().get(i).getPermissions())) {
                        completions.add(getPlayerSubcommands().get(i).getName());
                    }
                }
            } else if (args.length >= 2) {
                // Find corresponding subcommand
                SubCommandPlayer subCommand = null;
                for (SubCommandPlayer sc : playersubcommands) {
                    if (sc.getName().equalsIgnoreCase(args[0])) {
                        subCommand = sc;
                        break;
                    }
                }
                if (subCommand != null) {
                    // Autocomplete suggestions for subcommand arguments
                    String[] subArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, subArgs, 0, subArgs.length);
                    completions.addAll(subCommand.tabComplete(p, subArgs));
                }
            }
        }

        // Filter and return autocomplete suggestions based on what the user has typed
        String currentArg = args[args.length - 1].toLowerCase();
        completions.removeIf(suggestion -> !suggestion.toLowerCase().startsWith(currentArg));

        return completions;
    }
}
