package cz.thepatik.welcomeplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static cz.thepatik.welcomeplugin.VersionCheck.pluginVersion;
import static cz.thepatik.welcomeplugin.VersionCheck.versionCheck;

public final class WelcomePlugin extends JavaPlugin {

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
        //Check if config file exists
        if (!fileExists(configFile())) {
            getLogger().info("Creating config file...");
            this.saveDefaultConfig();
            if (fileExists(configFile())) {
                getLogger().info("The config file successfully created!");
                getLogger().info("The plugin is installed!");
            } else {
                getLogger().warning("There was a problem with creation of config file!");
            }
        }
        //If config exists...
          else {
            //Check ProtocolLibs
            if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
                getLogger().warning("This plugin needs ProtocolLib in order to work!");
                getLogger().warning("Disabling the plugin...");
                Bukkit.getPluginManager().disablePlugin(this);
                onDisable();
            }
            //Check version
            if (versionCheck()) {
                getLogger().warning("There is a new version! Check GitHub!");
            } else {
                getLogger().info("The plugin is up to date!");
            }
            protocolManager = ProtocolLibrary.getProtocolManager();
            this.getConfig().set("pluginVersion", pluginVersion);
            this.saveConfig();
            //Finally the plugin is loaded...
            getLogger().info("The plugin is loaded!");
            getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("The plugin was successfully unloaded!");
    }
}
