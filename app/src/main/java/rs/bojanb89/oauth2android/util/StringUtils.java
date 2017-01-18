package rs.bojanb89.oauth2android.util;

import android.content.Context;
import android.os.Build;
import android.util.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by bojanb on 1/17/17.
 */

public class StringUtils {

    public static String toBase64(String text) {

        Charset charset = Charset.forName("UTF-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            charset = StandardCharsets.UTF_8;
        }
        byte[] data = text.getBytes(charset);
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }


    public static String fromBase64(String base64) {

        byte[] data = Base64.decode(base64, Base64.NO_WRAP);

        Charset charset = Charset.forName("UTF-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            charset = StandardCharsets.UTF_8;
        }

        return new String(data, charset);
    }

    public static String getResourceString(String name, Context context) {
        int nameResourceID = context.getResources().getIdentifier(name, "string", context.getApplicationInfo().packageName);
        if (nameResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + name);
        } else {
            return context.getString(nameResourceID);
        }
    }
}
