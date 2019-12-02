package singlepagespringboot.request;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Interface that enables the definition of virtual hosts.
 */
public interface VirtualHostPolicy {

    /**
     * Returns true if the virtual host feature is enabled, and false otherwise.
     *
     * @return true if virtual hosts are enabled, false otherwise
     */
    boolean isEnabled();

    /**
     * Returns the static file root for the virtual host in the given request, if possible.
     *
     * @param request the request to extract the virtual host from
     * @return the static file root provider for the given virtual host
     */
    StaticFileRootProvider getFileRootProvider(HttpServletRequest request);
}
