package cz.thepatik.welcomeplugin.utils;

import org.bukkit.plugin.java.JavaPlugin;

import javax.print.URIException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class Updater {
    private final JavaPlugin plugin;
    private int project = 0;
    private String newVersion;
    private URL checkURL;

    public Updater(JavaPlugin plugin, int projectID){
        this.plugin = plugin;
        this.project = projectID;
        try {
            this.checkURL = new URI("https://api.spigotmc.org/legacy/update.php?resource=" + projectID).toURL();
        }catch (Exception e){
            plugin.getLogger().severe("There was a problem with updater: " + e);
        }
    }

    public String getPluginVersion(){
        return plugin.getDescription().getVersion();
    }

    public int getProjectID(){
        return project;
    }

    public String getResourceURL(){
        return "https://www.spigotmc.org/resources/" + project;
    }

    public boolean checkForUpdates() {
        try {
            URLConnection con = checkURL.openConnection();
            this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        }catch (Exception e){
            plugin.getLogger().severe("Could not check for updates!" + e);
        }
        return plugin.getDescription().getVersion().equals(newVersion);
    }

    public String getNewVersion(){
        try {
            URLConnection con = checkURL.openConnection();
            this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        }catch (Exception e){
            plugin.getLogger().severe("Could not find new version!");
        }
        return newVersion;
    }
}
