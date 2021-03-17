package br.com.codethebasics.authmanager.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuthenticationResponse {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("authentication_datetime")
    private LocalDateTime authenticationDatetime;

    @JsonProperty("expires_in_minutes")
    private Integer expiresInMinutes;

    @JsonProperty("token")
    private String token;

    @JsonProperty("misc")
    private Object misc;
}
