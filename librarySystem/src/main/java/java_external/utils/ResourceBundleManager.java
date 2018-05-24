package java_external.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleManager {

    public static void setNewLocale(Locale locale) {
        Locale.setDefault(locale);
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("properties/text", new UTF8Control());
    }

    public static String getMessage(String key) {
        return ResourceBundle.getBundle("properties/text", new UTF8Control()).getString(key);
    }
}