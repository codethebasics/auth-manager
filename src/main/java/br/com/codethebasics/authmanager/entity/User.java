package br.com.codethebasics.authmanager.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @NonNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_logon")
    private LocalDateTime lastLogon;

    @ManyToMany(mappedBy = "users")
    private List<Permission> permissions;
}
