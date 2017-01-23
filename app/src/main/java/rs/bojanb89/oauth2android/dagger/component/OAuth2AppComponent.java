package rs.bojanb89.oauth2android.dagger.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import rs.bojanb89.oauth2android.OAuth2Activity;
import rs.bojanb89.oauth2android.dagger.module.APIModule;
import rs.bojanb89.oauth2android.dagger.module.DataModule;
import rs.bojanb89.oauth2android.dagger.module.OAuth2AppModule;
import rs.bojanb89.oauth2android.rest.auth.TokenAuthenticator;

/**
 * Created by bojanb on 1/13/17.
 */
@Singleton
@Component (
        modules = {
                OAuth2AppModule.class,
                DataModule.class,
                APIModule.class
        }
)
public interface OAuth2AppComponent {
    void inject(TokenAuthenticator tokenAuthenticator);

    void inject(OAuth2Activity oauth2Activity);

    Application application();
}
