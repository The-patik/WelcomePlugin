package cz.thepatik.welcomeplugin.utils.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.tasks.PlayTimeTask;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static cz.thepatik.welcomeplugin.WelcomePlugin.database;


public class PlayerHandler implements Listener {
    private HashMap<UUID, BukkitTask> tasks = new HashMap<>();
    private final WelcomePlugin plugin;

    public PlayerHandler(WelcomePlugin plugin) {
        this.plugin = plugin;
    }

    public void displayCredits(Player player) throws InvocationTargetException {
        PacketContainer useBed = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
        useBed.getGameStateIDs().write(0, 4);
        useBed.getFloat().write(0, 1f);

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, useBed);
    }
    @EventHandler
    public void onResourcePackEvent(PlayerResourcePackStatusEvent statusEvent) throws InterruptedException, InvocationTargetException {
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
    public void onPlayerJoin(PlayerJoinEvent event) throws InvocationTargetException, SQLException, InterruptedException {
        Player p = event.getPlayer();

        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");

        String mainTitleMessage = cs.getString("main-title-message");
        String subTitleMessage = cs.getString("subtitle-message");

        //Make PlaceholderAPI do its job
        mainTitleMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), mainTitleMessage);
        subTitleMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), subTitleMessage);

        if (cs.getBoolean("enable-titles")) {
            p.sendTitle(mainTitleMessage, subTitleMessage, 20, 100, 20);
        }

        //Writing player to database if not exists
        if (!(database.playerExists(p))) {
            database.addPlayer(p);
        }
        //If player exists in database add join counter
        if (database.playerExists(p)) {
            database.addPlayerJoin(p);
            BukkitTask playerTimeTask = new PlayTimeTask(this, p.getPlayer()).runTaskTimer(plugin, 0, 20);
            tasks.put(p.getUniqueId(), playerTimeTask);
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
}
