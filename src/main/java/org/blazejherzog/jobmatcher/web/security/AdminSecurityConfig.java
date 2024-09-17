package org.blazejherzog.jobmatcher.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class AdminSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .csrf(configurer -> configurer.ignoringRequestMatchers(toH2Console()))
                .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin((form) -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin")
                        .permitAll()
                )
                .logout(configurer -> configurer
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                        .permitAll())
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(
                        new AjaxAwareLoginUrlAuthenticationEntryPoint("/admin/login")));

        return http.build();
    }
}
