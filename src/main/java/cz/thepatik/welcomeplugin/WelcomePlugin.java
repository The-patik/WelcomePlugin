package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cz.thepatik.welcomeplugin.commands.CommandManager;
import cz.thepatik.welcomeplugin.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

import static cz.thepatik.welcomeplugin.VersionCheck.*;
import static java.lang.Double.parseDouble;

public final class WelcomePlugin extends JavaPlugin {

    private static WelcomePlugin plugin;

    public static Logger logger;
    private ProtocolManager protocolManager;

    public static Database database;

    @Override
    public void onEnable() {

        plugin = this;

        // Plugin startup logic
        this.saveDefaultConfig();

        //Check ProtocolLibs
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger().warning("This plugin needs ProtocolLib in order to work!");
            getLogger().warning("Disabling the plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
        } //Check PlaceholderAPI
        else if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            getLogger().warning("Disabling the plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        //Check version
        if (pluginVersion == parseDouble(getCurrentOnlineVersion())) {
            getLogger().info("The plugin is up to date!");
        } else {
            getLogger().warning("There is a new version! Check GitHub!");
            getLogger().info("Plugin version: " + pluginVersion + pluginVersionStage);
            getLogger().info("Updated version: " + getCurrentOnlineVersion()+ pluginVersionStage);
        }
        //Register commands
        getCommand("welcome").setExecutor(new CommandManager());

        //Register ProtocolLib
        protocolManager = ProtocolLibrary.getProtocolManager();

        //Register PlayerListener
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        String pluginVersionConfig = pluginVersion + pluginVersionStage;

        //Set version in config
        if (!pluginVersionConfig.equals(this.getConfig().getString("plugin-version"))) {
            this.getConfig().set("plugin-version", pluginVersion + pluginVersionStage);
            this.saveConfig();
        }

        //Check if data folder exists
        File dataFolder = new File(getDataFolder() + "/data");
        if (!dataFolder.exists()){
            dataFolder.mkdirs();
        }

        //Load database
        try {
            database = new Database(getDataFolder().getAbsolutePath() + "/data/database.db");
        } catch (SQLException e) {
            System.out.println("Connection to database failed");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }

        //Finally the plugin is loaded...
        getLogger().info("The plugin is loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("The plugin was successfully unloaded!");

        try {
            database.closeConnection();
        } catch (SQLException e){
            System.out.println("Just error...");
        }
    }

    public static WelcomePlugin getPlugin(){
        return plugin;
    }

}
