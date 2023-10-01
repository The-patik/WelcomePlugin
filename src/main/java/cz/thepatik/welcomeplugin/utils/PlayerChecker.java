package cz.thepatik.welcomeplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class PlayerChecker {
    public static boolean isPlayerOnline(String playerName){
        Player player = Bukkit.getPlayer(playerName);
        return player != null && player.isOnline();
    }
}
