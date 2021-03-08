package br.com.codethebasics.authmanager.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * -----------------------------
 * Verifica a saúde da aplicação
 * -----------------------------
 * @author codethebasics
 */
@RestController
@RequestMapping("/api/v1/public/healthcheck")
public class HealthCheck {

    @GetMapping
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("Application running... 200 [OK]");
    }
}
