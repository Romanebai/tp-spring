package fr.diginamic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationConfiguration config;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        config.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),
                req.getPassword()));
        return jwtUtil.generateToken(req.getUsername());
    }
}
