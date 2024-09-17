package org.blazejherzog.jobmatcher.web.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;

public class AjaxAwareLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public AjaxAwareLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            super.commence(request, response, authException);
        }
    }
}
