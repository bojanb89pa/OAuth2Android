package rs.bojanb89.oauth2android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.bojanb89.oauth2android.dagger.Injector;
import rs.bojanb89.oauth2android.rest.api.HealthAPI;
import rs.bojanb89.oauth2android.rest.api.OAuth2API;
import rs.bojanb89.oauth2android.rest.auth.AuthorizationManager;
import rs.bojanb89.oauth2android.rest.model.Health;
import rs.bojanb89.oauth2android.rest.model.OAuth2Token;

public class OAuth2Activity extends AppCompatActivity {

    @Inject
    OAuth2API oAuth2API;

    @Inject
    HealthAPI healthAPI;

    @Inject
    AuthorizationManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.get().inject(this);
        setContentView(R.layout.activity_oauth2);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.checkServerBtn)
    public void checkServer() {
        authManager.authType = AuthorizationManager.AuthType.AUTH_NONE;
        healthAPI.health().enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(OAuth2Activity.this, "Status: " + response.body().status, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OAuth2Activity.this, "Error occurred", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                Toast.makeText(OAuth2Activity.this, "Error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.loginBtn)
    public void login() {
        authManager.authType = AuthorizationManager.AuthType.AUTH_BASIC;
        oAuth2API.login("user", "password", AuthorizationManager.GRANT_TYPE).enqueue(new Callback<OAuth2Token>() {
            @Override
            public void onResponse(Call<OAuth2Token> call, Response<OAuth2Token> response) {
                if(response.isSuccessful()) {
                    authManager.setOAuth2Token(response.body());
                    authManager.authType = AuthorizationManager.AuthType.AUTH_BEARER;
                    Toast.makeText(OAuth2Activity.this, "Access token: " + response.body().accessToken, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OAuth2Activity.this, "Error occurred", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OAuth2Token> call, Throwable t) {
                Toast.makeText(OAuth2Activity.this, "Error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

}
