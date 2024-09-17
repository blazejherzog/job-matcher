package org.blazejherzog.jobmatcher.infrastructure.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.EnumType;
import lombok.Getter;

import java.util.Set;

@Entity
@Getter
@Table(name = "t_users")
public class UserEntity {

    private static final String SEQ_NAME = "t_users_id_seq";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "t_user_authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Set<UserAuthority> authorities;
}
