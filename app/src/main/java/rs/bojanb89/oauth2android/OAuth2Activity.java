package rs.bojanb89.oauth2android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rs.bojanb89.oauth2android.dagger.Injector;
import rs.bojanb89.oauth2android.rest.APICallback;
import rs.bojanb89.oauth2android.rest.api.HealthAPI;
import rs.bojanb89.oauth2android.rest.api.OAuth2API;
import rs.bojanb89.oauth2android.rest.api.UserAPI;
import rs.bojanb89.oauth2android.rest.auth.AuthorizationManager;
import rs.bojanb89.oauth2android.rest.model.Health;
import rs.bojanb89.oauth2android.rest.model.OAuth2Token;
import rs.bojanb89.oauth2android.rest.model.User;

public class OAuth2Activity extends AppCompatActivity {

    @Inject
    OAuth2API oAuth2API;

    @Inject
    HealthAPI healthAPI;

    @Inject
    UserAPI userAPI;

    @Inject
    Retrofit retrofit;

    @Inject
    AuthorizationManager authManager;
    @BindString(R.string.ERROR_50000_DEFAULT_CODE) String defaultErrorMessage;

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
                    Toast.makeText(OAuth2Activity.this, defaultErrorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                Toast.makeText(OAuth2Activity.this, defaultErrorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.loginBtn)
    public void login() {
        login("user", "password");
    }

    private void login(String username, String password) {
        authManager.authType = AuthorizationManager.AuthType.AUTH_BASIC;
        oAuth2API.login(username, password, AuthorizationManager.GRANT_TYPE_PASSWORD).enqueue(new Callback<OAuth2Token>() {
            @Override
            public void onResponse(Call<OAuth2Token> call, Response<OAuth2Token> response) {
                if(response.isSuccessful()) {
                    authManager.setOAuth2Token(response.body());
                    Toast.makeText(OAuth2Activity.this, "Access token: " + response.body().accessToken, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OAuth2Activity.this, defaultErrorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OAuth2Token> call, Throwable t) {
                Toast.makeText(OAuth2Activity.this, defaultErrorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }



    @OnClick(R.id.logoutBtn)
    public void logout() {
        authManager.setOAuth2Token(null);
        Toast.makeText(OAuth2Activity.this, "Access token removed", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.registerBtn)
    public void register() {
        authManager.authType = AuthorizationManager.AuthType.AUTH_NONE;
        final User user = new User("test", "test@mailinator.com", "test123");
        userAPI.signup(user).enqueue(new APICallback<ResponseBody>(this, retrofit) {
                                         @Override
                                         public void success(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                login(user.username, user.password);
                                            }
                                         }

                                         @Override
                                         public void failure(Call<ResponseBody> call, Throwable t) {
                                             Toast.makeText(OAuth2Activity.this, defaultErrorMessage, Toast.LENGTH_LONG).show();
                                         }
                                     });
    }

    @OnClick(R.id.checkUserBtn)
    public void checkUser() {
        userAPI.getUser("test").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Toast.makeText(OAuth2Activity.this, "Username: " + user.username + "\nemail: " + user.email, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OAuth2Activity.this, defaultErrorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(OAuth2Activity.this, defaultErrorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

}
