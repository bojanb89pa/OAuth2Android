package rs.bojanb89.oauth2android.rest;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import rs.bojanb89.oauth2android.OAuth2Activity;
import rs.bojanb89.oauth2android.R;
import rs.bojanb89.oauth2android.rest.model.APIError;
import rs.bojanb89.oauth2android.util.StringUtils;

/**
 * Created by bojanb on 1/18/17.
 */

public abstract class APIErrorHandler<T> implements Callback <T>{

    private Context context;
    private Retrofit retrofit;

    public abstract void success(Call<T> call, Response<T> response);

    protected APIErrorHandler(Context context, Retrofit retrofit) {
        this.context = context;
        this.retrofit = retrofit;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            success(call, response);
        } else {
            // API error handling
            Converter<ResponseBody, APIError> converter;
            converter = retrofit.responseBodyConverter(APIError.class, new Annotation[0]);

            APIError error;
            try {
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                error = new APIError();
            }

            String errorMessage;
            try {
                errorMessage = StringUtils.getResourceString("ERROR_" + error.codeMessage, context);
            } catch (IllegalArgumentException e) {
                errorMessage = context.getResources().getString(R.string.ERROR_50000_DEFAULT_CODE);
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(context, context.getResources().getString(R.string.ERROR_50000_DEFAULT_CODE), Toast.LENGTH_LONG).show();
    }
}
