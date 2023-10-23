package cz.thepatik.welcomeplugin.utils;

import cz.thepatik.welcomeplugin.WelcomePlugin;
import cz.thepatik.welcomeplugin.database.MySQLDatabase;
import cz.thepatik.welcomeplugin.database.SQLiteDatabase;
import cz.thepatik.welcomeplugin.database.functions.mysql.PlayerFunctions;
import cz.thepatik.welcomeplugin.utils.handlers.MessagesHandler;
import cz.thepatik.welcomeplugin.utils.listeners.utils.MessageUtils;
import cz.thepatik.welcomeplugin.utils.listeners.utils.TitleUtils;

public class Functions {
    public WelcomePlugin welcomePlugin(){
        return WelcomePlugin.getPlugin();
    }
    public MySQLDatabase mySQLDatabase(){
        return new MySQLDatabase();
    }
    public SQLiteDatabase sqLiteDatabase() {
        return welcomePlugin().sqLiteDatabase;
    }
    public PlayerFunctions mysqlPlayerFunctions(){
        return new PlayerFunctions();
    }
    public cz.thepatik.welcomeplugin.database.functions.sqlite.PlayerFunctions sqLitePlayerFunctions(){
        return new cz.thepatik.welcomeplugin.database.functions.sqlite.PlayerFunctions();
    }
    public MessagesHandler getMessagesHandler(){
        return new MessagesHandler(welcomePlugin());
    }
    public MessageUtils getMessageUtils(){
        return new MessageUtils();
    }
    public TitleUtils getTitleUtils(){
        return new TitleUtils();
    }
    public Updater getUpdater(){
        return new Updater(welcomePlugin(), 112870);
    }
}
