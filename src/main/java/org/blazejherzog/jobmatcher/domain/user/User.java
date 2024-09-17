package org.blazejherzog.jobmatcher.domain.user;

import org.blazejherzog.jobmatcher.infrastructure.user.UserAuthority;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
public class User implements UserDetails {

    @NonNull
    private final UserId id;

    @NonNull
    private final String username;

    @NonNull
    private final String firstName;

    @NonNull
    private final String lastName;

    @NonNull
    private final String password;

    @NonNull
    private final Boolean enabled;

    private final Set<UserAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(userAuthority -> new SimpleGrantedAuthority(userAuthority.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
