package cz.thepatik.welcomeplugin.utils.listeners.utils;

import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {
    Functions functions = new Functions();

    // JOIN MESSAGES LOGIC
    public void sendJoinMessage(Player p){
        // Check if join messages are enabled
        if (functions.welcomePlugin().settingsSection.getBoolean("enable-join-messages")) {
            // Check if custom player join messages are enabled
            if (functions.welcomePlugin().settingsSection.getBoolean("enable-custom-join-messages")) {
                // Check if database SQLite
                if (functions.welcomePlugin().databaseType().equals("sqlite")) {
                    // Check if player has permission and has own join message
                    if (p.hasPermission("welcomeplugin.custom-message") && functions.sqLitePlayerFunctions().hasOwnJoinMessage(p)) {
                        Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&',
                                functions.sqLitePlayerFunctions().getPlayerJoinMessage(p))));
                    } else {
                        Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&',
                                functions.getMessagesHandler().getMessages("ingame-messages",
                                        "player-join-message"))));
                    }
                } else if (functions.welcomePlugin().databaseType().equals("mysql")) {
                    if (p.hasPermission("welcomeplugin.custom-message") && functions.mysqlPlayerFunctions().hasOwnJoinMessage(p)) {
                        Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p,ChatColor.translateAlternateColorCodes('&',
                                functions.mysqlPlayerFunctions().getPlayerJoinMessage(p))));
                    } else {
                        Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p,ChatColor.translateAlternateColorCodes('&',
                                functions.getMessagesHandler().getMessages("ingame-messages",
                                        "player-join-message"))));
                    }
                }
            } else {
                Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p,ChatColor.translateAlternateColorCodes('&',
                        functions.getMessagesHandler().getMessages("ingame-messages",
                                "player-join-message"))));
            }
        }
    }
    // LEAVE MESSAGES LOGIC
    public void sendLeaveMessage(Player p){
        // Check if join messages are enabled
        if (functions.welcomePlugin().settingsSection.getBoolean("enable-leave-messages")) {
            // Check if custom player join messages are enabled
            if (functions.welcomePlugin().settingsSection.getBoolean("enable-custom-leave-messages")) {
                // Check if database SQLite
                if (functions.welcomePlugin().databaseType().equals("sqlite")) {
                    // Check if player has permission and has own join message
                    if (p.hasPermission("welcomeplugin.custom-message") && functions.sqLitePlayerFunctions().hasOwnLeaveMessage(p)) {
                        Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p,ChatColor.translateAlternateColorCodes('&',
                                functions.sqLitePlayerFunctions().getPlayerLeaveMessage(p))));
                    } else {
                        Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p,ChatColor.translateAlternateColorCodes('&',
                                functions.getMessagesHandler().getMessages("ingame-messages",
                                        "player-leave-message"))));
                    }
                } else if (functions.welcomePlugin().databaseType().equals("mysql")) {
                    if (p.hasPermission("welcomeplugin.custom-message") && functions.mysqlPlayerFunctions().hasOwnLeaveMessage(p)) {
                        Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p,ChatColor.translateAlternateColorCodes('&',
                                functions.mysqlPlayerFunctions().getPlayerLeaveMessage(p))));
                    } else {
                        Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p,ChatColor.translateAlternateColorCodes('&',
                                functions.getMessagesHandler().getMessages("ingame-messages",
                                        "player-leave-message"))));
                    }
                }
            } else {
                Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(p,ChatColor.translateAlternateColorCodes('&',
                        functions.getMessagesHandler().getMessages("ingame-messages",
                                "player-leave-message"))));
            }
        }
    }
    // ADD MESSAGE TO DATABASE
    public void addMessageToDatabase(Player p){
        // Add message
        if (functions.welcomePlugin().settingsSection.getBoolean("enable-counters")) {
            if (functions.welcomePlugin().databaseType().equals("sqlite")) {
                functions.sqLitePlayerFunctions().addMessagesSent(p);
            } else if (functions.welcomePlugin().databaseType().equals("mysql")) {
                functions.sqLitePlayerFunctions().addMessagesSent(p);
            }
        }
    }
}
