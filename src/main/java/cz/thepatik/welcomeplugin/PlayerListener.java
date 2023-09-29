package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;


public class PlayerListener implements Listener {

   private final WelcomePlugin plugin;

   public PlayerListener(WelcomePlugin plugin){
       this.plugin = plugin;
   }


    public void displayCredits(Player player) throws InvocationTargetException {
        PacketContainer useBed = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
        useBed.getGameStateIDs().write(0, 4);
        useBed.getFloat().write(0, 1f);

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, useBed);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws InvocationTargetException {
        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");

        String configShowCredits = cs.getString("show-credits");
        String mainTitleMessage = cs.getString("main-title-message");
        String subTitleMessage = cs.getString("subtitle-message");

        //Make PlaceholderAPI do its job
        mainTitleMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), mainTitleMessage);
        subTitleMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), subTitleMessage);

        if (Objects.equals(configShowCredits, "newcomers")) {
            if (!event.getPlayer().hasPlayedBefore()) {
                displayCredits(event.getPlayer());
            }
        } else if (Objects.equals(configShowCredits, "everyone")) {
            displayCredits(event.getPlayer());
        }

        if (cs.getBoolean("enable-titles")) {
            event.getPlayer().sendTitle(mainTitleMessage, subTitleMessage, 20, 100, 20);
        }

    }

}
