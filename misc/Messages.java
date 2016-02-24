

package misc;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Messages {

    private static String         bundleName     = "misc.messages_en";                   //$NON-NLS-1$
    private static ResourceBundle resourceBundle = ResourceBundle
                                                         .getBundle(Messages.bundleName);

    public static String getString(String key) {

        try {
            return Messages.resourceBundle.getString(key);
        }catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }


    public static void setLanguage(String lang) {

        try {
            Messages.bundleName = Messages.bundleName.replaceAll("_..", "_"
                    + lang);
        }catch (MissingResourceException e) {
            Messages.bundleName = "misc.messages_en";
        }finally {
            Messages.resourceBundle = ResourceBundle
                    .getBundle(Messages.bundleName);
        }
    }

    private Messages() {

    }
}
