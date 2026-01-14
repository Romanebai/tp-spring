package fr.diginamic.security;

import fr.diginamic.daos.UserRepository;
import fr.diginamic.entities.Role;
import fr.diginamic.entities.User;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private UserRepository utilisateurRepository;
    private PasswordEncoder encoder;

    @Autowired
    public JpaUserDetailsService(UserRepository utilisateurRepository, PasswordEncoder encoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.encoder = encoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByUsername(username);
    }

    @PostConstruct
    @Transactional
    public void chargeUsers() {
        if (utilisateurRepository.findByUsername("admin") == null) {
            utilisateurRepository.save(new User("admin", encoder.encode("admin"), new Role("ROLE_ADMIN")));
        }

        if (utilisateurRepository.findByUsername("user") == null) {
            utilisateurRepository.save(new User("user", encoder.encode("user"), new Role("ROLE_USER")));
        }
    }
}
