package cz.thepatik.welcomeplugin.utils.listeners.utils;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.utils.Functions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class TitleUtils {
    Functions functions = new Functions();
    WelcomePlugin plugin = functions.welcomePlugin();
    public void sendWelcomeTitle(Player p){
        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");

        String mainTitleMessage = functions.getMessagesHandler()
                .getMessages("ingame-messages", "main-title-message");
        String subTitleMessage = functions.getMessagesHandler()
                .getMessages("ingame-messages", "subtitle-message");
        String firstTimePlayerChatMessage = functions.getMessagesHandler()
        .getMessages("ingame-messages", "first-time-player-chat-message");
        String welcomePlayerChatMessage = functions.getMessagesHandler()
                .getMessages("ingame-messages", "welcome-player-chat-message");

        //Make PlaceholderAPI do its job
        mainTitleMessage = PlaceholderAPI.setPlaceholders(p, mainTitleMessage);
        subTitleMessage = PlaceholderAPI.setPlaceholders(p, subTitleMessage);

        if (cs.getBoolean("enable-titles")) {
            p.sendTitle(ChatColor.translateAlternateColorCodes
                            ('&', mainTitleMessage),
                    ChatColor.translateAlternateColorCodes
                            ('&', subTitleMessage), 20, 100, 20);
        }
        if (cs.getBoolean("enable-first-time-chat-message")) {
            if (!p.hasPlayedBefore()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (p, firstTimePlayerChatMessage)));
            }
        }

        if (cs.getBoolean("enable-welcome-chat-message")) {
            if (p.hasPlayedBefore()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (p, welcomePlayerChatMessage)));
            }
        }
    }
}
