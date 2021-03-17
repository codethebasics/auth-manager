package br.com.codethebasics.authmanager.security.service.impl;

import br.com.codethebasics.authmanager.entity.User;
import br.com.codethebasics.authmanager.security.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class JdbcAuthenticationService implements AuthenticationService {

    @Override
    public User authenticate(User user) throws Exception {
        return null;
    }
}
