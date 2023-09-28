package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.InvocationTargetException;


public class PlayerListener implements Listener {

    public void displayCredits(Player player) throws InvocationTargetException {
        PacketContainer useBed = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
        useBed.getGameStateIDs().write(0, 4);
        useBed.getFloat().write(0, 1f);

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, useBed);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws InvocationTargetException {
        if (!event.getPlayer().hasPlayedBefore()) {
            displayCredits(event.getPlayer());
        } else {
            // There will be action bar or screen message
        }
    }

}
