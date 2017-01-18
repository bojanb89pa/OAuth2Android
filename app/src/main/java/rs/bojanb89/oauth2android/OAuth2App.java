package rs.bojanb89.oauth2android;

import android.app.Application;

import rs.bojanb89.oauth2android.dagger.Injector;

/**
 * Created by bojanb on 1/13/17.
 */

public class OAuth2App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.initialize(this);
    }

}
