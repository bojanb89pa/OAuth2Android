package rs.bojanb89.oauth2android.rest.auth;

import rs.bojanb89.oauth2android.BuildConfig;
import rs.bojanb89.oauth2android.data.DataCachingManager;
import rs.bojanb89.oauth2android.rest.model.OAuth2Token;
import rs.bojanb89.oauth2android.util.StringUtils;

/**
 * Created by bojanb on 1/16/17.
 */

public class AuthorizationManager {


    public final static String GRANT_TYPE_PASSWORD = "password";
    public final static String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    private final static String OAUTH2_TOKEN_KEY = "oauth2Token";

    private final DataCachingManager dataCachingManager;

    private OAuth2Token oAuth2Token;

    public enum AuthType {
        AUTH_BASIC("Basic"), AUTH_BEARER("Bearer"), AUTH_NONE("None");

        private final String type;

        AuthType(String type) {
            this.type = type;
        }
    }

    public AuthType authType = AuthType.AUTH_BEARER;


    public AuthorizationManager(DataCachingManager dataCachingManager) {
        this.dataCachingManager = dataCachingManager;
    }

    public OAuth2Token getOAuth2Token() {
        if(oAuth2Token == null) {
            oAuth2Token = dataCachingManager.get(OAuth2Token.class, OAUTH2_TOKEN_KEY);
            if(oAuth2Token != null) {
                authType = AuthType.AUTH_BEARER;
            }
        }

        return oAuth2Token;
    }


    public void setOAuth2Token(OAuth2Token oauth2Token) {
        this.oAuth2Token = oauth2Token;
        dataCachingManager.save(OAUTH2_TOKEN_KEY, oauth2Token);
        authType = AuthType.AUTH_BEARER;
    }

    public String getAuthorization() {

        StringBuilder sb = new StringBuilder();
        sb.append(authType.type).append(" ");
        switch (authType) {
            case AUTH_BASIC:
                sb.append(StringUtils.toBase64(BuildConfig.clientName + ":" + BuildConfig.clientSecret));
                break;
            case AUTH_BEARER:
                if(oAuth2Token != null) {
                    sb.append(oAuth2Token.accessToken);
                }
                break;
        }

        return sb.toString();
    }

    public boolean hasAuthorization() {
        return authType != null && !authType.equals(AuthType.AUTH_NONE);
    }


}
