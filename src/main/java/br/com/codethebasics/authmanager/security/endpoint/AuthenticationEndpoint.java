package br.com.codethebasics.authmanager.security.endpoint;

import br.com.codethebasics.authmanager.request.AuthenticationRequest;
import br.com.codethebasics.authmanager.response.AuthenticationResponse;
import br.com.codethebasics.authmanager.response.TemplateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/public/auth")
public class AuthenticationEndpoint {

    @PostMapping
    public ResponseEntity<TemplateResponse<AuthenticationResponse>> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest) {

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setUsername(authenticationRequest.getUsername());
        authenticationResponse.setToken("fsdfsd89fsd98fsd89f009s8d");
        authenticationResponse.setExpiresInMinutes(60);
        authenticationResponse.setAuthenticationDatetime(LocalDateTime.now());

        TemplateResponse<AuthenticationResponse> templateResponse = new TemplateResponse<>();
        templateResponse.setStatus(200);
        templateResponse.setMessage("Sucesso");
        templateResponse.setBody(authenticationResponse);

        return ResponseEntity.ok(templateResponse);
    }
}
