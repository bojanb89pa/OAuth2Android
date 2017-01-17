package rs.bojanb89.oauth2android.rest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import rs.bojanb89.oauth2android.rest.model.Health;

/**
 * Created by bojanb on 1/17/17.
 */

public interface HealthAPI {

    @GET("health")
    Call<Health> health();
}
