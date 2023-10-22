package cz.thepatik.welcomeplugin.utils.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.tasks.PlayTimeTask;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static cz.thepatik.welcomeplugin.WelcomePlugin.database;


public class PlayerListener implements Listener {
    private HashMap<UUID, BukkitTask> tasks = new HashMap<>();
    private final WelcomePlugin plugin;

    public PlayerListener(WelcomePlugin plugin) {
        this.plugin = plugin;
    }

    public void displayCredits(Player player) {
        PacketContainer useBed = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
        useBed.getGameStateIDs().write(0, 4);
        useBed.getFloat().write(0, 1f);

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, useBed);
    }
    @EventHandler
    public void onResourcePackEvent(PlayerResourcePackStatusEvent statusEvent) {
        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");
        String configShowCredits = cs.getString("show-credits");

        Player p = statusEvent.getPlayer();

        if (statusEvent.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED){
            statusEvent.getPlayer().kickPlayer("You did not accept required resource pack");
        } else if (statusEvent.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            statusEvent.getPlayer().kickPlayer("Resource pack download failed!");
        } else if(statusEvent.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED){
            if (Objects.equals(configShowCredits, "newcomers")) {
                if (!p.hasPlayedBefore()) {
                    displayCredits(statusEvent.getPlayer());
                }
            } else if (Objects.equals(configShowCredits, "everyone")) {
                displayCredits(statusEvent.getPlayer());
            } else if (Objects.equals(configShowCredits, "nobody")) {
            }
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player p = event.getPlayer();

        //Writing player to database if not exists
        if (!database.playerExists(p)) {
            database.addPlayer(p);
            BukkitTask playerTimeTask = new PlayTimeTask(this, p.getPlayer()).runTaskTimer(plugin, 0, 20);
            tasks.put(p.getUniqueId(), playerTimeTask);
        }
        //If player exists in database add join counter
        if (database.playerExists(p)) {
            database.addPlayerJoin(p);
            BukkitTask playerTimeTask = new PlayTimeTask(this, p.getPlayer()).runTaskTimer(plugin, 0, 20);
            tasks.put(p.getUniqueId(), playerTimeTask);
        }

        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");

        String mainTitleMessage = plugin.getMessagesHandler().getMessages("ingame-messages","main-title-message");
        String subTitleMessage = plugin.getMessagesHandler().getMessages("ingame-messages","subtitle-message");
        String firstTimePlayerChatMessage = plugin.getMessagesHandler().getMessages("ingame-messages","first-time-player-chat-message");
        String welcomePlayerChatMessage = plugin.getMessagesHandler().getMessages("ingame-messages","welcome-player-chat-message");

        //Make PlaceholderAPI do its job
        mainTitleMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), mainTitleMessage);
        subTitleMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), subTitleMessage);

        if (cs.getBoolean("enable-titles")) {
            p.sendTitle(ChatColor.translateAlternateColorCodes
                    ('&', mainTitleMessage),
                    ChatColor.translateAlternateColorCodes
                            ('&', subTitleMessage), 20, 100, 20);
        }
        if (cs.getBoolean("enable-first-time-chat-message")){
            if (!p.hasPlayedBefore()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (p, firstTimePlayerChatMessage)));
            }
        }

        if (cs.getBoolean("enable-welcome-chat-message")){
            if (p.hasPlayedBefore()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes
                        ('&', PlaceholderAPI.setPlaceholders
                                (p, welcomePlayerChatMessage)));
            }
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        BukkitTask playerTimeTask = tasks.remove(p.getUniqueId());
        if (playerTimeTask != null) {
            playerTimeTask.cancel();
        }
    }

    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        database.addMessagesSent(player);

    }

}
