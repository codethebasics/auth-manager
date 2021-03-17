package br.com.codethebasics.authmanager.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/manager")
public class ManagerEndpoint {

    @PutMapping("/user/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok("Blocking user with id " + userId);
    }
}
