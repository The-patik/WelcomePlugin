package cz.thepatik.welcomeplugin.utils.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import cz.thepatik.welcomeplugin.WelcomePlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public void setMessage(String section, String value) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        if (messagesConfig.contains(section)) {
            try {
                    ObjectNode root = (ObjectNode) mapper.readTree(new File (WelcomePlugin.getPlugin()
                            .getDataFolder(), "messages.yml"));
                    root.put(section, value);
                    mapper.writer().writeValue(new File(WelcomePlugin.getPlugin()
                            .getDataFolder(), "messages.yml"), root);
                    saveConfig(WelcomePlugin.getPlugin());
            } catch (IOException e) {
                WelcomePlugin.getPlugin().getLogger().severe("Could not set messages.yml plugin version!");
                throw new RuntimeException(e);
            }
        }
    }

    public void reloadConfig(JavaPlugin plugin){
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        try {
            saveConfig(plugin);
            messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        }catch (Exception e){
            plugin.getLogger().severe("Can not reload messages.yml: " + e);
        }
    }
    public void saveConfig(JavaPlugin plugin){
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        try {
            messagesConfig.save(messagesFile);
        }catch (Exception e){
            plugin.getLogger().severe("Can not save messages.yml: " + e);
        }
    }

}
