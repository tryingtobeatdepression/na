package netappspractical.demo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * To fully leverage the Spring dependency injection and be able to retrieve the authentication everywhere,
 * not just in @Controller beans, we need to hide the static access behind a simple facade.
 * @see https://www.baeldung.com/get-user-in-spring-security
 */
interface IAuthenticationFacade {
    Authentication getAuthentication();
}

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

