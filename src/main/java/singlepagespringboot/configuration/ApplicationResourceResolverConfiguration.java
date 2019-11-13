package singlepagespringboot.configuration;

import singlepagespringboot.properties.UIProperties;
import singlepagespringboot.request.StaticFileRootProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Configure beans that manage how application resources are resolved.
 *
 * @author Max Günther
 */
@Configuration
public class ApplicationResourceResolverConfiguration {
    @Autowired private UIProperties uiProperties;

    /**
     * Configure a bean that resolves the absolute path to a file relative the static file root.
     *
     * @return the provider
     */
    @Bean
    public StaticFileRootProvider staticFileRootProvider() {
        return path -> new File(uiProperties.getStaticFileRoot(), path);
    }
}
