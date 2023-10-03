package cz.thepatik.welcomeplugin.utils;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static cz.thepatik.welcomeplugin.utils.placeholder.PlayedTime.getPlayedTime;
import static cz.thepatik.welcomeplugin.utils.placeholder.PlayerJoins.getElsePlayerJoins;
import static cz.thepatik.welcomeplugin.utils.placeholder.PlayerJoins.getPlayerJoins;

public class Placeholders extends PlaceholderExpansion {
    private final WelcomePlugin plugin;
    public Placeholders(WelcomePlugin plugin){
        this.plugin = plugin;
    }
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
        return plugin.getVersionCheck().getPluginVersion();
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
            if (identifier.equals("played_time")){
                return getPlayedTime(player, plugin);
            } else if (identifier.equals("player_joins")) {
                return String.valueOf(getPlayerJoins(player));
            } else if (identifier.startsWith("player_") && identifier.endsWith("_joins")){
                String playerName = identifier.substring("player_".length(), identifier.length() - "_joins".length());
                return String.valueOf(getElsePlayerJoins(playerName));
            } else {
                player.sendMessage(ChatColor.RED + "not valid placeholder");
            }
            return null;
        }
}
