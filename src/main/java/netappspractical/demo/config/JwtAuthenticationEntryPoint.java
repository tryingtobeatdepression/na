package netappspractical.demo.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * AuthenticationEntryPoint: is an interface implemented by ExceptionTranslationFilter,
 * basically a filter which is the first point of entry for Spring Security.
 * It is the entry point to check if a user is authenticated and logs the person in
 * or throws exception (unauthorized).
 * Usually the class can be used like that in simple applications but when using Spring security in REST,
 * JWT etc one will have to extend it to provide better Spring Security filter chain management.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
