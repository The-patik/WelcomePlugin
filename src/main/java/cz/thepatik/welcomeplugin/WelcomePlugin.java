package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cz.thepatik.welcomeplugin.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

import static cz.thepatik.welcomeplugin.VersionCheck.*;
import static java.lang.Double.parseDouble;

public final class WelcomePlugin extends JavaPlugin {
    public static Logger logger;
    private ProtocolManager protocolManager;

    public File configFile() {
        return new File(getDataFolder(), "config.yml");
    }

    public boolean fileExists(File filepath) {
        File file = new File(String.valueOf(filepath));
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        //Check ProtocolLibs
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger().warning("This plugin needs ProtocolLib in order to work!");
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
}
