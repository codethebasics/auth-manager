package br.com.codethebasics.authmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

    private User user;

    private Token token;

    private LocalDateTime authenticationDatetime;
}
