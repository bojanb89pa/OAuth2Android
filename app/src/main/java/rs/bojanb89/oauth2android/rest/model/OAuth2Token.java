package rs.bojanb89.oauth2android.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by bojanb on 1/16/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuth2Token {


    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @JsonIgnore
    public Date expirationDate;

    @JsonProperty("expires_in")
    private Integer expiresIn;      // value in seconds

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        if(expiresIn != null && expiresIn != -1) {
            expirationDate = new Date(new Date().getTime() + expiresIn * 1000);
        }
    }

    public boolean isExpired() {
        if(expirationDate != null) {
            if (expirationDate.getTime() < new Date().getTime()) {
                return false;
            }
        }
        return true;
    }
}
