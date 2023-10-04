package cz.thepatik.welcomeplugin.utils.handlers;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MessagesHandler {

    private YamlConfiguration messagesConfig;


    public MessagesHandler(JavaPlugin plugin) {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()){
            plugin.saveResource("messages.yml", false);
            plugin.getLogger().info("File messages.yml successfully created!");
        }
        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getMessages(String section, String key){
        if (messagesConfig.contains(section + "." + key)){
            if (messagesConfig.getString(section + "." + key).contains("'")){
                return messagesConfig.getString(section + "." + key).replace("'", "");
            }
            return messagesConfig.getString(section + "." + key);
        }
        return "Message not found!";
    }

    public void saveMessagesConfig(JavaPlugin plugin){
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        try {
            messagesConfig.save(messagesFile);
        }catch (Exception e){
            plugin.getLogger().severe("Can not save messages.yml: " + e);
        }
    }

}
