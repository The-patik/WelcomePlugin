package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import cz.thepatik.welcomeplugin.tasks.PlayTimeTask;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;
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


    public void displayCredits(Player player) throws InvocationTargetException {
        PacketContainer useBed = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
        useBed.getGameStateIDs().write(0, 4);
        useBed.getFloat().write(0, 1f);

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, useBed);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws InvocationTargetException, SQLException {
        Player p = event.getPlayer();

        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");

        String configShowCredits = cs.getString("show-credits");
        String mainTitleMessage = cs.getString("main-title-message");
        String subTitleMessage = cs.getString("subtitle-message");

        //Make PlaceholderAPI do its job
        mainTitleMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), mainTitleMessage);
        subTitleMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), subTitleMessage);

        if (Objects.equals(configShowCredits, "newcomers")) {
            if (!p.hasPlayedBefore()) {
                displayCredits(event.getPlayer());
            }
        } else if (Objects.equals(configShowCredits, "everyone")) {
            displayCredits(event.getPlayer());
        }

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
