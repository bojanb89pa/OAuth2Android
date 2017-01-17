package rs.bojanb89.oauth2android.dagger;

import rs.bojanb89.oauth2android.OAuth2App;
import rs.bojanb89.oauth2android.dagger.component.DaggerOAuth2AppComponent;
import rs.bojanb89.oauth2android.dagger.component.OAuth2AppComponent;
import rs.bojanb89.oauth2android.dagger.module.OAuth2AppModule;

/**
 * Created by bojanb on 1/16/17.
 */

public enum Injector {
    INSTANCE;
    OAuth2AppComponent applicationComponent;

    public static void initialize(OAuth2App oAuth2App) {
        INSTANCE.applicationComponent = DaggerOAuth2AppComponent.builder()
                .oAuth2AppModule(new OAuth2AppModule(oAuth2App))
                .build();
    }

    public static OAuth2AppComponent get() {
        return INSTANCE.applicationComponent;
    }
}
