package singlepagespringboot.configuration;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures Apache Tika, a library for detecting mime types.
 */
@Configuration
public class TikaConfiguration {

    /**
     * Configure a {@link Tika} bean that detects mime types.
     *
     * @return the Tika bean
     */
    @Bean
    public Tika tika() {
        return new Tika();
    }
}
