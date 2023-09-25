package cz.thepatik.welcomeplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class WelcomePlugin extends JavaPlugin {

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
        if (!fileExists(configFile())) {
            getLogger().info("Creating config file...");
            saveDefaultConfig();
            if (fileExists(configFile())) {
                getLogger().info("The config file successfully created!");
                getLogger().info("The plugin is installed!");
            } else {
                getLogger().warning("There was a problem with creation of config file!");
            }
        } else {

            getLogger().info("The plugin is loaded!");
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
