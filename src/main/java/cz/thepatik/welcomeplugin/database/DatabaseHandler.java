package cz.thepatik.welcomeplugin.database;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.utils.Functions;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.*;

public class DatabaseHandler {
    Functions functions = new Functions();
    WelcomePlugin plugin = functions.welcomePlugin();
    ConfigurationSection cs = null;

    private ConfigurationSection getConfigSection(){
        if (cs == null) {
            try {
                cs = plugin.getConfig().getConfigurationSection("mysql-settings");
            } catch (NullPointerException e) {
                plugin.getLogger().severe("Error while getting config!" + e);
            }
        }
        return cs;
    }

    public Connection connection;
    private final String host = getConfigSection().getString("db-host");
    private final String port = getConfigSection().getString("db-port");
    private final String database = getConfigSection().getString("db-name");
    private final String username = getConfigSection().getString("db-username");
    private final String passwd = getConfigSection().getString("db-password");

    public Connection getConnection(){
        if (connection == null){
            if (plugin.settingsSection.getString("database-type").equals("mysql")) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, passwd);
                } catch (ClassNotFoundException | SQLException e) {
                    plugin.getLogger().severe("There was an error while connecting to mysql database!" + e);
                }
            } else if (plugin.settingsSection.getString("database-type").equals("sqlite")) {

            } else {
                plugin.getLogger().info("Wrong database type in config!");
            }
        }
        return connection;

    }
    public void initializeDatabase() throws SQLException{
        try (Statement statement = connection.createStatement();) {
            statement.execute("CREATE TABLE IF NOT EXISTS PlayerData " +
                    "(PlayerID int NOT NULL AUTO_INCREMENT, PlayerUUID varchar(255)," +
                    " PlayerName varchar(225), PlayTime int NOT NULL DEFAULT 0," +
                    " PlayerJoins int NOT NULL DEFAULT 0, SentMessages int NOT NULL DEFAULT 0," +
                    " HasOwnJoinMessage int NOT NULL DEFAULT 0, PlayerJoinMessage varchar(255)," +
                    " HasOwnLeaveMessage int NOT NULL DEFAULT 0, PlayerLeaveMessage varchar(255));");
        }
    }
    public void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
    public void checkMissingColumns(){

        try {
            String[] columnsIntToCheck = {"SentMessages", "HasOwnJoinMessage", "HasOwnLeaveMessage"};
            String[] columnsStringToCheck = {"PlayerJoinMessage", "PlayerLeaveMessage"};

            for (String columnIntName : columnsIntToCheck) {
                ResultSet resultSet = getConnection().getMetaData().getColumns(null, null, "PlayerData", columnIntName);

                if (!resultSet.next()){
                    try(Statement statement = getConnection().createStatement()) {
                        statement.execute("ALTER TABLE PlayerData ADD COLUMN " + columnIntName + " int NOT NULL DEFAULT 0");
                    }
                }
            }
            for (String columnStringName : columnsStringToCheck){
                ResultSet resultSet = getConnection().getMetaData().getColumns(null, null, "PlayerData", columnStringName);

                if (!resultSet.next()){
                    try(Statement statement = getConnection().createStatement()) {
                        statement.execute("ALTER TABLE PlayerData ADD COLUMN " + columnStringName + " varchar(255)");
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
