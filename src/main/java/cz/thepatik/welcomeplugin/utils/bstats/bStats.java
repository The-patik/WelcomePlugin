package cz.thepatik.welcomeplugin.utils.bstats;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import org.bstats.bukkit.*;

public class bStats {

    private final int bstatsID;

    private final WelcomePlugin welcomePlugin;

    public bStats(WelcomePlugin welcomePlugin, int bstatsID){
        this.bstatsID = bstatsID;
        this.welcomePlugin = welcomePlugin;
    }

    public void load(){
        Metrics metrics = new Metrics(welcomePlugin, bstatsID);
        welcomePlugin.getLogger().info("Stats loaded successfully!");
    }



}
