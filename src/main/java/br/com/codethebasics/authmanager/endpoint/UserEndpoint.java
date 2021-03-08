package br.com.codethebasics.authmanager.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserEndpoint {

    @GetMapping
    public ResponseEntity<String> list() {
        return ResponseEntity.ok("list()");
    }
}
