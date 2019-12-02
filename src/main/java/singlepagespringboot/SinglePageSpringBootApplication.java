package singlepagespringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * This is the main entry point into the application.
 *
 * @author Max Günther
 */
@SpringBootApplication
@EnableWebMvc
public class SinglePageSpringBootApplication extends SpringBootServletInitializer {

    /**
     * Spring Boot entry point into the application.
     *
     * @param args Spring Boot commandline arguments
     */
    public static void main(final String args[]) {
        SpringApplication.run(SinglePageSpringBootApplication.class, args);
    }
}

