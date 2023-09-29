package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cz.thepatik.welcomeplugin.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static cz.thepatik.welcomeplugin.VersionCheck.*;
import static java.lang.Double.parseDouble;

public final class WelcomePlugin extends JavaPlugin {

    private static WelcomePlugin plugin;

    public static Logger logger;
    private ProtocolManager protocolManager;

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
            onDisable();
        } //Check PlaceholderAPI
        else if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            getLogger().warning("Disabling the plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            onDisable();
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

        //Register PlaceholderAPI
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        String pluginVersionConfig = pluginVersion + pluginVersionStage;

        //Set version in config
        if (!pluginVersionConfig.equals(this.getConfig().getString("plugin-version"))) {
            this.getConfig().set("plugin-version", pluginVersion + pluginVersionStage);
            this.saveConfig();
        }

        //Finally the plugin is loaded...
        getLogger().info("The plugin is loaded!");
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("The plugin was successfully unloaded!");
    }

    public static WelcomePlugin getPlugin(){
        return plugin;
    }

}
