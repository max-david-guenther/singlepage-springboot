package singlepagespringboot.configuration;

import singlepagespringboot.locale.AcceptHeaderAndCookieLocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Configure beans concerning locale.
 *
 * @author Max Günther
 */
@Configuration
public class LocaleConfiguration {

    /**
     * Configure a bean that resolves the best matching locale from a HTTP request.
     *
     * @return the resolver
     */
    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        return new AcceptHeaderAndCookieLocaleResolver();
    }
}
