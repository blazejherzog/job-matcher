package org.blazejherzog.jobmatcher.infrastructure.user;

import org.blazejherzog.jobmatcher.domain.user.User;
import org.blazejherzog.jobmatcher.domain.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(this::toUser)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    private UserDetails toUser(UserEntity userEntity) {
        return User.builder()
                .id(UserId.of(userEntity.getId()))
                .username(userEntity.getUsername())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .password(userEntity.getPassword())
                .enabled(userEntity.getEnabled())
                .authorities(userEntity.getAuthorities())
                .build();
    }
}
