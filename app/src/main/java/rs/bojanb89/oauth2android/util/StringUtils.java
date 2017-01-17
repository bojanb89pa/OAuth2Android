package rs.bojanb89.oauth2android.util;

import android.os.Build;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
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
}
