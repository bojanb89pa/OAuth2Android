package rs.bojanb89.oauth2android.data;

import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

/**
 * Created by bojanb on 1/16/17.
 */

public class DataCachingManager {

    private SharedPreferences preferences;

    public DataCachingManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public <S>S get(Class<S> objectClass, String key) {
        String jsonString = preferences.getString(key, "");
        try {
            return new ObjectMapper().readValue(jsonString, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(String key, Object object) {

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String jsonString = objectWriter.writeValueAsString(object);
            preferences.edit().putString(key, jsonString).apply();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
