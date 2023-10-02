package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cz.thepatik.welcomeplugin.commands.CommandManager;
import cz.thepatik.welcomeplugin.database.Database;
import cz.thepatik.welcomeplugin.utils.Placeholders;
import cz.thepatik.welcomeplugin.utils.handlers.PlayerHandler;
import cz.thepatik.welcomeplugin.utils.handlers.ReloadHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

import static cz.thepatik.welcomeplugin.utils.VersionCheck.*;
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
        } else {
            //Check version
            if (pluginVersion == parseDouble(getCurrentOnlineVersion())) {
                getLogger().info("The plugin is up to date!");
            } else {
                getLogger().warning("There is a new version! Check GitHub!");
                getLogger().info("Plugin version: " + pluginVersion + pluginVersionStage);
                getLogger().info("Updated version: " + getCurrentOnlineVersion() + pluginVersionStage);
            }

            //Register commands
            getCommand("welcome").setExecutor(new CommandManager());

            //Register ProtocolLib
            protocolManager = ProtocolLibrary.getProtocolManager();

            // Register Placeholders
            new Placeholders(this).register();

            //Register Handlers
            Bukkit.getPluginManager().registerEvents(new PlayerHandler(this), this);
            Bukkit.getPluginManager().registerEvents(new ReloadHandler(), this);

            String pluginVersionConfig = pluginVersion + pluginVersionStage;

            //Set version in config
            if (!pluginVersionConfig.equals(this.getConfig().getString("plugin-version"))) {
                this.getConfig().set("plugin-version", pluginVersion + pluginVersionStage);
                this.saveConfig();
            }

            //Check if data folder exists
            File dataFolder = new File(getDataFolder() + "/data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            //Load database
            try {
                database = new Database(getDataFolder().getAbsolutePath() + "/data/database.db");
            } catch (SQLException e) {
                System.out.println("Connection to database failed!");
                Bukkit.getPluginManager().disablePlugin(this);
                throw new RuntimeException(e);
            }

            //Finally the plugin is loaded...
            getLogger().info("The plugin is loaded!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("The plugin was successfully unloaded!");

        try {
            database.closeConnection();
        } catch (SQLException e){
            System.out.println("Error when closing database connection");
        }
    }

    public static WelcomePlugin getPlugin(){
        return plugin;
    }

}
