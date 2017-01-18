package rs.bojanb89.oauth2android.rest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rs.bojanb89.oauth2android.rest.auth.AuthorizationManager;

/**
 * Created by bojanb on 1/16/17.
 */

public class HeaderInterceptor implements Interceptor {

    private final AuthorizationManager authorizationManager;

    public static final String HEADER_AUTH = "Authorization";

    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_JSON = "application/json";

    public HeaderInterceptor(AuthorizationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header(HEADER_ACCEPT, HEADER_JSON)
                .method(original.method(), original.body());

        if(authorizationManager.hasAuthorization()) {
            requestBuilder.header(HEADER_AUTH, authorizationManager.getAuthorization());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
