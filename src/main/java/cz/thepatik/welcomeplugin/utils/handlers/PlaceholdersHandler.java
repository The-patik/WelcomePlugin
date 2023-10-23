package cz.thepatik.welcomeplugin.utils.handlers;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.utils.Functions;
import cz.thepatik.welcomeplugin.utils.placeholders.PlayedTime;
import cz.thepatik.welcomeplugin.utils.placeholders.PlayerJoins;
import cz.thepatik.welcomeplugin.utils.placeholders.SentMessages;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholdersHandler extends PlaceholderExpansion {
    Functions functions = new Functions();
    WelcomePlugin plugin = functions.welcomePlugin();
    @Override
    public @NotNull String getIdentifier() {
        return "WelcomePlugin";
    }

    @Override
    public @NotNull String getAuthor() {
        return "The-patik";
    }

    @Override
    public @NotNull String getVersion() {
        return functions.getUpdater().getPluginVersion();
    }
    @Override
    public boolean persist(){
        return true;
    }
    @Override
    public boolean canRegister(){
        return true;
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        PlayerJoins playerJoins = new PlayerJoins(plugin);
        SentMessages sentMessages = new SentMessages(plugin);
        PlayedTime playedTime = new PlayedTime();

            if (identifier.equals("played_time")){
                return playedTime.getPlayedTime(player, plugin);
            } else if (identifier.equals("player_joins")) {
                return String.valueOf(playerJoins.getPlayerJoins(player));
            } else if (identifier.startsWith("player_") && identifier.endsWith("_joins")){
                String playerName = identifier.substring("player_".length(), identifier.length() - "_joins".length());
                return String.valueOf(playerJoins.getElsePlayerJoins(playerName));
            } else if (identifier.equals("sent_messages")){
                return String.valueOf(sentMessages.getMessagesSent(player));
            } else {
                player.sendMessage(ChatColor.RED + "not valid placeholder");
            }
            return null;
        }
}
