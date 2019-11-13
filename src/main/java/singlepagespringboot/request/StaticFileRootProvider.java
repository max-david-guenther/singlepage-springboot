package singlepagespringboot.request;

import java.io.File;

/**
 * Provides the root of static files served by the application.
 */
public interface StaticFileRootProvider {

    /**
     * Makes the given file path an absolute file path relative to the root.
     *
     * @param path the relative file path
     * @return the absolute file
     */
    public File getFile(String path);
}
