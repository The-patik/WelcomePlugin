package cz.thepatik.welcomeplugin.utils;

import cz.thepatik.welcomeplugin.utils.handlers.MessagesHandler;

public class TimeConverter {

    public static String convertSecondsToTime(int seconds, MessagesHandler messagesHandler){
        int days = (seconds / 86400);
        seconds %= 86400;
        int hours = (seconds / 3600);
        seconds %= 3600;
        int minutes = (seconds / 60);
        seconds %= 60;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(formatNumberWithGrammaticalCases
                    (days, messagesHandler.getMessages("time-format", "singular-days")
                            , messagesHandler.getMessages("time-format", "pluralFew-days")
                            , messagesHandler.getMessages("time-format", "pluralMany-days")))
                    .append(" ");
        }
        if (hours > 0) {
            result.append(formatNumberWithGrammaticalCases
                    (hours,messagesHandler.getMessages("time-format", "singular-hours")
                            , messagesHandler.getMessages("time-format", "pluralFew-hours")
                            , messagesHandler.getMessages("time-format", "pluralMany-hours")))
                    .append(" ");
        }
        if (minutes > 0) {
            result.append(formatNumberWithGrammaticalCases
                    (minutes, messagesHandler.getMessages("time-format", "singular-minutes")
                            , messagesHandler.getMessages("time-format", "pluralFew-minutes")
                            , messagesHandler.getMessages("time-format", "pluralMany-minutes")))
                    .append(" ");
        }
        if (seconds > 0) {
            result.append(formatNumberWithGrammaticalCases
                    (seconds, messagesHandler.getMessages("time-format", "singular-seconds")
                            , messagesHandler.getMessages("time-format", "pluralFew-seconds")
                            , messagesHandler.getMessages("time-format", "pluralMany-seconds")));
        }

        return result.toString().trim();
    }

    public static String formatNumberWithGrammaticalCases(int number, String singular, String pluralFew, String pluralMany) {
        if (number == 1) {
            return number + " " + singular;
        } else if (number >= 2 && number <= 4) {
            return number + " " + pluralFew;
        } else {
            return number + " " + pluralMany;
        }
    }
}
