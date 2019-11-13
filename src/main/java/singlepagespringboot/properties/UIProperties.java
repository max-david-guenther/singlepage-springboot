package singlepagespringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Root configuration properties of the application.
 *
 * @author Max Günther
 */
@Component
@ConfigurationProperties(prefix="singlepage-springboot")
public class UIProperties {
    private File staticFileRoot;
    private String indexFileName;

    /**
     * The root of static files served.
     *
     * @return the root
     */
    public File getStaticFileRoot() {
        return staticFileRoot;
    }

    /**
     * The root of static files served.
     *
     * @param staticFileRoot the file root
     */
    public void setStaticFileRoot(File staticFileRoot) {
        this.staticFileRoot = staticFileRoot;
    }

    /**
     * The name of the index file name. This is the file that is selected when the request resource doesn't match any
     * resource available to the server. Default is <code>index.html</code>.
     *
     * @return the name of the index file
     */
    public String getIndexFileName() {
        return indexFileName;
    }

    /**
     * The name of the index file name. This is the file that is selected when the request resource doesn't match any
     * resource available to the server. Default is <code>index.html</code>.
     *
     * @param indexFileName the name of the index file
     */
    public void setIndexFileName(String indexFileName) {
        this.indexFileName = indexFileName;
    }
}
