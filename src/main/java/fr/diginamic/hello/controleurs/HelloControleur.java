package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Hello controleur.
 */
@RestController
@RequestMapping("/hello")

public class HelloControleur {

    @Autowired
    private HelloService service;

    /**
     * Dire hello string.
     *
     * @return the string
     */
    @GetMapping
    public String direHello(){
        return service.salutations();
    }

}
