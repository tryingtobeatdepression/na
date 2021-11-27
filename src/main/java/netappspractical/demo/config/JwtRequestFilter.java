package netappspractical.demo.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import netappspractical.demo.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Filters:
 * ** Provide the ability to encapsulate recurring tasks in reusable units.
 * (Modularizing Code) **
 * ** Can be used to transform the response from a servlet or a JSP page.
 * (i.e. JSON) **
 * <p>
 * How it works:
 * A filter chain, passed to a filter by the container,
 * provides a mechanism for invoking a series of filters.
 * A filter config contains initialization data.
 * <p>
 * DoFilter:
 * ** Customizes req/res.
 * ** Invokes the next entity in the filter chain.
 *
 * TODO: Catch malformed jwt exception.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.auth.header}")
    private String authHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(authHeader);
        String username = null, jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token.");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired.");
            }
        }

        // Validate token and get user credentials
        // If no authentication exists && username exists
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            // If the token is valid, configure Spring Security to manually set authentication.
            if (this.jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }

        // Invokes the next entity by calling the doFilter on teh chain object
        // (passing in the request and response it was called with,
        // or the wrapped versions it may have created)
        // Alternatively, it can choose to block the request by not making the call to invoke the next entity.
        // In the latter case, the filter is responsible for filling out the response.
        filterChain.doFilter(request, response);
    }
}

