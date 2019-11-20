package singlepagespringboot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Springs WebMvc.
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * Enforce a default content type of <code>application/octet-stream</code>.
     *
     * @param configurer the content negotiation configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_OCTET_STREAM);
    }
}
