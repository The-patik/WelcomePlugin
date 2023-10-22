package cz.thepatik.welcomeplugin.utils.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.tasks.PlayTimeTask;
import cz.thepatik.welcomeplugin.utils.Functions;
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

public class PlayerListener implements Listener {
    private final HashMap<UUID, BukkitTask> tasks = new HashMap<>();

    Functions functions = new Functions();
    WelcomePlugin plugin = functions.welcomePlugin();

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

        if (statusEvent.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            // Check for settings if kick player enabled and kick player when resource pack declined
            if (plugin.settingsSection.getBoolean("check-for-resource-pack")) {
                statusEvent.getPlayer().kickPlayer("You did not accept required resource pack");
            }
        } else if (statusEvent.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            // Check for settings if kick player enabled and kick player when failed resource pack download
            if (plugin.settingsSection.getBoolean("check-for-resource-pack")) {
                statusEvent.getPlayer().kickPlayer("Resource pack download failed!");
            }
        } else if (statusEvent.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
            if (Objects.equals(configShowCredits, "newcomers")) {
                if (!p.hasPlayedBefore()) {
                    displayCredits(statusEvent.getPlayer());
                }
            } else if (Objects.equals(configShowCredits, "everyone")) {
                displayCredits(statusEvent.getPlayer());
            } else {
                plugin.getLogger().severe("You set wrong setting in show-credits! Check it!");
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player p = event.getPlayer();

        // Check if counters are enabled
        if (plugin.settingsSection.getBoolean("enable-counters")) {

            // Check if SQLite database
            if (plugin.databaseType().equals("sqlite")) {

                // Writing player to database if not exists
                if (!(functions.sqLitePlayerFunctions().playerExists(p))) {
                    functions.sqLitePlayerFunctions().addPlayer(p);
                    functions.sqLitePlayerFunctions().addPlayerJoin(p);
                    BukkitTask playerTimeTask = new PlayTimeTask(this, p.getPlayer()).runTaskTimer(plugin, 0, 20);
                    tasks.put(p.getUniqueId(), playerTimeTask);
                }
                // If player exists in database add join counter
                if (functions.sqLitePlayerFunctions().playerExists(p)) {
                    functions.sqLitePlayerFunctions().addPlayerJoin(p);
                    BukkitTask playerTimeTask = new PlayTimeTask(this, p.getPlayer()).runTaskTimer(plugin, 0, 20);
                    tasks.put(p.getUniqueId(), playerTimeTask);
                }
                // Check if database MySQL
            } else if (plugin.databaseType().equals("mysql")) {

                // Writing player to database if not exists
                if (!(functions.mysqlPlayerFunctions().playerExists(p))) {
                    functions.mysqlPlayerFunctions().addPlayer(p);
                    functions.mysqlPlayerFunctions().addPlayerJoin(p);
                    BukkitTask playerTimeTask = new PlayTimeTask(this, p.getPlayer()).runTaskTimer(plugin, 0, 20);
                    tasks.put(p.getUniqueId(), playerTimeTask);
                }
                // If player exists in database add join counter
                if (functions.mysqlPlayerFunctions().playerExists(p)) {
                    functions.mysqlPlayerFunctions().addPlayerJoin(p);
                    BukkitTask playerTimeTask = new PlayTimeTask(this, p.getPlayer()).runTaskTimer(plugin, 0, 20);
                    tasks.put(p.getUniqueId(), playerTimeTask);
                }
            }
        }

        // Send join message
        event.setJoinMessage(functions.getMessageUtils().sendJoinMessage(p));

        // Send welcome title
        functions.getTitleUtils().sendWelcomeTitle(p);

    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        // Stop counting played time on player disconnect
        if (plugin.settingsSection.getBoolean("enable-counters")) {
            BukkitTask playerTimeTask = tasks.remove(p.getUniqueId());
            if (playerTimeTask != null) {
                playerTimeTask.cancel();
            }
        }
        // Send leave message
        event.setQuitMessage(functions.getMessageUtils().sendLeaveMessage(p));;
    }

    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();

        // Add new message to db
        functions.getMessageUtils().addMessageToDatabase(p);
    }

}
