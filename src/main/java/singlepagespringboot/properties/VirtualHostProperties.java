package singlepagespringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Configuration properties for virtual hosts.
 *
 * @author Max Günther
 */
@Component
@ConfigurationProperties(prefix="singlepage-springboot.virtual-host")
public class VirtualHostProperties {
    private boolean enabled;

    /**
     * If true the virtual host feature is enabled. Default is <code>false</code>.
     *
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * If true the virtual host feature is enabled. Default is <code>false</code>.
     *
     * @param enabled true if enabled, false otherwise
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
