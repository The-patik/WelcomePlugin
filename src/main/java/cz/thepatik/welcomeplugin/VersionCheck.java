package cz.thepatik.welcomeplugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class VersionCheck {

    public static String pluginVersion = "0.2-alpha";

    public static boolean versionCheck() {
        if (pluginVersion == getCurrentOnlineVersion) {
            return true;
        } else {
            return false;
        }
    }

    private static String getCurrentOnlineVersionString() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        URL url = new URL("https://raw.githubusercontent.com/The-patik/WelcomePlugin/master/pluginVersion.txt");

        InputStream in = url.openStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } finally {
            in.close();
        }

        return sb.toString();
    }

    private static String getCurrentOnlineVersion;

    static {
        try {
            getCurrentOnlineVersion = getCurrentOnlineVersionString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
