package fr.diginamic.security;

import fr.diginamic.entities.Role;
import fr.diginamic.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemoireService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemoireService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin")) {
            return new User(username,passwordEncoder.encode("admin"), new Role("ROLE_ADMIN"));
        } else if (username.equals("user")) {
            return new User(username, passwordEncoder.encode("user"), new Role("ROLE_USER"));
        }
        throw new UsernameNotFoundException(username + " est un utilisateur inconnu.");
    }
}
