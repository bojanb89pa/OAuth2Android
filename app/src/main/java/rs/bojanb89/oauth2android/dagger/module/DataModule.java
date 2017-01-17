package rs.bojanb89.oauth2android.dagger.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rs.bojanb89.oauth2android.data.DataCachingManager;
import rs.bojanb89.oauth2android.rest.auth.AuthorizationManager;

/**
 * Created by bojanb on 1/13/17.
 */
@Module
public class DataModule {

    private static final String PREFS_DEFAULT = "oauth2";

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return application.getSharedPreferences(PREFS_DEFAULT, Context.MODE_PRIVATE);
    }
    @Provides
    @Singleton
    DataCachingManager providesDataCachingManager(SharedPreferences preferences) {
        return new DataCachingManager(preferences);
    }

    @Provides
    @Singleton
    AuthorizationManager provideAuthorizationManager(DataCachingManager dataCachingManager) {
        return new AuthorizationManager(dataCachingManager);
    }
}
