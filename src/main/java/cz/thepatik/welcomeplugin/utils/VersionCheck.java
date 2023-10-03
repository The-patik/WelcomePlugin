package cz.thepatik.welcomeplugin.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionCheck {

    String pluginVersion;

    JavaPlugin plugin;

    public VersionCheck(JavaPlugin plugin){
        this.plugin = plugin;
        this.pluginVersion = plugin.getDescription().getVersion();
    }

    public String getPluginVersion(){
        return pluginVersion;
    }

    public  String getCurrentOnlineVersionString() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        URL url = new URL("https://raw.githubusercontent.com/The-patik/WelcomePlugin/master/pluginVersion.number");

        try (InputStream in = url.openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    public  String getCurrentOnlineVersion(){
            try {
                return getCurrentOnlineVersionString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
