package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cz.thepatik.welcomeplugin.commands.CommandManager;
import cz.thepatik.welcomeplugin.database.MySQLDatabase;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import cz.thepatik.welcomeplugin.utils.Functions;
import cz.thepatik.welcomeplugin.utils.handlers.MessagesHandler;
import cz.thepatik.welcomeplugin.utils.handlers.PlaceholdersHandler;
import cz.thepatik.welcomeplugin.utils.Updater;
import cz.thepatik.welcomeplugin.utils.handlers.ReloadHandler;
import cz.thepatik.welcomeplugin.utils.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;

public final class WelcomePlugin extends JavaPlugin {
    private static WelcomePlugin plugin;
    Functions functions = new Functions();
    public SQLiteDatabase sqLiteDatabase;
    public MySQLDatabase mySQLDatabase;
    public ConfigurationSection settingsSection = getConfig().getConfigurationSection("settings");

    @Override
    public void onEnable() {

        plugin = this;

        MessagesHandler messagesHandler = new MessagesHandler(this);

        File config = new File(getDataFolder() + "/config.yml");
        File messages = new File(getDataFolder() + "/messages.yml");

        // Plugin startup logic

        // Register messages.yml and config.yml if not exists
        if (!config.exists()) {
            saveDefaultConfig();
        }
        if (!messages.exists()) {
            new MessagesHandler(this);
        }
        // Initialize Updater
        Updater updater = new Updater(this, 112870);

        // Check if data folder exists
        File dataFolder = new File(getDataFolder() + "/data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        // Load database
        if (databaseType().equals("sqlite")) {
            try {
                sqLiteDatabase = new SQLiteDatabase(getDataFolder().getAbsolutePath() + "/data/database.db");
            } catch (SQLException e) {
                getLogger().severe("Connection to SQLite database failed!" + e);
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }
        if (databaseType().equals("mysql")){
            try {
                mySQLDatabase = functions.mySQLDatabase();
                mySQLDatabase.getConnection();
                mySQLDatabase.initializeDatabase();
            } catch (SQLException e){
                getLogger().severe("Connection to MySQL database failed!" + e);
            }
        }

        // Check ProtocolLibs
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
            // Check version
            if (updater.checkForUpdates()) {
                getLogger().info("The plugin is up to date!");
            } else {
                getLogger().warning("There is a new version! Check Spigot! https://www.spigotmc.org/resources/" + updater.getProjectID());
                getLogger().info("Plugin version: " + updater.getPluginVersion());
                getLogger().info("Updated version: " + updater.getNewVersion());
            }

            // Register commands
            getCommand("welcome").setExecutor(new CommandManager());

            // Register ProtocolLib
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

            // Register Placeholders
            new PlaceholdersHandler(this).register();

            // Register Handlers
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
            Bukkit.getPluginManager().registerEvents(new ReloadHandler(), this);

            String timeStamp = new SimpleDateFormat("HH-mm").format(Calendar.getInstance().getTime());
            File version = new File(getDataFolder(), "/data/version");
            if (!version.exists()){
                try {
                    version.createNewFile();
                    FileWriter writer = new FileWriter(version);
                    writer.write(getUpdater().getPluginVersion());
                    writer.close();
                } catch (IOException e) {
                    getLogger().severe("Could not create version!");
                    e.printStackTrace();
                }
            }
            String pluginVersion = "";
            try {
                Scanner sc = new Scanner(version);
                 pluginVersion = sc.nextLine();
                 sc.close();
            } catch (FileNotFoundException e) {
                getLogger().severe("Could not check plugin version!");
                e.printStackTrace();
            }

            //Set new version if is
            if (!getUpdater().getPluginVersion().equals(pluginVersion)) {

                // Write version in version file
                try {
                    FileWriter writer = new FileWriter(version);
                    writer.write(getUpdater().getPluginVersion());
                    writer.close();
                } catch (IOException e) {
                    getLogger().severe("Could not change plugin version!");
                    e.printStackTrace();
                }

                // Create backup folder
                File backup = new File(getDataFolder() + "/backup");
                if (!backup.exists()) {
                    backup.mkdirs();
                }

                // Backup messages
                File messagesCopy = new File(backup, "/messages-backup-"+ timeStamp + ".yml");
                try {
                    Files.copy(messages.toPath(), messagesCopy.toPath());
                    Files.delete(messages.toPath());
                    new MessagesHandler(this);
                } catch (Exception e){
                    getLogger().severe("Could not create backup of messages.yml");
                    e.printStackTrace();
                }

                // Backup messages
                File configCopy = new File(backup, "/config-backup-"+ timeStamp + ".yml");
                try {
                    Files.copy(config.toPath(), configCopy.toPath());
                    Files.delete(config.toPath());
                    saveDefaultConfig();
                } catch (Exception e){
                    getLogger().severe("Could not create backup of config.yml");
                    e.printStackTrace();
                }
            }
            // Check missingColumns after update
            if (databaseType().equals("sqlite")) {
                sqLiteDatabase.checkMissingColumns();
            }
            if (databaseType().equals("mysql")) {
                mySQLDatabase.checkMissingColumns();
            }
            // Finally the plugin is loaded...
            getLogger().info("The plugin is loaded!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("The plugin was successfully unloaded!");

        if (databaseType().equals("sqlite")) {
            try {
                sqLiteDatabase.closeConnection();
            } catch (Exception e) {
                getLogger().severe("Database connection was closed...");
            }
        } else if (databaseType().equals("mysql")){
            try{
                mySQLDatabase.closeConnection();
            }catch (Exception e) {
                getLogger().severe("Database connection was closed...");
            }
        }
        getLogger().info("Database connection was closed...");
    }
    public static WelcomePlugin getPlugin(){
        return plugin;
    }
    public Updater getUpdater(){
        return new Updater(this, 112870);
    }
    public String databaseType(){
        return settingsSection.getString("database-type");
    }

}
