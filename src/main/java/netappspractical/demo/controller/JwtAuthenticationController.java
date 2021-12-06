package netappspractical.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import netappspractical.demo.config.JwtTokenUtil;
import netappspractical.demo.model.JwtRequest;
import netappspractical.demo.model.JwtResponse;
import netappspractical.demo.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Using Spring AuthenticationManager we authenticate
 * the username and password.
 * If credentials are valid, jwt is created using JwtTokenUtil
 * and provided to the client
 */
@RestController
@CrossOrigin()
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    /**
     * ResponseEntity represents the whole HTTP response: status code, headers, and body.
     *
     * @param authReq
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/login",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            consumes = {
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authReq)
            throws Exception {
        authenticate(authReq.getEmail(), authReq.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authReq.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * Authenticate an existing user
     *
     * @param username
     * @param password
     * @throws Exception
     */
    private void authenticate(String username, String password)
            throws Exception {

        try {
            // UsernamePasswordAuthenticationToken: An Authentication implementation that is
            // designed for simple representation of a username and password.
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            // DisabledException Thrown if an authentication request is rejected
            // because the account is disabled.
            // Makes no assertion about credentials validity.
            throw new Exception("User Disabled. ", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials. ", e);
        }
    }
}
