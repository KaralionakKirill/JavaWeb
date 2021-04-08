package com.epam.bar.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class used to localize content in the server
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class LocalizationMessage {
    private LocalizationMessage() {
    }

    /**
     * Localize string.
     *
     * @param locale  the locale
     * @param message the message
     * @return the string
     */
    public static String localize(String locale, String message) {
        String[] parsedLocale = locale.split("_");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("property.content",
                new Locale(parsedLocale[0], parsedLocale[1]));
        return resourceBundle.getString(message);
    }
}
