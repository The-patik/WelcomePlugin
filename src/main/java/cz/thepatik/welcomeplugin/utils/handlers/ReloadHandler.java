package cz.thepatik.welcomeplugin.utils.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class ReloadHandler implements Listener {

    @EventHandler
    public void onServerCommand(ServerCommandEvent event){
        if (event.getCommand().equalsIgnoreCase("reload")){
            for (Player player : Bukkit.getOnlinePlayers()){
                player.kickPlayer("Server reload, please rejoin");
            }
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event){
        String message = event.getMessage().toLowerCase();

        if (message.startsWith("/reload")){
            event.setCancelled(true);

            for (Player player : Bukkit.getOnlinePlayers()){
                player.kickPlayer("Server reload, please rejoin");
            }

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "reload");

        }

    }

}
