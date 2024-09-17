package org.blazejherzog.jobmatcher.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class UserSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/user/**") // Match only user URLs
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .csrf(configurer -> configurer.ignoringRequestMatchers(toH2Console()))
                .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin((form) -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(configurer -> configurer
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(
                        new AjaxAwareLoginUrlAuthenticationEntryPoint("/user/login")));

        return http.build();
    }
}
