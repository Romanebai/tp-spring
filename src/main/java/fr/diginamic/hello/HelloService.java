package fr.diginamic.hello;

import org.springframework.stereotype.Service;

/**
 * The type Hello service.
 */
@Service
public class HelloService {
    /**
     * Salutations string.
     *
     * @return the string
     */
    public String salutations() {
        return "Je suis la classe de service et je vous dis Bonjour";
    }


}
