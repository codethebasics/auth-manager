package br.com.codethebasics.authmanager.security.service;

import br.com.codethebasics.authmanager.entity.User;

public interface AuthenticationService {

    User authenticate(User user) throws Exception;

}
