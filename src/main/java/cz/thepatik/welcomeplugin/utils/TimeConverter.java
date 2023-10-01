package cz.thepatik.welcomeplugin.utils;

public class TimeConverter {
    public static String convertSecondsToTime(int seconds){
        int days = (seconds / 86400);
        seconds %= 86400;
        int hours = (seconds / 3600);
        seconds %= 3600;
        int minutes = (seconds / 60);
        seconds %= 60;

        StringBuilder result = new StringBuilder();

        if (days > 0){
            result.append(days).append(" days ");
        }
        if (hours > 0){
            result.append(hours).append(" hours ");
        }
        if (minutes > 0){
            result.append(minutes).append(" minutes ");
        }
        if (seconds > 0){
            result.append(seconds).append(" seconds");
        }

        return result.toString().trim();
    }
}
