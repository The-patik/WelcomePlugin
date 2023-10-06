package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cz.thepatik.welcomeplugin.commands.CommandManager;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import cz.thepatik.welcomeplugin.utils.handlers.PlaceholdersHandler;
import cz.thepatik.welcomeplugin.utils.Updater;
import cz.thepatik.welcomeplugin.utils.handlers.MessagesHandler;
import cz.thepatik.welcomeplugin.utils.handlers.ReloadHandler;
import cz.thepatik.welcomeplugin.utils.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class WelcomePlugin extends JavaPlugin {
    private static WelcomePlugin plugin;
    public static SQLiteDatabase database;
    private MessagesHandler messagesHandler;

    @Override
    public void onEnable() {

        plugin = this;

        // Plugin startup logic
        this.saveDefaultConfig();

        // Initialize MessageHandler
        messagesHandler = new MessagesHandler(this);

        // Initialize Updater
        Updater updater = new Updater(this, 112870);

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
            if (updater.checkForUpdates()) {
                getLogger().info("The plugin is up to date!");
            } else {
                getLogger().warning("There is a new version! Check Spigot! https://www.spigotmc.org/resources/" + updater.getProjectID());
                getLogger().info("Plugin version: " + updater.getPluginVersion());
                getLogger().info("Updated version: " + updater.getNewVersion());
            }

            //Register commands
            getCommand("welcome").setExecutor(new CommandManager());

            //Register ProtocolLib
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

            // Register Placeholders
            new PlaceholdersHandler(this).register();

            //Register Handlers
            Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
            Bukkit.getPluginManager().registerEvents(new ReloadHandler(), this);

            String currentPluginVersion = getConfig().getString("plugin-version");

            //Set version in config
            if (!getUpdater().getPluginVersion().equals(this.getConfig().getString("plugin-version"))) {
                getLogger().info("Plugin has been updated from " + currentPluginVersion + " to new version: " + getUpdater().getPluginVersion());
                this.getConfig().set("plugin-version", getUpdater().getPluginVersion());
                this.saveConfig();
            }

            //Check if data folder exists
            File dataFolder = new File(getDataFolder() + "/data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            //Load database
            try {
                database = new SQLiteDatabase(getDataFolder().getAbsolutePath() + "/data/database.db");
            } catch (SQLException e) {
                getLogger().severe("Connection to database failed!" + e);
                Bukkit.getPluginManager().disablePlugin(this);
            }

            // Check missingColumns after update
            database.checkMissingColumns();

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
        }catch (Exception e){
            getLogger().severe("Database connection was closed...");
        }
        getLogger().info("Database connection was closed...");
    }

    public static WelcomePlugin getPlugin(){
        return plugin;
    }

    public MessagesHandler getMessagesHandler(){
        return messagesHandler;
    }

    public Updater getUpdater(){
        return new Updater(this, 112870);
    }

}
