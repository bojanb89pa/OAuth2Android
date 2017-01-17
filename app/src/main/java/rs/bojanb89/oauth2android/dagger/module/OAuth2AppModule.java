package rs.bojanb89.oauth2android.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rs.bojanb89.oauth2android.OAuth2App;

/**
 * Created by bojanb on 1/13/17.
 */
@Module
public class OAuth2AppModule {
    private final OAuth2App application;

    public OAuth2AppModule(OAuth2App application) {
        this.application = application;
    }

    public OAuth2App provideOAuth2App() { return application; }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

}
