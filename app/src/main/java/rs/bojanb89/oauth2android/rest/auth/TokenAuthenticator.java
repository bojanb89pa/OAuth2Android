package rs.bojanb89.oauth2android.rest.auth;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import rs.bojanb89.oauth2android.dagger.Injector;
import rs.bojanb89.oauth2android.rest.HeaderInterceptor;
import rs.bojanb89.oauth2android.rest.api.OAuth2API;
import rs.bojanb89.oauth2android.rest.model.OAuth2Token;


/**
 * Created by bojanb on 1/16/17.
 */

public class TokenAuthenticator implements Authenticator {

    @Inject
    AuthorizationManager authManager;

    @Inject
    OAuth2API oAuth2API;

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        Injector.get().inject(this);

        if (authManager.getOAuth2Token() != null) {
            String refreshToken = authManager.getOAuth2Token().refreshToken;
            authManager.authType = AuthorizationManager.AuthType.AUTH_BASIC;
            Call<OAuth2Token> call = oAuth2API.refreshToken(refreshToken, AuthorizationManager.GRANT_TYPE_REFRESH_TOKEN);
            retrofit2.Response<OAuth2Token> refreshResponse = call.execute();
            if (refreshResponse.isSuccessful()) {
                OAuth2Token newOAuth2Token = refreshResponse.body();
                if (newOAuth2Token != null) {
                    authManager.setOAuth2Token(newOAuth2Token);

                    Request.Builder requestBuilder = response.request().newBuilder();

                    authManager.authType = AuthorizationManager.AuthType.AUTH_BEARER;

//                  Add new header to rejected request and retry it
                    requestBuilder.header(HeaderInterceptor.HEADER_AUTH, authManager.getAuthorization());

                    return requestBuilder.build();
                }
            }
        }
        return null;
    }
}
