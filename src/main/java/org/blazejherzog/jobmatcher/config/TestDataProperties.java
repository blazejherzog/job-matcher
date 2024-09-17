package org.blazejherzog.jobmatcher.config;

import org.blazejherzog.jobmatcher.infrastructure.user.UserAuthority;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jobmatcher.test-data")
public class TestDataProperties {

    private List<User> users = new ArrayList<>();

    @Data
    public static class User {

        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private boolean enabled = true;
        private Set<UserAuthority> authorities = new HashSet<>();
    }
}
