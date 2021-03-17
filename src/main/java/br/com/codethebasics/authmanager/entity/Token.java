package br.com.codethebasics.authmanager.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @NonNull
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "expired")
    private Boolean expired;

}
