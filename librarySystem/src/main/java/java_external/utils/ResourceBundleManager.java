package java_external.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleManager{

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("text", new Locale("en","EN"));


    public static void setNewLocale(Locale locale){
        resourceBundle = ResourceBundle.getBundle("text", locale);
    }

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}