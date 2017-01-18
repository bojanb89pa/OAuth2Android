package rs.bojanb89.oauth2android.dagger.module;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rs.bojanb89.oauth2android.BuildConfig;
import rs.bojanb89.oauth2android.rest.HeaderInterceptor;
import rs.bojanb89.oauth2android.rest.api.HealthAPI;
import rs.bojanb89.oauth2android.rest.api.OAuth2API;
import rs.bojanb89.oauth2android.rest.api.UserAPI;
import rs.bojanb89.oauth2android.rest.auth.AuthorizationManager;
import rs.bojanb89.oauth2android.rest.auth.TokenAuthenticator;

/**
 * Created by bojanb on 1/16/17.
 */
@Module
public class APIModule {

    @Provides
    @Named("serverUrl")
    String provideServerUrl() {
        return BuildConfig.SERVER_URL + "/";
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;        // 10 MB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    HeaderInterceptor provideHeaderInterceptor(AuthorizationManager authorizationManager) {
        return new HeaderInterceptor(authorizationManager);
    }

    @Provides
    @Singleton
    Authenticator provideAuthenticator() {
        return new TokenAuthenticator();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache, HeaderInterceptor headerInterceptor, Authenticator authenticator) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache)
                .addInterceptor(headerInterceptor)
                .authenticator(authenticator);
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@Named("serverUrl") String serverUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    OAuth2API provideOAuth2API(Retrofit retrofit) {
        return retrofit.create(OAuth2API.class);
    }

    @Provides
    @Singleton
    HealthAPI provideHealthAPI(Retrofit retrofit) {
        return retrofit.create(HealthAPI.class);
    }

    @Provides
    @Singleton
    UserAPI provideUserAPI(Retrofit retrofit) {
        return retrofit.create(UserAPI.class);
    }
}
