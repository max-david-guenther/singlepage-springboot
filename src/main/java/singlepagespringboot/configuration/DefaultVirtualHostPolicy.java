package singlepagespringboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import singlepagespringboot.properties.UIProperties;
import singlepagespringboot.properties.VirtualHostProperties;
import singlepagespringboot.request.RequestResourceMapper;
import singlepagespringboot.request.StaticFileRootProvider;
import singlepagespringboot.request.VirtualHostPolicy;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Optional;

import static org.apache.tomcat.websocket.Constants.HOST_HEADER_NAME;

/**
 * Implements the default virtual host policy that uses multiple folder to represent each virtual host.
 *
 * @author Max Günther
 */
@Configuration
public class DefaultVirtualHostPolicy implements VirtualHostPolicy {
    @Autowired private VirtualHostProperties virtualHostProperties;
    @Autowired private UIProperties uiProperties;

    @Override
    public boolean isEnabled() {
        return virtualHostProperties.isEnabled();
    }

    @Override
    public StaticFileRootProvider getFileRootProvider(HttpServletRequest request) {
        if (virtualHostProperties.isEnabled()) {
            String virtualHost = request.getHeader(HOST_HEADER_NAME);
            if (virtualHost != null) {
                return path -> {
                    File virtualHostRoot = new File(uiProperties.getStaticFileRoot(), virtualHost);
                    return new File(virtualHostRoot, path);
                };
            }
        }
        return path -> new File(uiProperties.getStaticFileRoot(), path);
    }
}
