package rs.bojanb89.oauth2android.rest.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rs.bojanb89.oauth2android.rest.model.User;

/**
 * Created by bojanb on 1/17/17.
 */

public interface UserAPI {

    @POST("signup")
    Call<ResponseBody> signup(@Body User user);

    @GET("user")
    Call<User> getUser(@Query("username") String username);
}
