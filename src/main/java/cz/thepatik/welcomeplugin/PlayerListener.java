package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.injector.BukkitUnwrapper;
import com.comphenix.protocol.reflect.FuzzyReflection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.InvocationTargetException;


public class PlayerListener implements Listener {

    public void displayCredits(Player player) throws IllegalArgumentException, InvocationTargetException, IllegalAccessException {
        BukkitUnwrapper unwrapper = new BukkitUnwrapper();
        Object nms = unwrapper.unwrapItem(player);
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);

        packet.getIntegers().
                write(0, 4).
                write(1, 0);
        FuzzyReflection.fromObject(nms).getFieldByName("viewingCredits").set(nms, true);
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws InvocationTargetException, IllegalAccessException {
        if (event.getPlayer().hasPlayedBefore()) {
            displayCredits(event.getPlayer());
        }
    }

}
