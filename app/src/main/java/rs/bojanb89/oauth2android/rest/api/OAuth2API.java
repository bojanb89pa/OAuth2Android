package rs.bojanb89.oauth2android.rest.api;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rs.bojanb89.oauth2android.rest.model.OAuth2Token;

/**
 * Created by bojanb on 1/16/17.
 */

public interface OAuth2API {

    @POST("oauth/token")
    Call<OAuth2Token> login(@Query("username") String username, @Query("password") String password, @Query("grant_type") String grant_type);

    @POST("oauth/token")
    Call<OAuth2Token> refreshToken(@Query("refresh_token") String refreshToken, @Query("grant_type") String grant_type);
}
